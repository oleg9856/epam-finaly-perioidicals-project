package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.SubscriptionDAO;
import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.entity.*;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.SubscriptionService;
import com.gmail.fursovych20.service.exception.InsufficientFundsInAccountException;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionDAO subscriptionDao;
	private final PublicationService publicationService;
	private final UserDAO userDao;

	public SubscriptionServiceImpl(SubscriptionDAO subscriptionDao, PublicationService publicationService, UserDAO userDao) {
		this.subscriptionDao = subscriptionDao;
		this.publicationService = publicationService;
		this.userDao = userDao;
	}

	@Override
	public List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws ServiceException {
		List<Subscription> subscriptions;
		try {
			subscriptions = subscriptionDao.findActiveUserSubscriptionsByUserId(userId);
			LocalDate currentDate = LocalDate.now();
			Iterator<Subscription> iterator = subscriptions.iterator();
			while (iterator.hasNext()) {
				Subscription subscription = iterator.next();
				LocalDate dateOfExpiration = subscription.getEndLocalDate();
				if (dateOfExpiration.isBefore(currentDate)) {
					subscription.setStatus(SubscriptionStatus.EXPIRED);
					subscriptionDao.update(subscription);
					iterator.remove();
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return subscriptions;
	}

	@Override
	public List<Subscription> findAllSubscriptionByUserId(int userId) throws ServiceException{
		List<Subscription> subscriptions;
		try {
			subscriptions = subscriptionDao.findAllSubscriptionByUserId(userId);
			LocalDate currentDate = LocalDate.now();
			Iterator<Subscription> iterator = subscriptions.iterator();
			while (iterator.hasNext()) {
				Subscription subscription = iterator.next();
				if (SubscriptionStatus.ACTIVE.equals(subscription.getStatus())) {
					LocalDate dateOfExpiration = subscription.getEndLocalDate();
					if (dateOfExpiration.isBefore(currentDate)) {
						subscription.setStatus(SubscriptionStatus.EXPIRED);
						subscriptionDao.update(subscription);
					} else {
						iterator.remove();
					}
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return subscriptions;
	}

	@Override
	public boolean terminateSubscription(int subscriptionId) throws ServiceException{
		try {
			Subscription subscription = subscriptionDao.findSubscriptionById(subscriptionId);
			int monthToExpiration = subscription.getEndLocalDate().getMonthValue() - LocalDate.now().getMonthValue();
			if (monthToExpiration != 0) {
				double sum = calculateSumForRefund(monthToExpiration, subscription.getPublicationId());
				BalanceOperation balanceOperation = new BalanceOperation();
				balanceOperation.setLocalDate(LocalDate.now());
				balanceOperation.setIdUser(subscription.getUserId());
				balanceOperation.setSum(BigDecimal.valueOf(sum));
				balanceOperation.setType(BalanceOperationType.REFUND);
				return subscriptionDao.terminateSubscription(subscription, balanceOperation);
			} else {
				subscription.setStatus(SubscriptionStatus.TERMINATED);
				return subscriptionDao.update(subscription);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public Subscription findSubscriptionById(int id) throws ServiceException {
		try {
			return subscriptionDao.findSubscriptionById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean create(int userId, int publicationId, LocalDate startDate, int duration) throws ServiceException {
		try {
			LocalizedPublicationDTO publication = publicationService.readLocalized(publicationId);
			double subscriptionPrice = publication.getPrice() * duration;
			if (subscriptionPrice > userDao.findUserBalance(userId)) {
				throw new InsufficientFundsInAccountException();
			}
			LocalDate endDate;
			int endDateInt = startDate.getMonthValue()+duration;
			if (endDateInt > 12){
				endDate = LocalDate.of(Year.now().plusYears(1).getValue(),(endDateInt-12),startDate.getDayOfMonth());
			}else {
				endDate =
						LocalDate.of(Year.now().getValue(),
								Month.of(endDateInt), startDate.getDayOfMonth());
			}
			Subscription subscription = new Subscription();
			subscription.setUserId(userId);
			subscription.setPublicationId(publicationId);
			subscription.setPrice(subscriptionPrice);
			subscription.setStartLocalDate(startDate.plusDays(1));
			subscription.setEndLocalDate(endDate.plusDays(1));
			subscription.setPrice(subscriptionPrice);
			subscription.setStatus(SubscriptionStatus.ACTIVE);
			
			BalanceOperation balanceOperation = new BalanceOperation();
			balanceOperation.setLocalDate(LocalDate.now());
			balanceOperation.setIdUser(userId);
			balanceOperation.setType(BalanceOperationType.PAYMENT_OF_SUBSCRIPTION);
			balanceOperation.setSum(BigDecimal.valueOf(subscriptionPrice));
		
			return subscriptionDao.create(subscription, balanceOperation);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private double calculateSumForRefund(int monthToExpiration, int publicationID) throws ServiceException {
		Publication publication = publicationService.findPublicationByIdAndLocale(publicationID, LocaleType.en_US);
		return monthToExpiration * publication.getPrice();
	}

}

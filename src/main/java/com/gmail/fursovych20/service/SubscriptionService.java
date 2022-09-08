package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that provides methods for working with subscriptions at the dao level
 */
public interface SubscriptionService {
	
	List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws ServiceException;
	
	List<Subscription> findAllSubscriptionByUserId(int userId) throws ServiceException;
	
	boolean terminateSubscription(int subscriptionId) throws ServiceException;
	
	Subscription findSubscriptionById(int id) throws ServiceException;
	
	boolean create(int userId, int publicationId, LocalDate startDate, int duration) throws ServiceException;

}

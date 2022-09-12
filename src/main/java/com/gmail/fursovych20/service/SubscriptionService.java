package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that provides methods for working with subscriptions at the dao level
 */
public interface SubscriptionService {

	/**
	 * The method which find active user subscriptions by user id using dao layer
	 *
	 * @param userId param id which using for found active subscriptions
	 * @return {@code List<Subscription>} active
	 * @throws ServiceException throws exception
	 */
	List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws ServiceException;

	/**
	 * The method which find all user subscriptions by user id using dao layer
	 *
	 * @param userId param id which using for found all subscriptions
	 * @return {@code List<Subscription>} all
	 * @throws ServiceException throws exception
	 */
	List<Subscription> findAllSubscriptionByUserId(int userId) throws ServiceException;

	/**
	 * The method which terminate active subscription in user using dao layer
	 *
	 * @param subscriptionId param id which using for terminate all subscriptions
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean terminateSubscription(int subscriptionId) throws ServiceException;

	/**
	 * This method which find subscription by id, using dao layer
	 *
	 * @param id param for searching
	 * @return subscription
	 * @throws ServiceException throws exception
	 */
	Subscription findSubscriptionById(int id) throws ServiceException;

	/**
	 * This method which create subscription by parameters, using dao layer
	 *
	 * @param userId param user id for create
	 * @param publicationId param publication id for create
	 * @param startDate param start date subscription for create
	 * @param duration duration of subscription
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean create(int userId, int publicationId, LocalDate startDate, int duration) throws ServiceException;

}

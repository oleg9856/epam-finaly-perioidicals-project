package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.Subscription;

import java.util.List;

/**
 * An interface that provides methods for edition and other operation
 * for subscription operations
 *
 * @author O.Fursovych
 */
public interface SubscriptionDAO {

	/**
	 * The method that {@code create} subscription in user by
	 * subscription entity
	 *
	 * @param subscription parameter, subscription entity create in {@code command}
	 * @param balanceOperation parameter, which using for create subscription balance
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception in DAO
	 */
	boolean create(Subscription subscription, BalanceOperation balanceOperation) throws DAOException;

	/**
	 * A method that {@code update} subscription using other object subscription
	 *
	 * @param subscription parameter, which is the object to update
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException  throws exception in DAO
	 */
	boolean update(Subscription subscription) throws DAOException;

	/**
	 * A method that {@code read} subscription by {@code subscriptionId}
	 *
	 * @param subscriptionId parameter, which is {@code id} to search in method
	 * @return subscription which found with this parameters
	 * @throws DAOException throws exception in DAO
	 */
	Subscription findSubscriptionById(int subscriptionId) throws DAOException;

	/**
	 * A method that {@code read} subscription by the user ID of the active subscription
	 *
	 * @param userId parameter, which using to find object
	 * @return subscription which found with this parameters
	 * @throws DAOException throws exception
	 */
	List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws DAOException;

	/**
	 * A method that {@code read} all subscription by the user ID
	 *
	 * @param userId parameter, which using to find object
	 * @return subscription which found with this parameters
	 * @throws DAOException throws exception
	 */
	List<Subscription> findAllSubscriptionByUserId(int userId) throws DAOException;

	/**
	 * This method which terminate subscription in specify user and
	 * return part of the balance according to certain rules if this user meets the requirements
	 *
	 * @param subscription parameter, which is the object to using in terminate operation
	 * @param balanceOperation balance operation in this operation
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean terminateSubscription(Subscription subscription, BalanceOperation balanceOperation) throws DAOException;

}

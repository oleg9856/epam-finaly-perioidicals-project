package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.Subscription;

import java.util.List;

public interface SubscriptionDAO {
	
	boolean create(Subscription subscription, BalanceOperation balanceOperation) throws DAOException;
	
	boolean update(Subscription subscription) throws DAOException;
	
	Subscription findSubscriptionById(int subscriptionId) throws DAOException;
	
	List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws DAOException;
	
	List<Subscription> findAllSubscriptionByUserId(int userId) throws DAOException;
	
	boolean terminateSubscription(Subscription subscription, BalanceOperation balanceOperation) throws DAOException;

}

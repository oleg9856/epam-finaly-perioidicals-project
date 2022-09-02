package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;

import java.sql.Connection;
import java.util.List;

/**
 * An interface that provides methods for {@code balanceOperation} and also performs
 * operations on the user's account
 *
 * @author O.Fursovych
 */
public interface BalanceOperationDAO {

	/**
	 * A method that finds a user's balance operations by user ID.
	 *
	 * @param userID {@code userId} parameter that is passed to the constructor
	 * @return List {@code BalanceOperation}
	 * @throws DAOException throws an exception
	 */
	List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws DAOException;

	/**
	 * A method that {@code create} balance operation
	 *
	 * @param balanceOperation that is created by the user and passed to the constructor
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws an exception
	 */
	boolean create(BalanceOperation balanceOperation) throws DAOException;

	/**
	 * A method that create transaction for user using connection for transaction
	 * in layer UserDAO
	 *
	 * @param balanceOperation that is created by the user and passed to the constructor
	 * @param connection parameter that is passed to the constructor
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws an exception
	 */
	boolean createTransaction(BalanceOperation balanceOperation, Connection connection) throws DAOException;

}

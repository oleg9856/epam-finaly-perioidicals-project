package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

/**
 * An interface that provides methods for working with balance at the dao level
 */
public interface BalanceOperationService {

	/**
	 * A method which have access to dao operation for creating balance operation
	 * and using method create
	 *
	 * @param balanceOperation param, object {@code BalanceOperation} using
	 * for create new balance operation
	 * @return true or false dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean create(BalanceOperation balanceOperation) throws ServiceException;

	/**
	 * A method which have access to dao operation for find balance operation
	 * and using method in doa layer for find balance operation
	 *
	 * @param userID param {@code userId} which using for searching balance operation
	 * @return {@code List<BalanceOperation>}
	 * @throws ServiceException throws exception
	 */
	List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws ServiceException;

}

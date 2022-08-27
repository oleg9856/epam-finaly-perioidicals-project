package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

public interface BalanceOperationService {

	boolean create(BalanceOperation balanceOperation) throws ServiceException;

	List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws ServiceException;

}

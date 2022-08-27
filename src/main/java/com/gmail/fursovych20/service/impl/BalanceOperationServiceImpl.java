package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.BalanceOperationDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.service.BalanceOperationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;

import java.util.List;

public class BalanceOperationServiceImpl implements BalanceOperationService {

    private final BalanceOperationDAO balanceOperationDao;

    public BalanceOperationServiceImpl(BalanceOperationDAO balanceOperationDao) {
        this.balanceOperationDao = balanceOperationDao;
    }

    @Override
    public boolean create(BalanceOperation balanceOperation) throws ServiceException {
        if (!Validator.balanceOperationIsValid(balanceOperation)) {
            throw new ValidationException("Balance operation data is not valid");
        }
        try {
            return balanceOperationDao.create(balanceOperation);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws ServiceException {
        try {
            return balanceOperationDao.findBalanceOperationUserByUserId(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}

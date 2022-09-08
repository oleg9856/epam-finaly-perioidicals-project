package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.BalanceOperationDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.BalanceOperationType;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceOperationServiceImplTest {

    @Mock
    private BalanceOperationDAO balanceOperationDAO;

    @InjectMocks
    private BalanceOperationServiceImpl balanceOperationServiceImpl;

    private static final int ID = 1;
    private static final int USER_ID = 1;
    private static final BigDecimal SUM = new BigDecimal("450.6");
    private static final String TYPE = "BALANCE_REPLENISHMENT";
    private static final BalanceOperation BALANCE_OPERATION = getBalanceOperation();

    @Test
    public void testSuccessCreate() throws DAOException, ServiceException {
        when(balanceOperationDAO.create(BALANCE_OPERATION)).thenReturn(true);
        assertTrue(balanceOperationServiceImpl.create(BALANCE_OPERATION));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionCreate() throws DAOException, ServiceException {
        when(balanceOperationDAO.create(BALANCE_OPERATION)).thenThrow(new DAOException());
        balanceOperationServiceImpl.create(BALANCE_OPERATION);
    }

    @Test
    public void testSuccessFindBalanceOperationUserByUserId() throws DAOException, ServiceException {
        List<BalanceOperation> balanceOperations = new ArrayList<>();
        balanceOperations.add(BALANCE_OPERATION);
        when(balanceOperationDAO.findBalanceOperationUserByUserId(ID)).thenReturn(balanceOperations);
        assertEquals(balanceOperations, balanceOperationServiceImpl.findBalanceOperationUserByUserId(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionFindBalanceOperationUserByUserId() throws DAOException, ServiceException {
        when(balanceOperationDAO.findBalanceOperationUserByUserId(ID)).thenThrow(new DAOException());
        balanceOperationServiceImpl.findBalanceOperationUserByUserId(ID);
    }

    private static BalanceOperation getBalanceOperation() {
        return new BalanceOperation.Builder()
                .setId(ID)
                .setIdUser(ID)
                .setSum(SUM)
                .setLocalDate(LocalDate.now())
                .setType(BalanceOperationType.valueOf(TYPE))
                .build();
    }
}
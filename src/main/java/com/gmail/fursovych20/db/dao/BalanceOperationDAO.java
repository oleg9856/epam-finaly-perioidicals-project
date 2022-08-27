package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;

import java.sql.Connection;
import java.util.List;

public interface BalanceOperationDAO {
	
	List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws DAOException;
	
	boolean create(BalanceOperation balanceOperation) throws DAOException;
	
	boolean createTransaction(BalanceOperation balanceOperation, Connection connection) throws DAOException;

}

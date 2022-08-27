package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserDAO {
	
	boolean create(User user) throws DAOException;

	Optional<String> findPasswordByUserData(String loginOrEmail) throws DAOException;

	User findUserByLoginOrEmail(String loginOrEmail) throws DAOException;
	
	User findUserById(int userId) throws DAOException;

	void addToBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException;
	
	void removeFromBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException;
	
	boolean findLoginIfExists(String login) throws DAOException;

	boolean findEmailIfExists(String email) throws DAOException;

	boolean changeRoleByUserId(int id, Role role) throws DAOException;

	List<User> findAllUsers() throws DAOException;

	List<User> findUsersHavingSubscription(int publicationId, LocalDate date) throws DAOException;
	
	double findUserBalance(int userId) throws DAOException;
}

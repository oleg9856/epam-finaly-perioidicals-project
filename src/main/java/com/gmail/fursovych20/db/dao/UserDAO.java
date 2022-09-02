package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * An interface that provides methods for edition and other operation for user
 *
 * @author O.Fursovych
 */
public interface UserDAO {

	/**
	 * A method that {@code create} {@code user} using object type user
	 *
	 * @param user parameter entity which using for
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean create(User user) throws DAOException;

	/**
	 * A method that found password using login or email type string
	 *
	 * @param loginOrEmail parameter login or email for searching password
	 * @return {@code Optional<String>} for processing further layer
	 * @throws DAOException throws exception
	 */
	Optional<String> findPasswordByUserData(String loginOrEmail) throws DAOException;

	/**
	 * A method that found user using login or email type string
	 *
	 * @param loginOrEmail parameter login or email for searching password
	 * @return {@code User} for processing further layer
	 * @throws DAOException throws exception
	 */
	User findUserByLoginOrEmail(String loginOrEmail) throws DAOException;

	/**
	 * This method using for find users by {@code userId}
	 *
	 * @param userId param which using for search in method
	 * @return {@code User} for processing further layer
	 * @throws DAOException throws exception
	 */
	User findUserById(int userId) throws DAOException;

	/**
	 * This method using for add costs to balance user by {@code userId}
	 *
	 * @param userId param which using for search in method
	 * @param sum parameter which is sum to add in balance
	 * @param connection parameter that is passed to the constructor for operation with user
	 * @throws DAOException throws exception
	 */
	void addToBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException;

	/**
	 * This method using for remove costs to balance user by {@code userId}
	 *
	 * @param userId param which using for search in method
	 * @param sum parameter which is sum to remove in balance
	 * @param connection parameter that is passed to the constructor for operation with user
	 * @throws DAOException throws exception
	 */
	void removeFromBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException;

	/**
	 * A method that find user by login if exists using login type string
	 *
	 * @param login parameter, user login
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean findLoginIfExists(String login) throws DAOException;

	/**
	 * A method that find user by email if exists using email type string
	 *
	 * @param email parameter, user email
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean findEmailIfExists(String email) throws DAOException;

	/**
	 * A method that change user role by id
	 *
	 * @param id param {@code userId} which is using for change user role
	 * @param role param for changing user role
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean changeRoleByUserId(int id, Role role) throws DAOException;

	/**
	 * A method which find all users
	 *
	 * @return {@code List<User>}
	 * @throws DAOException throws exception
	 */
	List<User> findAllUsers() throws DAOException;

	/**
	 * This method finds users who have a subscription
	 *
	 * @param publicationId param which using fo search users
	 * @param date param date active subscription between start day and end date subscription
	 * @return {@code List<User>}
	 * @throws DAOException throws exception
	 */
	List<User> findUsersHavingSubscription(int publicationId, LocalDate date) throws DAOException;

	/**
	 * A method which find user balance by {@code userId}
	 *
	 * @param userId param using for searching balance
	 * @return sum of balance
	 * @throws DAOException throws exception
	 */
	double findUserBalance(int userId) throws DAOException;
}

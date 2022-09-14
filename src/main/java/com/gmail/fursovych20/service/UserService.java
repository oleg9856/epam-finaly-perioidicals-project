package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that provides methods for working with users at the dao level
 */
public interface UserService {

	/**
	 * A method that finds the user by login or email<br/>
	 * and password using and using the dao layer
	 *
	 * @param loginOrEmail the parameter used for the search
	 * @param password the parameter used for the search
	 * @return user in the system
	 * @throws ServiceException throws exception
	 */
	User findUserByLoginOrEmailAndPassword(String loginOrEmail, String password) throws ServiceException;

	/**
	 * A method that finds the user by id<br/>
	 * and using the dao layer
	 *
	 * @param userId the identifier of the parameter used for the search
	 * @return user in the system
	 * @throws ServiceException throws exception
	 */
	User findUserByUserID(int userId) throws ServiceException;

	/**
	 * A method that create user using object type user and using dao layer
	 *
	 * @param user parameter entity which using for
	 * @return boolean true, or false dependency of parameters
	 * @throws ServiceException throws exception
	 */
	boolean createUser(User user) throws ServiceException;

	/**
	 * The method that change role by user id using dao layer
	 *
	 * @param id the identifier of the parameter used for the method
	 * @param role param which using for
	 * @return boolean true, or false dependency of parameters
	 * @throws ServiceException throws exception
	 */
	boolean changeRoleByUserId(int id, Role role) throws ServiceException;

	/**
	 * This method finds all users in system using dao layer
	 *
	 * @return {@code List<User>}
	 * @throws ServiceException throws exception
	 */
	List<User> findAllUsers() throws ServiceException;

	/**
	 * The method that searches for users with an existing subscription using the dao layer
	 *
	 * @param publicationId the parameter used for the search
	 * @param date the parameter used for the search
	 * @return {@code List<User>}
	 * @throws ServiceException throws exception
	 */
	List<User> usersHavingSubscription(int publicationId, LocalDate date) throws ServiceException;
	
}

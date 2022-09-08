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
	
	User findUserByLoginOrEmailAndPassword(String loginOrEmail, String password) throws ServiceException;
	
	User findUserByUserID(int userId) throws ServiceException;
	
	boolean createUser(User user) throws ServiceException;

	boolean changeRoleByUserId(int id, Role role) throws ServiceException;

	List<User> findAllUsers() throws ServiceException;
	
	List<User> usersHavingSubscription(int publicationId, LocalDate date) throws ServiceException;
	
}

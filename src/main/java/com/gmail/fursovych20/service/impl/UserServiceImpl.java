package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.service.util.Hashing;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.EmailAlreadyExistsException;
import com.gmail.fursovych20.service.exception.LoginAlreadyExistsException;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

/**
 * UserServiceImpl implementation for manipulating dao layer
 *
 * @author O. Fursovych
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByLoginOrEmailAndPassword(String loginOrEmail, String password) throws ServiceException, IllegalArgumentException {
        if (!Validator.validateStrings(loginOrEmail, password)) {
            throw new ValidationException("Data is not valid");
        }
        try {
            if(Hashing.verifyHash(password, userDao.findPasswordByUserData(loginOrEmail)
                    .orElse("not valid string"))){
                return userDao.findUserByLoginOrEmail(loginOrEmail);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public User findUserByUserID(int userId) throws ServiceException {
        try {
            return userDao.findUserById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createUser(User user) throws ServiceException {
        if (!Validator.userIsValid(user)) {
            throw new ValidationException("User data is not valid");
        }
        try {
            if (userDao.findLoginIfExists(user.getLogin())) {
                throw new LoginAlreadyExistsException("Login already exists");
            }
            if (userDao.findEmailIfExists(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists");
            }
            user.setPassword(hashingPasswordUser(user.getPassword()));
            return userDao.create(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeRoleByUserId(int id, Role role) throws ServiceException {
        try{
            return userDao.changeRoleByUserId(id, role);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return userDao.findAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> usersHavingSubscription(int publicationId, LocalDate localDate) throws ServiceException {
        try {
            return userDao.findUsersHavingSubscription(publicationId, localDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private String hashingPasswordUser(String password) {
        // Mini function to test updates.
        String[] mutableHash = new String[1];
        Function<String, Boolean> update = hash -> {
            mutableHash[0] = hash;
            return true;
        };

        String hashPassword = Hashing.hash(password);
        boolean isHashingEquals = Hashing.verifyAndUpdateHash(password, hashPassword, update);
        if (isHashingEquals) {
            LOG.trace("verifying oldHash: {}, hash upgraded to: {}",
                    true, mutableHash[0]);
            return hashPassword;
        } else {
            return null;
        }
    }

}

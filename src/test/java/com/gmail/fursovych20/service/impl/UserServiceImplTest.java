package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.util.Hashing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    private static final int USER_ID = 1;
    private static final String USER_LOGIN = "userLogin";
    private static final String USER_PASS = "userPass";
    private static final String USER_NAME = "userName";
    private static final String USER_SURNAME = "userSurname";
    private static final String USER_EMAIL = "user@exaple.com";
    private static final BigDecimal USER_BALANCE = new BigDecimal("400.0");
    private static final Role USER_ROLE = Role.CUSTOMER;
    private static final User USER = getUser();
    private static final List<User> USERS = new ArrayList<>(List.of(USER));

    @Test
    public void testSuccessFindUserByLoginOrEmailAndPassword() throws DAOException, ServiceException {
        when(userDAO.findPasswordByUserData(USER_PASS)).thenReturn(Optional.ofNullable(Hashing.hash(USER_PASS)));
        userService.findUserByLoginOrEmailAndPassword(USER_PASS, USER_EMAIL);
    }

    @Test
    public void testSuccessFindUserByUserID() throws DAOException, ServiceException {
        when(userDAO.findUserById(USER_ID)).thenReturn(USER);
        userService.findUserByUserID(USER_ID);
    }

    @Test
    public void testSuccessCreateUser() throws DAOException, ServiceException {
        when(userDAO.create(USER)).thenReturn(true);
        assertTrue(userService.createUser(USER));
    }

    @Test
    public void testSuccessChangeRoleByUserId() throws DAOException, ServiceException {
        when(userDAO.changeRoleByUserId(USER_ID, USER_ROLE)).thenReturn(true);
        assertTrue(userService.changeRoleByUserId(USER_ID, USER_ROLE));
    }

    @Test
    public void testSuccessFindAllUsers() throws DAOException, ServiceException {
        when(userDAO.findAllUsers()).thenReturn(USERS);
        assertEquals(USERS, userService.findAllUsers());
    }

    @Test
    public void testSuccessUsersHavingSubscription() throws DAOException, ServiceException {
        when(userDAO.findUsersHavingSubscription(USER_ID, LocalDate.now())).thenReturn(USERS);
        assertEquals(USERS, userService.usersHavingSubscription(USER_ID, LocalDate.now()));
    }

    private static User getUser() {
        return new User.Builder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASS)
                .setName(USER_NAME)
                .setSetSurName(USER_SURNAME)
                .setEmail(USER_EMAIL)
                .setBalance(USER_BALANCE)
                .setRole(USER_ROLE)
                .build();
    }
}
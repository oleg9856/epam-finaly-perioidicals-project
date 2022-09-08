package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private UserDAOImpl userDAO;

    private static final String TEST_SQL_ONE = "INSERT INTO periodicals_website.users (`login`, `password`, `name`, `surname`, `email`) VALUES (?, ?, ?, ?, ?)";

    private static final int USER_ID = 1;
    private static final String USER_LOGIN = "userLogin";
    private static final String USER_PASS = "userPass";
    private static final String USER_NAME = "userName";
    private static final String USER_SURNAME = "userSurname";
    private static final String USER_EMAIL = "user@exaple.com";
    private static final BigDecimal USER_BALANCE = new BigDecimal("400.0");
    private static final Role USER_ROLE = Role.CUSTOMER;
    private static final User user = getUser();


    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testSuccessCreateNewUser() throws DAOException, SQLException {
        when(connection.prepareStatement(TEST_SQL_ONE
                , Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        assertTrue(userDAO.create(user));
    }

    @Test
    public void testSuccessFindUserById() throws SQLException, DAOException {
        User user = getUser();
       when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
       when(preparedStatement.executeQuery()).thenReturn(resultSet);
       when(resultSet.next()).thenReturn(true);

        setTestUser();

        User userFindById = userDAO.findUserById(USER_ID);

        verify(preparedStatement).setInt(1, USER_ID);
        assertEquals(user, userFindById);
        assertNotNull(userFindById);
    }

    @Test
    public void testFindUserByLoginOrEmail() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        setTestUser();

        User userFindLoginOrEmail = userDAO.findUserByLoginOrEmail(USER_EMAIL);

        assertNotNull(userFindLoginOrEmail);
        assertEquals(user, userFindLoginOrEmail);
    }

    @Test
    public void testFindSuccessPasswordByUserData() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("password")).thenReturn(USER_PASS);

        String userPass = userDAO.findPasswordByUserData(USER_LOGIN).orElse("notPassword");

        assertNotNull(userPass);
        assertEquals(user.getPassword(), userPass);
        verify(preparedStatement).setString(1, USER_LOGIN);
        verify(preparedStatement).setString(2, USER_LOGIN);
    }

    @Test
    public void testFindSuccessLoginIfExists() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertTrue(userDAO.findLoginIfExists(USER_LOGIN));
        verify(preparedStatement).setString(1, USER_LOGIN);
    }

    @Test
    public void testFindSuccessEmailIfExists() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertTrue(userDAO.findEmailIfExists(USER_EMAIL));
        verify(preparedStatement).setString(1, USER_EMAIL);
    }

    @Test
    public void testSuccessChangeRoleByUserId() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);

        assertTrue(userDAO.changeRoleByUserId(USER_ID, Role.BLOCK));
        verify(preparedStatement).setString(1, Role.BLOCK.name());
        verify(preparedStatement).setInt(2, USER_ID);
    }

    @Test
    public void testSuccessFindAllUsers() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setTestUser();
        List<User> userFind = userDAO.findAllUsers();

        assertNotNull(userFind);
        assertEquals(user, userFind.get(0));
    }

    @Test
    public void testSuccessFindUsersHavingSubscription() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setTestUser();
        List<User> userFind = userDAO.findUsersHavingSubscription(1 ,LocalDate.now());

        assertNotNull(userFind);
        assertEquals(user, userFind.get(0));
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setTimestamp(2,
                Timestamp.valueOf(LocalDate.now().atStartOfDay()));
    }

    @Test
    public void testSuccessFindUserBalance() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        userDAO.findUserBalance(USER_ID);
        verify(preparedStatement).setInt(1, USER_ID);
    }

    private void setTestUser() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(USER_ID);
        when(resultSet.getString("login")).thenReturn(USER_LOGIN);
        when(resultSet.getString("password")).thenReturn(USER_PASS);
        when(resultSet.getString("name")).thenReturn(USER_NAME);
        when(resultSet.getString("surname")).thenReturn(USER_SURNAME);
        when(resultSet.getString("email")).thenReturn(USER_EMAIL);
        when(resultSet.getBigDecimal("balance")).thenReturn(USER_BALANCE);
        when(resultSet.getString("role")).thenReturn(USER_ROLE.name());
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
package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.connectionpool.DBManager;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDAO implementation for manipulating of users in the database
 *
 * @author O. Fursovych
 */
public class UserDAOImpl implements UserDAO {

    public static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);
    private final DataSource dataSource;

    private static final String READ_USER_WITH_EMAIL_OR_LOGIN = "SELECT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id WHERE (login=? OR email=?)";
    private static final String READ_USER_PASSWORD_WITH_EMAIL_OR_LOGIN = "SELECT password FROM users WHERE (login=? OR email=?)";
    private static final String READ_USER_WITH_ID = "SELECT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id WHERE users.id=?";
    private static final String READ_ALL_USERS = "SELECT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id";
    private static final String GET_USER_BALANCE = "SELECT balance FROM users WHERE users.id=?";
    private static final String READ_USERS_WITH_SUBSCRIPTION = "SELECT DISTINCT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id JOIN subscriptions ON users.id=subscriptions.id_user WHERE id_publication=? AND ? BETWEEN TIMESTAMP(start_date) AND TIMESTAMP(end_date)";
    private static final String CREATE_USER = "INSERT INTO periodicals_website.users (`login`, `password`, `name`, `surname`, `email`) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ROLE_BY_USER_ID = "UPDATE periodicals_website.users SET id_role = (SELECT roles.id FROM periodicals_website.roles WHERE roles.name = ?) WHERE users.id = ?";
    private static final String ADD_TO_USER_BALANCE = "UPDATE periodicals_website.users SET `balance`=(SELECT balance + ? FROM (SELECT balance FROM users WHERE id=?) res) WHERE `id`=?";
    private static final String REMOVE_FROM_USER_BALANCE = "UPDATE periodicals_website.users SET `balance`=(SELECT balance - ? FROM (SELECT balance FROM users WHERE id=?) res) WHERE `id`=?";
    private static final String FIND_LOGIN = "SELECT id FROM users WHERE login=?";
    private static final String FIND_EMAIL = "SELECT id FROM users WHERE email=?";
    private static final String EXCEPTION_READING_USER = "Exception reading user";

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            ps = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurName());
            ps.setString(5, user.getEmail());
            int result = ps.executeUpdate();
            if (result > 0) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                user.setId(resultSet.getInt(1));
            }
            LOG.info("Create user successfully --> {}", user.getLogin());
            return result != 0;
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot create user --> ", e);
            throw new DAOException("Exception creating user", e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
    }

    @Override
    public Optional<String> findPasswordByUserData(String loginOrEmail) throws DAOException, IllegalArgumentException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_USER_PASSWORD_WITH_EMAIL_OR_LOGIN);
            ps.setString(1, loginOrEmail);
            ps.setString(2, loginOrEmail);
            resultSet = ps.executeQuery();
            if (resultSet.next()){
                LOG.info("Find password successfully by --> {}", loginOrEmail);
                return Optional.of(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot find password by login or email", e);
            throw new DAOException(EXCEPTION_READING_USER,e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return Optional.empty();
    }

    @Override
    public User findUserById(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_USER_WITH_ID);
            ps.setInt(1, userId);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
            LOG.info("User by ID is find --> {}", userId);
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot find user by ID", e);
            throw new DAOException(EXCEPTION_READING_USER, e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return user;
    }

    @Override
    public User findUserByLoginOrEmail(String loginOrEmail) throws DAOException, IllegalArgumentException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        User user = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_USER_WITH_EMAIL_OR_LOGIN);
            ps.setString(1, loginOrEmail);
            ps.setString(2, loginOrEmail);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
            LOG.info("User find by email or login --> {}", loginOrEmail);
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot find user by login or email", e);
            throw new DAOException(EXCEPTION_READING_USER, e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return user;
    }

    @Override
    public void addToBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(ADD_TO_USER_BALANCE)) {
            ps.setBigDecimal(1, sum);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            LOG.info("Transaction add to balance is successfully sum: {}, userID: {}", sum, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("(SQLException) It is not possible to add this amount to the user's balance", e);
            throw new DAOException("Exception adding to user balance", e);
        }
    }

    @Override
    public void removeFromBalanceTransaction(int userId, BigDecimal sum, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(REMOVE_FROM_USER_BALANCE)) {
            ps.setBigDecimal(1, sum);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            LOG.info("Transaction remove from balance is successfully sum: {}, userID: {}", sum, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("(SQLException) It is not possible to remove this amount to the user's balance", e);
            throw new DAOException("Exception removing to user balance", e);
        }
    }

    @Override
    public boolean findLoginIfExists(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(FIND_LOGIN);
            ps.setString(1, login);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                LOG.info("Login is not exists --> {}", login);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("(SQLException) Exception finding login", e);
            throw new DAOException("Exception finding login", e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return false;
    }

    @Override
    public boolean findEmailIfExists(String email) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(FIND_EMAIL);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                LOG.info("Email is not exists --> {}", email);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("(SQLException) Exception finding email", e);
            throw new DAOException("Exception finding email", e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return false;
    }

    @Override
    public boolean changeRoleByUserId(int id, Role role) throws DAOException {
        try(Connection conn = getConnection();
            PreparedStatement preps = conn.prepareStatement(UPDATE_ROLE_BY_USER_ID)){
            preps.setString(1, role.name());
            preps.setInt(2, id);
            LOG.info("Role is changed successfully on role --> {}", role.name());
            return preps.executeUpdate() != 0;
        }catch (SQLException e) {
            LOG.error("(SQLException) Cannot update role!", e);
            throw new DAOException("Exception can`t change role",e);
        }
    }

    @Override
    public List<User> findAllUsers() throws DAOException{
        ArrayList<User> users = new ArrayList<>();
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(READ_ALL_USERS);
            ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()){
                users.add(createUser(resultSet));
            }
            LOG.info("Users is found in size --> {}",users.size());
        } catch (SQLException e) {
            LOG.error("(SQLException) Can`t find all users!",e);
            throw new DAOException("Exception find users!",e);
        }
        return users;
    }

    @Override
    public List<User> findUsersHavingSubscription(int publicationId, LocalDate localDate) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_USERS_WITH_SUBSCRIPTION);
            ps.setInt(1, publicationId);
            ps.setTimestamp(2, Timestamp.valueOf(localDate.atStartOfDay()));

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
            LOG.info("Find users which having subscription");
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot find users which having subscription");
            throw new DAOException(EXCEPTION_READING_USER, e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return users;
    }

    @Override
    public double findUserBalance(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        double balance = 0;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(GET_USER_BALANCE);
            ps.setInt(1, userId);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
                LOG.info("Balance received by userID --> {}", balance);
            }
        } catch (SQLException e) {
            LOG.error("(SQLException) Cannot received balance by userId ", e);
            throw new DAOException(EXCEPTION_READING_USER, e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return balance;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurName(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setBalance(resultSet.getBigDecimal("balance"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        return user;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
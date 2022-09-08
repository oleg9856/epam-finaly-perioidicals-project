package com.gmail.fursovych20.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gmail.fursovych20.db.connectionpool.DBManager;
import com.gmail.fursovych20.db.dao.BalanceOperationDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.SubscriptionDAO;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.entity.SubscriptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

/**
 * IssueOperation implementation for manipulating edition in the database
 *
 * @author O. Fursovych
 */

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private static final Logger LOG = LogManager.getLogger(SubscriptionDAOImpl.class);
    private final DataSource dataSource;
    private final BalanceOperationDAO balanceOperationDao;

    private static final String CREATE_SUBSCRIPTION = "INSERT INTO `periodicals_website`.`subscriptions` (`id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String READ_USER_ACTIVE_SUBSCRIPTIONS = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id_user=? AND status='ACTIVE'";
    private static final String READ_USER_SUBSCRIPTIONS = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id_user=?";
    private static final String UPDATE_SUBSCRIPTION = "UPDATE periodicals_website.subscriptions SET `id_publication`=?, `id_user`=?, `start_date`=?, `end_date`=?, `price`=?, `status`=? WHERE `id`=?";
    private static final String READ_SUBSCRIPTION = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id=?";

    private static final String ID = "id";
    private static final String USER_ID = "id_user";
    private static final String PUBLICATION_ID = "id_publication";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String PRICE = "price";
    private static final String STATUS = "status";

    public SubscriptionDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        balanceOperationDao = new BalanceOperationDAOImpl(dataSource);
    }

    @Override
    public boolean create(Subscription subscription, BalanceOperation balanceOperation) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int resultUpdate;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(CREATE_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, subscription.getPublicationId());
            ps.setInt(2, subscription.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(subscription.getStartLocalDate().atStartOfDay()));
            ps.setTimestamp(4, Timestamp.valueOf(subscription.getEndLocalDate().atStartOfDay()));
            ps.setDouble(5, subscription.getPrice());
            ps.setString(6, subscription.getStatus().name());
            resultUpdate = ps.executeUpdate();

            balanceOperationDao.createTransaction(balanceOperation, connection);

            connection.commit();
        } catch (SQLException | DAOException e) {
            LOG.error("(SQLException) Can`t create sub", e);
            DBManager.rollback(connection);
            throw new DAOException("Exception creating subscription", e);
        } finally {
            DBManager.close(connection, ps);
        }
        return (resultUpdate != 0);
    }

    @Override
    public boolean update(Subscription subscription) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SUBSCRIPTION)
        ) {
            ps.setInt(1, subscription.getPublicationId());
            ps.setInt(2, subscription.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(subscription.getStartLocalDate().atStartOfDay()));
            ps.setTimestamp(4, Timestamp.valueOf(subscription.getEndLocalDate().atStartOfDay()));
            ps.setDouble(5, subscription.getPrice());
            ps.setString(6, subscription.getStatus().name());
            ps.setInt(7, subscription.getId());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error("(SQLException) Can`t update subscription", e);
            throw new DAOException("Exception creating subscription", e);
        }
    }

    @Override
    public List<Subscription> findActiveUserSubscriptionsByUserId(int userId) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(READ_USER_ACTIVE_SUBSCRIPTIONS)) {
                ps.setInt(1, userId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        Subscription subscription = formSubscription(resultSet);
                        subscriptions.add(subscription);
                    }
                    LOG.info("Find active subscription by user id --> {}", userId);
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find active user subscription by user id", e);
            throw new DAOException("Exception reading subscriptions", e);
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> findAllSubscriptionByUserId(int userId) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        try(Connection connection = getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(READ_USER_SUBSCRIPTIONS)) {
                ps.setInt(1, userId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        Subscription subscription = formSubscription(resultSet);
                        subscriptions.add(subscription);
                    }
                    LOG.info("Find all subscription by user id --> {}", userId);
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find all subscription by user id");
            throw new DAOException("Exception reading subscriptions", e);
        }
        return subscriptions;
    }

    @Override
    public Subscription findSubscriptionById(int subscriptionId) throws DAOException {
        Subscription subscription = null;
        try(Connection connection = getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(READ_SUBSCRIPTION)) {
                ps.setInt(1, subscriptionId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        subscription = formSubscription(resultSet);
                    }
                    LOG.info("Find subscription by user id --> {}", subscriptionId);
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find subscription by id", e);
            throw new DAOException("Exception reading subscriptions", e);
        }
        return subscription;
    }

    @Override
    public boolean terminateSubscription(Subscription subscription, BalanceOperation balanceOperation) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int resultUpdate;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(UPDATE_SUBSCRIPTION);

            ps.setInt(1, subscription.getPublicationId());
            ps.setInt(2, subscription.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(subscription.getStartLocalDate().atStartOfDay()));
            ps.setTimestamp(4, Timestamp.valueOf(subscription.getEndLocalDate().atStartOfDay()));
            ps.setDouble(5, subscription.getPrice());
            ps.setString(6, SubscriptionStatus.TERMINATED.name());
            ps.setInt(7, subscription.getId());
            resultUpdate = ps.executeUpdate();

            balanceOperationDao.createTransaction(balanceOperation, connection);

            connection.commit();
        } catch (SQLException | DAOException e) {
            LOG.error("(SQLException)Can`t terminate subscription", e);
            DBManager.rollback(connection);
            throw new DAOException("Exception terminating subscription", e);
        } finally {
            DBManager.close(connection, ps);
        }
        return (resultUpdate != 0);
    }

    private Subscription formSubscription(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(resultSet.getInt(ID));
        subscription.setUserId(resultSet.getInt(USER_ID));
        subscription.setPublicationId(resultSet.getInt(PUBLICATION_ID));
        subscription.setStartLocalDate(resultSet.getTimestamp(START_DATE).toLocalDateTime().toLocalDate());
        subscription.setEndLocalDate(resultSet.getTimestamp(END_DATE).toLocalDateTime().toLocalDate());
        subscription.setPrice(resultSet.getDouble(PRICE));
        subscription.setStatus(SubscriptionStatus.valueOf(resultSet.getString(STATUS)));
        return subscription;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

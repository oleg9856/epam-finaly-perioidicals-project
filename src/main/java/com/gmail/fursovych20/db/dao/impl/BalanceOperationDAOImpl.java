package com.gmail.fursovych20.db.dao.impl;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import com.gmail.fursovych20.db.connectionpool.DBManager;
import com.gmail.fursovych20.db.dao.BalanceOperationDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.BalanceOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

/**
 * BalanceOperationImpl implementation for manipulating costs in the database
 *
 * @author O. Fursovych
 */

public class BalanceOperationDAOImpl implements BalanceOperationDAO {
    private static final Logger LOG = LogManager.getLogger(BalanceOperationDAOImpl.class);
    private final DataSource dataSource;
    private final UserDAO userDao;

    private static final String READ_USER_BALANCE_OPERATIONS = "SELECT id, id_user, date, sum, type FROM balance_operations WHERE id_user=? ORDER BY date DESC";
    private static final String CREATE_BALANCE_OPERATION = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";

    private static final String ID = "id";
    private static final String USER_ID = "id_user";
    private static final String DATE = "date";
    private static final String SUM = "sum";
    private static final String TYPE = "type";

    public BalanceOperationDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        userDao = new UserDAOImpl(dataSource);
    }

    @Override
    public List<BalanceOperation> findBalanceOperationUserByUserId(int userID) throws DAOException {
        List<BalanceOperation> balanceOperations = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(READ_USER_BALANCE_OPERATIONS)
        ) {
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                BalanceOperation balanceOperation = getBalanceOperation(resultSet);
                balanceOperations.add(balanceOperation);
            }
            LOG.info("Find balance operation for user by userId is successfully");
        } catch (SQLException e) {
            LOG.error("Cannot find balance operation for user by userId", e);
            throw new DAOException("Exception reading balance operations", e);
        }
        return balanceOperations;
    }

    @Override
    public boolean create(BalanceOperation balanceOperation) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int resultUpdate;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(CREATE_BALANCE_OPERATION, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);

            ps.setInt(1, balanceOperation.getIdUser());
            ps.setTimestamp(2, Timestamp.valueOf(balanceOperation.getLocalDate().atStartOfDay()));
            ps.setBigDecimal(3, balanceOperation.getSum());
            ps.setString(4, balanceOperation.getType().name());
            resultUpdate = ps.executeUpdate();
            if (balanceOperation.getType() == BalanceOperationType.PAYMENT_OF_SUBSCRIPTION) {
                userDao.removeFromBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
            } else {
                userDao.addToBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
            }
            connection.commit();
            LOG.info("Balance operation created successfully!");
        } catch (SQLException e) {
            LOG.error("Balance transaction error!");
            DBManager.rollback(connection);
            throw new DAOException("Exception creating balance operation", e);
        } finally {
            DBManager.close(connection, ps);
        }
        return resultUpdate != 0;
    }

    @Override
    public boolean createTransaction(BalanceOperation balanceOperation, Connection connection) throws DAOException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int resultUpdate;
        try{
            ps = connection.prepareStatement(CREATE_BALANCE_OPERATION,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, balanceOperation.getIdUser());
            ps.setTimestamp(2, Timestamp.valueOf(balanceOperation.getLocalDate().atStartOfDay()));
            ps.setBigDecimal(3, balanceOperation.getSum());
            ps.setString(4, balanceOperation.getType().name());
            resultUpdate = ps.executeUpdate();
            if (resultUpdate > 0) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                balanceOperation.setId(resultSet.getInt(1));
            }
            if (balanceOperation.getType() == BalanceOperationType.PAYMENT_OF_SUBSCRIPTION) {
                userDao.removeFromBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
            } else {
                userDao.addToBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
            }
            LOG.info("Transaction is created successfully!");
        } catch (SQLException e) {
            LOG.info("Exception creating balance operation transaction", e);
            throw new DAOException("Exception creating balance operation transaction", e);
        }finally {
            DBManager.close(ps, resultSet);
        }
        return resultUpdate != 0;
    }

    private BalanceOperation getBalanceOperation(ResultSet resultSet) throws SQLException {
        BalanceOperation balanceOperation = new BalanceOperation();

        balanceOperation.setId(resultSet.getInt(ID));
        balanceOperation.setIdUser(resultSet.getInt(USER_ID));
        balanceOperation.setLocalDate(resultSet.getDate(DATE).toLocalDate());
        balanceOperation.setSum(resultSet.getBigDecimal(SUM));
        balanceOperation.setType(BalanceOperationType.valueOf(resultSet.getString(TYPE)));

        return balanceOperation;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

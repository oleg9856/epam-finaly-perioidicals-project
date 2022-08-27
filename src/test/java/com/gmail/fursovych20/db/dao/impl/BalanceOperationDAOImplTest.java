package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.BalanceOperationType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceOperationDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private PreparedStatement preparedStatement2;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private BalanceOperationDAOImpl balanceOperationDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";
    private static final String SQL_TEST_TWO = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";

    private static final int ID = 1;
    private static final int USER_ID = 1;
    private static final Date DATE = Date.valueOf(LocalDate.now());
    private static final BigDecimal SUM = new BigDecimal("450.6");
    private static final String TYPE = "BALANCE_REPLENISHMENT";
    private final BalanceOperation balanceOperation = new BalanceOperation(ID, USER_ID, LocalDate.now(), SUM,
            BalanceOperationType.valueOf(TYPE));

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testSuccessFindBalanceOperationUserByUserId() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setBalanceOperation();
        List<BalanceOperation> balanceOperationFind = balanceOperationDAO.findBalanceOperationUserByUserId(ID);


        assertNotNull(balanceOperationFind);
        assertEquals(balanceOperation, balanceOperationFind.get(0));
        verify(preparedStatement1).setInt(1, USER_ID);
    }

    @Test
    public void testSuccessCreate() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);

        assertTrue(balanceOperationDAO.create(balanceOperation));

        verify(preparedStatement1).setInt(1, USER_ID);
        verify(preparedStatement1).setTimestamp(2, Timestamp.valueOf(DATE.toLocalDate().atStartOfDay()));
        verify(preparedStatement1).setBigDecimal(3, SUM);
        verify(preparedStatement1).setString(4, TYPE);
        verify(preparedStatement2).setBigDecimal(1, SUM);
        verify(preparedStatement2).setInt(2, USER_ID);
        verify(preparedStatement2).setInt(3, USER_ID);

    }

    @Test
    public void testSuccessCreateTransaction() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_TWO,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);

        assertTrue(balanceOperationDAO.createTransaction(balanceOperation,connection));

        verify(preparedStatement1).setInt(1, balanceOperation.getIdUser());
        verify(preparedStatement1).setTimestamp(2, Timestamp.valueOf(balanceOperation.getLocalDate().atStartOfDay()));
        verify(preparedStatement1).setBigDecimal(3, balanceOperation.getSum());
        verify(preparedStatement1).setString(4, balanceOperation.getType().name());
        verify(preparedStatement2).setBigDecimal(1, SUM);
        verify(preparedStatement2).setInt(2, USER_ID);
        verify(preparedStatement2).setInt(3, USER_ID);

    }

    private void setBalanceOperation() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getInt("id_user")).thenReturn(USER_ID);
        when(resultSet.getDate("date")).thenReturn(DATE);
        when(resultSet.getBigDecimal("sum")).thenReturn(SUM);
        when(resultSet.getString("type")).thenReturn(TYPE);
    }
}
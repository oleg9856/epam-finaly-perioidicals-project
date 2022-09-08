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
    private static final Date DATE = Date.valueOf(LocalDate.now());
    private static final BigDecimal SUM = new BigDecimal("450.6");
    private static final String TYPE = "BALANCE_REPLENISHMENT";
    private static final BalanceOperation BALANCE_OPERATION = getBalanceOperation();

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
        assertEquals(BALANCE_OPERATION, balanceOperationFind.get(0));
        verify(preparedStatement1).setInt(1, ID);
    }

    @Test
    public void testSuccessCreate() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);

        assertTrue(balanceOperationDAO.create(BALANCE_OPERATION));

        verify(preparedStatement1).setInt(1, ID);
        verify(preparedStatement1).setTimestamp(2, Timestamp.valueOf(DATE.toLocalDate().atStartOfDay()));
        verify(preparedStatement1).setBigDecimal(3, SUM);
        verify(preparedStatement1).setString(4, TYPE);
        verify(preparedStatement2).setBigDecimal(1, SUM);
        verify(preparedStatement2).setInt(2, ID);
        verify(preparedStatement2).setInt(3, ID);

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

        assertTrue(balanceOperationDAO.createTransaction(BALANCE_OPERATION,connection));

        verify(preparedStatement1).setInt(1, BALANCE_OPERATION.getIdUser());
        verify(preparedStatement1).setTimestamp(2, Timestamp.valueOf(BALANCE_OPERATION.getLocalDate().atStartOfDay()));
        verify(preparedStatement1).setBigDecimal(3, BALANCE_OPERATION.getSum());
        verify(preparedStatement1).setString(4, BALANCE_OPERATION.getType().name());
        verify(preparedStatement2).setBigDecimal(1, SUM);
        verify(preparedStatement2).setInt(2, ID);
        verify(preparedStatement2).setInt(3, ID);

    }

    private void setBalanceOperation() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getInt("id_user")).thenReturn(ID);
        when(resultSet.getDate("date")).thenReturn(DATE);
        when(resultSet.getBigDecimal("sum")).thenReturn(SUM);
        when(resultSet.getString("type")).thenReturn(TYPE);
    }

    private static BalanceOperation getBalanceOperation() {
        return new BalanceOperation.Builder()
                .setId(ID)
                .setIdUser(ID)
                .setSum(SUM)
                .setLocalDate(LocalDate.now())
                .setType(BalanceOperationType.valueOf(TYPE))
                .build();
    }
}
package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.BalanceOperationType;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.entity.SubscriptionStatus;
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
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private PreparedStatement preparedStatement2;
    @Mock
    private PreparedStatement preparedStatement3;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private SubscriptionDAOImpl subscriptionDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`subscriptions` (`id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_TEST_TWO = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";
    private static final String SQL_TEST_THIRD = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";


    private static final int ID = 1;
    private static final int USER_ID = 1;
    private static final int PUBLICATION_ID = 1;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = LocalDate.of(Year.now().getValue(), START_DATE.getMonthValue()+2, START_DATE.getDayOfMonth());
    private static final double PRICE = 56.5;
    private static final SubscriptionStatus STATUS = SubscriptionStatus.ACTIVE;
    private static final BigDecimal SUM = new BigDecimal("450.6");
    private static final String TYPE = "BALANCE_REPLENISHMENT";

    private static final Subscription SUBSCRIPTION = getSubscription();

    private static final BalanceOperation BALANCE_OPERATION = getBalanceOperation();

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void create() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(connection.prepareStatement(SQL_TEST_TWO, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement2);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement3);
        when(preparedStatement1.executeUpdate()).thenReturn(5);

        assertTrue(subscriptionDAO.create(SUBSCRIPTION, BALANCE_OPERATION));
        verify(preparedStatement1).setInt(1, SUBSCRIPTION.getPublicationId());
        verify(preparedStatement1).setInt(2, SUBSCRIPTION.getUserId());
        verify(preparedStatement1).setTimestamp(3, Timestamp.valueOf(SUBSCRIPTION.getStartLocalDate().atStartOfDay()));
        verify(preparedStatement1).setTimestamp(4, Timestamp.valueOf(SUBSCRIPTION.getEndLocalDate().atStartOfDay()));
        verify(preparedStatement1).setDouble(5, SUBSCRIPTION.getPrice());
        verify(preparedStatement1).setString(6, SUBSCRIPTION.getStatus().name());
    }

    @Test
    public void update() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);

        assertTrue(subscriptionDAO.update(SUBSCRIPTION));
        verify(preparedStatement1).setInt(1, SUBSCRIPTION.getPublicationId());
        verify(preparedStatement1).setInt(2, SUBSCRIPTION.getUserId());
        verify(preparedStatement1).setTimestamp(3, Timestamp.valueOf(SUBSCRIPTION.getStartLocalDate().atStartOfDay()));
        verify(preparedStatement1).setTimestamp(4, Timestamp.valueOf(SUBSCRIPTION.getEndLocalDate().atStartOfDay()));
        verify(preparedStatement1).setDouble(5, SUBSCRIPTION.getPrice());
        verify(preparedStatement1).setString(6, SUBSCRIPTION.getStatus().name());
        verify(preparedStatement1).setInt(7, SUBSCRIPTION.getId());


    }

    @Test
    public void findActiveUserSubscriptionsByUserId() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setSubscription();
        List<Subscription> subscriptionFind = subscriptionDAO.findActiveUserSubscriptionsByUserId(ID);

        assertNotNull(subscriptionFind);
        assertEquals(SUBSCRIPTION, subscriptionFind.get(0));
        verify(preparedStatement1).setInt(1, USER_ID);
    }

    @Test
    public void findAllSubscriptionByUserId() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setSubscription();
        List<Subscription> subscriptionFind = subscriptionDAO.findAllSubscriptionByUserId(ID);

        assertNotNull(subscriptionFind);
        assertEquals(SUBSCRIPTION, subscriptionFind.get(0));
        verify(preparedStatement1).setInt(1, USER_ID);
    }

    @Test
    public void findSubscriptionById() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setSubscription();
        Subscription subscriptionFind = subscriptionDAO.findSubscriptionById(ID);

        assertNotNull(subscriptionFind);
        assertEquals(SUBSCRIPTION, subscriptionFind);
        verify(preparedStatement1).setInt(1, ID);
    }

    @Test
    public void terminateSubscription() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(connection.prepareStatement(SQL_TEST_THIRD,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement2);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(preparedStatement2.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement2.executeUpdate()).thenReturn(5);


        assertTrue(subscriptionDAO.terminateSubscription(SUBSCRIPTION, BALANCE_OPERATION));
    }

    private void setSubscription() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getInt("id_user")).thenReturn(USER_ID);
        when(resultSet.getInt("id_publication")).thenReturn(PUBLICATION_ID);
        when(resultSet.getTimestamp("start_date")).thenReturn(Timestamp.valueOf(START_DATE.atStartOfDay()));
        when(resultSet.getTimestamp("end_date")).thenReturn(Timestamp.valueOf(END_DATE.atStartOfDay()));
        when(resultSet.getDouble("price")).thenReturn(PRICE);
        when(resultSet.getString("status")).thenReturn(STATUS.name());
    }

    private static Subscription getSubscription() {
        return new Subscription.Builder()
                .setId(ID)
                .setUserId(USER_ID)
                .setStatus(STATUS)
                .setPublicationId(PUBLICATION_ID)
                .setStartLocalDate(START_DATE)
                .setEndLocalDate(END_DATE)
                .setPrice(PRICE)
                .build();
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
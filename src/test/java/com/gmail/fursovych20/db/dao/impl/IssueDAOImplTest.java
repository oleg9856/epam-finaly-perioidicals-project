package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueDAOImplTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private IssueDAOImpl issueDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`issues` (`date_of_publication`, `publication_id`, `file`, `description`) VALUES (?, ?, ?, ?)";

    private static final int ID = 1;
    private static final Date DATE_OF_PUBLICATION = Date.valueOf(LocalDate.now());
    private static final int PUBLICATION_ID = 1;
    private static final String DESCRIPTION = "description";
    private static final String FILE = "test.txt";
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = LocalDate.of(2022, 10, START_DATE.getDayOfMonth());

    private final Issue issue = getIssue();

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testSuccessCreate() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE
                , Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertTrue(issueDAO.create(issue));
        verify(preparedStatement).setTimestamp(1, Timestamp.valueOf(issue.getLocalDateOfPublication().atStartOfDay()));
        verify(preparedStatement).setInt(2, issue.getPublicationId());
        verify(preparedStatement).setString(3, issue.getFile());
        verify(preparedStatement).setString(4, issue.getDescription());
    }

    @Test
    public void testSuccessFindIssueByID() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setIssue();
        Issue issueFind = issueDAO.findIssueByID(ID);

        assertNotNull(issueFind);
        assertEquals(issue, issueFind);
        verify(preparedStatement).setInt(1, ID);
    }

    @Test
    public void findPublicationBetweenDates() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setIssue();
        List<Issue> issueFind = issueDAO.findPublicationBetweenDates(PUBLICATION_ID, START_DATE, END_DATE);

        assertNotNull(issueFind);
        assertEquals(issue, issueFind.get(0));
        verify(preparedStatement).setInt(1, PUBLICATION_ID);
        verify(preparedStatement).setTimestamp(2, Timestamp.valueOf(START_DATE.atStartOfDay()));
        verify(preparedStatement).setTimestamp(3, Timestamp.valueOf(END_DATE.atStartOfDay()));
    }

    private void setIssue() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getDate("date_of_publication")).thenReturn(DATE_OF_PUBLICATION);
        when(resultSet.getInt("publication_id")).thenReturn(PUBLICATION_ID);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);
        when(resultSet.getString("file")).thenReturn(FILE);
    }

    private Issue getIssue() {
        return new Issue.Builder()
                .setId(ID)
                .setPublicationId(ID)
                .setDescription(DESCRIPTION)
                .setLocalDateOfPublication(DATE_OF_PUBLICATION.toLocalDate())
                .setFile(FILE)
                .build();
    }
}
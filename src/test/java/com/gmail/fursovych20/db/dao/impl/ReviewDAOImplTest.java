package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private ReviewDAOImpl reviewDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`reviews` (`id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (?, ?, ?, ?, ?);";

    private static final int ID = 1;
    private static final int USER_ID = 1;
    private static final int PUBLICATION_ID = 1;
    private static final LocalDate DATE_OF_PUBLICATION = LocalDate.now();
    private static final String TEXT = "SOME TEXT";
    private static final byte MARK = 3;

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void findReviewByPublicationId() throws SQLException, DAOException {
        Review review = new Review(ID, USER_ID, PUBLICATION_ID, DATE_OF_PUBLICATION, TEXT, MARK);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setReview();

        List<Review> reviewFind = reviewDAO.findReviewByPublicationId(PUBLICATION_ID);

        assertNotNull(reviewFind);
        assertEquals(review, reviewFind.get(0));
        verify(preparedStatement).setInt(1, ID);
    }

    @Test
    public void create() throws SQLException, DAOException {
        Review REVIEW = new Review(ID, USER_ID, PUBLICATION_ID, DATE_OF_PUBLICATION, TEXT, MARK);
        when(connection.prepareStatement(SQL_TEST_ONE,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        assertTrue(reviewDAO.create(REVIEW));
        verify(preparedStatement).setInt(1, REVIEW.getUserId());
        verify(preparedStatement).setInt(2, REVIEW.getPublicationId());
        verify(preparedStatement).setTimestamp(3, Timestamp.valueOf(REVIEW.getDateOfPublication().atStartOfDay()));
        verify(preparedStatement).setString(4, REVIEW.getText());
        verify(preparedStatement).setByte(5, REVIEW.getMark());
    }

    @Test
    public void update() throws SQLException, DAOException {
        Review review = new Review(ID, USER_ID, PUBLICATION_ID, DATE_OF_PUBLICATION, TEXT, MARK);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);

        assertTrue(reviewDAO.update(review));
        verify(preparedStatement).setString(1, review.getText());
        verify(preparedStatement).setByte(2, review.getMark());
        verify(preparedStatement).setInt(3, review.getId());
    }

    @Test
    public void delete() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(5);

        assertTrue(reviewDAO.delete(ID));
        verify(preparedStatement).setInt(1, ID);
    }

    @Test
    public void findReviewById() throws SQLException, DAOException {
        Review review = new Review(ID, USER_ID, PUBLICATION_ID, DATE_OF_PUBLICATION, TEXT, MARK);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setReview();
        Review reviewFind = reviewDAO.findReviewById(ID);

        assertNotNull(reviewFind);
        assertEquals(review, reviewFind);
        verify(preparedStatement).setInt(1, ID);
    }

    private void setReview() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getInt("id_user")).thenReturn(USER_ID);
        when(resultSet.getInt("id_publication")).thenReturn(PUBLICATION_ID);
        when(resultSet.getTimestamp("date_of_publication")).thenReturn(Timestamp.valueOf(DATE_OF_PUBLICATION.atStartOfDay()));
        when(resultSet.getString("text")).thenReturn(TEXT);
        when(resultSet.getByte("mark")).thenReturn(MARK);
    }
}
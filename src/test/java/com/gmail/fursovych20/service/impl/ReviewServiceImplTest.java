package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.ReviewDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private static final int ID = 1;
    private static final LocalDate DATE_OF_PUBLICATION = LocalDate.now();
    private static final String TEXT = "SOME TEXT";
    private static final byte MARK = 3;

    private static final Review REVIEW = getReview();
    private static final List<Review> REVIEWS = new ArrayList<>(List.of(REVIEW));

    @Test
    public void testSuccessFindReviewByPublicationId() throws DAOException, ServiceException {
        when(reviewDAO.findReviewByPublicationId(ID))
                .thenReturn(REVIEWS);
        assertEquals(REVIEWS, reviewService.findReviewByPublicationId(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionFindReviewByPublicationId() throws DAOException, ServiceException {
        when(reviewDAO.findReviewByPublicationId(ID)).thenThrow(new DAOException());
        reviewService.findReviewByPublicationId(ID);
    }

    @Test
    public void testSuccessAddReview() throws DAOException, ServiceException {
        when(reviewDAO.create(REVIEW)).thenReturn(true);
        assertTrue(reviewService.addReview(REVIEW));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionAddReview() throws DAOException, ServiceException {
        when(reviewDAO.create(REVIEW)).thenThrow(new DAOException());
        reviewService.addReview(REVIEW);
    }

    @Test
    public void testSuccessUpdate() throws ServiceException, DAOException {
        when(reviewDAO.update(REVIEW)).thenReturn(true);
        assertTrue(reviewService.update(REVIEW));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionUpdate() throws DAOException, ServiceException {
        when(reviewDAO.update(REVIEW)).thenThrow(new DAOException());
        reviewService.update(REVIEW);
    }

    @Test
    public void testSuccessDelete() throws ServiceException, DAOException {
        when(reviewDAO.delete(ID)).thenReturn(true);
        assertTrue(reviewService.delete(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionDelete() throws DAOException, ServiceException {
        when(reviewDAO.delete(ID)).thenThrow(new DAOException());
        reviewService.delete(ID);
    }


    @Test
    public void findReviewById() throws DAOException, ServiceException {
        when(reviewDAO.findReviewById(ID)).thenReturn(REVIEW);
        assertEquals(REVIEW, reviewService.findReviewById(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionFindReviewById() throws DAOException, ServiceException {
        when(reviewDAO.findReviewById(ID)).thenThrow(new DAOException());
        reviewService.findReviewById(ID);
    }

    private static Review getReview() {
        return new Review.Builder()
                .setId(ID)
                .setUserId(ID)
                .setPublicationId(ID)
                .setDateOfPublication(DATE_OF_PUBLICATION)
                .setMark(MARK)
                .setText(TEXT)
                .build();
    }
}
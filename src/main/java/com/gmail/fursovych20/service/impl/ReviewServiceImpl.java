package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.ReviewDAO;
import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.ReviewService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;

import java.util.List;

/**
 * ReviewServiceImpl implementation for manipulating dao layer
 *
 * @author O. Fursovych
 */
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDao;

    public ReviewServiceImpl(ReviewDAO reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> findReviewByPublicationId(int publicationId) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = reviewDao.findReviewByPublicationId(publicationId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviews;
    }

    @Override
    public boolean addReview(Review review) throws ServiceException {
        if (!Validator.reviewIsValid(review)) {
            throw new ValidationException("Review data is not valid");
        }
        try {
            return reviewDao.create(review);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Review review) throws ServiceException {
        if (!Validator.reviewIsValid(review)) {
            throw new ValidationException("Review data is not valid");
        }
        try {
            return reviewDao.update(review);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int reviewId) throws ServiceException {
        try {
            return reviewDao.delete(reviewId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Review findReviewById(int reviewId) throws ServiceException {
        try {
            return reviewDao.findReviewById(reviewId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}

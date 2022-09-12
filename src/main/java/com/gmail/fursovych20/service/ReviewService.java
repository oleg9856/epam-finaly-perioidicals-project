package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

/**
 * An interface that provides methods for working with reviews at the dao level
 */
public interface ReviewService {

	/**
	 * This method find review by publication id
	 *
	 * @param publicationId param publication id
	 * @return review you were looking for
	 * @throws ServiceException throws exception
	 */
	List<Review> findReviewByPublicationId(int publicationId) throws ServiceException;

	/**
	 * The method which add review
	 *
	 * @param review param, which will be added
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean addReview(Review review) throws ServiceException;

	/**
	 * The method update review
	 *
	 * @param review param, which is updated
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean update(Review review) throws ServiceException;

	/**
	 * @param reviewId param, which using for delete publication
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean delete(int reviewId) throws ServiceException;

	/**
	 * A method which have access to dao operation for find review by id
	 * and using method find in dao layer
	 *
	 * @param reviewId param, which using for find publication
	 * @return review you were looking for
	 * @throws ServiceException throws exception
	 */
	Review findReviewById(int reviewId)  throws ServiceException;
}

package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;

import java.util.List;

/**
 * An interface that provides methods for edition and other operation for {@code review}
 *
 * @author O.Fursovych
 */
public interface ReviewDAO {

	/**
	 * The method that {@code review} by {@code publicationId}
	 *
	 * @param publicationId {@code publication id}
	 * @return list of review
	 * @throws DAOException throws an exception
	 */
	List<Review> findReviewByPublicationId(int publicationId) throws DAOException;

	/**
	 * The method that {@code create} review by {@code publicationId}
	 *
	 * @param review parameter which provide data for create
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws an exception
	 */
	boolean create(Review review) throws DAOException;

	/**
	 * The method to {@code update} an existing {@code review} by {@code review entity}
	 *
	 * @param review parameter which provide data for create
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws an exception
	 */
	boolean update(Review review) throws DAOException;

	/**
	 * The method to {@code delete} an existing {@code review} by {@code id}
	 *
	 * @param id {@code reviewId} for delete
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws an exception
	 */
	boolean delete(int id) throws DAOException;

	/**
	 * The method to {@code read} an existing {@code review} by {@code id}
	 *
	 * @param id {@code reviewId} for read
	 * @return {@code review} which found
	 * @throws DAOException throws an exception
	 */
	Review findReviewById(int id) throws DAOException;

}

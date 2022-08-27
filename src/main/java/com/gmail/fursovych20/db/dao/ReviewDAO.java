package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;

import java.util.List;

public interface ReviewDAO {
	
	List<Review> findReviewByPublicationId(int publicationId) throws DAOException;
	
	boolean create(Review review) throws DAOException;
	
	boolean update(Review review) throws DAOException;
	
	boolean delete(int id) throws DAOException;
	
	Review findReviewById(int id) throws DAOException;

}

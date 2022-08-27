package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

public interface ReviewService {
	
	List<Review> findReviewByPublicationId(int publicationId) throws ServiceException;
	
	boolean addReview(Review review) throws ServiceException;
	
	boolean update(Review review) throws ServiceException;
	
	boolean delete(int reviewId) throws ServiceException;
	
	Review findReviewById(int reviewId)  throws ServiceException;
}

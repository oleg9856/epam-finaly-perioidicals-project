package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

/**
 * An interface that provides methods for working with issues at the dao level
 */
public interface IssueService {

	/**
	 * A method that selects issues in the database between the dates specified in the subscription
	 *
	 * @param subscription param which using for searching object Issue
	 * @return {@code List<Issue>}
	 * @throws ServiceException  throws exception
	 */
	List<Issue> findPublicationBetweenDates(Subscription subscription) throws ServiceException;

	/**
	 * A method that searches for issues by id using the dao layer
	 *
	 * @param id param {@code id} using for find issue by id
	 * @return {@code Issue}
	 * @throws ServiceException throws exception
	 */
	Issue findIssueById(int id)  throws ServiceException;

	/**
	 * A method that creates a publication using the dao layer
	 *
	 * @param issue object for creating in database
	 * @param locale which using for searching by locale
	 * @return true or false dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean create(Issue issue, LocaleType locale) throws ServiceException;
}

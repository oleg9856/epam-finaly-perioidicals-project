package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Issue;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that provides methods for edition and other operation for issue
 *
 * @author O.Fursovych
 */
public interface IssueDAO {

	/**
	 * A method that create the issue using
	 * exits issue.
	 *
	 * @param issue parameter that is passed to the constructor
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws an exception
	 */
	boolean create(Issue issue) throws DAOException;

	/**
	 * A method that find an issue by ID.
	 *
	 * @param id issue id parameter that is passed to the constructor
	 * @return issue which found
	 * @throws DAOException throws an exception
	 */
	Issue findIssueByID(int id) throws DAOException;

	/**
	 * A method that finds a post between dates by according to external data
	 *
	 * @param publicationId parameter that is passed to the constructor
	 * @param startDate parameter in constructor start date
	 * @param endDate parameter in constructor end date
	 * @return List Issue
	 * @throws DAOException throws an exception
	 */
	List<Issue> findPublicationBetweenDates(int publicationId, LocalDate startDate, LocalDate endDate) throws DAOException;
}

package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Issue;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that provides methods for edition and other operation for {@code Issue}
 *
 * @author O.Fursovych
 */
public interface IssueDAO {

	/**
	 * The method that {@code create} the issue using
	 * exits issue.
	 *
	 * @param issue parameter, that is passed to the constructor
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws an exception
	 */
	boolean create(Issue issue) throws DAOException;

	/**
	 * The method that find an {@code issue} by {@code Id}.
	 *
	 * @param id {@code issueId} parameter that is passed to the constructor
	 * @return {@code issue} which found
	 * @throws DAOException throws an exception
	 */
	Issue findIssueByID(int id) throws DAOException;

	/**
	 * The method that finds a post between dates by according to external data
	 *
	 * @param publicationId parameter that is passed to the constructor
	 * @param startDate parameter in constructor start date
	 * @param endDate parameter in constructor end date
	 * @return List {@code Issue}
	 * @throws DAOException throws an exception
	 */
	List<Issue> findPublicationBetweenDates(int publicationId, LocalDate startDate, LocalDate endDate) throws DAOException;
}

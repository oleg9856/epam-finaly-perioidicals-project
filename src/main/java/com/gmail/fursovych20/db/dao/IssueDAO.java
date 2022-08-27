package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Issue;

import java.time.LocalDate;
import java.util.List;

public interface IssueDAO {

	boolean create(Issue issue) throws DAOException;
	
	Issue findIssueByID(int id) throws DAOException;
	
	List<Issue> findPublicationBetweenDates(int publicationId, LocalDate startDate, LocalDate endDate) throws DAOException;
}

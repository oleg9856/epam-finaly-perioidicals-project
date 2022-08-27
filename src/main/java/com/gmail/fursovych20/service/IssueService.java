package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

public interface IssueService {

	List<Issue> findPublicationBetweenDates(Subscription subscription) throws ServiceException;
	
	Issue findIssueById(int id)  throws ServiceException;
	
	boolean create(Issue issue) throws ServiceException;
}

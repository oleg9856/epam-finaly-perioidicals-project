package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.IssueDAO;
import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.service.IssueService;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.util.MailSender;

import java.util.List;

public class IssueServiceImpl implements IssueService {
	
	private final IssueDAO issueDao;
	private final UserService userService;
	private final PublicationService publicationService;

	public IssueServiceImpl(IssueDAO issueDao, UserService userService, PublicationService publicationService) {
		this.issueDao = issueDao;
		this.userService = userService;
		this.publicationService = publicationService;
	}

	@Override
	public List<Issue> findPublicationBetweenDates(Subscription subscription) throws ServiceException {
		try {			
			return issueDao.findPublicationBetweenDates(subscription.getPublicationId(), subscription.getStartLocalDate(), subscription.getEndLocalDate());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Issue findIssueById(int id) throws ServiceException {
		try {
			return issueDao.findIssueByID(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean create(Issue issue) throws ServiceException {
		boolean isCreated;
		try {
			isCreated = issueDao.create(issue);
			
			List<User> users = userService.usersHavingSubscription(issue.getPublicationId(), issue.getLocalDateOfPublication());
			LocalizedPublicationDTO localizedPublicationDTO = publicationService.readLocalized(issue.getPublicationId());

			MailSender.sendNotifications(users, issue, localizedPublicationDTO);
			return isCreated;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}

package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.IssueService;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.SubscriptionService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements show issue balance command
 */
public class ShowIssuesCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(ShowIssuesCommand.class);
	
	private final SubscriptionService subscriptionService;
	private final IssueService issueService;
	private final PublicationService publicationService;

	public ShowIssuesCommand(SubscriptionService subscriptionService, IssueService issueService, PublicationService publicationService) {
		this.subscriptionService = subscriptionService;
		this.issueService = issueService;
		this.publicationService = publicationService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		try {
			LOG.debug("ShowIssuesCommand starts");
			LocaleType locale = HttpUtil.getLocale(request);
			LOG.trace("Locale --> {}", locale.name());
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			LOG.trace("User ID --> {}", userId);
			int subscriptionId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID));
			LOG.trace("Subscription ID --> {}", subscriptionId);
			Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
			Publication publication = publicationService.findPublicationByIdAndLocale(subscription.getPublicationId(), locale);
			
			List<Issue> issues = Collections.emptyList();
			if (subscription.getUserId() == userId) {
				issues = issueService.findPublicationBetweenDates(subscription);
			}
			
			request.setAttribute(REQUEST_ATTR_ISSUES, issues);
			request.setAttribute(REQUEST_ATTR_PUBLICATION, publication);
			LOG.debug("ShowIssuesCommand finish success!");
			return SEND_TO_FORWARD+ VIEW_AVAILABLE_ISSUES;
		} catch (ServiceException e) {
			LOG.error("Exception showing issues", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}

}

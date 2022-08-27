package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.service.IssueService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class ShowIssueFileCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(ShowIssueFileCommand.class);

	private final IssueService issueService;

	public ShowIssueFileCommand(IssueService issueService) {
		this.issueService = issueService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		try {
			LOG.debug("ShowIssueCommand starts");
			int issueId = Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID));
			LOG.trace("Issue ID --> {}",issueId);
			Issue issue = issueService.findIssueById(issueId);
			
			HttpUtil.writeIssueIntoResponse(issue, response, request);
			LOG.debug("ShowIssueCommand finish successfully!");
			return TO_DO_NOTHING;
		} catch (ServiceException | IOException e) {
			LOG.error("Exception showing issue file", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}

}

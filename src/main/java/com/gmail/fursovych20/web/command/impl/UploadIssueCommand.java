package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.service.IssueService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class UploadIssueCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(UploadIssueCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "issue.fail";
	private static final String SUCCESS_MESSAGE_KEY = "add_issue.success";
	
	private final IssueService issueService;

	public UploadIssueCommand(IssueService issueService) {
		this.issueService = issueService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		LocaleType locale = HttpUtil.getLocale(request);
		try {			
			Issue issue = getIssue(request);
			issueService.create(issue);

			String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
			return SEND_TO_REDIRECT+path;
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
			return SEND_TO_REDIRECT+path;
		} catch (ServiceException | IOException | ServletException e) {
			LOG.error("Exception uploading issue", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}

	private Issue getIssue(HttpServletRequest request) throws IOException, ServletException {
		Issue issue = new Issue();
		issue.setPublicationId(Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION)));
		issue.setDescription(request.getParameter(REQUEST_PARAM_ISSUE_DESCRIPTION));

		LocalDate localDate = LocalDate.now();
		LOG.debug("LocalDate with Pattern --> {}", localDate);
		issue.setLocalDateOfPublication(localDate.plusDays(1));

		HttpUtil.uploadIssueFile(issue, request);
		
		return issue;
	}
}

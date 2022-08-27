package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class ShowResultPageCommand implements Command{

	private static final Logger LOG = LogManager.getLogger(ShowResultPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		LOG.debug("ShowResultPageCommand starts");
		String message = request.getParameter(REQUEST_PARAM_MESSAGE);
		LOG.trace("Message --> {}",message);
		String returnPage = request.getParameter(REQUEST_PARAM_RETURN_PAGE);
		LOG.trace("Return page --> {}",returnPage);

		request.setAttribute(REQUEST_ATTR_MESSAGE, message);
		request.setAttribute(REQUEST_ATTR_RETURN_PAGE, returnPage);
		LOG.debug("ShowResultPageCommand finish successfully!");
		return SEND_TO_FORWARD+VIEW_RESULT_PAGE;
	}

}

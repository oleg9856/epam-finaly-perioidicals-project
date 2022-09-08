package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements user profile command
 */
public class UserProfileCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(UserProfileCommand.class);
	
	private final UserService userService;

	public UserProfileCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		try {
			LOG.debug("UserProfileCommand starts");
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			LOG.trace("User ID --> {}",userId);
			request.setAttribute(REQUEST_ATTR_USER, userService.findUserByUserID(userId));
			LOG.debug("UserProfileCommand finish success!");
			return SEND_TO_FORWARD+VIEW_USER_PROFILE;
		} catch (ServiceException e) {
			LOG.error("Exception showing profile", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}

}

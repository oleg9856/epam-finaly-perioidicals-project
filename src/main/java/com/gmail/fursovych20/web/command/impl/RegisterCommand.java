package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.EmailAlreadyExistsException;
import com.gmail.fursovych20.service.exception.LoginAlreadyExistsException;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.VerifyRecaptcha;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements register command
 */
public class RegisterCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
	
	private static final String LOGIN_EXISTS_MESSAGE = "register.login_exists";
	private static final String EMAIL_EXISTS_MESSAGE = "register.email_exists";
	private static final String INVALID_DATA = "register.invalid_data";
	
	private final UserService userService;

	public RegisterCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		LOG.debug("RegisterCommand starts");
		String referPage = HttpUtil.getReferPage(request);
		LOG.trace("Refer page --> {}",referPage);
		if (!referPage.contains(COMMAND_LOGIN) && !referPage.contains(COMMAND_REGISTER)){
			request.getSession().setAttribute(SESSION_ATTR_REFER_PAGE, referPage);
		} 
		if (referPage.contains(COMMAND_REGISTER)) {
			LocaleType locale = HttpUtil.getLocale(request);
			try {

				if(!VerifyRecaptcha.verify(request.getParameter(REQUEST_PARAM_RECAPTCHA_NAME))){
					throw new ValidationException();
				}
				User user = getUser(request);
				userService.createUser(user);
				String path = (String) request.getSession().getAttribute(SESSION_ATTR_REFER_PAGE);
				path = (path != null) ? path : HttpUtil.formRedirectUrl(request, COMMAND_HOME);
				LOG.trace("Path to controller --> {}", path);
				LOG.debug("RegisterCommand finish successfully!");
				return SEND_TO_REDIRECT+path;
				
			} catch (ValidationException e) {
				LOG.warn("Validation failed",e);
				String message = MessageResolver.getMessage(INVALID_DATA, locale);
				request.setAttribute(FAIL_MESSAGE_REGISTER, message);
				return SEND_TO_FORWARD+VIEW_REGISTER;
			} catch (LoginAlreadyExistsException e) {
				LOG.warn("Login already exists!",e);
				String message = MessageResolver.getMessage(LOGIN_EXISTS_MESSAGE, locale);
				request.setAttribute(FAIL_MESSAGE_REGISTER, message);
				return SEND_TO_FORWARD+VIEW_REGISTER;
				
			} catch (EmailAlreadyExistsException e) {
				LOG.warn("Email already exists!",e);
				String message = MessageResolver.getMessage(EMAIL_EXISTS_MESSAGE, locale);
				request.setAttribute(FAIL_MESSAGE_REGISTER, message);
				return SEND_TO_FORWARD+VIEW_REGISTER;
			}
			
			catch (ServiceException e) {
				LOG.error("Exception registering user", e);
				return SEND_TO_FORWARD+VIEW_ERROR;
			}	
		} else {
			LOG.trace("RegisterCommand final failed!");
			return SEND_TO_FORWARD+VIEW_REGISTER;
		}
	}
	
	private User getUser(HttpServletRequest request) {
		User user = new User();
		user.setLogin(request.getParameter(REQUEST_PARAM_LOGIN));
		user.setPassword(request.getParameter(REQUEST_PARAM_PASSWORD));
		user.setName(request.getParameter(REQUEST_PARAM_NAME));
		user.setSurName(request.getParameter(REQUEST_PARAM_SURNAME));
		user.setEmail(request.getParameter(REQUEST_PARAM_EMAIL));
		return user;
	}
	
}

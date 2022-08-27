package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class LoginCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    private static final String INVALID_DATA = "login.invalid_data";

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)  throws CommandExeption {
        LOG.debug("LoginCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        try {
            String referPage = HttpUtil.getReferPage(request);
            if (!referPage.contains(COMMAND_LOGIN) && !referPage.contains(COMMAND_REGISTER)){
                request.getSession().setAttribute(SESSION_ATTR_REFER_PAGE, referPage);
            }
            if (referPage.contains(COMMAND_LOGIN)) {
                String loginOrEmail = request.getParameter(REQUEST_PARAM_LOGIN_OR_EMAIL);
                String password = request.getParameter(REQUEST_PARAM_PASSWORD);

                User user = userService.findUserByLoginOrEmailAndPassword(loginOrEmail, password);
                if (user == null) {
                    LOG.trace("User is not find is system");
                    String message = MessageResolver.getMessage(INVALID_DATA, locale);
                    request.setAttribute(FAIL_MESSAGE_LOGIN, message);
                    return SEND_TO_FORWARD+VIEW_LOGIN;
                } else {
                    LOG.trace("User find is system successfully --> {}", user.getLogin());
                    HttpSession session = request.getSession();
                    session.setAttribute(SESSION_ATTR_USER_ID, user.getId());
                    session.setAttribute(SESSION_ATTR_USER_NAME, user.getName());
                    session.setAttribute(SESSION_ATTR_USER_ROLE, user.getRole());

                    String path = (String) request.getSession().getAttribute(SESSION_ATTR_REFER_PAGE);
                    path = (path != null) ? path : HttpUtil.formRedirectUrl(request, COMMAND_HOME);
                    LOG.trace("Path to send redirect --> {}",path);
                    LOG.debug("LoginCommand finish successfully!");
                    return SEND_TO_REDIRECT+path;
                }
            } else {
                LOG.warn("referPage does not contain a login command!");
                return SEND_TO_FORWARD+VIEW_LOGIN;
            }
        } catch (IllegalArgumentException | ValidationException e) {
            LOG.warn("Validation failed!");
            String message = MessageResolver.getMessage(INVALID_DATA, locale);
            request.setAttribute(FAIL_MESSAGE_LOGIN, message);
            return SEND_TO_FORWARD+VIEW_LOGIN;
        } catch (ServiceException e) {
            LOG.error("Exception during log in", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

}

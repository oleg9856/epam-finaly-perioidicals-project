package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeUserRoleCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ChangeUserRoleCommand.class);
    private static final String SUCCESS_CHANGED_ROLE = "user.change_role_success";

    private final UserService userService;

    public ChangeUserRoleCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("ChangeUserRoleCommand starts");
            LocaleType locale = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}", locale);

            int userId = Integer.parseInt(request.getParameter(REQUEST_ATTR_USER_ID));
            LOG.trace("User id is --> {}", userId);
            Role role = Role.valueOf(request.getParameter(REQUEST_ATTR_ROLE));
            LOG.trace("Role is --> {}", role.name());
            userService.changeRoleByUserId(userId, role);

            String message = MessageResolver.getMessage(SUCCESS_CHANGED_ROLE, locale);
            String referPage = HttpUtil.getReferPage(request);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_PARAM_RETURN_PAGE, referPage);
            LOG.debug("ChangeUserRoleCommand finish successfully");
            return SEND_TO_REDIRECT + path;
        } catch (ServiceException e) {
            LOG.warn("Exception changed role of user");
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

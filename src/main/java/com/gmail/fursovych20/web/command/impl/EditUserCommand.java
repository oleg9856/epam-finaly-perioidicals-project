package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements edit user command
 */
public class EditUserCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditUserCommand.class);

    private final UserService userService;

    public EditUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("ShowResultPageCommand starts");
            List<User> userList = userService.findAllUsers();
            request.setAttribute("users",userList);
            LOG.debug("ShowResultPageCommand finish");
            return SEND_TO_FORWARD+VIEW_ALL_USERS;
        } catch (ServiceException e) {
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

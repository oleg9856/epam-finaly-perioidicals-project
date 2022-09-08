package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.service.BalanceOperationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements update balance operation command
 */
public class UserBalanceOperationCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UserBalanceOperationCommand.class);

    private final BalanceOperationService balanceOperationService;

    public UserBalanceOperationCommand(BalanceOperationService balanceOperationService) {
        this.balanceOperationService = balanceOperationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("BalanceOperation starts");
            int userID = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
            LOG.trace("User ID --> {}",userID);
            List<BalanceOperation> balanceOperations = balanceOperationService.findBalanceOperationUserByUserId(userID);
            request.setAttribute(REQUEST_ATTR_BALANCE_OPERATION_LIST,balanceOperations);
            LOG.debug("BalanceOperationCommand finish successfully!");
            return SEND_TO_FORWARD+VIEW_USER_BALANCE_OPERATION_HISTORY;
        } catch (ServiceException e) {
            LOG.error("Exception reading balance operation",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

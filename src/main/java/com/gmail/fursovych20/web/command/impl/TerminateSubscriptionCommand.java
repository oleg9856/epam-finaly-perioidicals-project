package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.service.SubscriptionService;
import com.gmail.fursovych20.service.exception.ServiceException;
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
 * and implements terminate subscription command
 */
public class TerminateSubscriptionCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(TerminateSubscriptionCommand.class);

    private static final String SUCCESS_TERMINATE_SUBSCRIPTION = "subscription.terminate_success";
    private final SubscriptionService subscriptionService;

    public TerminateSubscriptionCommand(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("TerminateSubscription starts");
            LocaleType locale = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}", locale.name());
            int subscriptionId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID));
            LOG.trace("Subscription Id --> {}", subscriptionId);

            subscriptionService.terminateSubscription(subscriptionId);
            String message = MessageResolver.getMessage(SUCCESS_TERMINATE_SUBSCRIPTION, locale);
            String returnPage = HttpUtil.getReferPage(request);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
            LOG.trace("Path to redirect --> {}",path);
            LOG.debug("TerminateSubscription finish successfully!");

            return SEND_TO_REDIRECT+path;
        } catch (ServiceException e) {
            LOG.error("Exception terminate subscription");
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

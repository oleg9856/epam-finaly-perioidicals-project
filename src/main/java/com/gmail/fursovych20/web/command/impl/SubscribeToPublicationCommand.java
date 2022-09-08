package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.service.SubscriptionService;
import com.gmail.fursovych20.service.exception.InsufficientFundsInAccountException;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements subscription to publication command
 */
public class SubscribeToPublicationCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(SubscribeToPublicationCommand.class);

    private static final String SUCCESS_SUB_TO_PUB = "subscription.success";
    private static final String FAILED_SUB_TO_PUB_NO_MONEY = "subscription.no_money";

    private static final String FAILED_SUB_TO_PUB_DATE_INVALID = "subscription.date_invalid";

    private final SubscriptionService subscriptionService;

    public SubscribeToPublicationCommand(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("SubscribeToPublicationCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}", locale.name());
        int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
        LOG.trace("User Id --> {}", userId);
        int publicationId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID_OF_PUBLICATION));
        LOG.trace("Publication Id --> {}", publicationId);
        LocalDate startDate = LocalDate.parse(request.getParameter(REQUEST_PARAM_START_DATA));
        LOG.trace("Start date --> {}", startDate);
        int duration = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_DURATION));
        LOG.trace("Duration --> {}", duration);
        try {
            subscriptionService.create(userId, publicationId, startDate, duration);
            LOG.trace("Subscription create!");
            String message = MessageResolver.getMessage(SUCCESS_SUB_TO_PUB, locale);
            String referPage = HttpUtil.getReferPage(request);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_PARAM_RETURN_PAGE, referPage);
            LOG.debug("SubscribeToPublicationCommand finish success!");
            return SEND_TO_REDIRECT + path;
        } catch (InsufficientFundsInAccountException e) {
            LOG.debug("Insufficient funds!");
            String message = MessageResolver.getMessage(FAILED_SUB_TO_PUB_NO_MONEY, locale);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_PUBLICATION) + "?id=" + publicationId;
            path = HttpUtil.addParamToPath(path, FAIL_MESSAGE_SUBSCRIPTION, message);
            return SEND_TO_REDIRECT + path;
        } catch (DateTimeException e){
            LOG.warn("Invalid date!");
            String message = MessageResolver.getMessage(FAILED_SUB_TO_PUB_DATE_INVALID, locale);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_PUBLICATION) + "?id=" + publicationId;
            path = HttpUtil.addParamToPath(path, FAIL_MESSAGE_SUBSCRIPTION, message);
            return SEND_TO_REDIRECT + path;
        } catch (ServiceException e) {
            LOG.warn("Exception made subscription", e);
            return SEND_TO_FORWARD + VIEW_503_ERROR;
        }
    }
}

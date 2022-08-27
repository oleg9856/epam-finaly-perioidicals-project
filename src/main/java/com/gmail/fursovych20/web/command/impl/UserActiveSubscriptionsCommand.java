package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.SubscriptionService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class UserActiveSubscriptionsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UserActiveSubscriptionsCommand.class);

    private final SubscriptionService subscriptionService;
    private final PublicationService publicationService;

    public UserActiveSubscriptionsCommand(SubscriptionService subscriptionService, PublicationService publicationService) {
        this.subscriptionService = subscriptionService;
        this.publicationService = publicationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("UserActiveSubscriptionCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}",locale.name());
        int userID = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
        LOG.trace("User ID --> {}",userID);

        List<Subscription> subscriptions;
        Map<Integer, String> publicationNames = new HashMap<>();
        try {
            subscriptions = subscriptionService.findActiveUserSubscriptionsByUserId(userID);
            for (Subscription subscription: subscriptions){
                Publication publication = publicationService.findPublicationByIdAndLocale(subscription.getPublicationId(),locale);
                publicationNames.put(publication.getId(), publication.getName());
            }

            request.setAttribute(REQUEST_ATTR_ACTIVE_SUBSCRIPTION_LIST,subscriptions);
            request.setAttribute(REQUEST_ATTR_PUBLICATION_NAMES, publicationNames);
            LOG.debug("UserActiveSubscriptionCommand finish success!");
            return SEND_TO_FORWARD+VIEW_USER_ACTIVE_SUBSCRIPTIONS;
        } catch (ServiceException e) {
            LOG.error("Exception reading active subscription command",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

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

public class UserSubscriptionHistoryCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UserSubscriptionHistoryCommand.class);

    private final SubscriptionService subscriptionService;
    private final PublicationService publicationService;

    public UserSubscriptionHistoryCommand(SubscriptionService subscriptionService, PublicationService publicationService) {
        this.subscriptionService = subscriptionService;
        this.publicationService = publicationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("UserSubscriptionHistoryCommand starts");
            LocaleType locale = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}",locale.name());
            int userID = (int)request.getSession().getAttribute(SESSION_ATTR_USER_ID);
            LOG.trace("User ID --> {}", userID);
            List<Subscription> subscriptions = subscriptionService.findAllSubscriptionByUserId(userID);
            Map<Integer, String> publicationNames = new HashMap<>();
            for (Subscription subscription : subscriptions) {
                Publication publication = publicationService.findPublicationByIdAndLocale(subscription.getPublicationId(), locale);
                publicationNames.put(publication.getId(), publication.getName());
            }
            request.setAttribute(REQUEST_ATTR_SUBSCRIPTION_LIST, subscriptions);
            request.setAttribute(REQUEST_ATTR_PUBLICATION_NAMES, publicationNames);
            LOG.debug("UserSubscriptionHistoryCommand finish success!");
            return SEND_TO_FORWARD+VIEW_USER_SUBSCRIPTION_HISTORY;
        } catch (ServiceException e) {
            LOG.error("Exception showing subscription!");
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.*;
import com.gmail.fursovych20.service.*;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class ShowPublicationCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ShowPublicationCommand.class);

    private final PublicationService publicationService;
    private final ReviewService reviewService;
    private final ThemeService themeService;
    private final TypeService typeService;

    public ShowPublicationCommand(PublicationService publicationService, ReviewService reviewService, ThemeService themeService, TypeService typeService) {
        this.publicationService = publicationService;
        this.reviewService = reviewService;
        this.themeService = themeService;
        this.typeService = typeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("ShowPublicationCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}",locale.name());
        int publicationId = Integer.parseInt(request.getParameter(REQUEST_PARAM_PUBLICATION_ID));
        LOG.trace("Publication Id --> {}",publicationId);
        try{
            Publication publication = publicationService.findPublicationByIdAndLocale(publicationId,locale);
            LOG.trace("Publication --> {}",publication);
            request.setAttribute(REQUEST_ATTR_PUBLICATION, publication);
            List<Review> reviews = reviewService.findReviewByPublicationId(publication.getId());
            LOG.trace("Reviews --> {}",reviews);
            request.setAttribute(REQUEST_ATTR_REVIEWS, reviews);
            Theme theme = themeService.findThemeById(publication.getThemeId(), locale);
            LOG.trace("Theme --> {}",theme);
            request.setAttribute(REQUEST_ATTR_THEME, theme);
            Type type = typeService.findTypeServiceById(publication.getTypeId(),locale);
            LOG.trace("Type --> {}",type);
            request.setAttribute(REQUEST_ATTR_TYPE, type);

            LOG.debug("ShowPublicationCommand finish!");
            return SEND_TO_FORWARD+VIEW_PUBLICATION_DETAILS;
        } catch (NullPointerException e){
            return SEND_TO_FORWARD + VIEW_ERROR;
        } catch (ServiceException e){
            LOG.error("Exception showing publication", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

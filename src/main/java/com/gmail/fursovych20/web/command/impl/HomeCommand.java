package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.*;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import com.gmail.fursovych20.service.*;
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

/**
 * A class which implements interface Command <br/>
 * and implements home command
 */
public class HomeCommand implements Command {

    private static final int THEME_DEFAULT = 0;
    private static final int TYPE_DEFAULT = 0;
    private static final int SORT_DEFAULT = 0;
    private static final int CURRENT_PAGE_DEFAULT = 1;
    private static final int ITEMS_PER_PAGE_DEFAULT = 10;
    private static final Logger LOG = LogManager.getLogger(HomeCommand.class);

    private final PublicationService publicationService;
    private final ThemeService themeService;
    private final TypeService typeService;

    public HomeCommand(PublicationService publicationService, ThemeService themeService, TypeService typeService) {
        this.publicationService = publicationService;
        this.themeService = themeService;
        this.typeService = typeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("HomeCommand starts");
            LocaleType localeType = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}",localeType);
            PublicationSearchCriteriaDTO criteria = getSearchCriteria(request);
            List<Publication> publications = publicationService.findAllPublicationByCriteria(criteria);
            List<Theme> themes = themeService.findAllThemeByLocaleType(localeType);


            List<Type> types = typeService.findAllTypeServiceByLocaleType(localeType);
            Map<Short, String> typeNames = new HashMap<>();
            for (Type type : types) {
                typeNames.put(type.getId(), type.getName());
            }
            request.setAttribute(REQUEST_ATTR_PUBLICATION_LIST, publications);
            request.setAttribute(REQUEST_ATTR_THEMES, themes);
            request.setAttribute(REQUEST_ATTR_TYPES, types);
            request.setAttribute(REQUEST_ATTR_TYPE_NAMES, typeNames);
            request.setAttribute(REQUEST_ATTR_PUBLICATION_SEARCH_CRITERIA, criteria);
            LOG.debug("HomeCommand finish");
            return SEND_TO_FORWARD+VIEW_HOME;
        } catch (ServiceException e) {
            LOG.warn("Exception showing home page", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private PublicationSearchCriteriaDTO getSearchCriteria(HttpServletRequest request) {
        LOG.debug("getSearchCriteria starts");
        PublicationSearchCriteriaDTO criteria = new PublicationSearchCriteriaDTO();
        LocaleType localeType = HttpUtil.getLocale(request);

        int themeId;
        int typeId;
        int sortId;
        int currentPage;
        int itemsPage;

        try {
            themeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_THEME_ID));
            typeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_TYPE_ID));
            sortId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SORT_ID));
            currentPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_CURRENT_PAGE));
            itemsPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_ITEMS_PER_PAGE));
        } catch (NumberFormatException e) {
            themeId = THEME_DEFAULT;
            typeId = TYPE_DEFAULT;
            sortId = SORT_DEFAULT;
            currentPage = CURRENT_PAGE_DEFAULT;
            itemsPage = ITEMS_PER_PAGE_DEFAULT;
        }

        criteria.setLocale(localeType);
        criteria.setThemeId(themeId);
        criteria.setTypeId(typeId);
        criteria.setOrderId(sortId);
        criteria.setCurrentPage(currentPage);
        criteria.setItemsPerPage(itemsPage);

        LOG.debug("getSearchCriteria finally success!");
        return criteria;
    }
}

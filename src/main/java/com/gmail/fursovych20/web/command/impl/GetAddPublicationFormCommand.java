package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.TypeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

import java.util.List;

/**
 * A class which implements interface Command <br/>
 * and implements get add publication form command
 */
public class GetAddPublicationFormCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(GetAddPublicationFormCommand.class);

    private final ThemeService themeService;
    private final TypeService typeService;

    public GetAddPublicationFormCommand(ThemeService themeService, TypeService typeService) {
        this.themeService = themeService;
        this.typeService = typeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("GetAddPublicationFormCommand starts");
            LocaleType locale = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}",locale);
            List<Theme> themes = themeService.findAllThemeByLocaleType(locale);
            List<Type> types = typeService.findAllTypeServiceByLocaleType(locale);

            request.setAttribute(REQUEST_ATTR_THEMES, themes);
            request.setAttribute(REQUEST_ATTR_TYPES, types);
            LOG.debug("GetAddPublicationFormCommand finish success!");
            return SEND_TO_FORWARD+VIEW_ADD_PUBLICATION_FORM;
        } catch (ServiceException e) {
            LOG.error("Exception preparing publication form",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

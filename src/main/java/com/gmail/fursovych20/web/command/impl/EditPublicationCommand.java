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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class EditPublicationCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditPublicationCommand.class);

    private final TypeService typeService;
    private final ThemeService themeService;

    public EditPublicationCommand(TypeService typeService, ThemeService themeService) {
        this.typeService = typeService;
        this.themeService = themeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("EditPublicationCommand starts");
            LocaleType locale = HttpUtil.getLocale(request);
            LOG.trace("Locale --> {}",locale);
            List<Theme> themes = themeService.findAllThemeByLocaleType(locale);
            List<Type> types = typeService.findAllTypeServiceByLocaleType(locale);
            request.setAttribute(REQUEST_ATTR_TYPES, types);
            request.setAttribute(REQUEST_ATTR_THEMES, themes);
            request.setAttribute(REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION,
                    Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID_OF_PUBLICATION)));

            LOG.debug("EditPublicationCommand finish success!");
            return SEND_TO_FORWARD+VIEW_EDIT_PUBLICATION;
        } catch (ServiceException e) {
            LOG.error("Exception reading localized type",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

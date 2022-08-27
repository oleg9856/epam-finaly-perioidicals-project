package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class EditThemeCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditThemeCommand.class);

    private final ThemeService themeService;

    public EditThemeCommand(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("EditThemeCommand starts");
            List<LocalizedThemeDTO> themes = themeService.findAllLocalized();
            request.setAttribute(REQUEST_ATTR_THEMES,themes);

            LOG.debug("EditThemeCommand finish success!");
            return SEND_TO_FORWARD+VIEW_EDIT_THEMES;
        } catch (ServiceException e) {
            LOG.error("Exception reading localized types", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

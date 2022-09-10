package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements update theme command
 */
public class UpdateThemeCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateThemeCommand.class);

    private static final String FAIL_MESSAGE_KEY = "theme.update_failed";

    private final ThemeService themeService;

    public UpdateThemeCommand(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("UpdateThemeCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}", locale.name());
        try {
            LocalizedThemeDTO localizedThemeDTO = getLocalizedTheme(request);
            themeService.update(localizedThemeDTO);
            String referPage = HttpUtil.formRedirectUrl(request, COMMAND_EDIT_THEMES);
            LOG.trace("referPage --> {}",referPage);
            return SEND_TO_REDIRECT+referPage;
        } catch (ValidationException e){
            LOG.warn("Invalid data",e);
            String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
            request.setAttribute(FAIL_MESSAGE_UPDATE_THEME, message);
            return SEND_TO_FORWARD+VIEW_EDIT_THEMES;
        } catch (ServiceException e) {
            LOG.error("Exception updating localized theme!",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private LocalizedThemeDTO getLocalizedTheme(HttpServletRequest request) {
        int themeID = Integer.parseInt(request.getParameter(REQUEST_PARAM_THEME_ID));
        LOG.trace("Theme ID --> {}", themeID);
        Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
        String nameUA = request.getParameter(REQUEST_PARAM_NAME_UA);
        String nameEN = request.getParameter(REQUEST_PARAM_NAME_EN);
        names.put(LocaleType.uk_UA, nameUA);
        names.put(LocaleType.en_US, nameEN);

        return new LocalizedThemeDTO.Builder()
                .setId(themeID)
                .setDefaultName(nameEN)
                .setLocalizedNames(names)
                .build();
    }
}

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
 * and implements add theme command
 */
public class AddThemeCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddThemeCommand.class);

    private static final String FAIL_MESSAGE_KEY = "theme.add_failed";
    private static final String SUCCESS_MESSAGE_KEY = "theme.add_success";

    private final ThemeService themeService;

    public AddThemeCommand(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("AddThemeCommand starts");
        LocaleType localeType = HttpUtil.getLocale(request);
        LOG.trace("Locale is --> {}", localeType.name());
        try {
            LocalizedThemeDTO localizedThemeDTO = getLocalizedTheme(request);
            themeService.create(localizedThemeDTO);

            String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, localeType);
            String returnPage = HttpUtil.getReferPage(request);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
            return SEND_TO_REDIRECT+path;
        }catch (ValidationException e){
            LOG.warn("Invalid data");
            String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY,localeType);
            request.setAttribute(FAIL_MESSAGE_ADD_THEME, message);
            return SEND_TO_FORWARD+COMMAND_ADD_PUBLICATION;
        }catch (ServiceException e) {
            LOG.error("Exception creating localized theme", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private LocalizedThemeDTO getLocalizedTheme(HttpServletRequest request) {
        Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
        String nameUa = request.getParameter(REQUEST_PARAM_NAME_UA);
        String nameEn = request.getParameter(REQUEST_PARAM_NAME_EN);
        names.put(LocaleType.uk_UA, nameUa);
        names.put(LocaleType.en_US, nameEn);

        return new LocalizedThemeDTO.Builder()
                .setDefaultName(nameEn)
                .setLocalizedNames(names)
                .build();
    }
}

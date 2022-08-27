package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.TypeService;
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

public class AddTypeCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddTypeCommand.class);

    private static final String FAIL_MESSAGE_KEY = "type.add_failed";
    private static final String SUCCESS_MESSAGE_KEY = "type.add_success";

    private final TypeService typeService;

    public AddTypeCommand(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("AddType starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}", locale.name());
        try {
            LocalizedTypeDTO localizedTheme = getLocalizedType(request);
            typeService.create(localizedTheme);

            String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
            String returnPage = HttpUtil.getReferPage(request);
            String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
            return SEND_TO_REDIRECT+path;
        } catch (ValidationException e){
            LOG.warn("Invalid data");
            String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
            request.setAttribute(FAIL_MESSAGE_ADD_TYPE,message);
            return SEND_TO_FORWARD+COMMAND_ADD_PUBLICATION;
        } catch (ServiceException e) {
            LOG.error("Exception creating locale type",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private LocalizedTypeDTO getLocalizedType(HttpServletRequest request) {
        LOG.debug("getLocalizedTheme starts");
        LocalizedTypeDTO localizedTypeDTO = new LocalizedTypeDTO();
        Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
        String nameUa = request.getParameter(REQUEST_PARAM_NAME_UA);
        String nameEn = request.getParameter(REQUEST_PARAM_NAME_UA);
        names.put(LocaleType.uk_UA, nameUa);
        names.put(LocaleType.en_US, nameEn);

        localizedTypeDTO.setDefaultName(nameEn);
        localizedTypeDTO.setLocalizedNames(names);
        LOG.debug("getLocalizedTheme finish successfully!");
        return localizedTypeDTO;
    }
}

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

/**
 * A class which implements interface Command <br/>
 * and implements update type command
 */
public class UpdateTypeCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateTypeCommand.class);

    private static final String FAIL_KEY_MESSAGE = "type.update_failed";

    private final TypeService typeService;

    public UpdateTypeCommand(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("UpdateTypeCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}",locale.name());
        try{
            LocalizedTypeDTO localizedTypeDTO = getLocalizedType(request);
            typeService.update(localizedTypeDTO);

            String referPage = HttpUtil.formRedirectUrl(request, COMMAND_EDIT_TYPES);
            LOG.trace("referPage --> {}",referPage);
            return SEND_TO_REDIRECT+referPage;
        } catch (ValidationException e){
            LOG.warn("Invalid data!");
            String message = MessageResolver.getMessage(FAIL_KEY_MESSAGE,locale);
            request.setAttribute(FAIL_MESSAGE_UPDATE_TYPE,message);
            return SEND_TO_FORWARD+VIEW_EDIT_TYPES;
        } catch (ServiceException e){
            LOG.error("Exception updating localized type", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private LocalizedTypeDTO getLocalizedType(HttpServletRequest request) {
        Map<LocaleType,String> names = new EnumMap<>(LocaleType.class);
        int typeID = Integer.parseInt(request.getParameter(REQUEST_PARAM_TYPE_ID));
        LOG.trace("Type ID --> {}",typeID);
        String nameUA = request.getParameter(REQUEST_PARAM_NAME_UA);
        String nameEN = request.getParameter(REQUEST_PARAM_NAME_EN);
        names.put(LocaleType.en_US, nameEN);
        names.put(LocaleType.uk_UA, nameUA);

        return new LocalizedTypeDTO.Builder()
                .setId(typeID)
                .setLocalizedNames(names)
                .setDefaultName(nameEN)
                .build();
    }
}

package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.TypeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements edit type command
 */
public class EditTypeCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditTypeCommand.class);

    private final TypeService themeService;

    public EditTypeCommand(TypeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("EditTypeCommand starts");
            List<LocalizedTypeDTO> types = themeService.findAllLocalized();
            request.setAttribute(REQUEST_ATTR_TYPES, types);

            LOG.debug("EditTypeCommand finish success!");
            return SEND_TO_FORWARD+VIEW_EDIT_TYPES;

        } catch (ServiceException e) {
            LOG.error("Exception reading localized type",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

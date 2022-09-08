package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements update publication command
 */
public class UpdatePublicationCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdatePublicationCommand.class);

    private static final String FAIL_MESSAGE_KEY = "add_issue.fail";
    private static final String SUCCESS_MESSAGE_KEY = "add_publication.success";

    private final PublicationService publicationService;

    public UpdatePublicationCommand(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("UpdatePublicationCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}",locale.name());
        try {
            LocalizedPublicationDTO localizedPublicationDTO = getLocalizedPublication(request);
            publicationService.update(localizedPublicationDTO);
            String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY,locale);
            String referPage = HttpUtil.getReferPage(request);
            LOG.trace("referPage --> {}",referPage);
            String path = HttpUtil.formRedirectUrl(request,COMMAND_SHOW_RESULT_PAGE);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, referPage);
            LOG.trace("UpdatePublicationCommand finish successfully!");
            return SEND_TO_REDIRECT+path;
        }catch (ValidationException e) {
            LOG.warn("Invalid data");
            String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
            request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
            return SEND_TO_FORWARD+VIEW_ADD_PUBLICATION_FORM;
        } catch (ServletException | IOException | ServiceException e) {
            LOG.error("Exception adding publication", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private LocalizedPublicationDTO getLocalizedPublication(HttpServletRequest request) throws ServletException, IOException {
        LocalizedPublicationDTO localizedPublicationDTO = new LocalizedPublicationDTO();
        int publicationID = Integer.parseInt(request.getParameter(REQUEST_PARAM_PUBLICATION_ID));
        short themeId = Short.parseShort(request.getParameter(REQUEST_PARAM_THEME_ID));
        short typeId = Short.parseShort(request.getParameter(REQUEST_PARAM_TYPE_ID));
        String nameUA = request.getParameter(REQUEST_PARAM_NAME_UA);
        String nameEN = request.getParameter(REQUEST_PARAM_NAME_EN);
        String descriptionUA = request.getParameter(REQUEST_PARAM_PUBLICATION_DESCRIPTION_UA);
        String descriptionEN = request.getParameter(REQUEST_PARAM_PUBLICATION_DESCRIPTION_EN);
        double price = Double.parseDouble(request.getParameter(REQUEST_PARAM_PUBLICATION_PRICE));
        Map<LocaleType,String> names = new EnumMap<>(LocaleType.class);
        names.put(LocaleType.en_US, nameEN);
        names.put(LocaleType.uk_UA, nameUA);
        Map<LocaleType,String> descriptions= new EnumMap<>(LocaleType.class);
        descriptions.put(LocaleType.en_US, descriptionEN);
        descriptions.put(LocaleType.uk_UA, descriptionUA);

        String pathToPicture = HttpUtil.uploadPublicationPicture(request);

        localizedPublicationDTO.setId(publicationID);
        localizedPublicationDTO.setThemeId(themeId);
        localizedPublicationDTO.setTypeID(typeId);
        localizedPublicationDTO.setNames(names);
        localizedPublicationDTO.setDescriptions(descriptions);
        localizedPublicationDTO.setPrice(price);
        localizedPublicationDTO.setPicturePath(pathToPicture);
        return localizedPublicationDTO;
    }
}

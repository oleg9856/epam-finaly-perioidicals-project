package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DeletePublicationCommand is command for deleting the specific publication
 *
 * @author O. Fursovych
 */
public class DeletePublicationCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeletePublicationCommand.class);
    private final PublicationService publicationService;
    private static final String SUCCESS_MESSAGE_KEY = "delete_publication.success";

    public DeletePublicationCommand(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("DeletePublicationCommand starts");
        LocaleType locale = HttpUtil.getLocale(request);
        LOG.trace("Locale --> {}",locale);
        try {
            int id = Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION));
            LOG.trace("Publication id --> {}", id);
            publicationService.deletePublication(id);
            String path = HttpUtil.formRedirectUrl(request,COMMAND_SHOW_RESULT_PAGE);
            String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY,locale);
            String referPage = HttpUtil.getReferPage(request);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
            path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, referPage);
            LOG.debug("DeletePublicationCommand finish");
            return SEND_TO_REDIRECT+path;
        } catch (ServiceException e) {
            LOG.info("Exception delete publication", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

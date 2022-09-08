package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements change locale command
 */
public class ChangeLocaleCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("ChangeLocaleCommand starts");
        String locale = request.getParameter(REQUEST_PARAM_LOCALE);
        LOG.trace("Locale --> {}",locale);
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_ATTR_LOCALE, locale);
        String referPage = HttpUtil.getReferPage(request);
        LOG.debug("ChangeLocaleCommand finish!");
        return SEND_TO_REDIRECT+referPage;
    }
}

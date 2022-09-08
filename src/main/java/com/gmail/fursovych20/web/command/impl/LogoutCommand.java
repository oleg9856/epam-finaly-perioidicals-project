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
 * and implements logout command
 */
public class LogoutCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        LOG.debug("LogoutCommand starts");
        String referPage = HttpUtil.getReferPage(request);
        LOG.trace("referPage --> {}",referPage);
        HttpSession session = request.getSession();
        if (session != null) {
            LOG.trace("Session is --> {}",session);
            session.invalidate();
        }
        LOG.debug("LogoutCommand finish");
        return SEND_TO_REDIRECT+referPage;
    }
}

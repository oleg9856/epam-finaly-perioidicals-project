package com.gmail.fursovych20.web;

import com.gmail.fursovych20.web.command.Command;

import com.gmail.fursovych20.web.command.CommandContainer;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * WebController for redirect to the different pages,<br/> using
 * forward and sendRedirect or do nothing.
 *
 * @author O. Fursovych
 */

@MultipartConfig
@WebServlet(name = "Controller", value = "/controller/*")
public class FontController extends HttpServlet {

	private static final long serialVersionUID = -648733403178379343L;

	private static final Logger LOG = LogManager.getLogger(FontController.class);

    public FontController() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Controller#doGet starts");
		process(request, response);
		LOG.debug("Controller#doGet finished");
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Controller#doGPost starts");
		process(request, response);
		LOG.debug("Controller#doGPost finished");
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		LOG.debug("Controller starts");

		//extract command name from the request
		String commandName = req.getPathInfo();
		LOG.trace("Request parameter: command --> {}", commandName);

		//obtain command object by its name
		Command command = CommandContainer.getCommand(commandName);
		LOG.trace("Obtain key --> {}", command);

		// execute command and get forward address
		String address;
		try {
			address = command.execute(req, resp);
		} catch (CommandExeption e) {
			LOG.error("(ServiceException) Error! Cannot extract this command ",e);
			throw new ServletException(e);
		}
		LOG.trace("Forward address --> {}", address);

		LOG.debug("Controller finished, now go to address --> {}", address);

		// if the forward address is not null go to the address
		if (address != null) {
			if(address.equals(TO_DO_NOTHING)){
				return;
			}
			String[] viewNameParts = address.split("->");
			String dispatchType = viewNameParts[0];
			String viewName = viewNameParts[1];

			switch (dispatchType) {
				case "forward":
					LOG.trace("Send to forward --> {}",viewName);
					RequestDispatcher dispatcher = req.getRequestDispatcher(viewName);
					dispatcher.forward(req, resp);
					break;
				case "redirect":
					LOG.trace("Send to redirect --> {}",viewName);
					resp.sendRedirect(viewName);
					break;
				default:
					throw new IllegalArgumentException(
							String.format("Incorrect the dispatch type of the abstractViewName: %s", address));
			}
		}else {
			LOG.error("Command is null");
			RequestDispatcher dispatcher = req.getRequestDispatcher(VIEW_ERROR);
			dispatcher.forward(req,resp);
		}
	}

}

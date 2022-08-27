package com.gmail.fursovych20.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.gmail.fursovych20.db.connectionpool.JDBCManager;
import com.gmail.fursovych20.db.dao.*;
import com.gmail.fursovych20.db.dao.impl.*;
import com.gmail.fursovych20.service.*;
import com.gmail.fursovych20.service.impl.*;
import com.gmail.fursovych20.web.command.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Servlet context initialized starts");

        initCommands();

        log("Servlet context initialized finished");
    }



    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }

    /**
     * Initializes command container
     */
    private void initCommands() {
        DataSource dataSource = JDBCManager.getInstance().getDataSource();
        CommandContainer commandContainer = new CommandContainer();
        BalanceOperationDAO balanceOperationDAO = new BalanceOperationDAOImpl(dataSource);
        IssueDAO issueDAO = new IssueDAOImpl(dataSource);
        PublicationDAO publicationDAO = new PublicationDAOImpl(dataSource);
        ReviewDAO reviewDAO = new ReviewDAOImpl(dataSource);
        SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl(dataSource);
        ThemeDAO themeDAO = new ThemeDAOImpl(dataSource);
        TypeDAO typeDAO = new TypeDAOImpl(dataSource);
        UserDAO userDAO = new UserDAOImpl(dataSource);

        BalanceOperationService balanceOperationService = new BalanceOperationServiceImpl(balanceOperationDAO);
        commandContainer.setBalanceOperationService(balanceOperationService);
        UserService userService = new UserServiceImpl(userDAO);
        commandContainer.setUserService(userService);
        PublicationService publicationService = new PublicationServiceImpl(publicationDAO);
        commandContainer.setPublicationService(publicationService);
        ReviewService reviewService = new ReviewServiceImpl(reviewDAO);
        commandContainer.setReviewService(reviewService);
        SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, publicationService, userDAO);
        commandContainer.setSubscriptionService(subscriptionService);
        ThemeService themeService = new ThemeServiceImpl(themeDAO);
        commandContainer.setThemeService(themeService);
        TypeService typeService = new TypeServiceImpl(typeDAO);
        commandContainer.setTypeService(typeService);
        IssueService issueService = new IssueServiceImpl(issueDAO,userService, publicationService);
        commandContainer.setIssueService(issueService);

        commandContainer.putCommands();
    }
}

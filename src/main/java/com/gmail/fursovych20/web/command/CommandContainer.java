package com.gmail.fursovych20.web.command;

import com.gmail.fursovych20.service.*;
import com.gmail.fursovych20.web.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * Holder for all commands
 *
 * @author O.Fursovych
 */
public final class CommandContainer {
    private static final Logger LOG = LogManager.getLogger(CommandContainer.class);

    private BalanceOperationService balanceOperationService;
    private UserService userService;
    private PublicationService publicationService;
    private ReviewService reviewService;
    private SubscriptionService subscriptionService;
    private ThemeService themeService;
    private TypeService typeService;
    private IssueService issueService;

    public CommandContainer() {
    }

    private static final Map<String, Command> commands = new HashMap<>();

    //commands
    public void putCommands(){
        //admin commands
        commands.put(COMMAND_EDIT_TYPES, new EditTypeCommand(typeService));
        commands.put(COMMAND_EDIT_THEMES,new EditThemeCommand(themeService));
        commands.put(COMMAND_DELETE_REVIEW, new DeleteReviewCommand(reviewService));
        commands.put(COMMAND_ADD_TYPE, new AddTypeCommand(typeService));
        commands.put(COMMAND_ADD_PUBLICATION, new AddPublicationCommand(publicationService));
        commands.put(COMMAND_UPDATE_PUBLICATION, new UpdatePublicationCommand(publicationService));
        commands.put(COMMAND_UPDATE_REVIEW, new UpdateReviewCommand(reviewService));
        commands.put(COMMAND_UPDATE_THEME,new UpdateThemeCommand(themeService));
        commands.put(COMMAND_UPDATE_TYPE, new UpdateTypeCommand(typeService));
        commands.put(COMMAND_UPLOAD_ISSUE, new UploadIssueCommand(issueService));
        commands.put(COMMAND_EDIT_PUBLICATION,new EditPublicationCommand(typeService, themeService));
        commands.put(COMMAND_ADD_PUB_FORM, new GetAddPublicationFormCommand(themeService, typeService));
        commands.put(COMMAND_CHANGE_USER_ROLE, new ChangeUserRoleCommand(userService));
        commands.put(COMMAND_ADD_THEME, new AddThemeCommand(themeService));
        commands.put(COMMAND_EDIT_USERS, new EditUserCommand(userService));
        commands.put(COMMAND_DELETE_PUBLICATION, new DeletePublicationCommand(publicationService));
        // user commands
        commands.put(COMMAND_SHOW_ISSUE_FILE, new ShowIssueFileCommand(issueService));
        commands.put(COMMAND_ADD_REVIEW, new AddReviewCommand(reviewService));
        commands.put(COMMAND_REPLENISH_BALANCE, new ReplenishBalanceCommand(balanceOperationService));
        commands.put(COMMAND_SUBSCRIBE, new SubscribeToPublicationCommand(subscriptionService));
        commands.put(COMMAND_SHOW_ISSUES,new ShowIssuesCommand(subscriptionService, issueService, publicationService));
        commands.put(COMMAND_TERMINATE_SUBSCRIPTION, new TerminateSubscriptionCommand(subscriptionService));
        commands.put(COMMAND_SHOW_USER_ACTIVE_SUBSCRIPTIONS, new UserActiveSubscriptionsCommand(subscriptionService, publicationService));
        commands.put(COMMAND_SHOW_USER_BALANCE_OPERATION_HISTORY, new UserBalanceOperationCommand(balanceOperationService));
        commands.put(COMMAND_SHOW_USER_PROFILE, new UserProfileCommand(userService));
        commands.put(COMMAND_SHOW_USER_SUBSCRIPTION_HISTORY, new UserSubscriptionHistoryCommand(subscriptionService, publicationService));

        commands.put(COMMAND_SHOW_PUBLICATION, new ShowPublicationCommand(publicationService, reviewService, themeService, typeService));
        commands.put(COMMAND_SHOW_RESULT_PAGE, new ShowResultPageCommand());
        commands.put(COMMAND_CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(COMMAND_HOME, new HomeCommand(publicationService, themeService, typeService));
        commands.put(COMMAND_LOGOUT, new LogoutCommand());
        commands.put(COMMAND_LOGIN, new LoginCommand(userService));
        commands.put(COMMAND_REGISTER, new RegisterCommand(userService));
        commands.put(COMMAND_SEARCH, new SearchCommand(publicationService, themeService, typeService));

        LOG.trace("Number of commands --> {}", commands.size());
        LOG.debug("Command container was successfully initialized");
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command getCommand(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.warn("Command not found, commandName --> {}", commandName);
            return commands.get(COMMAND_HOME);
        }

        return commands.get(commandName);
    }

    public void setBalanceOperationService(BalanceOperationService balanceOperationService) {
        this.balanceOperationService = balanceOperationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public ThemeService getThemeService() {
        return themeService;
    }

    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }


    public void setIssueService(IssueService issueService) {
        this.issueService = issueService;
    }
}

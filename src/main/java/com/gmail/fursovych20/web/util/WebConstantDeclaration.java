package com.gmail.fursovych20.web.util;

public final class WebConstantDeclaration {

    private WebConstantDeclaration() {}


	public static final String VIEW_503_ERROR = "/WEB-INF/jsp/503-error.jsp";
	public static final String VIEW_ERROR = "/WEB-INF/jsp/404-error.jsp";
	public static final String VIEW_HOME = "/WEB-INF/jsp/home.jsp";
	public static final String VIEW_LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String VIEW_REGISTER = "/WEB-INF/jsp/register.jsp";
	public static final String VIEW_USER_PROFILE = "/WEB-INF/jsp/user/profile.jsp";
	public static final String VIEW_USER_ACTIVE_SUBSCRIPTIONS = "/WEB-INF/jsp/user/subscriptions.jsp";
	public static final String VIEW_USER_SUBSCRIPTION_HISTORY = "/WEB-INF/jsp/user/subscription_history.jsp";
	public static final String VIEW_USER_BALANCE_OPERATION_HISTORY = "/WEB-INF/jsp/user/balance_operation_history.jsp";
	public static final String VIEW_EDIT_PUBLICATION = "/WEB-INF/jsp/admin/update_publication.jsp";
	public static final String VIEW_PUBLICATION_DETAILS = "/WEB-INF/jsp/publication.jsp";
	public static final String VIEW_ADD_PUBLICATION_FORM = "/WEB-INF/jsp/admin/add_publication_form.jsp";
	public static final String VIEW_EDIT_THEMES = "/WEB-INF/jsp/admin/edit_themes.jsp";
	public static final String VIEW_EDIT_TYPES = "/WEB-INF/jsp/admin/edit_types.jsp";
	public static final String VIEW_ALL_USERS = "/WEB-INF/jsp/admin/change_user_role.jsp";
	public static final String VIEW_AVAILABLE_ISSUES = "/WEB-INF/jsp/user/avaliable_issues.jsp";
	public static final String VIEW_RESULT_PAGE = "/WEB-INF/jsp/result_page.jsp";

	public static final String REQUEST_PARAM_LOGIN_OR_EMAIL = "login_or_email";
	public static final String REQUEST_PARAM_PASSWORD = "password";
	public static final String REQUEST_PARAM_LOCALE = "locale";
	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_NAME = "name";
	public static final String REQUEST_PARAM_SURNAME = "surname";
	public static final String REQUEST_PARAM_EMAIL = "email";
	public static final String REQUEST_PARAM_SUBSCRIPTION_ID = "subscription_id";
	public static final String REQUEST_PARAM_PUBLICATION_ID = "id";
	public static final String REQUEST_PARAM_THEME_ID = "theme";
	public static final String REQUEST_PARAM_TYPE_ID = "type";
	public static final String REQUEST_PARAM_SORT_ID = "order";
	public static final String REQUEST_PARAM_CURRENT_PAGE = "currentPage";
	public static final String REQUEST_PARAM_ITEMS_PER_PAGE = "itemsPerPage";
	public static final String REQUEST_PARAM_REVIEW_TEXT = "text";
	public static final String REQUEST_PARAM_REVIEW_MARK = "mark";
	public static final String REQUEST_PARAM_RECAPTCHA_NAME = "g-recaptcha-response";
	public static final String REQUEST_PARAM_REVIEW_ID_OF_PUBLICATION = "id_publication";
	public static final String REQUEST_PARAM_SUBSCRIPTION_ID_OF_PUBLICATION = "publication_id";
	public static final String REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION = "publication_id";
	public static final String REQUEST_PARAM_REVIEW_ID = "review_id";
	public static final String REQUEST_PARAM_SUM_FOR_REPLENISHMENT = "sum";
	public static final String REQUEST_PARAM_PUBLICATION_NAME = "publication_name";
	public static final String REQUEST_PARAM_ISSUE_ID = "issue_id";
	public static final String REQUEST_PARAM_ISSUE_DESCRIPTION = "description";
	public static final String REQUEST_PARAM_ISSUE_FILE = "issue_file";
	public static final String REQUEST_PARAM_START_DATA = "start_date";
	public static final String REQUEST_PARAM_SUBSCRIPTION_DURATION = "duration";
	public static final String REQUEST_PARAM_NAME_EN = "name_en";
	public static final String REQUEST_PARAM_NAME_UA = "name_ua";
	public static final String REQUEST_PARAM_PUBLICATION_DESCRIPTION_UA = "description_ua";
	public static final String REQUEST_PARAM_PUBLICATION_DESCRIPTION_EN = "description_en";
	public static final String REQUEST_PARAM_PUBLICATION_PRICE = "price";
	public static final String REQUEST_PARAM_MESSAGE = "message";
	public static final String REQUEST_PARAM_RETURN_PAGE = "return_page";

	public static final String SESSION_ATTR_LOCALE = "locale";
	public static final String SESSION_ATTR_REFER_PAGE = "referPage";
	public static final String SESSION_ATTR_USER_ID = "userId";
	public static final String SESSION_ATTR_USER_NAME = "userName";
	public static final String SESSION_ATTR_USER_ROLE = "userRole";

	public static final String REQUEST_HEADER_REFER_PAGE = "referer";
	public static final String REQUEST_ATTR_PUBLICATION_LIST = "publications";
	public static final String REQUEST_ATTR_PUBLICATION_NAMES = "publicationNames";
	public static final String REQUEST_ATTR_ACTIVE_SUBSCRIPTION_LIST = "active_subscriptions";
	public static final String REQUEST_ATTR_USER_ID = "user_id";
	public static final String REQUEST_ATTR_ROLE = "role";
	public static final String REQUEST_ATTR_SUBSCRIPTION_LIST = "subscriptions";
	public static final String REQUEST_ATTR_BALANCE_OPERATION_LIST = "balance_operations";
	public static final String REQUEST_ATTR_PUBLICATION = "publication";
	public static final String REQUEST_ATTR_REVIEWS = "reviews";
	public static final String REQUEST_ATTR_THEMES = "themes";
	public static final String REQUEST_ATTR_TYPES = "types";
	public static final String REQUEST_ATTR_USER = "user";
	public static final String REQUEST_ATTR_PUBLICATION_SEARCH_CRITERIA = "search_criteria";
	public static final String REQUEST_ATTR_ISSUES = "issues";
	public static final String REQUEST_ATTR_MESSAGE = "message";
	public static final String REQUEST_ATTR_RETURN_PAGE = "return_page";
	public static final String REQUEST_ATTR_TYPE_NAMES = "typeNames";
	public static final String REQUEST_ATTR_TYPE = "type";
	public static final String REQUEST_ATTR_THEME = "theme";

	public static final String SEND_TO_REDIRECT = "redirect->";
	public static final String SEND_TO_FORWARD = "forward->";

	public static final String TO_DO_NOTHING = "DO_NOTHING";
	public static final String COMMAND_HOME = "/home";
	public static final String COMMAND_LOGIN = "/login";
	public static final String COMMAND_LOGOUT = "/logout";
	public static final String COMMAND_REGISTER = "/register";
	public static final String COMMAND_SEARCH = "/search-publication";
	public static final String COMMAND_CHANGE_USER_ROLE = "/admin/change-user-role";
	public static final String COMMAND_CHANGE_LOCALE = "/change-locale";
	public static final String COMMAND_SHOW_RESULT_PAGE = "/show-result-page";
	public static final String COMMAND_SHOW_USER_PROFILE = "/user/profile";
	public static final String COMMAND_SHOW_ISSUES = "/user/show-issues";
	public static final String COMMAND_SHOW_ISSUE_FILE = "/user/show-issue-file";
	public static final String COMMAND_SHOW_USER_ACTIVE_SUBSCRIPTIONS = "/user/subscriptions";
	public static final String COMMAND_SHOW_USER_SUBSCRIPTION_HISTORY = "/user/subscription-history";
	public static final String COMMAND_SHOW_USER_BALANCE_OPERATION_HISTORY = "/user/balance-operation-history";
	public static final String COMMAND_TERMINATE_SUBSCRIPTION = "/user/terminate-subscription";
	public static final String COMMAND_SHOW_PUBLICATION = "/publication";
	public static final String COMMAND_ADD_REVIEW = "/user/add-review";
	public static final String COMMAND_UPDATE_REVIEW = "/admin/update-review";
	public static final String COMMAND_DELETE_REVIEW = "/admin/delete-review";
	public static final String COMMAND_REPLENISH_BALANCE = "/user/replenish-balance";
	public static final String COMMAND_ADD_THEME = "/admin/add-theme";
	public static final String COMMAND_ADD_PUBLICATION = "/admin/add-publication";
	public static final String COMMAND_DELETE_PUBLICATION = "/admin/delete-publication";
	public static final String COMMAND_ADD_PUB_FORM = "/admin/add-publication-form";
	public static final String COMMAND_EDIT_USERS = "/admin/edit-users";
	public static final String COMMAND_EDIT_PUBLICATION = "/admin/edit-publication";
	public static final String COMMAND_EDIT_THEMES = "/admin/edit-themes";
	public static final String COMMAND_UPDATE_THEME = "/admin/update-theme";
	public static final String COMMAND_EDIT_TYPES = "/admin/edit-types";
	public static final String COMMAND_UPDATE_TYPE = "/admin/update-type";
	public static final String COMMAND_ADD_TYPE = "/admin/add-type";
	public static final String COMMAND_UPLOAD_ISSUE = "/admin/upload-issue";
	public static final String COMMAND_UPDATE_PUBLICATION = "/admin/update-publication";
	public static final String COMMAND_SUBSCRIBE = "/user/subscribe";

	public static final String FAIL_MESSAGE_ADD_PUBLICATION = "publication_fail_message";
	public static final String FAIL_MESSAGE_UPDATE_THEME = "theme_fail_message";
	public static final String FAIL_MESSAGE_ADD_THEME = "theme_fail_message";
	public static final String FAIL_MESSAGE_ADD_TYPE = "type_fail_message";
	public static final String FAIL_MESSAGE_UPDATE_TYPE = "type_fail_message";
	public static final String FAIL_MESSAGE_REGISTER = "register_fail_message";
	public static final String FAIL_MESSAGE_SUBSCRIPTION = "subscription_fail_message";
	public static final String FAIL_MESSAGE_LOGIN = "login_fail_message";
	
}

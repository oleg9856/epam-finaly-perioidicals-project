package com.gmail.fursovych20.web.filter;

import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.WebConstantDeclaration;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * WebFilter for user which have admin role
 *
 * @author O. Fursovych
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/controller/admin/*")
public class AdminAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	HttpServletResponse httpResponse = (HttpServletResponse) response;
    	Role role = (Role) httpRequest.getSession().getAttribute(WebConstantDeclaration.SESSION_ATTR_USER_ROLE);
    	
    	if (role == null){
    		httpRequest.getSession().setAttribute(WebConstantDeclaration.SESSION_ATTR_REFER_PAGE, httpRequest.getRequestURI());
    		httpResponse.sendRedirect(HttpUtil.formRedirectUrl(httpRequest, WebConstantDeclaration.COMMAND_LOGIN));
    	} else if (role == Role.ADMIN) {
    		chain.doFilter(httpRequest, httpResponse);
    	} else {
    		httpResponse.sendRedirect(HttpUtil.formRedirectUrl(httpRequest, WebConstantDeclaration.COMMAND_HOME));
    	}
    }

}

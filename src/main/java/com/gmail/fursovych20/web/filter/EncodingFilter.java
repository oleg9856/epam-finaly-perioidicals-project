package com.gmail.fursovych20.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import java.io.IOException;
/**
 * WebFilter for encoding UTF-8
 *
 * @author O. Fursovych
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/controller/*")
public class EncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String CONTEXT_PARAM_NAME = "encoding";

    private String encodingName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encodingName = filterConfig.getServletContext().getInitParameter(CONTEXT_PARAM_NAME);
        if (encodingName == null) {
            encodingName = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encodingName);
        response.setCharacterEncoding(encodingName);
        chain.doFilter(request, response);
    }

}

package com.gmail.fursovych20.web.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EncodingFilterTest {

    @Mock
    private FilterChain chain;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private EncodingFilter encodingFilter;

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Test
    public void setDefaultEncodingDoFilter() throws ServletException, IOException {
        when(request.getCharacterEncoding()).thenReturn(DEFAULT_ENCODING);
       encodingFilter.doFilter(request, response, chain);

       assertEquals(DEFAULT_ENCODING, request.getCharacterEncoding());
    }
}
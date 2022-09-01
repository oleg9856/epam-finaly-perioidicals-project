package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.UserService;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;
    @InjectMocks
    private RegisterCommand registerCommand;

    private static final String HEADER = "/register";
    private static final String HEADER_FAIL = "fail_header";
    private static final String CODE_RECAPTCHA = "6Ld367khAAAAAN77biIpSCDVo_SbexYV_nIDUtxl";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        when(request.getParameter(REQUEST_PARAM_RECAPTCHA_NAME)).thenReturn(CODE_RECAPTCHA);
        assertNotNull(registerCommand.execute(request, response));
    }

    @Test
    public void testFailedExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER_FAIL);
        assertNotNull(registerCommand.execute(request, response));
    }
}
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

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.REQUEST_HEADER_REFER_PAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private LogoutCommand logoutCommand;

    private final static String HEADER = "header";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        assertNotNull(logoutCommand.execute(request, response));
    }
}
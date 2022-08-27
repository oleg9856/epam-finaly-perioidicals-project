package com.gmail.fursovych20.web.command.impl;

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
import static com.gmail.fursovych20.web.util.WebConstantDeclaration.REQUEST_PARAM_LOCALE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeLocaleCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ChangeLocaleCommand changeLocaleCommand;

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getParameter(REQUEST_PARAM_LOCALE)).thenReturn("uk_UA");
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn("header");
        assertNotNull(changeLocaleCommand.execute(request, response));
    }
}
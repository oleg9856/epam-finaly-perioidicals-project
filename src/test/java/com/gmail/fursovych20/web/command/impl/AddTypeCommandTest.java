package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.TypeService;
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
public class AddTypeCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private TypeService typeService;

    @InjectMocks
    private AddTypeCommand addTypeCommand;

    private static final String NAME = "NAME";
    public static final String REQUEST_HEADER_REFER_PAGE = "referer";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn("header");
        when(request.getParameter(REQUEST_PARAM_NAME_UA)).thenReturn(NAME);
        assertNotNull(addTypeCommand.execute(request,response));
    }
}
package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.ReviewService;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.exception.ServiceException;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddThemeCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ThemeService themeService;

    @InjectMocks
    private AddThemeCommand addThemeCommand;

    private static final String NAME = "NAME";
    public static final String HEADER = "header";


    @Test
    public void testSuccessExecute() throws CommandExeption, ServiceException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(REQUEST_PARAM_NAME_UA)).thenReturn(NAME);
        when(request.getParameter(REQUEST_PARAM_NAME_EN)).thenReturn(NAME);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        assertNotNull(addThemeCommand.execute(request,response));
    }
}
package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.TypeService;
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
import static com.gmail.fursovych20.web.util.WebConstantDeclaration.REQUEST_PARAM_ITEMS_PER_PAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private PublicationService publicationService;

    @Mock
    private ThemeService themeService;

    @Mock
    private TypeService typeService;

    @InjectMocks
    private SearchCommand searchCommand;

    private static final String ID = "1";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(REQUEST_PARAM_PUBLICATION_NAME)).thenReturn("name");
        when(request.getParameter(REQUEST_PARAM_THEME_ID)).thenReturn(ID);
        when(request.getParameter(REQUEST_PARAM_TYPE_ID)).thenReturn(ID);
        when(request.getParameter(REQUEST_PARAM_SORT_ID)).thenReturn(ID);
        when(request.getParameter(REQUEST_PARAM_CURRENT_PAGE)).thenReturn(ID);
        when(request.getParameter(REQUEST_PARAM_ITEMS_PER_PAGE)).thenReturn(ID);
        assertNotNull(searchCommand.execute(request, response));
    }
}
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

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddPublicationCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AddPublicationCommand addPublicationCommand;

    @Test(expected = NullPointerException.class)
    public void execute() throws CommandExeption{
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(REQUEST_PARAM_THEME_ID)).thenThrow(new NullPointerException());
        addPublicationCommand.execute(request, response);
    }
}
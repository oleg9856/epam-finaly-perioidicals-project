package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EditThemeCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ThemeService themeService;

    @InjectMocks
    private EditThemeCommand editThemeCommand;

    @Test
    public void testSuccessExecute() throws CommandExeption {
        assertNotNull(editThemeCommand.execute(request, response));
    }
}
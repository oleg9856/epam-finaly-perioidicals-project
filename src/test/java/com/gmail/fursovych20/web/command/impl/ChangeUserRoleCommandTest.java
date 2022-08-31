package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Role;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeUserRoleCommandTest {


    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChangeUserRoleCommand changeUserRoleCommand;

    private static final int ID = 1;
    private static final String ROLE = Role.CUSTOMER.name();
    private static final String HEADER = "header";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(REQUEST_ATTR_USER_ID)).thenReturn(String.valueOf(ID));
        when(request.getParameter(REQUEST_ATTR_ROLE)).thenReturn(ROLE);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        assertNotNull(changeUserRoleCommand.execute(request, response));
    }
}
package com.gmail.fursovych20.web.filter;

import com.gmail.fursovych20.entity.Role;
import com.gmail.fursovych20.web.util.WebConstantDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAccessFilterTest {

    @Mock
    private FilterChain chain;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CustomerAccessFilter customerAccessFilter;

    private static final Role ROLE_ADMIN = Role.ADMIN;
    private static final Role ROLE_CUSTOMER = Role.CUSTOMER;

    @Before
    public void setUp(){
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testUserEqualsNullDoFilter() throws ServletException, IOException {
        customerAccessFilter.doFilter(request, response, chain);
    }

    @Test
    public void testUserEqualsAdminDoFilter() throws ServletException, IOException {
        when(request.getSession().getAttribute(WebConstantDeclaration.SESSION_ATTR_USER_ROLE)).thenReturn(ROLE_ADMIN);
        customerAccessFilter.doFilter(request, response, chain);
    }

    @Test
    public void testUserEqualsCustomerDoFilter() throws ServletException, IOException {
        when(request.getSession().getAttribute(WebConstantDeclaration.SESSION_ATTR_USER_ROLE)).thenReturn(ROLE_CUSTOMER);
        customerAccessFilter.doFilter(request, response, chain);
    }
}
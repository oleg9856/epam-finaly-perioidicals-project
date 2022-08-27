package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.ReviewService;
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

import java.time.LocalDate;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddReviewCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private AddReviewCommand addReviewCommand;

    public static final String  HEADER = "header";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        when(request.getSession().getAttribute(SESSION_ATTR_USER_ID)).thenReturn(1);
        setReview();
        assertNotNull(addReviewCommand.execute(request,response));
    }

    private void setReview() {
        when(request.getParameter(REQUEST_PARAM_REVIEW_TEXT)).thenReturn("text");
        when(request.getParameter(REQUEST_PARAM_REVIEW_MARK)).thenReturn("5");
        when(request.getParameter(REQUEST_PARAM_REVIEW_ID_OF_PUBLICATION)).thenReturn("2");
    }
}
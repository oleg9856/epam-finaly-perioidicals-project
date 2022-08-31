package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.ReviewService;
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
import static com.gmail.fursovych20.web.util.WebConstantDeclaration.REQUEST_PARAM_REVIEW_ID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteReviewCommandTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ReviewService publicationService;

    @InjectMocks
    private DeleteReviewCommand reviewCommand;

    private static final int ID = 1;
    private static final String HEADER = "header";

    @Test
    public void testSuccessExecute() throws CommandExeption {
        when(request.getParameter(REQUEST_PARAM_REVIEW_ID)).thenReturn(String.valueOf(ID));
        when(request.getHeader(REQUEST_HEADER_REFER_PAGE)).thenReturn(HEADER);
        assertNotNull(reviewCommand.execute(request, response));
    }
}
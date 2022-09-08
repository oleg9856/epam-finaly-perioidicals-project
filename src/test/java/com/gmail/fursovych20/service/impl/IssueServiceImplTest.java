package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.IssueDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Subscription;
import com.gmail.fursovych20.entity.SubscriptionStatus;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceImplTest {

    @Mock
    private IssueDAO issueDAO;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PublicationServiceImpl publicationService;

    @InjectMocks
    private IssueServiceImpl issueService;

    private static final int ID = 1;
    private static final Date DATE_OF_PUBLICATION = Date.valueOf(LocalDate.now());
    private static final int PUBLICATION_ID = 1;
    private static final String DESCRIPTION = "description";
    private static final String FILE = "test.txt";
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = LocalDate.of(Year.now().getValue(), START_DATE.getMonthValue()+2, START_DATE.getDayOfMonth());
    private static final double PRICE = 56.5;
    private static final  LocaleType UK_UA = LocaleType.uk_UA;

    private static final SubscriptionStatus STATUS = SubscriptionStatus.ACTIVE;

    private final Issue issue  = getIssue();
    private final Subscription subscription = getSubscription();
    private final List<Issue> issues = new ArrayList<>(List.of(issue));


    @Test
    public void testSuccessFindPublicationBetweenDates() throws DAOException, ServiceException {
        when(issueDAO.findPublicationBetweenDates(PUBLICATION_ID, START_DATE, END_DATE)).thenReturn(issues);
        assertEquals(issues, issueService.findPublicationBetweenDates(subscription));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionFindPublicationBetweenDates() throws DAOException, ServiceException {
        when(issueDAO.findPublicationBetweenDates(PUBLICATION_ID, START_DATE, END_DATE)).thenThrow(new DAOException());
        issueService.findPublicationBetweenDates(subscription);
    }

    @Test
    public void testSuccessFindIssueById() throws DAOException, ServiceException {
        when(issueDAO.findIssueByID(ID)).thenReturn(issue);
        assertEquals(issue, issueService.findIssueById(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionFindIssueById() throws DAOException, ServiceException {
        when(issueDAO.findIssueByID(ID)).thenThrow(new DAOException());
        issueService.findIssueById(ID);
    }

    @Test
    public void testSuccessCreate() throws DAOException, ServiceException {
        when(issueDAO.create(issue)).thenReturn(true);
        assertTrue(issueService.create(issue, UK_UA));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionCreate() throws DAOException, ServiceException {
        when(issueDAO.create(issue)).thenThrow(new DAOException());
        issueService.create(issue, UK_UA);
    }

    private static Subscription getSubscription() {
        return new Subscription.Builder()
                .setId(ID)
                .setUserId(ID)
                .setStatus(STATUS)
                .setPublicationId(PUBLICATION_ID)
                .setStartLocalDate(START_DATE)
                .setEndLocalDate(END_DATE)
                .setPrice(PRICE)
                .build();
    }

    private Issue getIssue() {
        return new Issue.Builder()
                .setId(ID)
                .setPublicationId(ID)
                .setDescription(DESCRIPTION)
                .setLocalDateOfPublication(DATE_OF_PUBLICATION.toLocalDate())
                .setFile(FILE)
                .build();
    }
}
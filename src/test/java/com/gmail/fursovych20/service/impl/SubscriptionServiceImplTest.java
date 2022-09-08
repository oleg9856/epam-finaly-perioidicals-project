package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.PublicationDAO;
import com.gmail.fursovych20.db.dao.SubscriptionDAO;
import com.gmail.fursovych20.db.dao.UserDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.*;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionDAO subscriptionDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    private PublicationService publicationService;

    @Mock
    private PublicationDAO publicationDAO;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private static final int ID = 1;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = LocalDate.of(Year.now().getValue(), START_DATE.getMonthValue()+2, START_DATE.getDayOfMonth());
    private static final double PRICE = 56.5;
    private static final SubscriptionStatus STATUS = SubscriptionStatus.ACTIVE;
    private static final BigDecimal SUM = new BigDecimal("450.6");
    private static final String NAME = "publicationName";
    private static final String DESCRIPTION = "description";
    private static final short THEME_ID = 1;
    private static final short TYPE_ID = 1;
    private static final String PICTURE_PATH = "C:/user/periodical/id/2022";


    private static final Publication PUBLICATION = getPublication();
    private static final Subscription SUBSCRIPTION = getSubscription();
    private static final List<Subscription> SUBSCRIPTIONS = new ArrayList<>(List.of(SUBSCRIPTION));
    private static final LocalizedPublicationDTO LOCALIZED =
           getPublicationDTO();

    @Test
    public void testSuccessFindActiveUserSubscriptionsByUserId() throws DAOException, ServiceException {
        when(subscriptionDAO.findActiveUserSubscriptionsByUserId(ID))
                .thenReturn(SUBSCRIPTIONS);
        assertEquals(SUBSCRIPTIONS, subscriptionService.findActiveUserSubscriptionsByUserId(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsFindActiveUserSubscriptionsByUserId() throws DAOException, ServiceException {
        when(subscriptionDAO.findActiveUserSubscriptionsByUserId(ID))
                .thenThrow(new DAOException());
        subscriptionService.findActiveUserSubscriptionsByUserId(ID);
    }

    @Test
    public void testSuccessFindAllSubscriptionByUserId() throws DAOException, ServiceException {
        when(subscriptionDAO.findAllSubscriptionByUserId(ID))
                .thenReturn(SUBSCRIPTIONS);
        assertEquals(SUBSCRIPTIONS, subscriptionService.findAllSubscriptionByUserId(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionFindAllSubscriptionByUserId() throws DAOException, ServiceException {
        when(subscriptionDAO.findAllSubscriptionByUserId(ID))
                .thenThrow(new DAOException());
        subscriptionService.findAllSubscriptionByUserId(ID);
    }

    @Test
    public void testFailTerminateSubscription() throws ServiceException, DAOException {
        when(subscriptionDAO.findSubscriptionById(ID)).thenReturn(SUBSCRIPTION);
        when(publicationService.findPublicationByIdAndLocale(ID, LocaleType.en_US))
                .thenReturn(PUBLICATION);

        assertFalse(subscriptionService.terminateSubscription(ID));
    }

    @Test
    public void testSuccessFindSubscriptionById() throws DAOException, ServiceException {
        when(subscriptionDAO.findSubscriptionById(ID))
                .thenReturn(SUBSCRIPTION);
        assertEquals(SUBSCRIPTION, subscriptionService.findSubscriptionById(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowsExceptionFindSubscriptionById() throws DAOException, ServiceException {
        when(subscriptionDAO.findSubscriptionById(ID))
                .thenThrow(new DAOException());
        subscriptionService.findSubscriptionById(ID);
    }

    @Test
    public void testCreate() throws DAOException, ServiceException {
        when(publicationService.readLocalized(ID)).thenReturn(LOCALIZED);
        when(userDAO.findUserBalance(ID)).thenReturn(SUM.doubleValue()+40);
        subscriptionService.create(ID,ID, START_DATE, ID);
    }

    private static Subscription getSubscription() {
        return new Subscription.Builder()
                .setId(ID)
                .setUserId(ID)
                .setStatus(STATUS)
                .setPublicationId(ID)
                .setStartLocalDate(START_DATE)
                .setEndLocalDate(END_DATE)
                .setPrice(PRICE)
                .build();
    }

    private static Publication getPublication() {
        return new Publication.Builder()
                .setId(ID)
                .setThemeId(THEME_ID)
                .setTypeId(TYPE_ID)
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setPrice(PRICE)
                .setPicturePath(PICTURE_PATH)
                .build();
    }

    private static LocalizedPublicationDTO getPublicationDTO() {
        return new LocalizedPublicationDTO.Builder()
                .setId(ID)
                .setNames(new EnumMap<>(Map.of(LocaleType.uk_UA, "PublicationNames")))
                .setDescriptions(new EnumMap<>(Map.of(LocaleType.uk_UA, "PublicationText")))
                .setTypeID(TYPE_ID)
                .setThemeId(THEME_ID)
                .setPrice(PRICE)
                .setPicturePath(PICTURE_PATH)
                .build();
    }

}

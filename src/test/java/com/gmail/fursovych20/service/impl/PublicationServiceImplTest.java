package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.PublicationDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PublicationServiceImplTest {

    @Mock
    private PublicationDAO publicationDAO;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private IssueServiceImpl issueService;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    private static final int ID = 1;
    private static final String NAME = "publicationName";
    private static final String DESCRIPTION = "description";
    private static final short THEME_ID = 1;
    private static final short TYPE_ID = 1;
    private static final double PRICE = 300;
    private static final String PICTURE_PATH = "C:/user/periodical/id/2022";
    private static final LocaleType LOCALE = LocaleType.uk_UA;

    private static final Publication PUBLICATION = getPublication();
    private static final PublicationSearchCriteriaDTO CRITERIA =
            getSearchCriteriaDTO();
    private static final LocalizedPublicationDTO LOCALIZED = getPublicationDTO();

    private static final List<Publication> PUBLICATIONS = new ArrayList<>(List.of(PUBLICATION));



    @Test
    public void testSuccessFindAllPublicationByCriteria() throws DAOException, ServiceException {
        when(publicationDAO.findAllPublicationByCriteria(CRITERIA)).thenReturn(PUBLICATIONS);
        assertEquals(PUBLICATIONS, publicationService.findAllPublicationByCriteria(CRITERIA));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionFindAllPublicationByCriteria() throws DAOException, ServiceException {
        when(publicationDAO.findAllPublicationByCriteria(CRITERIA)).thenThrow(new DAOException());
        publicationService.findAllPublicationByCriteria(CRITERIA);
    }

    @Test
    public void testSuccessFindPublicationByIdAndLocale() throws DAOException, ServiceException {
        when(publicationDAO.findPublicationByIdAndLocale(ID, LOCALE)).thenReturn(PUBLICATION);
        assertEquals(PUBLICATION, publicationService.findPublicationByIdAndLocale(ID, LOCALE));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionFindPublicationByIdAndLocale() throws DAOException, ServiceException {
        when(publicationDAO.findPublicationByIdAndLocale(ID, LOCALE)).thenThrow(new DAOException());
        publicationService.findPublicationByIdAndLocale(ID, LOCALE);
    }

    @Test
    public void testSuccessFindPublicationsByNameAndCriteria() throws DAOException, ServiceException {
        when(publicationDAO.findPublicationsByNameAndCriteria(CRITERIA, "%"+NAME+"%")).thenReturn(PUBLICATIONS);
        assertEquals(PUBLICATIONS, publicationService.findPublicationsByNameAndCriteria(CRITERIA, NAME));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionFindPublicationsByNameAndCriteria() throws DAOException, ServiceException {
        when(publicationDAO.findPublicationsByNameAndCriteria(CRITERIA, "%"+NAME+"%")).thenThrow(new DAOException());
        publicationService.findPublicationsByNameAndCriteria(CRITERIA, NAME);
    }

    @Test
    public void testSuccessAddPublication() throws DAOException, ServiceException {
        when(publicationDAO.create(LOCALIZED)).thenReturn(true);
        publicationService.addPublication(LOCALIZED);
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionAddPublication() throws DAOException, ServiceException {
        when(publicationDAO.create(LOCALIZED)).thenThrow(new DAOException());
        publicationService.addPublication(LOCALIZED);
    }

    @Test
    public void testSuccessReadLocalized() throws DAOException, ServiceException {
        when(publicationDAO.readLocalized(ID)).thenReturn(LOCALIZED);
        assertEquals(LOCALIZED, publicationService.readLocalized(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionReadLocalized() throws DAOException, ServiceException {
        when(publicationDAO.readLocalized(ID)).thenThrow(new DAOException());
        publicationService.readLocalized(ID);
    }

    @Test
    public void testSuccessDeletePublication() throws DAOException, ServiceException {
        when(publicationDAO.delete(ID)).thenReturn(true);
        assertTrue(publicationService.deletePublication(ID));
    }

    @Test
    public void testFailDeletePublication() throws DAOException, ServiceException {
        when(publicationDAO.delete(ID)).thenReturn(false);
        assertFalse(publicationService.deletePublication(ID));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionDeletePublication() throws DAOException, ServiceException {
        when(publicationDAO.delete(ID)).thenThrow(new DAOException());
        publicationService.deletePublication(ID);
    }

    @Test
    public void testSuccessUpdate() throws ServiceException, DAOException {
        when(publicationDAO.update(LOCALIZED)).thenReturn(true);
        assertTrue(publicationService.update(LOCALIZED));
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionUpdatePublication() throws DAOException, ServiceException {
        when(publicationDAO.update(LOCALIZED)).thenThrow(new DAOException());
        publicationService.update(LOCALIZED);
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

    private static PublicationSearchCriteriaDTO getSearchCriteriaDTO() {
        return new PublicationSearchCriteriaDTO.Builder()
                .setLocale(LocaleType.uk_UA)
                .setOrderId(ID)
                .setItemsPerPage(ID)
                .setCurrentPage(ID)
                .setTypeId(ID)
                .setPageCount(ID)
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
package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.ThemeDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThemeServiceImplTest {
    @Mock
    private ThemeDAO themeDAO;

    @InjectMocks
    private ThemeServiceImpl themeService;

    private static final short ID = 1;
    private static final String NAME = "themeName";
    private static final LocaleType LOCALE = LocaleType.en_US;
    private static final Theme THEME = new Theme(ID, NAME);

    private static final LocalizedThemeDTO LOCALIZED_THEME = new LocalizedThemeDTO(ID, NAME, new EnumMap<>(Map.of(LOCALE, NAME)));
    private static final List<Theme> THEMES = new ArrayList<>(List.of(THEME));
    private static final List<LocalizedThemeDTO> LOCALIZED_THEMES = List.of(LOCALIZED_THEME);

    @Test
    public void testSuccessFindAllThemeByLocaleType() throws DAOException, ServiceException {
        when(themeDAO.findAll(LOCALE)).thenReturn(THEMES);
        assertEquals(THEMES, themeService.findAllThemeByLocaleType(LOCALE));
    }

    @Test
    public void testSuccessFindThemeById() throws DAOException, ServiceException {
        when(themeDAO.findThemeById((int) ID, LOCALE)).thenReturn(THEME);
        assertEquals(THEME, themeService.findThemeById(ID, LOCALE));
    }

    @Test
    public void testSuccessFindAllLocalized() throws DAOException, ServiceException {
        when(themeDAO.findAllLocalized()).thenReturn(LOCALIZED_THEMES);
        assertEquals(LOCALIZED_THEMES, themeService.findAllLocalized());
    }

    @Test
    public void testSuccessCreate() throws DAOException, ServiceException {
        when(themeDAO.create(LOCALIZED_THEME)).thenReturn(true);
        assertTrue(themeService.create(LOCALIZED_THEME));
    }

    @Test
    public void testSuccessUpdate() throws DAOException, ServiceException {
        when(themeDAO.update(LOCALIZED_THEME)).thenReturn(true);
        assertTrue(themeService.update(LOCALIZED_THEME));
    }
}
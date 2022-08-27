package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ThemeDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private PreparedStatement preparedStatement2;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private ThemeDAOImpl themeDAO;

    private static final String SQL_TEST_ONE = "SELECT themes.id, default_name, locale, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme ORDER BY themes.id";
    private static final String SQL_TEST_TWO = "INSERT INTO `periodicals_website`.`themes` (`default_name`) VALUES (?)";

    private static final int totalReturn = 5;

    private static final short ID = 1;
    private static final String NAME = "themeName";
    private static final LocaleType LOCALE = LocaleType.en_US;
    private static final Theme THEME = new Theme(ID, NAME);

    private static final LocalizedThemeDTO LOCALIZED_THEME = new LocalizedThemeDTO(ID, NAME, new EnumMap<>(Map.of(LOCALE, NAME)));

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void findAll() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setTheme();
        List<Theme> themeFind = themeDAO.findAll(LOCALE);

        assertNotNull(themeFind);
        assertEquals(THEME, themeFind.get(0));
        verify(preparedStatement1).setString(1, LOCALE.name());
    }

    @Test
    public void findThemeById() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setTheme();
        Theme themeFind = themeDAO.findThemeById((int) ID, LOCALE);

        assertNotNull(themeFind);
        assertEquals(THEME, themeFind);
        verify(preparedStatement1).setInt(1, ID);
        verify(preparedStatement1).setString(2, LOCALE.name());
    }

    @Test
    public void findAllLocalized() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setLocalizedTheme();
        List<LocalizedThemeDTO> localized = themeDAO.findAllLocalized();

        assertNotNull(localized);
        assertEquals(LOCALIZED_THEME, localized.get(0));
    }

    @Test
    public void update() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(totalReturn);

        assertTrue(themeDAO.update(LOCALIZED_THEME));
    }

    @Test
    public void create() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_TWO,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);
        when(preparedStatement1.executeUpdate()).thenReturn(totalReturn);
        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getShort(1)).thenReturn(ID);
        when(preparedStatement2.executeUpdate()).thenReturn(totalReturn);

        assertTrue(themeDAO.create(LOCALIZED_THEME));
    }

    private void setTheme() throws SQLException {
        when(resultSet.getShort("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
    }

    private void setLocalizedTheme() throws SQLException {
        when(resultSet.getShort("id")).thenReturn(ID);
        when(resultSet.getString("default_name")).thenReturn(NAME);
        when(resultSet.getString("locale")).thenReturn(LOCALE.name());
        when(resultSet.getString("name")).thenReturn(NAME);
    }
}
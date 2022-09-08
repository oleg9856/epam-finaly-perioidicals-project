package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TypeDAOImplTest {

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
    private TypeDAOImpl typeDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`types` (`default_name`) VALUES (?)";
    private static final String SQL_TEST_TWO = "SELECT types.id, default_name, locale, name FROM types JOIN types_local ON types.id=types_local.id_type ORDER BY types.id";


    private static final LocaleType LOCALE = LocaleType.uk_UA;
    private static final String NAME = "typesName";
    private static final String DEFAULT_NAME = "defaultName";
    private static final short ID = 1;

    private static final Type TYPE = getType();
    private static final LocalizedTypeDTO LOCALIZED_TYPE = getLocalizedTypeDTO();

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testSuccessFindAllType() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true ,false);
        setType();
        List<Type> typeFind = typeDAO.findAllType(LOCALE);

        assertNotNull(typeFind);
        assertEquals(TYPE, typeFind.get(0));
        verify(preparedStatement1).setString(1, LOCALE.name());
    }

    @Test
    public void testSuccessFindTypeById() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setType();
        Type typeFind = typeDAO.findTypeById((int) ID, LOCALE);

        assertNotNull(typeFind);
        assertEquals(TYPE, typeFind);
        verify(preparedStatement1).setInt(1, ID);
        verify(preparedStatement1).setString(2, LOCALE.name());
    }

    @Test
    public void testSuccessFindAllLocalizedTypes() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_TWO
                , ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setLocalizedType();
        List<LocalizedTypeDTO> localizedTypeDTOFind = typeDAO.findAllLocalizedTypes();

        assertEquals(LOCALIZED_TYPE, localizedTypeDTOFind.get(0));
    }

    @Test
    public void testSuccessUpdate() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);

        assertTrue(typeDAO.update(LOCALIZED_TYPE));
    }

    @Test
    public void testSuccessCreate() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getShort(1)).thenReturn(ID);

        assertTrue(typeDAO.create(LOCALIZED_TYPE));
        verify(preparedStatement1).setString(1, LOCALIZED_TYPE.getDefaultName());
        verify(preparedStatement2).setString(2, LOCALE.name());
        verify(preparedStatement2).setString(3, LOCALIZED_TYPE.getLocalizedNames().get(LOCALE));
    }

    private void setType() throws SQLException {
        when(resultSet.getShort("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
    }

    private void setLocalizedType() throws SQLException {
        when(resultSet.getShort("id")).thenReturn(ID);
        when(resultSet.getString("default_name")).thenReturn(DEFAULT_NAME);
        when(resultSet.getString("locale")).thenReturn(LOCALE.name());
        when(resultSet.getString("name")).thenReturn(NAME);
    }

    private static Type getType() {
        return new Type.Builder()
                .setId(ID)
                .setName(NAME)
                .build();
    }

    private static LocalizedTypeDTO getLocalizedTypeDTO() {
        return new LocalizedTypeDTO.Builder()
                .setId(ID)
                .setDefaultName(DEFAULT_NAME)
                .setLocalizedNames(new EnumMap<>(Map.of(LOCALE, NAME)))
                .build();
    }
}
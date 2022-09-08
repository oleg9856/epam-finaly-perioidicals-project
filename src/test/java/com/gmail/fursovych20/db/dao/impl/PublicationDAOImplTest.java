package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
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
public class PublicationDAOImplTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement1;
    @Mock
    private PreparedStatement preparedStatement2;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private PublicationDAOImpl publicationDAO;

    private static final String SQL_TEST_ONE = "INSERT INTO `periodicals_website`.`publications` (`id_theme`, `id_type`, `price`, `picture_path`) VALUES (?, ?, ?, ?)";

    private static final int ID = 1;
    private static final String NAME = "publicationName";
    private static final String DESCRIPTION = "description";
    private static final short THEME_ID = 1;
    private static final short TYPE_ID = 1;
    private static final double PRICE = 300;
    private static final String PICTURE_PATH = "C:/user/periodical/id/2022";
    private static final LocaleType LOCALE = LocaleType.uk_UA;

    private static final Publication publication =
            getPublication();

    private static final PublicationSearchCriteriaDTO CRITERIA =
            getSearchCriteriaDTO();

    private static final LocalizedPublicationDTO LOCALIZED =
            getPublicationDTO();

    @Before
    public void getConnection() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testSuccessFindPublicationByIdAndLocale() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        setPublication();

        Publication publicationFind  = publicationDAO.findPublicationByIdAndLocale(ID, LOCALE);

        assertNotNull(publicationFind);
        assertEquals(publication, publicationFind);
        verify(preparedStatement1).setInt(1, ID);
        verify(preparedStatement1).setString(2, LOCALE.name());
    }
    @Test
    public void testSuccessFindAllPublicationByCriteria() throws SQLException, DAOException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        setPublication();

        List<Publication> publicationFind =
                publicationDAO.findAllPublicationByCriteria(CRITERIA);
        assertNotNull(publicationFind);
        assertEquals(publication, publicationFind.get(0));
    }

    @Test
    public void testSuccessCreate() throws SQLException, DAOException {
        when(connection.prepareStatement(SQL_TEST_ONE,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement1);
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement2);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement2.executeUpdate()).thenReturn(5);
        publicationDAO.create(LOCALIZED);

        verify(preparedStatement1).setShort(1, (short) 1);
        verify(preparedStatement1).setShort(2, (short) 1);
        verify(preparedStatement1).setDouble(3, LOCALIZED.getPrice());
        verify(preparedStatement1).setString(4, LOCALIZED.getPicturePath());
    }

    @Test
    public void testSuccessUpdate() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeUpdate()).thenReturn(5);
        boolean update = publicationDAO.update(LOCALIZED);

        assertTrue(update);
        verify(preparedStatement1).setShort(1, LOCALIZED.getThemeId());
        verify(preparedStatement1).setShort(2, LOCALIZED.getTypeID());
        verify(preparedStatement1).setDouble(3, LOCALIZED.getPrice());
        verify(preparedStatement1).setString(4, LOCALIZED.getPicturePath());
        verify(preparedStatement1).setInt(5, LOCALIZED.getId());
    }

    @Test
    public void testSuccessReadLocalized() throws SQLException, DAOException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement1);
        when(preparedStatement1.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        LOCALIZED.setId(ID);
        setPublicationLocalized();
        LocalizedPublicationDTO localizedPublicationDTO = publicationDAO.readLocalized(ID);

        assertNotNull(localizedPublicationDTO);
        assertEquals(LOCALIZED, localizedPublicationDTO);
        verify(preparedStatement1).setInt(1, ID);
    }

    @Test
    public void testSuccessGetTotalCount() throws SQLException, DAOException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(50);

        int totalCount = publicationDAO.getTotalCount(CRITERIA);

        assertEquals(50, totalCount);
    }

    private void setPublication() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);
        when(resultSet.getShort("id_theme")).thenReturn(THEME_ID);
        when(resultSet.getShort("id_type")).thenReturn(TYPE_ID);
        when(resultSet.getDouble("price")).thenReturn(PRICE);
        when(resultSet.getString("picture_path")).thenReturn(PICTURE_PATH);
    }

    private void setPublicationLocalized() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);
        when(resultSet.getShort("id_theme")).thenReturn(THEME_ID);
        when(resultSet.getShort("id_type")).thenReturn(TYPE_ID);
        when(resultSet.getDouble("price")).thenReturn(PRICE);
        when(resultSet.getString("picture_path")).thenReturn(PICTURE_PATH);

        when(resultSet.getString("locale")).thenReturn(LOCALE.name());
        when(resultSet.getString("name")).thenReturn(LOCALIZED.getNames().get(LOCALE));
        when(resultSet.getString("description")).thenReturn(LOCALIZED.getDescriptions().get(LOCALE));
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
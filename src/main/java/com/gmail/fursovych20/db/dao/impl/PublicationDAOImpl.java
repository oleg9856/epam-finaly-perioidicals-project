package com.gmail.fursovych20.db.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.gmail.fursovych20.db.connectionpool.DBManager;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.PublicationDAO;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

/**
 * PublicationOperationImpl implementation for manipulating publication in the database
 *
 * @author O. Fursovych
 */

public class PublicationDAOImpl implements PublicationDAO {

    private static final Logger LOG = LogManager.getLogger(PublicationDAOImpl.class);
    private final DataSource dataSource;
    private static final String READ_BY_ID_AND_LOCALE = "SELECT id, name, description, id_theme, id_type, price, picture_path FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ? AND locale = ?";
    private static final String READ_LOCALIZED_BY_ID_AND_LOCALE = "SELECT id, name, description, id_theme, id_type, price, picture_path, locale FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ? AND locale = ?";
    private static final String READ_BY_ID = "SELECT id, name, description, id_theme, id_type, price, picture_path, locale FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ?";
    private static final String DELETE_FROM_PERIODICALS = "DELETE FROM periodicals_website.publications WHERE `id`=?";
    private static final String DELETE_FROM_PERIODICALS_LOCAL = "DELETE FROM periodicals_website.publications_local WHERE `id_publication`=?";
    private static final String FIND_USERS_BY_NAME = "SELECT id, name, description, id_theme, id_type, price, picture_path, locale FROM publications JOIN publications_local i ON publications.id = i.id_publication WHERE name LIKE '%s' AND locale = '%s'";
    private static final String READ_ALL_WITH_LOCALE = "SELECT id, name, description, id_theme, id_type, price, picture_path FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE publications_local.locale='%s'";
    private static final String COUNT_PUBLICATIONS = "SELECT COUNT(id) FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE publications_local.locale='%s'";
    private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`publications` (`id_theme`, `id_type`, `price`, `picture_path`) VALUES (?, ?, ?, ?)";
    private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`publications` SET `id_theme`=?, `id_type`=?, `price`=?, `picture_path`=? WHERE `id`=?";
    private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`publications_local` SET `name`=?, `description`=? WHERE `id_publication`=? and`locale`=?";

    private static final String THEME_CLAUSE = " AND id_theme='%d'";
    private static final String TYPE_CLAUSE = " AND id_type='%d'";
    private static final String ORDER_CLAUSE = " ORDER BY %s";
    private static final String LIMIT_CLAUSE = " LIMIT %d,%d";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String ID_THEME = "id_theme";
    private static final String ID_TYPE = "id_type";
    private static final String PRICE = "price";
    private static final String PICTURE_PATH = "picture_path";
    private static final String LOCALE = "locale";
    private static final int ORDER_BY_PRICE = 2;
    private static final int ALL_THEMES = 0;
    private static final int ALL_TYPES = 0;

    public PublicationDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws DAOException {
        Publication publication = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(READ_BY_ID_AND_LOCALE)) {
                ps.setInt(1, id);
                ps.setString(2, locale.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        publication = formPublication(resultSet);
                    }
                    LOG.info("Find a publication by ID: {}, and LocalType: {}", id, locale.name());
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not find the publication", e);
            throw new DAOException("Exception reading publication", e);
        }
        return publication;
    }

    @Override
    public List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws DAOException {
        List<Publication> publications = new ArrayList<>();
        String query = formQuery(criteria);
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        Publication publication = formPublication(resultSet);
                        publications.add(publication);
                    }
                    LOG.info("Find all publications by criteria --> {}", criteria);
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not find publications");
            throw new DAOException("Exception reading publications", e);
        }
        return publications;
    }

    @Override
    public List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws DAOException {
        List<Publication> publications = new ArrayList<>();
        String query = formSearchQuery(criteria, name);
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(query)) {
                    while (rs.next()) {
                        LOG.info("Publication has find successfully");
                        publications.add(formPublication(rs));
                    }
                    return publications;
                }
            }
        } catch (SQLException e) {
            LOG.error("Can`t find publication by name --> {}", name);
            throw new DAOException("Exception find publication", e);
        }
    }

    @Override
    public boolean create(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        ResultSet resultSet = null;
        int resultUpdate;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
            psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setShort(1, (short) 1);
            psMain.setShort(2, (short) 1);
            psMain.setDouble(3, localizedPublicationDTO.getPrice());
            psMain.setString(4, localizedPublicationDTO.getPicturePath());

            resultUpdate = psMain.executeUpdate();

            resultSet = psMain.getGeneratedKeys();
            resultSet.next();
            localizedPublicationDTO.setId(resultSet.getInt(1));

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setInt(1, localizedPublicationDTO.getId());
                psAdditional.setString(2, locale.name());
                psAdditional.setString(3, localizedPublicationDTO.getNames().get(locale));
                psAdditional.setString(4, localizedPublicationDTO.getDescriptions().get(locale));
                psAdditional.executeUpdate();
            }
            LOG.info("Create publication successfully!");
            connection.commit();
        } catch (SQLException e) {
            LOG.error("(SQLException) Can`t create new publication!");
            DBManager.rollback(connection);
            throw new DAOException("Exception creating publication", e);
        } finally {
            DBManager.close(connection, psMain, psAdditional, resultSet);
        }
        return resultUpdate != 0;
    }

    @Override
    public boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        int resultUpdate;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
            psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setShort(1, localizedPublicationDTO.getThemeId());
            psMain.setShort(2, localizedPublicationDTO.getTypeID());
            psMain.setDouble(3, localizedPublicationDTO.getPrice());
            psMain.setString(4, localizedPublicationDTO.getPicturePath());
            psMain.setInt(5, localizedPublicationDTO.getId());

            resultUpdate = psMain.executeUpdate();

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setString(1, localizedPublicationDTO.getNames().get(locale));
                psAdditional.setString(2, localizedPublicationDTO.getDescriptions().get(locale));
                psAdditional.setInt(3, localizedPublicationDTO.getId());
                psAdditional.setString(4, locale.name());
                psAdditional.executeUpdate();
            }

            LOG.info("Update publication successfully!");
            connection.commit();
        } catch (SQLException e) {
            DBManager.rollback(connection);
            throw new DAOException("Exception creating publication", e);
        } finally {
            DBManager.close(connection, psMain, psAdditional);
        }
        return resultUpdate != 0;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(DELETE_FROM_PERIODICALS_LOCAL);
            psMain.setInt(1, id);
            psAdditional = connection.prepareStatement(DELETE_FROM_PERIODICALS);
            psAdditional.setInt(1, id);
            return psMain.executeUpdate() != 0 && psAdditional.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error("Can`t delete publication by id --> {}", id);
            DBManager.rollback(connection);
            throw new DAOException("Exception delete publication by publication id", e);
        } finally {
            DBManager.close(connection, psMain, psAdditional);
        }
    }

    @Override
    public LocalizedPublicationDTO readLocalized(int id) throws DAOException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(READ_BY_ID)) {
                LocalizedPublicationDTO localizedPublicationDTO = null;
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        localizedPublicationDTO = formLocalizedPublication(resultSet);
                    }
                    LOG.info("Read localized publication by Id successfully --> {}", id);
                    return localizedPublicationDTO;
                }
            }
        } catch (SQLException e) {
            LOG.info("Can`t reading localized publication!");
            throw new DAOException("Exception reading localized publication", e);
        }
    }

    @Override
    public LocalizedPublicationDTO readLocalizedWithLocalization(int id, LocaleType localeType) throws DAOException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(READ_LOCALIZED_BY_ID_AND_LOCALE)) {
                LocalizedPublicationDTO localizedPublicationDTO = null;
                ps.setInt(1, id);
                ps.setString(2, localeType.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        localizedPublicationDTO = formLocalizedPublication(resultSet);
                    }
                    LOG.info("Read localized publication by Id and locale successfully --> {}", id);
                    return localizedPublicationDTO;
                }
            }
        } catch (SQLException e) {
            LOG.info("Can`t reading localized publication!");
            throw new DAOException("Exception reading localized publication", e);
        }
    }

    @Override
    public int getTotalCount(PublicationSearchCriteriaDTO criteria) throws DAOException {
        String query = formCountQuery(criteria);
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    resultSet.next();
                    LOG.info("Received total count --> {}", criteria);
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception counting publications", e);
            throw new DAOException("Exception counting publications", e);
        }
    }

    private Publication formPublication(ResultSet resultSet) throws SQLException {
        return new Publication.Builder()
                .setId(resultSet.getInt(ID))
                .setName(resultSet.getString(NAME))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setThemeId(resultSet.getShort(ID_THEME))
                .setTypeId(resultSet.getShort(ID_TYPE))
                .setPrice(resultSet.getDouble(PRICE))
                .setPicturePath(resultSet.getString(PICTURE_PATH))
                .build();
    }

    private LocalizedPublicationDTO formLocalizedPublication(ResultSet resultSet) throws SQLException {
        Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
        Map<LocaleType, String> descriptions = new EnumMap<>(LocaleType.class);
        LocalizedPublicationDTO localizedPublicationDTO;
        do {
            LocaleType locale = LocaleType.valueOf(resultSet.getString(LOCALE));
            String name = resultSet.getString(NAME);
            String description = resultSet.getString(DESCRIPTION);
            names.put(locale, name);
            descriptions.put(locale, description);
            localizedPublicationDTO = new LocalizedPublicationDTO.Builder()
                    .setId(resultSet.getInt(ID))
                    .setThemeId(resultSet.getShort(ID_THEME))
                    .setTypeID(resultSet.getShort(ID_TYPE))
                    .setPrice(resultSet.getDouble(PRICE))
                    .setPicturePath(resultSet.getString(PICTURE_PATH))
                    .setNames(names)
                    .setDescriptions(descriptions)
                    .build();
        } while (resultSet.next());

        return localizedPublicationDTO;
    }

    private String formQuery(PublicationSearchCriteriaDTO criteria) {
        StringBuilder query = new StringBuilder(String.format(READ_ALL_WITH_LOCALE, criteria.getLocale().name()));
        setTheme(query, criteria);
        setType(query, criteria);
        setOrder(query, criteria);
        setLimitCriteria(query, criteria);
        return query.toString();
    }

    private String formSearchQuery(PublicationSearchCriteriaDTO criteria, String name) {
        StringBuilder query = new StringBuilder(String.format(FIND_USERS_BY_NAME, name, criteria.getLocale().name()));
        setTheme(query, criteria);
        setType(query, criteria);
        setOrder(query, criteria);
        setLimitCriteria(query, criteria);
        return query.toString();
    }

    private String formCountQuery(PublicationSearchCriteriaDTO criteria) {
        StringBuilder query = new StringBuilder(String.format(COUNT_PUBLICATIONS, criteria.getLocale().name()));
        setTheme(query, criteria);
        setType(query, criteria);
        return query.toString();
    }

    private void setTheme(StringBuilder query, PublicationSearchCriteriaDTO criteria) {
        if (criteria.getThemeId() != ALL_THEMES) {
            query.append(String.format(THEME_CLAUSE, criteria.getThemeId()));
        }
    }

    private void setType(StringBuilder query, PublicationSearchCriteriaDTO criteria) {
        if (criteria.getTypeId() != ALL_TYPES) {
            query.append(String.format(TYPE_CLAUSE, criteria.getTypeId()));
        }
    }

    private void setOrder(StringBuilder query, PublicationSearchCriteriaDTO criteria) {
        if (criteria.getOrderId() == ORDER_BY_PRICE) {
            query.append(String.format(ORDER_CLAUSE, PRICE));
        } else {
            query.append(String.format(ORDER_CLAUSE, NAME));
        }
    }

    private void setLimitCriteria(StringBuilder query, PublicationSearchCriteriaDTO criteria) {
        int start = criteria.getItemsPerPage() * (criteria.getCurrentPage() - 1);
        query.append(String.format(LIMIT_CLAUSE, start, criteria.getItemsPerPage()));
    }


    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

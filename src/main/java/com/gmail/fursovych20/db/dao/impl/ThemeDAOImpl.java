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

import com.gmail.fursovych20.db.connectionpool.JDBCManager;
import com.gmail.fursovych20.db.dao.ThemeDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;

public class ThemeDAOImpl implements ThemeDAO {

    private static final Logger LOG = LogManager.getLogger(ThemeDAOImpl.class);
    private final DataSource dataSource;
    private static final String READ_ALL_WITH_LOCALE = "SELECT themes.id, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme WHERE locale=?";
    private static final String READ_WITH_LOCALE = "SELECT themes.id, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme WHERE themes.id=? AND locale=?";
    private static final String READ_ALL = "SELECT themes.id, default_name, locale, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme ORDER BY themes.id";
    private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`themes` (`default_name`) VALUES (?)";
    private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (?, ?, ?);";
    private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`themes` SET `default_name`=? WHERE `id`=?";
    private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`themes_local` SET `name`=? WHERE `id_theme`=? and`locale`=?";

    private static final String ID = "id";
    private static final String DEFAULT_NAME = "default_name";
    private static final String LOCALE = "locale";
    private static final String NAME = "name";

    public ThemeDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Theme> findAll(LocaleType locale) throws DAOException {
        List<Theme> themes = new ArrayList<>();
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(READ_ALL_WITH_LOCALE)) {
                ps.setString(1, locale.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        Theme theme = formTheme(resultSet);
                        themes.add(theme);
                    }
                    LOG.info("Find all theme successfully!");
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find all theme!");
            throw new DAOException("Exception reading themes", e);
        }
        return themes;
    }

    @Override
    public Theme findThemeById(Integer id, LocaleType locale) throws DAOException {
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(READ_WITH_LOCALE)) {
                ps.setInt(1, id);
                ps.setString(2, locale.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    Theme theme = null;
                    if (resultSet.next()) {
                        theme = formTheme(resultSet);
                    }
                    LOG.info("Find theme by id successfully --> {}", id);
                    return theme;
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find theme by id", e);
            throw new DAOException("Exception reading theme", e);
        }
    }

    @Override
    public List<LocalizedThemeDTO> findAllLocalized() throws DAOException {
        List<LocalizedThemeDTO> localizedThemeDTOS = new ArrayList<>();
        try(Connection connection = getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(READ_ALL, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {
                try(ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        LocalizedThemeDTO localizedThemeDTO = formLocalisedTheme(resultSet);
                        localizedThemeDTOS.add(localizedThemeDTO);
                    }
                    LOG.info("Find all localized successfully!");
                }
            }
        } catch(SQLException e){
            LOG.error("(SQLException)Can`t find all localized!");
            throw new DAOException("Exception reading localized themes", e);
        }
        return localizedThemeDTOS;
    }


    @Override
    public boolean update(LocalizedThemeDTO localizedThemeDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        int resultUpdate;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
            psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setString(1, localizedThemeDTO.getDefaultName());
            psMain.setInt(2, localizedThemeDTO.getId());

            resultUpdate = psMain.executeUpdate();

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setString(1, localizedThemeDTO.getLocalizedNames().get(locale));
                psAdditional.setInt(2, localizedThemeDTO.getId());
                psAdditional.setString(3, locale.name());
                psAdditional.executeUpdate();
            }
            LOG.info("Update theme successfully!");
            connection.commit();
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t update theme!");
            JDBCManager.rollback(connection);
            throw new DAOException("Exception updating theme", e);
        } finally {
            JDBCManager.close(connection, psMain, psAdditional);
        }
        return resultUpdate != 0;
    }


    @Override
    public boolean create(LocalizedThemeDTO localizedThemeDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        int resultUpdate;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
            psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setString(1, localizedThemeDTO.getDefaultName());

            resultUpdate = psMain.executeUpdate();

            ResultSet resultSet = psMain.getGeneratedKeys();
            resultSet.next();
            localizedThemeDTO.setId(resultSet.getShort(1));

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setInt(1, localizedThemeDTO.getId());
                psAdditional.setString(2, locale.name());
                psAdditional.setString(3, localizedThemeDTO.getLocalizedNames().get(locale));
                psAdditional.executeUpdate();
            }

            LOG.info("Create theme successfully!");
            connection.commit();
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t create theme");
            JDBCManager.rollback(connection);
            throw new DAOException("Exception  theme", e);
        } finally {
            JDBCManager.close(connection, psMain, psAdditional);
        }
        return resultUpdate != 0;
    }

    private Theme formTheme(ResultSet resultSet) throws SQLException {
        Theme theme = new Theme();
        theme.setId(resultSet.getShort(ID));
        theme.setName(resultSet.getString(NAME));
        return theme;
    }

    private LocalizedThemeDTO formLocalisedTheme(ResultSet resultSet) throws SQLException {
        LocalizedThemeDTO localizedThemeDTO = new LocalizedThemeDTO();

        short id = resultSet.getShort(ID);
        localizedThemeDTO.setId(id);
        localizedThemeDTO.setDefaultName(resultSet.getString(DEFAULT_NAME));

        Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
        LocaleType locale = LocaleType.valueOf(resultSet.getString(LOCALE));
        String name = resultSet.getString(NAME);
        names.put(locale, name);

        while (resultSet.next()) {
            short nextId = resultSet.getShort(ID);
            if (id == nextId) {
                locale = LocaleType.valueOf(resultSet.getString(LOCALE));
                name = resultSet.getString(NAME);
                names.put(locale, name);
            } else {
                resultSet.previous();
                break;
            }
        }

        localizedThemeDTO.setLocalizedNames(names);
        return localizedThemeDTO;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

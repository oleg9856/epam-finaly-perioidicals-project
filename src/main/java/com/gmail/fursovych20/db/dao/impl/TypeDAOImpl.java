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
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.TypeDAO;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;


public class TypeDAOImpl implements TypeDAO {

    private static final Logger LOG = LogManager.getLogger(TypeDAOImpl.class);
    private final DataSource dataSource;
    private static final String READ_ALL_WITH_LOCALE = "SELECT types.id, name FROM types JOIN types_local ON types.id=types_local.id_type WHERE locale=?";
    private static final String READ_WITH_LOCALE = "SELECT types.id, name FROM types JOIN types_local ON types.id=types_local.id_type WHERE types.id=? AND locale=?";
    private static final String READ_ALL = "SELECT types.id, default_name, locale, name FROM types JOIN types_local ON types.id=types_local.id_type ORDER BY types.id";
    private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`types` (`default_name`) VALUES (?)";
    private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (?, ?, ?);";
    private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`types` SET `default_name`=? WHERE `id`=?";
    private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`types_local` SET `name`=? WHERE `id_type`=? and`locale`=?";

    private static final String ID = "id";
    private static final String DEFAULT_NAME = "default_name";
    private static final String LOCALE = "locale";
    private static final String NAME = "name";

    public TypeDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Type> findAllType(LocaleType locale) throws DAOException {
        List<Type> types = new ArrayList<>();
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(READ_ALL_WITH_LOCALE)) {
                ps.setString(1, locale.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        Type type = formType(resultSet);
                        types.add(type);
                    }
                    LOG.info("Find all type successfully --> {}", locale.name());
                }
            }
        } catch (SQLException e) {
            LOG.error("Can`t find all type!");
            throw new DAOException("Exception reading types", e);
        }
        return types;
    }

    @Override
    public Type findTypeById(Integer id, LocaleType locale) throws DAOException {
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(READ_WITH_LOCALE)) {
                ps.setInt(1, id);
                ps.setString(2, locale.name());
                try (ResultSet resultSet = ps.executeQuery()) {
                    Type type = null;
                    if (resultSet.next()) {
                        type = formType(resultSet);
                    }
                    LOG.info("Find type by id --> {}", id);
                    return type;
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException) Can`t find type by id");
            throw new DAOException("Exception reading type", e);
        }
    }

    @Override
    public List<LocalizedTypeDTO> findAllLocalizedTypes() throws DAOException {
        List<LocalizedTypeDTO> localizedTypeDTOS = new ArrayList<>();
        try(Connection connection = getConnection()) {
            try(PreparedStatement ps  = connection.prepareStatement(READ_ALL
                    , ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        LocalizedTypeDTO localizedTypeDTO = formLocalisedType(resultSet);
                        localizedTypeDTOS.add(localizedTypeDTO);
                    }
                    LOG.info("Find all localized successfully!");
                    return localizedTypeDTOS;
                }
            }
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t find localized types", e);
            throw new DAOException("Exception reading localized types", e);
        }
    }


    @Override
    public boolean update(LocalizedTypeDTO localizedTypeDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional = null;
        int resultUpdate = 0;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
            psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setString(1, localizedTypeDTO.getDefaultName());
            psMain.setInt(2, localizedTypeDTO.getId());

            resultUpdate = psMain.executeUpdate();

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setString(1, localizedTypeDTO.getLocalizedNames().get(locale));
                psAdditional.setInt(2, localizedTypeDTO.getId());
                psAdditional.setString(3, locale.name());
                psAdditional.executeUpdate();
            }
            LOG.info("Update localizedType successfully --> {}", localizedTypeDTO);
            connection.commit();
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t update localizedType!");
            JDBCManager.rollback(connection);
        } finally {
            JDBCManager.close(connection, psMain, psAdditional);
        }
        return resultUpdate != 0;
    }


    @Override
    public boolean create(LocalizedTypeDTO localizedTypeDTO) throws DAOException {
        Connection connection = null;
        PreparedStatement psMain = null;
        PreparedStatement psAdditional=null;
        ResultSet resultSet = null;
        int resultUpdate;
        try {
            connection = getConnection();
            psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
            psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
            connection.setAutoCommit(false);

            psMain.setString(1, localizedTypeDTO.getDefaultName());

            resultUpdate = psMain.executeUpdate();
            resultSet = psMain.getGeneratedKeys();

            resultSet.next();
            localizedTypeDTO.setId(resultSet.getShort(1));

            for (LocaleType locale : LocaleType.values()) {
                psAdditional.setInt(1, localizedTypeDTO.getId());
                psAdditional.setString(2, locale.name());
                psAdditional.setString(3, localizedTypeDTO.getLocalizedNames().get(locale));
                psAdditional.executeUpdate();
            }
            LOG.info("Create localized type successfully --> {}", localizedTypeDTO);
            connection.commit();
        } catch (SQLException e) {
            LOG.error("(SQLException)Can`t create localized type");
            JDBCManager.rollback(connection);
            throw new DAOException("Exception creating type", e);
        } finally {
            JDBCManager.close(connection, psMain, psAdditional, resultSet);
        }
        return resultUpdate != 0;
    }

    private Type formType(ResultSet resultSet) throws SQLException {
        Type type = new Type();
        type.setId(resultSet.getShort(ID));
        type.setName(resultSet.getString(NAME));
        return type;
    }

    private LocalizedTypeDTO formLocalisedType(ResultSet resultSet) throws SQLException {
        LocalizedTypeDTO localizedTypeDTO = new LocalizedTypeDTO();

        short id = resultSet.getShort(ID);
        localizedTypeDTO.setId(id);
        localizedTypeDTO.setDefaultName(resultSet.getString(DEFAULT_NAME));

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

        localizedTypeDTO.setLocalizedNames(names);
        return localizedTypeDTO;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

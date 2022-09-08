package com.gmail.fursovych20.db.dao.impl;

import com.gmail.fursovych20.db.connectionpool.DBManager;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.IssueDAO;
import com.gmail.fursovych20.entity.Issue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * IssueOperationImpl implementation for manipulating edition in the database
 *
 * @author O. Fursovych
 */

public class IssueDAOImpl implements IssueDAO {

    public static final Logger LOG = LogManager.getLogger(IssueDAOImpl.class);

    private final DataSource dataSource;

    private static final String READ_FOR_PUBLICATION_BETWEEN_DATES = "SELECT id, date_of_publication, publication_id, file, description FROM issues WHERE publication_id=? AND TIMESTAMP(date_of_publication) BETWEEN ? AND ?";
    private static final String READ_ISSUE = "SELECT id, date_of_publication, publication_id, file, description FROM issues WHERE id=?";
    private static final String CREATE_ISSUE = "INSERT INTO `periodicals_website`.`issues` (`date_of_publication`, `publication_id`, `file`, `description`) VALUES (?, ?, ?, ?)";

    private static final String ID = "id";
    private static final String DATE_OF_PUBLICATION = "date_of_publication";
    private static final String PUBLICATION_ID = "publication_id";
    private static final String DESCRIPTION = "description";
    private static final String FILE = "file";

    public IssueDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(Issue issue) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(CREATE_ISSUE, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(issue.getLocalDateOfPublication().atStartOfDay()));
            ps.setInt(2, issue.getPublicationId());
            ps.setString(3, issue.getFile());
            ps.setString(4, issue.getDescription());
            int result = ps.executeUpdate();

            if (result > 0) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                issue.setId(resultSet.getInt(1));
            }
            LOG.info("Issue created successfully!");
            return result != 0;
        } catch (SQLException e) {
            LOG.error("Cannot creating issue", e);
            throw new DAOException("Exception creating issue", e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
    }

    @Override
    public Issue findIssueByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_ISSUE);
            ps.setInt(1, id);

            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                LOG.info("Find issue by ID --> {}", id);
                return formIssue(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Cannot find issue by ID");
            throw new DAOException("Exception reading issue", e);
        } finally {
            DBManager.close(connection, ps, resultSet);
        }
        return null;
    }

    @Override
    public List<Issue> findPublicationBetweenDates(int publicationId, LocalDate startDate, LocalDate endDate) throws DAOException {
        List<Issue> issues = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(READ_FOR_PUBLICATION_BETWEEN_DATES);
            ps.setInt(1, publicationId);
            ps.setTimestamp(2, Timestamp.valueOf(startDate.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(endDate.atStartOfDay()));

            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Issue issue = formIssue(resultSet);
                issues.add(issue);
            }
            LOG.info("Found a post between dates");
        } catch (SQLException e) {
            LOG.error("Can't find publication between dates", e);
            throw new DAOException("Exception reading issues", e);
        }finally {
            DBManager.close(connection, ps, resultSet);
        }
        return issues;
    }

    private Issue formIssue(ResultSet resultSet) throws SQLException {
        Issue issue = new Issue();

        issue.setId(resultSet.getInt(ID));
        issue.setLocalDateOfPublication(resultSet.getDate(DATE_OF_PUBLICATION).toLocalDate());
        issue.setPublicationId(resultSet.getInt(PUBLICATION_ID));
        issue.setDescription(resultSet.getString(DESCRIPTION));
        issue.setFile(resultSet.getString(FILE));

        return issue;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

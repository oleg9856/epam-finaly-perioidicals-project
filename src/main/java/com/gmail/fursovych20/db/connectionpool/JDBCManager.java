package com.gmail.fursovych20.db.connectionpool;

import com.gmail.fursovych20.db.connectionpool.exception.JDBCException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation Connection
 * through DataSource and with ConnectionPool to database
 *
 * @author O.Fursovych
 */

public class JDBCManager {

    private static final Logger LOG = LogManager.getLogger(JDBCManager.class);

    private static DataSource ds;
    private static JDBCManager instance;

    // singleton
    public static synchronized JDBCManager getInstance() {
        if (instance == null)
            instance = new JDBCManager();
        return instance;
    }

    /**
     * Public constructor that initializes DataSource
     * with ConnectionPool
     */
    private JDBCManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/periodicals_website");
            LOG.debug("DataSource is initialize");
        } catch (NamingException e) {
            LOG.error("Error in initialization DataSource", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns a DataSource.
     *
     * @return A DB connection.
     */
    public DataSource getDataSource() {
        return ds;
    }

    /**
     * A method that closes different threads using AutoCloseable
     * and set autocommit in connection
     *
     * @param autoCloseables parameter that is passed for closing
     */

    public static void close(AutoCloseable... autoCloseables) {
        if (autoCloseables != null) {
            try {
                for (AutoCloseable as : autoCloseables) {
                    if (as != null) {
                        if (as instanceof Connection && !((Connection) as).getAutoCommit()) {
                            ((Connection) as).setAutoCommit(true);
                        }
                        as.close();
                    }
                }
            } catch (Exception e) {
                LOG.error("(SQLException) Error! Cannot close connection", e);
                throw new JDBCException(e);
            }
        }
    }

    /**
     * Rollback cancellation of operations
     *
     * @param conn connection that is transmitted for closing
     */
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                LOG.error("(SQLException) Error! Cannot rollback", e);
                throw new JDBCException(e);
            }
        }
    }
}


package ru.job4j.principle_004;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pool with connection rollback.
 */
public class PoolRollback extends BasicDataSource {
    @Override
    public Connection getConnection() throws SQLException {
        return ConnectionRollback.create(super.getConnection());
    }
}

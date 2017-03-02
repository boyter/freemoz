package com.freemoz.app.config;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConfig {
    Connection getConnection() throws SQLException;
}

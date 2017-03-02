package com.freemoz.app.service;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.config.SQLiteDatabaseConfig;

public class Singleton {
    private static IDatabaseConfig databaseConfig = null;

    public static synchronized IDatabaseConfig getDatabaseConfig() {
        if (databaseConfig == null) {
            databaseConfig = new SQLiteDatabaseConfig();
        }

        return databaseConfig;
    }
}

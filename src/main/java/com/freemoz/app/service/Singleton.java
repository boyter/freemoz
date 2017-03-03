package com.freemoz.app.service;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.config.SQLiteDatabaseConfig;
import com.freemoz.app.dao.UserDAO;
import com.freemoz.app.util.Helpers;

public class Singleton {
    private static IDatabaseConfig databaseConfig = null;
    private static UserDAO userDAO = null;
    private static Helpers helpers = null;

    public synchronized static IDatabaseConfig getDatabaseConfig() {
        if (databaseConfig == null) {
            databaseConfig = new SQLiteDatabaseConfig();
        }

        return databaseConfig;
    }

    public synchronized static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }

        return userDAO;
    }

    public synchronized static Helpers getHelpers() {
        if (helpers == null) {
            helpers = new Helpers();
        }

        return helpers;
    }
}

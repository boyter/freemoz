package com.freemoz.app.service;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.config.SQLiteDatabaseConfig;
import com.freemoz.app.config.Values;
import com.freemoz.app.dao.ContentDAO;
import com.freemoz.app.dao.UserDAO;
import com.freemoz.app.util.Helpers;
import com.freemoz.app.util.Properties;

public class Singleton {
    private static IDatabaseConfig userDatabaseConfig = null;
    private static IDatabaseConfig contentDatabaseConfig = null;
    private static UserDAO userDAO = null;
    private static ContentDAO contentDAO = null;
    private static Helpers helpers = null;

    public synchronized static IDatabaseConfig getUserDatabaseConfig() {
        if (userDatabaseConfig == null) {
            String sqliteFile = (String) Properties.getProperties().getOrDefault(Values.USER_SQLITE_FILE, Values.DEFAULT_USER_SQLITE_FILE);
            userDatabaseConfig = new SQLiteDatabaseConfig(sqliteFile);
        }

        return userDatabaseConfig;
    }

    public synchronized static IDatabaseConfig getContentDatabaseConfig() {
        if (contentDatabaseConfig == null) {
            String sqliteFile = (String) Properties.getProperties().getOrDefault(Values.CONTENT_SQLITE_FILE, Values.DEFAULT_CONTENT_SQLITE_FILE);
            contentDatabaseConfig = new SQLiteDatabaseConfig(sqliteFile);
        }

        return contentDatabaseConfig;
    }

    public synchronized static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }

        return userDAO;
    }

    public synchronized static ContentDAO getContentDAO() {
        if (contentDAO == null) {
            contentDAO = new ContentDAO();
        }

        return contentDAO;
    }

    public synchronized static Helpers getHelpers() {
        if (helpers == null) {
            helpers = new Helpers();
        }

        return helpers;
    }
}

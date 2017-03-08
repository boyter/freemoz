package com.freemoz.app.dao;

import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentDAO {
    private IDatabaseConfig dbConfig;
    private Helpers helpers;

    public ContentDAO() {
        this(Singleton.getContentDatabaseConfig(), Singleton.getHelpers());
    }

    public ContentDAO(IDatabaseConfig dbConfig, Helpers helpers) {
        this.dbConfig = dbConfig;
        this.helpers = helpers;
    }

    public List<String> getSubcategories(String category) {
        List<String> categories = new ArrayList<>();

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("select * from structure where parentid = (select id from structure where topic = ? limit 1);");
            preparedStatement.setString(1, category);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(resultSet.getString("topic"));
            }
        }
        catch (SQLException ex) {
        }
        finally {
            this.helpers.closeQuietly(resultSet);
            this.helpers.closeQuietly(preparedStatement);
        }

        return categories;
    }
}

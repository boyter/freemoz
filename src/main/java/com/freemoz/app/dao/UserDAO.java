package com.freemoz.app.dao;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.dto.UserDTO;
import com.freemoz.app.dto.UserDTOData;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.Helpers;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private IDatabaseConfig dbConfig;
    private Helpers helpers;

    public UserDAO() {
        this(Singleton.getUserDatabaseConfig(), Singleton.getHelpers());
    }

    public UserDAO(IDatabaseConfig dbConfig, Helpers helpers) {
        this.dbConfig = dbConfig;
        this.helpers = helpers;
    }

    public synchronized UserDTO getUserByUsername(String username) {
        UserDTO userDTO = null;
        Gson gson = new Gson();

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("select username, password, data from \"user\" where username = ?;");
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userDTO = new UserDTO(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    gson.fromJson(resultSet.getString("data"), UserDTOData.class)
                );
            }
        }
        catch(SQLException ex) {

        }
        finally {
            this.helpers.closeQuietly(resultSet);
            this.helpers.closeQuietly(preparedStatement);
        }

        return userDTO;
    }

    public synchronized void createTableIfMissing() {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("CREATE TABLE  IF NOT EXISTS \"main\".\"user\" (\"username\" VARCHAR PRIMARY KEY  NOT NULL , \"password\" VARCHAR NOT NULL , \"data\" TEXT);");
            preparedStatement.execute();

        }
        catch(SQLException ex) {
        }
        finally {
            this.helpers.closeQuietly(resultSet);
            this.helpers.closeQuietly(preparedStatement);
        }
    }
}

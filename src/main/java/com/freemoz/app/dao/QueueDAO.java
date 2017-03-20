package com.freemoz.app.dao;


import com.freemoz.app.config.IDatabaseConfig;
import com.freemoz.app.config.Values;
import com.freemoz.app.dto.SubmissionDTO;
import com.freemoz.app.dto.UserDTO;
import com.freemoz.app.service.Singleton;
import com.freemoz.app.util.Helpers;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueueDAO {

    private IDatabaseConfig dbConfig;
    private Helpers helpers;
    private Gson gson;

    public QueueDAO() {
        this(Singleton.getSubmissionDatabaseConfig(), Singleton.getHelpers(), new Gson());
    }

    public QueueDAO(IDatabaseConfig dbConfig, Helpers helpers, Gson gson) {
        this.dbConfig = dbConfig;
        this.helpers = helpers;
        this.gson = gson;
    }

    public synchronized SubmissionDTO getNextSubmission(UserDTO userDTO) {
        SubmissionDTO submissionDTO = null;
        int submissionInt = -1;

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("SELECT id,bucket,user,timeout,status,data FROM submission WHERE status = 0 AND timeout < ? AND bucket = ? LIMIT 1;");

            preparedStatement.setLong(1, System.currentTimeMillis());
            preparedStatement.setString(2, "A");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                submissionInt = resultSet.getInt("id");
                submissionDTO = gson.fromJson(resultSet.getString("data"), SubmissionDTO.class);
            }
        }
        catch(SQLException ex) {
        }
        finally {
            this.helpers.closeQuietly(preparedStatement);
            this.helpers.closeQuietly(resultSet);
        }

        if (submissionDTO != null) {
            boolean lockedSubmission = this.lockSubmission(submissionInt, userDTO);

            if (lockedSubmission == false) {
                return null;
            }
        }

        return submissionDTO;
    }

    private synchronized boolean lockSubmission(int submissionInt, UserDTO userDTO) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE \"submission\" SET \"user\" = ?, \"timeout\" = ? WHERE  \"id\" = ?;");

            preparedStatement.setString(1, userDTO.getUsername());
            preparedStatement.setLong(2, System.currentTimeMillis() + Values.QUEUE_LOCK_TIMEOUT);
            preparedStatement.setInt(3, submissionInt);
            preparedStatement.execute();
        }
        catch(SQLException ex) {
            return false;
        }
        finally {
            this.helpers.closeQuietly(preparedStatement);
        }

        return true;
    }

    public synchronized boolean addSubmission(SubmissionDTO submissionDTO) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO \"main\".\"submission\" (\"bucket\", \"user\",\"timeout\",\"status\",\"data\") VALUES (?,'',?,?,?)");
            preparedStatement.setString(1, "A");
            preparedStatement.setLong(2, System.currentTimeMillis());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, gson.toJson(submissionDTO));
            preparedStatement.execute();
        }
        catch(SQLException ex) {
            return false;
        }
        finally {
            this.helpers.closeQuietly(preparedStatement);
        }

        return true;
    }

    public synchronized void createTableIfMissing() {
        this.createSubmissionTableIfMissing();
    }

    public synchronized void createSubmissionTableIfMissing() {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.dbConfig.getConnection();
            preparedStatement = connection.prepareStatement("CREATE TABLE \"submission\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"bucket\" VARCHAR NOT NULL , \"user\" VARCHAR, \"timeout\" LONG NOT NULL , \"status\" INTEGER NOT NULL , \"data\" TEXT NOT NULL );");
            preparedStatement.execute();
        }
        catch(SQLException ex) {}
        finally {
            this.helpers.closeQuietly(preparedStatement);
        }
    }
}

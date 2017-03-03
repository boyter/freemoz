package com.freemoz.app.util;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Helpers {

    public boolean isNullOrWhitespace(String test) {
        if (test == null) {
            return true;
        }

        if (test.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public void closeQuietly(ResultSet resultSet) {
        try {
            resultSet.close();
        }
        catch (Exception ex) {}
    }

    public void closeQuietly(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        }
        catch (Exception ex) {}
    }

    public void closeQuietly(Connection connection) {
        try {
            connection.close();
        }
        catch (Exception ex) {}
    }

    public void closeQuietly(Process process) {
        try {
            process.destroy();
        }
        catch (Exception ex) {}
    }

    public void closeQuietly(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        }
        catch (Exception ex) {}
    }
}

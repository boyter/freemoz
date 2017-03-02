package com.freemoz.app.util;

import java.sql.PreparedStatement;

public class Helpers {
    public static void closeQuietly(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        }
        catch (Exception ex) {}
    }
}

package com.freemoz.app.config;

public class Values {
    public static final String PROPERTIES_FILE_NAME = "freemoz.properties";

    // User Database
    public static final String USER_SQLITE_FILE = "user_sqlite_file";
    public static final String DEFAULT_USER_SQLITE_FILE = "freemoz-user.sqlite";

    // Content Database
    public static final String CONTENT_SQLITE_FILE = "content_sqlite_file";
    public static final String DEFAULT_CONTENT_SQLITE_FILE = "freemoz-content.sqlite";

    public static final String SERVER_PORT = "server_port";
    public static final int DEFAULT_SERVER_PORT = 8080;
    public static final String SERVER_TYPE = "server_type";
    public static final String DEFAULT_SERVER_TYPE = "authoring";


    public static final String INDEX_LOCATION = "index_location";
    public static final String DEFAULT_INDEX_LOCATION = "./index/";

    public static final String EMPTY_STRING = "";


    // Used for indexing
    public static final String PATH = "path";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
}

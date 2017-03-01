package com.freemoz.app.util;


import com.freemoz.app.config.Values;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Small helper used to load the properties file
 */
public class Properties {

    private static java.util.Properties properties = null;

    public static synchronized java.util.Properties getProperties() {
        if (properties == null) {
            properties = new java.util.Properties();
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(Values.PROPERTIES_FILE_NAME);
                properties.load(fileInputStream);
            } catch (IOException ex) {}
            finally {
                IOUtils.closeQuietly(fileInputStream);
            }
        }

        return properties;
    }
}

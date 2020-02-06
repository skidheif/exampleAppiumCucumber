package com.test.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    Properties envProperties = new Properties();

    public static PropertyReader instance;

    InputStream inputStreamConfig;


    public PropertyReader () {
        getEnvironment();
    }

    private void getEnvironment () {
        try {
            inputStreamConfig = PropertyReader.class.getResourceAsStream("/properties/config.properties");
            envProperties.load(inputStreamConfig);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String readProperty ( String key ) {
        return envProperties.getProperty(key);
    }
}

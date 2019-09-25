package com.jtljia.gwote.util;


import com.jtljia.gwote.controller.GetWorkOverTime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
    public static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(GetWorkOverTime.class.getResourceAsStream("/url.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}

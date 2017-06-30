/**
 * Copyright(C) 2016  Công ty cổ phần phần mềm Luvina
 * ConfigProperties.java, Aug 7, 2016 LA16
 */
package net.luvina.manageinsurances.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class đọc file config.properties
 * @author LA-AM
 *
 */
@SuppressWarnings("unchecked")
public class ConfigProperties {
	static private Map<String, String> data = new HashMap<String, String>();

    static {
        Properties prop = new Properties();
        try {
            prop.load(ConfigProperties.class.getResourceAsStream(("/config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<String> en  = (Enumeration<String>)prop.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String)en.nextElement();
            data.put(key, prop.getProperty(key));
        }
    }



    /**
     * getData from file properties
     * @param key key
     * @return String
     */
    static public String getMessage(String key) {
        String string = "";
        if (data.containsKey(key)) {
            string = data.get(key);
        }
        return string;
    }
}

package com.qa.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    static {
        try {
            FileInputStream fis = new FileInputStream("config/config.properties");
            props.load(fis);
            log.info("config.properties loaded successfully");
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("config.properties not found", e);
        }
    }

    public static String get(String key) {
        // System property overrides config file (useful for CI/CD)
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }
        value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }
}

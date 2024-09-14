package com.pandora.esUtils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public class Config {
    private static Logger LOG = LoggerFactory.getLogger(Config.class);

    public static String[] esHost;
    public static int esPort;
    public static String baseToken;
    public static String[] indices;
    public static String env;

    private static String esUser;
    private static String esPassword;

    static {
        Properties properties = new Properties();

        try {
            env = System.getProperty("env");
            indices = System.getProperty("indices").split(",");
            properties.load(Config.class.getClassLoader().getResourceAsStream(env + "/config.properties"));
            esHost = properties.getProperty("es.host").split(",");
            esPort = Integer.parseInt(properties.getProperty("es.port"));
            esUser = properties.getProperty("es.user");
            esPassword = properties.getProperty("es.password");
            baseToken = "Basic" + Base64.getEncoder().encodeToString((esUser + ":" + esPassword).getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            LOG.error("init properties failure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

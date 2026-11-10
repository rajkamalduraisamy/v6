package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getBrowser()      { return properties.getProperty("browser"); }
    public static String getAppUrl()       { return properties.getProperty("app.url"); }
    public static String getUsername()     { return properties.getProperty("app.username"); }
    public static String getPassword()     { return properties.getProperty("app.password"); }
    public static int getExplicitWait()    { return Integer.parseInt(properties.getProperty("explicit.wait", "10")); }
}

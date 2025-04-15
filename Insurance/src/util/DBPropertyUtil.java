package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {
    private Properties properties = new Properties();

   
    public DBPropertyUtil(String propertiesFilePath) {
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        } catch (IOException ex) {
            System.err.println("Error loading properties file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

  
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

   
    public String getUrl() {
        return getProperty("db.url");
    }

    
    public String getUser() {
        return getProperty("db.user");
    }

   
    public String getPassword() {
        return getProperty("db.password");
    }
}

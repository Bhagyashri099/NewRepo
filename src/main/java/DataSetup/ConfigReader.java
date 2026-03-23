package DataSetup;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    public static Properties properties;

    // STEP 1: Add this Constructor to your class
    public ConfigReader() {
        if (properties == null) {
            loadProperties();
        }
    }

    public static void loadProperties() {
        try {
            properties = new Properties();
            // System.getProperty("user.dir") to avoid path errors
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Could not load config file at: " + System.getProperty("user.dir"));
        }
    }

   
    // 
    public String getExecutionEnv() {
        return properties.getProperty("execution_env");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getRemoteUrl() {
        return properties.getProperty("remote_url");
    }


//	    public String getBrowser() {
//	        
//	            String browserName = properties.getProperty("browser");
//	            if (browserName != null) {
//	                return browserName;
//	            } else {
//	                // Provide a clear error message if the property is missing
//	                throw new RuntimeException("browser property not specified in the config.properties file.");
//	            }
    
    public boolean getHeadless() {
        String headless = properties.getProperty("headless");
        return Boolean.parseBoolean(headless); // Returns true 
    }
	    }
	    
	    
	



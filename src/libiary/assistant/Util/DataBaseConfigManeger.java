package libiary.assistant.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class DataBaseConfigManeger {

    public void saveDatabaseProperty(DataBaseProperty dbProperty) {

        Properties prop = new Properties();

        try (OutputStream os = new FileOutputStream("dbConfig.properties")) {

            prop.setProperty("host", dbProperty.getHost());
            prop.setProperty("port", dbProperty.getPort());
            prop.setProperty("username", dbProperty.getUser());
            prop.setProperty("password", dbProperty.getPassword());

            prop.store(os, "for database Configuration");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public DataBaseProperty getDatabaseProperry() {

        Properties prop = new Properties();
        
        DataBaseProperty dbProperty = null;

        try (InputStream is = new FileInputStream("dbConfig.properties")) {

            prop.load(is);
            
            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String user = prop.getProperty("username");
            String password = prop.getProperty("password");
            
            dbProperty = new DataBaseProperty(host, port, user, password);
            

        } catch (IOException e) {
            e.printStackTrace();

        }
        return dbProperty;

    }

}

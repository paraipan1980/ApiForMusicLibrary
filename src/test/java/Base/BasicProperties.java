package Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BasicProperties {

    public Properties properties;

    //set the constructor to read my .properties file
    public BasicProperties() {
        try {
            properties = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/Config/config.properties");
            properties.load(ip);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

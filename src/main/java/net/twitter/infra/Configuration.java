package net.twitter.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
@NoArgsConstructor
public class Configuration {

    private static Configuration instance = null;
    private String auth0ClientId = "cli3nt";
    private String auth0ClientSecret = "s3cret";
    private String auth0Domain = "test.auth0";
    private String requiredScope;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private int dbMinPoolSize = 5;
    private int dbMaxPoolSize = 25;
    private long dbConnectionIdleTimeout = 30000L;

    public static Configuration instance() {
        if (instance == null) {
            File file = new File("appconfig.yml");
            if (file.exists()) {
                ObjectMapper om = new ObjectMapper(new YAMLFactory());
                om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                try {
                    instance = om.readValue(file, Configuration.class);
                } catch (IOException ex) {
                    Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                instance = new Configuration();
            }
        }
        return instance;
    }
}

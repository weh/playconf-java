package helpers;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.ServerConfigStartup;

/**
 * Created by patrik on 17.06.2014.
 */
public class TestDbConfigStartup implements ServerConfigStartup {
    @Override
    public void onStart(ServerConfig serverConfig) {
        serverConfig.setDefaultServer(true);
    }
}

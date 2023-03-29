package configuration;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class EnvironmentProperty{

    private final String APP_ENV;
    PropertyStore p = new PropertyStore();

    public static EnvironmentProperty getInstance() {
        return EnvironmentProperty.EnvironmentPropertySingleton.INSTANCE;
    }

    private EnvironmentProperty() {
        this.APP_ENV = "environment";
        new BrowserEnvironment();
        this.initEnv();
    }

    private void initEnv() {
        if (!this.APP_ENV.isEmpty()) {
            setSystemProperties(this.APP_ENV);
        } else {
            log.error("Please provide APP_ENV name");
            System.exit(0);
        }
    }

    private void setSystemProperties(String env) {
        Map<String, Object> config = p.readConfigFile();
        Map<String, Object> environment = (Map<String, Object>) config.get(env);
        String defaultEnvironment = config.get("default environment").toString();
        log.debug(" >>>>>>>>>>>>>>>>>>>>> Environmental name: " + defaultEnvironment);
        if (environment != null && environment.containsKey(defaultEnvironment)) {
            Map<String, Object> defaultEnv = (Map<String, Object>) environment.get(defaultEnvironment);
            defaultEnv.forEach((key, value) -> System.setProperty(key, value.toString()));
            log.debug(" >>>>>>>>>>>>>>>>>>>>> Properties loaded for load for " + defaultEnvironment);
        }
    }

    private static class EnvironmentPropertySingleton {
        private static final EnvironmentProperty INSTANCE = new EnvironmentProperty();
    }

}


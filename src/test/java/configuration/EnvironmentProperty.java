package configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;


@Slf4j
public class EnvironmentProperty {

    private final String APP_ENV;

    public static EnvironmentProperty getInstance() {
        return EnvironmentProperty.EnvironmentPropertySingleton.INSTANCE;
    }

    private EnvironmentProperty() {
        this.APP_ENV = initAppEnv();
        BrowserEnvironment BROWSER_ENV = new BrowserEnvironment();
        this.initEnv();
    }

    private static String initAppEnv() {
        return PropertyStore.ENVIRONMENT.isSpecified() ? PropertyStore.ENVIRONMENT.getValue() : "";
    }

    private void initEnv() {
        if (!this.APP_ENV.isEmpty()) {
            log.debug(" >>>>>>>>>>>>>>>>>>>>> Environmental name: " + this.APP_ENV);
            loadAllEnvPropertiesToSystem(this.APP_ENV);
        } else {
            log.error("Please provide env name");
            System.exit(0);
        }
    }

    private void loadAllEnvPropertiesToSystem(String envName) {
        setSystemPropertiesFromPathUrl(envName);
    }

    private void setSystemPropertiesFromPathUrl(String envName) {
        URL url = EnvironmentProperty.class.getClassLoader().getResource(envName);
        if (url != null) {
            Properties environmentProperties = new Properties();
            try {
                Stream<Path> files = Files.walk(Paths.get(url.toURI()));
                try {
                    for (Path path : files.filter(Files::isRegularFile).toList()) {
                        try {
                            environmentProperties.load(new FileInputStream(path.toString()));
                        } catch (IOException var3) {
                            log.error("error 1");
                        }
                    }
                } catch (Exception e) {
                    log.error("error 2");

                } finally {
                    if (files != null) {
                        try {
                            files.close();
                        } catch (Throwable var13) {
                            log.error("error 3");
                        }
                    } else {
                        files.close();
                    }
                }
            } catch (Exception r) {
                log.error("error 4");
            }
            log.debug("#### Loading property from url {}", url);
            environmentProperties.forEach((propertyName, propertyValue) -> {
                if (System.getProperty(propertyName.toString()) == null) {
                    System.setProperty(propertyName.toString(), propertyValue.toString());
                    log.debug("****Loading environment property {} = {} ", propertyName, propertyValue);
                }
            });
            log.debug("#### Properties loaded from {} : {} ", envName, environmentProperties.size());
        } else {
            log.warn("No such property directory '{}' present in the resources ,make sure you are providing correct directory name.", envName);
        }
    }

    private static class EnvironmentPropertySingleton {
        private static final EnvironmentProperty INSTANCE = new EnvironmentProperty();
    }

}

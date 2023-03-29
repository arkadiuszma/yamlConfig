package tests;

import configuration.BrowserEnvironment;
import configuration.EnvironmentProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

@Slf4j
public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    protected void setupDriver() {
        EnvironmentProperty.getInstance();
        BrowserEnvironment browserEnvironment = new BrowserEnvironment();
        driver = browserEnvironment.getDriver();
        log.debug("Driver initialized properly");
    }

    @AfterEach
    protected void tearDown() {
        driver.quit();
        log.info("Browser was closed.");
    }
}

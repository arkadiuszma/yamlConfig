package browser;

import configuration.PropertyStore;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

@Slf4j
public class BrowserEnvironment {
    private String browser;
    private boolean headless;
    private int timeout;
    private boolean screenshots;
    private WebDriver driver;


    public BrowserEnvironment() {
        this.browser = "chrome";
        this.headless = false;
        this.timeout = 5;
        this.screenshots = false;
        this.initBrowserSettings();
        this.getDriver();
    }

    private void initBrowserSettings() {
        this.browser = PropertyStore.BROWSER.isSpecified() ? PropertyStore.BROWSER.getValue() : this.browser;
        this.timeout = PropertyStore.TIMEOUT.isSpecified() ? PropertyStore.TIMEOUT.getIntValue() : this.timeout;
        this.screenshots = PropertyStore.SCREENSHOTS.isSpecified() ? PropertyStore.SCREENSHOTS.getBooleanValue() : this.screenshots;
        this.headless = PropertyStore.HEADLESS.isSpecified() ? PropertyStore.HEADLESS.getBooleanValue() : this.headless;
    }
    public WebDriver getDriver(){
        switch(this.browser){
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                driver.get(System.getProperty("url"));
            }
            case "firefox" -> {
                driver = new FirefoxDriver();
                driver.get(System.getProperty("url"));
                driver.manage().window().maximize();
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("start-maximized");
                driver = new EdgeDriver(options);
                driver.get(System.getProperty("url"));
            }
        }
        return driver;
    }
}

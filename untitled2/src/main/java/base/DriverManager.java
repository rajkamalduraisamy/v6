package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

public class DriverManager {

    private static WebDriver driver;

    public static void initDriver() {
        String browser = ConfigReader.getBrowser();
        if (browser.equalsIgnoreCase("chrome")) {
            String driverPath = System.getProperty("user.dir") + "/drivers/chromedriver";
            System.setProperty("webdriver.chrome.driver", driverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().deleteAllCookies();
        } else {
            throw new RuntimeException("Browser '" + browser + "' not supported. Use 'chrome'.");
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

package TestBases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * TestBase class provides basic setup and tear down methods for WebDriver initialization and termination.
 * It is configured to run tests using different browsers and Selenium Grid based on the `browserType` and `useGrid` properties.
 */
public class CashCollections_TestBase {

    public static WebDriver driver;

    // Define browser type here (e.g., "chrome", "headless-chrome", "firefox", "ie")
    private static final String BROWSER_TYPE = "chrome";

    // Set to true to use Selenium Grid, false to use local browser
    private static final boolean USE_GRID = false;

    // URL of the Selenium Grid Hub
    private static final String GRID_URL = "http://192.168.2.45:30240/wd/hub";

    // Path to WebDriver binaries
    private static final String CHROME_DRIVER_PATH = "E:\\Automation Projects\\Egyptian_Post_Authority_Automation_Project\\drivers\\chromedriver.exe";
    private static final String GECKO_DRIVER_PATH = "E:\\Automation Projects\\Egyptian_Post_Authority_Automation_Project\\drivers\\geckodriver.exe";
    private static final String IE_DRIVER_PATH = "E:\\Automation Projects\\Egyptian_Post_Authority_Automation_Project\\drivers\\IEDriverServer.exe";

    @BeforeClass
    public void startDriver() {
        try {
            if (USE_GRID) {
                // Use Selenium Grid
                switch (BROWSER_TYPE.toLowerCase()) {
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        driver = new RemoteWebDriver(new URL(GRID_URL), firefoxOptions);
                        break;

                    case "ie":
                        InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                        ieOptions.ignoreZoomSettings();
                        ieOptions.enablePersistentHovering();
                        driver = new RemoteWebDriver(new URL(GRID_URL), ieOptions);
                        break;

                    case "headless-chrome":
                        ChromeOptions headlessChromeOptions = new ChromeOptions();
                        headlessChromeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox");
                        driver = new RemoteWebDriver(new URL(GRID_URL), headlessChromeOptions);
                        break;

                    case "chrome":
                    default:
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--start-maximized", "--disable-infobars", "--disable-extensions");
                        driver = new RemoteWebDriver(new URL(GRID_URL), chromeOptions);
                        break;
                }
            } else {
                // Use local browser
                switch (BROWSER_TYPE.toLowerCase()) {
                    case "firefox":
                        System.setProperty("webdriver.gecko.driver", GECKO_DRIVER_PATH);
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        driver = new FirefoxDriver(firefoxOptions);
                        break;

                    case "ie":
                        System.setProperty("webdriver.ie.driver", IE_DRIVER_PATH);
                        InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                        ieOptions.ignoreZoomSettings();
                        ieOptions.enablePersistentHovering();
                        driver = new InternetExplorerDriver(ieOptions);
                        break;

                    case "headless-chrome":
                        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                        ChromeOptions headlessChromeOptions = new ChromeOptions();
                        headlessChromeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox");
                        driver = new ChromeDriver(headlessChromeOptions);
                        break;

                    case "chrome":
                    default:
                        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--start-maximized", "--disable-infobars", "--disable-extensions");
                        driver = new ChromeDriver(chromeOptions);
                        break;
                }
            }

            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
            driver.navigate().to("http://172.26.2.115:7000/Teller_Cement/pages/login.aspx");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Selenium Grid Hub URL", e);
        }
    }

    @AfterClass
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}

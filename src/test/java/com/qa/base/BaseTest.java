package com.qa.base;

import com.qa.utils.ConfigReader;
import com.qa.utils.ReportManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        log.info("=== Setting up Appium session ===");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platformName"));
        options.setPlatformVersion(ConfigReader.get("platformVersion"));
        options.setDeviceName(ConfigReader.get("deviceName"));
        options.setAppPackage(ConfigReader.get("appPackage"));
        options.setAppActivity(ConfigReader.get("appActivity"));
        options.setApp(Paths.get(ConfigReader.get("appPath")).toAbsolutePath().toString());
        options.setNoReset(false);
        options.setAutoGrantPermissions(true);

        String serverUrl = ConfigReader.get("appiumServerURL");
        driver = new AndroidDriver(new URL(serverUrl), options);

        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(ConfigReader.get("implicitWait")))
        );

        log.info("Driver initialised. Device: {}", ConfigReader.get("deviceName"));
        ReportManager.createTest(getClass().getSimpleName());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("=== Appium session closed ===");
        }
        ReportManager.flushReports();
    }
}

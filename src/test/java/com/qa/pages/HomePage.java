package com.qa.pages;

import com.qa.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class HomePage {

    private final AndroidDriver driver;
    private static final Logger log = LogManager.getLogger(HomePage.class);

    @AndroidFindBy(id = "com.example.demoapp:id/tv_welcome_message")
    private WebElement welcomeMessage;

    @AndroidFindBy(id = "com.example.demoapp:id/bottom_nav_home")
    private WebElement homeNavItem;

    @AndroidFindBy(id = "com.example.demoapp:id/bottom_nav_search")
    private WebElement searchNavItem;

    @AndroidFindBy(id = "com.example.demoapp:id/bottom_nav_profile")
    private WebElement profileNavItem;

    @AndroidFindBy(id = "com.example.demoapp:id/toolbar_title")
    private WebElement toolbarTitle;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    public boolean isHomePageDisplayed() {
        WaitUtils.waitForVisibility(driver, welcomeMessage);
        return welcomeMessage.isDisplayed();
    }

    public String getWelcomeMessage() {
        WaitUtils.waitForVisibility(driver, welcomeMessage);
        return welcomeMessage.getText();
    }

    public String getToolbarTitle() {
        return toolbarTitle.getText();
    }

    public SearchPage navigateToSearch() {
        log.info("Navigating to Search");
        searchNavItem.click();
        return new SearchPage(driver);
    }

    public ProfilePage navigateToProfile() {
        log.info("Navigating to Profile");
        profileNavItem.click();
        return new ProfilePage(driver);
    }
}

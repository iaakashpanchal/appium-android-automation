package com.qa.pages;

import com.qa.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ProfilePage {

    private final AndroidDriver driver;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_username")
    private WebElement usernameText;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_email")
    private WebElement emailText;

    @AndroidFindBy(id = "com.example.demoapp:id/btn_edit_profile")
    private WebElement editProfileButton;

    @AndroidFindBy(id = "com.example.demoapp:id/btn_logout")
    private WebElement logoutButton;

    @AndroidFindBy(id = "com.example.demoapp:id/iv_profile_image")
    private WebElement profileImage;

    public ProfilePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    public boolean isProfilePageDisplayed() {
        WaitUtils.waitForVisibility(driver, usernameText);
        return usernameText.isDisplayed();
    }

    public String getUsername() {
        return usernameText.getText();
    }

    public String getEmail() {
        return emailText.getText();
    }

    public LoginPage logout() {
        WaitUtils.waitForClickable(driver, logoutButton);
        logoutButton.click();
        return new LoginPage(driver);
    }

    public boolean isProfileImageDisplayed() {
        return profileImage.isDisplayed();
    }
}

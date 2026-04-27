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

public class LoginPage {

    private final AndroidDriver driver;
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // --- Locators ---
    @AndroidFindBy(id = "com.example.demoapp:id/et_email")
    private WebElement emailField;

    @AndroidFindBy(id = "com.example.demoapp:id/et_password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.example.demoapp:id/btn_login")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_error_message")
    private WebElement errorMessage;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_email_error")
    private WebElement emailError;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_forgot_password")
    private WebElement forgotPasswordLink;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_register")
    private WebElement registerLink;

    // --- Constructor ---
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    // --- Actions ---
    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        WaitUtils.waitForVisibility(driver, emailField);
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public HomePage clickLogin() {
        log.info("Clicking login button");
        WaitUtils.waitForClickable(driver, loginButton);
        loginButton.click();
        return new HomePage(driver);
    }

    public LoginPage clickLoginExpectingError() {
        log.info("Clicking login (expecting error)");
        loginButton.click();
        return this;
    }

    public LoginPage clickForgotPassword() {
        forgotPasswordLink.click();
        return this;
    }

    // --- Getters ---
    public String getErrorMessage() {
        WaitUtils.waitForVisibility(driver, errorMessage);
        return errorMessage.getText();
    }

    public String getEmailError() {
        WaitUtils.waitForVisibility(driver, emailError);
        return emailError.getText();
    }

    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }

    public boolean isEmailFieldDisplayed() {
        return emailField.isDisplayed();
    }

    // --- Fluent login helper ---
    public HomePage login(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLogin();
    }
}

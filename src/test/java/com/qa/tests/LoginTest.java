package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import com.qa.utils.ConfigReader;
import com.qa.utils.ReportManager;
import com.qa.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    // -------------------------------------------------------
    // TC_001 — Valid login
    // -------------------------------------------------------
    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        ReportManager.logInfo("TC_001: Valid login");

        String email = ConfigReader.get("validEmail");
        String password = ConfigReader.get("validPassword");

        HomePage homePage = loginPage.login(email, password);

        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Home page should be displayed after successful login");

        ReportManager.logPass("TC_001 PASSED — Home page displayed after login");
    }

    // -------------------------------------------------------
    // TC_002 — Invalid password
    // -------------------------------------------------------
    @Test(priority = 2, description = "Verify error message for incorrect password")
    public void testLoginWithInvalidPassword() {
        ReportManager.logInfo("TC_002: Invalid password");

        loginPage.enterEmail(ConfigReader.get("validEmail"))
                 .enterPassword("WrongPassword@99")
                 .clickLoginExpectingError();

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials") || error.contains("Incorrect password"),
                "Error message expected for wrong password. Got: " + error);

        ReportManager.logPass("TC_002 PASSED — Error shown for invalid password");
    }

    // -------------------------------------------------------
    // TC_003 — Empty fields validation
    // -------------------------------------------------------
    @Test(priority = 3, description = "Verify validation messages when fields are empty")
    public void testEmptyFieldsValidation() {
        ReportManager.logInfo("TC_003: Empty fields validation");

        loginPage.clickLoginExpectingError();

        String emailError = loginPage.getEmailError();
        Assert.assertFalse(emailError.isEmpty(),
                "Email field validation message should be shown");

        ReportManager.logPass("TC_003 PASSED — Validation shown for empty fields");
    }

    // -------------------------------------------------------
    // TC_004 — Data-driven: multiple invalid credentials
    // -------------------------------------------------------
    @Test(priority = 4,
          description = "Data-driven test: multiple invalid credential combinations",
          dataProvider = "invalidCredentials")
    public void testInvalidCredentialsCombinations(String email, String password, String scenario) {
        ReportManager.logInfo("TC_004: " + scenario);

        loginPage.enterEmail(email)
                 .enterPassword(password)
                 .clickLoginExpectingError();

        String error = loginPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(),
                "Error message should appear for scenario: " + scenario);

        ReportManager.logPass("TC_004 PASSED — " + scenario);
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
            { "nonexistent@test.com",     "SomePass@1",   "Unregistered email"         },
            { ConfigReader.get("validEmail"), "short",    "Password too short"          },
            { "notanemail",               "ValidPass@1",  "Malformed email format"      },
            { "",                         "ValidPass@1",  "Empty email, valid password" },
            { ConfigReader.get("validEmail"), "",         "Valid email, empty password" }
        };
    }

    // -------------------------------------------------------
    // TC_005 — UI elements visible on login screen
    // -------------------------------------------------------
    @Test(priority = 5, description = "Verify all UI elements are present on login screen")
    public void testLoginPageUIElements() {
        ReportManager.logInfo("TC_005: Login page UI elements check");

        Assert.assertTrue(loginPage.isEmailFieldDisplayed(),
                "Email field should be visible");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should be visible");

        ReportManager.logPass("TC_005 PASSED — All login UI elements present");
    }

    // -------------------------------------------------------
    // TC_006 — Logout flow
    // -------------------------------------------------------
    @Test(priority = 6, description = "Verify logout navigates back to login screen")
    public void testLogout() {
        ReportManager.logInfo("TC_006: Logout flow");

        HomePage homePage = loginPage.login(
            ConfigReader.get("validEmail"),
            ConfigReader.get("validPassword")
        );

        LoginPage loginPageAfterLogout = homePage
                .navigateToProfile()
                .logout();

        Assert.assertTrue(loginPageAfterLogout.isLoginButtonDisplayed(),
                "Login screen should be displayed after logout");

        ReportManager.logPass("TC_006 PASSED — Logout successful, login screen shown");
    }
}

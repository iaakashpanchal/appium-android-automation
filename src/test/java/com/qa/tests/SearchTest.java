package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import com.qa.pages.SearchPage;
import com.qa.utils.ConfigReader;
import com.qa.utils.ReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    private SearchPage searchPage;

    @BeforeMethod
    public void loginAndNavigate() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.login(
            ConfigReader.get("validEmail"),
            ConfigReader.get("validPassword")
        );
        searchPage = homePage.navigateToSearch();
    }

    @Test(priority = 1, description = "Verify valid search returns results")
    public void testSearchWithValidKeyword() {
        ReportManager.logInfo("TC_S01: Search with valid keyword");

        searchPage.search("Android");

        int results = searchPage.getResultCount();
        Assert.assertTrue(results > 0,
                "Search results should be returned for a valid keyword. Got: " + results);

        ReportManager.logPass("TC_S01 PASSED — " + results + " results returned");
    }

    @Test(priority = 2, description = "Verify empty state shown for invalid search")
    public void testSearchWithInvalidKeyword() {
        ReportManager.logInfo("TC_S02: Search with invalid keyword");

        searchPage.search("xyzInvalidKeyword123@@");

        Assert.assertTrue(searchPage.isNoResultsDisplayed(),
                "No results UI should be displayed for invalid keyword");

        ReportManager.logPass("TC_S02 PASSED — No results state shown correctly");
    }

    @Test(priority = 3, description = "Verify clear button resets the search field")
    public void testClearSearch() {
        ReportManager.logInfo("TC_S03: Clear search field");

        searchPage.search("Test").clearSearch();

        String fieldText = searchPage.getSearchFieldText();
        Assert.assertTrue(fieldText == null || fieldText.isEmpty() || fieldText.equals("Search"),
                "Search field should be empty after clear");

        ReportManager.logPass("TC_S03 PASSED — Search field cleared successfully");
    }
}

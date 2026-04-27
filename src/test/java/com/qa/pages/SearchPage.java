package com.qa.pages;

import com.qa.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class SearchPage {

    private final AndroidDriver driver;

    @AndroidFindBy(id = "com.example.demoapp:id/et_search")
    private WebElement searchField;

    @AndroidFindBy(id = "com.example.demoapp:id/btn_search_clear")
    private WebElement clearButton;

    @AndroidFindBy(id = "com.example.demoapp:id/tv_no_results")
    private WebElement noResultsText;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.demoapp:id/rv_search_results']/android.view.ViewGroup")
    private List<WebElement> searchResults;

    public SearchPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    public SearchPage search(String keyword) {
        WaitUtils.waitForVisibility(driver, searchField);
        searchField.click();
        searchField.sendKeys(keyword);
        return this;
    }

    public int getResultCount() {
        WaitUtils.waitForListNotEmpty(driver, searchResults);
        return searchResults.size();
    }

    public boolean isNoResultsDisplayed() {
        WaitUtils.waitForVisibility(driver, noResultsText);
        return noResultsText.isDisplayed();
    }

    public SearchPage clearSearch() {
        clearButton.click();
        return this;
    }

    public String getSearchFieldText() {
        return searchField.getAttribute("text");
    }
}

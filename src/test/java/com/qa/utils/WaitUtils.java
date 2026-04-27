package com.qa.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private static long getWait() {
        return Long.parseLong(ConfigReader.get("explicitWait"));
    }

    public static void waitForVisibility(AndroidDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(getWait()))
            .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(AndroidDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(getWait()))
            .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForListNotEmpty(AndroidDriver driver, List<WebElement> elements) {
        new WebDriverWait(driver, Duration.ofSeconds(getWait()))
            .until(d -> !elements.isEmpty());
    }

    public static void waitForTextToBe(AndroidDriver driver, WebElement element, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(getWait()))
            .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void hardWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

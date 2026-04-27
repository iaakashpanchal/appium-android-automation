package com.qa.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Base64;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info(">>> Test started: {}", result.getMethod().getMethodName());
        ReportManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("<<< PASSED: {}", result.getMethod().getMethodName());
        ReportManager.logPass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("<<< FAILED: {} — {}", result.getMethod().getMethodName(), result.getThrowable().getMessage());
        ReportManager.logFail("Test Failed: " + result.getThrowable().getMessage());

        try {
            Object instance = result.getInstance();
            AndroidDriver driver = (AndroidDriver) instance.getClass().getField("driver").get(instance);
            if (driver != null) {
                String base64 = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BASE64);
                ReportManager.getTest().fail("Screenshot on failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
            }
        } catch (Exception e) {
            log.warn("Could not capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("--- SKIPPED: {}", result.getMethod().getMethodName());
        ReportManager.logSkip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flushReports();
        log.info("All tests completed. Report flushed.");
    }
}

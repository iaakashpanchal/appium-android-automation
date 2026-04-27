package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    static {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setDocumentTitle("Appium Android Test Report");
        reporter.config().setReportName("Mobile Automation Results");
        reporter.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Framework", "Appium + TestNG + Java");
        extent.setSystemInfo("Platform", "Android");
        extent.setSystemInfo("Author", "Aakash Panchal");
    }

    public static void createTest(String testName) {
        testThread.set(extent.createTest(testName));
    }

    public static void logInfo(String message) {
        testThread.get().info(message);
    }

    public static void logPass(String message) {
        testThread.get().pass(message);
    }

    public static void logFail(String message) {
        testThread.get().fail(message);
    }

    public static void logSkip(String message) {
        testThread.get().skip(message);
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}

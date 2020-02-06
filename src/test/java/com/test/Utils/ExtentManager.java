package com.test.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;

    public synchronized static ExtentReports getReporter () {
        if (extent == null) {

            String workingDir = System.getProperty("user.dir");
            htmlReporter = new ExtentHtmlReporter(workingDir + "/target/AutomationReport.html");
            htmlReporter.config().setChartVisibilityOnOpen(true);

            htmlReporter.config().setDocumentTitle("Appium Test");
            htmlReporter.config().setReportName("Appium Test");
            htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.setSystemInfo("user", System.getProperty("Test User"));
            extent.setSystemInfo("os", "Mac OSX");
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }
}

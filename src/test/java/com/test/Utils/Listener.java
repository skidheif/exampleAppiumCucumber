package com.test.Utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.ExceptionUtil;
import com.test.Configuration.DeviceHelper;
import com.test.Configuration.LocalDriverManager;
import cucumber.api.testng.CucumberFeatureWrapperImpl;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Listener implements ITestListener {

    String deviceName;
    String methodName;

    private String getTestMethodName ( ITestResult iTestResult ) {
        return methodName;
    }

    @Override
    public void onStart ( ITestContext iTestContext ) {
        iTestContext.setAttribute("WebDriver", LocalDriverManager.getDriver());
        deviceName = iTestContext.getCurrentXmlTest().getLocalParameters().get("deviceName");

    }

    @Override
    public void onFinish ( ITestContext iTestContext ) {
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart ( ITestResult iTestResult ) {

        methodName = iTestResult.getMethod().getMethodName();
        String description = "Running tests on:" + deviceName;
        Object[] obj = iTestResult.getParameters();

        int counter = 0;
        while (obj.length != counter) {
            methodName = ((CucumberFeatureWrapperImpl) obj[0]).getCucumberFeature().getPath().replace(".feature", "");
            description = ((CucumberFeatureWrapperImpl) obj[0]).getCucumberFeature().getFeatureElements().get(0).getVisualName()
                    + "DeviceDetails:" + "<br></br>"
                    + "<b>DeviceName: <b>" + DeviceHelper.getDeviceName() + "<br></br>"
                    + "<b>DeviceUdid: <b>" + LocalDriverManager.getDriver().getPlatformName() + "<br></br>"
                    + "<b>DeviceModel: <b>" + LocalDriverManager.getDriver().getSessionDetails().get("deviceModel");
            counter++;
        }
        ExtentTestManager.createTest(methodName + " - " + deviceName, description);
    }

    @Override
    public void onTestSuccess ( ITestResult iTestResult ) {
        ExtentTestManager.getTest().log(Status.INFO, "Test passed");
    }

    @Override
    public void onTestFailure ( ITestResult iTestResult ) {

        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = LocalDriverManager.getDriver();

        if (webDriver != null) {
            File scrFile = ((TakesScreenshot) webDriver)
                    .getScreenshotAs(OutputType.FILE);

            String failedScreen =
                    System.getProperty("user.dir") + "/target/screenshot/" + "/"
                            + testClass.toString() + currentDateAndTime() + "_" + "_failed" + ".png";

            try {
                FileUtils.copyFile(scrFile, new File(failedScreen));

                ExtentTestManager.getTest().log(Status.FAIL, "Test Failed" + "<br></br>");
                ExtentTestManager.getTest().log(Status.INFO, "<b>Failure Reason--->>> </b>  <br></br>" + iTestResult.getThrowable().getMessage());
                ExtentTestManager.getTest().log(Status.INFO, "<b>Exception Details--->>></b> + <br></br>" + "<pre>" + ExceptionUtil.getStackTrace(iTestResult.getThrowable()) + "</pre>");
                ExtentTestManager.getTest().addScreenCaptureFromPath(failedScreen, ExtentTestManager.getTest().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            webDriver.quit();

        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
            ExtentTestManager.getTest().log(Status.INFO, "<b>Failure Reason--->>> </b>  <br></br>" + iTestResult.getThrowable().getMessage());
            ExtentTestManager.getTest().log(Status.INFO, "<b>Exception Details--->>></b> + <br></br>" + "<pre>" + ExceptionUtil.getStackTrace(iTestResult.getThrowable()) + "</pre>");
        }
    }

    public String currentDateAndTime () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        return now.format(dtf);
    }

    @Override
    public void onTestSkipped ( ITestResult iTestResult ) {
        ExtentTestManager.extent.removeTest(ExtentTestManager.getTest());
        IRetryAnalyzer retryAnalyzer = iTestResult.getMethod().getRetryAnalyzer();
        if (((Retry) retryAnalyzer).retryCountForTest == ((Retry) retryAnalyzer).maxRetryCount) {
            ExtentManager.getReporter().flush();
        }
        System.out.println("I am onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped or failed on 1st Execution");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage ( ITestResult iTestResult ) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}
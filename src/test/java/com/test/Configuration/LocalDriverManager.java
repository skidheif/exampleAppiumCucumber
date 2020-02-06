package com.test.Configuration;

import io.appium.java_client.AppiumDriver;

public class LocalDriverManager {

    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();

    public static AppiumDriver getDriver () {
        return appiumDriver.get();
    }

    public static void setWebDriver ( AppiumDriver driver ) {
        appiumDriver.set(driver);
    }

}

package com.test.Configuration;

import io.appium.java_client.service.local.AppiumDriverLocalService;

public class ServiceManager {

    private static ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();

    public static AppiumDriverLocalService getService () {
        return appiumService.get();
    }

    public static void setService ( AppiumDriverLocalService service ) {
        appiumService.set(service);
    }
}

package com.test.Configuration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;

public class Hooks {

    static URL url;
    private PropertyReader pathReader = new PropertyReader();

    @BeforeSuite
    public void startServer () {

        AppiumDriverLocalService service;

        String appiumPath = pathReader.readProperty("APPIUM_PATH");

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumPath))
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort();
        service = appiumServiceBuilder.build();

        ServiceManager.setService(service);

        ServiceManager.getService().start();


        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        url = service.getUrl();

        System.out.println("Server started Successfully on " + service.getUrl());
    }

    @AfterSuite
    public void stopServer () {
        if (ServiceManager.getService() != null) {
            ServiceManager.getService().stop();
        }
    }

    @Parameters({"platform", "platformVersion", "deviceName", "port", "udid"})
    @BeforeClass(alwaysRun = true)
    public void launchDriver ( String platform, String platformVersion, String deviceName, String port, String udid ) {

        if (platform.toLowerCase().equals("android")) {
            LocalDriverManager.setWebDriver(getAndroidDriver(platformVersion, deviceName, port, udid));

            DeviceHelper.setDeviceName(deviceName);

        } else {
            LocalDriverManager.setWebDriver(getIOSDriver(platformVersion, deviceName, port, udid));

            DeviceHelper.setDeviceName(deviceName);
        }
    }

    @AfterClass(alwaysRun = true)
    public void disposeDriver () {
        if (LocalDriverManager.getDriver() != null) {
            LocalDriverManager.getDriver().quit();
        }
    }

    private AppiumDriver getIOSDriver ( String platformVersion, String deviceName, String wdaLocalPort, String udid ) {

        if (url != null) {

            File appDir = new File("src/test/resources/");
            File app = new File(appDir, "*.ipa");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability(MobileCapabilityType.APP, app);
            capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            capabilities.setCapability(IOSMobileCapabilityType.PREVENT_WDAATTACHMENTS, false);
            capabilities.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 80000);
            capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 80000);
            capabilities.setCapability(IOSMobileCapabilityType.SHOULD_USE_SINGLETON_TESTMANAGER, false);
            capabilities.setCapability(IOSMobileCapabilityType.SIMPLE_ISVISIBLE_CHECK, true);
            capabilities.setCapability(IOSMobileCapabilityType.MAX_TYPING_FREQUENCY, 10);
            capabilities.setCapability("wdaLocalPort", Integer.valueOf(wdaLocalPort));
            capabilities.setCapability("clearSystemFiles", true);
            capabilities.setCapability("udid", udid);
            return new IOSDriver<IOSElement>(url, capabilities);
        } else {
            System.out.println("You have to launch appium server before launching driver");
            return null;
        }
    }

    private AppiumDriver getAndroidDriver ( String platformVersion, String deviceName, String portNumber, String udid ) {

        if (url != null) {

            File appDir = new File("src/test/resources/");
            File app = new File(appDir, "com.allgoritm.youla.apk");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.valueOf(portNumber));
            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
            capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

            if (udid != "") {
                capabilities.setCapability("udid", udid);
            }

            return new AndroidDriver<AndroidElement>(url, capabilities);

        } else {
            System.out.println("You have to launch appium server before launching driver");
            return null;
        }
    }
}
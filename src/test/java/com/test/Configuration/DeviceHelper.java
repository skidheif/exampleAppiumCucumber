package com.test.Configuration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DeviceHelper {

    private static ThreadLocal<String> device = new ThreadLocal<>();

    public static void setDeviceName ( String deviceName ) {
        device.set(deviceName);
    }

    public static String getDeviceName () {
        return device.get();
    }

    public void waitForPageToLoad ( WebElement id ) {
        WebDriverWait wait = new WebDriverWait(LocalDriverManager.getDriver(), 25);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public WebElement waitForElement ( WebElement arg ) {
        waitForPageToLoad(arg);
        return arg;
    }


    public void swipeRight () {
        Dimension size = LocalDriverManager.getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.9);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(LocalDriverManager.getDriver())
                .press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(endx, starty)).perform().release();
    }

    public void swipeLeft () {
        Dimension size = LocalDriverManager.getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.8);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(LocalDriverManager.getDriver())
                .press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(endx, starty)).perform().release();
    }
}



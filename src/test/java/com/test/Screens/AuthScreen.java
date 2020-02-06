package com.test.Screens;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;

public class AuthScreen {

    @AndroidFindBy(id = "com.allgoritm.youla:id/ok")
    @iOSFindBy(xpath = "")
    public WebElement okButton;

    @AndroidFindBy(xpath = "//*[@resource-id = 'field_email']")
    @iOSFindBy(xpath = "")
    public WebElement email_field;

    @AndroidFindBy(xpath = "//*[@resource-id = 'field_password']")
    @iOSFindBy(xpath = "")
    public WebElement pass_field;

    @AndroidFindBy(xpath = "//android.widget.Button[@text = 'Sign in']")
    @iOSFindBy(xpath = "")
    public WebElement sighIn_button;
}

package com.test.StepDefinitions;

import com.test.Configuration.DeviceHelper;
import com.test.Configuration.LocalDriverManager;
import com.test.Screens.AuthScreen;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AuthPage extends DeviceHelper {

    AuthScreen authScreen = new AuthScreen();

    public AuthPage() {
        PageFactory.initElements(new AppiumFieldDecorator(LocalDriverManager.getDriver()), authScreen);
    }

    @When("^User click ok button$")
    public void userClickOKButton() {
        authScreen.okButton.click();
    }

    @When("^User fill \"([^\"]*)\" the email field$")
    public void userFillEmailField(String text) {
        waitForElement(authScreen.email_field).sendKeys(text);
    }

    @When("^User fill \"([^\"]*)\" the password field$")
    public void userFillEmailPassField(String text) {
        waitForElement(authScreen.pass_field).sendKeys(text);
    }

    @When("^User click sign in button$")
    public void userClickSignButton() {
        authScreen.sighIn_button.click();
    }

    @Then("^User waiting \"([^\"]*)\" seconds$")
    public void userWaiting(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

}

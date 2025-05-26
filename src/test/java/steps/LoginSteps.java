package steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utils.AESHelper;
import utils.CommonMethods;
import utils.ConfigReader;


public class LoginSteps extends CommonMethods {
    @Given("User fills the username and password fields with correct credentials")
    public void user_fills_the_username_and_password_fields_with_correct_credentials() throws Exception {
        //waitForClickability(login.LoginOrSignInHb);

        // hover(login.LoginOrSignInHb);
        waitForClickability(login.LoginN11);

        click(login.LoginN11);
        sendText(login.userNameFieldN11, AESHelper.decrypt(ConfigReader.getPropertyValue("username")));
        Thread.sleep(2000);
        sendText(login.passFieldN11, AESHelper.decrypt(ConfigReader.getPropertyValue("password")));
        Thread.sleep(2000);
        click(login.loginButton);
        // Assert.assertEquals("Favorilerim",login.favorites.getText());
        System.out.println(login.favorites.getText());
        Assert.assertTrue(login.favorites.isDisplayed());

        //throw new io.cucumber.java.PendingException();

    }

    @Then("User logins successfully")
    public void user_logins_successfullyy() {
        Assert.assertTrue( login.favorites.isDisplayed());




    }
}




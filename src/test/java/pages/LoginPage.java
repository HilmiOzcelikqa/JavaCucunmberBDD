package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;



public class LoginPage extends CommonMethods {

    @FindBy(id ="login")
    public WebElement LoginHb;
    @FindBy(xpath ="//*[@id=\"header\"]/div/div/div/div[2]/div[5]/div/div/div/a[2]")
    public WebElement LoginN11;

    @FindBy(id ="email")
    public WebElement userNameFieldN11;
    @FindBy(id ="txtUserName")
    public WebElement userNameFieldHb;
    @FindBy(id ="password")
    public WebElement passFieldN11;

    @FindBy(id="loginButton")
    public WebElement loginButton;


    @FindBy(className = "myFavorities")
    public WebElement favorites;



    public LoginPage()
    {
        PageFactory.initElements(driver,this);
    }









}

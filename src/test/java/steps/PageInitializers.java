package steps;

import pages.HomePage;
import pages.LoginPage;

public class PageInitializers {
    public static LoginPage login;
    public static HomePage homePage;


    public static void initializePageObjects() {
        login = new LoginPage();
        homePage=new HomePage();

    }
}

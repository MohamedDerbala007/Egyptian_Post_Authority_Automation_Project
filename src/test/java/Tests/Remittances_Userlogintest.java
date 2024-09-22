package Tests;

import Pages.LoginPage;
import TestBases.Remittances_TestBase;
import org.testng.annotations.Test;

public class Remittances_Userlogintest extends Remittances_TestBase
{
    LoginPage loginPageObject;
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";

    @Test
    public void userCanLoginSuccessfully() throws InterruptedException
    {
        loginPageObject = new LoginPage(driver);
        loginPageObject.userLogin(userName, password);
        Thread.sleep(5000);
    }
}

package Tests.Remittances;

import TestBases.Remittances_TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Remittances.Pages.Remittances_HomePage;
import Remittances.Pages.Remittances_LoginPage;

import java.time.Duration;

public class Remittances_Userlogintest extends Remittances_TestBase
{
    Remittances_LoginPage loginPageObject;
    Remittances_HomePage homePageObject;
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";

    @Test
    public void userCanLoginSuccessfully() throws InterruptedException
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPageObject = new Remittances_LoginPage(driver);
        loginPageObject.userLogin(userName, password);

        homePageObject = new Remittances_HomePage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(homePageObject.addNewRemittanceBtn));
        Assert.assertTrue(homePageObject.addNewRemittanceBtn.isDisplayed());
    }
}

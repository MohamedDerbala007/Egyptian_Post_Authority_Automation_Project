package Tests;

import Pages.Remittances_CashingOrRefundingRemittancesPage;
import Pages.Remittances_HomePage;
import Pages.Remittances_LoginPage;
import TestBases.Remittances_TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;


public class Remittances_CashingOutRemittancetest extends Remittances_TestBase {
	Remittances_LoginPage loginPageObject;
    Remittances_HomePage homePageObject;
    Remittances_CashingOrRefundingRemittancesPage cashingOrRefundingRemittancesPageObject;
    
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";

    @Test
    public void userCanCashOutRemittancesSuccessfully() throws InterruptedException 
    {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPageObject = new Remittances_LoginPage(driver);
        loginPageObject.userLogin(userName, password);

        homePageObject = new Remittances_HomePage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(homePageObject.instantCashingOrRefundingOfRemittancesBtn));
        homePageObject.cashingOrRefundingRemittances();
        cashingOrRefundingRemittancesPageObject = new Remittances_CashingOrRefundingRemittancesPage(driver);
        wait.until(ExpectedConditions.visibilityOf(cashingOrRefundingRemittancesPageObject.senderIdenTypeDDL));
        cashingOrRefundingRemittancesPageObject.searchForInstantRemittance();
        Thread.sleep(5000);
    }
        
}
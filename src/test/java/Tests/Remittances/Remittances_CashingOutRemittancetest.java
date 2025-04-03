package Tests.Remittances;

import TestBases.Remittances_TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Remittances.Pages.Remittances_CashingOrRefundingRemittancesPage;
import Remittances.Pages.Remittances_DetailsOfSearchedRemittancesPage;
import Remittances.Pages.Remittances_HomePage;
import Remittances.Pages.Remittances_LoginPage;
import Remittances.Pages.Remittances_SearchedRemittancesPage;

import java.time.Duration;


public class Remittances_CashingOutRemittancetest extends Remittances_TestBase {
	Remittances_LoginPage loginPageObject;
	Remittances_HomePage homePageObject;
	Remittances_CashingOrRefundingRemittancesPage cashingOrRefundingRemittancesPageObject;
	Remittances_SearchedRemittancesPage remittances_SearchedRemittancesObjet;
	Remittances_DetailsOfSearchedRemittancesPage remittances_DetailsOfSearchedRemittancesObject;

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
		remittances_SearchedRemittancesObjet = new Remittances_SearchedRemittancesPage(driver);
		remittances_SearchedRemittancesObjet.openTheSearchedRemittance();
		remittances_DetailsOfSearchedRemittancesObject = new Remittances_DetailsOfSearchedRemittancesPage(driver);
		wait.until(ExpectedConditions.visibilityOf(remittances_DetailsOfSearchedRemittancesObject.receiverIdenSearchBtn));
		remittances_DetailsOfSearchedRemittancesObject.selectReceiverType();
		remittances_DetailsOfSearchedRemittancesObject.insertReceiverIdenNo();
		remittances_DetailsOfSearchedRemittancesObject.cashOutRemittance();

		Alert alert = driver.switchTo().alert();
		alert.accept();

		String originalWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!originalWindow.equals(windowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
		int originalWindowCount = driver.getWindowHandles().size();

	    // Wait and check for a new window handle (receipt page)
	    wait.until(ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1));

	    // Assert that a new page has opened
	    Assert.assertTrue(driver.getWindowHandles().size() > originalWindowCount, "Receipt page did not open in a new window.");
	}


}
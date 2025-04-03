package Tests.CashCollection;

import TestBases.CashCollections_TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import CashCollection.Pages.CC_AddNewCashCollectionPage;
import CashCollection.Pages.CC_HomePage;
import CashCollection.Pages.CC_LoginPage;
import CashCollection.Pages.CC_ReceiptPage;
import org.testng.Assert;
import java.awt.AWTException;
import java.time.Duration;

public class CashCollection_AddNewCashCollectiontest_Config4_Fixed extends CashCollections_TestBase{
	CC_LoginPage loginObject;
	CC_HomePage homeObject;
	CC_AddNewCashCollectionPage addNewCashCollectionObject;
	CC_ReceiptPage receiptObject;
	String userName = "حُسنيه محمد السيد الحمراوي";
	String password = "q1234567890";
	String BankName = "Test Bank";
	String CompanyName = "درباله تيست فيكسد 4";
	String Depositor = "شركه العوادلي لتجاره مواد البناء";
	String RecipientID =  "29802261801751";
	String CashAmount = "6000000";
	String ChangeAmount = "50000";
	
	@Test
	public void userCanAddNewCashCollectionSuccessfully() throws InterruptedException, AWTException 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		loginObject = new CC_LoginPage(driver);
		loginObject.userLogin(userName, password);
		homeObject = new CC_HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homeObject.addNewCashCollectionBtn));
		homeObject.openNewCashCollectionPage();
		addNewCashCollectionObject = new CC_AddNewCashCollectionPage(driver);
		addNewCashCollectionObject.addNewCashCollection(BankName, CompanyName, RecipientID, CashAmount, ChangeAmount);
		receiptObject = new CC_ReceiptPage(driver);
		receiptObject.cancelPrinting();
		Thread.sleep(3000);
		String displayedBigValueAmount = receiptObject.getBigValueText();
		String displayedChangeValueAmount = receiptObject.getChangeValueText();
		String displayedChangeFeesAmount = receiptObject.getChangeFeesText();
		String displayedPayedValue = receiptObject.getPayedValueText();
		String displayedServiceFee = receiptObject.getServiceFeeText();
		double expectedChangeFees = 0.02 * Double.parseDouble(ChangeAmount);
	    double actualChangeFees = Double.parseDouble(displayedChangeFeesAmount);
	    double actualServiceFee = Double.parseDouble(displayedServiceFee);
	    double expectedPayedValue = Double.parseDouble(CashAmount) + Double.parseDouble(ChangeAmount) + actualServiceFee + actualChangeFees;;
	    double actualPayedValue = Double.parseDouble(displayedPayedValue);
	    
	    Assert.assertEquals(displayedBigValueAmount, CashAmount, "Cash amount in receipt does not match expected value!");
	    Assert.assertEquals(displayedChangeValueAmount, ChangeAmount, "Change amount in receipt does not match expected value!");
	    Assert.assertEquals(actualChangeFees, expectedChangeFees, "Change fees do not match the expected calculation!");
	    Assert.assertEquals(actualPayedValue, expectedPayedValue, "Payed value in receipt is not correct");
	    
	    receiptObject.insertPayment();
	}
}

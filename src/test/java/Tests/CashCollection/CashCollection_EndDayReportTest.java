package Tests.CashCollection;

import TestBases.CashCollections_TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import CashCollection.Pages.CC_EndDayReportPage;
import CashCollection.Pages.CC_HomePage;
import CashCollection.Pages.CC_LoginPage;
import org.testng.Assert;
import java.awt.AWTException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class CashCollection_EndDayReportTest extends CashCollections_TestBase{
	CC_LoginPage loginObject;
	CC_HomePage homeObject;
	CC_EndDayReportPage endDayReportObject;
	String userName = "حُسنيه محمد السيد الحمراوي";
	String password = "q1234567890";
	
	
	@Test
	public void userCanAddNewCashCollectionSuccessfully() throws InterruptedException, AWTException, SQLException 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		loginObject = new CC_LoginPage(driver);
		loginObject.userLogin(userName, password);
		homeObject = new CC_HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homeObject.endDayReportBtn));
		homeObject.openEndDayReportPage();
		endDayReportObject = new CC_EndDayReportPage(driver);
		
		List<Map<String, String>> uiTransactions = endDayReportObject.getTransactionTableData();

	    // Step 3: Query data from DB
	    String query = "SELECT [Transaction_CustomerNumber], [Transaction_DateTime], " +
	                   "[Transaction_NetAmount], [Transaction_TotalFees], [Transfer_Fees], " +
	                   "[Transaction_ChangeFees], [Transaction_BigFees], [Transaction_TotalValue], " +
	                   "[Transaction_ChangeValue], [Transaction_BigValue], " +
	                   "[FK_CustomerID], [Transaction_Serial], [Transaction_PaymentOrder] " +
	                   "FROM [CashCollectionNew].[dbo].[Transactions] " +
	                   "WHERE CAST(Transaction_DateTime AS DATE) = '2025-04-12'";

	    List<Map<String, String>> dbTransactions = utils.DBUtils.getQueryResults(query);

	    // Step 4: Compare each transaction
	    for (Map<String, String> uiTx : uiTransactions) {
	        boolean found = false;
	        for (Map<String, String> dbTx : dbTransactions) {
	            String uiPaymentOrder = uiTx.get("PaymentOrder").trim();
	            String dbPaymentOrder = dbTx.get("Transaction_PaymentOrder") != null ? dbTx.get("Transaction_PaymentOrder").trim() : "";

	            String uiCustomerNumber = uiTx.get("CustomerNumber").trim();
	            String dbCustomerNumber = dbTx.get("Transaction_CustomerNumber") != null ? dbTx.get("Transaction_CustomerNumber").trim() : "";

	            String uiTransactionSerial = uiTx.get("TransactionSerial").trim();
	            String dbTransactionSerial = dbTx.get("Transaction_Serial") != null ? dbTx.get("Transaction_Serial").trim() : "";

	            if (uiPaymentOrder.equals(dbPaymentOrder) &&
	                uiCustomerNumber.equals(dbCustomerNumber) &&
	                uiTransactionSerial.equals(dbTransactionSerial)) {
	                found = true;
	                break;
	            }
	        }
	        Assert.assertTrue(found, "Transaction not found in DB: " + uiTx);
	    }
	}
}
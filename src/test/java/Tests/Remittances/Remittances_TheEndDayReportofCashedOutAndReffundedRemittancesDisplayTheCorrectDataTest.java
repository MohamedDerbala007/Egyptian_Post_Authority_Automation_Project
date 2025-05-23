package Tests.Remittances;

import TestBases.Remittances_TestBase;
import utils.DatabaseHelper;
import utils.Transaction;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Remittances.Pages.Remittances_DataOfCashingOrRefundingRemittancesReportPage;
import Remittances.Pages.Remittances_ExportedRemittancesReportPage;
import Remittances.Pages.Remittances_HomePage;
import Remittances.Pages.Remittances_LoginPage;
import Remittances.Pages.Remittances_ReportsPage;

import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Remittances_TheEndDayReportofCashedOutAndReffundedRemittancesDisplayTheCorrectDataTest extends Remittances_TestBase {
    Remittances_LoginPage loginPageObject;
    Remittances_HomePage homePageObject;
    Remittances_ExportedRemittancesReportPage exportedRemittancesReportPageObject;
    Remittances_DataOfCashingOrRefundingRemittancesReportPage dataOfCashingOrRefundingRemittancesReportPageObject;
    Remittances_ReportsPage reportsPageObject;
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";
    String reportType = "نهاية اليوم)الحوالات المصروفة والمستردة 8 حوالات فورية)";
    String fromDate = "05-01-2025";

    @Test
    public void dataIsDisplayedCorrectlyInTheReports() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(5000);

        loginPageObject = new Remittances_LoginPage(driver);
        loginPageObject.userLogin(userName, password);

        homePageObject = new Remittances_HomePage(driver);
        String extractedDate = "";

        // Check if alert is present and extract date if exists
        if (isAlertPresent()) {
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                String alertMessage = alert.getText();

                String datePattern = "\\b(\\d{2}/\\d{2}/\\d{4})\\b";
                Pattern pattern = Pattern.compile(datePattern);
                Matcher matcher = pattern.matcher(alertMessage);

                if (matcher.find()) {
                    extractedDate = matcher.group(1);
                    System.out.println("Extracted Date from Alert: " + extractedDate);
                } else {
                    System.out.println("No date found in the alert message.");
                }

                alert.accept();
            } catch (NoAlertPresentException e) {
                System.out.println("No alert was present.");
            }
        } else {
            System.out.println("No alert was present.");
        }

        if (!extractedDate.isEmpty()) {
            String formattedDate = extractedDate.replace("/", "-");

            js.executeScript("arguments[0].value='" + formattedDate + "';", homePageObject.reportDateField);
            System.out.println("Date set in the report date field: " + formattedDate);
            homePageObject.displayTheEndDayReportofExportedRemittances(reportType);
            exportedRemittancesReportPageObject = new Remittances_ExportedRemittancesReportPage(driver);
            exportedRemittancesReportPageObject.printingTotalRemittancesClosingReport();
            exportedRemittancesReportPageObject.cancelPrinting();

            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(10000);
            exportedRemittancesReportPageObject.exitTheReport();
        }

        wait.until(ExpectedConditions.elementToBeClickable(homePageObject.reportsBtn));
        Assert.assertTrue(homePageObject.reportsBtn.isDisplayed());
        homePageObject.openReportsPage();
        reportsPageObject = new Remittances_ReportsPage(driver); 
        reportsPageObject.displayReport(fromDate, reportType);

        // Use the dynamic query method
        String countQuery = "SELECT COUNT(*) AS TransactionCount " +
                "FROM [CenterPost].[dbo].[Posting] " +
                "where (CONVERT(DATE, [PaidDate]) = CONVERT(DATE, GETDATE()) " +
                "OR CONVERT(DATE, [ReturnedDate]) = CONVERT(DATE, GETDATE()))";


        DatabaseHelper dbHelper = new DatabaseHelper();
        int transactionCountFromDB = dbHelper.executeCountQuery(countQuery);
        System.out.println("Number of the transactions in the database : " + transactionCountFromDB);

        // Get the value from the report
        dataOfCashingOrRefundingRemittancesReportPageObject = new Remittances_DataOfCashingOrRefundingRemittancesReportPage(driver);
        int transactionsCountFromTheReport = dataOfCashingOrRefundingRemittancesReportPageObject.transactionsCountFromReport();
        System.out.println("Number of the transactions displayed in the Report: " + transactionsCountFromTheReport);

        // Assert the counts match
        Assert.assertEquals(transactionsCountFromTheReport, transactionCountFromDB, "Transaction counts do not match!");
        String detailQuery = "SELECT [PostNum], [Amount], [Fees], [Receiver], [Sender], [MailboxedDate] " +
        		"FROM [CenterPost].[dbo].[Posting] " +
                "where (CONVERT(DATE, [PaidDate]) = CONVERT(DATE, GETDATE()) " +
                "OR CONVERT(DATE, [ReturnedDate]) = CONVERT(DATE, GETDATE()))" +
                "ORDER BY [Posting_ID] DESC";
        List<Transaction> transactionsFromDB = dbHelper.executeTransactionQuery(detailQuery);

        // Fetch transaction details from the UI
        List<Transaction> transactionsFromUI = dataOfCashingOrRefundingRemittancesReportPageObject.fetchTransactionsFromTable();

        // Compare transaction details
        for (int i = 0; i < transactionsFromDB.size()-1; i++) {
            Transaction dbTransaction = transactionsFromDB.get(i);
            Transaction uiTransaction = transactionsFromUI.get(i);

            Assert.assertEquals(uiTransaction.getTransactionNumber(), dbTransaction.getTransactionNumber(),
                    "Transaction number mismatch at index: " + i);
            Assert.assertEquals(uiTransaction.getAmount(), dbTransaction.getAmount(),
                    "Transaction amount mismatch at index: " + i);
            Assert.assertEquals(uiTransaction.getFees(), dbTransaction.getFees(),
                    "Transaction fees mismatch at index: " + i);
            Assert.assertEquals(uiTransaction.getReceiverName(), dbTransaction.getReceiverName(),
                    "Receiver name mismatch at index: " + i);
            Assert.assertEquals(uiTransaction.getSenderName(), dbTransaction.getSenderName(),
                    "Sender name mismatch at index: " + i);
        }

        System.out.println("All transactions matched successfully!");
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}

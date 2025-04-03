package Tests.Remittances;

import TestBases.Remittances_TestBase;
import utils.DatabaseHelper;
import utils.Transaction;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Remittances.Pages.*;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Remittances_TheEndDayReportofExportedRemittancesDisplayTheCorrectDataTest extends Remittances_TestBase {
    private Remittances_LoginPage loginPageObject;
    private Remittances_HomePage homePageObject;
    private Remittances_ExportedRemittancesReportPage exportedRemittancesReportPageObject;
    private Remittances_DataOfExportedRemittancesReportPage dataOfExportedRemittancesReportPageObject;
    private Remittances_ReportsPage reportsPageObject;

    private final String userName = "عمر ابراهيم احمد";
    private final String password = "q1234567890";
    private final String reportType = "نهاية اليوم)الحوالات المصدرة 6 حوالات فورية)";
    private final String fromDate = "05-01-2025";

    @Test
    public void dataIsDisplayedCorrectlyInTheEndDayReportofExportedRemittances() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Login and navigate to home page
        loginAndNavigateToHomePage();

        // Handle alert and extract date
        String extractedDate = handleAlertAndExtractDate(wait);

        // Display and validate report if a date was extracted
        if (!extractedDate.isEmpty()) {
            displayEndDayReport(js, extractedDate);
        }

        // Validate report data against database
        validateReportData(wait);
    }

    private void loginAndNavigateToHomePage() throws InterruptedException {
        loginPageObject = new Remittances_LoginPage(driver);
        loginPageObject.userLogin(userName, password);
        homePageObject = new Remittances_HomePage(driver);
        Thread.sleep(5000); // Allow time for the page to load
    }

    private String handleAlertAndExtractDate(WebDriverWait wait) {
        String extractedDate = "";

        if (isAlertPresent()) {
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                String alertMessage = alert.getText();

                extractedDate = extractDateFromAlertMessage(alertMessage);
                alert.accept();

                if (extractedDate.isEmpty()) {
                    System.out.println("No date found in the alert message.");
                } else {
                    System.out.println("Extracted Date from Alert: " + extractedDate);
                }
            } catch (NoAlertPresentException e) {
                System.out.println("No alert was present.");
            }
        } else {
            System.out.println("No alert was present.");
        }

        return extractedDate;
    }

    private String extractDateFromAlertMessage(String alertMessage) {
        String datePattern = "\\b(\\d{2}/\\d{2}/\\d{4})\\b";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(alertMessage);

        return matcher.find() ? matcher.group(1).replace("/", "-") : "";
    }

    private void displayEndDayReport(JavascriptExecutor js, String extractedDate) throws InterruptedException {
        js.executeScript("arguments[0].value='" + extractedDate + "';", homePageObject.reportDateField);
        System.out.println("Date set in the report date field: " + extractedDate);

        homePageObject.displayTheEndDayReportofExportedRemittances(reportType);

        exportedRemittancesReportPageObject = new Remittances_ExportedRemittancesReportPage(driver);
        exportedRemittancesReportPageObject.printingTotalRemittancesClosingReport();
        exportedRemittancesReportPageObject.cancelPrinting();

        // Handle alert after printing
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        Thread.sleep(5000); // Wait for the page to load before exiting the report
        exportedRemittancesReportPageObject.exitTheReport();
    }

    private void validateReportData(WebDriverWait wait) {
        // Open the reports page and display the report
        wait.until(ExpectedConditions.elementToBeClickable(homePageObject.reportsBtn));
        Assert.assertTrue(homePageObject.reportsBtn.isDisplayed(), "Reports button is not displayed.");
        homePageObject.openReportsPage();

        reportsPageObject = new Remittances_ReportsPage(driver);
        reportsPageObject.displayReport(fromDate, reportType);

        // Fetch and compare data between database and UI
        compareTransactionData();
    }

    private void compareTransactionData() {
        DatabaseHelper dbHelper = new DatabaseHelper();

        // Query to count transactions
        String countQuery = "SELECT COUNT(*) AS TransactionCount " +
                "FROM [CenterPost].[dbo].[Posting] " +
                "WHERE (CONVERT(DATE, [MailboxedDate]) = CONVERT(DATE, GETDATE()) " +
                "AND ([RemittanceStatus] IS NULL OR [RemittanceStatus] <> N'حواله ملغاه'))";
        int transactionCountFromDB = dbHelper.executeCountQuery(countQuery);
        System.out.println("Number of exported transactions in the database: " + transactionCountFromDB);

        // Fetch transaction count from the report
        dataOfExportedRemittancesReportPageObject = new Remittances_DataOfExportedRemittancesReportPage(driver);
        int transactionsCountFromReport = dataOfExportedRemittancesReportPageObject.transactionsCountFromReport();
        System.out.println("Number of exported transactions displayed in the report: " + transactionsCountFromReport);

        // Validate transaction counts
        Assert.assertEquals(transactionsCountFromReport, transactionCountFromDB, "Transaction counts do not match!");

        // Query to fetch detailed transaction data
        String detailQuery = "SELECT [PostNum], [Amount], [Fees], [Receiver], [Sender], [MailboxedDate] " +
                "FROM [CenterPost].[dbo].[Posting] " +
                "WHERE (CONVERT(DATE, [MailboxedDate]) = CONVERT(DATE, GETDATE()) " +
                "AND ([RemittanceStatus] IS NULL OR [RemittanceStatus] <> N'حواله ملغاه')) " +
                "ORDER BY [Posting_ID] ASC";
        List<Transaction> transactionsFromDB = dbHelper.executeTransactionQuery(detailQuery);

        // Fetch transaction details from the UI
        List<Transaction> transactionsFromUI = dataOfExportedRemittancesReportPageObject.fetchTransactionsFromTable();

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

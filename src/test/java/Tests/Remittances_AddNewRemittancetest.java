package Tests;

import Pages.Remittances_AddNewRemittancePage;
import Pages.Remittances_ExportedRemittancesReportPage;
import Pages.Remittances_HomePage;
import Pages.Remittances_LoginPage;
import TestBases.Remittances_TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelReader;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Remittances_AddNewRemittancetest extends Remittances_TestBase {
    Remittances_LoginPage loginPageObject;
    Remittances_HomePage homePageObject;
    Remittances_AddNewRemittancePage addNewRemittancePageObject;
    Remittances_ExportedRemittancesReportPage exportedRemittancesReportPageObject;
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";
    String reportType = "نهاية اليوم)الحوالات المصدرة 6 حوالات فورية)";
    String excelFilePath = "E:\\Automation Projects\\Egyptian_Post_Authority_Automation_Project\\remittance_data.xlsx";
    String sheetName = "Sheet1";

    @Test
    public void userCanAddNewRemittanceSuccessfully() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(5000);

        // Load Excel data
        Object[][] remittanceData = ExcelReader.readDataFromExcel(excelFilePath, sheetName);

        // Loop through Excel data
        for (Object[] row : remittanceData) {
            String senderID = row[0].toString();
            String receiverID = row[1].toString();
            String amount = row[2].toString();

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
                homePageObject = new Remittances_HomePage(driver);
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

            wait.until(ExpectedConditions.elementToBeClickable(homePageObject.addNewRemittanceBtn));
            Assert.assertTrue(homePageObject.addNewRemittanceBtn.isDisplayed());
            homePageObject.AddNewRemittance();

            addNewRemittancePageObject = new Remittances_AddNewRemittancePage(driver);
            wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.senderIdenTypeDDL));
            addNewRemittancePageObject.chooseIdenTypeForSender();
            addNewRemittancePageObject.insertSenderID(senderID);
            addNewRemittancePageObject.searchForSenderID();
            Thread.sleep(2000);
            
            wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.receiverIdenTypeDDL));
            addNewRemittancePageObject.chooseIdenTypeForReceiver();
            Thread.sleep(2000);
            
            wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.receiverIDTxtBox));
            addNewRemittancePageObject.insertReceiverID(receiverID);
            addNewRemittancePageObject.searchForReceiverID();
            Thread.sleep(2000);
            
            wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.bigValueTxtBox));
            addNewRemittancePageObject.insertTheBigValueofTheAmount(amount);
            addNewRemittancePageObject.confirmTheAmount();
            Thread.sleep(2000);
            
            addNewRemittancePageObject.chooseReasonForTheTransaction();
            wait.until(ExpectedConditions.elementToBeClickable(addNewRemittancePageObject.submitBtn));
            addNewRemittancePageObject.submitTheRemittanceTransaction();
            wait.until(ExpectedConditions.alertIsPresent());
            
            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(2000);

            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.transactionReceipt));
            Assert.assertTrue(addNewRemittancePageObject.transactionReceipt.isDisplayed());

            driver.close();
            driver.switchTo().window(originalWindow);
            homePageObject.userLogOut();
        }
    }

    // Helper method to check if an alert is present
    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true; // Alert is present
        } catch (NoAlertPresentException e) {
            return false; // No alert present
        }
    }
}

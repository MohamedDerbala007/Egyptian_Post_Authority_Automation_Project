package Tests;

import Pages.Remittances_AddNewRemittancePage;
import Pages.Remittances_HomePage;
import Pages.Remittances_LoginPage;
import TestBases.Remittances_TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Remittances_AddNewRemittancetest extends Remittances_TestBase
{
    Remittances_LoginPage loginPageObject;
    Remittances_HomePage homePageObject;
    Remittances_AddNewRemittancePage addNewRemittancePageObject;
    String userName = "عمر ابراهيم احمد";
    String password = "q1234567890";

    @Test
    public void userCanAddNewRemittanceSuccessfully() throws InterruptedException
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPageObject = new Remittances_LoginPage(driver);
        loginPageObject.userLogin(userName, password);

        homePageObject = new Remittances_HomePage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(homePageObject.addNewRemittanceBtn));
        Assert.assertTrue(homePageObject.addNewRemittanceBtn.isDisplayed());
        homePageObject.AddNewRemittance();

        addNewRemittancePageObject = new Remittances_AddNewRemittancePage(driver);
        wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.senderIdenTypeDDL));
        addNewRemittancePageObject = new Remittances_AddNewRemittancePage(driver);
        addNewRemittancePageObject.chooseIdenTypeForSender();
        addNewRemittancePageObject.insertSenderID();
        addNewRemittancePageObject.searchForSenderID();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.receiverIdenTypeDDL));
        addNewRemittancePageObject = new Remittances_AddNewRemittancePage(driver);
        addNewRemittancePageObject.chooseIdenTypeForReceiver();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.receiverIDTxtBox));
        addNewRemittancePageObject.insertReceiverID();
        addNewRemittancePageObject.searchForReceiverID();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(addNewRemittancePageObject.bigValueTxtBox));
        addNewRemittancePageObject.insertTheBigValueofTheAmount();
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
    }
    }


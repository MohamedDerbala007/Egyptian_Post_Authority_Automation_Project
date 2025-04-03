package CashCollection.Pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CC_AddNewCashCollectionPage extends CC_PageBase
{

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	public CC_AddNewCashCollectionPage(WebDriver driver) 
	{
		super(driver);
		if (driver == null) { 
			throw new IllegalArgumentException("WebDriver instance is NULL!"); 
		}
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_LstBanks\"]")
	public WebElement bankSelectionDDL;

	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_DropDownList_Customer\"]")
	public WebElement companySelectionDDL;

	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Button_Choosedepositor\"]")
	WebElement chooseDepositorBtn;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_TxtDepNat\"]")
	WebElement recipientIdField;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Button_Choosedepositor\"]")
	WebElement recipientSearchBtn;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Cash\"]")
	WebElement cashAmountField;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Change\"]")
	WebElement changeAmountField;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Button_PrintPO\"]")
	WebElement printPayemntOrderBtn;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_btnOk\"]")
	WebElement confirmBtn;

	private void selectTheBank(String BankName)
	{
		Select select = new Select(bankSelectionDDL);
		select.selectByVisibleText(BankName);
	}

	private void selectTheCompany(String CompanyName)
	{
		Select select = new Select(companySelectionDDL);
		select.selectByVisibleText(CompanyName);
	}

	public void searchRecipient(String RecipientID)
	{
		wait.until(ExpectedConditions.visibilityOf(recipientIdField));
		clickButton(recipientIdField);
		setTextInElementTextBox(recipientIdField, RecipientID);
		clickButton(recipientSearchBtn);
	}
	
	public void insertTransferredAmount(String CashAmount, String ChangeAmount)
	{
		setTextInElementTextBox(cashAmountField, CashAmount);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", changeAmountField);
		wait.until(ExpectedConditions.visibilityOf(changeAmountField));
		clickButton(changeAmountField);
		setTextInElementTextBox(changeAmountField, ChangeAmount);
	}
	
	public void printPaymentOrder()
	{
		clickButton(printPayemntOrderBtn);
		clickButton(confirmBtn);
	}

	public void addNewCashCollection(String BankName, String CompanyName, String RecipientID, String CashAmount, String ChangeAmount) throws InterruptedException
	{
		wait.until(ExpectedConditions.visibilityOf(bankSelectionDDL));
		selectTheBank(BankName);
		wait.until(ExpectedConditions.visibilityOf(companySelectionDDL));
		selectTheCompany(CompanyName);
		wait.until(ExpectedConditions.visibilityOf(recipientIdField));
		searchRecipient(RecipientID);
		insertTransferredAmount(CashAmount, ChangeAmount);
		printPaymentOrder();
	}
}

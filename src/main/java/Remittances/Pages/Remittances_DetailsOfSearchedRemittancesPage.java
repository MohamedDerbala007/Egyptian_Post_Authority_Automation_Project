package Remittances.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Remittances_DetailsOfSearchedRemittancesPage extends PageBase
{

	public Remittances_DetailsOfSearchedRemittancesPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath = "//*[@id=\"btn_Action_Return\"]")
	public WebElement refundRemittanceBtn;
	
	@FindBy(xpath = "//*[@id=\"btn_Action_pay\"]")
	public WebElement cashOutRemittanceBtn;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_btn_RePrint\"]")
	public WebElement reprintRemittanceRecieptBtn;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_txtReceiverMobileNumber\"]")
	public WebElement receiverAndSenderPhoneNumberTxtbox;
	
	@FindBy(xpath = "//*[@id=\"_cmbUserNationalIds\"]")
	public WebElement receiverAndSenderIdenTypeDDL;
	
	@FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_txtReceiverNationalID\"]")
	public WebElement receiverAndSenderIdenTypeTxtbox;
	
	@FindBy(xpath = "//*[@id=\"_btnNationalIdSearch\"]")
	public WebElement receiverAndSenderIdenSearchBtn;
	
	public void refundRemittance()
	{
		clickButton(refundRemittanceBtn);
	}
	
	public void cashOutRemittance()
	{
		clickButton(cashOutRemittanceBtn);
	}
	
	public void reprintRemittanceReciept()
	{
		clickButton(reprintRemittanceRecieptBtn);
	}
	
	public void selectReceiverType()
	{
		Select select = new Select(receiverAndSenderIdenTypeDDL);
		select.selectByValue("2");
	}
	
	public void insertReceiverIdenNo()
	{
		receiverAndSenderIdenTypeTxtbox.sendKeys("1234");
		clickButton(receiverAndSenderIdenSearchBtn);
	}
	
	public void selectSenderType()
	{
		Select select = new Select(receiverAndSenderIdenTypeDDL);
		select.selectByValue("1");
	}
	
	public void insertSenderIdenNo()
	{
		receiverAndSenderIdenTypeTxtbox.sendKeys("29802261801751");
		clickButton(receiverAndSenderIdenSearchBtn);
	}
}

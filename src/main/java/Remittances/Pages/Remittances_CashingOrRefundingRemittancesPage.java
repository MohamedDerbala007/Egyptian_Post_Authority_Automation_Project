package Remittances.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Remittances_CashingOrRefundingRemittancesPage extends PageBase
{

    public Remittances_CashingOrRefundingRemittancesPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"_cmbUserNationalIds\"]")
    public WebElement senderIdenTypeDDL;

    @FindBy(xpath = "//*[@id=\"txt_SenderSocialNumber\"]")
    public WebElement senderIDTxtBox;
    
    @FindBy(xpath = "//*[@id=\"_cmbUserNationalIds2\"]")
    public WebElement receiverIdenTypeDDL;

    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_txt_ReceiverSocialNumber\"]")
    public WebElement receiverIDTxtBox;
    
    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_IsSender\"]")
    public WebElement senderRadioBtn;
    
    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_IsReceiver\"]")
    public WebElement receiverRadioBtn;
    
    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_btn_ser\"]")
    public WebElement searchBtn;
    
    public void searchForInstantRemittance()
    {
    	clickButton(senderRadioBtn);
    	Select selectSenderIden = new Select(senderIdenTypeDDL);
    	selectSenderIden.selectByValue("1");;
    	setTextInElementTextBox(senderIDTxtBox, "29802261801751");
    	clickButton(searchBtn);
    }
    
}
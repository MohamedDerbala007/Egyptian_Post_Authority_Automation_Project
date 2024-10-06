package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Remittances_AddNewRemittancePage extends PageBase
{

    public Remittances_AddNewRemittancePage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"IdenType\"]")
    public WebElement senderIdenTypeDDL;

    @FindBy(xpath = "//*[@id=\"TxtNationalID\"]")
    public WebElement senderIDTxtBox;

    @FindBy(xpath = "//*[@id=\"_btnNationalIdSearch\"]")
    public WebElement senderIDSearchBtn;

    @FindBy(xpath = "//*[@id=\"IdenType2\"]")
    public WebElement receiverIdenTypeDDL;

    @FindBy(xpath = "//*[@id=\"txt_ReceiverNationalId\"]")
    public WebElement receiverIDTxtBox;

    @FindBy(xpath = "//*[@id=\"_btnSearchReceiverId\"]")
    public WebElement receiverIDSearchBtn;

    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_txt_amountTotal\"]")
    public WebElement bigValueTxtBox;

    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_TafkeetBtn\"]")
    public WebElement amountConfirmationBtn;

    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_DDReasons\"]")
    public WebElement reasonForTheTransactionDDL;

    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1__btnSubmit\"]")
    public WebElement submitBtn;

    @FindBy(xpath = "//*[@id=\"Table_01\"]/center/table")
    public WebElement transactionReceipt;

    public void chooseIdenTypeForSender()
    {
        Select select = new Select(senderIdenTypeDDL);
        select.selectByValue("1");
    }

    public void insertSenderID()
    {
        senderIDTxtBox.sendKeys("29802261801751");
    }

    public void searchForSenderID()
    {
        clickButton(senderIDSearchBtn);
    }

    public void chooseIdenTypeForReceiver()
    {
        Select select = new Select(receiverIdenTypeDDL);
        select.selectByValue("2");
    }

    public void insertReceiverID()
    {
        receiverIDTxtBox.sendKeys("1234");
    }

    public void searchForReceiverID()
    {
        clickButton(receiverIDSearchBtn);
    }

    public void insertTheBigValueofTheAmount()
    {
        bigValueTxtBox.sendKeys("5000");
    }

    public void confirmTheAmount()
    {
        clickButton(amountConfirmationBtn);
    }

    public void chooseReasonForTheTransaction()
    {
        Select select = new Select(reasonForTheTransactionDDL);
        select.selectByValue("6");
    }

    public void submitTheRemittanceTransaction()
    {
        clickButton(submitBtn);
    }
}
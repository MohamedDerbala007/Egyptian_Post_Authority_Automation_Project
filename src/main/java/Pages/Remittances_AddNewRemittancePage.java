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
}
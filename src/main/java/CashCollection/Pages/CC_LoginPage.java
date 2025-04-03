package CashCollection.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.Select;

public class CC_LoginPage extends CC_PageBase
{

    public CC_LoginPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"_dropdownList_OperatorName\"]")
    WebElement userNameDDL;

    @FindBy(xpath = "//*[@id=\"_TextBox_Password\"]")
    WebElement txt_pass;

    @FindBy(xpath = "//*[@id=\"Button1\"]")
    WebElement loginBtn;

    public void userLogin(String userName , String password)
    {
        Select selectUser = new Select(userNameDDL);
        selectUser.selectByVisibleText(userName);
        setTextInElementTextBox(txt_pass, password);
        clickButton(loginBtn);
    }
}
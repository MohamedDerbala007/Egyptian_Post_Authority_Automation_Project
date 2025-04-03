package Remittances.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.Select;

public class Remittances_LoginPage extends PageBase
{

    public Remittances_LoginPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"_dropdownList_OperatorName\"]")
    WebElement userNameDDL;

    @FindBy(xpath = "//*[@id=\"txt_pass\"]")
    WebElement txt_pass;

    @FindBy(xpath = "//*[@id=\"Table_01\"]/tbody/tr[2]/td[2]/table/tbody/tr[2]/td/table/tbody/tr[1]/td[2]/table/tbody/tr/td[2]/input")
    WebElement loginBtn;

    public void userLogin(String userName , String password)
    {
        Select selectUser = new Select(userNameDDL);
        selectUser.selectByVisibleText(userName);
        setTextInElementTextBox(txt_pass, password);
        clickButton(loginBtn);
    }
}
package Pages;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Remittances_HomePage extends PageBase
{

    public Remittances_HomePage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"ctl00__btnNewRemittance\"]")
    public WebElement addNewRemittanceBtn;

    public void AddNewRemittance()
    {
        clickButton(addNewRemittanceBtn);
    }
}
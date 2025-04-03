package CashCollection.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CC_HomePage extends CC_PageBase
{

	public CC_HomePage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//*[@id=\"ctl00_HyperLink4\"]")
    public WebElement addNewCashCollectionBtn;
	
	public void openNewCashCollectionPage()
	{
		clickButton(addNewCashCollectionBtn);
	}

}

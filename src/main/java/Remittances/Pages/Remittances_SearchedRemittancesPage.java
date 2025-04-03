package Remittances.Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Remittances_SearchedRemittancesPage extends PageBase
{

	public Remittances_SearchedRemittancesPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath = "//*[@id=\"_lnkbtnRemNum\"]")
	public List<WebElement> listOfSearchedRemNum;
	
	public void openTheSearchedRemittance() 
	{
	    if (!listOfSearchedRemNum.isEmpty()) 
	    {
	        listOfSearchedRemNum.get(0).click();
	    } else 
	    {
	        System.out.println("The list is empty.");
	    }
	}
}

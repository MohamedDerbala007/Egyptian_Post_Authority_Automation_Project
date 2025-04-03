package CashCollection.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CC_PageBase
{
	protected WebDriver driver;
	public JavascriptExecutor jse;
	public Select select;
	public Actions action;

	public CC_PageBase (WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	protected static void clickButton(WebElement button)
	{
		button.click();
	}

	protected static void setTextInElementTextBox(WebElement textElement , String value)
	{
		textElement.sendKeys(value);
	}

	public void scrollToButtom()
	{
		jse.executeScript("scrollBy(0,2500)");
	}

	protected static void clearText(WebElement textElement)
	{
		textElement.clear();
	}
}
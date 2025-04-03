package CashCollection.Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class CC_ReceiptPage extends CC_PageBase
{

	public CC_ReceiptPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(id = "label_bigvalue")
    public WebElement bigValueLabel;
	
	@FindBy(id = "Label_changevalue")
    public WebElement changeValueLabel;
	
	@FindBy(id = "label_changefees") 
    public WebElement changeFeesLabel;
	
	@FindBy(id = "label_payedvalue") 
    public WebElement payedValueLabel;
	
	@FindBy(xpath = "//td[contains(text(),'أجرالخدمة')]")
    public WebElement serviceFeeElement;
	
	@FindBy(xpath = "//*[@id=\"Button_Insert\"]") 
    public WebElement insertPaymentBtn;
	
	
	
	public void cancelPrinting() throws AWTException {
	    Robot robot = new Robot();
	    robot.delay(2000); // Wait for print dialog to appear

	    // Press ESC key to cancel the print dialog
	    robot.keyPress(KeyEvent.VK_ESCAPE);
	    robot.keyRelease(KeyEvent.VK_ESCAPE);
	}
	
	public String getBigValueText() 
	{
        return bigValueLabel.getText().trim();
    }
	
	public String getChangeValueText() 
	{
        return changeValueLabel.getText().trim();
    }
	
	public String getChangeFeesText() 
	{
        return changeFeesLabel.getText().trim();
    }

	public String getPayedValueText() 
	{
        return payedValueLabel.getText().trim();
    }
	
	public String getServiceFeeText() 
	{
        return serviceFeeElement.getText().replaceAll("[^0-9]", "").trim();
    }
	
	public void insertPayment()
	{
		clickButton(insertPaymentBtn);
		Alert alert = driver.switchTo().alert();
		System.out.println("Popup Message: " + alert.getText()); 
		alert.accept();
	}
}

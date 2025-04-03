package Remittances.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Remittances_ReportsPage extends PageBase
{

    public Remittances_ReportsPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"txt_FromDt\"]")
    public WebElement txt_fromDate;
    
    @FindBy(xpath = "//*[@id=\"_cmboxReportType\"]")
    public WebElement typeOfReportDDL;
    
    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Button1\"]")
    public WebElement submitBtn;

    public void displayReport(String fromDate , String reportType)
    {
    	//setTextInElementTextBox(txt_fromDate, fromDate);
        Select selectReportType = new Select(typeOfReportDDL);
        selectReportType.selectByVisibleText(reportType);
        clickButton(submitBtn);
    }
}
package Remittances.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.Select;

public class Remittances_ExportedRemittancesReportPage extends PageBase
{

    public Remittances_ExportedRemittancesReportPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"Img_AllPagePrint\"]")
    WebElement 	printBtnOfDetailedRemittancesClosingReport;
    
    @FindBy(xpath = "//*[@id=\"Img_TotalsPrint\"]")
    WebElement 	printBtnOfTotalRemittancesClosingReport;
    
    @FindBy(xpath = "//*[@id=\"Img_TotalsPrint\"]")
    WebElement 	printBtnOfDailyStatementOfRemittancesReport;
    
    @FindBy(xpath = "//*[@id=\"backimg\"]")
    WebElement 	backBtnOfReport;
    
    @FindBy(xpath = "//*[@id=\"sidebar\"]//print-preview-button-strip//div/cr-button[2]")
    WebElement 	cancelBtnOfPrinting;

    public void printingDetailedRemittancesClosingReport()
    {
    	clickButton(printBtnOfDetailedRemittancesClosingReport);
    }
    
    public void printingTotalRemittancesClosingReport()
    {
    	clickButton(printBtnOfTotalRemittancesClosingReport);
    }
    
    public void printingDailyStatementOfRemittancesReport()
    {
    	clickButton(printBtnOfDailyStatementOfRemittancesReport);
    }
    
    public void exitTheReport()
    {
    	clickButton(backBtnOfReport);
    }
    
    public void cancelPrinting()
    {
    	clickButton(cancelBtnOfPrinting);
    }
}
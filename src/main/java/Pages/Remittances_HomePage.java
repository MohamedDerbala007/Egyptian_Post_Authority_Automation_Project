package Pages;

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
    
    @FindBy(xpath = "//*[@id=\"txt_FromDt\"]")
    public WebElement reportDateField;
    
    @FindBy(xpath = "//*[@id=\"ctl00_ContentPlaceHolder1_Button1\"]")
    public WebElement showReportBtn;
    
    @FindBy(xpath = "//*[@id=\"_cmboxReportType\"]")
    WebElement reportTypeDDL;
    
    @FindBy(xpath = "//*[@id=\"ctl00__btnMainForm\"]")
    public WebElement instantCashingOrRefundingOfRemittancesBtn;
    
    @FindBy(xpath = "//*[@id=\"ctl00_hyp_logout\"]")
    public WebElement logoutBtn;
    
    @FindBy(xpath = "//*[@id=\"ctl00_hyp_rep\"]")
    public WebElement reportsBtn;

    public void AddNewRemittance()
    {
        clickButton(addNewRemittanceBtn);
    }
    
    public void displayTheEndDayReportofExportedRemittances(String reportType)
    {
    	Select selectReportType = new Select(reportTypeDDL);
    	selectReportType.selectByVisibleText(reportType);
    	clickButton(showReportBtn);
    }
    
    public void cashingOrRefundingRemittances()
    {
    	clickButton(instantCashingOrRefundingOfRemittancesBtn);
    }
    
    public void userLogOut()
    {
    	clickButton(logoutBtn);
    }
    
    public void openReportsPage()
    {
    	clickButton(reportsBtn);
    }
}
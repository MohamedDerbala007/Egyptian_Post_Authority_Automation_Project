package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Remittances_DataOfCashingOrRefundingRemittancesReportPage extends PageBase {

    public Remittances_DataOfCashingOrRefundingRemittancesReportPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"Label3NetNew\"]")
    public WebElement transactionsCount;

    public int transactionsCountFromReport() {
        // Get the text from the WebElement
        String text = transactionsCount.getText();
        
        // Parse the text to an integer and return it
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            // Log the error or handle it appropriately
            System.err.println("Unable to parse transactions count: " + text);
            return 0; // Return 0 or an appropriate default value in case of failure
        }
    }
}

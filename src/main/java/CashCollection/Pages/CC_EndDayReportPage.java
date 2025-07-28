package CashCollection.Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CC_EndDayReportPage {

    WebDriver driver;  
    
    public CC_EndDayReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Map<String, String>> getTransactionTableData() {
        List<Map<String, String>> transactionList = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='GridView2']/tbody/tr[position() > 1]"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            Map<String, String> data = new HashMap<>();

            data.put("CustomerName", cells.get(0).getText().trim());
            data.put("EmployeeName", cells.get(1).getText().trim());
            data.put("TransactionDate", cells.get(2).getText().trim());
            data.put("NetAmount", cells.get(3).getText().trim());
            data.put("TotalFees", cells.get(4).getText().trim());
            data.put("TransferFees", cells.get(5).getText().trim());
            data.put("ChangeFees", cells.get(6).getText().trim());
            data.put("BigFees", cells.get(7).getText().trim());
            data.put("TotalValue", cells.get(8).getText().trim());
            data.put("ChangeValue", cells.get(9).getText().trim());
            data.put("BigValue", cells.get(10).getText().trim());
            data.put("CustomerNumber", cells.get(11).getText().trim());
            data.put("TransactionSerial", cells.get(12).getText().trim());
            data.put("PaymentOrder", cells.get(13).getText().trim());

            transactionList.add(data);
        }
        return transactionList;
    }
}

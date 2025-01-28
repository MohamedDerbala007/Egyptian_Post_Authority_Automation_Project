package Pages;

import org.openqa.selenium.By;
import utils.Transaction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Remittances_DataOfExportedRemittancesReportPage extends PageBase {

    // XPath Constants
    private static final String TRANSACTIONS_COUNT_XPATH = "//*[@id=\"Label3NetNew\"]";
    private static final String TRANSACTION_TABLE_XPATH = "//*[@id=\"grdv_Data\"]";
    private static final String ROWS_XPATH = ".//tr[position() > 1]";

    public Remittances_DataOfExportedRemittancesReportPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = TRANSACTIONS_COUNT_XPATH)
    private WebElement transactionsCount;

    @FindBy(xpath = TRANSACTION_TABLE_XPATH)
    private WebElement transactionTable;

    /**
     * Retrieves the total transaction count from the report.
     *
     * @return the number of transactions as an integer or 0 if invalid.
     */
    public int transactionsCountFromReport() {
        if (transactionsCount == null) {
            System.err.println("Transactions count element is not found on the page.");
            return 0;
        }

        String text = transactionsCount.getText();
        if (isNullOrEmpty(text)) {
            System.err.println("Transaction count is null or empty.");
            return 0;
        }

        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse transactions count: " + text);
            return 0;
        }
    }

    /**
     * Fetches all transactions from the transaction table.
     *
     * @return a list of transactions, or an empty list if no transactions are found.
     */
    public List<Transaction> fetchTransactionsFromTable() {
        List<Transaction> transactions = new ArrayList<>();
        
        if (transactionTable == null) {
            System.err.println("Transaction table element is not found on the page.");
            return transactions;
        }

        List<WebElement> rows;
        try {
            rows = transactionTable.findElements(By.xpath(ROWS_XPATH));
            if (rows.isEmpty()) {
                System.err.println("No rows found in the transaction table.");
                return transactions;
            }
        } catch (Exception e) {
            System.err.println("Error fetching rows from the transaction table: " + e.getMessage());
            return transactions;
        }

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() < 6) {
                System.err.println("Row does not have enough cells. Skipping row: " + row.getText());
                continue;
            }

            try {
                Transaction transaction = parseTransactionFromRow(cells);
                transactions.add(transaction);
            } catch (Exception e) {
                System.err.println("Error processing row: " + row.getText() + " | Error: " + e.getMessage());
            }
        }
        return transactions;
    }

    /**
     * Parses a Transaction object from a row's cells.
     *
     * @param cells the list of cell elements in the row.
     * @return a Transaction object.
     */
    private Transaction parseTransactionFromRow(List<WebElement> cells) {
        return new Transaction(
            cells.get(6).getText().trim(),  
            parseDoubleSafely(cells.get(3).getText().trim(), "Amount"),  
            parseDoubleSafely(cells.get(2).getText().trim(), "Fees"), 
            cells.get(4).getText().trim(),
            cells.get(5).getText().trim(),
            cells.get(1).getText().trim()
        );
    }

    /**
     * Safely parses a string into a double, with error handling.
     *
     * @param text      the string to parse.
     * @param fieldName the field name for error logging.
     * @return the parsed double, or 0.0 if parsing fails.
     */
    private double parseDoubleSafely(String text, String fieldName) {
        if (isNullOrEmpty(text)) {
            System.err.println(fieldName + " field is null or empty.");
            return 0.0;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse " + fieldName + ": " + text);
            return 0.0;
        }
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param text the string to check.
     * @return true if null or empty, false otherwise.
     */
    private boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}

package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // Connection method to connect to SQL Server
    public Connection connectToDB() throws Exception {
        String url = "jdbc:sqlserver://172.26.2.48\\test;databaseName=CenterPost";
        String user = "TestUser";
        String password = "TestUser_te$T";

        // Load SQL Server JDBC driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("SQL Server JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
            throw new Exception("SQL Server JDBC Driver not found", e);
        }

        // Establish connection and check if successful
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the database.");
            return conn;
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            throw e;
        }
    }

    // Method to execute a query and return results
    public ResultSet getTransactionData(String query) throws Exception {
        Connection conn = connectToDB();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Method to execute a count query, making it flexible for different tests
    public int executeCountQuery(String query) {
        int count = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Execute the passed query
            conn = connectToDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                count = rs.getInt(1);  // Getting the count from the first column
            }
        } catch (Exception e) {
            System.out.println("Error while executing the count query.");
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            closeResources(conn, stmt, rs);
        }

        return count;
    }

    // Method to execute a transaction query and return a list of Transaction objects
    public List<Transaction> executeTransactionQuery(String query) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectToDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getString("PostNum"), // Column name for transaction number
                        rs.getDouble("Amount"),            // Column name for amount
                        rs.getDouble("Fees"),              // Column name for fees
                        rs.getString("Receiver"),      // Column name for receiver name
                        rs.getString("Sender"), 
                        rs.getString("MailboxedDate")
                );
                transactions.add(transaction);
            }
        } catch (Exception e) {
            System.out.println("Error while executing the transaction query.");
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            closeResources(conn, stmt, rs);
        }

        return transactions;
    }

    // Helper method to close resources
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            System.out.println("Error closing resources.");
            ex.printStackTrace();
        }
    }
}

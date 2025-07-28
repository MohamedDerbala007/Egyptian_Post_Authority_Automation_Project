package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    public static List<Map<String, String>> getQueryResults(String query) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://172.26.2.48\\test;databaseName=CashCollectionNew", "TestUser", "TestUser_te$T");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(meta.getColumnName(i), rs.getString(i));
            }
            results.add(row);
        }

        rs.close();
        stmt.close();
        conn.close();
        return results;
    }
}


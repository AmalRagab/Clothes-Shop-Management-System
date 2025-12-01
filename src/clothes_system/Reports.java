/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;
import static clothes_system.DBconnector.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author soft zone
 */
public class Reports {
     private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;
    public static List<Map<String, Object>> topRevenueUser() {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT P.Name, P.Contact_Info, COUNT(O.OID) AS no_OF_Orders, SUM(Total_Price) AS total_Price " +
                       "FROM Person P INNER JOIN Orders O ON O.CID = P.ID " +
                       "GROUP BY P.ID ORDER BY no_OF_Orders DESC LIMIT 1";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Name", rs.getString("Name"));
                row.put("Contact_Info", rs.getString("Contact_Info"));
                row.put("no_OF_Orders", rs.getInt("no_OF_Orders"));
                row.put("total_Price", rs.getDouble("total_Price"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
        public static List<Map<String, Object>> topOrderUser() {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT P.Name, P.Contact_Info, COUNT(O.OID) AS no_OF_Orders " +
                       "FROM Person P INNER JOIN Orders O ON O.CID = P.ID " +
                       "GROUP BY P.ID ORDER BY no_OF_Orders DESC LIMIT 1";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Name", rs.getString("Name"));
                row.put("Contact_Info", rs.getString("Contact_Info"));
                row.put("no_OF_Orders", rs.getInt("no_OF_Orders"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
        public static List<Map<String, Object>> allOrdersInYears() {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT SUBSTR(DATE,-4) AS year, COUNT(ID) AS no_of_orders FROM Orders GROUP BY year";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("year", rs.getString("year"));
                row.put("no_of_orders", rs.getInt("no_of_orders"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
        public static List<Map<String, Object>> totalPriceInYears() {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT SUBSTR(DATE,-4) AS year, SUM(Total_price) AS total_year_price FROM Orders GROUP BY year";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("year", rs.getString("year"));
                row.put("total_year_price", rs.getDouble("total_year_price"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

        public static List<Map<String, Object>> payment_Methods_Prices() {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT Payment_Method, SUM(Total_Price) AS Payment_Prices FROM Orders GROUP BY Payment_Method ORDER BY Payment_Prices DESC";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Payment_Method", rs.getString("Payment_Method"));
                row.put("Payment_Prices", rs.getDouble("Payment_Prices"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

       public static List<Map<String, Object>> getTopNSoldProducts() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT P.ID, SUM(O.Desired_Quantity) AS TotalSoldQty FROM Product P INNER JOIN OrderItem O ON P.ID=O.PID GROUP BY P.ID ORDER BY TotalSoldQty DESC";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("ID", rs.getInt("ID"));
                row.put("TotalSoldQty", rs.getInt("TotalSoldQty"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
       public static List<Map<String, Object>> bestSupplier() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT P.SID, s.Name, SUM(O.Desired_Quantity) AS TotalSoldQty " +
                     "FROM Product P INNER JOIN OrderItem O ON P.ID=O.PID " +
                     "INNER JOIN Person s ON s.ID=P.SID " +
                     "GROUP BY P.SID ORDER BY TotalSoldQty DESC LIMIT 1";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("SID", rs.getInt("SID"));
                row.put("Name", rs.getString("Name"));
                row.put("TotalSoldQty", rs.getInt("TotalSoldQty"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

       public static List<Map<String, Object>> totalRevenue() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT SUM(Total_Price) AS total_revenue FROM Orders";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("total_revenue", rs.getDouble("total_revenue"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Map<String, Object>> LowStockAlert() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT ID, Name FROM Product WHERE Quantity < 6";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("ID", rs.getInt("ID"));
                row.put("Name", rs.getString("Name"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
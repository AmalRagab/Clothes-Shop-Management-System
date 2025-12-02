/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author soft zone
 */
public class Order_DBO {
    public static ObservableList<Order> getOrdersByCustomerId(int customerId) {
    ObservableList<Order> list = FXCollections.observableArrayList();
    
    String sql = "SELECT * FROM Orders WHERE CID = ?"; 
    
    try (Connection connection = DBconnector.connect()) {
        System.out.println("✓ Database connection successful");
        System.out.println("Executing query: " + sql + " with CID=" + customerId);
        
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, customerId);
            
            try (ResultSet rs = pst.executeQuery()) {
                int orderCount = 0;
                while (rs.next()) {
                    orderCount++;
                    System.out.println("✓ Found order #" + orderCount);
                    
                    Order o = new Order();
                    
                    // Get ID
                    int orderId = rs.getInt("ID");
                    o.setId(orderId);
                    System.out.println("  Order ID: " + orderId);
                    
                    // Get date
                    String dateStr = rs.getString("Date");
                    System.out.println("  Date string from DB: '" + dateStr + "'");
                    
                    if (dateStr != null && !dateStr.isEmpty()) {
                        try {
                            // Remove single quotes if present
                            dateStr = dateStr.replace("'", "");
                            
                            // Parse the date
                            java.text.SimpleDateFormat format;
                            java.util.Date parsedDate = null;
                            
                            // Try different date formats
                            try {
                                format = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                parsedDate = format.parse(dateStr);
                            } catch (java.text.ParseException e1) {
                                try {
                                    format = new java.text.SimpleDateFormat("dd/MM/yyyy");
                                    parsedDate = format.parse(dateStr);
                                } catch (java.text.ParseException e2) {
                                    System.out.println("  ⚠ Could not parse date: " + dateStr);
                                    parsedDate = new java.util.Date();
                                }
                            }
                            
                            o.setDate(parsedDate);
                            System.out.println("  Parsed date: " + parsedDate);
                            
                        } catch (Exception e) {
                            System.out.println("  ⚠ Error parsing date '" + dateStr + "': " + e.getMessage());
                            o.setDate(new java.util.Date());
                        }
                    } else {
                        o.setDate(new java.util.Date());
                    }
                    
                    // Get total price
                    double totalPrice = rs.getDouble("Total_Price");
                    o.setTotal_price(totalPrice);
                    System.out.println("  Total Price: " + totalPrice);
                    
                    // Get discount
                    float discount = rs.getFloat("Discount");
                    o.setDisount(discount);
                    System.out.println("  Discount: " + discount);
                    
                    // Get payment method
                    String method = rs.getString("Payment_Method");
                    System.out.println("  Payment Method from DB: '" + method + "'");
                    
                    if (method != null) {
                        try {
                            method = method.replace("'", "");
                            o.setPayment_method(Order.PaymentMethod.valueOf(method.toUpperCase()));
                            System.out.println("  Payment Method: " + method);
                        } catch (IllegalArgumentException e) {
                            System.out.println("  ⚠ Payment Method '" + method + "' not recognized, using CASH");
                            o.setPayment_method(Order.PaymentMethod.CASH);
                        }
                    } else {
                        o.setPayment_method(Order.PaymentMethod.CASH);
                    }
                    
                    // Get calculated price
                    double calculatedPrice = rs.getDouble("Calculated_Price");
                    o.setCalculated_price(calculatedPrice);
                    System.out.println("  Calculated Price: " + calculatedPrice);
                    
                    // ===== FIXED: Set CID field =====
                    // The CID in the database should match the customerId parameter
                    int dbCid = rs.getInt("CID");
                    o.setCid(dbCid);
                    System.out.println("  Customer ID (CID): " + dbCid);

                    // Set CAID (Cashier ID)
                    int caid = rs.getInt("CAID");
                    o.setCaid(caid);
                    System.out.println("  Cashier ID (CAID): " + caid);
                    // Note: Order class doesn't have a setCaid() method, so we need to add it
                    
                    list.add(o);
                    System.out.println("✓ Added order to list: " + o);
                }
                
                System.out.println("\nTotal orders found: " + orderCount);
            }
        }
        
    } catch (SQLException e) {
        System.out.println("❌ ERROR: SQL Exception: " + e.getMessage());
        e.printStackTrace();
    }
    
    System.out.println("DEBUG: Returning " + list.size() + " orders for customer ID " + customerId);
    
    return list;
}
    
    
    
    // Helper method to get row count
    private static int getRowCount(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    

}

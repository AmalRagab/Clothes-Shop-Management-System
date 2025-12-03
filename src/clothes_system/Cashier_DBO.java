
package clothes_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Cashier_DBO {
    
    public static void addOrder(Order o){
    try (Connection conn = DBconnector.connect()) {
        String insertOrderSQL = "INSERT INTO Orders (ID, Date, Discount, Payment_Method, Calculated_Price, Total_Price, CID, CAID) "
                              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertOrderSQL);
        ps.setInt(1, o.getId());
        ps.setString(2, o.getDate().toString());
        ps.setFloat(3, o.getDiscount());
        ps.setString(4, o.getPayment_method());
        ps.setDouble(5, o.getCalculated_price());
        ps.setDouble(6, o.getTotal_price());
        ps.setInt(7, o.getCustomerId());
        ps.setInt(8, o.getCashierId());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void addOrderItem(OrderItems oi ,int orderId){
    products pr = new products();
            Product p = pr.searchProduct(oi.getProductId());
    try (Connection conn = DBconnector.connect()) {
        String insertItemSQL = "INSERT INTO OrderItem (PID, OID, Desired_Quantity, Total_Price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertItemSQL);
        ps.setInt(1, oi.getProductId());
        ps.setInt(2, orderId);
        ps.setInt(3, oi.getDesired_quantity());
        ps.setDouble(4,oi.getDesired_quantity()*p.getPrice());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public double calculateTotalPrice(ArrayList<OrderItems> tempItems ) {
        double total = 0;
        for (OrderItems item : tempItems)
            total += item.getTotal_price();
        return total;
    }
    
    
    public static ObservableList<ObservableList<String>> getAllCashierInfoForTable(String cashierEmail) {
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

    String sql = "SELECT O.ID, O.CID, O.Total_Price,"+
                 "O.Payment_Method, O.Date "+
                 "FROM Orders O INNER JOIN User U ON O.CAID=U.UID "+
                 "WHERE Email = ?";  

    
    try (Connection conn = DBconnector.connect();   
         PreparedStatement ps = conn.prepareStatement(sql)){
           ps.setString(1, cashierEmail);
        try( ResultSet rs = ps.executeQuery()) {
          
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
             //  ID
            row.add(rs.getString("ID")); 
            
            //  CID 
            row.add(rs.getString("CID")); 
            
            //  Total_Price 
            row.add(rs.getString("Total_Price")); 
            
            //  Payment_Method 
            row.add(rs.getString("Payment_Method")); 
            
            //  Date 
            row.add(rs.getString("Date")); 


            data.add(row);
        }
         }
    } catch (SQLException e) {
        System.err.println("SQL Error while loading Cashier Orders: " + e.getMessage());
        e.printStackTrace();
    }
    return data;
}
    

}
    
    
    

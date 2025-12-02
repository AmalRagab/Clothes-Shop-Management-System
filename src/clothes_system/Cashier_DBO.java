/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package clothes_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Cashier_DBO {
    
    
    public static ObservableList<ObservableList<String>> getAllCashierInfoForTable() {
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

    String sql = "SELECT O.ID, O.CID, O.Total_Price, " +
                 "O.Payment_Method, O.Date " +
                 "FROM Orders O " ;  

    
    try (Connection conn = DBconnector.connect();
            
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

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

    } catch (SQLException e) {
        System.err.println("SQL Error while loading Cashier Orders: " + e.getMessage());
        e.printStackTrace();
    }
    return data;
}
    

}
    
    



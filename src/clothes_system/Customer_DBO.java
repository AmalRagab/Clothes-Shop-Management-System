/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;


import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import clothes_system.Person.Type;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer_DBO {

    //--------------------------------------------------------------------------------------------------------------------
     
    // SIMPLE addCustomer method that will work
    public static boolean addCustomer(Customer c) {
        System.out.println("\n=== Adding customer: " + c.getName() + " ===");
        
        try (Connection connection = DBconnector.connect()) {
            // Just add to Person table
            String sql = "INSERT INTO Person(Name, Contact_Info, Type) VALUES(?, ?, 'CUSTOMER')";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, c.getName());
                pst.setString(2, c.getContact_info());
                pst.executeUpdate();
                System.out.println("✓ Added to Person table");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        
        // Use LEFT JOIN to get all customers even if Customer table has issues
        String sql = "SELECT p.ID, p.Name, p.Contact_Info " +
                     "FROM Person p " +
                     "WHERE p.Type = 'CUSTOMER' " +
                     "ORDER BY p.ID";

        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("ID")); 
                c.setName(rs.getString("Name"));
                c.setContact_info(rs.getString("Contact_Info"));
                list.add(c);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Database cleanup method
    public static void fixCustomerTable() {
        System.out.println("\n=== Fixing Customer Table ===");
        
        try (Connection connection = DBconnector.connect()) {
            // Remove orphaned Customer records
            String cleanupSql = "DELETE FROM Customer WHERE CID NOT IN (SELECT ID FROM Person)";
            try (Statement stmt = connection.createStatement()) {
                int deleted = stmt.executeUpdate(cleanupSql);
                System.out.println("Removed " + deleted + " orphaned Customer records");
            }
            
            System.out.println("Database cleanup complete!");
            
        } catch (SQLException e) {
            System.out.println("Error fixing Customer table: " + e.getMessage());
        }
    }
    
    public List<Customer> searchCustomer(String key) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT p.ID, p.Name, p.Contact_Info " +
                     "FROM Person p " +
                     "WHERE p.Type = 'CUSTOMER' AND (p.Name LIKE ? OR p.Contact_Info LIKE ?)";

        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            String N = "%" + key + "%";
            pst.setString(1, N);
            pst.setString(2, N);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                  Customer c = new Customer();
                c.setId(rs.getInt("ID")); 
                c.setName(rs.getString("Name"));
                c.setContact_info(rs.getString("Contact_Info"));
                list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
   
    public static boolean updateCustomer(Customer c) {
        String sql = "UPDATE Person SET Name = ?, Contact_Info = ? WHERE ID = ?";
        
        try (Connection connection = DBconnector.connect();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, c.getName());
            pst.setString(2, c.getContact_info());
            pst.setInt(3, c.getId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCustomer(int customerId) {
        try (Connection connection = DBconnector.connect()) {
            connection.setAutoCommit(false);

            // Delete from Customer table first
            String sqlCustomer = "DELETE FROM Customer WHERE CID = ?";
            try (PreparedStatement pstCust = connection.prepareStatement(sqlCustomer)) {
                pstCust.setInt(1, customerId);
                pstCust.executeUpdate();
            }

            // Then delete from Person table
            String sqlPerson = "DELETE FROM Person WHERE ID = ?";
            try (PreparedStatement pstPerson = connection.prepareStatement(sqlPerson)) {
                pstPerson.setInt(1, customerId);
                pstPerson.executeUpdate();
            }

            connection.commit();
            System.out.println("Customer deleted successfully from both tables");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete customer");
            return false;
        }
    }


}
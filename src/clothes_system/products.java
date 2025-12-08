/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;


public class products {

    // Get all products
    public ObservableList<Product> getAllProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("ID"),
                        rs.getInt("SID"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getString("Colour")
                );
                productList.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Delete product
    public void deleteProducts(int id) {
        String sql = "DELETE FROM product WHERE ID = ?";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update product
    // Update product
    public boolean updateProduct(Product p) {
        String sql = "UPDATE product SET Name=?, Price=?, Quantity=?, Status=?, SID=?, Colour=? WHERE ID=?";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getSupplierID());
            ps.setString(6, p.getColour());
            ps.setInt(7, p.getId());

            ps.executeUpdate();
             return true;
        }       
        catch (SQLException e) {
    // Check which trigger caused the error
    String msg = e.getMessage();
   
    
    if (msg.contains("Supplier does not exist!")) {
        showAlert("Error", "Supplier does not exist!");
          return false;
    }
    else {
        showAlert("Error", "Database error: " + msg);
          return false;
    }
   
}
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Add product
    public boolean addProduct(Product p) {
        
        String sql = "INSERT INTO product (ID,Name, Price, Quantity, Status, SID, Colour) VALUES (?,?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getStatus());
            ps.setInt(6, p.getSupplierID());
            ps.setString(7, p.getColour());
            ps.executeUpdate();
             return true;
        }
          catch (SQLException e) {
    // Check which trigger caused the error
    String msg = e.getMessage();
    if (msg.contains("Product ID already exists!")) {
        showAlert("Error", "Product ID already exists!");
    }
    else if (msg.contains("Duplicate name and colour!")) {
        showAlert("Error", "Duplicate name and colour!");
    }
    else if (msg.contains("Supplier does not exist!")) {
        showAlert("Error", "Supplier does not exist!");
    }
    else {
        showAlert("Error", "Database error: " + msg);
    }
    return false;
}
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
      private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
      public Product searchProduct( int productId){
         String sql = "SELECT ID, Name, Price, Quantity, Status, SID, Colour FROM Product WHERE ID = ?";

    try (Connection conn = DBconnector.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, productId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Product(
                        rs.getInt("ID"),
                        rs.getInt("SID"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getString("Colour")
                );
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
     }
}
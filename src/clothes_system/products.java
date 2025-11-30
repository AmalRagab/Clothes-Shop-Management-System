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
    public void updateProduct(Product p) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add product
    public void addProduct(Product p) {
        String sql = "INSERT INTO product (Name, Price, Quantity, Status, SID, Colour) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getSupplierID());
            ps.setString(6, p.getColour());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
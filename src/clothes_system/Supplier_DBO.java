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

public class Supplier_DBO {
    
    public static boolean addPerson(Person p){
        String sql ="INSERT INTO Person(Name,Contact_Info,Type) VALUES(?,?,?)";
        try(Connection connection=DBconnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,p.getName());
            ps.setString(2,p.getContact_info());
            ps.setString(3,p.getType());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
    
    }

    public static boolean deletePerson(int id){
       
    String sql = "DELETE FROM Person WHERE ID = ?";
    try (Connection connection = DBconnector.connect();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        int affectedRows = ps.executeUpdate();
        
        return affectedRows > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
   }
    
    public static boolean updatePerson(int id, String newName, String newContact){
    String sql = "UPDATE Person SET Name = ?, Contact_Info = ? WHERE ID = ?";

    try (Connection conn = DBconnector.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, newName);
        ps.setString(2, newContact);
        ps.setInt(3, id);

        int rows = ps.executeUpdate();
        return rows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public static Person searchPerson(String searchTerm) {
    Integer id = null;
    try {
        id = Integer.parseInt(searchTerm);
    } catch (NumberFormatException e) {
        // ignore, searchTerm is not ID
    }

    try (Connection conn = DBconnector.connect()) {

        String sqlSupplier =
            "SELECT p.ID, p.Name, p.Contact_Info, p.Type AS PersonType " +
            "FROM Person p " +
            "JOIN Supplier s ON p.ID = s.SID " +
            "WHERE (? IS NOT NULL AND p.ID = ?) OR p.Name = ? OR p.Contact_Info = ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlSupplier)) {

            ps.setObject(1, id);
            ps.setObject(2, id);
            ps.setString(3, searchTerm);
            ps.setString(4, searchTerm);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Person.Type personType =
                        Person.Type.valueOf(rs.getString("PersonType").toUpperCase());

                    return new Person(
                        rs.getString("Name"),
                        rs.getString("Contact_Info"),
                        personType
                    );
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
    }

    public static ObservableList<ObservableList<String>> getAllPersonsForTable() {
     ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

    String sql = "SELECT p.ID, p.Name, p.Contact_Info, p.Type " +
                 "FROM Person p " +
                 "INNER JOIN Supplier s ON p.ID = s.SID";

    try (Connection conn = DBconnector.connect();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(rs.getString("ID"));
            row.add(rs.getString("Name"));
            row.add(rs.getString("Contact_Info"));
            row.add(rs.getString("Type"));
            data.add(row);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return data;
    

}
    
     public static ObservableList<ObservableList<String>> searchUsersForTable(String searchTerm) {
    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    String key = "%" + searchTerm + "%";

    try (Connection conn = DBconnector.connect()) {
        String sqlSupplier = "SELECT p.ID, p.Name, p.Contact_Info " +
                             "FROM Person p JOIN Supplier s ON p.ID = s.SID " +
                             "WHERE CAST(p.ID AS TEXT) LIKE ? OR p.Name LIKE ? OR p.Contact_Info LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlSupplier)) {
            ps.setString(1, key); // ID
            ps.setString(2, key); // Name
            ps.setString(3, key); // Contact_Info
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    row.add(rs.getString("ID"));
                    row.add(rs.getString("Name"));
                    row.add(rs.getString("Contact_Info"));
                    data.add(row);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return data;
}


}

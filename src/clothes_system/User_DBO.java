/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clothes_system;

import clothes_system.Person.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DBO {
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
 public static boolean addUser(User u) {
    Person p = new Person(u.getName(), u.getContact_info(), Type.USER);
     addPerson(p);
    
    String sqlUpdateUser = "UPDATE User SET Email = ?, Password = ?, Type = ?, Salary = ? WHERE UID = ?";
    
    try (Connection connection = DBconnector.connect();
         PreparedStatement ps = connection.prepareStatement(sqlUpdateUser)) {
        
        ps.setString(1, u.getEmail());
        ps.setString(2, u.getPassword());
        ps.setString(3, u.getUtype()); 
        ps.setDouble(4, u.getSalary());
        ps.setInt(5, p.getId()); 
        
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
        
    } catch (SQLException e) {
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
    public static boolean updateUser(int id, String newName, String newContact,String newEmail , String newPassword ,double newSalary){
        updatePerson(id,newName,newContact);
        String sql = "UPDATE User SET Email = ?, Password = ?, Salary = ? WHERE UID = ?";

    try (Connection conn = DBconnector.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, newEmail);
        ps.setString(2, newPassword);
        ps.setDouble(3, newSalary);
        ps.setInt(4, id);

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;

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
        
    }

    try (Connection conn = DBconnector.connect()) {

        String sqlUser = "SELECT p.ID, p.Name, p.Contact_Info, p.Type AS PersonType, u.Type AS UserType, u.Email, u.Password, u.Salary " +
                         "FROM Person p JOIN User u ON p.ID = u.UID " +
                         "WHERE (? IS NOT NULL AND p.ID = ?) OR p.Name = ? OR p.Contact_Info = ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setObject(1, id);
            ps.setObject(2, id);
            ps.setString(3, searchTerm);
            ps.setString(4, searchTerm);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    Person.Type personType = Person.Type.valueOf(rs.getString("PersonType").toUpperCase());
                    User.Utype userType = User.Utype.valueOf(rs.getString("UserType").toUpperCase());

                    return new User(
                        
                        rs.getString("Name"),
                        rs.getString("Contact_Info"),
                        personType,
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getDouble("Salary"),
                        userType
                    );
                }
            }
        }

        String sqlPerson = "SELECT p.ID, p.Name, p.Contact_Info, p.Type AS PersonType " +
                           "FROM Person p " +
                           "LEFT JOIN Supplier s ON p.ID = s.SID " +
                           "LEFT JOIN Customer c ON p.ID = c.CID " +
                           "WHERE ((? IS NOT NULL AND p.ID = ?) OR p.Name = ? OR p.Contact_Info = ?) " +
                           "AND (s.SID IS NOT NULL OR c.CID IS NOT NULL)";

        try (PreparedStatement ps = conn.prepareStatement(sqlPerson)) {
            ps.setObject(1, id);
            ps.setObject(2, id);
            ps.setString(3, searchTerm);
            ps.setString(4, searchTerm);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Person.Type personType = Person.Type.valueOf(rs.getString("PersonType").toUpperCase());
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

    String sql = "SELECT p.ID, p.Name, p.Contact_Info, " +
                 "u.Email, u.Salary, u.Type as Role " +
                 "FROM Person p " +
                 "INNER JOIN User u ON p.ID = u.UID";  

    try (Connection conn = DBconnector.connect();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(rs.getString("ID"));
            row.add(rs.getString("Name"));
            row.add(rs.getString("Contact_Info"));
            row.add(rs.getString("Role"));
            row.add(rs.getString("Salary") != null ? rs.getString("Salary") : "");
            row.add(rs.getString("Email") != null ? rs.getString("Email") : "");

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
        String sqlUser = "SELECT p.ID, p.Name, p.Contact_Info, u.Type AS UserType, u.Email, u.Salary " +
                         "FROM Person p JOIN User u ON p.ID = u.UID " +
                         "WHERE CAST(p.ID AS TEXT) LIKE ? OR p.Name LIKE ? OR p.Contact_Info LIKE ? OR u.Email LIKE ? OR CAST(u.Salary AS TEXT) LIKE ? OR u.Type LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setString(1, key); // ID
            ps.setString(2, key); // Name
            ps.setString(3, key); // Contact_Info
            ps.setString(4, key); // Email
            ps.setString(5, key); // Salary
            ps.setString(6, key); // Role (UserType)
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    row.add(rs.getString("ID"));
                    row.add(rs.getString("Name"));
                    row.add(rs.getString("Contact_Info"));
                    row.add(rs.getString("UserType")); // role
                    row.add(rs.getString("Salary"));
                    row.add(rs.getString("Email"));
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
  

  

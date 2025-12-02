/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clothes_system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author soft zone
 */
import clothes_system.DBconnector;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInController implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Text signInText;
    @FXML
    private Text emailText;
    @FXML
    private Text passText;
    @FXML
    private Button signInBtn;
    @FXML
    private Label errormsg;

    

    @FXML
private void signInAction() throws IOException {
    errormsg.setVisible(false);
    String email = emailField.getText();
    String password = passField.getText();

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        conn = DBconnector.connect();
        String sql = "SELECT Email, Password, Type FROM \"User\" WHERE Email = ? AND Password = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, password);
        rs = pst.executeQuery();

        if (rs.next()) {
            String typeDB = rs.getString("Type");
            System.out.println("Login successful! User type: " + typeDB);
            
            if (typeDB.equalsIgnoreCase("ADMIN")) {
                AdminController.receive_Info(send_Info(email,password));
                chooseAdmin();
            } else {
                CashierController.receive_Info(send_Info(email,password));
                chooseCashier();
            }
        } else {
            errormsg.setVisible(true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // غلق كل شيء لتجنب الـ database locked
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (pst != null) pst.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}

    private void chooseAdmin() throws IOException{
        
         Stage stage = (Stage) signInBtn.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();

        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(root); 
        stage.setScene(scene);
         stage.setMaximized(true);
        stage.show();

        
    }
    private void chooseCashier() throws IOException{
        Stage stage = (Stage) signInBtn.getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();

        Parent root = FXMLLoader.load(getClass().getResource("Cashier.fxml"));
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
    }
    
    public List<String> send_Info(String email,String pass) {
    List<String> info = new ArrayList<>();
    info.add(email); 
    info.add(pass);            
    return info;
}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}

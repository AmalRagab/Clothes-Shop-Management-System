
package clothes_system;

import static clothes_system.AdminController.rec_Email;
import static clothes_system.AdminController.rec_Pass;
import static clothes_system.Cashier_DBO.getAllCashierInfoForTable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CashierController implements Initializable {

    // ================= TableView Cashier's Orders =================
    @FXML private TableView<ObservableList<String>> CashierOrders;
    @FXML private TableColumn<ObservableList<String>, String> OID;
    @FXML private TableColumn<ObservableList<String>, String> CID;
    @FXML private TableColumn<ObservableList<String>, String> totalAmount;
    @FXML private TableColumn<ObservableList<String>, String> Payment;
    @FXML private TableColumn<ObservableList<String>, String> Date;
   
    static String rec_Email;
    static String rec_Pass;
    public static void receive_Info(List<String> info) {
    rec_Email = info.get(0);
    rec_Pass = info.get(1);
   
}
    

    
 

    @FXML Text cashierName;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            cashierName.setText(User_DBO.getCashierName(rec_Email, rec_Pass));
            loadOrderData();
        } catch (SQLException ex) {
            Logger.getLogger(CashierController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }    
    private void loadOrderData() throws SQLException {
        ObservableList<ObservableList<String>> data = Cashier_DBO.getAllCashierInfoForTable();
        
       OID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));

    //(CID)
    CID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
    
    // (Total Amount)
    totalAmount.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
    
    //(Payment)
    Payment.setCellValueFactory(param ->  new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
    
    // (Date)
    Date.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)));
   
        CashierOrders.setItems(data);
   
        System.out.println("Data rows assigned to Table: " + CashierOrders.getItems().size());
        
    }
    
     @FXML
     private Label CashierOrdersChoose;
      @FXML 
       private AnchorPane CashierOrderPane;
    
 @FXML
    private void reportsDisplay() {
        CashierOrderPane.setVisible(true);
       
    }
    
     @FXML private Label createOrderChoose;
      @FXML private AnchorPane createOrderPane;
    @FXML
    private void showEmployeesPane(){
        createOrderPane.setVisible(true);
        
        CashierOrderPane.setVisible(false);
        
    }
    @FXML
    private void returnToSignIn(MouseEvent e ) throws IOException{
    Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        double width = stage.getWidth();
        double height = stage.getHeight();

        Scene scene = new Scene(root, width, height); 
        stage.setScene(scene);
        stage.show();

    }
}

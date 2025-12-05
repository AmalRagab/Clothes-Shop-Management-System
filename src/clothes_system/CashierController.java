
package clothes_system;

import static clothes_system.Cashier_DBO.getAllCashierInfoForTable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CashierController implements Initializable {
    
      @FXML private Label CashierOrdersChoose;
      @FXML private Label createOrderChoose;
      @FXML private AnchorPane CashierOrderPane;
      @FXML private AnchorPane createOrderPane;

    // ================= TableView Cashier's Orders =================
    @FXML private TableView<ObservableList<String>> CashierOrders;
    @FXML private TableColumn<ObservableList<String>, String> OID;
    @FXML private TableColumn<ObservableList<String>, String> CID;
    @FXML private TableColumn<ObservableList<String>, String> totalAmount;
    @FXML private TableColumn<ObservableList<String>, String> Payment;
    @FXML private TableColumn<ObservableList<String>, String> Date;
   // ================= Create order =================
    @FXML private AnchorPane addProductPane;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private TextField productIdField ;
    @FXML private TextField cashierIdField;
    @FXML private TextField discountField;
    @FXML private TextField customerPhoneField;
    @FXML private TextField customerNameField;
    
    @FXML private AnchorPane orderDetailsPane;
    @FXML private RadioButton cashRb;
    @FXML private RadioButton creditRb;
    @FXML private javafx.scene.control.ToggleGroup paymentMethod;

    @FXML private Button printInvoiceBtn;
    @FXML private AnchorPane invoicePane;
    @FXML private TextArea invoiceArea;
    @FXML private Button closeBtn;
    
    
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
          } catch (SQLException ex) {
              Logger.getLogger(CashierController.class.getName()).log(Level.SEVERE, null, ex);
          }
      loadOrderData();
      closeAll();
      // spinner initialization 
      SpinnerValueFactory<Integer> valueFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

    quantitySpinner.setValueFactory(valueFactory);

    quantitySpinner.setEditable(true);
    }    
    private void loadOrderData() {
        ObservableList<ObservableList<String>> data = Cashier_DBO.getAllCashierInfoForTable(rec_Email);
        
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
    private void returnToSignIn(MouseEvent e ) throws IOException{
    Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        double width = stage.getWidth();
        double height = stage.getHeight();

        Scene scene = new Scene(root, width, height); 
        stage.setScene(scene);
        stage.show();

    }
    
   
    
 @FXML
    private void reportsDisplay() {
        closeAll();
        CashierOrderPane.setVisible(true);
    }
    @FXML AnchorPane createCustomerPane;
    @FXML
    private void showCustomerPane(){
        closeAll();
        createCustomerPane.setVisible(true);
 
    
    }
     
     @FXML
     private void showEmployeesPane(){
        closeAll();
     createOrderPane.setVisible(true);
     addProductPane.setVisible(true);
     
        
    }
    ArrayList<OrderItems> orderItemsList = new ArrayList<>();      // order items list
    @FXML
    private void nextBtnClicked() {
    int pId = Integer.parseInt(productIdField.getText());
    int quantity = quantitySpinner.getValue();
    //  create product items list ,add product items in it
    products pr = new products();
    Product p = pr.searchProduct(pId);
    
    orderItemsList.add(new OrderItems(quantity, p.getPrice() * quantity, p.getId()));
    // clear the fields for add again
    productIdField.clear();
    quantitySpinner.getValueFactory().setValue(1);
    
}
    @FXML
    private void finishBtnClicked() {
        closeAll();
     createOrderPane.setVisible(true);
     orderDetailsPane.setVisible(true);
        
    }
    @FXML
    private void printInvoiceClicked() {
    Cashier_DBO cd = new  Cashier_DBO();
        double totalPrice = cd.calculateTotalPrice(orderItemsList);
        Order.PaymentMethod paymentMethod = cashRb.isSelected()? Order.PaymentMethod.CASH: Order.PaymentMethod.CREDIT;
        Date date = new Date();
        double discount = Double.parseDouble(discountField.getText().trim());
        double calculatedPrice = totalPrice-totalPrice*discount;
        String phone = customerPhoneField.getText();
        User_DBO ud = new User_DBO();
        Person p = ud.searchPerson(phone);
        int customerId;
        if (p==null){
            customerNameField.setVisible(true);
            String name = customerNameField.getText();
            Person cus = new Person(name , phone,Person.Type.CUSTOMER);
            ud.addPerson(cus);
            customerId = cus.getId();
        }else{
            customerNameField.setVisible(true);
            customerNameField.setText(p.getName());
            customerId = p.getId();
        }
        int cashierId = Integer.parseInt(cashierIdField.getText());
        Order order = new Order(date,(float)discount,paymentMethod,calculatedPrice,totalPrice,customerId,cashierId);
        
        for (OrderItems item : orderItemsList) {
            order.addOrderItem(item);
        }
        
        cd.addOrder(order);
        
        for (OrderItems item : orderItemsList) {
        cd.addOrderItem(item, order.getId());

        }
        closeAll();
     createOrderPane.setVisible(true);
        invoicePane.setVisible(true);
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Velora Store ===\n");
        sb.append("Order ID: ").append(order.getId()).append("\n");
        sb.append("Cashier ID: ").append(order.getCashierId()).append("\n");
        sb.append("Customer ID: ").append(order.getCustomerId()).append("\n");
        sb.append("Date: ").append(order.getDate()).append("\n");
        sb.append("Payment Method: ").append(order.getPayment_method()).append("\n");
        sb.append("-------------------------------\n");
        sb.append(String.format("%-15s %-10s %-10s\n", "Product", "Qty", "Price"));

        for (OrderItems item : order.getOrderItems()) {
            sb.append(String.format("%-15s %-10d %-10.2f\n",
                    "ProductID-" + item.getProductId(),
                    item.getDesired_quantity(),
                    item.getTotal_price()));
        }

        sb.append("-------------------------------\n");
        sb.append(String.format("Total Price: %.2f\n", order.getTotal_price()));
        sb.append(String.format("Discount: %.2f%%\n", order.getDiscount()));
        sb.append(String.format("Final Price: %.2f\n", order.getCalculated_price()));
        sb.append("===============================\n");

        invoiceArea.setText(sb.toString());
        
}
    @FXML
    void closeBtnClicked() {
        showEmployeesPane();
         productIdField.clear();
         customerPhoneField.clear();
         customerNameField.clear();
         cashierIdField.clear();
         discountField.clear();
    }
    @FXML
    private void closeAll(){
        invoicePane.setVisible(false);
        addProductPane.setVisible(false);
        CashierOrderPane.setVisible(false);
        customerNameField.setVisible(false);
        orderDetailsPane.setVisible(false);
        createOrderPane.setVisible(false);
    }
    


}

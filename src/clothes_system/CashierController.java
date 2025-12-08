
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
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

      @FXML private Label CashierOrdersChoose;
      @FXML private Label createOrderChoose;
      @FXML private AnchorPane CashierOrderPane;
      @FXML private AnchorPane createOrderPane;
      @FXML private AnchorPane createCustomerPane;
      @FXML private AnchorPane invoice;

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
    @FXML private TextField discountField;
    @FXML private TextField customerPhoneField;
    @FXML private TextField custNameField;
    @FXML private AnchorPane orderDetailsPane;
    @FXML private RadioButton cashRb;
    @FXML private RadioButton creditRb;
    @FXML private javafx.scene.control.ToggleGroup paymentMethod;

    @FXML private Button printInvoiceBtn;
    @FXML private AnchorPane invoicePane;
    @FXML private TextArea invoiceArea;
    @FXML private Button closeBtn;
    
    @FXML private Label custname;      
    @FXML private Label phonecust;     
    @FXML private Label payment;       
    @FXML private Label total;        
    @FXML private Label discountLbl;     
    @FXML private Label invoicenum;    
    @FXML private Label cashiername;   

   
    @FXML private TableView<OrderItems> invoiceitems; 

    // TableColumns
    @FXML private TableColumn<OrderItems, String> product;  
    @FXML private TableColumn<OrderItems, Integer> Quantity; 
    @FXML private TableColumn<OrderItems, Double> UnitPrice; 
    @FXML private TableColumn<OrderItems, Double> Total; 
    
    static String rec_Email;
    static String rec_Pass;
    public static void receive_Info(List<String> info) {
    rec_Email = info.get(0);
    rec_Pass = info.get(1);
   
}
     int cashierId ;
     @FXML Text cashierName;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
              cashierName.setText(User_DBO.getCashierName(rec_Email, rec_Pass));
               cashierId =  Cashier_DBO.getCashierIdByEmail(rec_Email);

          } catch (SQLException ex) {
              Logger.getLogger(CashierController.class.getName()).log(Level.SEVERE, null, ex);
          }
      loadOrderData();
      closeAll();
      // spinner initialization 
      SpinnerValueFactory<Integer> valueFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
      

    quantitySpinner.setValueFactory(valueFactory);

    quantitySpinner.setEditable(true);
    quantitySpinner.focusedProperty().addListener((obs, oldVal, newVal) -> {
    if (!newVal) {  
        quantitySpinner.increment(0); 
    }
});
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
  

     
     @FXML
     private void showEmployeesPane(){
        closeAll();
     createOrderPane.setVisible(true);
     addProductPane.setVisible(true);
     
        
    }
    ArrayList<OrderItems> orderItemsList = new ArrayList<>(); // order items list

    @FXML
    private void nextBtnClicked() {

    String productIdText = productIdField.getText().trim();
    if (productIdText.isEmpty()) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Field");
        alert.setContentText("Product ID cannot be empty!");
        alert.showAndWait();
        return;
    }

    int pId;
    try {
        pId = Integer.parseInt(productIdText);
    } catch (NumberFormatException e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Product ID must be a number!");
        alert.showAndWait();
        return;
    }

    int quantity = quantitySpinner.getValue();
    
    products pr = new products();
    Product p = pr.searchProduct(pId);
    
    if (p == null) {
        alert.setTitle("Error");
        alert.setHeaderText("Product Not Found");
        alert.setContentText("Product with ID " + pId + " does not exist!");
        alert.showAndWait();
        return;
    }
    
    if (quantity>p.getQuantity()){
        alert.setTitle("Quantity Error");
        alert.setHeaderText("Insufficient Stock");
        alert.setContentText("Sorry, the requested quantity is not available. " + "Available stock: " + p.getQuantity());
        alert.showAndWait();
        return;
    }


    orderItemsList.add(new OrderItems(quantity, p.getPrice() * quantity, p.getId()));

    productIdField.clear();
    quantitySpinner.getValueFactory().setValue(1);
}

    @FXML
    private void finishBtnClicked() {
    addProductPane.setVisible(false);
    invoicePane.setVisible(false);
    createCustomerPane.setVisible(false);

    createOrderPane.setVisible(true);  
    orderDetailsPane.setVisible(true);  
    orderDetailsPane.toFront();
    }
    int customerId;
    @FXML
    private void printInvoiceClicked() {
   
    String phone = customerPhoneField.getText().trim();
    String discountText = discountField.getText().trim();

    if (phone.isEmpty()) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Field");
        alert.setContentText("Customer phone cannot be empty!");
        alert.showAndWait();
        return;
    }

    if (discountText.isEmpty()) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Field");
        alert.setContentText("Discount cannot be empty!");
        alert.showAndWait();
        return;
    }

    double discount;
    try {
        discount = Double.parseDouble(discountText);
    } catch (NumberFormatException e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Discount");
        alert.setContentText("Discount must be a number!");
        alert.showAndWait();
        return;
    }

    if (discount > 100|| discount<0) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Discount");
        alert.setContentText("Discount cannot be greater than 100% or less than 0%");
        alert.showAndWait();
        return;
    }

    Cashier_DBO cd = new Cashier_DBO();
    double totalPrice = cd.calculateTotalPrice(orderItemsList);
    Order.PaymentMethod paymentMethod = cashRb.isSelected() ? Order.PaymentMethod.CASH : Order.PaymentMethod.CREDIT;
    Date date = new Date();
    double calculatedPrice = totalPrice - totalPrice * (discount / 100);

    User_DBO ud = new User_DBO();
    Person p = ud.searchPerson(phone);

    if (p != null && p.getType().equals("CUSTOMER")) {
        customerId = p.getId();
    } else {
        createCustomerPane.setVisible(true);
        return; 
    }

    Order order = new Order(date, (float) discount, paymentMethod, calculatedPrice, totalPrice, cashierId, customerId);

    for (OrderItems item : orderItemsList) {
        order.addOrderItem(item);
    }

    cd.addOrder(order);

    for (OrderItems item : orderItemsList) {
        cd.addOrderItem(item, order.getId());
    }
    
    Person cashier = User_DBO.searchPerson(Integer.toString(cashierId));

    closeAll();
    createOrderPane.setVisible(true);
    invoicePane.setVisible(true);
    
    custname.setText(p.getName());
    phonecust.setText(p.getContact_info());
    payment.setText(order.getPayment_method());
    total.setText(String.format("%.2f", calculatedPrice));
    discountLbl.setText(String.format("%.2f", order.getDiscount()));
    invoicenum.setText(String.valueOf(order.getId()));
    cashiername.setText(cashier.getName());
    
        product.setCellValueFactory(cellData -> 
        new SimpleStringProperty("ProductID-" + cellData.getValue().getProductId())
    );
    Quantity.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getDesired_quantity())
    );
    UnitPrice.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_price()/ cellData.getValue().getDesired_quantity())
    );
    Total.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal_price())
    );
    
    invoiceitems.getItems().clear();
    invoiceitems.getItems().addAll(order.getOrderItems());
    
//    paymentMethod.selectToggle(null);
    loadOrderData();
}

     @FXML
    public void handleSaveCustomer() {
    String name = custNameField.getText().trim();
    String phone = customerPhoneField.getText().trim();

    if (name.isEmpty() || phone.isEmpty()) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Field");
        alert.setContentText("Customer name and phone cannot be empty!");
        alert.showAndWait();
        return;
    }

    Person cus = new Person(name, phone, Person.Type.CUSTOMER);
    User_DBO.addPerson(cus);
    customerId = cus.getId();

    createCustomerPane.setVisible(false);
    printInvoiceClicked();
}


    @FXML
    void closeBtnClicked() {
        showEmployeesPane();
         productIdField.clear();
         customerPhoneField.clear();
         custNameField.clear();
         discountField.clear();
    }
    @FXML
    private void closeAll(){
        invoicePane.setVisible(false);
        addProductPane.setVisible(false);
        CashierOrderPane.setVisible(false);
        createCustomerPane.setVisible(false);
        orderDetailsPane.setVisible(false);
        createOrderPane.setVisible(false);
    }
   


}

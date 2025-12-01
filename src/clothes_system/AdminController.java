/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clothes_system;


import static clothes_system.Reports.LowStockAlert;
import static clothes_system.Reports.allOrdersInYears;
import static clothes_system.Reports.bestSupplier;
import static clothes_system.Reports.getTopNSoldProducts;
import static clothes_system.Reports.payment_Methods_Prices;
import static clothes_system.Reports.topOrderUser;
import static clothes_system.Reports.topRevenueUser;
import static clothes_system.Reports.totalPriceInYears;
import static clothes_system.Reports.totalRevenue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author soft zone
 */
public class AdminController implements Initializable {
    // ================= Text Reports =================
    @FXML private Text top_Price_Result;
    @FXML private Text top_Order_Result;
    @FXML private Text top_Supplier;
    @FXML private Text payment_Method_Rep;
    @FXML private Text Total_Revenue;
    @FXML private Text alert;
    // ================= Charts =================
    @FXML private BarChart<String, Number> prices_Chart;
    @FXML private BarChart<String, Number> Orders_Chart;
    @FXML private BarChart<String, Number> Seller_Chart;
    // ================= Panes =================
    @FXML private AnchorPane reportsPane;
    @FXML private AnchorPane employeesPane;
    @FXML private AnchorPane addEmpPane;
    @FXML private AnchorPane editEmpPane;
    @FXML private AnchorPane productPane;
    @FXML private AnchorPane addpane;
    @FXML private AnchorPane editpane;
    // ================= Buttons =================
    @FXML private Button employeesButton;
    @FXML private Button customerButton;
    @FXML private Button logoutButton;
    @FXML private Button AddUserButton;
    @FXML private Button EditUserButton;
    @FXML private Button DelUserButton;
    @FXML private Button Addbtn;
    @FXML private Button Deletebtn;
    @FXML private Button Editbtn;
    @FXML private Button savebtn;
    @FXML private Button cancelbtn;
    @FXML
    private Button cancel;
    @FXML
    private Button finish;
    // ================= TableView Employees =================
    @FXML private TableView<ObservableList<String>> employeeTable;
    @FXML private TableColumn<ObservableList<String>, String> colID;
    @FXML private TableColumn<ObservableList<String>, String> colName;
    @FXML private TableColumn<ObservableList<String>, String> colPhone;
    @FXML private TableColumn<ObservableList<String>, String> colEmail;
    @FXML private TableColumn<ObservableList<String>, String> colSalary;
    @FXML private TableColumn<ObservableList<String>, String> colRole;
     // ================= TableView Products =================
    @FXML private TableView<Product> producttable;
    @FXML private TableColumn<Product, String> productID;
    @FXML private TableColumn<Product, String> Pname;
    @FXML private TableColumn<Product, Double> price;
    @FXML private TableColumn<Product, Integer> quantity;
    @FXML private TableColumn<Product, String> status;
    @FXML private TableColumn<Product, Integer> supid;
    @FXML private TableColumn<Product, String> pcolour;
    // ================= AddEmployees Fields =================
    @FXML private TextField addEmpNameField;
    @FXML private TextField addEmpPhoneField;
    @FXML private TextField addEmpEmailField;
    @FXML private TextField addEmpSalaryField;
    @FXML private TextField addEmpPassField;
    @FXML private Button addEmpBtn;
    @FXML private TextField searchField;

    @FXML private RadioButton rbAdmin;
    @FXML private RadioButton rbCashier;


    @FXML private ToggleGroup userType;
    // ================= Edit Employees Fields =================
    @FXML private TextField editEmpNameField;
    @FXML private TextField editEmpPhoneField;
    @FXML private TextField editEmpEmailField;
    @FXML private TextField editEmpPassField;
    @FXML private TextField editEmpSalaryField;
    
    @FXML private Button editEmpbtn;
    // ================= Edit Products Fields =================
    @FXML private TextField search;

    // Edit TextFields
    @FXML private TextField tid;
    @FXML private TextField tname;
    @FXML private TextField tprice;
    @FXML private TextField tquantity;
    @FXML private TextField tstatus;
    @FXML private TextField tsid;
    @FXML private TextField tcolour;
    
    @FXML private TextField fid;
    @FXML private TextField fname;
    @FXML private TextField fprice;
    @FXML private TextField fquantity;
    @FXML private TextField fstatus;
    @FXML private TextField fsid;
    @FXML private TextField fcolour;
    // ================= Add Products Fields =================
    @FXML
    private TextField colour;
    @FXML
    private TextField name;

 @FXML
    private TextField price1;
 @FXML
    private TextField quantity1;
 @FXML
    private TextField status1;
   
 @FXML
    private TextField sid;
  
     @FXML
    private TextField pid;
    
    // ================= Text Reports Methods =================
    public void getReportText() throws SQLException {
     List<Map<String, Object>> report = Reports.topRevenueUser();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        String name = (String) row.get("Name");
        double price = (Double) row.get("total_Price"); // خلي بالك اسم المفتاح مطابق للكلاس الجديد
        sb.append("Customer: ").append(name)
          .append("\n")
          .append("Total Price: ").append(price)
          .append("\n");
    }
    top_Price_Result.setText(sb.toString());
    top_Price_Result.setFont(Font.font("Arial", 15));
    top_Price_Result.setFill(Color.web("#000000"));
}

    public void getReport2Text() {
    // استدعاء النسخة الجديدة من Reports اللي بترجع List
    List<Map<String, Object>> report = Reports.topOrderUser();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        String name = (String) row.get("Name");
        String contact = (String) row.get("Contact_Info");
        int no_Orders = (Integer) row.get("no_OF_Orders");

        sb.append("Customer: ").append(name)
          .append("\n")
          .append("Contact Information: ").append(contact)
          .append("\n")
          .append("Number Of Orders: ").append(no_Orders)
          .append("\n");
    }

    top_Order_Result.setText(sb.toString());
    top_Order_Result.setFont(Font.font("Arial", 15));
    top_Order_Result.setFill(Color.web("#000000"));
}

    public void getReport4Text() throws SQLException {
    List<Map<String, Object>> report = Reports.bestSupplier();
StringBuilder sb = new StringBuilder();

for (Map<String, Object> row : report) {
    int supplierID = (Integer) row.get("SID");
    String supplierName = (String) row.get("Name");
    int soldQTY = (Integer) row.get("TotalSoldQty");

    sb.append("Supplier ID: ").append(supplierID)
      .append("\n")
      .append("Supplier Name: ").append(supplierName)
      .append("\n")
      .append("Sold Quantity: ").append(soldQTY)
      .append("\n");
}

System.out.println(sb.toString());

    top_Supplier.setText(sb.toString());
    top_Supplier.setFont(Font.font("Arial", 15));
    top_Supplier.setFill(Color.web("#000000"));

}

    public void getReport3Text() {
    // استدعاء النسخة الجديدة من Reports اللي بترجع List
    List<Map<String, Object>> report = Reports.payment_Methods_Prices();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        String payment = (String) row.get("Payment_Method");
        double payment_Price = (Double) row.get("Payment_Prices");

        sb.append("Payment Method: ").append(payment)
          .append("\n")
          .append("Payment Total Price: ").append(payment_Price)
          .append("\n");
    }

    payment_Method_Rep.setText(sb.toString());
    payment_Method_Rep.setFont(Font.font("Arial", 15));
    payment_Method_Rep.setFill(Color.web("#000000"));
}


    public void getReport5Text() {
    List<Map<String, Object>> report = Reports.totalRevenue();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        double price = (Double) row.get("total_revenue");
        sb.append("Total Revenue For All Orders: ").append(price).append("\n");
    }

    Total_Revenue.setText(sb.toString());
    Total_Revenue.setFont(Font.font("Arial", 15));
    Total_Revenue.setFill(Color.web("#000000"));
}

    

    public void getReport6Text() {
    List<Map<String, Object>> report = Reports.LowStockAlert();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        int ID = (Integer) row.get("ID");
        String name = (String) row.get("Name");

        sb.append("Product ID: ").append(ID)
          .append("\n")
          .append("Product Name: ").append(name)
          .append("\n");
    }

    alert.setText(sb.toString());
    alert.setFont(Font.font("Arial", 15));
    alert.setFill(Color.web("#000000"));
}


// ================= Chart Methods =================

 @FXML
private void loadSalesData() {
    AreaChart.Series<String, Number> series = new AreaChart.Series<>();
    series.setName("Sales");

    List<Map<String, Object>> report = Reports.totalPriceInYears();

    for (Map<String, Object> row : report) {
        String year = (String) row.get("year");
        double price = (Double) row.get("total_year_price");
        series.getData().add(new AreaChart.Data<>(year, price));
    }

    prices_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : prices_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #B597E8;");
        }
    }
}



@FXML
private void loadOrdersData() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Orders");

    List<Map<String, Object>> report = Reports.allOrdersInYears();

    for (Map<String, Object> row : report) {
        String year = (String) row.get("year");
        int number = (Integer) row.get("no_of_orders");
        series.getData().add(new XYChart.Data<>(year, number));
    }

    Orders_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : Orders_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #B597E8;");
        }
    }
}

 

@FXML
private void loadSellerData() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Seller");

    List<Map<String, Object>> report = Reports.getTopNSoldProducts();

    for (Map<String, Object> row : report) {
        int productId = (Integer) row.get("ID");
        int quantity = (Integer) row.get("TotalSoldQty");
        series.getData().add(new XYChart.Data<>(String.valueOf(productId), quantity));
    }

    Seller_Chart.getData().add(series);

    for (XYChart.Series<String, Number> s : Seller_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #B597E8;");
        }
    }
}

// ================= Load Employees Data =================
    @FXML
    private void loadEmployeesData() {
        ObservableList<ObservableList<String>> data = User_DBO.getAllPersonsForTable();
        colID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        colName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        colPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
        colRole.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
        colSalary.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)));
        colEmail.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(5)));
        employeeTable.setItems(data);
    }
    // ================= Search Employees =================
    
    @FXML
         public void searchEmp() {
             String keyword = searchField.getText().trim();
             if (keyword.isEmpty()) {
                 loadEmployeesData();
                   return;
                }
             ObservableList<ObservableList<String>> data = User_DBO.searchUsersForTable(keyword);
             if (data.isEmpty()) {
             showAlert("Info", "No user found with this keyword!");
               }

             colID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
             colName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
             colPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
             colRole.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
             colSalary.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)));
             colEmail.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(5)));
             employeeTable.setItems(data);
             }
         // ================= Delete Employee =================
    @FXML
      public void deleteEmployee() {
    ObservableList<String> selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
    
    if (selectedEmployee == null) {
        showAlert("Error", "Please select an employee to delete!");
        return;
    }
    
    try {
        int employeeId = Integer.parseInt(selectedEmployee.get(0)); 
        String employeeName = selectedEmployee.get(1);
        
        // Alert to confirm deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete Employee");
        confirmation.setContentText("Are you sure you want to delete " + employeeName + "?");
        
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = User_DBO.deletePerson(employeeId);
            loadEmployeesData();

        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Invalid employee ID!");
    }
}
      // ================= Edit Employee =================
        private int empId; 

        @FXML
        public void editEmpClicked() {    // get the user id from the table and fill the fields with the data
            addEmpPane.setVisible(false);  // close the add pane if it's open
            ObservableList<String> selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null) {
                showAlert("Error", "Please select an employee to edit!");
                return;
            }

            try {
                //
                empId = Integer.parseInt(selectedEmployee.get(0));

                Person person = User_DBO.searchPerson(String.valueOf(empId));
                if (person == null) {
                    showAlert("Error", "Employee not found in database!");
                    return;
                }

                // fill old data
                editEmpNameField.setText(person.getName());
                editEmpPhoneField.setText(person.getContact_info());

                if (person instanceof User ) {
                     User u = (User) person;
                    editEmpEmailField.setText(u.getEmail());
                    editEmpSalaryField.setText(String.valueOf(u.getSalary()));
                    editEmpPassField.setText(u.getPassword());
                }

                editEmpPane.setVisible(true); 

            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid employee ID!");
            }
        }

        @FXML
        public void saveEmpClicked() {
            try {
                String newName = editEmpNameField.getText();
                String newPhone = editEmpPhoneField.getText();
                String newEmail = editEmpEmailField.getText();
                String newPass = editEmpPassField.getText();
                double newSalary = Double.parseDouble(editEmpSalaryField.getText());

                
                boolean success = User_DBO.updateUser(empId, newName, newPhone, newEmail, newPass, newSalary);

                if (success) {
                    showAlert("Success", "Employee updated successfully!");
                    editEmpPane.setVisible(false);
                    loadEmployeesData();
                } else {
                    showAlert("Error", "Failed to update employee.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input data!");
            }
        }
        // ================= Add Employee =================
         @FXML
        public void addEmpClicked() {
            String name = addEmpNameField.getText().trim();
            String phone = addEmpPhoneField.getText().trim();
            String email = addEmpEmailField.getText().trim();
            String pass = addEmpPassField.getText().trim();
            String salaryText = addEmpSalaryField.getText().trim();

            // check empty fields
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty() || salaryText.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }

            // salary is a positive number
            double salary;
            try {
                salary = Double.parseDouble(salaryText);
                if (salary < 0) {
                    showAlert("Error", "Salary cannot be negative!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Salary must be a valid number!");
                return;
            }

            // type selected
            if (userType == null || userType.getSelectedToggle() == null) {
                showAlert("Error", "Please select a role (Admin or Cashier)!");
                return;
            }

            // user type
            User.Utype utypeValue = rbAdmin.isSelected() ? User.Utype.ADMIN : User.Utype.CASHIER;
            User u = new User(name, phone, Person.Type.USER, email, pass, salary, utypeValue);

            // exceptions
            try {
                User_DBO.addUser(u);
                if (true) {
                    showAlert("Success", "User added successfully!");
                    loadEmployeesData();
                    addEmpPane.setVisible(false);
                    clearFields();
                } else {
                    showAlert("Error", "Failed to add user!");
                }
            } catch (Exception e) {
                // trigger exception
                if (e.getMessage().contains("User with this Email already exists")) {
                    showAlert("Error", "User already exists!");
                    // data base exceptions
                } else {    
                    showAlert("Error", "Database error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // clear fields after add
        private void clearFields() {
            addEmpNameField.clear();
            addEmpPhoneField.clear();
            addEmpEmailField.clear();
            addEmpPassField.clear();
            addEmpSalaryField.clear();

            if (userType != null) {
                userType.selectToggle(null);
            }
        }

        // Alert method
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
// ================= Initialize Reports =================
@FXML
private void intializeReports() throws SQLException{
        getReportText(); 
        getReport2Text();
        getReport3Text();
        getReport4Text();
        getReport5Text();
        getReport6Text();
        loadSalesData();
        loadOrdersData();
        loadSellerData();
}



    

    

    
   
  
     

    

    

    

    

    private ObservableList<Product> productlist = FXCollections.observableArrayList();
    private FilteredList<Product> filteredList;
    private products productDB = new products(); // DB handler

    private Product selectedProduct; // For editing

    // Initialize TableView columns
    private void setupTable() {
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Pname.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        supid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        pcolour.setCellValueFactory(new PropertyValueFactory<>("colour"));
    }

    // Load products from DB
    private void loadProducts() {
        productlist = productDB.getAllProducts();
        System.out.println("Number of products loaded: " + productlist.size());
for (Product p : productlist) {
    System.out.println(p);
}

        filteredList = new FilteredList<>(productlist, b -> true);
        producttable.setItems(filteredList);
    }

    // Search/filter products
    private void setupSearch() {

         

        search.textProperty().addListener((obs, oldVal, newVal) -> {

            filteredList.setPredicate(product -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String filter = newVal.toLowerCase();
                return product.getName().toLowerCase().contains(filter)
                        || product.getColour().toLowerCase().contains(filter)
                        || product.getStatus().toLowerCase().contains(filter)
                        || String.valueOf(product.getId()).contains(filter);
            });
        });
    }

 @FXML
private void deleteproduct(ActionEvent event) {
    Product selected = producttable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        System.out.println("No product selected!");
        return;
    }

    // 1. Delete from database
    productDB.deleteProducts(selected.getId());

    // 2. Remove from ObservableList so TableView updates
    productlist.remove(selected);

}

    

    @FXML
    private void cancel(ActionEvent event) {
        addpane.setVisible(false);
        editpane.setVisible(false);
        productPane.setVisible(true);
    }

    @FXML

private void save(ActionEvent event) {
    if (selectedProduct == null) return;

    try {
        // Update product with TO field values
        selectedProduct.setId(Integer.parseInt(tid.getText()));
        selectedProduct.setName(tname.getText());
        selectedProduct.setPrice(Double.parseDouble(tprice.getText()));
        selectedProduct.setQuantity(Integer.parseInt(tquantity.getText()));
        selectedProduct.setStatus(tstatus.getText());
        selectedProduct.setSupplierID(Integer.parseInt(tsid.getText()));
        selectedProduct.setColour(tcolour.getText());

        // Update in database
        productDB.updateProduct(selectedProduct);

        // Refresh TableView
        loadProducts();

        // Close edit pane
        editpane.setVisible(false);
        productPane.setVisible(true);

        System.out.println("Product updated successfully!");

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error updating product. Please check fields.");
    }
}


 @FXML
private void finish(ActionEvent event) {
    try {
        // 1. Read values from text fields
        int productID = Integer.parseInt(pid.getText()); // optional if DB auto-generates
        String productName = name.getText();
        double productPrice = Double.parseDouble(price1.getText());
        int productQuantity = Integer.parseInt(quantity1.getText());
        String productColour = colour.getText();
        int supplierId = Integer.parseInt( sid.getText());
        String productStatus = status1.getText();

        // 2. Create Product object
        Product newProduct = new Product(
            productID,        // 0 if auto-generated
            supplierId,    
            productName,
            productPrice,
            productQuantity,
            productStatus,
            productColour
        );

        productlist.add(newProduct);
productDB.addProduct(newProduct);

      

        // 6. Switch panes back to main product view
        addpane.setVisible(false);
        productPane.setVisible(true);

        System.out.println("Product added successfully!");

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error adding product. Please check all fields.");
    }
}
@FXML
private Label reportChoose;

    @FXML
    private void reportsDisplay() {
        reportsPane.setVisible(true);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        employeesPane.setVisible(false);
    }
    @FXML
private Label product;
    @FXML
    private void showProductPane() {
        productPane.setVisible(true);
        addpane.setVisible(false);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
    }

    @FXML
    private void showAddProductPane() {
        productPane.setVisible(true);
        addpane.setVisible(true);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
    }

    @FXML
    private void showEditProductPane() {
      Product selected = producttable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        System.out.println("No product selected for edit!");
        return;
    }
    this.selectedProduct = selected;

    // Fill FROM fields (show current values)
    fid.setText(String.valueOf(selected.getId()));
    fname.setText(selected.getName());
    fprice.setText(String.valueOf(selected.getPrice()));
    fquantity.setText(String.valueOf(selected.getQuantity()));
    fstatus.setText(selected.getStatus());
    fsid.setText(String.valueOf(selected.getSupplierID()));
    fcolour.setText(selected.getColour());
      productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(true);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
    }
    @FXML private Label employeesChoose;
    @FXML
    private void showEmployeesPane(){
        employeesPane.setVisible(true);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        
    }
    @FXML 
    private void showAddEmployees(){
        addEmpPane.setVisible(true);
    }
 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupTable();
            loadProducts();
            setupSearch();
            intializeReports();
            userType = new ToggleGroup();
            rbAdmin.setToggleGroup(userType);
         rbCashier.setToggleGroup(userType);
         // Search while typing
         searchField.textProperty().addListener((observable, oldValue, newValue) -> {
         String keyword = newValue.trim();
             if (keyword.isEmpty()) {
             loadEmployeesData(); 
                return;
                }
            ObservableList<ObservableList<String>> data = User_DBO.searchUsersForTable(keyword);

           colID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
           colName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
           colPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
           colRole.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
           colSalary.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)));
           colEmail.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(5)));
           employeeTable.setItems(data);
            });
          loadEmployeesData();
         

        
            
            
  
 
 
     
         
     } catch (SQLException ex) {
         Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
     }
        reportsPane.setVisible(false);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        employeesPane.setVisible(false);
        addEmpPane.setVisible(false);
        editEmpPane.setVisible(false);
    }



    
    
   
         

    
    
}
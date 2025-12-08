
package clothes_system;


import clothes_system.Product;
import static clothes_system.Reports.LowStockAlert;
import static clothes_system.Reports.allOrdersInYears;
import static clothes_system.Reports.bestSupplier;
import static clothes_system.Reports.getTopNSoldProducts;
import static clothes_system.Reports.payment_Methods_Prices;
import static clothes_system.Reports.topOrderUser;
import static clothes_system.Reports.topRevenueUser;
import static clothes_system.Reports.totalPriceInYears;
import static clothes_system.Reports.totalRevenue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


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
    // ================= Labels =================
    @FXML private Label logoutButton;
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
    @FXML private AnchorPane suppliersPane;
    @FXML private AnchorPane addSuppPane;
    @FXML private AnchorPane editSuppPane;
    // ================= Buttons =================
    @FXML private Button employeesButton;
    @FXML private Button customerButton;
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
    // ================= TableView Suppliers =================
    @FXML private TableView<ObservableList<String>> suppliersTable;
    @FXML private TableColumn<ObservableList<String>, String> colSuppID;
    @FXML private TableColumn<ObservableList<String>, String> colSuppName;
    @FXML private TableColumn<ObservableList<String>, String> colSuppPhone;
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
    // ================= AddSuppliers Fields =================
    @FXML private TextField addSuppNameField;
    @FXML private TextField addSuppPhoneField;
    @FXML private Button addSuppBtn;
    @FXML private TextField searchSuppField;
    // ================= Edit Employees Fields =================
    @FXML private TextField editEmpNameField;
    @FXML private TextField editEmpPhoneField;
    @FXML private TextField editEmpEmailField;
    @FXML private TextField editEmpPassField;
    @FXML private TextField editEmpSalaryField;
    
    @FXML private Button editEmpbtn;
    // ================= Edit Suppliers Fields =================
    @FXML private TextField editSuppNameField;
    @FXML private TextField editSuppPhoneField;
    
    @FXML private Button editSuppbtn;
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
    // ========================================================= Reports ===========================================================
    // ================= Text Reports Methods =================
    public void getReportText() throws SQLException {
     List<Map<String, Object>> report = Reports.topRevenueUser();

    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> row : report) {
        String name = (String) row.get("Name");
        double price = (Double) row.get("total_Price");
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
    prices_Chart.getData().clear();
    AreaChart.Series<String, Number> series = new AreaChart.Series<>();
    series.setName("Sales");

    List<Map<String, Object>> report = Reports.totalPriceInYears();

    for (Map<String, Object> row : report) {
        String year = (String) row.get("year");
        double price = (Double) row.get("total_year_price");
        series.getData().add(new AreaChart.Data<>(year, price));
    }

    prices_Chart.getData().add(series);

     String[] colors = {
        "#800080", // Purple
        "#9370DB", // MediumPurple
        "#B597E8", // لونك الأصلي
        "#BA55D3", // MediumOrchid
        "#DDA0DD", // Plum
        "#4B0082"  // Indigo
    };

    int index = 0; // عداد للألوان

    // 2. المرور على البيانات وتلوين كل عمود بلون مختلف بالترتيب
    for (XYChart.Series<String, Number> s : prices_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            
            // معادلة اختيار اللون: العداد % عدد الألوان (لضمان التكرار لو العواميد زادت عن الألوان)
            String color = colors[index % colors.length];
            
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
            
            index++; // الانتقال للون التالي
        }
    }
    // ------------------ نهاية التعديل ------------------
}



@FXML
private void loadOrdersData() {
    Orders_Chart.getData().clear();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Orders");

    List<Map<String, Object>> report = Reports.allOrdersInYears();

    for (Map<String, Object> row : report) {
        String year = (String) row.get("year");
        int number = (Integer) row.get("no_of_orders");
        series.getData().add(new XYChart.Data<>(year, number));
    }

    Orders_Chart.getData().add(series);

     String[] colors = {
        "#800080", // Purple
        "#9370DB", // MediumPurple
        "#B597E8", // لونك الأصلي
        "#BA55D3", // MediumOrchid
        "#DDA0DD", // Plum
        "#4B0082"  // Indigo
    };

    int index = 0; // عداد للألوان

    // 2. المرور على البيانات وتلوين كل عمود بلون مختلف بالترتيب
    for (XYChart.Series<String, Number> s : Orders_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            
            // معادلة اختيار اللون: العداد % عدد الألوان (لضمان التكرار لو العواميد زادت عن الألوان)
            String color = colors[index % colors.length];
            
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
            
            index++; // الانتقال للون التالي
        }
    }
    // ------------------ نهاية التعديل ------------------
}

 

@FXML
private void loadSellerData() {
    Seller_Chart.getData().clear();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Seller");

    List<Map<String, Object>> report = Reports.getTopNSoldProducts();

    for (Map<String, Object> row : report) {
        int productId = (Integer) row.get("ID");
        int quantity = (Integer) row.get("TotalSoldQty");
        series.getData().add(new XYChart.Data<>(String.valueOf(productId), quantity));
    }

    Seller_Chart.getData().add(series);

    String[] colors = {
        "#800080", // Purple
        "#9370DB", // MediumPurple
        "#B597E8", // لونك الأصلي
        "#BA55D3", // MediumOrchid
        "#DDA0DD", // Plum
        "#4B0082"  // Indigo
    };

    int index = 0; // عداد للألوان

    // 2. المرور على البيانات وتلوين كل عمود بلون مختلف بالترتيب
    for (XYChart.Series<String, Number> s : Seller_Chart.getData()) {
        for (XYChart.Data<String, Number> data : s.getData()) {
            
            // معادلة اختيار اللون: العداد % عدد الألوان (لضمان التكرار لو العواميد زادت عن الألوان)
            String color = colors[index % colors.length];
            
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
            
            index++; // الانتقال للون التالي
        }
    }
    // ------------------ نهاية التعديل ------------------
}
// ================= Initialize Reports =================
//Refresh data
@FXML
void refreshData(MouseEvent event) throws SQLException {
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
@FXML
private Label reportChoose;

    @FXML
    private void reportsDisplay() {
        reportsPane.setVisible(true);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        employeesPane.setVisible(false);
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
    }
//================================================================================================================================

// ========================================================= Employees ===========================================================
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
        //Name validation
 private boolean isValidName(String name) {
        
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return false;
        }
        return trimmedName.matches("^[\\p{L}\\s]+$");
    }
//⭐ Email validation
 private boolean isValidGmailEmail(String email) {

        return email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    }
        @FXML
        public void saveEmpClicked() {
            try {
                String newName = editEmpNameField.getText();
                String newPhone = editEmpPhoneField.getText();
                String newEmail = editEmpEmailField.getText();
                String newPass = editEmpPassField.getText();
                double newSalary = Double.parseDouble(editEmpSalaryField.getText());

                
                // ⭐ START: Name Validation
        if (newName.trim().isEmpty()) {
            showAlert("Error", "Error\nName field cannot be empty.");
            editEmpNameField.requestFocus();
            return;
        }
        if (!isValidName(newName)) {
            showAlert("Error", "Invalid Name!\nName must contain only letters and spaces,\n and cannot contain numbers or special characters.");
            editEmpNameField.requestFocus();
            editEmpNameField.selectAll();
            return;
        }
        // ⭐ END: Name Validation
        
                // ⭐ START: Phone Number Validation
        String cleanPhone = newPhone.replaceAll("[\\s\\-()]", ""); 
        if (!isValidEgyptianPhoneNumber(cleanPhone)) {
            showAlert("Error", "Invalid Phone Number! \nPlease enter a valid Egyptian phone number (11 digits, starts with 01).");
            editEmpPhoneField.requestFocus();
            editEmpPhoneField.selectAll();
            return;
        }
        // ⭐ END: Phone Number Validation
        // ⭐ add new
        // ⭐ START: Email Validation (must end with @gmail.com)
        if (!isValidGmailEmail(newEmail)) {
            showAlert("Error", "Invalid Email Format!\nThe email address must be a valid format and must end with @gmail.com.");
            editEmpEmailField.requestFocus();
            editEmpEmailField.selectAll();
            return;
        }
        // ⭐ END: Email Validation
        // ⭐ end
                boolean success = User_DBO.updateUser(empId, newName, cleanPhone, newEmail, newPass, newSalary);

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
            // ⭐ START: Name Validation
    if (!isValidName(name)) {
        showAlert("Error", "Invalid Name!\nName must contain only letters and spaces,\n and cannot contain numbers or special characters.");
        addEmpNameField.requestFocus();
        addEmpNameField.selectAll();
        return;
    }
    // ⭐ END: Name Validation
            
        // ⭐ START: Phone Number Validation
        String cleanPhone = phone.replaceAll("[\\s\\-()]", ""); 
        if (!isValidEgyptianPhoneNumber(cleanPhone)) {
        showAlert("Error", "Invalid Phone Number!\nPlease enter a valid Egyptian phone number (11 digits, starts with 01).");
        addEmpPhoneField.requestFocus();
        addEmpPhoneField.selectAll();
        return;
    }
    // ⭐ END: Phone Number Validation
    
    //// ⭐ add new
         // ⭐ START: Email Validation (must end with @gmail.com)
      if (!isValidGmailEmail(email)) {
        showAlert("Error", "Invalid Email Format!\nThe email address must be a valid format\nand must end with @gmail.com.");
        addEmpEmailField.requestFocus();
        addEmpEmailField.selectAll();
        return;
    }
    // ⭐ END: Email Validation
    
     // ⭐ is email exist?
    if (User_DBO.emailExists(email)) {
        showAlert("Error", "This email is already registered!\nPlease use another email.");
        addEmpEmailField.requestFocus();
        addEmpEmailField.selectAll();
        return;
    }
    // ⭐ end
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
            User u = new User(name, cleanPhone, Person.Type.USER, email, pass, salary, utypeValue);
// ⭐ add new
            boolean success = User_DBO.addUser(u);

    if (success) {
        showAlert("Success", "Employee added successfully!");
        addEmpPane.setVisible(false);
        loadEmployeesData();
    } else {
        showAlert("Error", "Failed to add employee!");
    }
    // ⭐ end
          
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
         @FXML private Label employeesChoose;
    @FXML
    private void showEmployeesPane(){
        employeesPane.setVisible(true);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
        
    }
    @FXML 
    private void showAddEmployees(){
        addEmpPane.setVisible(true);
    }
//================================================================================================================================

// ========================================================= Suppliers ===========================================================
  // ================= Load Suppliers Data =================
    @FXML
    private void loadSuppliersData() {
        ObservableList<ObservableList<String>> data = Supplier_DBO.getAllPersonsForTable();
        colSuppID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        colSuppName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        colSuppPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
        suppliersTable.setItems(data);
    }
    // ================= Search Suppliers =================
    
    @FXML
         public void searchSupplier() {
             String keyword = searchSuppField.getText().trim();
             if (keyword.isEmpty()) {
                 loadSuppliersData();
                   return;
                }
             ObservableList<ObservableList<String>> data = Supplier_DBO.searchUsersForTable(keyword);
             if (data.isEmpty()) {
             showAlert("Info", "No Supplier found with this keyword!");
               }

             colSuppID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
             colSuppName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
             colSuppPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
             suppliersTable.setItems(data);
             }
         // ================= Delete Supplier =================
    @FXML
      public void deleteSupplier() {
    ObservableList<String> selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
    
    if (selectedSupplier == null) {
        showAlert("Error", "Please select a supplier to delete!");
        return;
    }
    
    try {
        int supplierId = Integer.parseInt(selectedSupplier.get(0)); 
        String supplierName = selectedSupplier.get(1);
        
        // Alert to confirm deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete Supplier");
        confirmation.setContentText("Are you sure you want to delete " + supplierName + "?");
        
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = Supplier_DBO.deletePerson(supplierId);
            loadSuppliersData();

        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Invalid supplier ID!");
    }
}
      // ================= Edit Supplier =================
        private int suppId; 
        private String personPreviousPhone;

        @FXML
        public void editSuppClicked() {    // get the user id from the table and fill the fields with the data
            addSuppPane.setVisible(false);  // close the add pane if it's open
            ObservableList<String> selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showAlert("Error", "Please select a supplier to edit!");
                return;
            }

            try {
                //
                suppId = Integer.parseInt(selectedSupplier.get(0));

                Person person = Supplier_DBO.searchPerson(String.valueOf(suppId));
                if (person == null) {
                    showAlert("Error", "Supplier not found in database!");
                    return;
                }

                // fill old data
                editSuppNameField.setText(person.getName());
                editSuppPhoneField.setText(person.getContact_info());
                personPreviousPhone=person.getContact_info();
                System.out.println(personPreviousPhone);


                editSuppPane.setVisible(true); 

            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid supplier ID!");
            }
        }

        @FXML
        public void saveSuppClicked() {
            try {
                String newName = editSuppNameField.getText();
                String newPhone = editSuppPhoneField.getText();
                System.out.println(newPhone);
                if (newName.isEmpty() || newPhone.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }else if(!isValidEgyptianPhoneNumber(newPhone)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText("Phone Number Format Error");
            alert.setContentText("Please enter a valid Egyptian phone number:\n" +
                               "• Must start with 01\n" +
                               "• Must be exactly 11 digits\n" +
                               "• Format: 01XXXXXXXXX (e.g., 01123456789)\n" +
                               "• Only digits allowed (no spaces or dashes)");
            alert.showAndWait();
            addSuppPhoneField.requestFocus();
            addSuppPhoneField.selectAll();
            return;
            }else if(isPhoneNumberExists(newPhone)){
                if(newPhone.equals(personPreviousPhone )){
                    
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Phone Number");
            alert.setHeaderText("Phone Number Already Exists");
            alert.setContentText("This phone number is already registered to another person.\n" +
                               "Please use a different phone number.");
            alert.showAndWait();
            addSuppPhoneField.requestFocus();
            addSuppPhoneField.selectAll();
            return;
                }
            
                
            }else if(!newName.matches("[a-zA-Z]+")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Detect number");
            alert.setHeaderText("Invalid Name");
            alert.setContentText("Name can't contain a number\n");
            alert.showAndWait();
            addSuppNameField.requestFocus();
            addSuppNameField.selectAll();
            return;
            }

                boolean success = Supplier_DBO.updatePerson(suppId, newName, newPhone);

                if (success) {
                    showAlert("Success", "Supplier updated successfully!");
                    editSuppPane.setVisible(false);
                    loadSuppliersData();
                } else {
                    showAlert("Error", "Failed to update supplier.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input data!");
            }
        }
        // ================= Add Supplier =================
         @FXML
        public void addSuppClicked() {
            String name = addSuppNameField.getText().trim();
            String phone = addSuppPhoneField.getText().trim();


            // check empty fields
            if (name.isEmpty() || phone.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }else if(!isValidEgyptianPhoneNumber(phone)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText("Phone Number Format Error");
            alert.setContentText("Please enter a valid Egyptian phone number:\n" +
                               "• Must start with 01\n" +
                               "• Must be exactly 11 digits\n" +
                               "• Format: 01XXXXXXXXX (e.g., 01123456789)\n" +
                               "• Only digits allowed (no spaces or dashes)");
            alert.showAndWait();
            addSuppPhoneField.requestFocus();
            addSuppPhoneField.selectAll();
            return;
            }else if(isPhoneNumberExists(phone)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Phone Number");
            alert.setHeaderText("Phone Number Already Exists");
            alert.setContentText("This phone number is already registered to another person.\n" +
                               "Please use a different phone number.");
            alert.showAndWait();
            addSuppPhoneField.requestFocus();
            addSuppPhoneField.selectAll();
            return;
                
            }else if(!name.matches("[a-zA-Z]+")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Detect number");
            alert.setHeaderText("Invalid Name");
            alert.setContentText("Name can't contain a number\n");
            alert.showAndWait();
            addSuppNameField.requestFocus();
            addSuppNameField.selectAll();
            return;
            }

            Person p = new Person(name, phone, Person.Type.SUPPLIER);

            // exceptions
            try {
                Supplier_DBO.addPerson(p);
                if (true) {
                    showAlert("Success", "Supplier added successfully!");
                    loadSuppliersData();
                    addSuppPane.setVisible(false);
                    clearFields();
                } else {
                    showAlert("Error", "Failed to add supplier!");
                }
            } catch (Exception e) {
                
            }
        }

        // clear fields after add
        private void clearSuppFields() {
            addSuppNameField.clear();
            addSuppPhoneField.clear();

        }

        // Alert method
        private void showSuppAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
         @FXML private Label suppliersChoose;
    @FXML
    private void showSuppliersPane(){
        suppliersPane.setVisible(true);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
        addEmpPane.setVisible(false);
        editEmpPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
        
    }
    @FXML 
    private void showAddSuppliers(){
        addSuppPane.setVisible(true);
    }
    
//================================================================================================================================        

// ========================================================= Products ===========================================================        

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

    // Get selected product
    Product selected = producttable.getSelectionModel().getSelectedItem();

    // If nothing selected → show error and stop
    if (selected == null) {
        showAlert("Error", "Please select a product to delete!");
        return;
    }

    // Show confirmation dialog
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Delete");
    alert.setHeaderText("Are you sure you want to delete this product?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();

    // If user clicked CANCEL → do nothing
    if (!result.isPresent() || result.get() != ButtonType.OK) {
        return;
    }

    // If user clicked OK → delete from DB
    productDB.deleteProducts(selected.getId());

    // Delete from list so TableView updates
    productlist.remove(selected);

    showAlert("Success", "Product deleted successfully!");
}



     private void clearFieldsproduct() {
            name.clear();
            price1.clear();
            quantity1.clear();
            pid.clear();
            status1.clear();
            colour.clear();
            sid.clear();
             tname.clear();
            tprice.clear();
            tquantity.clear();
            tid.clear();
            tstatus.clear();
            tcolour.clear();
            tsid.clear();
           

        }

    @FXML
    private void cancel(ActionEvent event) {
        
        addpane.setVisible(false);
        editpane.setVisible(false);
        productPane.setVisible(true);
         clearFieldsproduct();
    }

    @FXML

private void save(ActionEvent event) {
    if (selectedProduct == null) return;

    try {
        // Update product with TO field values
       // selectedProduct.setId(Integer.parseInt(tid.getText()));
        selectedProduct.setName(tname.getText());
        selectedProduct.setPrice(Double.parseDouble(tprice.getText()));
        selectedProduct.setQuantity(Integer.parseInt(tquantity.getText()));
        selectedProduct.setStatus(tstatus.getText());
        selectedProduct.setSupplierID(Integer.parseInt(tsid.getText()));
        selectedProduct.setColour(tcolour.getText());

        // Update in database
        

        // Refresh TableView
     

        // Close edit pane
    
         boolean isedited=productDB.updateProduct(selectedProduct);
        if(isedited){
                 
              // productDB.updateProduct(selectedProduct);
               loadProducts();
                showAlert("success", "product updated successfully"); 
                editpane.setVisible(false);
                productPane.setVisible(true);
        }
        else{
           showAlert("Error", "edit failed.supplier does not exist");
        }
       
      

    } catch (Exception e) {
        e.printStackTrace();
       
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
      String status = status1.getText();
         if ("UNAVAILABLE".equals(status)) {
            showAlert("Error", "Cannot add product. Quantity must be more than 0!");
            return; // stop further execution
        }
        // 2. Create Product object
        Product newProduct = new Product(
            productID,        // 0 if auto-generated
            supplierId,    
            productName,
            productPrice,
            productQuantity,
            status,
            productColour
        );
        boolean isadded=productDB.addProduct(newProduct);
        if(isadded){
                productlist.add(newProduct); 
               // productDB.addProduct(newProduct);
                  showAlert("success", "product added successfully");
        }
        else{
            
        }
       
    


      

        // 6. Switch panes back to main product view
        addpane.setVisible(false);
        productPane.setVisible(true);
        clearFieldsproduct();
          name.setText("name");
           price1.setText("price");
            quantity1.setText("quantity");
            pid.setText("pid");
            status1.setText("AVAILABLE");
            colour.setText("colour");
            sid.setText("sid");
          

    } 
   catch (Exception e) {
    showAlert("Error", "Invalid input. Please check the fields.");
}
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
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    @FXML
    private void showAddProductPane() {

        productPane.setVisible(true);
        addpane.setVisible(true);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
        
        quantity1.textProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal.isEmpty()) {
        status1.setText("");
        return;
    }

    try {
        int qty = Integer.parseInt(newVal);

        if (qty > 0) {
            status1.setText("AVAILABLE");
        } else {
            status1.setText("UNAVAILABLE");
        }

        status1.setEditable(false);

    } catch (NumberFormatException e) {
        status1.setText("");
    }
});
    }

    @FXML
    private void showEditProductPane() {
      Product selected = producttable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("Error", "Please select a product to edit!");
        return;
    }
     
    this.selectedProduct = selected;

    // Fill FROM fields (show current values)
    fid.setText(String.valueOf(selected.getId()));
    tid.setText(String.valueOf(selected.getId()));
    tid.setEditable(false);
    fname.setText(selected.getName());
    fprice.setText(String.valueOf(selected.getPrice()));
    fquantity.setText(String.valueOf(selected.getQuantity()));
    fstatus.setText(selected.getStatus());
    fsid.setText(String.valueOf(selected.getSupplierID()));
    fcolour.setText(selected.getColour());
      productPane.setVisible(true);
        addpane.setVisible(false);
        editpane.setVisible(true);
     reportsPane.setVisible(false);
     employeesPane.setVisible(false);
     suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
        
        tquantity.textProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal.isEmpty()) {
        tstatus.setText("");
        return;
    }

    try {
        int qty = Integer.parseInt(newVal);

        if (qty > 0) {
            tstatus.setText("AVAILABLE");
        } else {
            tstatus.setText("UNAVAILABLE");
        }

        tstatus.setEditable(false);

    } catch (NumberFormatException e) {
        tstatus.setText("");
    }
});
    }
//================================================================================================================================ 
// ========================================================= Customers ===========================================================    

   
    @FXML private AnchorPane customerPane;  // Changed from 'customer'
    @FXML private Button Add;
    @FXML private Button Delete;
    @FXML private Button Update;
    @FXML private Button show_orders;
    @FXML private TextField Search;
    @FXML private TableView<Customer> customers;
    @FXML private TableColumn<Customer, Integer> Id;
    @FXML private TableColumn<Customer, String> Name;
    @FXML private TableColumn<Customer, String> Contact_info;
    
    @FXML private Button Save;
    @FXML private Button Cancel;  // Changed from 'cancel' to 'Cancel'
    @FXML private TextField custName;
    @FXML private TextField Contact;
    
    @FXML private AnchorPane ordersPane;  // Changed from 'his_orders'
    @FXML private TableView<Order> Orders; 
    @FXML private TableColumn<Order, Integer> id;
    @FXML private TableColumn<Order, Date> date;
    @FXML private TableColumn<Order, Double> discount;
    @FXML private TableColumn<Order, Double> calc_price;
    @FXML private TableColumn<Order, String> pay_method;
    @FXML private TableColumn<Order, Double> tot_price;
    @FXML private TableColumn<Order, Integer> cid;
    @FXML private TableColumn<Order, Integer> caid;
    @FXML private Button Back;
    @FXML private Label CustName;
    
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredCustomers;
    
   
    
    // Setup real-time search functionality
    private void setupRealTimeSearch() {
        // Initialize filtered list
        filteredCustomers = new FilteredList<>(allCustomers, p -> true);
        customers.setItems(filteredCustomers);
        
        // Add listener to search field for real-time filtering
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCustomers(newValue);
        });
        
        // Add search placeholder text
        Search.setPromptText("Search by name, phone, or ID...");
    }
    
    // Method to filter customers based on search text
    private void filterCustomers(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            filteredCustomers.setPredicate(customer -> true); // Show all customers
        } else {
            String lowerCaseFilter = searchText.toLowerCase().trim();
            
            filteredCustomers.setPredicate(customer -> {
                // Search in name (case insensitive)
                if (customer.getName() != null && customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                
                // Search in contact info (case insensitive)
                if (customer.getContact_info() != null && customer.getContact_info().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                
                // Search in ID (exact match or partial)
                if (String.valueOf(customer.getId()).contains(lowerCaseFilter)) {
                    return true;
                }
                
                return false; // No match found
            });
        }
    }
    
    private void refreshCustomerTable() {
        List<Customer> customerList = Customer_DBO.getAllCustomers();
        allCustomers.setAll(customerList);
        
        // Refresh the filtered list with new data
        if (filteredCustomers != null) {
            String searchText = Search.getText();
            if (searchText == null || searchText.isEmpty()) {
                filteredCustomers.setPredicate(customer -> true);
            } else {
                String lowerCaseFilter = searchText.toLowerCase().trim();
                filteredCustomers.setPredicate(customer -> {
                    // Search in name
                    if (customer.getName() != null && customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    
                    // Search in contact info
                    if (customer.getContact_info() != null && customer.getContact_info().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    
                    // Search in ID
                    if (String.valueOf(customer.getId()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    
                    return false;
                });
            }
        }
    }
    
    @FXML
    private void onAddButtonClick(ActionEvent event) {
        // Clear form for new customer
        custName.clear();
        Contact.clear();
        custName.requestFocus();
        
        // Reset Save button to add mode
        Save.setOnAction(this::handleSaveCustomer);
    }
    @FXML
    private void handleSaveCustomer(ActionEvent event) {
    String customerName = custName.getText().trim();
    String contactInfo = Contact.getText().trim();

    // Check for empty fields
    if (customerName.isEmpty() || contactInfo.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Missing Information");
        alert.setContentText("Please fill in all fields.");
        alert.showAndWait();
        return;
    }

    // === ADDED: Validate customer name contains only letters and spaces ===
    if (!isValidCustomerName(customerName)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Customer Name");
        alert.setHeaderText("Name Format Error");
        alert.setContentText("Please enter a valid customer name:\n" +
                           "• Must contain only letters and spaces\n" +
                           "• No numbers or special characters allowed");
        alert.showAndWait();
        custName.requestFocus();
        custName.selectAll();
        return;
    }
    // === END NAME VALIDATION ===

    // Remove any spaces, dashes, or parentheses from phone number
    String cleanContactInfo = contactInfo.replaceAll("[\\s\\-()]", "");
    
    // Validate contact info format (Egyptian phone number)
    if (!isValidEgyptianPhoneNumber(cleanContactInfo)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Phone Number");
        alert.setHeaderText("Phone Number Format Error");
        alert.setContentText("Please enter a valid Egyptian phone number:\n" +
                           "• Must start with 01\n" +
                           "• Must be exactly 11 digits\n" +
                           "• Format: 01XXXXXXXXX (e.g., 01123456789)\n" +
                           "• Only digits allowed (no spaces or dashes)");
        alert.showAndWait();
        Contact.requestFocus();
        Contact.selectAll();
        return;
    }

    // Check if phone number already exists in database
    if (isPhoneNumberExists(cleanContactInfo)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Duplicate Phone Number");
        alert.setHeaderText("Phone Number Already Exists");
        alert.setContentText("This phone number is already registered to another customer.\n" +
                           "Please use a different phone number.");
        alert.showAndWait();
        Contact.requestFocus();
        Contact.selectAll();
        return;
    }

    // === ADDED CONFIRMATION DIALOG FOR ADD ===
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirm Addition");
    confirmAlert.setHeaderText("Add New Customer");
    confirmAlert.setContentText("Are you sure you want to add this customer?\n\n" +
                               "• Name: " + customerName + "\n" +
                               "• Phone: " + formatPhoneNumber(cleanContactInfo) + "\n\n" +
                               "Please verify the information is correct.");

    Optional<ButtonType> result = confirmAlert.showAndWait();
    
    // If user clicks Cancel or closes the dialog, do nothing
    if (result.isPresent() && result.get() != ButtonType.OK) {
        return;
    }
    // === END CONFIRMATION DIALOG ===

    Customer newCustomer = new Customer(customerName, cleanContactInfo, Person.Type.CUSTOMER);
    boolean success = Customer_DBO.addCustomer(newCustomer);

    if (success) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer Added Successfully");
        alert.setContentText("Customer '" + customerName + "' has been added to the system.\n" +
                           "Phone: " + formatPhoneNumber(cleanContactInfo));
        alert.showAndWait();
        
        custName.clear();
        Contact.clear();
        refreshCustomerTable();
        
        // Clear search after adding new customer
        Search.clear();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Add Failed");
        alert.setContentText("Could not add the customer. Please try again.");
        alert.showAndWait();
    }
}

// === ADDED: Helper method to validate customer name ===
private boolean isValidCustomerName(String name) {
  
    String namePattern = "^[\\p{L} .'-]+$";
    
    return name.matches(namePattern);
}
    
    @FXML
    private void handleCancel(ActionEvent event) {
        custName.clear();
        Contact.clear();
    }
    
    @FXML
private void handleDeleteCustomer(ActionEvent event) {
    Customer selectedCust = customers.getSelectionModel().getSelectedItem();

    if (selectedCust == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No Selection");
        alert.setContentText("Please select a customer to delete.");
        alert.showAndWait();
        return;
    }

    // === CHECK FOR EXISTING ORDERS FIRST ===
    int orderCount = Customer_DBO.getCustomerOrderCount(selectedCust.getId());
    
    if (orderCount > 0) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cannot Delete Customer");
        alert.setHeaderText("Customer Has Existing Orders");
        alert.setContentText("Cannot delete customer '" + selectedCust.getName() + "' because they have " + 
                           orderCount + " existing order(s).\n\n" +
                           "To delete this customer, you must first delete or reassign all their orders.");
        alert.showAndWait();
        return;
    }

    // === VERIFICATION DIALOG FOR DELETE ===
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirm Deletion");
    confirmAlert.setHeaderText("Delete Customer");
    confirmAlert.setContentText("Are you sure you want to delete customer:\n\n" +
                               "• Name: " + selectedCust.getName() + "\n" +
                               "• Phone: " + formatPhoneNumber(selectedCust.getContact_info()) + "\n" +
                               "• ID: " + selectedCust.getId() + "\n\n" +
                               "⚠ This action cannot be undone!");

    Optional<ButtonType> result = confirmAlert.showAndWait();
    
    // If user clicks Cancel or closes the dialog, do nothing
    if (result.isPresent() && result.get() != ButtonType.OK) {
        return;
    }
    // === END VERIFICATION DIALOG ===

    boolean success = Customer_DBO.deleteCustomer(selectedCust.getId());

    if (success) {
        refreshCustomerTable(); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer Deleted");
        alert.setContentText("Customer '" + selectedCust.getName() + "' has been deleted successfully.");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Delete Failed");
        alert.setContentText("Could not delete the customer. Please try again.");
        alert.showAndWait();
    }
}
    @FXML
    private void handleShowOrders(ActionEvent event) {
        Customer selectedCust = customers.getSelectionModel().getSelectedItem();

        if (selectedCust == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select a customer to view orders.");
            alert.showAndWait();
            return;
        }

        System.out.println("=== DEBUG: Selected Customer ID: " + selectedCust.getId() + " ===");

        ObservableList<Order> orders = Order_DBO.getOrdersByCustomerId(selectedCust.getId());

        // DEBUG: Check what we're getting
        System.out.println("Number of orders found: " + orders.size());
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId() + 
                             " | CID in Order object: " + order.getCustomerId() + 
                             " | CAID: " + order.getCashierId());
        }

        if (orders != null && !orders.isEmpty()) {
            Orders.setItems(orders);
            custName.setText("Customer Name: " + selectedCust.getName());

            // DEBUG: Check TableView binding
            System.out.println("TableView items count: " + Orders.getItems().size());

            customerPane.setVisible(false); 
            ordersPane.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Orders");
            alert.setHeaderText("No Orders Found");
            alert.setContentText("This customer has no orders yet.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        ordersPane.setVisible(false);
        customerPane.setVisible(true);
        refreshCustomerTable();
    }
    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
    Customer selectedCust = customers.getSelectionModel().getSelectedItem();

    if (selectedCust == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No Selection");
        alert.setContentText("Please select a customer to update.");
        alert.showAndWait();
        return;
    }
    // Fill form with existing data
    custName.setText(selectedCust.getName());
    Contact.setText(selectedCust.getContact_info());

    // Store the original customer for comparison
    Customer originalCustomer = selectedCust;

    // Store customer to update
    Customer customerToUpdate = selectedCust;

    // Temporarily change Save button action for update
    Save.setOnAction(e -> {
        String newName = custName.getText().trim();
        String newContact = Contact.getText().trim();

        // Check for empty fields
        if (newName.isEmpty() || newContact.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // === ADDED: Validate customer name contains only letters and spaces ===
        if (!isValidCustomerName(newName)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Customer Name");
            alert.setHeaderText("Name Format Error");
            alert.setContentText("Please enter a valid customer name:\n" +
                               "• Must contain only letters and spaces\n" +
                               "• No numbers or special characters allowed");
            alert.showAndWait();
            custName.requestFocus();
            custName.selectAll();
            return;
        }
        // === END NAME VALIDATION ===

        // Remove any spaces, dashes, or parentheses from phone number
        String cleanContactInfo = newContact.replaceAll("[\\s\\-()]", "");

        // Validate contact info format
        if (!isValidEgyptianPhoneNumber(cleanContactInfo)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText("Phone Number Format Error");
            alert.setContentText("Please enter a valid Egyptian phone number:\n" +
                               "• Must start with 01\n" +
                               "• Must be exactly 11 digits\n" +
                               "• Format: 01XXXXXXXXX (e.g., 01123456789)\n" +
                               "• Only digits allowed (no spaces or dashes)");
            alert.showAndWait();
            Contact.requestFocus();
            Contact.selectAll();
            return;
        }

        // Check if phone number already exists (excluding current customer)
        if (!cleanContactInfo.equals(originalCustomer.getContact_info()) && isPhoneNumberExists(cleanContactInfo)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Phone Number");
            alert.setHeaderText("Phone Number Already Exists");
            alert.setContentText("This phone number is already registered to another customer.\n" +
                               "Please use a different phone number.");
            alert.showAndWait();
            Contact.requestFocus();
            Contact.selectAll();
            return;
        }

        // === CONFIRMATION DIALOG FOR UPDATE ===
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Update");
        confirmAlert.setHeaderText("Update Customer Information");

        // Show changes
        StringBuilder changes = new StringBuilder();
        changes.append("Are you sure you want to update this customer?\n\n");

        if (!newName.equals(originalCustomer.getName())) {
            changes.append("• Name: ").append(originalCustomer.getName())
                   .append(" → ").append(newName).append("\n");
        } else {
            changes.append("• Name: ").append(newName).append(" (unchanged)\n");
        }

        if (!cleanContactInfo.equals(originalCustomer.getContact_info())) {
            changes.append("• Phone: ").append(formatPhoneNumber(originalCustomer.getContact_info()))
                   .append(" → ").append(formatPhoneNumber(cleanContactInfo)).append("\n");
        } else {
            changes.append("• Phone: ").append(formatPhoneNumber(cleanContactInfo)).append(" (unchanged)\n");
        }

        changes.append("\nPlease verify the changes are correct.");

        confirmAlert.setContentText(changes.toString());

        Optional<ButtonType> result = confirmAlert.showAndWait();

        // If user clicks Cancel or closes the dialog, do nothing
        if (result.isPresent() && result.get() != ButtonType.OK) {
            return;
        }
        // === END CONFIRMATION DIALOG ===

        customerToUpdate.setName(newName);
        customerToUpdate.setContact_info(cleanContactInfo);

        boolean success = Customer_DBO.updateCustomer(customerToUpdate);

        if (success) {
            // REMOVED THE DUPLICATE SUCCESS ALERT - ONLY SHOW CONFIRMATION DIALOG

            custName.clear();
            Contact.clear();
            refreshCustomerTable();

            // Reset Save button to normal add action
            Save.setOnAction(this::handleSaveCustomer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Update Failed");
            alert.setContentText("Could not update the customer.");
            alert.showAndWait();
        }
    });
}
    
    // Helper method to validate Egyptian phone number format
    private boolean isValidEgyptianPhoneNumber(String phoneNumber) {
        // Remove any spaces, dashes, or other characters
        phoneNumber = phoneNumber.replaceAll("[\\s\\-()]", "");
        
        // Check if it's exactly 11 digits and starts with 01
        if (!phoneNumber.matches("01\\d{9}")) {
            return false;
        }
        
        // Additional validation: check specific Egyptian mobile prefixes
        String prefix = phoneNumber.substring(0, 3);
        String[] validPrefixes = {"010", "011", "012", "015"};
        
        for (String validPrefix : validPrefixes) {
            if (prefix.equals(validPrefix)) {
                return true;
            }
        }
        
        return false;
    }
    
    // Helper method to check if phone number already exists in database
    private boolean isPhoneNumberExists(String phoneNumber) {
        // Remove any formatting for comparison
        phoneNumber = phoneNumber.replaceAll("[\\s\\-()]", "");
        
        String sql = "SELECT COUNT(*) as count FROM Person WHERE Contact_Info = ?";
        
        try (java.sql.Connection connection = DBconnector.connect();
             java.sql.PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, phoneNumber);
            java.sql.ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (java.sql.SQLException e) {
            System.out.println("Error checking phone number: " + e.getMessage());
        }
        
        return false;
    }
    
    // Helper method to format phone number for display
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return phoneNumber;
        }
        // Format as 01X-XXX-XXXX
        return phoneNumber.substring(0, 3) + "-" + 
               phoneNumber.substring(3, 7) + "-" + 
               phoneNumber.substring(7);
    }
    
    // Optional: Clear search button
    @FXML
    private void handleClearSearch(ActionEvent event) {
        Search.clear();
        Search.requestFocus();
    }
    
    // Optional: Enter key search
    @FXML
    private void handleSearch(ActionEvent event) {
        // Focus on table if there are results
        if (!customers.getItems().isEmpty()) {
            customers.requestFocus();
            customers.getSelectionModel().selectFirst();
        }
    } 
    @FXML
    private void showCustomerPane() {
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        reportsPane.setVisible(false);
        employeesPane.setVisible(false);
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(true);
        ordersPane.setVisible(false);
    }
    
//================================================================================================================================    
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
    private void closeAll(){
        reportsPane.setVisible(false);
        productPane.setVisible(false);
        addpane.setVisible(false);
        editpane.setVisible(false);
        employeesPane.setVisible(false);
        addEmpPane.setVisible(false);
        editEmpPane.setVisible(false);
        suppliersPane.setVisible(false);
        addSuppPane.setVisible(false);
        editSuppPane.setVisible(false);
        customerPane.setVisible(false);
        ordersPane.setVisible(false);
        
    }
    private void intialize_Emp(){
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
    }
    private void intialize_Supp(){
         searchSuppField.textProperty().addListener((observable, oldValue, newValue) -> {
         String keyword = newValue.trim();
             if (keyword.isEmpty()) {
             loadSuppliersData(); 
                return;
                }
            ObservableList<ObservableList<String>> data = Supplier_DBO.searchUsersForTable(keyword);

           colSuppID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
           colSuppName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
           colSuppPhone.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));

           suppliersTable.setItems(data);
            });
          loadSuppliersData();
    }
    
    private void initialize_Prod(){
        setupTable();
            loadProducts();
            setupSearch();
    }
    private void initialize_Cust(){
        customerPane.setVisible(true); 
        ordersPane.setVisible(false);

        

        Id.setCellValueFactory(new PropertyValueFactory<>("id")); 
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Contact_info.setCellValueFactory(new PropertyValueFactory<>("contact_info"));

        // ORDER TABLE COLUMNS
        id.setCellValueFactory(new PropertyValueFactory<>("id"));  // Order ID
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tot_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));  // Note the typo
        pay_method.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
        calc_price.setCellValueFactory(new PropertyValueFactory<>("calculated_price"));
        cid.setCellValueFactory(new PropertyValueFactory<>("customerId"));  // FIXED: Changed from "id" to "cid"
        caid.setCellValueFactory(new PropertyValueFactory<>("cashierId"));

        
        setupRealTimeSearch();
        Contact.setPromptText("01123456789 (11 digits starting with 01)");
        refreshCustomerTable();
    }
    static String rec_Email;
    static String rec_Pass;
    public static void receive_Info(List<String> info) {
    rec_Email = info.get(0);
    rec_Pass = info.get(1);
   
}
    

    
 

    @FXML Text adminName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialize_Prod();
            intializeReports();
            intialize_Emp();
            intialize_Supp();
            initialize_Cust();
            adminName.setText(User_DBO.getAdminName(rec_Email, rec_Pass));
            
            
            
            
      
     } catch (SQLException ex) {
         Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
     }
       closeAll();
    }



    
    
   
         

    
    
}
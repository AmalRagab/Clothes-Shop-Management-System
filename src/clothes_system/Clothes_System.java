
package clothes_system;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Clothes_System extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));



        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        Reports.payment_Methods_Prices();

        
        

        
          /*User u = new User("wateen","01543333",Type.USER,"wateen@gmail.com","123w",1000543,Utype.CASHIER);
        if(addUser(u)) System.out.println("sucessful");
*/

    }

    public static boolean addUser(User u){
        String sql ="INSERT INTO Person(ID,Name,Contact_Info,Type) VALUES(?,?,?,?)";
        try(Connection connection=DBconnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,u.getId());
            ps.setString(2,u.getName());
            ps.setString(3,u.getContact_info());
            ps.setString(4,u.getType());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            int personId = 0;
            if (generatedKeys.next()) {
                 personId = generatedKeys.getInt(1);
            }
            String sqlUser = "INSERT INTO User(UID, Email, Password, Type, Salary) VALUES(?,?,?,?,?)";
            PreparedStatement psUser = connection.prepareStatement(sqlUser);
            psUser.setInt(1, personId);
            psUser.setString(2, u.getEmail());
            psUser.setString(3, u.getPassword());
            psUser.setString(4, u.getUtype());
            psUser.setDouble(5, u.getSalary());
            psUser.executeUpdate();
                
   
            System.out.println(" missin done ");
            return true;

            
            
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
    }
    public static void main(String[] args) {
        launch(args);
      
    }
    
}

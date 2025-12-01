
package clothes_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    
    public static final String url = "jdbc:sqlite:System.db";
    public static Connection connect(){
        Connection connection = null;
        try{
           connection = DriverManager.getConnection(url);
            System.out.println("Connected to SQLite database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database!");
            e.printStackTrace();
        }
            return connection;
               
    }
}

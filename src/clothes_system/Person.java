
package clothes_system;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Person {
    public enum Type {USER, CUSTOMER  , SUPPLIER}
    private int id;
    private String name;
    private String contact_info;
    private Type type;
    static private int counter;

    public Person(String name, String contact_info, Type type) {
        initializeCounter();
        counter ++;
        this .id=counter;
        this.name = name;
        this.contact_info = contact_info;
        this.type = type;
    }
    public static void initializeCounter() {
            String sql = "SELECT MAX(ID) FROM Person";
            counter = getMaxId();
        }
    public static int getMaxId() {
            String sql = "SELECT MAX(ID) FROM Person";
            try (Connection connection = DBconnector.connect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
    public int getId() {
        return id;
    }

    public String getType() {
        return type.name();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", contact_info=" + contact_info + ", type=" + type + '}';
    }
    
    
    
}
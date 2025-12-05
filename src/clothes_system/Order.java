
package clothes_system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;



public class Order {
    public enum PaymentMethod {CASH,CREDIT}
    private ArrayList<OrderItems> orderItems;
    private int id;
    private Date date;
    private float discount;
    private PaymentMethod payment_method;
    private double calculated_price;
    private double total_price;
    static private int order_counter;
    private int cashierId;
    private int customerId;

    public Order( Date date, float discount, PaymentMethod payment_method, double calculated_price, double total_price,int cashierId ,int customerId) {
        initializeCounter();
        order_counter++;
        this.cashierId=cashierId;
        this.customerId=customerId;
        this.id=order_counter;
        this.date = date;
        this.discount = discount;
        this.payment_method = payment_method;
        this.calculated_price = calculated_price;
        this.total_price = total_price;
        this.orderItems = new ArrayList<>();
        
    }

    public Order() {
        
    }
    
    
     public static void initializeCounter() {
            String sql = "SELECT MAX(ID) FROM Orders";
            order_counter = getMaxId();
        }
    public static int getMaxId() {
            String sql = "SELECT MAX(ID) FROM Orders";
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

    public void setId(int id) {
        this.id = id;
    }
    

    public int getCashierId() {
        return cashierId;
    }

    public void setCashier(int cashierId) {
        this.cashierId = cashierId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

  
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getPayment_method() {
        return payment_method.name();
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public double getCalculated_price() {
        return calculated_price;
    }

    public void setCalculated_price(double calculated_price) {
        this.calculated_price = calculated_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
    
     public void addOrderItem(OrderItems item) {
        this.orderItems.add(item);
    }

    public void removeOrderItem(OrderItems item) {
        this.orderItems.remove(item);
    }

    public ArrayList<OrderItems> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", date=" + date + ", disount=" + discount + ", payment_method=" + payment_method + ", calculated_price=" + calculated_price + ", total_price=" + total_price + '}';
    }
    
    
    
}

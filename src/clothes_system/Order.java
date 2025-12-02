
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
    private float disount;
    private PaymentMethod payment_method;
    private double calculated_price;
    private double total_price;
    static private int order_counter;
    private Cashier cashier;
    private int cid;     // Customer ID
    private int caid;    // Cashier ID
    

    public Order( Date date, float disount, PaymentMethod payment_method, double calculated_price, double total_price,Cashier cashier) {
        initializeCounter();
        order_counter++;
        this.cashier=cashier;
        this.id=order_counter;
        this.date = date;
        this.disount = disount;
        this.payment_method = payment_method;
        this.calculated_price = calculated_price;
        this.total_price = total_price;
        this.orderItems = new ArrayList<>();
        
    }

    public Order() {}
    

    
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
    

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

  
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        public int getCid() {
        return cid;
    }
    
    public void setCid(int cid) {
        this.cid = cid;
    }
    
    public int getCaid() {
        return caid;
    }
    
    public void setCaid(int caid) {
        this.caid = caid;
    }


    public float getDisount() {
        return disount;
    }

    public void setDisount(float disount) {
        this.disount = disount;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
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
        return "Order{" + "id=" + id + ", date=" + date + ", disount=" + disount + ", payment_method=" + payment_method + ", calculated_price=" + calculated_price + ", total_price=" + total_price + '}';
    }
    
    
    
}

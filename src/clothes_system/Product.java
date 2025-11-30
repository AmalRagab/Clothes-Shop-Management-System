
package clothes_system;



public class Product {

    private  String colour;

    
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String status;
    private int supplierID;

    public Product( int id,int supplierID, String name, double price, int quantity, String status,String colour) {
        this.id = id;
        this.supplierID=supplierID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.colour = colour;
    }

    public int getId() {
        return id;
    }

    public int getSupplierID(){
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", status=" + status + '}';
    }
    
    
}

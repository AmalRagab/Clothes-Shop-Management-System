
package clothes_system;


public class Customer extends Person {
    
    public Customer( String name, String contact_info, Type type) {
        super( name, contact_info, type);
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString()+ '}';
    }
    
    
}
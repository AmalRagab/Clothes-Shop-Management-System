
package clothes_system;


public class User extends Person {
    public enum Utype {ADMIN ,CASHIER}
    private String email;
    private String password;
    private double salary;
    private Utype utype;

    public User(String name, String contact_info, Type type,String email, String password, double salary, Utype utype) {
        super( name, contact_info, type);
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.utype = utype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getUtype() {
        return utype.name();
    }

    public void setUtype(Utype utype) {
        this.utype = utype;
    }
    
    
    
}
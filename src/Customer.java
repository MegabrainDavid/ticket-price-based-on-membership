public class Customer {
    private String firstName;
    private String lastName;
    private String customerID;
    private float amountSpent;

    public Customer(){

    }
    public Customer(String customerID,String firstName, String lastName, float amountSpent){
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerID = customerID;
        this.amountSpent = amountSpent;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }
    public String getCustomerID(){
        return customerID;
    }
    public void setAmountSpent(float amountSpent){
        this.amountSpent = amountSpent;
    }
    public float getAmountSpent(){
        return amountSpent;
    }
    
}

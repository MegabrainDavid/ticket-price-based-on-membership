public class gold extends Customer{
    private int discount; 
    public gold(Customer cus){

    }
    public gold(String customerID, String firstName, String lastName, float amountSpent, int discount){
        super(customerID, firstName, lastName, amountSpent);
        this.discount = discount;
    }
 
    public void setDiscount(int discount){
        this.discount = discount;
    }
    public int getDiscount(){
        return discount; 
    }
    @Override
    public String toString() {
        return  this.discount + "%";
    }
 
}

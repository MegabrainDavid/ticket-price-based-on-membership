public class Plat extends Customer{
    private int bonus; 
    public Plat(){

    }
    public Plat(String customerID, String firstName, String lastName,  float amountSpent, int bonus){
        super(customerID, firstName, lastName, amountSpent);
        this.bonus = bonus;
        
    }
    public void setBonus(int bonus){
        this.bonus = bonus;
    }
    public int getBonus(){
        return bonus; 
    }
    @Override
    public String toString() {
        return this.bonus + "";
    }
}

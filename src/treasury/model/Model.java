package treasury.model;

public class Model {
    
    private int amountOfMoney;
    
    public void deposit(int amountAdded) {
        amountOfMoney += amountAdded;
    }
    
    public void withdrawal(int amountSubtracted) {
        amountOfMoney -= amountSubtracted;
    }
    
    public int getMoney() {
        return amountOfMoney;
    }
    
}

package treasury.model;

import treasury.Main;

public class Model {
    
    private Main main;
    
    public void deposit(int amountAdded) {
        int amountOfMoney = main.getMoney();
        amountOfMoney += amountAdded;
        main.setMoney(amountOfMoney);
    }
    
    public void withdrawal(int amountSubtracted) {
        int amountOfMoney = main.getMoney();
        amountOfMoney -= amountSubtracted;
        main.setMoney(amountOfMoney);
    }

    public void setMain(Main main) {
        this.main = main;
    }
    
}

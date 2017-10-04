package treasury.view;

import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.ImageView;
import treasury.Main;

public class AppController {

    private Main main;
    @FXML
    private ImageView graphics;
    @FXML
    private Label amountOfMoney;
    @FXML
    private Label currencySymbol;
    @FXML
    public TextField textField;
    
    /**
     *  prevents from typing anything apart from numbers 0-9
     */
    public UnaryOperator<Change> integerFilter = change -> {
    String input = change.getText();
    if (input.matches("[0-9]*")) { 
        return change;
    }
        return null;
    };

    public void setMain(Main main) {
        this.main = main;
        setImage();
        updateLabel();
    }
    public void initialize() {
        textField.setTextFormatter(new TextFormatter<>(integerFilter));
    }   
    
    @FXML
    private void handleDeposit() {
        int amount = Integer.valueOf(textField.getText());
        main.getModel().deposit(amount);
        updateLabel();
        textField.clear();
    }
    
    @FXML
    private void handleWithdrawal() {
        int amount = Integer.valueOf(textField.getText());
        main.getModel().withdrawal(amount);
        updateLabel();
        textField.clear();
    }
    
    @FXML
    private void handleSettings() {
        main.showSettings();
    }
    
    @FXML
    private void handleInfo() {
        main.showInfo();
    }
    private void updateLabel() {
        amountOfMoney.setText(String.valueOf(main.getMoney()));
    }
    public void setCurrencySymbol() {
        String currency = main.getCurrency();
        switch (currency) {
            case "USD":
                currencySymbol.setText("$");
                break;
            case "PLN":
                currencySymbol.setText("PLN");
                break;    
            case "EUR":
                currencySymbol.setText("€");
                break;    
            case "GBP":
                currencySymbol.setText("£");
                break;      
            default:
                throw new AssertionError();
        }
    }
    
    private void setImage() {
        String imagePath = 
    }
    
}

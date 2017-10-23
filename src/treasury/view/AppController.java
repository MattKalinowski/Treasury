package treasury.view;

import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import treasury.Main;
import treasury.util.Utility;

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
    @FXML
    private AnchorPane anchorPane;
    
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
        if(!textField.getText().isEmpty()) {
        int amount = Integer.valueOf(textField.getText());
        main.getModel().deposit(amount);
        updateLabel();
        textField.clear();
        setImage();
        isGoalReached();
        Utility.playDepositSound();
        }
    }
    
    @FXML
    private void handleWithdrawal() {
        if(!textField.getText().isEmpty()) {
        int amount = Integer.valueOf(textField.getText());
        main.getModel().withdrawal(amount);
        updateLabel();
        textField.clear();
        setImage();
        Utility.playWithdrawalSound();
        }
    }
    
    @FXML
    private void handleSettings() {
        Utility.playClickSound();
        main.showSettings();
    }
    
    @FXML
    private void handleInfo() {
        Utility.playClickSound();
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
    
    private double calculatePercentageProgress() {
        double progress;
        if(main.getGoal() != 0) {
        double money = main.getMoney();
        double goal = main.getGoal();
        progress = (money/goal)*100;
        } else {
            progress = 0;
        }
        return progress;
    }
    
    private void setImage() {
        graphics.setImage(getImage(chooseImage()));
    }
    
    private Image getImage(String url) {
        Image image = new Image(getClass().getResource(url).toExternalForm());
        return image;
    }
    
    private String chooseImage() {
        double progress = calculatePercentageProgress();
        String imageUrl = null;
        if (progress == 0) {
            imageUrl = "graphics/Treasure0.png";
        } 
        else if (progress > 0 && progress <= 2) {
            imageUrl = "graphics/Treasure1.png";
        }
        else if (progress > 2 && progress <= 5) {
            imageUrl = "graphics/Treasure2.png";
        }
        else if (progress > 5 && progress <= 10) {
            imageUrl = "graphics/Treasure3.png";
        }
        else if (progress > 10 && progress <= 14) {
            imageUrl = "graphics/Treasure4.png";
        }
        else if (progress > 14 && progress <= 17) {
            imageUrl = "graphics/Treasure5.png";
        }
        else if (progress > 17 && progress <= 20) {
            imageUrl = "graphics/Treasure6.png";
        }
        else if (progress > 20 && progress <= 23) {
            imageUrl = "graphics/Treasure7.png";
        }
        else if (progress > 23 && progress <= 25) {
            imageUrl = "graphics/Treasure8.png";
        }
        else if (progress > 25 && progress <= 30) {
            imageUrl = "graphics/Treasure9.png";
        }
        else if (progress > 30 && progress <= 35) {
            imageUrl = "graphics/Treasure10.png";
        }
        else if (progress > 35 && progress <= 40) {
            imageUrl = "graphics/Treasure11.png";
        }
        else if (progress > 40 && progress <= 45) {
            imageUrl = "graphics/Treasure12.png";
        }
        else if (progress > 45 && progress <= 51) {
            imageUrl = "graphics/Treasure13.png";
        }
        else if (progress > 51 && progress <= 58) {
            imageUrl = "graphics/Treasure14.png";
        }
        else if (progress > 58 && progress <= 63) {
            imageUrl = "graphics/Treasure15.png";
        }
        else if (progress > 63 && progress <= 70) {
            imageUrl = "graphics/Treasure16.png";
        }
        else if (progress > 70 && progress <= 80) {
            imageUrl = "graphics/Treasure17.png";
        }
        else if (progress > 80 && progress <= 90) {
            imageUrl = "graphics/Treasure18.png";
        }
        else if (progress > 90 && progress < 100) {
            imageUrl = "graphics/Treasure19.png";
        }
        else if (progress >= 100) {
            imageUrl = "graphics/Treasure20.png";
        } 
        return imageUrl;
    }
    
    private void isGoalReached() {
        double progress = calculatePercentageProgress();
        if (progress >= 100) {
            main.showCongratulations();
        }
    }
    
    @FXML
    private void unfocus() {
        anchorPane.requestFocus();
    }
    
}

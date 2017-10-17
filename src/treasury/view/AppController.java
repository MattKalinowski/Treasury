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
        playDepositSound();
        int amount = Integer.valueOf(textField.getText());
        main.getModel().deposit(amount);
        updateLabel();
        textField.clear();
        setImage();
        isGoalReached();
        }
    }
    
    @FXML
    private void handleWithdrawal() {
        if(!textField.getText().isEmpty()) {
        playWithdrawalSound();
        int amount = Integer.valueOf(textField.getText());
        main.getModel().withdrawal(amount);
        updateLabel();
        textField.clear();
        setImage();
        }
    }
    
    @FXML
    private void handleSettings() {
        playClickSound();
        main.showSettings();
    }
    
    @FXML
    private void handleInfo() {
        playClickSound();
        main.showInfo();
    }
    private void updateLabel() {
        amountOfMoney.setText(String.valueOf(main.getMoney()));
    }
    public void setCurrencySymbol() {
        String currency = main.getCurrency(); //currency jest null
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
        else if (progress > 0 && progress <= 5) {
            imageUrl = "graphics/Treasure1.png";
        }
        else if (progress > 5 && progress <= 10) {
            imageUrl = "graphics/Treasure2.png";
        }
        else if (progress > 10 && progress <= 15) {
            imageUrl = "graphics/Treasure3.png";
        }
        else if (progress > 15 && progress <= 20) {
            imageUrl = "graphics/Treasure4.png";
        }
        else if (progress > 20 && progress <= 25) {
            imageUrl = "graphics/Treasure5.png";
        }
        else if (progress > 25 && progress <= 30) {
            imageUrl = "graphics/Treasure6.png";
        }
        else if (progress > 30 && progress <= 35) {
            imageUrl = "graphics/Treasure7.png";
        }
        else if (progress > 35 && progress <= 40) {
            imageUrl = "graphics/Treasure8.png";
        }
        else if (progress > 40 && progress <= 45) {
            imageUrl = "graphics/Treasure9.png";
        }
        else if (progress > 45 && progress <= 50) {
            imageUrl = "graphics/Treasure10.png";
        }
        else if (progress > 50 && progress <= 55) {
            imageUrl = "graphics/Treasure11.png";
        }
        else if (progress > 55 && progress <= 60) {
            imageUrl = "graphics/Treasure12.png";
        }
        else if (progress > 60 && progress <= 65) {
            imageUrl = "graphics/Treasure13.png";
        }
        else if (progress > 65 && progress <= 70) {
            imageUrl = "graphics/Treasure14.png";
        }
        else if (progress > 70 && progress <= 75) {
            imageUrl = "graphics/Treasure15.png";
        }
        else if (progress > 75 && progress <= 80) {
            imageUrl = "graphics/Treasure16.png";
        }
        else if (progress > 80 && progress <= 85) {
            imageUrl = "graphics/Treasure17.png";
        }
        else if (progress > 85 && progress <= 90) {
            imageUrl = "graphics/Treasure18.png";
        }
        else if (progress > 90 && progress <= 99) {
            imageUrl = "graphics/Treasure19.png";
        }
        else if (progress > 99) {
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
    
    private void playClickSound() {
        AudioClip click = new AudioClip(this.getClass().getResource("sounds/click.wav").toString());
        click.play();
    }
    
    private void playDepositSound() {
        AudioClip deposit = new AudioClip(this.getClass().getResource("sounds/deposit.wav").toString());
        deposit.play();
    }
    
    private void playWithdrawalSound() {
        AudioClip withdrawal = new AudioClip(this.getClass().getResource("sounds/withdrawal.wav").toString());
        withdrawal.play();
    }
    
}

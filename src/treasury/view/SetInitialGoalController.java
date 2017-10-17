package treasury.view;

import java.util.function.UnaryOperator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import treasury.Main;

public class SetInitialGoalController {
    
    private Main main;
    private Stage setInitialGoalStage;
    @FXML
    private TextField textField;
    @FXML
    private AnchorPane anchorPane;
    @FXML 
    private Button okButton;
    
    /**
     *  prevents from typing anything apart from numbers 0-9
     */
    public UnaryOperator<TextFormatter.Change> integerFilter = change -> {
    String input = change.getText();
    if (input.matches("[0-9]*")) { 
        return change;
    }
        return null;
    };
    
    /**
     *  prevents from typing numbers longer than i
     */
    public EventHandler<KeyEvent> maxLength(final Integer i) {
        return (KeyEvent arg0) -> {
            TextField tx = (TextField) arg0.getSource();
            if (tx.getText().length() >= i) {
                arg0.consume();
            }
        };

    }
    
    public void initialize() {
        textField.setTextFormatter(new TextFormatter<>(integerFilter));
        textField.addEventFilter(KeyEvent.KEY_TYPED, maxLength(6));
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              okButton.fire();
             }
        }); 
    }    
    
    public void setSetGoalStage(Stage setInitialGoalStage) {
        this.setInitialGoalStage = setInitialGoalStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleOkButton() {
        if(!textField.getText().isEmpty()) {
        playClickSound();
        int id = main.getDatabase().selectUserId(main.getName());
        main.getDatabase().updateNewUser(false, id);
        main.setGoal(Integer.valueOf(textField.getText()));
        setInitialGoalStage.close();
        main.showApp();
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
    
}

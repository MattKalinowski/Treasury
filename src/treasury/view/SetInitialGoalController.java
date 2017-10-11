package treasury.view;

import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
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
    
    public void initialize() {
        textField.setTextFormatter(new TextFormatter<>(integerFilter));
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
    
}

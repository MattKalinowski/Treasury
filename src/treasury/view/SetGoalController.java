package treasury.view;

import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import treasury.Main;

public class SetGoalController {

    private Main main;
    private Stage setGoalStage;
    @FXML
    private TextField textField;
    
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
    }    

    public void setSetGoalStage(Stage setGoalStage) {
        this.setGoalStage = setGoalStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void handleAccept() {
        main.setGoal(Integer.valueOf(textField.getText()));
        main.showSettings();
        setGoalStage.close();
    }
    @FXML
    private void handleCancel() {
        setGoalStage.close();
    }
    
}

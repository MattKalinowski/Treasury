package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import treasury.Main;

public class ChangeNameController {

    private Stage changeNameStage;
    private Main main;
    @FXML
    private TextField textField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button acceptButton;
    @FXML
    private Label alert;

    public void initialize() {
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              acceptButton.fire();
             }
        }); 
    }    

    public void setChangeNameStage(Stage changeNameStage) {
        this.changeNameStage = changeNameStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void handleAccept() {
        String name;
        if (validateName()) {
        main.setName(textField.getText());
        name = main.getName();
        int id = main.getDatabase().selectUserId(name);
        main.getDatabase().updateUserName(name, id);
        main.showSettings();
        changeNameStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        changeNameStage.close();
    }
    
    @FXML
    private void unfocus() {
        anchorPane.requestFocus();
    }
    
    private boolean validateName() {
        String username = textField.getText();
        if (username.isEmpty()) {
            return false;
        } 
        else if (main.getDatabase().selectUserName(username)) {
            alert.setVisible(true);
            return false;
        } else {
           // nameAlert2.setVisible(false);
            return true;
        }
    }
    
}

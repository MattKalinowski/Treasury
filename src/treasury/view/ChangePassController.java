package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import treasury.Main;

public class ChangePassController {

    private Stage changePassStage;
    private Main main;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button acceptButton;
    @FXML
    private Label alert1;
    @FXML
    private Label alert2;

    public void initialize() {
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              acceptButton.fire();
             }
        }); 
    }    

    public void setChangePassStage(Stage changePassStage) {
        this.changePassStage = changePassStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void handleAccept() {
        passwordChange();
    }
    @FXML
    private void handleCancel() {
        changePassStage.close();
    }
    private void passwordChange() {
        String oldPassInput = oldPass.getText();
        String newPassInput = newPass.getText();
        String oldPassInputHash = main.getPasswordHash(oldPassInput);
        String newPassInputHash = main.getPasswordHash(newPassInput);
        String password = main.getDatabase().selectUserPassword(main.getName());
        int passwordMinLength = 3;
        int passLength = newPass.getText().length();
        if (oldPassInputHash.equals(password) && passLength >= passwordMinLength) {
            String name;
            String newPassword;
            main.setPass(newPassInputHash);
            newPassword = main.getPass();
            name = main.getName();
            int id = main.getDatabase().selectUserId(name);
            main.getDatabase().updateUserPassword(newPassword, id);
            main.showSettings();
            changePassStage.close();
        } else if (!oldPassInputHash.equals(password)) {
            alert2.setVisible(false);
            alert1.setVisible(true);
        }
        else if (passLength < passwordMinLength) {
            alert1.setVisible(false);
            alert2.setVisible(true);
        }
    }
    
    @FXML
    private void unfocus() {
        anchorPane.requestFocus();
    }
    
}

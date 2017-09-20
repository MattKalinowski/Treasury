package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import treasury.Main;

public class ChangePassController {

    private Stage changePassStage;
    private Main main;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;

    public void initialize() {
        // TODO
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
        String oldPassword = oldPass.getText();
        String newPassword = newPass.getText();
        int passwordMinLength = 3;
        if (oldPassword.equals(main.getPass()) && newPassword.length() >= passwordMinLength) {
            main.setPass(newPassword);
            main.showSettings();
            changePassStage.close();
        } else if (!oldPassword.equals(main.getPass())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(changePassStage);
            alert.setHeaderText(main.getBundle().getString("inwalidpass"));
            alert.setContentText(main.getBundle().getString("typecorrect"));
            alert.showAndWait(); 
        }
        else if (newPassword.length() < passwordMinLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(changePassStage);
            alert.setHeaderText(main.getBundle().getString("inwalidpass"));
            alert.setContentText(main.getBundle().getString("passhint"));
            alert.showAndWait(); 
        }
    }
    
}

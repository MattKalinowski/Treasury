package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
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
        /*
           Accepting changes by clicking Enter button
        */
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
        int id = main.getDatabase().selectUserId(main.getName());
        if (validateName()) {
        playClickSound();
        main.setName(textField.getText());
        name = main.getName();
        main.getDatabase().updateUserName(name, id);
        main.showSettings();
        changeNameStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        playClickSound();
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
        else if (main.getDatabase().isUserNameInDatabase(username)) {
            alert.setVisible(true);
            return false;
        } else {
            return true;
        }
    }
    
    private void playClickSound() {
        AudioClip click = new AudioClip(this.getClass().getResource("sounds/click.wav").toString());
        click.play();
    }
    
}

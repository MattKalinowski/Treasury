package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import treasury.Main;
import treasury.util.Utility;

public class SignUpController {

    private Main main;
    private String username;
    private String password;
    private String email;
    
    @FXML
    private TextField emailField;
    @FXML
    private TextField confirmEmailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private CheckBox agreeToTerms;
    @FXML
    private Label emailAlert1;
    @FXML
    private Label emailAlert2;
    @FXML
    private Label emailAlert3;
    @FXML
    private Label passwordAlert;
    @FXML
    private Label nameAlert;
    @FXML
    private Label nameAlert2;
    @FXML 
    private Label termsAlert;
    @FXML
    private Button signupButton;
    @FXML
    private AnchorPane anchorPane;
    
    public void setMain(Main main) {
        this.main = main;
    }
    
    public void initialize() {
         /*
           Accepting changes by clicking Enter button
        */
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              signupButton.fire();
             }
        }); 
    }
    
    @FXML
    private void handleLogIn() {
        Utility.playClickSound();
        main.showLogIn();
    }
    
    @FXML
    private void handleSignUp() {
        Utility.playClickSound();
        if (validateTextFields()) {
            createAnAccount();
            main.showLogIn();
        }
    }
    
    private void createAnAccount() {
        main.setName(username);
        main.setPass(password);
        main.setEmail(email);
        main.getDatabase().insertLoginData(username, password, email);
        main.getDatabase().insertInitialAppData(true, 0, 0, main.getCurrency());
    }
    
    private boolean validateTextFields() {
        return validateEmail() && validatePassword() 
                && validateName() && validateAgreeToTerms();
    }
    
    private boolean validateEmail() {
        boolean validate;
        email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();
        if (!email.equals(confirmEmail)) {
            emailAlert2.setVisible(true);
            validate = false;
        } else {
            emailAlert2.setVisible(false);
        }
        if (email.isEmpty()) {
            emailAlert3.setVisible(true);
            validate = false;
        } else {
            emailAlert3.setVisible(false);
        }
        if (main.getDatabase().isUserEmailInDatabase(email)) {
            emailAlert1.setVisible(true);
            validate = false;
        } else {
            emailAlert1.setVisible(false);
        }
        return !(validate = false);
    }
    
    private boolean validateName() {
        username = usernameField.getText();
        if (username.isEmpty()) {
            nameAlert2.setVisible(true);
            return false;
        } 
        else if (main.getDatabase().isUserNameInDatabase(username)) {
            nameAlert.setVisible(true);
            return false;
        } else {
            nameAlert2.setVisible(false);
            return true;
        }
    }
    
    private boolean validatePassword() {
        int passwordMinLength = 3;
        int passLength = passwordField.getText().length();
        if (passLength < passwordMinLength) {
            passwordAlert.setVisible(true);
            return false;
        } else {
            passwordAlert.setVisible(false);
            password = main.getPasswordHash(passwordField.getText());
            return true;
        }
    }
    
    private boolean validateAgreeToTerms() {
        if (!agreeToTerms.isSelected()) {
            termsAlert.setVisible(true);
            return false;
        } else {
            termsAlert.setVisible(false);
            return true;
        }
    }
    
    @FXML
    private void unfocus() {
        anchorPane.requestFocus();
    }
    
}

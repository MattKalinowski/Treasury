package treasury.view;

import java.util.Locale;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import treasury.Main;

public class LogInController {

    private Main main;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox keepLoggedInBox;
    @FXML
    private Button switchLanguageButton;
    private boolean keepLoggedIn;
    private boolean newUser;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label alert;
    
    
     // allows to press a button using "enter"
    public void initialize() {
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              loginButton.fire();
             }
        }); 
    }
    
    public void setMain(Main main) {
            this.main = main;
            switchLangButtonPicture(main.getLocale().getLanguage());
    }
    @FXML
    private void handleLogIn() {
        validate();
    }
    @FXML
    private void handleSignUp() {
        main.showSignUp();
    }
    @FXML
    private void handleTerms() {
        main.showTerms();
    }
    @FXML
    private void handleLanguageSwitch() {
        if (main.getLocale().getLanguage().equals("pl")) {
            main.setLocale(new Locale("en"));
            main.getDatabase().updateLanguage("en");
            main.showLogIn();
        } else if (main.getLocale().getLanguage().equals("en")) {
            main.setLocale(new Locale("pl"));
            main.getDatabase().updateLanguage("pl");
            main.showLogIn();
        }
        main.setDefaultCurrency();
    }
    @FXML
    private void handleKeepLoggedIn() {
        keepLoggedIn = keepLoggedInBox.isSelected();
    }

    private void validate() {
        String typedName = name.getText();
        String typedPass = password.getText();
        String typedPassHash = main.getPasswordHash(typedPass);
        if (main.getDatabase().verifyLoginData(typedName, typedPassHash)) {
            main.getDatabase().insertLoginfo(1, keepLoggedIn, name.getText());
            loadAppData(typedName);
            if (newUser) {
                main.showWelcomeToTreasury();
            } else {
            main.showApp();
            }
        } else {
            alert.setVisible(true);
        }
    }

    public void loadAppData(String name) {
        int userId = main.getDatabase().selectUserId(name);
        main.setName(name);
        main.setMoney(main.getDatabase().selectMoney(userId));
        main.setGoal(main.getDatabase().selectGoal(userId));
        main.setCurrency(main.getDatabase().selectCurrency(userId));
        newUser = main.getDatabase().selectNewUser(userId);
    }
    
    public boolean getLogInfo() {
        return keepLoggedIn;
    }
    
    /*
       Changes button's style by selecting corresponding CSS' Pseudo Class 
    */
    private void switchLangButtonPicture(String lang) {
        if (lang.equals("pl")) {
            PseudoClass buttonPlPseudoClass = PseudoClass.getPseudoClass("button-pl");
            switchLanguageButton.pseudoClassStateChanged(buttonPlPseudoClass, true);
        } else if (lang.equals("en")) {
            PseudoClass buttonEnPseudoClass = PseudoClass.getPseudoClass("button-en");
            switchLanguageButton.pseudoClassStateChanged(buttonEnPseudoClass, true);
        }
        
    }
    
    @FXML
    private void unfocus() {
        anchorPane.requestFocus();
    }
    
}

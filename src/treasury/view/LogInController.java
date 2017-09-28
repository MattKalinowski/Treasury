package treasury.view;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import treasury.Main;

public class LogInController implements Initializable {

    private Main main;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox keepLoggedInBox;
    @FXML
    private Button switchLanguage;
    private boolean keepLoggedIn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setMain(Main main) {
            this.main = main;
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
            main.showApp();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getWindow());
            alert.setHeaderText(main.getBundle().getString("inwalidpassandname"));
            alert.showAndWait(); 
        }
    }

    public void loadAppData(String name) {
        int userId = main.getDatabase().selectUserId(name);
        main.setName(name);
        main.setMoney(main.getDatabase().selectMoney(userId));
        main.setGoal(main.getDatabase().selectGoal(userId));
        main.setCurrency(main.getDatabase().selectCurrency(userId));
    }
    
    public boolean getLogInfo() {
        return keepLoggedIn;
    }
    
}

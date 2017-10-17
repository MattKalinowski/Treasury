package treasury.view;

import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import treasury.Main;

public class SettingsController {

    private Main main;
    
    @FXML
    private Label name;
    @FXML
    private Label goal;
    @FXML
    private Label currencySymbol;
    @FXML
    private ImageView flag;
    @FXML
    private Button okButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<String> languagesBox;
    private final ObservableList<String> languages = FXCollections
            .observableArrayList("english", "polski");
    @FXML
    private ChoiceBox<String> currencyBox;
    private final ObservableList<String> currencies = FXCollections
            .observableArrayList("USD", "EUR", "GBP", "PLN");
    
    private final Image EN = new Image(getClass().getResource("graphics/EN_flag.jpg").toExternalForm());
    private final Image PL = new Image(getClass().getResource("graphics/PL_flag.jpg").toExternalForm());
    

    public void setMain(Main main) {
        this.main = main;
        
        name.setText(main.getName());
        
        currencyBox.setItems(currencies);
        currencyBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> 
                { main.setCurrency(newValue); setCurrencySymbol(); });
        
        languagesBox.setItems(languages);
        languagesBox.setValue(main.getLanguageString());
        languagesBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) ->
                        handleLanguageSwitch(newValue));
    }
    
    public void initialize() {
        anchorPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
              okButton.fire();
             }
        }); 
    }

    @FXML
    private void handleOk() {
        playClickSound();
        main.showApp();
    }
    
    @FXML
    private void handleChangePassword() {
        playClickSound();
        main.showChangePass();
    }
    
    @FXML
    private void handleChangeName() {
        playClickSound();
        main.showChangeName();
    }
    
    @FXML
    private void handleLogout() {
        playClickSound();
        int id = 1;
        main.getDatabase().deleteLoginfo(id);
        main.showLogIn();
    }
    
    @FXML
    private void handleDelete() {
        playClickSound();
        main.showDeleteAccount();
    }
    
    @FXML
    private void handleChangeGoal() {
        playClickSound();
        main.showSetGoal();
    }
    
    private void handleLanguageSwitch(String lang) {
        if (lang.equals("polski")) {
            main.setLocale(new Locale("pl"));
            main.getDatabase().updateLanguage("pl");
        } else if (lang.equals("english")) {
            main.setLocale(new Locale("en"));
            main.getDatabase().updateLanguage("en");
        }
        main.showSettings();
    }
    
    public void setFlag() {
        String language = main.getLocale().getLanguage();
        switch (language) {
            case "en":
                flag.setImage(EN);
                break;
            case "pl":
                flag.setImage(PL);
                break;    
            default:
                throw new AssertionError();
        }
    }
    
    public void setDefaultCurrency() {
        String currency = main.getCurrency();
        switch (currency) {
            case "USD":
                currencyBox.setValue(currencies.get(0));
                break;
            case "PLN":
                currencyBox.setValue(currencies.get(3));
                break;
            case "EUR":
                currencyBox.setValue(currencies.get(1));
                break;
            case "GBP":
                currencyBox.setValue(currencies.get(2));
                break;    
            default:
                throw new AssertionError();
        }
    }
    
    public void setCurrencySymbol() {
        String currency = main.getCurrency();
        switch (currency) {
            case "USD":
                currencySymbol.setText("$");
                break;
            case "PLN":
                currencySymbol.setText("PLN");
                break;    
            case "EUR":
                currencySymbol.setText("€");
                break;    
            case "GBP":
                currencySymbol.setText("£");
                break;      
            default:
                throw new AssertionError();
        }
    }
    
    public void setGoalLabel() {
        goal.setText(String.valueOf(main.getGoal()));
    }
    
    private void playClickSound() {
        AudioClip click = new AudioClip(this.getClass().getResource("sounds/click.wav").toString());
        click.play();
    }
    
}

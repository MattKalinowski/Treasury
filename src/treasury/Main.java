package treasury;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import treasury.data.Database;
import treasury.data.Security;
import treasury.model.Model;
import treasury.view.AppController;
import treasury.view.ChangeNameController;
import treasury.view.ChangePassController;
import treasury.view.CongratulationsController;
import treasury.view.DeleteAccountController;
import treasury.view.InfoController;
import treasury.view.LogInController;
import treasury.view.SetGoalController;
import treasury.view.SetInitialGoalController;
import treasury.view.SettingsController;
import treasury.view.SignUpController;
import treasury.view.TermsController;
import treasury.view.WelcomeToTreasuryController;

public class Main extends Application {
    
    private Stage window;
    private BorderPane rootLayout;
    private final Model mod = new Model();
    private final Database database = new Database();
    private Locale locale;
    private ResourceBundle bundle;
    private String name;
    private String password;
    private String email;
    private String language;
    private String currency;
    private boolean autolog;
    private int money;
    private int goal;
    
    @Override
    public void start(Stage primaryStage) {
        
        window = primaryStage;
        window.setTitle("Treasury");
        setLanguage();
        mod.setMain(this);
        initRootLayout();
        showLogIn();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLogIn() {
        try {   
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/LogIn.fxml"));
            AnchorPane logIn = (AnchorPane) loader.load();

            rootLayout.setCenter(logIn);
            
        LogInController controller = loader.getController();
        controller.setMain(this);
        
        boolean loginfo = getDatabase().selectLoginfo();
        String loginfoName = getDatabase().selectLoginfoName();
        autolog(loginfo, loginfoName);
            if (autolog) {
                controller.loadAppData(name);
                showApp();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSignUp() {
        try {   
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/SignUp.fxml"));
            AnchorPane signUp = (AnchorPane) loader.load();

            rootLayout.setCenter(signUp);
            
        SignUpController controller = loader.getController();
        controller.setMain(this);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showWelcomeToTreasury() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/WelcomeToTreasury.fxml"));
            AnchorPane welcomeToTreasury = (AnchorPane) loader.load();
            
            rootLayout.setCenter(welcomeToTreasury);
            int UserId = 1;
            window.setOnCloseRequest(event -> {getDatabase().deleteLoginfo(UserId);} );
            
            WelcomeToTreasuryController controller = loader.getController();
            controller.setMain(this);
        
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showApp() {
        try {   
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/App.fxml"));
            AnchorPane app = (AnchorPane) loader.load();
            
            rootLayout.setCenter(app);
            window.setOnCloseRequest(event -> {} ); //overrides the listener from showWelcomeToTreasury()
            
        AppController controller = loader.getController();
        controller.setMain(this);
        setDefaultCurrency();
        controller.setCurrencySymbol();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSettings() {
        try {   
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/Settings.fxml"));
            AnchorPane settings = (AnchorPane) loader.load();

            rootLayout.setCenter(settings);
            
        SettingsController controller = loader.getController();
        controller.setMain(this);
        controller.setDefaultCurrency(); 
        controller.setFlag(); 
        controller.setGoalLabel();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showTerms() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/Terms.fxml"));
            AnchorPane terms = (AnchorPane) loader.load();
            
            Stage termsStage = new Stage();
            termsStage.initModality(Modality.WINDOW_MODAL);
            termsStage.initOwner(window);
            Scene scene = new Scene(terms);
            termsStage.setScene(scene);
            
            TermsController controller = loader.getController();
            controller.setTermsStage(termsStage);
            controller.setMain(this);
        
            termsStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showInfo() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/Info.fxml"));
            AnchorPane info = (AnchorPane) loader.load();
            
            Stage infoStage = new Stage();
            infoStage.initModality(Modality.WINDOW_MODAL);
            infoStage.initOwner(window);
            Scene scene = new Scene(info);
            infoStage.setScene(scene);
            
            InfoController controller = loader.getController();
            controller.setInfoStage(infoStage);
            controller.setMain(this);
        
            infoStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSetGoal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/SetGoal.fxml"));
            AnchorPane setGoal = (AnchorPane) loader.load();
            
            Stage setGoalStage = new Stage();
            setGoalStage.initModality(Modality.WINDOW_MODAL);
            setGoalStage.initStyle(StageStyle.UNDECORATED);
            setGoalStage.initOwner(window);
            Scene scene = new Scene(setGoal);
            setGoalStage.setScene(scene);
            
            SetGoalController controller = loader.getController();
            controller.setSetGoalStage(setGoalStage);
            controller.setMain(this);
        
            setGoalStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSetInitialGoal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/SetInitialGoal.fxml"));
            AnchorPane setInitialGoal = (AnchorPane) loader.load();
            
            Stage setInitialGoalStage = new Stage();
            setInitialGoalStage.initModality(Modality.WINDOW_MODAL);
            setInitialGoalStage.initStyle(StageStyle.UNDECORATED);
            setInitialGoalStage.initOwner(window);
            Scene scene = new Scene(setInitialGoal);
            setInitialGoalStage.setScene(scene);
            
            SetInitialGoalController controller = loader.getController();
            controller.setSetGoalStage(setInitialGoalStage);
            controller.setMain(this);
        
            setInitialGoalStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showChangePass() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/ChangePass.fxml"));
            AnchorPane changePass = (AnchorPane) loader.load();
            
            Stage changePassStage = new Stage();
            changePassStage.initModality(Modality.WINDOW_MODAL);
            changePassStage.initStyle(StageStyle.UNDECORATED);
            changePassStage.initOwner(window);
            Scene scene = new Scene(changePass);
            changePassStage.setScene(scene);
            
            ChangePassController controller = loader.getController();
            controller.setChangePassStage(changePassStage);
            controller.setMain(this);
        
            changePassStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showChangeName() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/ChangeName.fxml"));
            AnchorPane changeName = (AnchorPane) loader.load();
            
            Stage changeNameStage = new Stage();
            changeNameStage.initModality(Modality.WINDOW_MODAL);
            changeNameStage.initStyle(StageStyle.UNDECORATED);
            changeNameStage.initOwner(window);
            Scene scene = new Scene(changeName);
            changeNameStage.setScene(scene);
            
            ChangeNameController controller = loader.getController();
            controller.setChangeNameStage(changeNameStage);
            controller.setMain(this);
        
            changeNameStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showDeleteAccount() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/DeleteAccount.fxml"));
            AnchorPane deleteAccount = (AnchorPane) loader.load();
            
            Stage deleteAccountStage = new Stage();
            deleteAccountStage.initModality(Modality.WINDOW_MODAL);
            deleteAccountStage.initStyle(StageStyle.UNDECORATED);
            deleteAccountStage.initOwner(window);
            Scene scene = new Scene(deleteAccount);
            deleteAccountStage.setScene(scene);
            
            DeleteAccountController controller = loader.getController();
            controller.DeleteAccountStage(deleteAccountStage);
            controller.setMain(this);
        
            deleteAccountStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showCongratulations() {
        try {
            FXMLLoader loader = new FXMLLoader();
            bundle = ResourceBundle.getBundle("treasury.view.lang", locale);
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("view/Congratulations.fxml"));
            AnchorPane congratulations = (AnchorPane) loader.load();
            
            Stage congratulationsStage = new Stage();
            congratulationsStage.initModality(Modality.WINDOW_MODAL);
            congratulationsStage.initStyle(StageStyle.UNDECORATED);
            congratulationsStage.initOwner(window);
            Scene scene = new Scene(congratulations);
            congratulationsStage.setScene(scene);
            
            CongratulationsController controller = loader.getController();
            controller.congratulationStage(congratulationsStage);
            controller.setMain(this);
        
            congratulationsStage.showAndWait();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Stage getWindow() {
        return window;
    }
    
    public Model getModel() {
        return mod;
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    public ResourceBundle getBundle() {
        return bundle;
    }
    
    public void setLanguage() {
        if (getDatabase().selectLanguage() == null) {
            locale = Locale.getDefault();
            getDatabase().insertLanguage(locale.getLanguage());
        } else {
            setLocale(new Locale(getDatabase().selectLanguage()));
        }
    }
    
    public String getLanguageString() {
        String lang = locale.getLanguage();
        switch (lang) {
            case "en":
                language = "english";
                break;
            case "pl":
                language = "polski";
                break;    
            default:
                throw new AssertionError();
        }
        return language;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
        int userId = getDatabase().selectUserId(name);
        getDatabase().updateCurrency(currency, userId);
    }
    
    public void setDefaultCurrency() {
        int userId = getDatabase().selectUserId(name);
        if(getDatabase().selectCurrency(userId) == null) {
        String lang = locale.getLanguage();
        switch (lang) {
            case "en":
                currency = "USD";
                break;
            case "pl":
                currency = "PLN";
                break;    
            default:
                throw new AssertionError();
        }
        getDatabase().updateCurrency(currency, userId);
        } 
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public int getGoal() {
        return goal;
    }
    
    public void setGoal(int goal) {
        this.goal = goal;
        int userId = getDatabase().selectUserId(name);
        getDatabase().updateGoal(goal, userId);
        
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPass() {
        return password;
    }
    
    public void setPass(String password) {
        this.password = password;
    }
    
    public String getPasswordHash(String password) {
        String hash = Security.getHash(password.getBytes(), "SHA-224");
        return hash;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Database getDatabase() {
        return database;
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
        int userId = getDatabase().selectUserId(name);
        getDatabase().updateMoney(money, userId);
    }
    
    private void autolog(boolean keeploggedin, String name) {
        autolog = keeploggedin;
        this.name = name;
    }

}

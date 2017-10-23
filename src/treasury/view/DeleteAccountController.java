package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import treasury.Main;
import treasury.util.Utility;

public class DeleteAccountController {
    
    private Stage deleteAccountStage;
    private Main main;

    public void DeleteAccountStage(Stage deleteAccountStage) {
        this.deleteAccountStage = deleteAccountStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    
    @FXML
    public void handleYes() {
        Utility.playClickSound();
        String name = main.getName();
        int userId = main.getDatabase().selectUserId(name);
        main.getDatabase().deleteUser(userId);
        main.getDatabase().deleteLoginfo(1);
        deleteAccountStage.close();
        main.showLogIn();
    }
    
    @FXML
    public void handleNo() {
        Utility.playClickSound();
        deleteAccountStage.close();
    }
    
}

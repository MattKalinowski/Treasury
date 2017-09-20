package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treasury.Main;

public class ChangeNameController {

    private Stage changeNameStage;
    private Main main;
    @FXML
    private TextField textField;

    public void initialize() {
        // TODO
    }    

    public void setChangeNameStage(Stage changeNameStage) {
        this.changeNameStage = changeNameStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void handleAccept() {
        main.setName(textField.getText());
        main.showSettings();
        changeNameStage.close();
    }
    @FXML
    private void handleCancel() {
        changeNameStage.close();
    }
    
}

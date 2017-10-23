package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import treasury.util.Utility;

public class CongratulationsController {

    private Stage congratulationStage;

    public void congratulationStage(Stage congratulationsStage) {
        this.congratulationStage = congratulationsStage;
    }

    @FXML
    private void handleOk() {
        Utility.playClickSound();
        congratulationStage.close();
    }
    
}

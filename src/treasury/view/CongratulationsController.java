package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class CongratulationsController {

    private Stage congratulationStage;

    public void congratulationStage(Stage congratulationsStage) {
        this.congratulationStage = congratulationsStage;
    }

    @FXML
    private void handleOk() {
        playClickSound();
        congratulationStage.close();
    }
    
    private void playClickSound() {
        AudioClip click = new AudioClip(this.getClass().getResource("sounds/click.wav").toString());
        click.play();
    }
    
}

package treasury.view;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import treasury.Main;

public class CongratulationsController {

    private Stage congratulationStage;
    private Main main;

    public void initialize() {
        // TODO
    }    

    public void congratulationStage(Stage congratulationsStage) {
        this.congratulationStage = congratulationsStage;
    }

    public void setMain(Main main) {
        this.main = main;
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

package treasury.util;

import javafx.scene.media.AudioClip;

public final class Utility {
    
    private Utility() {
        
    }
    
    public static void playClickSound() {
        AudioClip click = new AudioClip(Utility.class.getResource("sounds/click.wav").toString());
        click.play();
    }
    
    public static void playDepositSound() {
        AudioClip click = new AudioClip(Utility.class.getResource("sounds/deposit.wav").toString());
        click.play();
    }
    
    public static void playWithdrawalSound() {
        AudioClip click = new AudioClip(Utility.class.getResource("sounds/withdrawal.wav").toString());
        click.play();
    }
    
}
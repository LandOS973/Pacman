package src.view.etatCommand;
import javax.swing.JButton;

public class EtatPlay extends Etat {
    @Override
    public void handleButtons(JButton restartButton, JButton pauseButton, JButton playButton, JButton stepButton) {
        restartButton.setEnabled(true);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
        stepButton.setEnabled(false);   
    }
}

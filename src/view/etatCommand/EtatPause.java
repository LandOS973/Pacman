package src.view.etatCommand;
import javax.swing.JButton;


public class EtatPause extends Etat {

    @Override
    public void handleButtons(JButton restartButton, JButton pauseButton, JButton playButton, JButton stepButton) {
        restartButton.setEnabled(true);
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
        stepButton.setEnabled(true);
    }
    
}

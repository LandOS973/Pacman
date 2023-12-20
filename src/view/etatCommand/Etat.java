package src.view.etatCommand;
import javax.swing.JButton;
import src.view.ViewCommand;

public abstract class Etat {
    protected ViewCommand vc;


    public abstract void handleButtons(JButton restartButton, JButton pauseButton, JButton playButton, JButton stepButton);
}
package src.view;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import src.controller.AbstractController;
import src.modele.Game;
import src.view.etatCommand.Etat;
import src.view.etatCommand.EtatPlay;
import src.view.etatCommand.EtatPause;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewCommand {
    private Etat etat;

    public ViewCommand(Game game,AbstractController controller) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(900, 600));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;

        Icon iconRestart = new ImageIcon("icons/icon_restart.png");
        Icon iconPause = new ImageIcon("icons/icon_pause.png");
        Icon iconPlay = new ImageIcon("icons/icon_run.png");
        Icon iconStep= new ImageIcon("icons/icon_step.png");

        JButton restartButton = new JButton(iconRestart);
        JButton pauseButton = new JButton(iconPause);
        JButton playButton = new JButton(iconPlay);
        JButton stopButton = new JButton(iconStep);

        setEtat(new EtatPlay(), restartButton, pauseButton, playButton, stopButton);

        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        topPanel.add(restartButton);
        topPanel.add(pauseButton);
        topPanel.add(playButton);
        topPanel.add(stopButton);

        JLabel numberOfTurns = new JLabel("Number of turns per second");
        numberOfTurns.setHorizontalAlignment(JLabel.CENTER);

        JSlider slider = new JSlider(1, 10);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue((int) (1000 / game.getTime()));


        JPanel bottomLeftJPanel = new JPanel(new GridLayout(2, 1));
        bottomLeftJPanel.add(numberOfTurns);
        bottomLeftJPanel.add(slider);
        
        JLabel turnLabel = new JLabel("Tour : 0");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        jFrame.add(turnLabel);

        game.addTurnChangeListener(evt -> {
            turnLabel.setText("Tour : " + evt.getNewValue());
        });

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(bottomLeftJPanel);
        bottomPanel.add(turnLabel);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);

        jFrame.add(mainPanel);

        jFrame.setLocation(dx, dy);
        jFrame.setVisible(true);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.restart();
                setEtat(new EtatPlay(), restartButton, pauseButton, playButton, stopButton);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.pause();
                setEtat(new EtatPause(), restartButton, pauseButton, playButton, stopButton);
            }
        });

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.play();
                setEtat(new EtatPlay(), restartButton, pauseButton, playButton, stopButton);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                controller.step();
            }
        });

        slider.addChangeListener(evt -> {
            controller.setSpeed(slider.getValue());
        });
    }

    public void setEtat(Etat etat, JButton restartButton, JButton pauseButton, JButton playButton, JButton stopButton) {
        this.etat = etat;
        this.etat.handleButtons(restartButton, pauseButton, playButton, stopButton);
    }

    public Etat getEtat() {
        return etat;
    }
    
}

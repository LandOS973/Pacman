package src.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import src.modele.Game;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

public class ViewSimpleGame {

    public ViewSimpleGame(Game game) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;

        JLabel turnLabel = new JLabel("Tour : 0");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        jFrame.add(turnLabel);

        game.addTurnChangeListener(evt -> {
            turnLabel.setText("Tour : " + evt.getNewValue());
        });

        jFrame.setLocation(dx, dy);
        jFrame.setVisible(true);
    }
}


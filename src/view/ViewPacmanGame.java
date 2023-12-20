package src.view;

import javax.swing.JFrame;
import src.modele.Maze;
import src.modele.PacmanGame;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class ViewPacmanGame implements KeyListener {
    private PacmanGame game;
    private PanelPacmanGame panel;
    private Maze maze;

    public static int lastKeyPressed = 0;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'z':
                lastKeyPressed = 0; 
                break;
            case 's':
                lastKeyPressed = 1; 
                break;
            case 'q':
                lastKeyPressed = 3; 
                break;
            case 'd':
                lastKeyPressed = 2; 
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    public ViewPacmanGame(PacmanGame game) {
        this.game = game;
        try {
            this.maze = new Maze(game.getMapName());
            this.panel = new PanelPacmanGame(maze);
            panel.setVisible(true);
            JFrame jFrame = new JFrame();
            jFrame.setTitle("Game");
            jFrame.setSize(new Dimension(this.maze.getSizeX() * 30, this.maze.getSizeY() * 30));
            jFrame.add(panel);
            Dimension windowSize = jFrame.getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;
            jFrame.setLocation(dx, dy);
            jFrame.setVisible(true);
            jFrame.setFocusable(true);
            jFrame.addKeyListener(this);
            update();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void update() {
        game.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "moveAgent":
                    panel.setPacmans_pos(game.getPacmanPositions());
                    panel.setGhosts_pos(game.getGhostPositions());
                    break;
                case "eat":
                    panel.setMaze((Maze) evt.getNewValue());
                    break;
                case "ghostsScared":
                    panel.setGhostsScarred((boolean) evt.getNewValue());
                    break;
                default:
                    return;
            }
            panel.repaint();
        });
    }
    
}

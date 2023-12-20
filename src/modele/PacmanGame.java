package src.modele;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;

import src.modele.Agents.Agent;
import src.modele.Agents.AgentAction;
import src.modele.Agents.Pacman;
import src.modele.Agents.Factory.AgentFactory;
import src.modele.Agents.Factory.GhostFactory;
import src.modele.Agents.Factory.PacmanFactory;
import src.modele.Agents.Strategy.AgentStrategy;
import src.modele.Agents.Strategy.RandomStrategy;
import src.modele.Agents.Strategy.Ghost.AStarStrategy;
import src.modele.Agents.Strategy.Ghost.RunAwayStrategy;
import src.modele.Agents.Strategy.Pacman.EatAndRunStrategy;
import src.modele.Agents.Strategy.Pacman.PlayerStrategy;
import javax.swing.JOptionPane;


public class PacmanGame extends Game {

    private Maze maze;
    private ArrayList<Agent> pacmans;
    private ArrayList<Agent> ghosts;
    private String mapName;
    private int timer;
    private boolean win;
    private static int life = 2;

    private static PacmanGame instance;

    private PacmanGame(int maxturn, long time) {
        super(maxturn, time);
        this.win = false;
        this.timer = 0;
        this.pacmans = new ArrayList<>(); 
        this.ghosts = new ArrayList<>();
        this.mapName = askMapName();
        try {
            this.maze = new Maze(mapName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "in init maze on PacmanGame constructor");
        }
        initializeGame();
    }

    public static  synchronized PacmanGame getInstance(int maxturn, long time) {
        if (instance == null) {
            instance = new PacmanGame(maxturn, time);
        }
        return instance;
    }

    public static int getNumberOfLife(){
        return life;
    }

    public AgentStrategy askPacmanStrategy() {
        String[] strategies = {"Player", "EatAndRun", "Random" };
        String chosenStrategy = (String) JOptionPane.showInputDialog(null, 
            "Choisissez la stratégie pour Pacman:", 
            "Sélection de la Stratégie", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            strategies, 
            strategies[0]);
        
        if(chosenStrategy == null){
            chosenStrategy = "Player";
        }

        switch (chosenStrategy) {
            case "EatAndRun":
                return new EatAndRunStrategy(this);
            case "Random":
                return new RandomStrategy(); 
            case "Player":
                return new PlayerStrategy(); 
            default:
                return new PlayerStrategy(); 
        }
    }
    

    public String askMapName(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("./layouts"));
        fileChooser.setDialogTitle("Choisir un layout");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return "./layouts/" + fileChooser.getSelectedFile().getName();
        } else {
            return "./layouts/originalClassic.lay";
        }
    } 

    @Override
    public void initializeGame() {   
        life = 2;
        this.pacmans = new ArrayList<>(); 
        this.ghosts = new ArrayList<>();   
        ArrayList<PositionAgent> pacman_start = maze.getPacman_start();
        ArrayList<PositionAgent> ghosts_start = maze.getGhosts_start();
        AgentFactory pacmanFactory = new PacmanFactory();
        AgentFactory ghostFactory = new GhostFactory();

        AgentStrategy pacmanStrategy = askPacmanStrategy();

        for (PositionAgent pos : pacman_start) {
            this.pacmans.add(pacmanFactory.createAgent(
                new PositionAgent(pos.getX(), pos.getY(), pos.getDir()), 
                pacmanStrategy));
        }

        for (PositionAgent pos : ghosts_start) {
            this.ghosts.add(ghostFactory.createAgent(
                (new PositionAgent(pos.getX(), pos.getY(), pos.getDir())), 
                new AStarStrategy(this)));
        }
    }

    public boolean isLegalMove(int nextX, int nextY) {
        return !maze.isWall(nextX, nextY);
    }
    
    public boolean isAtSamePosition(PositionAgent pos1, PositionAgent pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY();
    }

    public enum ItemType {
        FOOD, CAPSULE, GHOST
    }
    
    public void eatItem(int x, int y, Pacman agent, ItemType itemType) {
        switch (itemType) {
            case FOOD:
                if (maze.isFood(x, y)) {
                    maze.setFood(x, y, false);
                    maze.decreaseFoodCount();
                    PropertyChangeSupport.firePropertyChange("eat", null, maze);
                    if (maze.getRemainingFood() == 0) {
                        this.win = true;
                    }
                }
                break;
            case CAPSULE:
                if (maze.isCapsule(x, y)) {
                    maze.setCapsule(x, y, false);
                    PropertyChangeSupport.firePropertyChange("ghostsScared", false, true);
                    this.timer = 20;
                    PropertyChangeSupport.firePropertyChange("eat", null, maze);
                    for (Agent ghost : ghosts) {
                        ghost.setStrategy(new RunAwayStrategy(this));
                    }
                }
                break;
            case GHOST:
                PositionAgent pacmanPos = agent.getPosition();
                for(Agent ghost : ghosts){
                    if (isAtSamePosition(pacmanPos, ghost.getPosition()) && timer > 0) {
                        ghost.setPosition(maze.getGhosts_start().get(0));
                        PropertyChangeSupport.firePropertyChange("moveAgent", null, ghost.getPosition());
                    }
                }
                break;
        }
    }
    
    public PositionAgent moveAgent(Agent agent) {
        AgentAction action = agent.getAction();
        int x = agent.getPosition().getX() + action.get_vx();
        int y = agent.getPosition().getY() + action.get_vy();
    
        if (isLegalMove(x, y)) {
            agent.getPosition().setX(x);
            agent.getPosition().setY(y);
            
            if (agent instanceof Pacman) {
                Pacman pacman = (Pacman) agent;
                eatItem(x, y, pacman, ItemType.FOOD);
                eatItem(x, y, pacman, ItemType.CAPSULE);
                eatItem(x, y, pacman, ItemType.GHOST);
            }
        }
    
        agent.getPosition().setDir(action.get_direction());
        return new PositionAgent(x, y, action.get_direction());
    }

    public ArrayList<PositionAgent> moveAllAgents(ArrayList<Agent> agents) {
        return agents.stream()
                     .map(agent -> moveAgent(agent))
                     .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<PositionAgent> getPacmanPositions() {
        return pacmans.stream()
                    .map(pacmans -> pacmans.getPosition())
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<PositionAgent> getGhostPositions() {
        return ghosts.stream()
                    .map(ghosts -> ghosts.getPosition())
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean isPacmanCatched(){
        if(life > 0 && areGhostsAndPacmansInSamePosition()){
            life--;
            PropertyChangeSupport.firePropertyChange("moveAgent", null, pacmans);
            for(int i = 0; i < pacmans.size(); i++){
                PositionAgent startPosition = maze.getPacman_start().get(i);
                PositionAgent newPosition = new PositionAgent(startPosition.getX(), startPosition.getY(), startPosition.getDir());
                pacmans.get(i).setPosition(newPosition);
                pacmans.get(i).getStrategy().setPosition(newPosition);
            }
            
            for(int i = 0; i < ghosts.size(); i++){
                PositionAgent startPosition = maze.getGhosts_start().get(i);
                PositionAgent newPosition = new PositionAgent(startPosition.getX(), startPosition.getY(), startPosition.getDir());
                ghosts.get(i).setPosition(newPosition);
                ghosts.get(i).getStrategy().setPosition(newPosition);
            }
            return true;
        }
        return false;
    }

    @Override
    public void takeTurn() {
        if(this.timer > 0){
            timer --;
        }

        if(this.timer == 1){
            PropertyChangeSupport.firePropertyChange("ghostsScared", true, false);
            for (Agent ghost : ghosts) {
                ghost.setStrategy(new AStarStrategy(this));
            }
        }

        ArrayList<PositionAgent> newPacmanPositions = moveAllAgents(pacmans);
        if(!gameOver() && !isPacmanCatched()){
            PropertyChangeSupport.firePropertyChange("moveAgent", null, newPacmanPositions);
        }
        ArrayList<PositionAgent> newGhostPositions = moveAllAgents(ghosts);
        if(!gameOver() && !isPacmanCatched()){
            PropertyChangeSupport.firePropertyChange("moveAgent", null, newGhostPositions);
        }
    }


    @Override
    public boolean gameContinue() {
        return !gameOver() && !win;
    }

    public boolean areGhostsAndPacmansInSamePosition(){
        return getPacmanPositions().stream()
            .anyMatch(pacmanPos -> getGhostPositions().stream()
                .anyMatch(ghostPos -> isAtSamePosition(pacmanPos, ghostPos)));
    }

    @Override
    public boolean gameOver() {
        boolean isGameOver = areGhostsAndPacmansInSamePosition() && this.timer == 0 && life == 0;
        if(isGameOver){
            this.pause();
            JOptionPane.showMessageDialog(null, "Game Over");
            System.exit(0);
        }
        return isGameOver;
    }
    
    public boolean isThereAGhost(int x, int y) {
        ArrayList<Agent> ghostsCopy = new ArrayList<Agent>(ghosts);
        return ghostsCopy.stream()
                         .anyMatch(ghost -> isAtSamePosition(ghost.getPosition(), new PositionAgent(x, y, 0)));
    }
    

    public String getMapName() {
        return mapName;
    }

    public Maze getMaze() {
        return maze;
    }
}

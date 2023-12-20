package src.modele.Agents.Strategy.Pacman;
import src.modele.Agents.AgentAction;
import src.modele.Agents.Strategy.AgentStrategy;

import java.util.ArrayList;

import src.modele.Maze;
import src.modele.PacmanGame;
import src.modele.PositionAgent;

public class EatAndRunStrategy extends AgentStrategy {
    private PacmanGame game;

    public EatAndRunStrategy(PacmanGame game) {
        this.game = game;
    }

    public AgentAction getAction() {
        Maze maze = this.game.getMaze();
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
    
        // Éviter les fantômes si l'un d'eux est à moins de 3 cases de distance
        if (isGhostNearby(2)) {
            return avoidGhosts();
        }
    
        // Manger de la nourriture si elle est à proximité
        AgentAction eatAction = tryToEat(maze, x, y);
        if (eatAction != null) {
            return eatAction;
        }

        int random;
        AgentAction action;
        do {
            random = (int) (Math.random() * 4);
            action = new AgentAction(random);
        } while (maze.isWall(x + action.get_vx(), y + action.get_vy()));
        return new AgentAction(random);
    }
    
    private boolean isGhostNearby(int distance) {
        ArrayList<PositionAgent> ghosts = this.game.getGhostPositions();
        for (PositionAgent ghost : ghosts) {
            int ghostX = ghost.getX();
            int ghostY = ghost.getY();
            if (Math.abs(ghostX - this.getPosition().getX()) <= distance && Math.abs(ghostY - this.getPosition().getX()) <= distance) {
                return true;
            }
        }
        return false;
    }
    
    private AgentAction avoidGhosts() {
        int pacmanX = this.getPosition().getX();
        int pacmanY = this.getPosition().getY();
        ArrayList<PositionAgent> ghosts = this.game.getGhostPositions();
    
        ArrayList<AgentAction> possibleActions = new ArrayList<>();
    
        // Parcourir tous les fantômes et déterminer les actions possibles pour échapper à chacun
        for (PositionAgent ghost : ghosts) {
            int ghostX = ghost.getX();
            int ghostY = ghost.getY();
    
            int diffX = pacmanX - ghostX;
            int diffY = pacmanY - ghostY;
    
            // Ajouter les actions possibles à la liste en tenant compte des murs
            if(diffX > 0 && !this.game.getMaze().isWall(pacmanX+1, pacmanY)) {
                possibleActions.add(new AgentAction(AgentAction.EAST));
            } 
            if(diffX < 0 && !this.game.getMaze().isWall(pacmanX-1, pacmanY)) {
                possibleActions.add(new AgentAction(AgentAction.WEST));
            } 
            if(diffY > 0 && !this.game.getMaze().isWall(pacmanX, pacmanY+1)) {
                possibleActions.add(new AgentAction(AgentAction.SOUTH));
            } 
            if(diffY < 0 && !this.game.getMaze().isWall(pacmanX, pacmanY-1)) {
                possibleActions.add(new AgentAction(AgentAction.NORTH));
            }
        }
    
        // S'il existe des actions possibles, choisissez celle qui maximise la distance de tous les fantômes
        if(!possibleActions.isEmpty()) {
            return possibleActions.get((int) (Math.random() * possibleActions.size()));
        }
    
        // Si aucune action sûre n'est trouvée random
        int random = (int) (Math.random() * 4);
        return new AgentAction(random);
    }
    
    
    private AgentAction tryToEat(Maze maze, int x, int y) {
        if (maze.isFood(x-1, y) || maze.isCapsule(x-1, y)) return new AgentAction(AgentAction.WEST);
        if (maze.isFood(x+1, y) || maze.isCapsule(x+1, y)) return new AgentAction(AgentAction.EAST);
        if (maze.isFood(x, y-1) || maze.isCapsule(x, y-1)) return new AgentAction(AgentAction.NORTH);
        if (maze.isFood(x, y+1) || maze.isCapsule(x, y+1)) return new AgentAction(AgentAction.SOUTH);
        return null;
    }
    
    
}

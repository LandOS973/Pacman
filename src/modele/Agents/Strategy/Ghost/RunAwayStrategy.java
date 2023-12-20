package src.modele.Agents.Strategy.Ghost;
import src.modele.PacmanGame;
import src.modele.PositionAgent;
import src.modele.Agents.AgentAction;
import src.modele.Agents.Strategy.AgentStrategy;

public class RunAwayStrategy extends AgentStrategy {
    private PacmanGame game;

    public RunAwayStrategy(PacmanGame game) {
        this.game = game;
    }

    public AgentAction getAction() {
        PositionAgent ghostPosition = this.getPosition();
        PositionAgent pacmanPosition = this.game.getPacmanPositions().get(0);
        double maxDistance = -1;
        AgentAction bestAction = null;

        for (int i = 0; i < 4; i++) {
            AgentAction action = new AgentAction(i);
            PositionAgent newPosition = new PositionAgent(ghostPosition.getX() + action.get_vx(), ghostPosition.getY() + action.get_vy(), action.get_direction());
            if (!game.getMaze().isWall(newPosition.getX(), newPosition.getY())) {
                double distance = distanceFrom(newPosition,pacmanPosition);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    bestAction = action;
                }
            }
        }

        return bestAction != null ? bestAction : new AgentAction(AgentAction.STOP);
    }

    private double distanceFrom(PositionAgent position, PositionAgent pacmanPosition) {
        return Math.abs(position.getX() - pacmanPosition.getX()) + Math.abs(position.getY() - pacmanPosition.getY());
    }
}

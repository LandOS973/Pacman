package src.modele.Agents.Strategy.Pacman;
import src.modele.Agents.AgentAction;
import src.modele.Agents.Strategy.AgentStrategy;
import src.view.ViewPacmanGame;

public class PlayerStrategy extends AgentStrategy {
    @Override
    public AgentAction getAction() {
        int action = ViewPacmanGame.lastKeyPressed;
        if (action != -1) {
            return new AgentAction(action);
        } else {
            return new AgentAction(0); // up
        }
    }
}

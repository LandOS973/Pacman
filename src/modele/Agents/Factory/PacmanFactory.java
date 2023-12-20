package src.modele.Agents.Factory;
import src.modele.Agents.Strategy.AgentStrategy;
import src.modele.Agents.Agent;
import src.modele.Agents.Pacman;
import src.modele.PositionAgent;

public class PacmanFactory implements AgentFactory {
    public Agent createAgent(PositionAgent p, AgentStrategy strategy) {
        return new Pacman(p, strategy);
    }
}

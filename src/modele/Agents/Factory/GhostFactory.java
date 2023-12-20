package src.modele.Agents.Factory;
import src.modele.PositionAgent;
import src.modele.Agents.Agent;
import src.modele.Agents.Ghost;
import src.modele.Agents.Strategy.AgentStrategy;

public class GhostFactory implements AgentFactory {
    public Agent createAgent(PositionAgent p, AgentStrategy strategy) {
        return new Ghost(p, strategy);
    }
}

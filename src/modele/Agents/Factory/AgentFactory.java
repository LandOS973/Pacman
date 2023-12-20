package src.modele.Agents.Factory;
import src.modele.Agents.Agent;
import src.modele.PositionAgent;
import src.modele.Agents.Strategy.AgentStrategy;

public interface AgentFactory {
    public Agent createAgent(PositionAgent p, AgentStrategy strategy);
}

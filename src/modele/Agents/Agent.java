package src.modele.Agents;

import src.modele.PositionAgent;
import src.modele.Agents.Strategy.AgentStrategy;

public abstract class Agent {
    private PositionAgent p;
    private AgentStrategy strategy;

    public Agent(PositionAgent p, AgentStrategy strategy) {
        this.p = p;
        this.strategy = strategy;
        this.strategy.setPosition(p);
    }

    public AgentAction getAction(){
        return strategy.getAction();
    }

    // getter
    public PositionAgent getPosition() {
        return p;
    }

    public AgentStrategy getStrategy() {
        return strategy;
    }

    // setter
    public void setPosition(PositionAgent p) {
        this.p = p;
    }

    public void setStrategy(AgentStrategy strategy) {
        this.strategy = strategy;
        this.strategy.setPosition(p);
    }
}

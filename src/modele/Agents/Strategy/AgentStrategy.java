package src.modele.Agents.Strategy;
import src.modele.PositionAgent;
import src.modele.Agents.AgentAction;

public abstract class AgentStrategy {
    private PositionAgent p;

    public abstract AgentAction getAction();
    
    // getter
    public PositionAgent getPosition() {
        return p;
    }

    // setter
    public void setPosition(PositionAgent p) {
        this.p = p;
    }
}

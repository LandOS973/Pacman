package src.modele.Agents.Strategy;
import src.modele.Agents.AgentAction;

public class RandomStrategy extends AgentStrategy {
    public AgentAction getAction() {
        int random = (int) (Math.random() * 4);
        return new AgentAction(random);
    }
}

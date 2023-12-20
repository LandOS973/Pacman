package src.modele.Agents.Strategy.Ghost;

import src.modele.Agents.AgentAction;
import src.modele.Agents.Strategy.AgentStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import src.modele.PacmanGame;
import src.modele.PositionAgent;

public class AStarStrategy extends AgentStrategy {
    private PacmanGame game;

    public AStarStrategy(PacmanGame game) {
        this.game = game;
    }

    private int heuristique(PositionAgent position, PositionAgent pacmanPosition) {
        return Math.abs(position.getX() - pacmanPosition.getX()) + Math.abs(position.getY() - pacmanPosition.getY());
    }

    private AgentAction cheminLePlusCourt(PositionAgent ghostPosition, PositionAgent pacmanPosition) {
        Map<PositionAgent, node> openList = new HashMap<>();
        Set<PositionAgent> closedList = new HashSet<>();
        PriorityQueue<node> queue = new PriorityQueue<>();

        node startNode = new node(ghostPosition, null, 0, heuristique(ghostPosition, pacmanPosition));
        queue.add(startNode);
        openList.put(ghostPosition, startNode);

        while (!queue.isEmpty()) {
            node currentNode = queue.poll();
            PositionAgent currentPosition = currentNode.position;
        
            if (currentPosition.equals(pacmanPosition)) {
                return retracerChemin(currentNode);
            }
        
            closedList.add(currentPosition);
        
            for (PositionAgent neighbor : getVoisins(currentPosition)) {
                if (closedList.contains(neighbor)){
                    continue;
                } 
        
                int costToNeighbor = currentNode.cout + 1;
                node existingNode = openList.get(neighbor);
        
                if (existingNode == null || costToNeighbor < existingNode.cout) {
                    node neighborNode = new node(neighbor, currentNode, costToNeighbor, heuristique(neighbor, pacmanPosition));
                    queue.add(neighborNode);
                    openList.put(neighbor, neighborNode);
                }
            }
        }

        // Si aucun chemin n'a été trouvé, retournez une action par défaut
        int random = (int) (Math.random() * 4);
        return new AgentAction(random);
    }


private AgentAction retracerChemin(node endNode) {
    node current = endNode;
    node parent = endNode.parent;

    if(parent == null){
        int random = (int) (Math.random() * 4);
        return new AgentAction(random);
    }
    while (parent != null && parent.parent != null) {
        current = parent;
        parent = parent.parent;
    }
    // Calculez l'action à partir de la position parent à la position courante
    return calculerAction(parent.position, current.position);
}


private List<PositionAgent> getVoisins(PositionAgent position) {
    List<PositionAgent> voisins = new ArrayList<>();
    int x = position.getX();
    int y = position.getY();

    // Vérifiez chaque direction et ajoutez si le mouvement est valide (pas de mur)
    if (!game.getMaze().isWall(x+1, y) && !game.isThereAGhost(x+1, y)) voisins.add(new PositionAgent(x+1, y, AgentAction.EAST));
    if (!game.getMaze().isWall(x-1, y) && !game.isThereAGhost(x-1, y)) voisins.add(new PositionAgent(x-1, y, AgentAction.WEST));
    if (!game.getMaze().isWall(x, y+1) && !game.isThereAGhost(x, y+1)) voisins.add(new PositionAgent(x, y+1, AgentAction.SOUTH));
    if (!game.getMaze().isWall(x, y-1) && !game.isThereAGhost(x, y-1)) voisins.add(new PositionAgent(x, y-1, AgentAction.NORTH));

    return voisins;
}

private AgentAction calculerAction(PositionAgent from, PositionAgent to) {
    int diffX = to.getX() - from.getX();
    int diffY = to.getY() - from.getY();

    if (diffX == 1) return new AgentAction(AgentAction.EAST);
    if (diffX == -1) return new AgentAction(AgentAction.WEST);
    if (diffY == 1) return new AgentAction(AgentAction.SOUTH);
    if (diffY == -1) return new AgentAction(AgentAction.NORTH);

    return new AgentAction(AgentAction.STOP); // ou une autre action par défaut
}

    @Override
    public AgentAction getAction() {
        PositionAgent ghostPosition = this.getPosition();
        PositionAgent pacmanPosition = this.game.getPacmanPositions().get(0);

        return cheminLePlusCourt(ghostPosition, pacmanPosition);
    }

    class node implements Comparable<node> {
        PositionAgent position;
        node parent;
        int cout;
        int heuristique;
        node(PositionAgent position, node parent, int cout, int heuristique) {
            this.position = position;
            this.parent = parent;
            this.cout = cout;
            this.heuristique = heuristique;
        }

        @Override
        public int compareTo(node o) {
            return (this.cout + this.heuristique) - (o.cout + o.heuristique);
        }
    }
}

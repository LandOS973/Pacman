import src.controller.ControllerPacmanGame;
import src.modele.PacmanGame;



public class Main {
    public static void main(String[] args) {
        PacmanGame game = PacmanGame.getInstance(10000, 200);
        new ControllerPacmanGame(game);
    }
}

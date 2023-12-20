package src.controller;

import src.modele.PacmanGame;
import src.view.ViewCommand;
import src.view.ViewPacmanGame;

public class ControllerPacmanGame extends AbstractController{

    public ControllerPacmanGame(PacmanGame game) {
        super(game);
        new ViewPacmanGame(game);
        new ViewCommand(game, this);
        game.launch();
    }   
}
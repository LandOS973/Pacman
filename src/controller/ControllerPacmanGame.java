package src.controller;

import src.modele.PacmanGame;
import src.view.ViewCommand;
import src.view.ViewPacmanGame;

public class ControllerPacmanGame extends AbstractController{

    public ControllerPacmanGame(PacmanGame game) {
        super(game);
        new ViewCommand(game, this);
        new ViewPacmanGame(game);
        game.launch();
    }   
}
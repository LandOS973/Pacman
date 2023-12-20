package src.controller;

import src.modele.Game;
import src.view.ViewCommand;

public class ControllerCommand extends AbstractController {
    public ControllerCommand(Game game) {
        super(game);
        new ViewCommand(game,this);
        game.launch();
    }
    
}

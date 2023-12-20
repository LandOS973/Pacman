package src.controller;

import src.modele.SimpleGame;
import src.view.ViewCommand;

public class ControllerSimpleGame extends AbstractController {
    public ControllerSimpleGame(SimpleGame game) {
        super(game);
        new ViewCommand(game,this);
        game.launch();
    }
}

package src.controller;

import src.modele.Game;

public class AbstractController {
    private Game game;

    public AbstractController(Game game) {
        this.game = game;
    }

    public void restart() {
        game.init();
        game.launch();
    }

    public void step() {
        game.step();
    }

    public void play() {
        game.launch();
    }

    public void pause() {
        game.pause();
    }

    public void setSpeed(double speed) {
        game.setTime((long) (1000 / speed));
    }
}

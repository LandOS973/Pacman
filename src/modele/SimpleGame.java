package src.modele;
/*
 * SimpleGame.java
 * A simple game to test the Game class
 */

public class SimpleGame extends Game {
    private static SimpleGame instance = null;

    private SimpleGame(int maxturn, long time) {
        super(maxturn, time);
        initializeGame();
    }

    public static synchronized SimpleGame getInstance(int maxturn, long time) {
        if (instance == null) {
            instance = new SimpleGame(maxturn, time);
        }
        return instance;
    }

    @Override
    public void initializeGame() {
        System.out.println("Initialisation du jeu");
    }

    @Override
    public void takeTurn() {
        System.out.println("Current turn " + getTurn());
    }

    @Override
    public boolean gameContinue() {
        return true;
    }

    @Override
    public boolean gameOver() {
        System.out.println("Fin du jeu");
        return true;
    }
}
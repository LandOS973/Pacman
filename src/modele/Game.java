package src.modele;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/*
 * Game.java
 * Abstract class for a game
 */
public abstract class Game implements Runnable {
    private int turn;
    private int maxturn;
    private boolean isRunning;
    private Thread thread;
    private long time;
    protected PropertyChangeSupport PropertyChangeSupport; // api observer

    public abstract void initializeGame();
    public abstract boolean gameContinue();
    public abstract void takeTurn();
    public abstract boolean gameOver();

    public Game(int maxturn, long time) {
        this.maxturn = maxturn;
        if(time < 0 || time > 1000) {
            this.time = 1000;
        }else {
            this.time = time;
        }
        this.PropertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addTurnChangeListener(PropertyChangeListener listener) {
        PropertyChangeSupport.addPropertyChangeListener("turn", listener);
    }

    public void removeTurnChangeListener(PropertyChangeListener listener) {
        PropertyChangeSupport.removePropertyChangeListener("turn", listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PropertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void stopGame() {
        isRunning = false;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(); // Attendre que le thread se termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void launch() {
        if (isRunning) {
            stopGame(); // Arrête le thread en cours d'exécution avant d'en démarrer un nouveau
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void init() {
        turn = 0;
        setIsRunning(isRunning = true);
        initializeGame();
    }

    public void step() {
        if (!gameOver() && gameContinue() && turn < maxturn) {
            int oldTurn = this.turn;
            turn++;
            PropertyChangeSupport.firePropertyChange("turn", oldTurn, turn);
            takeTurn();
        } else {
            isRunning = false;
            gameOver();
        }
    }

    public void pause() {
        isRunning = false;
    }

    // long time is in milliseconds
    public void run() {
        while (isRunning) {
            step();
            try {
                Thread.sleep(this.getTime());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // GETTER AND SETTER 
    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMaxturn() {
        return this.maxturn;
    }

    public void setMaxturn(int maxturn) {
        this.maxturn = maxturn;
    }

    public boolean isIsRunning() {
        return this.isRunning;
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}


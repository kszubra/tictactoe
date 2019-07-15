package tictactoe.mechanics;

import java.io.Serializable;

public class ScoreKeeper implements Serializable, Comparable {

    private static final long serialVersionUID = 6490758134151908748L;
    private int wonByPlayer;
    private int lostByPlayer;
    private double winLoseRatio;

    public ScoreKeeper() {
        this.wonByPlayer = 0;
        this.lostByPlayer = 0;
        this.winLoseRatio = 0.0;
    }

    int getWonByPlayer() {
        return wonByPlayer;
    }

    public void addWonByPlayer() {
        wonByPlayer++;

        if(lostByPlayer == 0) {
            winLoseRatio = (double) wonByPlayer;
        } else {
            winLoseRatio = (double)wonByPlayer / (double)lostByPlayer;
        }
    }

    int getLostByPlayer() {
        return lostByPlayer;
    }

    public void addLostByPlayer() {
        lostByPlayer++;
        winLoseRatio = (double)wonByPlayer / (double)lostByPlayer;
    }

    double getWinLoseRatio() {
        return Math.round(winLoseRatio *100.0) / 100.0;
    }


    @Override
    public int compareTo(Object o) {
        ScoreKeeper a = (ScoreKeeper)o;
        if (this.winLoseRatio > a.getWinLoseRatio()) {return -1;}
        if (this.winLoseRatio < a.getWinLoseRatio()) {return 1;}
        else return 0;
    }

    @Override
    public String toString() {
        return "W/L: " + getWinLoseRatio() + " (" + wonByPlayer + " won to " + lostByPlayer + " lost) \r\n";
    }
}

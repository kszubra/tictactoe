package tictactoe.mechanics;

import tictactoe.enumerics.CellStatus;
import tictactoe.enumerics.GameMode;

import java.io.Serializable;

public class GameCheckpoint implements Serializable {

    private static final long serialVersionUID = 7829931793828920005L;
    private String playerName;
    private CellStatus[][] gameMatrix;
    private boolean isPlayersTurn;
    private GameMode gameMode;

    GameCheckpoint(String playerName, CellStatus[][] gameMatrix, boolean isPlayersTurn, GameMode gameMode) {
        this.playerName = playerName;
        this.gameMatrix = gameMatrix;
        this.isPlayersTurn = isPlayersTurn;
        this.gameMode = gameMode;
    }

    GameMode getGameMode() {
        return gameMode;
    }

    String getPlayerName() {
        return playerName;
    }

    CellStatus[][] getGameMatrix() {
        return gameMatrix;
    }

    boolean isPlayersTurn() {
        return isPlayersTurn;
    }
}

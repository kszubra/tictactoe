package tictactoe.mechanics;

import tictactoe.enumerics.GameMode;

public class InitialGameData {

    private String playerName;
    private GameMode gameMode;

    public InitialGameData(String playerName, GameMode gameMode) {
        this.playerName = playerName;
        this.gameMode = gameMode;
    }

    String getPlayerName() {
        return playerName;
    }

    GameMode getGameMode() {
        return gameMode;
    }
}

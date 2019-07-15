package tictactoe.mechanics;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.enumerics.CellStatus;
import tictactoe.enumerics.GameMode;

import static tictactoe.enumerics.CellStatus.CIRCLE;
import static tictactoe.enumerics.CellStatus.CROSS;
import static tictactoe.enumerics.CellStatus.EMPTY;

public class GameCheckpointTestSuite {

    private Game testGame;


    @Test
    public void testMakeAndGetCheckpoint(){
        //Given
        testGame = new Game(new InitialGameData("testName", GameMode.RANDOM));
        testGame.setHumanTurn(false);
        testGame.setGameMatrixValue(0,0, CROSS);
        testGame.setGameMatrixValue(2,2, CIRCLE);
        testGame.setGameMatrixValue(1,1, CROSS);
        testGame.makeCheckpoint();


        //When
        String expectedName = "testName";
        String realName = testGame.getCheckpoint().getPlayerName();

        boolean expectedHumanTurn = false;
        boolean realHumanTurn = testGame.getCheckpoint().isPlayersTurn();

        CellStatus[][] expectedMatrix = {
                {CROSS, EMPTY, EMPTY},
                {EMPTY, CROSS, EMPTY},
                {EMPTY, EMPTY, CIRCLE}
        };
        CellStatus[][] realMatrix = testGame.getCheckpoint().getGameMatrix();

        //Then
        Assert.assertTrue(expectedName.equals(realName));
        Assert.assertEquals(realHumanTurn, expectedHumanTurn);
        Assert.assertArrayEquals(expectedMatrix, realMatrix);
    }

    @Test
    public void testSetGameFromCheckpoint() {
        //Given
        testGame = new Game(new InitialGameData("John Rambo", GameMode.RANDOM));
        testGame.setHumanTurn(false);
        testGame.setGameMatrixValue(0,0, CROSS);
        testGame.setGameMatrixValue(2,2, CIRCLE);
        testGame.setGameMatrixValue(1,1, CROSS);
        testGame.makeCheckpoint();

        GameCheckpoint testCheckpoint = testGame.getCheckpoint();

        Game gameToSetFromCheckpoint = new Game(new InitialGameData("Terminator", GameMode.STRATEGIC));

        gameToSetFromCheckpoint.setGameFromCheckpoint(testCheckpoint);

        //When
        String expectedName = "John Rambo";
        String realName = gameToSetFromCheckpoint.getHumanPlayerName();

        boolean expectedHumanTurn = false;
        boolean realHumanTurn = gameToSetFromCheckpoint.getHumanTurn();

        CellStatus[][] expectedMatrix = {
                {CROSS, EMPTY, EMPTY},
                {EMPTY, CROSS, EMPTY},
                {EMPTY, EMPTY, CIRCLE}
        };
        CellStatus[][] realMatrix = gameToSetFromCheckpoint.getGameMatrix();

        GameMode expectedGameMode = GameMode.RANDOM;
        GameMode realGameMode = gameToSetFromCheckpoint.getGameMode();

        //Then
        Assert.assertTrue(expectedName.equals(realName));
        Assert.assertEquals(expectedHumanTurn, realHumanTurn);
        Assert.assertArrayEquals(expectedMatrix, realMatrix);
        Assert.assertEquals(expectedGameMode, realGameMode);
    }
}

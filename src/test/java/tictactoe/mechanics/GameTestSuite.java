package tictactoe.mechanics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tictactoe.enumerics.CellStatus;
import tictactoe.enumerics.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tictactoe.enumerics.CellStatus.CROSS;
import static tictactoe.enumerics.CellStatus.EMPTY;


public class GameTestSuite {

    Game testGame;

    @Before
    public void initializeGame(){
        testGame = new Game(new InitialGameData("testName", GameMode.RANDOM));
    }


    @Test
    public void testGameMatrixCreation(){
        //Given
        CellStatus[][] testMatrix = testGame.getGameMatrix();

        //When
        CellStatus expectedStatus = EMPTY;

        CellStatus realStatus00 = testMatrix[0][0];
        CellStatus realStatus01 = testMatrix[0][1];
        CellStatus realStatus02 = testMatrix[0][2];
        CellStatus realStatus10 = testMatrix[1][0];
        CellStatus realStatus11 = testMatrix[1][1];
        CellStatus realStatus12 = testMatrix[1][2];
        CellStatus realStatus20 = testMatrix[2][0];
        CellStatus realStatus21 = testMatrix[2][1];
        CellStatus realStatus22 = testMatrix[2][2];

        //Then
        Assert.assertEquals(expectedStatus, realStatus00);
        Assert.assertEquals(expectedStatus, realStatus01);
        Assert.assertEquals(expectedStatus, realStatus02);
        Assert.assertEquals(expectedStatus, realStatus10);
        Assert.assertEquals(expectedStatus, realStatus11);
        Assert.assertEquals(expectedStatus, realStatus12);
        Assert.assertEquals(expectedStatus, realStatus20);
        Assert.assertEquals(expectedStatus, realStatus21);
        Assert.assertEquals(expectedStatus, realStatus22);

    }

    @Test
    public void testMakeRandomComputerMove(){
        //Given
        testGame.makeComputerMove();

        //When
        boolean expectedCircle = true;
        List<CellStatus> matrixElements = Arrays.stream(testGame.getGameMatrix())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        boolean realCircle = matrixElements.contains(CellStatus.CIRCLE);

        //Then
        Assert.assertEquals(expectedCircle, realCircle);

    }

    @Test
    public void testPlayRandomCornerWithFreeCorner(){
        //Given
        testGame.setGameMatrixValue(0,0, CROSS); //top left corner
        testGame.setGameMatrixValue(0, 2, CROSS); //top right corner
        testGame.setGameMatrixValue(2, 0, CROSS); // bottom left corner

        //When
        CellStatus expectedResult = CellStatus.CIRCLE;
        testGame.playRandomCorner();
        CellStatus testMatrix[][] = testGame.getGameMatrix();
        CellStatus realResult = testMatrix[2][2];

        //Then
        Assert.assertEquals(expectedResult, realResult);
    }

    @Test
    public void testPlayRandomCornerWithoutFreeCorner(){
        //Given
        testGame.setGameMatrixValue(0,0, CROSS); //top left corner
        testGame.setGameMatrixValue(0, 2, CROSS); //top right corner
        testGame.setGameMatrixValue(2, 0, CROSS); // bottom left corner
        testGame.setGameMatrixValue(2,2, CROSS); //bottom right corner

        //When
        CellStatus expectedResult = CROSS;
        testGame.playRandomCorner();
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[0][0]);
        Assert.assertEquals(expectedResult, testMatrix[0][2]);
        Assert.assertEquals(expectedResult, testMatrix[2][0]);
        Assert.assertEquals(expectedResult, testMatrix[2][2]);
    }

    @Test
    public void testPlayOppositeCorner1() {
        //When
        CellStatus expectedResult = CellStatus.CIRCLE;
        testGame.playOppositeCornerTo(0,0);
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[2][2]);
    }

    @Test
    public void testPlayOppositeCorner2() {
        //When
        CellStatus expectedResult = CellStatus.CIRCLE;
        testGame.playOppositeCornerTo(2,2);
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[0][0]);
    }

    @Test
    public void testPlayOppositeCorner3() {
        //When
        CellStatus expectedResult = CellStatus.CIRCLE;
        testGame.playOppositeCornerTo(2,0);
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[0][2]);
    }

    @Test
    public void testPlayOppositeCorner4() {
        //When
        CellStatus expectedResult = CellStatus.CIRCLE;
        testGame.playOppositeCornerTo(0,2);
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[2][0]);
    }

    @Test
    public void testPlayOppositeCornerAllTaken() {
        //Given
        testGame.setGameMatrixValue(0,0, CROSS); //top left corner
        testGame.setGameMatrixValue(0, 2, CROSS); //top right corner
        testGame.setGameMatrixValue(2, 0, CROSS); // bottom left corner
        testGame.setGameMatrixValue(2,2, CROSS); //bottom right corner

        //When
        CellStatus expectedResult = CROSS;
        testGame.playOppositeCornerTo(0,0);
        testGame.playOppositeCornerTo(0,2);
        testGame.playOppositeCornerTo(2,0);
        testGame.playOppositeCornerTo(2,2);
        CellStatus testMatrix[][] = testGame.getGameMatrix();

        //Then
        Assert.assertEquals(expectedResult, testMatrix[0][0]);
        Assert.assertEquals(expectedResult, testMatrix[0][2]);
        Assert.assertEquals(expectedResult, testMatrix[2][0]);
        Assert.assertEquals(expectedResult, testMatrix[2][2]);

    }


    @Test
    public void testMakingOpeningStrategicMove(){
        //Given
        testGame = new Game(new InitialGameData("testName", GameMode.STRATEGIC));
        testGame.setHumanTurn(false);

        //When
        List<String> acceptableCorners = Arrays.asList("00", "20", "02", "22");
        testGame.makeComputerMove();
        String chosenCorener = "" + testGame.getComputerChoiceRow() + testGame.getComputerChoiceColumn();

        //Then
        Assert.assertTrue(acceptableCorners.contains(chosenCorener));

    }

    @Test
    public void testResetGame(){
        //Given
        testGame = new Game(new InitialGameData("testName", GameMode.RANDOM));
        testGame.setHumanTurn(false);
        testGame.makeComputerMove();
        testGame.makeComputerMove();
        testGame.makeComputerMove();
        testGame.setWinner(CROSS);
        testGame.resetGame();

        //When
        CellStatus expectedWinner = EMPTY;
        CellStatus realWinner = testGame.getWinner();

        CellStatus[][] expectedMatrix = {
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };
        CellStatus[][] realMatrix = testGame.getGameMatrix();

        //Then
        Assert.assertTrue(expectedWinner.equals(realWinner));
        Assert.assertEquals(expectedMatrix, realMatrix);

    }

}

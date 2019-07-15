package tictactoe.mechanics;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.enumerics.CellStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tictactoe.enumerics.CellStatus.CIRCLE;
import static tictactoe.enumerics.CellStatus.CROSS;
import static tictactoe.enumerics.CellStatus.EMPTY;

public class RulesTestSuite {

    @Test
    public void testCheckGameMatrixForWinner(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CROSS, CROSS},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        //When
        CellStatus expectedResult = CROSS;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner2(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CIRCLE, CROSS},
                {EMPTY, CROSS, EMPTY},
                {EMPTY, EMPTY, CROSS}
        };

        //When
        CellStatus expectedResult = CROSS;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner3(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CIRCLE, CIRCLE},
                {EMPTY, CROSS, CIRCLE},
                {EMPTY, EMPTY,CIRCLE}
        };

        //When
        CellStatus expectedResult = CIRCLE;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner4(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CIRCLE, CROSS},
                {EMPTY, CROSS, CIRCLE},
                {EMPTY, EMPTY,CIRCLE}
        };

        //When
        CellStatus expectedResult = EMPTY;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner5(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CIRCLE},
                {CIRCLE, CROSS, CROSS},
                {CIRCLE, CIRCLE,CROSS}
        };

        //When
        CellStatus expectedResult = CIRCLE;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner6(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, EMPTY, CROSS},
                {CIRCLE, EMPTY, CROSS},
                {EMPTY, EMPTY,CROSS}
        };

        //When
        CellStatus expectedResult = CROSS;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner7(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, EMPTY, CROSS},
                {EMPTY, EMPTY, EMPTY},
                {CROSS, CROSS,CROSS}
        };

        //When
        CellStatus expectedResult = CROSS;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner8(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CIRCLE, CROSS},
                {CIRCLE, CIRCLE, CIRCLE},
                {CIRCLE, CROSS,CROSS}
        };

        //When
        CellStatus expectedResult = CIRCLE;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner9(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CROSS},
                {CROSS, CIRCLE, CROSS},
                {CIRCLE, CIRCLE,CIRCLE}
        };

        //When
        CellStatus expectedResult = CIRCLE;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner10(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CROSS},
                {CROSS, CIRCLE, CIRCLE},
                {CIRCLE, CIRCLE,CROSS}
        };

        //When
        CellStatus expectedResult = EMPTY;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCheckGameMatrixForWinner11(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CROSS},
                {CROSS, CIRCLE, CIRCLE},
                {CIRCLE, CIRCLE, CROSS}
        };

        //When
        CellStatus expectedResult = EMPTY;

        //Then
        Assert.assertEquals(expectedResult, Rules.checkGameMatrixForWinner(testMatrix));

    }

    @Test
    public void testCreatingPlayableLines(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CIRCLE},
                {CIRCLE, CROSS, CROSS},
                {CIRCLE, CIRCLE, CROSS}
        };

        //When
        Map<String, List<CellStatus>> expectedMap = new HashMap<>();
        expectedMap.put("R0", Arrays.asList(CIRCLE, CROSS, CIRCLE));
        expectedMap.put("R1", Arrays.asList(CIRCLE, CROSS, CROSS));
        expectedMap.put("R2", Arrays.asList(CIRCLE, CIRCLE, CROSS));
        expectedMap.put("C0", Arrays.asList(CIRCLE, CIRCLE, CIRCLE));
        expectedMap.put("C1", Arrays.asList(CROSS, CROSS, CIRCLE));
        expectedMap.put("C2", Arrays.asList(CIRCLE, CROSS, CROSS));
        expectedMap.put("D0", Arrays.asList(CIRCLE, CROSS, CROSS));
        expectedMap.put("D2", Arrays.asList(CIRCLE, CROSS, CIRCLE));

        Map<String, List<CellStatus> > realMap = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //Then
        Assert.assertEquals(expectedMap, realMap);

    }

    @Test
    public void testIfMatrixUpdatesMap() {
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, CROSS, CIRCLE},
                {CIRCLE, CROSS, CROSS},
                {CIRCLE, CIRCLE, CROSS}
        };
        Map<String, List<CellStatus> > realMap = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //changing value in matrix
        testMatrix[2][2] = EMPTY;

        Assert.assertTrue(realMap.get("R2").get(2).equals(EMPTY));

    }

    @Test
    public void testGiveLineInDanger(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CROSS, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        //When
        Map<String, List<CellStatus> > expectedMap = new HashMap<>();
        expectedMap.put("R0", Arrays.asList(CROSS, CROSS, EMPTY));

        Map<String, List<CellStatus> > realMap = Rules.giveLineInDanger(Rules.makeMapOfLineCoordinatesInMatrix(testMatrix));

        //Then
        Assert.assertEquals(expectedMap, realMap);
    }


    @Test
    public void testTellWhatCellToBlock(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CROSS, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R0C2";
        String realResult = Rules.tellCellToBlock(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testTellWhatCellToBlock2(){
        //Given
        CellStatus[][] testMatrix = {
                {EMPTY, EMPTY, CROSS},
                {EMPTY, CROSS, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R2C0";
        String realResult = Rules.tellCellToBlock(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testTellWhatCellToBlock3(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CROSS, EMPTY},
                {EMPTY,CROSS, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R2C2";
        String realResult = Rules.tellCellToBlock(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testGiveLineWithChance(){
        //Given
        CellStatus[][] testMatrix = {
                {EMPTY, EMPTY, EMPTY},
                {CIRCLE, CIRCLE, EMPTY},
                {EMPTY, EMPTY, EMPTY}
        };

        //When
        Map<String, List<CellStatus> > expectedMap = new HashMap<>();
        expectedMap.put("R1", Arrays.asList(CIRCLE, CIRCLE, EMPTY));

        Map<String, List<CellStatus> > realMap = Rules.giveLineWithChance(Rules.makeMapOfLineCoordinatesInMatrix(testMatrix));

        //Then
        Assert.assertEquals(expectedMap, realMap);
    }

    @Test
    public void testGiveLineWithChance2(){
        //Given
        CellStatus[][] testMatrix = {
                {EMPTY, EMPTY, CIRCLE},
                {EMPTY, EMPTY, EMPTY},
                {CIRCLE, EMPTY, EMPTY}
        };

        //When
        Map<String, List<CellStatus> > expectedMap = new HashMap<>();
        expectedMap.put("D2", Arrays.asList(CIRCLE, EMPTY, CIRCLE));

        Map<String, List<CellStatus> > realMap = Rules.giveLineWithChance(Rules.makeMapOfLineCoordinatesInMatrix(testMatrix));

        //Then
        Assert.assertEquals(expectedMap, realMap);
    }

    @Test
    public void testTellWhatCellToWin(){
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {CIRCLE, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R1C0";
        String realResult = Rules.tellCellToWin(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testTellWhatCellToWin2(){
        //Given
        CellStatus[][] testMatrix = {
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, CIRCLE, EMPTY},
                {CIRCLE, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R0C2";
        String realResult = Rules.tellCellToWin(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testTellWhatCellToWin3(){
        //Given
        CellStatus[][] testMatrix = {
                {EMPTY, EMPTY, CIRCLE},
                {EMPTY, EMPTY, CIRCLE},
                {EMPTY, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R2C2";
        String realResult = Rules.tellCellToWin(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testTellWhatCellToWin4(){
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CIRCLE, CROSS},
                {EMPTY, CIRCLE, CROSS},
                {EMPTY, EMPTY, EMPTY}
        };

        Map<String, List<CellStatus> > map = Rules.makeMapOfLineCoordinatesInMatrix(testMatrix);

        //When
        String expectedResult = "R2C1";
        String realResult = Rules.tellCellToWin(map);

        //Then
        Assert.assertEquals(expectedResult, realResult);

    }

    @Test
    public void testGiveLineToContinue() {
        //Given
        CellStatus[][] testMatrix = {
                {CIRCLE, EMPTY, EMPTY},
                {CROSS, CROSS, EMPTY},
                {EMPTY, EMPTY, CROSS}
        };
        Map<String, List<CellStatus> > expectedMap = new HashMap<>();

        //When
        expectedMap.put("R0", Arrays.asList(CIRCLE, EMPTY, EMPTY));
        Map<String, List<CellStatus> > realMap = Rules.giveLineToContinue(Rules.makeMapOfLineCoordinatesInMatrix(testMatrix));

        //Then
        Assert.assertEquals(expectedMap, realMap);

    }

    @Test
    public void testGiveLineToContinue2() {
        //Given
        CellStatus[][] testMatrix = {
                {CROSS, CROSS, EMPTY},
                {EMPTY, CIRCLE, CROSS},
                {EMPTY, EMPTY,CROSS}
        };
        Map<String, List<CellStatus> > expectedMap = new HashMap<>();

        //When
        expectedMap.put("D2", Arrays.asList(EMPTY, CIRCLE, EMPTY));
        Map<String, List<CellStatus> > realMap = Rules.giveLineToContinue(Rules.makeMapOfLineCoordinatesInMatrix(testMatrix));

        //Then
        Assert.assertEquals(expectedMap, realMap);

    }

}

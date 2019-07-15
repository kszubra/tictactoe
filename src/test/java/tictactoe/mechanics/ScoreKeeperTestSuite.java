package tictactoe.mechanics;

import org.junit.Assert;
import org.junit.Test;

public class ScoreKeeperTestSuite {

    private ScoreKeeper testScoreKeeper;

    @Test
    public void testSettingObject() {
        //Given
        testScoreKeeper = new ScoreKeeper();

        testScoreKeeper.addLostByPlayer();
        testScoreKeeper.addLostByPlayer();
        testScoreKeeper.addLostByPlayer();

        testScoreKeeper.addWonByPlayer();

        //When
        double expectedWinLoseRatio = 0.33;
        double realWinLoseRatio = testScoreKeeper.getWinLoseRatio();

        int expectedWonNumber = 1;
        int realWonNumber = testScoreKeeper.getWonByPlayer();

        int expectedLostNumber = 3;
        int realLostnumber = testScoreKeeper.getLostByPlayer();

        //Then
        Assert.assertEquals(expectedWinLoseRatio, realWinLoseRatio, 0.001);
        Assert.assertEquals(realLostnumber, expectedLostNumber);
        Assert.assertEquals(expectedWonNumber, realWonNumber);

    }
}

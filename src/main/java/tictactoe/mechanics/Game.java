package tictactoe.mechanics;

import tictactoe.enumerics.CellStatus;
import tictactoe.enumerics.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static tictactoe.enumerics.CellStatus.CROSS;
import static tictactoe.enumerics.CellStatus.EMPTY;

public class Game {

    private static final int BOUND = 2;
    private static final int MATRIX_ROWS = 3;
    private static final int MATRIX_COLUMNS = 3;
    private static final int NUMBER_OF_MATRIX_FIELDS = MATRIX_COLUMNS * MATRIX_ROWS;
    private static final int HUMAN_STARTS = 0;
    private String humanPlayerName;
    private CellStatus[][] gameMatrix;
    private CellStatus winner;
    private GameMode gameMode;
    private boolean humanTurn;
    private boolean humanStarts;
    private int computerChoiceRow;
    private int computerChoiceColumn;
    private Map<String, List<CellStatus>> mapOfAvaiableLinesInGameMatrix;
    private GameCheckpoint checkpoint;

    public Game(InitialGameData initialData) {
        this.humanPlayerName = initialData.getPlayerName();
        this.winner = EMPTY;
        this.gameMode = initialData.getGameMode();
        this.humanStarts = verifyIfHumanStarts();
        this.gameMatrix = new CellStatus[MATRIX_ROWS][MATRIX_COLUMNS];
        setGameBoardToEmpty();
    }

    public void setGameFromCheckpoint(GameCheckpoint checkpoint) {
        this.humanPlayerName = checkpoint.getPlayerName();
        this.gameMatrix = checkpoint.getGameMatrix();
        this.humanTurn = checkpoint.isPlayersTurn();
        this.gameMode = checkpoint.getGameMode();
    }

    public GameCheckpoint getCheckpoint() {
        return this.checkpoint;
    }

    GameMode getGameMode() {
        return gameMode;
    }

    public void makeCheckpoint() {
        this.checkpoint = new GameCheckpoint(humanPlayerName, gameMatrix, humanTurn, gameMode);
    }

    private void setGameBoardToEmpty() {

        for (int a = 0; a < MATRIX_ROWS; a++) {
            for (int b = 0; b < MATRIX_COLUMNS; b++) {
                this.gameMatrix[a][b] = EMPTY;
            }
        }
    }

    public void resetGame() {
        setGameBoardToEmpty();
        this.winner = EMPTY;
        this.humanStarts = verifyIfHumanStarts();
        this.humanTurn = humanStarts;
    }

    private boolean verifyIfHumanStarts() {
        int random = Rules.RANDOM_GENERATOR.nextInt(BOUND);

        return random == HUMAN_STARTS;
    }

    public CellStatus getWinner() {
        return winner;
    }

    public CellStatus[][] getGameMatrix() {
        return gameMatrix;
    }

    public String getHumanPlayerName() {
        return this.humanPlayerName;
    }

    public boolean getHumanTurn() {
        return this.humanTurn;
    }

    public boolean getHumanStarts() {
        return humanStarts;
    }

    public int getComputerChoiceRow() {
        return computerChoiceRow;
    }

    public int getComputerChoiceColumn() {
        return computerChoiceColumn;
    }

    public void setWinner(CellStatus winner) {
        this.winner = winner;
    }

    void setGameMatrixValue(int row, int column, CellStatus statusToSet) {
        this.gameMatrix[row][column] = statusToSet;
    }

    public void setHumanTurn(boolean valueToSet) {
        this.humanTurn = valueToSet;
        System.out.println("Player's turn set to: " + valueToSet);
    }

    private void makeRandomComputerMove() {
        int randomRow = Rules.RANDOM_GENERATOR.nextInt(MATRIX_ROWS);
        int randomColumn = Rules.RANDOM_GENERATOR.nextInt(MATRIX_COLUMNS);

        if (gameMatrix[randomRow][randomColumn].equals(EMPTY)) {
            setComputerChoiceFor(randomRow, randomColumn);

        } else {
            makeRandomComputerMove();
        }
    }

    private boolean checkIfHaveAnyFreeCorner() {
        return ((gameMatrix[0][0].equals(EMPTY)) || (gameMatrix[MATRIX_ROWS-1][0].equals(EMPTY)) || (gameMatrix[0][MATRIX_COLUMNS-1].equals(EMPTY)) || (gameMatrix[MATRIX_ROWS-1][MATRIX_COLUMNS-1].equals(EMPTY)));
    }

    void playRandomCorner(){

        if(checkIfHaveAnyFreeCorner()) {
            int randomCornerToPlay = Rules.RANDOM_GENERATOR.nextInt(4);

            //Top left corner
            if(randomCornerToPlay == 0 && gameMatrix[0][0].equals(EMPTY)) {
                setComputerChoiceFor(0,0);
            }
            //Bottom left corner
            else if(randomCornerToPlay == 1 && gameMatrix[MATRIX_ROWS-1][0].equals(EMPTY)) {
                setComputerChoiceFor(MATRIX_ROWS-1,0);
            }
            //Top right corner
            else if(randomCornerToPlay == 2 && gameMatrix[0][MATRIX_COLUMNS-1].equals(EMPTY)) {
                setComputerChoiceFor(0,MATRIX_COLUMNS-1);
            }
            //Bottom right corner
            else if(randomCornerToPlay == 3 && gameMatrix[MATRIX_ROWS-1][MATRIX_COLUMNS-1].equals(EMPTY)) {
                setComputerChoiceFor(MATRIX_ROWS-1, MATRIX_COLUMNS-1);
            }
            //If random corner is taken, try again
            else {
                playRandomCorner();
            }
        }
    }


    void playOppositeCornerTo(int row, int column) {
        int oppositeRow = 0;
        int oppositeColumn = 0;

        if(row == 0) {
            oppositeRow = MATRIX_ROWS-1;
        } else if (row == MATRIX_ROWS-1) {
            oppositeRow = 0;
        }

        if(column == 0) {
            oppositeColumn = MATRIX_COLUMNS-1;
        } else if (column == MATRIX_COLUMNS-1) {
            oppositeColumn = 0;
        }

        if(gameMatrix[oppositeRow][oppositeColumn].equals(EMPTY)){
            setComputerChoiceFor(oppositeRow, oppositeColumn);
        }

    }

    void tryToWin(){
            String cellToPlay = Rules.tellCellToWin(mapOfAvaiableLinesInGameMatrix);
            int row = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('R')+1, cellToPlay.indexOf('C')));
            int column = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('C')+1));
            setComputerChoiceFor(row, column);
    }

    void tryToBlock() {
            String cellToPlay = Rules.tellCellToBlock(mapOfAvaiableLinesInGameMatrix);
            int row = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('R')+1, cellToPlay.indexOf('C')));
            int column = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('C')+1));
            setComputerChoiceFor(row, column);
    }

    void tryToContinueStartedLine(){
            String cellToPlay = Rules.tellCellToContinue(mapOfAvaiableLinesInGameMatrix);
            int row = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('R')+1, cellToPlay.indexOf('C')));
            int column = Integer.parseInt(cellToPlay.substring(cellToPlay.indexOf('C')+1));
            setComputerChoiceFor(row, column);
    }

    void makeRegularMove() {
        if(checkIfHaveAnyFreeCorner()) {
            playRandomCorner();
        } else {
            try {
                tryToContinueStartedLine();
            } catch (NoSuchElementException e){
                makeRandomComputerMove();
            }
        }
    }

    private void makeStrategicComputerMove() {
        mapOfAvaiableLinesInGameMatrix = Rules.makeMapOfLineCoordinatesInMatrix(gameMatrix);
        int howManyEmptyCells = (int)Arrays.stream(gameMatrix)
                .flatMap(Arrays::stream)
                .filter(e->e.equals(EMPTY))
                .count();

        //check if it's opening move
        if(howManyEmptyCells == NUMBER_OF_MATRIX_FIELDS) {
            System.out.println("Performing opening case");
            playRandomCorner();
        }
        //check if it's second move
        if(howManyEmptyCells == NUMBER_OF_MATRIX_FIELDS-1) { //If middle is empty -> play it
            System.out.println("Performing case I'm second");
            if(gameMatrix[(MATRIX_ROWS-1)/2][(MATRIX_COLUMNS-1)/2].equals(EMPTY)) {
                System.out.println("Performing case I'm second with with middle empty");
                setComputerChoiceFor((MATRIX_ROWS-1)/2, (MATRIX_COLUMNS-1)/2);

            } else {
                System.out.println("Performing case I'm second with with middle taken");
                playRandomCorner();
            }
        }
        //2nd step
        if((howManyEmptyCells == NUMBER_OF_MATRIX_FIELDS-2) && (gameMatrix[(MATRIX_ROWS-1)/2][(MATRIX_COLUMNS-1)/2].equals(CROSS))){
            System.out.println("Performing 2nd step with Player in the middle");
            playOppositeCornerTo(computerChoiceRow, computerChoiceColumn);
        }

        if((howManyEmptyCells == NUMBER_OF_MATRIX_FIELDS-2) && !(gameMatrix[(MATRIX_ROWS-1)/2][(MATRIX_COLUMNS-1)/2].equals(CROSS))){
            System.out.println("Performing 2nd step with Player NOT in the middle");
            playRandomCorner();
        }

        //3rd step and further
        if(howManyEmptyCells <= NUMBER_OF_MATRIX_FIELDS-3){
            System.out.println("Performing step 3+ : ");
            try {
                tryToWin();
                System.out.println("Performing step 3+ : winning move");

            } catch (NoSuchElementException a) {
                System.out.println("Can't win this round");
                try {
                    tryToBlock();
                    System.out.println("Performing step 3+ : blocking move");

                } catch (NoSuchElementException b){
                    System.out.println("Nothing to block");
                    makeRegularMove();
                    System.out.println("Performing step 3+ : regular move");
                }
            }
        }
    }

    private void setComputerChoiceFor(int row, int column) {
        gameMatrix[row][column] = CellStatus.CIRCLE;
        computerChoiceRow = row;
        computerChoiceColumn = column;
        System.out.println("Computer chose row " + computerChoiceRow + ", column " + computerChoiceColumn);
    }

    public void makeComputerMove() {
        setWinner(Rules.checkGameMatrixForWinner(gameMatrix));

        if (!humanTurn & winner.equals(EMPTY)) {
            if (gameMode.equals(GameMode.RANDOM)) {
                System.out.println("Computer makes random move:");
                makeRandomComputerMove();
            } else if (gameMode.equals(GameMode.STRATEGIC)) {
                System.out.println("Computer makes strategic move:");
                makeStrategicComputerMove();
            }
            setWinner(Rules.checkGameMatrixForWinner(gameMatrix));
            System.out.println("Going to call setHumanTurn() in makeComputerMove()");
            setHumanTurn(true);
        }

    }

    public void setPlayerChoice (int rowIndex, int columnIndex) {
        setGameMatrixValue(rowIndex, columnIndex, CellStatus.CROSS);
        setWinner(Rules.checkGameMatrixForWinner(gameMatrix));
        System.out.println("Calling setHumanTurn() in setPlayerChoice");
        setHumanTurn(false);
    }


}




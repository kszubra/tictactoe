package tictactoe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tictactoe.enumerics.CellStatus;
import tictactoe.mechanics.Game;
import tictactoe.mechanics.GameCheckpoint;
import tictactoe.mechanics.Rules;
import tictactoe.mechanics.ScoreKeeper;
import tictactoe.popupboxes.ConfirmationBox;
import tictactoe.popupboxes.MessageBox;
import tictactoe.popupboxes.NewGameBox;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static tictactoe.enumerics.CellStatus.EMPTY;

public class TicTacToeRunner extends Application {

    private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final int NUMBER_OF_ROWS_AND_COLUMNS = 3;

    private static final Image IMAGE_FOR_BACKGROUND = new Image("Graphics/background.jpg");
    private static final Image IMAGE_FOR_GAME_BOARD = new Image("Graphics/FinalGraphics/board.jpg");
    private static final Image IMAGE_FOR_X = new Image("Graphics/FinalGraphics/cross.png");
    private static final Image IMAGE_FOR_O = new Image("Graphics/FinalGraphics/circle.png");
    private static final Image ANIMATION_FOR_X = new Image("Graphics/FinalGraphics/DrawCross.gif");
    private static final Image ANIMATION_FOR_O = new Image("Graphics/FinalGraphics/DrawCircle.gif");
    private static final Image IMAGE_FOR_CURSOR = new Image("Graphics/cursorIcon.png");
    private static final Image IMAGE_FOR_EMPTY_FIELD = new Image("Graphics/FinalGraphics/transparent.png");
    private static final String CHECKPOINT_PATH = "/checkpoint.chp";
    private static final String SCORE_BOARD_PATH = "/scoreBoard.scb";

    private int roundsWonByPlayer;
    private int roundsWonByComputer;

    private Button exitButton, newGameButton, restartGameButton, saveGameButton, loadLastSaveButton, musicOnOffButton;
    private VBox buttons, rightScoreBoard;

    private ScrollPane scrollPaneForRanking;

    GridPane gameBoardPane;

    private HBox topRoundBar, bottomTextBar;
    private Text messageBoard, bottomText, playerScoreText, rightRankingText;
    private LocalDateTime currentTime;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, ImageView> cellsMap = new HashMap<>();
    private ImageView[][] uiImageViewCellsMatrix = new ImageView[3][3];
    private Map<String, ScoreKeeper> scoreBoardMap;

    private boolean uiHasEmptyFields;

    private Game currentGame;

    private void newGame() {
        System.out.println("--------------------------------------Starting new game--------------------------------------");
        setAllGameFieldsToEmpty();
        roundsWonByComputer = 0;
        roundsWonByPlayer = 0;
        updateScoreBoard();
        currentGame = new Game(NewGameBox.getUserPreference());
        performFirstMove();
    }

    private void updateScoreBoard() {
        playerScoreText.setText("Player " + roundsWonByPlayer + ":"+ roundsWonByComputer +" Computer");
    }

    private void performFirstMove() {
        System.out.println("--------------------------------------New round starts here--------------------------------------");
        if (!currentGame.getHumanStarts()) { // computer's opening move
            System.out.println("Computer starts");
            messageBoard.setText("Computer's turn");
            System.out.println("Going to call setHumanTurn() in performFirstMove() line 103");
            currentGame.setHumanTurn(false);
            currentGame.makeComputerMove();
            setComputerChoiceOnBoard(currentGame.getComputerChoiceRow(), currentGame.getComputerChoiceColumn());
            messageBoard.setText(currentGame.getHumanPlayerName() + "'s turn");

        } else {
            System.out.println("Player starts");
            System.out.println("Going to call setHumanTurn() in performFirstMove() in line 113");
            currentGame.setHumanTurn(true);
            messageBoard.setText(currentGame.getHumanPlayerName() + "'s turn");
        }
    }

    private void setAllGameFieldsToEmpty() {
        for (ImageView image : cellsMap.values()) {
            image.setImage(IMAGE_FOR_EMPTY_FIELD);
        }
    }

    private void restartGame() {
        currentGame.resetGame();
        setAllGameFieldsToEmpty();
        performFirstMove();
    }


    private void handleMouseEntersCell(MouseEvent event) {

        ImageView eventObject = (ImageView) event.getSource();

        if (cellsMap.values().contains(eventObject) && eventObject.getImage().equals(IMAGE_FOR_EMPTY_FIELD)) {
            eventObject.setImage(ANIMATION_FOR_X);
        }
    }

    private void handleMouseExitsCell(MouseEvent event) {

        ImageView eventObject = (ImageView) event.getSource();

        if (cellsMap.values().contains(eventObject) && eventObject.getImage().equals(ANIMATION_FOR_X)) {
            eventObject.setImage(IMAGE_FOR_EMPTY_FIELD);
        }
    }

    private void setComputerChoiceOnBoard(int row, int column){
        System.out.println("Going to set circle image in row: " + row + ", column: " + column);
        String key = "" +row + column;
        cellsMap.get(key).setImage(IMAGE_FOR_O);
    }

    private void handleMouseClickCell(MouseEvent event) {
        System.out.println("Calling handleMouseClickCell() method");

        ImageView eventObject = (ImageView) event.getSource();

        if (!(currentGame.getHumanTurn())) {
            MessageBox.displayMessage("Wrong turn", "It's not your turn now. Please wait");
        } else if ((currentGame.getHumanTurn()) && !(eventObject.getImage().equals(ANIMATION_FOR_X))) {
            MessageBox.displayMessage("Cell taken", "This cell is already taken. Please choose different one");

        } else if ((currentGame.getHumanTurn()) && (eventObject.getImage().equals(ANIMATION_FOR_X))) {
            System.out.println("Calling setHumanTurn() in clickHandler:");
            currentGame.setHumanTurn(false);
            eventObject.setImage(IMAGE_FOR_X);
            int rowIndex = GridPane.getRowIndex(eventObject);
            int columnIndex = GridPane.getColumnIndex(eventObject);
            System.out.println("Player made move:");
            System.out.println("Player chose row: " + rowIndex + ", column: " + columnIndex);
            currentGame.setPlayerChoice(rowIndex, columnIndex);
            checkBoard();
            messageBoard.setText("Computer's turn");

            currentGame.makeComputerMove();
            setComputerChoiceOnBoard(currentGame.getComputerChoiceRow(), currentGame.getComputerChoiceColumn());
            checkBoard();
            messageBoard.setText(currentGame.getHumanPlayerName() + "'s turn");
        }
    }

    private CellStatus checkUIForWinner() {
        System.out.println("Checking UI for the winner");
        CellStatus[][] uiBoardStatus = new CellStatus[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                if(uiImageViewCellsMatrix[row][col].getImage().equals(IMAGE_FOR_EMPTY_FIELD)) {
                    uiBoardStatus[row][col] = EMPTY;
                } else if(uiImageViewCellsMatrix[row][col].getImage().equals(IMAGE_FOR_X)) {
                    uiBoardStatus[row][col] = CellStatus.CROSS;
                } else if(uiImageViewCellsMatrix[row][col].getImage().equals(IMAGE_FOR_O)) {
                    uiBoardStatus[row][col] = CellStatus.CIRCLE;
                }
            }
        }

        if(Arrays.stream(uiBoardStatus).flatMap(Arrays::stream).anyMatch(e->e.equals(EMPTY))) {
            uiHasEmptyFields = true;
        } else {
            uiHasEmptyFields = false;
        }

        if(Arrays.deepEquals(uiBoardStatus, currentGame.getGameMatrix())) {
            System.out.println("UI and game boards are equal");
        } else {
            System.out.println("UI and game boards ARE NOT EQUAL!!");
        }

        System.out.println("UI board: ");

        return Rules.checkGameMatrixForWinner(uiBoardStatus);

    }

    private void checkBoard() {
        System.out.print("checking board... ");
        CellStatus gameWinnerByUI = checkUIForWinner();
        System.out.println("Game winner due to UI: " + gameWinnerByUI);
        System.out.println("Game board: ");
        currentGame.setWinner(Rules.checkGameMatrixForWinner(currentGame.getGameMatrix()));
        List<CellStatus> gameMatrixElements = Arrays.stream(currentGame.getGameMatrix())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        if ((currentGame.getWinner().equals(CellStatus.CROSS)) || gameWinnerByUI.equals(CellStatus.CROSS)) {
            System.out.print("Cross wins \r\n");
            roundsWonByPlayer++;
            updateScoreBoard();
            addVictoryToScoreBoard();
            updateRankingBoard();
            if(ConfirmationBox.getDecision("Game ended", currentGame.getHumanPlayerName() + " won! \r\n Do you want to play again?")){
                restartGame();
            } else{
                System.exit(0);
            }

        } else if ((currentGame.getWinner().equals(CellStatus.CIRCLE)) || gameWinnerByUI.equals(CellStatus.CIRCLE)) {
            System.out.print("Circle wins \r\n");
            roundsWonByComputer++;
            updateScoreBoard();
            addDefeatToScoreBoard();
            updateRankingBoard();
            if(ConfirmationBox.getDecision("Game ended", "Computer won! \r\n Do you want to play again?")){
                restartGame();
            } else{
                System.exit(0);
            }

        } else if ( ((!gameMatrixElements.contains(EMPTY)) && (currentGame.getWinner().equals(EMPTY))) ||
                    ( (!uiHasEmptyFields)) && (gameWinnerByUI.equals(EMPTY)) ) {
            System.out.print("Draw \r\n");
            if(ConfirmationBox.getDecision("Game ended", "Draw! \r\n Do you want to play again?")){
                restartGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void saveScoreBoardMap() {

        try {
            ObjectOutputStream outStreamSaveScoreBoard = new ObjectOutputStream(new FileOutputStream(SCORE_BOARD_PATH));
            outStreamSaveScoreBoard.writeObject(scoreBoardMap);
            outStreamSaveScoreBoard.close();

        } catch (IOException savingIOException) {
            MessageBox.displayMessage("Exception", savingIOException.getMessage());
        }
    }

    private void addVictoryToScoreBoard() {
        if(scoreBoardMap.containsKey(currentGame.getHumanPlayerName())){
            scoreBoardMap.get(currentGame.getHumanPlayerName()).addWonByPlayer();
        } else {
            scoreBoardMap.put(currentGame.getHumanPlayerName(), new ScoreKeeper());
            scoreBoardMap.get(currentGame.getHumanPlayerName()).addWonByPlayer();
        }

        saveScoreBoardMap();
    }

    private void addDefeatToScoreBoard() {
        if(scoreBoardMap.containsKey(currentGame.getHumanPlayerName())){
            scoreBoardMap.get(currentGame.getHumanPlayerName()).addLostByPlayer();
        } else {
            scoreBoardMap.put(currentGame.getHumanPlayerName(), new ScoreKeeper());
            scoreBoardMap.get(currentGame.getHumanPlayerName()).addLostByPlayer();
        }

        saveScoreBoardMap();
    }

    private void updateRankingBoard() {
        StringBuilder rankingBuilder = new StringBuilder();

        List<Map.Entry<String, ScoreKeeper>> sortedRanking = new ArrayList<>();
        sortedRanking.addAll(scoreBoardMap.entrySet());

        sortedRanking.sort(Comparator.comparing(Map.Entry::getValue));

        for(Map.Entry<String, ScoreKeeper> entry : sortedRanking){
            rankingBuilder.append(entry.getKey() + ": " + entry.getValue().toString());
        }

        rightRankingText.setText(rankingBuilder.toString());
    }

    private void generateCell(int row, int column) {
        ImageView cellImage = new ImageView(IMAGE_FOR_EMPTY_FIELD);
        cellImage.setPickOnBounds(true);
        gameBoardPane.add(cellImage, column, row);

        String key = "" + row + column;
        cellsMap.put(key, cellImage);
        uiImageViewCellsMatrix[row][column] = cellImage;
        cellImage.setOnMouseEntered(e -> handleMouseEntersCell(e));
        cellImage.setOnMouseExited(e -> handleMouseExitsCell(e));
        cellImage.setOnMouseClicked(e -> handleMouseClickCell(e));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(SCREEN_WIDTH * 0.6, SCREEN_HEIGHT * 0.6, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(IMAGE_FOR_BACKGROUND, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        BackgroundSize boardBackgroundSize = new BackgroundSize(IMAGE_FOR_GAME_BOARD.getWidth(), IMAGE_FOR_BACKGROUND.getHeight(), true, true, true, false);
        BackgroundImage boardBackgroundImage = new BackgroundImage(IMAGE_FOR_GAME_BOARD, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, boardBackgroundSize);
        Background boardBackground = new Background(boardBackgroundImage);

        try{
            ObjectInputStream inStreamLoadScoreBoard = new ObjectInputStream(new FileInputStream(SCORE_BOARD_PATH));
            scoreBoardMap = (HashMap)inStreamLoadScoreBoard.readObject();
            inStreamLoadScoreBoard.close();
            System.out.println("Successfully loaded score board file");

        } catch (FileNotFoundException fileNotfoundException) { // If can't find file to load -> try to create it
            System.out.println("Unable to load score board file. trying to create it");
            this.scoreBoardMap = new HashMap<>();
            saveScoreBoardMap();
        }

        roundsWonByComputer = 0;
        roundsWonByPlayer = 0;

        Media soundFile = new Media(getClass().getResource("/Sounds/Darsilon.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(soundFile);
        player.setOnEndOfMedia(new Runnable() {
                                   @Override
                                   public void run() {
                                       player.seek(Duration.ZERO);
                                   }
                               }
        );
        player.play();

        exitButton = new Button("Exit");
        exitButton.setMinSize(200, 50);
        exitButton.setOnMouseClicked(e -> {
            if (ConfirmationBox.getDecision("Quit game", "Are you sure you want to quit?")) {
                System.exit(0);
            }
        });

        newGameButton = new Button("New game");
        newGameButton.setMinSize(200, 50);
        newGameButton.setOnMouseClicked(e -> {
            if (ConfirmationBox.getDecision("New game", "Are you sure you want to start a new game?")) {
                newGame();
            }
        });

        restartGameButton = new Button("Restart game");
        restartGameButton.setMinSize(200, 50);
        restartGameButton.setOnMouseClicked(e -> {
            if (ConfirmationBox.getDecision("Restarting game", "Are you sure you want to play again? \n Game mode and player's name won't change. " +
                    "If you want to change them, choose New Game")) {
                restartGame();
            }
        });

        saveGameButton = new Button("Create checkpoint");
        saveGameButton.setMinSize(200, 50);
        saveGameButton.setOnMouseClicked(e -> {
            try {
                ObjectOutputStream outStreamSaveGame = new ObjectOutputStream(new FileOutputStream(CHECKPOINT_PATH));
                currentGame.makeCheckpoint();
                outStreamSaveGame.writeObject(currentGame.getCheckpoint());
                outStreamSaveGame.close();
                MessageBox.displayMessage("Checkpoint", "Checkpoint successfully created");

            } catch (IOException saveGameException) {
                MessageBox.displayMessage("Exception", saveGameException.getMessage());
            }

        });

        loadLastSaveButton = new Button("Load last checkpoint");
        loadLastSaveButton.setMinSize(200, 50);
        loadLastSaveButton.setOnMouseClicked(e -> {

            try{
                ObjectInputStream inStreamLoadGame = new ObjectInputStream(new FileInputStream(CHECKPOINT_PATH));
                currentGame.setGameFromCheckpoint((GameCheckpoint) inStreamLoadGame.readObject());
                inStreamLoadGame.close();

            } catch (IOException | ClassNotFoundException loadGameException) {
                MessageBox.displayMessage("Exception", loadGameException.getMessage());
            }

            CellStatus[][] matrixFromGame = currentGame.getGameMatrix();

            for (int row = 0; row < NUMBER_OF_ROWS_AND_COLUMNS; row++) {
                for (int col = 0; col < NUMBER_OF_ROWS_AND_COLUMNS; col++) {

                    if (matrixFromGame[row][col].equals(CellStatus.CROSS)) {
                        cellsMap.get(""+row+col).setImage(IMAGE_FOR_X);
                    } else if (matrixFromGame[row][col].equals(CellStatus.CIRCLE)) {
                        cellsMap.get(""+row+col).setImage(IMAGE_FOR_O);
                    } else {
                        cellsMap.get(""+row+col).setImage(IMAGE_FOR_EMPTY_FIELD);
                    }
                }
            }

            if(currentGame.getHumanTurn()) {
                messageBoard.setText(currentGame.getHumanPlayerName() + "'s turn");
            } else {
                messageBoard.setText(currentGame.getHumanPlayerName() + "'s turn");
            }
        });


        musicOnOffButton = new Button("Turn music off");
        musicOnOffButton.setMinSize(200, 50);
        musicOnOffButton.setOnMouseClicked(e -> {
            if (musicOnOffButton.getText().equals("Turn music off")) {
                player.stop();
                musicOnOffButton.setText("Turn music on");
            } else if (musicOnOffButton.getText().equals("Turn music on")){
                player.play();
                musicOnOffButton.setText("Turn music off");
            }
        });

        playerScoreText = new Text("Player " + roundsWonByPlayer + ":"+ roundsWonByComputer +" Computer");
        playerScoreText.setFont(Font.font("Verdana", 20));
        playerScoreText.setFill(Color.BLACK);

        rightRankingText = new Text();
        rightRankingText.setFont(Font.font("Verdana", 15));
        rightRankingText.setFill(Color.BLACK);

        scrollPaneForRanking = new ScrollPane();
        scrollPaneForRanking.setContent(rightRankingText);
        scrollPaneForRanking.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneForRanking.setPrefViewportHeight(IMAGE_FOR_EMPTY_FIELD.getHeight() * 2);

        updateRankingBoard();

        rightScoreBoard = new VBox(playerScoreText, scrollPaneForRanking);
        rightScoreBoard.setSpacing(20);

        buttons = new VBox(exitButton, newGameButton, restartGameButton, saveGameButton, loadLastSaveButton, musicOnOffButton);
        buttons.setSpacing(25);

        messageBoard = new Text();
        messageBoard.setFont(Font.font("Verdana", 45));
        messageBoard.setFill(Color.BLACK);

        topRoundBar = new HBox(messageBoard);
        topRoundBar.setSpacing(15);

        bottomText = new Text();
        currentTime = LocalDateTime.now();
        bottomText.setText(currentTime.format(timeFormatter));
        bottomText.setFont(Font.font("Verdana", 45));
        bottomText.setFill(Color.BLACK);
        final Timeline clockTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            currentTime = LocalDateTime.now();
                            bottomText.setText(currentTime.format(timeFormatter));
                        }
                )
        );
        clockTimeline.setCycleCount( Animation.INDEFINITE );
        clockTimeline.play();

        bottomTextBar = new HBox(bottomText);

        gameBoardPane = new GridPane();
        gameBoardPane.setGridLinesVisible(true);
        gameBoardPane.setCursor(new ImageCursor(IMAGE_FOR_CURSOR));
        gameBoardPane.setMaxSize(409, 409);
        gameBoardPane.setAlignment(Pos.CENTER);
        gameBoardPane.setBackground(boardBackground);
        gameBoardPane.setPadding(new Insets(10, 10, 10, 10));
        gameBoardPane.setHgap(5);
        gameBoardPane.setVgap(5);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                generateCell(row, col);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(background);
        borderPane.setCenter(gameBoardPane);
        borderPane.setLeft(buttons);
        borderPane.setTop(topRoundBar);
        borderPane.setBottom(bottomTextBar);
        borderPane.setRight(rightScoreBoard);
        gameBoardPane.setAlignment(Pos.CENTER); //here was last change
        topRoundBar.setAlignment(Pos.CENTER);
        bottomTextBar.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        rightScoreBoard.setAlignment(Pos.CENTER);

        Scene scene = new Scene(borderPane, IMAGE_FOR_BACKGROUND.getWidth(), IMAGE_FOR_BACKGROUND.getHeight(), Color.BLACK);

        primaryStage.setTitle("Tick Tack Toe");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setOpacity(1);
        primaryStage.setScene(scene);
        primaryStage.show();

        newGame();
    }



    public static void main(String[] args) {

           launch(args);

    }

}

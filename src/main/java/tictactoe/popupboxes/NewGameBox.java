package tictactoe.popupboxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tictactoe.enumerics.GameMode;
import tictactoe.mechanics.InitialGameData;

public class NewGameBox {

    private static final int ONE_CHAR = 1;
    private static InitialGameData initialGameData;

    public static InitialGameData getUserPreference() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("New game");
        window.setWidth(400);
        window.setHeight(300);

        Label nameField = new Label();
        nameField.setText("Enter your name:");

        TextField inputTextField = new TextField();
        inputTextField.setMaxWidth(window.getWidth() * 0.5);

        Label gameModeChoice = new Label();
        gameModeChoice.setText("Select game mode:");

        ChoiceBox<GameMode> gameModeChoiceBox = new ChoiceBox<>();
        gameModeChoiceBox.getItems().addAll(GameMode.RANDOM, GameMode.STRATEGIC);
        gameModeChoiceBox.setValue(GameMode.RANDOM);

        Button confirmButton = new Button();
        confirmButton.setText("Confirm");
        confirmButton.setOnMouseClicked(e -> {

            if (isNotBlank(inputTextField)) {
                initialGameData = new InitialGameData(inputTextField.getText(), gameModeChoiceBox.getValue());
                window.close();
            }

        });

        VBox windowLayout = new VBox(nameField, inputTextField, gameModeChoice, gameModeChoiceBox, confirmButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5, 5, 5, 5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

        return initialGameData;
    }

    private static boolean isNotBlank(TextField inputTextField) {
        return inputTextField.getText().length() >= ONE_CHAR;
    }
}

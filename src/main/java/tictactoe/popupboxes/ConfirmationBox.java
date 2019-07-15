package tictactoe.popupboxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {

    private static boolean userChoice;

    public static boolean getDecision(String windowTitle, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle(windowTitle);
        window.setWidth(600);

        Label messageLabel = new Label();
        messageLabel.setText(message);

        Button yesButton = new Button();
        yesButton.setText("Yes");
        yesButton.setOnMouseClicked(e->{
            userChoice =true;
            window.close();
        });

        Button noButton = new Button();
        noButton.setText("No");
        noButton.setOnMouseClicked(e->{
            userChoice = false;
            window.close();
        });

        VBox windowLayout = new VBox(messageLabel, yesButton, noButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

        return userChoice;

    }
}

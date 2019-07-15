package tictactoe.popupboxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextInputBox {

    private static String userInputText;

    public static String getUserInputText(String windowTitle, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle(windowTitle);
        window.setWidth(600);

        Label messageLabel = new Label();
        messageLabel.setText(message);

        TextField inputTextField = new TextField();

        Button confirmButton = new Button();
        confirmButton.setText("Confirm");
        confirmButton.setOnMouseClicked(e->{
            if(!(inputTextField.getText().length()<1)){
                userInputText = inputTextField.getText();
                //System.out.println("taken text is: " + userInputText);
                window.close();
            }
        });

        VBox windowLayout = new VBox(messageLabel, inputTextField, confirmButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

        return userInputText;
    }
}

package com.example.guessnumberapp.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class AlertDialogHelper {

    public void showCongratsDialog(String username, int systemGeneratedNumber, int guessCount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bravo!!!");
        alert.setHeaderText("Congratulations " + username + "!\n\nYou guessed it right.");
        alert.setContentText("Correct Number: %d\nTotal attempts: %d".formatted(systemGeneratedNumber, guessCount));

        ImageView congratsIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/congrats.png"))));
        congratsIcon.setFitWidth(80);
        congratsIcon.setPreserveRatio(true);
        alert.setGraphic(congratsIcon);

        alert.showAndWait();
    }

    public void showGameOverDialog(String username, int systemGeneratedNumber, int guessCount) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("😭😭😭!!!");
        alert.setHeaderText("Game Over!\n\nWhy " + username + " Why 😭?.");
        alert.setContentText("Correct Number: %d\nTotal attempts: %d".formatted(systemGeneratedNumber, guessCount));

        ImageView congratsIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/game_over.png"))));
        congratsIcon.setFitWidth(180);
        congratsIcon.setPreserveRatio(true);
        alert.setGraphic(congratsIcon);

        alert.showAndWait();
    }
}

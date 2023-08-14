package com.example.guessnumberapp;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

public class HelloController {
    @FXML
    private Label hintLabel, countLabel, timerLabel;

    @FXML
    private TextField numberInputTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button startButton, hintButton, guessButton;

    int systemGeneratedNumber, guessCount;
    String hint = "";
    int COUNT_DOWN = 3;
    private final IntegerProperty countDownTime = new SimpleIntegerProperty();
    private boolean stopCountdown = false;

    private int generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("Number:" + randomNumber);
        hint = new HintProvider().provideHint(randomNumber);

        Tooltip tooltip = new Tooltip();
        tooltip.setText("Hint: " + hint);
        tooltip.setFont(Font.font("Comic Sans MS", 12));
        tooltip.setGraphic(getHintIcon());
        Tooltip.install(imageView, tooltip);


        this.guessCount = 0;
        return randomNumber;
    }

    @FXML
    private void initialize() {
        timerLabel.textProperty().bind(Bindings.format("Time remaining: %d", countDownTime));
        hintButton.setVisible(false);
        disableButtons(true);
        numberInputTextField.setOnMouseClicked(event -> numberInputTextField.selectAll());
    }

    @FXML
    private void onGuessButtonClicked(ActionEvent actionEvent) {
        try {
            //Grab the input from TextField
            String value = numberInputTextField.getText();

            //Check if the input is empty or not
            if (value.equals("")) {
                hintLabel.setText("Please provide number!!!");
            }

            //Parse the string value to integer
            int guessNumber = Integer.parseInt(value);

            //Ensure that the system generated number is valid
            if (systemGeneratedNumber <= 0) {
                guessCount++;
                hintLabel.setText("I never pick the number lower than 0!!!");
            } else if (guessNumber >= 100) {
                guessCount++;
                hintLabel.setText("Good for you! I never pick the number higher than 100!!!");
            }
            //Check if the guess number is correct or not
            else if (guessNumber > systemGeneratedNumber) {
                guessCount++;
                hintLabel.setText("Please provide lower number than this!!!");
            } else if (guessNumber < systemGeneratedNumber) {
                guessCount++;
                hintLabel.setText("Please provide higher number than this!!!");
            } else {
//                hintLabel.setText("Congratulations!!!");
                stopCountdown();
                disableButtons(true);
                showCongratsDialog();
            }
            countLabel.setText("Count: " + guessCount);
        } catch (NumberFormatException numberFormatException) {
            hintLabel.setText("Please provide valid number!!!");
        } catch (Exception err) {
            hintLabel.setText("Something went wrong [Reason:" + err.getMessage() + "]");
        }
    }

    public void onRestartButtonClicked(ActionEvent actionEvent) {
        this.startButton.setText("Restart");
        systemGeneratedNumber = this.generateRandomNumber();
        numberInputTextField.setText("");
        hintLabel.setText("");
        countLabel.setText("Count: 0");
        disableButtons(false);
        this.startCountdown(COUNT_DOWN);
    }

    private void startCountdown(int inputValue) {
        if (inputValue > 0) {
            stopCountdown = false;
            countDownTime.set(inputValue);
            Thread countdownThread = new Thread(() -> {
                while (countDownTime.get() > 0 && !stopCountdown) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int newValue = countDownTime.get() - 1;
                    Platform.runLater(() -> countDownTime.set(newValue));
                }

                if (countDownTime.get() <= 0) {
                    Platform.runLater(() -> {
                        countDownTime.set(0);
                        disableButtons(true);
                        showGameOverDialog();
                    });
                }
            });
            countdownThread.start();
        }

    }

    public void onHintButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Why you cheat?");
        alert.setHeaderText("Hint");
        alert.setContentText(hint);
        alert.setGraphic(getHintIcon());
        alert.showAndWait();
    }

    private void disableButtons(boolean flag) {
        hintButton.setDisable(flag);
        guessButton.setDisable(flag);
        numberInputTextField.setDisable(flag);
    }

    private ImageView getHintIcon() {
        ImageView hintImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/3194435-200.png"))));
        hintImage.setFitWidth(50);
        hintImage.setPreserveRatio(true);
        return hintImage;
    }

    private void stopCountdown() {
        stopCountdown = true;
    }

    private void showCongratsDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bravo!!!");
        alert.setHeaderText("Congratulations!\n\nYou guessed it right.");
        alert.setContentText("Correct Number: %d\nTotal attempts: %d".formatted(systemGeneratedNumber, guessCount));

        ImageView congratsIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/congrats.png"))));
        congratsIcon.setFitWidth(80);
        congratsIcon.setPreserveRatio(true);
        alert.setGraphic(congratsIcon);

        alert.showAndWait();
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("😭😭😭!!!");
        alert.setHeaderText("Game Over!\n\nWhy God Why 😭?.");
        alert.setContentText("Correct Number: %d\nTotal attempts: %d".formatted(systemGeneratedNumber, guessCount));

        ImageView congratsIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/game_over.png"))));
        congratsIcon.setFitWidth(180);
        congratsIcon.setPreserveRatio(true);
        alert.setGraphic(congratsIcon);

        alert.showAndWait();
    }
}
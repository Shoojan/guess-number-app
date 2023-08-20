package com.example.guessnumberapp.controllers;

import com.example.guessnumberapp.utils.AlertDialogHelper;
import com.example.guessnumberapp.utils.CountDownTimer;
import com.example.guessnumberapp.utils.HintProvider;
import com.example.guessnumberapp.utils.RandomNumberGenerator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.concurrent.ExecutionException;

public class MainController {
    @FXML
    private Label welcomeLabel, hintLabel, countLabel, timerLabel;

    @FXML
    private TextField numberInputTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button startButton, guessButton;

    @FXML
    private VBox gameAreaVBox;

    private BorderPane rootPane;
    private VBox helpArea;

    int systemGeneratedNumber, guessCount;

    private String username;
    private int countDown;
    private final static int DEFAULT_COUNT_DOWN = 30;
    private boolean isGameStarted = false;


    private AlertDialogHelper dialogHelper;
    private CountDownTimer countDownTimer;

    public void setData(String username, BorderPane root, VBox helpArea) {
        this.username = username;
        welcomeLabel.setText("Welcome " + username + "! Try guessing my number 😁");
        this.rootPane = root;
        this.helpArea = helpArea;
    }

    @FXML
    private void initialize() {
        disableButtons(true);
        numberInputTextField.setOnMouseClicked(event -> numberInputTextField.selectAll());
        numberInputTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) guessNumber();
        });
        this.dialogHelper = new AlertDialogHelper();
        isGameStarted = false;
    }

    @FXML
    private void onGuessButtonClicked(ActionEvent actionEvent) {
        guessNumber();
    }

    public void onRestartButtonClicked(ActionEvent actionEvent) throws ExecutionException {
        startButton.setText("Restart");
        systemGeneratedNumber = RandomNumberGenerator.generateRandomNumber();
        setHintInImage();
        this.guessCount = 0;
        numberInputTextField.setText("");
        hintLabel.setText("");
        countLabel.setText("Count: 0");
        disableButtons(false);
        isGameStarted = true;
        startCountDown(DEFAULT_COUNT_DOWN);
    }

    private void guessNumber() {
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
                countDownTimer.stopCountdown();
                disableButtons(true);
                dialogHelper.showCongratsDialog(username, systemGeneratedNumber, guessCount);
            }
            countLabel.setText("Count: " + guessCount);
        } catch (NumberFormatException numberFormatException) {
            hintLabel.setText("Please provide valid number!!!");
        } catch (Exception err) {
            hintLabel.setText("Something went wrong [Reason:" + err.getMessage() + "]");
        }
    }

    private void setHintInImage() {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Hint: " + new HintProvider().provideHint(systemGeneratedNumber));
        tooltip.setFont(Font.font("Comic Sans MS", 12));
        Tooltip.install(imageView, tooltip);
    }

    private void startCountDown(int countDownPeriod) {
        // Run the task in a background thread
        countDownTimer = new CountDownTimer(timerLabel, countDownPeriod);
        countDownTimer.setOnSucceeded(event -> {
            int finalCountDown = countDownTimer.getValue();
            if (finalCountDown == 0 && isGameStarted) {
                // Update the UI with the result using Platform.runLater()
                Platform.runLater(() -> {
                    disableButtons(true);
                    dialogHelper.showGameOverDialog(username, systemGeneratedNumber, guessCount);
                    isGameStarted = false;
                });
            } else {
                countDown = finalCountDown;
            }
        });
        new Thread(countDownTimer).start();
    }

    private void disableButtons(boolean flag) {
        guessButton.setDisable(flag);
        numberInputTextField.setDisable(flag);
    }

    public void onPauseButtonClicked(ActionEvent actionEvent) {
        Button pauseButton = (Button) actionEvent.getSource();
        if (pauseButton.getText().equalsIgnoreCase("Pause")) {
            startButton.setDisable(true);
            pauseButton.setText("Resume");
            if (countDownTimer != null)
                countDownTimer.pauseCountdown();
            rootPane.setCenter(helpArea);
        } else {
            startButton.setDisable(false);
            pauseButton.setText("Pause");
            startCountDown(countDown);
            rootPane.setCenter(gameAreaVBox);
        }
    }


}
package com.example.guessnumberapp.utils;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class CountDownTimer extends Task<Integer> {

    private final IntegerProperty countDownTime;

    private boolean stopCountDown = false;

    public CountDownTimer(Label timerLabel, int countDownPeriod) {
        countDownTime = new SimpleIntegerProperty();
        timerLabel.textProperty().bind(Bindings.format("Time remaining: %d", countDownTime));
        countDownTime.set(countDownPeriod);
    }

    public void stopCountdown() {
        stopCountDown = true;
    }

    public void pauseCountdown() {
        stopCountDown = true;
    }

    @Override
    public Integer call() throws Exception {
        while (countDownTime.get() > 0 && !stopCountDown) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int newValue = countDownTime.get() - 1;
            setCountDown(newValue);
        }
        if (countDownTime.get() <= 0) {
            setCountDown(0);
        }
        return countDownTime.get();
    }

    private void setCountDown(int value) {
        Platform.runLater(() -> countDownTime.set(value));
    }
}

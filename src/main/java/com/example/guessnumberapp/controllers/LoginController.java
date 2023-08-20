package com.example.guessnumberapp.controllers;

import com.example.guessnumberapp.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements IRootController {
    private MainApplication mainApp;

    @FXML
    private TextField usernameTextField;

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void onButtonClicked(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        if (!username.isBlank())
            mainApp.switchToGameStage(username);
    }
}

package com.example.guessnumberapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = showLoginStage();
    }

    private Stage showLoginStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent loginRoot = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        Stage loginStage = new Stage(StageStyle.DECORATED);
        loginStage.setTitle("Welcome");
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
        return loginStage;
    }

    private Stage showGameStage(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setUsername(username);
        mainController.setMainApp(this);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Guess the Number!");
        stage.setScene(new Scene(root));
        stage.show();
        return stage;
    }

    public void switchToGameStage(String username) throws IOException {
        if (mainStage != null) {
            mainStage.close();
        }
        mainStage = showGameStage(username);
    }

    public static void main(String[] args) {
        launch();
    }
}
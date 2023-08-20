module com.example.guessnumberapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.guessnumberapp to javafx.fxml;
    exports com.example.guessnumberapp;
    exports com.example.guessnumberapp.utils;
    opens com.example.guessnumberapp.utils to javafx.fxml;
    exports com.example.guessnumberapp.controllers;
    opens com.example.guessnumberapp.controllers to javafx.fxml;
}
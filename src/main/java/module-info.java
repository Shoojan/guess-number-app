module com.example.guessnumberapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.guessnumberapp to javafx.fxml;
    exports com.example.guessnumberapp;
}
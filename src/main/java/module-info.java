module com.example.racetrackproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.racetrackproject to javafx.fxml;
    exports com.example.racetrackproject;
}
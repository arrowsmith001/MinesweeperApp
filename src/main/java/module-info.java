module com.arrowsmith.minesweeperapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.arrowsmith.minesweeperapp to javafx.fxml;
    exports com.arrowsmith.minesweeperapp;
}
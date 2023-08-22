module com.arrowsmith.minesweeperapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.arrowsmith.minesweeperapp to javafx.fxml;
    exports com.arrowsmith.minesweeperapp;
}
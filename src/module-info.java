module RECOBEATS {
    requires javafx.controls;
    requires javafx.fxml;

    opens application to javafx.graphics, javafx.fxml;
    opens controllers to javafx.fxml;
}

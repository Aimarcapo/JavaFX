module memorygame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens memorygame to javafx.fxml;
    exports memorygame;
}

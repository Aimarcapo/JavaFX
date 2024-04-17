package memorygame;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("juego");
       JuegoController controlador = App.getLoader().getController();
       controlador.initializeWithData(4, 60);

    }
}

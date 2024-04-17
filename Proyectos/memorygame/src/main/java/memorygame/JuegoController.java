package memorygame;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.BackgroundImage;

public class JuegoController {
    @FXML
    AnchorPane paneGeneral;
    @FXML
    AnchorPane juegoPane;
    @FXML
    Label aciertos;
    @FXML
    Label segundos;

    Juego juego;
    Timeline timerTimeline;
    int currentCount;

    @FXML
    public void initialize() {

       
        int dim = 4;
        


    }

    public void initializeWithData(int dim2, int currentCount){
        int dim=4;
        juego = new Juego(dim);

        this.currentCount = 60;
      
       
       
        aciertos.setText(String.valueOf(juego.getAciertos()));
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Subtract 1 from currentCount
            this.currentCount--;

            // Update the Label
            segundos.setText(String.valueOf(this.currentCount));
        }));
       
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();

        GridPane gPane = new GridPane();
        paneGeneral.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Obtener la referencia al escenario
                Stage stage = (Stage) paneGeneral.getScene().getWindow();

                // Establecer el escenario en estado maximizado
                stage.setMaximized(true);

                // Bind the width and height of the AnchorPane to the width and height of the
                // Scene
                //stage.setMinWidth(300 * dim + 200);
                //stage.setMinHeight(165 * dim + 300);
                aciertos.setFont(new Font(20));
                segundos.setFont(new Font(20));
                aciertos.setMinWidth(200);
               
                segundos.setMinWidth(200);
                paneGeneral.prefWidthProperty().bind(newScene.widthProperty());
                paneGeneral.prefHeightProperty().bind(newScene.heightProperty());
                juegoPane.prefWidthProperty().bind(newScene.widthProperty());
                // juegoPane.prefHeightProperty().bind(newScene.heightProperty());
                juegoPane.prefHeightProperty().bind(newScene.heightProperty().subtract(paneGeneral.layoutYProperty())
                        .subtract(paneGeneral.getLayoutBounds().getMaxY()));
                gPane.prefWidthProperty().bind(juegoPane.widthProperty());
               
               gPane.prefHeightProperty().bind(juegoPane.heightProperty());

            }
        });

        gPane.setGridLinesVisible(true);
        gPane.setPadding(new Insets(10));
        
        // Define column constraints
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(100 / dim); // 25% width for each column
        gPane.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints, colConstraints);

        // Define row constraints
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100 / dim); // 25% height for each row
        gPane.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints);

        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                // Crear un nuevo ImageView con la imagen de fondo
                ImageView imageView = new ImageView();
                Image image = null;
                try {
                    image = new Image(getClass().getResource("/memorygame/imagenes/bocaabajo.png").toURI().toString());
                    imageView.setImage(image);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                // Crear un nuevo AnchorPane para cada celda y establecer la imagen de fondo
                AnchorPane aPane = new AnchorPane();
                aPane.setBackground(new Background(new BackgroundImage(image,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

                // Agregar el AnchorPane a la posición correspondiente en el GridPane
                gPane.add(aPane, col, row);

                // Configurar el tamaño de la celda en función del tamaño del GridPane y el
                // número de columnas/filas
                double cellWidth = gPane.getWidth() / dim;
                double cellHeight = gPane.getHeight() / dim;
                gPane.getColumnConstraints().get(col).setPercentWidth(100.0 / dim);
                gPane.getRowConstraints().get(row).setPercentHeight(100.0 / dim);

                // Establecer el tamaño del AnchorPane en función del tamaño de la celda
                aPane.setPrefWidth(cellWidth);
                aPane.setPrefHeight(cellHeight);
                gPane.setGridLinesVisible(false);
            }
        }

        juegoPane.getChildren().add(gPane);
    }

}

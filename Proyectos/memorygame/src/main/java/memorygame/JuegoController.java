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
import javafx.animation.PauseTransition;
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
    //ImageView imageViewPrimera;
    AnchorPane apanePrimera;
    @FXML
    public void initialize() {

        int dim = 4;
        initializeWithData(dim, 60);

    }

    public void initializeWithData(int dim, int currentCount) {
        juego = new Juego(dim);

        this.currentCount = 60;

        aciertos.setText(String.valueOf(juego.getAciertos()));
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Subtract 1 from currentCount
            if (this.currentCount > 0) {
                // Si no ha llegado a cero, decrementarlo
                this.currentCount--;

                // Update the Label
                segundos.setText(String.valueOf(this.currentCount));
            } else {
                // Si ha llegado a cero, detener el temporizador
                timerTimeline.stop();
            }
        }));

        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        // timerTimeline.setCycleCount(0);
        timerTimeline.play();

        GridPane gPane = new GridPane();
        gPane.setOnMouseClicked(event -> {
            System.out.println("Clic detectado en el GridPane.");
        });
        paneGeneral.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Obtener la referencia al escenario
                Stage stage = (Stage) paneGeneral.getScene().getWindow();

                // Establecer el escenario en estado maximizado
                stage.setMaximized(true);

                // Bind the width and height of the AnchorPane to the width and height of the
                // Scene
                aciertos.setFont(new Font(20));
                segundos.setFont(new Font(20));
                aciertos.setMinWidth(200);

                segundos.setMinWidth(200);
                paneGeneral.prefWidthProperty().bind(newScene.widthProperty());
                paneGeneral.prefHeightProperty().bind(newScene.heightProperty());
                juegoPane.prefWidthProperty().bind(newScene.widthProperty());
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
                //ImageView imageView = new ImageView();
                // Crear un nuevo AnchorPane para cada celda y establecer la imagen de fondo
                AnchorPane aPane = new AnchorPane();
                gestionarCarta("/memorygame/imagenes/bocaabajo.png", aPane);

                aPane.setOnMouseClicked(event -> {
                    int clickedRow = GridPane.getRowIndex(aPane);
                    int clickedColumn = GridPane.getColumnIndex(aPane);
                    boolean res;
                    if (juego.esPrimera()) {
                        res = juego.levantarCarta(clickedRow, clickedColumn);
                        apanePrimera = aPane;
                        // TODO generar getter dado una fila y una columna que nos diga que carta
                        // (numero estñá ahí.)
                        // mostrarTablero();
                        System.out.println("Bocaarriba primera carta");
                        gestionarCarta(
                                "/memorygame/imagenes/bocaarriba" + juego.getCarta(clickedRow, clickedColumn) + ".png",
                                aPane);
                    } else {
                        if (juego.getPRimeraFila() != clickedRow || juego.getPRimeraColumna() != clickedColumn) {
                            res = juego.levantarCarta(clickedRow, clickedColumn);
                            System.out.println("Bocaarriba segunda carta");
                            gestionarCarta( "/memorygame/imagenes/bocaarriba"
                                        + juego.getCarta(clickedRow, clickedColumn) + ".png", aPane);
                            if (res) {
                                // mensaje
                              
                                
                                System.out.println("Son pareja");

                                // juego.acertado();
                                aciertos.setText(String.valueOf(juego.getAciertos()));
                                System.out.println(juego.getAciertos());
                            } else {
                                juego.bajarCarta(clickedRow, clickedColumn);
                                juego.bajarCarta(juego.getPRimeraFila(), juego.getPRimeraColumna());

                                System.out.println("No son pareja");
                                // mensaje no pareja
                                /*
                                 * gestionarCarta(imageViewPrimera, "/memorygame/imagenes/bocaabajo.png",
                                 * aPane);
                                 * gestionarCarta(imageView, "/memorygame/imagenes/bocaabajo.png", aPane);
                                 */
                                // TODO obtener el imageView de la primera carta clickada, para eso tienes que
                                // tener en cuenta que en el juego tienes guardado la fila y columan de la
                                // primera carta
                                // gestionarCarta(imageView, "/memorygame/imagenes/bocaabajo.png", aPane);
                                PauseTransition pause = new PauseTransition(Duration.seconds(0.5)); // Ajusta el tiempo
                                                                                                    // según sea
                                                                                                    // necesario
                                pause.setOnFinished(evennt -> {
                                    // Voltear las cartas nuevamente después del retraso
                                    try {
                                        System.out.println("Bocaabajo primera carta");
                                        System.out.println("Bocaabajo segunda carta");
                                        gestionarCarta("/memorygame/imagenes/bocaabajo.png", aPane);
                                        gestionarCarta("/memorygame/imagenes/bocaabajo.png", apanePrimera);
                                        
                                        // setImageFile(nodoPrimeraCarta, "/assets/bocabajo.png");
                                    } catch (Exception e) {
                                        System.out.println("Se ha producido una excepción al voltear las cartas: "
                                                + e.getMessage());
                                        e.printStackTrace();
                                    }
                                });
                                pause.play();

                            }
                        } else {
                            juego.bajarCarta(clickedRow, clickedColumn);
                            gestionarCarta("/memorygame/imagenes/bocaabajo.png", aPane);
                        }

                        // TODO generar getter dado una fila y una columna que nos diga que carta
                        // (numero estñá ahí.)

                        // mostrarTablero();
                        // gestionarCarta(imageView, "/memorygame/imagenes/bocaarriba.png", aPane);

                        juego.resetearJugada();
                        // imageViewPrimera = null;
                        if (juego.isGameOver()) {
                            timerTimeline.stop();
                            try {
                                App.setRoot("gameover");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            System.out.println("Game over");
                        }

                    }
                    System.out.println("Tiene el valor de:" + juego.getCarta(clickedRow, clickedColumn));

                    // int[][] hola = juego.getCartas();

                    // tocado=true;

                    // Label label=new Label(hola[clickedRow][clickedColumn]);
                    System.out.println("Clic detectado en la celda: [" + clickedRow + ", " + clickedColumn + "]");

                });

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

    private void gestionarCarta(String ruta, AnchorPane aPane) {
        Image imagee = null;
        ImageView imageView=new ImageView();
        try {
            imagee = new Image(
                    getClass().getResource(ruta).toURI().toString());
            imageView.setImage(imagee);

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        aPane.setBackground(new Background(new BackgroundImage(imagee,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

}
package cr.ac.una.proyectospider;

import cr.ac.una.proyectospider.util.CustomCursor;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.FontDepartment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.beans.value.ChangeListener;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Solitario Spider");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/LogoSpiderIcon.png")));

        // Música de fondo
        if (mediaPlayer == null) {
            String musicPath = getClass().getResource("/cr/ac/una/proyectospider/resources/Night Drive.mp3").toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop
            mediaPlayer.setVolume(0.05); // Volumen bajo
            mediaPlayer.play();
        }

        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain("IntroView");


        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setMinWidth(1450);
        stage.setMinHeight(800);
        FontDepartment.loadFonts();

        stage.show();

        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            if (scene == null) return;
            Pane rootPane = (Pane) scene.getRoot();

            Image rawCursor = new Image(
                    getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/CursorSpider.png"),
                    32, 32, true, true
            );
            ImageView ivCursor = new ImageView(rawCursor);
            ivCursor.setFitWidth(32);
            ivCursor.setFitHeight(32);
            Pane cursorPane = new Pane(ivCursor);

            // Mostrar el cursor nativo solo cuando el mouse está sobre la ventana
            scene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_ENTERED, e -> {
                scene.setCursor(javafx.scene.Cursor.NONE);
            });
            scene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_EXITED, e -> {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);
            });
            // Inicialmente, ocultar el cursor nativo
            scene.setCursor(javafx.scene.Cursor.NONE);

            new CustomCursor(scene, rootPane, cursorPane, 10, 8);
        });
    }

    /**
     * Recorre recursivamente los nodos y agrega listeners para restaurar el cursor custom
     * al salir de TextField y PasswordField (hover y focus).
     */
    private void addCursorListenersToTextInputs(Node node, ImageCursor customCursor, Scene escena) {
        if (node instanceof TextField || node instanceof PasswordField) {
            ChangeListener<Boolean> hoverListener = (obs, oldVal, newVal) -> {
                if (!newVal) escena.setCursor(customCursor);
            };
            node.hoverProperty().addListener(hoverListener);
            ChangeListener<Boolean> focusListener = (obs, oldVal, newVal) -> {
                if (!newVal) escena.setCursor(customCursor);
            };
            node.focusedProperty().addListener(focusListener);
        }
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                addCursorListenersToTextInputs(child, customCursor, escena);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}




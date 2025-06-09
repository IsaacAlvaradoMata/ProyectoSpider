package cr.ac.una.proyectospider;

import cr.ac.una.proyectospider.util.CustomCursor;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.FontDepartment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Solitario Spider");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/LogoSpiderIcon.png")));

        if (mediaPlayer == null) {
            String musicPath = getClass().getResource("/cr/ac/una/proyectospider/resources/Night Drive.mp3").toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.08);
            mediaPlayer.play();
        }

        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain("IntroView");


        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setMinWidth(1450);
        stage.setMinHeight(800);
        FontDepartment.loadFonts();

        stage.show();

        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            if (scene == null) return;
            Pane rootPane = (Pane) scene.getRoot();

            String cursorGifPath = "/cr/ac/una/proyectospider/resources/CustomSpider.gif";
            java.net.URL gifUrl = getClass().getResource(cursorGifPath);
            if (gifUrl == null) {
                System.err.println("[ERROR] No se encontró el archivo de cursor GIF: " + cursorGifPath);
                return;
            }
            Image rawCursor = new Image(gifUrl.toExternalForm(), 95, 95, true, true);
            ImageView ivCursor = new ImageView(rawCursor);
            ivCursor.setFitWidth(95);
            ivCursor.setFitHeight(95);
            Pane cursorPane = new Pane(ivCursor);

            scene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_ENTERED, e -> {
                scene.setCursor(javafx.scene.Cursor.NONE);
            });
            scene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_EXITED, e -> {
                scene.setCursor(javafx.scene.Cursor.DEFAULT);
            });
            scene.setCursor(javafx.scene.Cursor.NONE);

            new CustomCursor(scene, rootPane, cursorPane, 40, 36);
        });
    }

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
}


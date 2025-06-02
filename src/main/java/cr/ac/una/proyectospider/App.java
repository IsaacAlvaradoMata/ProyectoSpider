package cr.ac.una.proyectospider;

import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.FontDepartment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

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
            mediaPlayer.setVolume(0.3); // Volumen bajo
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}

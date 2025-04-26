package cr.ac.una.proyectospider;

import cr.ac.una.proyectospider.util.FlowController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {


        stage.setTitle("Spider");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Spider.png")));
        stage.setScene(scene);
        stage.setMinWidth(1350);
        stage.setMinHeight(800);

        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain("IntroView");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

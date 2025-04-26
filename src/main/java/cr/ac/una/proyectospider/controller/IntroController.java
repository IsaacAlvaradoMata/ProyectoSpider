package cr.ac.una.proyectospider.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class IntroController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private ImageView imgLogoSpider;
    @FXML
    private Label lblTitulo;
    @FXML
    private VBox vboxLogo;
    @FXML
    private VBox vboxTitle;
    @FXML
    private StackPane spBackground;
    @FXML
    private ImageView imgBackground;
    @FXML
    private VBox vboxTitle2;
    @FXML
    private Label lblTitulo2;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgBackground.fitWidthProperty().bind(root.widthProperty());
        imgBackground.fitHeightProperty().bind(root.heightProperty());
        imgBackground.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/BackgroundIntro.gif")));
        imgBackground.setOpacity(0.4);
        imgBackground.setSmooth(true);
        vboxLogo.setOpacity(0);
        vboxTitle.setOpacity(0);
        vboxTitle.setTranslateY(0);
        vboxTitle2.setOpacity(0);
        vboxTitle2.setTranslateY(0);

        Platform.runLater(() -> {
            AnimationDepartment.epicLogoAnimation(vboxLogo);
            AnimationDepartment.pulse(vboxLogo, 1.5);
            AnimationDepartment.titleSplitEntrance(vboxTitle, vboxTitle2, 2);
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.glitchTextWithFlicker(lblTitulo2);

        });

    }

    @Override
    public void initialize() {

    }
}

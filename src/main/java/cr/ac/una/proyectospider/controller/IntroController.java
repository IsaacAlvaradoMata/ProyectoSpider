package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.SoundDepartment;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class IntroController extends Controller implements Initializable {

    @FXML
    public BorderPane Mainroot;
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
    @FXML
    private ImageView imgButtonComenzar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgBackground.fitWidthProperty().bind(Mainroot.widthProperty());
        imgBackground.fitHeightProperty().bind(Mainroot.heightProperty());
        imgBackground.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/BackgroundIntro.gif")));
        imgBackground.setOpacity(0.4);
        imgBackground.setSmooth(true);
        imgButtonComenzar.setDisable(true);

        vboxLogo.setOpacity(0);
        vboxTitle.setOpacity(0);
        vboxTitle.setTranslateY(0);
        vboxTitle2.setOpacity(0);
        vboxTitle2.setTranslateY(0);
        imgButtonComenzar.setOpacity(0);

        Platform.runLater(() -> {
            AnimationDepartment.epicLogoAnimation(vboxLogo);
            AnimationDepartment.pulse(vboxLogo, 1.5);
            AnimationDepartment.titleSplitEntrance(vboxTitle, vboxTitle2, 2);
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.glitchTextWithFlicker(lblTitulo2);
            AnimationDepartment.fadeIn(imgButtonComenzar, Duration.seconds(5.0));
            AnimationDepartment.animateNeonGlow(imgButtonComenzar);
            imgButtonComenzar.setDisable(false);

        });

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onMouseClickedButtonComenzar(MouseEvent event) {
        SoundDepartment.playClick();
        imgButtonComenzar.setDisable(true);
        AnimationDepartment.stopAllAnimations();
        AnimationDepartment.glitchFadeOut(spBackground, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("LoginView");
            LoginController controller = (LoginController) FlowController.getInstance().getController("LoginView");
            controller.RunLoginView();
            Platform.runLater(() -> imgButtonComenzar.setDisable(false));

        });
    }


}

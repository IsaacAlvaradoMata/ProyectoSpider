package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class LoginController extends Controller implements Initializable {

    @FXML
    public BorderPane root;
    @FXML
    public StackPane spBackgroundLogin;
    @FXML
    public ImageView imgBackgroundLogin;
    @FXML
    public StackPane spLoginInfo;
    @FXML
    public ImageView imgLoginInfo;
    @FXML
    public TextField txtfildLogin;
    @FXML
    public ImageView btnRegistrarJugador;
    @FXML
    public ImageView BtnIniciarSesion;
    @FXML
    public ImageView btnAcercaDe;
    @FXML
    public Label lblTitulo;
    @FXML
    public StackPane spTextFieldContainer;





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setOpacity(0);

        Platform.runLater(() -> {
            root.requestFocus();
            imgBackgroundLogin.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundLogin.fitHeightProperty().bind(root.getScene().heightProperty());
            imgBackgroundLogin.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/LoginBackground.gif")));
            imgBackgroundLogin.setPreserveRatio(false);
            imgBackgroundLogin.setSmooth(true);
            imgBackgroundLogin.setOpacity(0.4);
            spLoginInfo.setOpacity(0);
            lblTitulo.setOpacity(0);
            txtfildLogin.setOpacity(0);
            btnRegistrarJugador.setOpacity(0);
            BtnIniciarSesion.setOpacity(0);
            btnAcercaDe.setOpacity(0);

            Platform.runLater(() -> {
                AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            });

            AnimationDepartment.slideFromTop(spLoginInfo, Duration.seconds(1));
            spLoginInfo.setTranslateY(0);
            AnimationDepartment.subtleBounce(spLoginInfo, 2);
            AnimationDepartment.fadeIn(lblTitulo,Duration.seconds(2.2));
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.slideInFromBottom(txtfildLogin, 3);
            AnimationDepartment.animateNeonBorderWithLED(spTextFieldContainer, txtfildLogin, 3.5); // Delay de 1.5 segundos
            double sceneHeight = root.getHeight();
            AnimationDepartment.slideUpWithEpicBounceClean(btnRegistrarJugador ,Duration.seconds(3.2) , sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(BtnIniciarSesion ,Duration.seconds(3.4) , sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnAcercaDe ,Duration.seconds(3.6) , sceneHeight);
            AnimationDepartment.animateNeonGlow(BtnIniciarSesion);
            AnimationDepartment.animateNeonGlow(btnAcercaDe);
            AnimationDepartment.animateNeonGlow(btnRegistrarJugador);


        });
    }




    @Override
    public void initialize() {

    }

    @FXML
    private void onMouseClickedbtnRegistrarJugador(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnIniciarSesion(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnAcercaDe(MouseEvent event) {
        AnimationDepartment.glitchFadeOut(spBackgroundLogin, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("InfoView");
        });
    }


}

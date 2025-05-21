package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends Controller implements Initializable {

    @FXML
    private ImageView btnGuardarySalir;

    @FXML
    private ImageView btnPista;

    @FXML
    private HBox hboxPilas;

    @FXML
    private HBox hboxTablero;

    @FXML
    private HBox hboxTableroSuperior;

    @FXML
    private ImageView imgBackgroundGame;

    @FXML
    private ImageView imgBackgroundTablero;

    @FXML
    private ImageView imgMazo;

    @FXML
    private Label lblMovimientos;

    @FXML
    private Label lblNombreJugador;

    @FXML
    private Label lblPuntaje;

    @FXML
    private Label lblTiempo;

    @FXML
    private Label lblTitullo;

    @FXML
    private BorderPane root;

    @FXML
    private StackPane spGamebackground;

    @FXML
    private StackPane spTableroBackground;



    @FXML
    void oMouseClickedbtnGuardarySalir(MouseEvent event) {

        btnGuardarySalir.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spGamebackground, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnGuardarySalir.setDisable(false));

        });

    }

    @FXML
    void oMouseClickedbtnPista(MouseEvent event) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void RunGameView(){


    }


    public void ResetGameView(){



    }
}

package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.model.JugadorDto;
import cr.ac.una.proyectospider.service.JugadorService;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class LoginController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane spBackgroundLogin;
    @FXML
    private ImageView imgBackgroundLogin;
    @FXML
    private StackPane spLoginInfo;
    @FXML
    private ImageView imgLoginInfo;
    @FXML
    private TextField txtfildLogin;
    @FXML
    private ImageView btnRegistrarJugador;
    @FXML
    private ImageView BtnIniciarSesion;
    @FXML
    private ImageView btnAcercaDe;
    @FXML
    private Label lblTitulo;
    @FXML
    private StackPane spTextFieldContainer;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void RunLoginView() {
        ResetLoginView();
        System.out.println("Run Login View");

        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundLogin.getChildren().contains(imgBackgroundLogin)) {
            spBackgroundLogin.getChildren().add(0, imgBackgroundLogin);
        } else {
            spBackgroundLogin.getChildren().remove(imgBackgroundLogin);
            spBackgroundLogin.getChildren().add(0, imgBackgroundLogin);
        }

        // 🔁 Re-bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundLogin.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundLogin.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundLogin.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/LoginBackground.gif")));
        imgBackgroundLogin.setPreserveRatio(false);
        imgBackgroundLogin.setSmooth(true);
        imgBackgroundLogin.setOpacity(0.5);
        imgBackgroundLogin.setVisible(true);

        Platform.runLater(() -> {
            root.requestFocus();
            root.setVisible(true);
            root.setOpacity(1); // 🔓 Forzar visibilidad total

            root.applyCss();
            root.layout(); // ⬅️ Refresca el layout completamente

            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");

            AnimationDepartment.slideFromTop(spLoginInfo, Duration.seconds(1));
            spLoginInfo.setTranslateY(0);
            AnimationDepartment.subtleBounce(spLoginInfo, 2);
            AnimationDepartment.fadeIn(lblTitulo, Duration.seconds(2.2));
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.slideInFromBottom(txtfildLogin, 3);
            AnimationDepartment.animateNeonBorderWithLED(spTextFieldContainer, txtfildLogin, 3.5);
            AnimationDepartment.slideUpWithEpicBounceClean(btnRegistrarJugador, Duration.seconds(3.2), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(BtnIniciarSesion, Duration.seconds(3.4), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnAcercaDe, Duration.seconds(3.6), sceneHeight);
            AnimationDepartment.animateNeonGlow(BtnIniciarSesion);
            AnimationDepartment.animateNeonGlow(btnAcercaDe);
            AnimationDepartment.animateNeonGlow(btnRegistrarJugador);
            System.out.println("Scene bounds: " + root.getScene().getWidth() + "x" + root.getScene().getHeight());

        });

        System.out.println("Run final");
    }


    public void ResetLoginView() {
        // Reset visual
        System.out.println("Reset Login View");
        root.setOpacity(0);

        imgBackgroundLogin.setOpacity(0.5);
        imgBackgroundLogin.setTranslateX(0);
        imgBackgroundLogin.setTranslateY(0);
        imgBackgroundLogin.setEffect(null);
        imgBackgroundLogin.setVisible(true);

        spLoginInfo.setOpacity(0);
        spLoginInfo.setTranslateY(0);

        lblTitulo.setOpacity(0);
        lblTitulo.setTranslateX(0);
        lblTitulo.setTranslateY(0);
        lblTitulo.setScaleX(1.0);
        lblTitulo.setScaleY(1.0);
        lblTitulo.setTextFill(Color.web("#ffc107")); // Restaurar color original

        txtfildLogin.setOpacity(0);
        txtfildLogin.setTranslateY(0);
        txtfildLogin.setText("");

        btnRegistrarJugador.setOpacity(0);
        btnRegistrarJugador.setTranslateY(0);

        BtnIniciarSesion.setOpacity(0);
        BtnIniciarSesion.setTranslateY(0);

        btnAcercaDe.setOpacity(0);
        btnAcercaDe.setTranslateY(0);
        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundLogin.setEffect(null);
        spBackgroundLogin.setOpacity(1);
        spBackgroundLogin.setVisible(true);

    }


    @Override
    public void initialize() {

    }

    @FXML
    private void onMouseClickedbtnRegistrarJugador(MouseEvent event) {
        String nombre = txtfildLogin.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("⚠️ Debes ingresar un nombre de usuario.");
            return;
        }

        JugadorService jugadorService = new JugadorService();
        JugadorDto nuevoJugador = jugadorService.registrarJugador(nombre.trim());

        if (nuevoJugador != null) {
            System.out.println("✅ Jugador registrado con ID: " + nuevoJugador.idJugadorProperty().get());
            // Opcional: guardar en AppContext
            // AppContext.getInstance().set("jugadorActivo", nuevoJugador);
        } else {
            System.err.println("❌ No se pudo registrar el jugador.");
        }
    }


    @FXML
    private void onMouseClickedbtnIniciarSesion(MouseEvent event) {
        BtnIniciarSesion.setDisable(true);
        AnimationDepartment.stopAllAnimations();
        AnimationDepartment.glitchFadeOut(spBackgroundLogin, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> BtnIniciarSesion.setDisable(false));

        });
    }

    @FXML
    private void onMouseClickedbtnAcercaDe(MouseEvent event) {
        btnAcercaDe.setDisable(true);
        AnimationDepartment.stopAllAnimations();
        AnimationDepartment.glitchFadeOut(spBackgroundLogin, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("InfoView");
            InfoController controller = (InfoController) FlowController.getInstance().getController("InfoView");
            controller.RunInfoView();
            Platform.runLater(() -> btnAcercaDe.setDisable(false));
        });
    }


}

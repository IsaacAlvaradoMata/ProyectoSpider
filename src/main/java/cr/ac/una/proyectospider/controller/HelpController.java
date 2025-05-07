/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class HelpController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane spBackgroundHelp;
    @FXML
    private ImageView imgBackgroundHelp;
    @FXML
    private Label lblAyuda;
    @FXML
    private ImageView btnObjetivo;
    @FXML
    private ImageView btnDificultades;
    @FXML
    private ImageView btnControles;
    @FXML
    private ImageView btnCondiciones;
    @FXML
    private ImageView btnExplicacion;
    @FXML
    private ImageView btnVolver;
    @FXML
    private TabPane tabpaneCentroDeInfo;
    @FXML
    private Tab tabOjetivoJuego;
    @FXML
    private Tab tabControles;
    @FXML
    private Tab tabDificultades;
    @FXML
    private Tab tabCondiciones;
    @FXML
    private Tab tabExplicacion;
    @FXML
    private Tab tabGeneral;
    @FXML
    private StackPane spHelpIcon1;
    @FXML
    private ImageView imgHelpIcon1;
    @FXML
    private StackPane spHelpIcon2;
    @FXML
    private ImageView imgHelpIcon2;
    @FXML
    private StackPane spObjetivo;
    @FXML
    private Label lblIObjetivo;
    @FXML
    private StackPane spDificultades;
    @FXML
    private Label lblDificultades;
    @FXML
    private StackPane spControles;
    @FXML
    private Label lblControles;
    @FXML
    private StackPane spCondiciones;
    @FXML
    private Label lblCondiciones;
    @FXML
    private StackPane spExplicacion;
    @FXML
    private Label lblExplicacion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {

    }

    public void RunHelpView() {
        ResetHelpView();
        System.out.println("Run Help View");
        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundHelp.getChildren().contains(imgBackgroundHelp)) {
            spBackgroundHelp.getChildren().add(0, imgBackgroundHelp);
        } else {
            spBackgroundHelp.getChildren().remove(imgBackgroundHelp);
            spBackgroundHelp.getChildren().add(0, imgBackgroundHelp);
        }

        // 🔁 Re-bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundHelp.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundHelp.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundHelp.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/option18.gif")));
        imgBackgroundHelp.setPreserveRatio(false);
        imgBackgroundHelp.setSmooth(true);
        imgBackgroundHelp.setOpacity(0.5);
        imgBackgroundHelp.setVisible(true);

        Platform.runLater(() -> {
            root.setOpacity(1);
            root.setVisible(true);
            root.requestFocus();
            root.applyCss();
            root.layout();

//            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");

            PauseTransition t1 = new PauseTransition(Duration.seconds(1));
            t1.setOnFinished(e -> {
                AnimationDepartment.slideFromTop(lblAyuda, Duration.ZERO);
                AnimationDepartment.glitchTextWithFlicker(lblAyuda);
                AnimationDepartment.fadeIn(imgHelpIcon1, Duration.ZERO);
                AnimationDepartment.fadeIn(imgHelpIcon2, Duration.ZERO);

            });
            t1.play();


            PauseTransition t2 = new PauseTransition(Duration.seconds(1.2));
            t2.setOnFinished(e -> {
                AnimationDepartment.slideFromLeft(lblIObjetivo, Duration.seconds(1.2));
                AnimationDepartment.slideFromLeft(lblDificultades, Duration.seconds(1.4));
                AnimationDepartment.slideFromLeft(lblControles, Duration.seconds(1.6));
                AnimationDepartment.slideFromLeft(lblCondiciones, Duration.seconds(1.8));
                AnimationDepartment.slideFromLeft(lblExplicacion, Duration.seconds(2));
                AnimationDepartment.fadeIn(btnVolver, Duration.seconds(2.7));
                AnimationDepartment.animateNeonGlow(btnVolver);

                setupHoverEffect(lblIObjetivo, btnObjetivo);
                setupHoverEffect(lblControles, btnControles );
                setupHoverEffect(lblDificultades, btnDificultades);
                setupHoverEffect(lblCondiciones, btnCondiciones);
                setupHoverEffect(lblExplicacion, btnExplicacion);

            });
            t2.play();

            PauseTransition t3 = new PauseTransition(Duration.seconds(2.5));
            t3.setOnFinished(e -> {
                AnimationDepartment.animateNeonGlow(imgHelpIcon1);
                AnimationDepartment.slideLoopWithScale(imgHelpIcon1, -400, 1.8);

                AnimationDepartment.animateNeonGlow(imgHelpIcon2);
                AnimationDepartment.slideLoopWithScale(imgHelpIcon2, 400, 1.8);

            });
            t3.play();

            PauseTransition t4 = new PauseTransition(Duration.seconds(5.5));
            t4.setOnFinished(e -> {

            });
            t4.play();

        });


    }


    public void ResetHelpView() {
        System.out.println("🔁 Reset Help View");

        AnimationDepartment.stopAllAnimations();

        root.setOpacity(0);
        root.setVisible(true); // Para asegurar que esté presente en el DOM

        imgBackgroundHelp.setOpacity(0.7);
        imgBackgroundHelp.setTranslateX(0);
        imgBackgroundHelp.setTranslateY(0);
        imgBackgroundHelp.setEffect(null);

        btnObjetivo.setOpacity(0);
        btnDificultades.setOpacity(0);
        btnControles.setOpacity(0);
        btnCondiciones.setOpacity(0);
        btnExplicacion.setOpacity(0);
        btnVolver.setOpacity(0);

        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        imgBackgroundHelp.setEffect(null);
        imgBackgroundHelp.setOpacity(1);
        imgBackgroundHelp.setVisible(true);

        lblIObjetivo.setOpacity(0);
        lblDificultades.setOpacity(0);
        lblControles.setOpacity(0);
        lblCondiciones.setOpacity(0);
        lblExplicacion.setOpacity(0);
        lblAyuda.setOpacity(0);

        imgHelpIcon1.setOpacity(0);
        imgHelpIcon2.setOpacity(0);

//        spHelpIcon1.setOpacity(0);
//        spHelpIcon2.setOpacity(0);


    }

    @FXML
    private void OnMouseClickedBtnObjetivo(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedBtnControles(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedBtnDificultades(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedBtnCondiciones(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedBtnExplicacion(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedBtnBolver(MouseEvent event) {
        AnimationDepartment.stopAllAnimations();
        btnVolver.setDisable(true);

        AnimationDepartment.glitchFadeOut(imgBackgroundHelp, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnVolver.setDisable(false));

        });
    }

    private void setupHoverEffect(Label label, ImageView imageFrame) {
        imageFrame.setVisible(false); // Oculto por defecto
        imageFrame.setOpacity(1);
        label.setOnMouseEntered(e -> {
            imageFrame.setVisible(true); // Solo muestra la imagen
        });

        label.setOnMouseExited(e -> {
            imageFrame.setVisible(false); // Oculta la imagen
        });
    }

}

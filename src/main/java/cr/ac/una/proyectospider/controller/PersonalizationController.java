/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class PersonalizationController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane spBackgroundPersonalization;
    @FXML
    private ImageView imgBackgroundPersonalization;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblJugador;
    @FXML
    private ImageView imgPrevistaFondo;
    @FXML
    private ImageView imgCartasPrevista;
    @FXML
    private ImageView btnGuardarCambios;
    @FXML
    private Label lblEstilosCartas;
    @FXML
    private ImageView imgCartasCyberpunk;
    @FXML
    private Label lblCyberpunk;
    @FXML
    private RadioButton rbtnCyberpunk;
    @FXML
    private ImageView imgCartasClasicas;
    @FXML
    private Label lblClasicas;
    @FXML
    private RadioButton rbtnClasicas;
    @FXML
    private Label lblSeleccionFondo;
    @FXML
    private ImageView btnFlechaIzquierda;
    @FXML
    private ImageView btnAgregarOtro;
    @FXML
    private ImageView btnFlechaDerecha;
    @FXML
    private ImageView btnVolver;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    public void RunPersonalizationView() {
        ResetPersonalizationView();
        System.out.println("Run Login View");

        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundPersonalization.getChildren().contains(imgBackgroundPersonalization)) {
            spBackgroundPersonalization.getChildren().add(0, imgBackgroundPersonalization);
        } else {
            spBackgroundPersonalization.getChildren().remove(imgBackgroundPersonalization);
            spBackgroundPersonalization.getChildren().add(0, imgBackgroundPersonalization);
        }

        // 🔁 Re-bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundPersonalization.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundPersonalization.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundPersonalization.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/option6.gif")));
        imgBackgroundPersonalization.setPreserveRatio(false);
        imgBackgroundPersonalization.setSmooth(true);
        imgBackgroundPersonalization.setOpacity(0.3);
        imgBackgroundPersonalization.setVisible(true);

        Platform.runLater(() -> {
            root.requestFocus();
            root.setVisible(true);
            root.setOpacity(1); // 🔓 Forzar visibilidad total

            root.applyCss();
            root.layout(); // ⬅️ Refresca el layout completamente

            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");



        });

        System.out.println("Run final");

    }

    public void ResetPersonalizationView() {
        // Reset visual
        System.out.println("Reset Login View");
        root.setOpacity(0);

        imgBackgroundPersonalization.setOpacity(0.5);
        imgBackgroundPersonalization.setTranslateX(0);
        imgBackgroundPersonalization.setTranslateY(0);
        imgBackgroundPersonalization.setEffect(null);
        imgBackgroundPersonalization.setVisible(true);

        spBackgroundPersonalization.setOpacity(0);
        spBackgroundPersonalization.setTranslateY(0);

//        lblTitulo.setOpacity(0);
//        lblTitulo.setTranslateX(0);
//        lblTitulo.setTranslateY(0);
//        lblTitulo.setScaleX(1.0);
//        lblTitulo.setScaleY(1.0);


        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundPersonalization.setEffect(null);
        spBackgroundPersonalization.setOpacity(1);
        spBackgroundPersonalization.setVisible(true);

    }

    @FXML
    private void onMouseClickedbtnGuardarCambios(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedbtnFlechaIzquierda(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnAgregarOtro(MouseEvent event) {
    }

    @FXML
    private void OnMouseClickedbtnFlechaDerecha(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnVolver(MouseEvent event) {
        AnimationDepartment.stopAllAnimations();
        btnVolver.setDisable(true);

        AnimationDepartment.glitchFadeOut(spBackgroundPersonalization, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnVolver.setDisable(false));

        });
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.model.PartidaMock;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class MenuController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane spBackgroundMenu;
    @FXML
    private ImageView imgBackgroundMenu;
    @FXML
    private Label lblJugadorRegistrado;
    @FXML
    private Label lblJugadorRegistradoDinamico;
    @FXML
    private Label lblPuntajeAcomulado;
    @FXML
    private Label lblPuntajeAcomuladoDinamico;
    @FXML
    private Label lblTotalPartidasGanadas;
    @FXML
    private Label lblTotalPartidasGanadasDinamico;
    @FXML
    private Label lblTituloMenu;
    @FXML
    private StackPane spMenuLogo;
    @FXML
    private ImageView imgMenuLogo;
    @FXML
    private ImageView btnNuevaPartida;
    @FXML
    private ImageView btnContinuarPartida;
    @FXML
    private ImageView btnPersonalizacion;
    @FXML
    private ImageView btnVerPuntajes;
    @FXML
    private ImageView btnAyuda;
    @FXML
    private ImageView btnCerrarSesion;
    @FXML
    private Label lblFondoActual;
    @FXML
    private StackPane spFondoActual;
    @FXML
    private ImageView imgFondoActual;
    @FXML
    private Label lblCartasActual;
    @FXML
    private StackPane spCartasActual;
    @FXML
    private ImageView imgCartasActual;
    @FXML
    private Label lblPartidasPausadas;
    @FXML
    private TableView<PartidaMock> tblviewPartidasPausadas;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {

    }

    public void RunMenuView() {
        ResetMenuView();
        populateTableView();
        System.out.println("Run Menu View");

        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundMenu.getChildren().contains(imgBackgroundMenu)) {
            spBackgroundMenu.getChildren().add(0, imgBackgroundMenu);
        } else {
            spBackgroundMenu.getChildren().remove(imgBackgroundMenu);
            spBackgroundMenu.getChildren().add(0, imgBackgroundMenu);
        }

        // 🔁 Re-bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundMenu.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundMenu.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundMenu.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/MenuBackground.gif")));
        imgBackgroundMenu.setPreserveRatio(false);
        imgBackgroundMenu.setSmooth(true);
        imgBackgroundMenu.setOpacity(0.4);
        imgBackgroundMenu.setVisible(true);

        Platform.runLater(() -> {
            root.requestFocus();
            root.setVisible(true);
            root.setOpacity(1); // 🔓 Forzar visibilidad total

            root.applyCss();
            root.layout(); // ⬅️ Refresca el layout completamente

            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");

            PauseTransition t1 = new PauseTransition(Duration.seconds(1));
            t1.setOnFinished(e -> {
                AnimationDepartment.slideFromTop(lblTituloMenu, Duration.ZERO);
                AnimationDepartment.glitchTextWithFlicker(lblTituloMenu);
            });
            t1.play();

            PauseTransition t2 = new PauseTransition(Duration.seconds(2.5));
            t2.setOnFinished(e -> {
                AnimationDepartment.fadeIn(spMenuLogo, Duration.ZERO);
                AnimationDepartment.pulse(spMenuLogo, 2.5);
            });
            t2.play();

            PauseTransition t3 = new PauseTransition(Duration.seconds(4));
            t3.setOnFinished(e -> {
                AnimationDepartment.slideFromLeft(lblJugadorRegistrado, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPuntajeAcomulado, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblTotalPartidasGanadas, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPartidasPausadas, Duration.ZERO);
                AnimationDepartment.slideFromRight(lblFondoActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(lblCartasActual, Duration.ZERO);

                AnimationDepartment.glitchTextWithFlicker(lblJugadorRegistrado);
                AnimationDepartment.glitchTextWithFlicker(lblPuntajeAcomulado);
                AnimationDepartment.glitchTextWithFlicker(lblTotalPartidasGanadas);
                AnimationDepartment.glitchTextWithFlicker(lblPartidasPausadas);
                AnimationDepartment.glitchTextWithFlicker(lblFondoActual);
                AnimationDepartment.glitchTextWithFlicker(lblCartasActual);

            });
            t3.play();

            PauseTransition t4 = new PauseTransition(Duration.seconds(5.5));
            t4.setOnFinished(e -> {
                AnimationDepartment.slideFromLeft(lblJugadorRegistradoDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPuntajeAcomuladoDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblTotalPartidasGanadasDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(tblviewPartidasPausadas, Duration.ZERO);
                AnimationDepartment.slideFromRight(spFondoActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(spCartasActual, Duration.ZERO);


            });
            t4.play();


            PauseTransition t5 = new PauseTransition(Duration.seconds(6.5));
            t5.setOnFinished(e -> {
            AnimationDepartment.slideUpWithEpicBounceClean(btnNuevaPartida, Duration.seconds(0), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnContinuarPartida, Duration.seconds(0.2), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnPersonalizacion, Duration.seconds(0.4), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnVerPuntajes, Duration.seconds(0.6), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnAyuda, Duration.seconds(0.8), sceneHeight);
            AnimationDepartment.slideUpWithEpicBounceClean(btnCerrarSesion, Duration.seconds(1), sceneHeight);
            AnimationDepartment.animateNeonGlow(btnNuevaPartida);
            AnimationDepartment.animateNeonGlow(btnContinuarPartida);
            AnimationDepartment.animateNeonGlow(btnPersonalizacion);
            AnimationDepartment.animateNeonGlow(btnVerPuntajes);
            AnimationDepartment.animateNeonGlow(btnAyuda);
            AnimationDepartment.animateNeonGlow(btnCerrarSesion);
            });
            t5.play();

            ObservableList<PartidaMock> partidas = FXCollections.observableArrayList();
            for (int i = 1; i <= 15; i++) {
                partidas.add(new PartidaMock(
                        "2025-05-" + (i < 10 ? "0" + i : i),
                        (Math.random() * 1000 + 500) + " pts",
                        (10 + i) + " min"
                ));
            }
            tblviewPartidasPausadas.setItems(partidas);


        });

    }


    public void ResetMenuView() {
        System.out.println("🔁 Reset Menu View");

        AnimationDepartment.stopAllAnimations();
        root.setOpacity(0);
//        root.setVisible(true);

        imgBackgroundMenu.setOpacity(0.7);
        imgBackgroundMenu.setTranslateX(0);
        imgBackgroundMenu.setTranslateY(0);
        imgBackgroundMenu.setEffect(null);

        lblTituloMenu.setOpacity(0);
        lblPartidasPausadas.setOpacity(0);
        lblJugadorRegistrado.setOpacity(0);
        lblJugadorRegistradoDinamico.setOpacity(0);
        lblPuntajeAcomulado.setOpacity(0);
        lblPuntajeAcomuladoDinamico.setOpacity(0);
        lblTotalPartidasGanadas.setOpacity(0);
        lblTotalPartidasGanadasDinamico.setOpacity(0);
        lblFondoActual.setOpacity(0);
        lblCartasActual.setOpacity(0);
        spMenuLogo.setOpacity(0);
        spFondoActual.setOpacity(0);
        spCartasActual.setOpacity(0);

        btnNuevaPartida.setOpacity(0);
        btnNuevaPartida.setTranslateY(0);

        btnContinuarPartida.setOpacity(0);
        btnContinuarPartida.setTranslateY(0);

        btnPersonalizacion.setOpacity(0);
        btnPersonalizacion.setTranslateY(0);

        btnVerPuntajes.setOpacity(0);
        btnVerPuntajes.setTranslateY(0);

        btnAyuda.setOpacity(0);
        btnAyuda.setTranslateY(0);

        btnCerrarSesion.setOpacity(0);
        btnCerrarSesion.setTranslateY(0);

        tblviewPartidasPausadas.setOpacity(0);
        tblviewPartidasPausadas.setTranslateY(0);

        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundMenu.setEffect(null);
        spBackgroundMenu.setOpacity(1);
        spBackgroundMenu.setVisible(true);



    }


    @FXML
    private void onMouseClickedbtnNuevaPartida(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnContinuarPartida(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnPersonalizacion(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnVerPuntajes(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnAyuda(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnCerrarSesion(MouseEvent event) {
        btnCerrarSesion.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spBackgroundMenu, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("LoginView");
            LoginController controller = (LoginController) FlowController.getInstance().getController("LoginView");
            controller.RunLoginView();
            Platform.runLater(() -> btnCerrarSesion.setDisable(false));

        });
    }

    private void populateTableView(){
        TableColumn<PartidaMock, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha()));

        TableColumn<PartidaMock, String> colPuntaje = new TableColumn<>("Puntaje");
        colPuntaje.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPuntaje()));

        TableColumn<PartidaMock, String> colTiempo = new TableColumn<>("Tiempo Jugado");
        colTiempo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTiempo()));

// Aplica estilos opcionales si tienes CSS
        tblviewPartidasPausadas.getColumns().setAll(colFecha, colPuntaje, colTiempo);


    }
}

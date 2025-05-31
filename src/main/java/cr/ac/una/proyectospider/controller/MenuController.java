/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.model.JugadorDto;
import cr.ac.una.proyectospider.model.JugadorRankingMock;
import cr.ac.una.proyectospider.model.PartidaDto;
import cr.ac.una.proyectospider.model.PartidaMock;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.AppContext;
import cr.ac.una.proyectospider.util.FlowController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    @FXML
    private Label lblDificultad;
    @FXML
    private RadioButton rbtnFacil;
    @FXML
    private RadioButton rbtnMedio;
    @FXML
    private RadioButton rbtnDificil;
    @FXML
    private Label lblFaciil;
    @FXML
    private Label lblMedio;
    @FXML
    private Label lblDificl;
    @FXML
    private HBox hboxDificultades;

    private ToggleGroup tgDificultad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tgDificultad = new ToggleGroup();
        rbtnFacil.setToggleGroup(tgDificultad);
        rbtnMedio.setToggleGroup(tgDificultad);
        rbtnDificil.setToggleGroup(tgDificultad);

        // Dejar “Fácil” seleccionado por defecto
        rbtnFacil.setSelected(true);
    }

    @Override
    public void initialize() {

    }

    public void RunMenuView() {
        ResetMenuView();
        JugadorDto jugador = (JugadorDto) AppContext.getInstance().get("jugadorActivo");
        if (jugador != null) {
            lblJugadorRegistradoDinamico.setText(jugador.nombreUsuarioProperty().get() != null ? jugador.nombreUsuarioProperty().get() : "-");
            lblPuntajeAcomuladoDinamico.setText(String.valueOf(jugador.puntosAcumuladosProperty().get()));
            lblTotalPartidasGanadasDinamico.setText(String.valueOf(jugador.partidasGanadasProperty().get()));
        }

        // Usando la misma constante
        Object fondoEnContext = AppContext.getInstance().get(AppContext.KEY_FONDO_SELECCIONADO);
        if (fondoEnContext instanceof Image) {
            imgFondoActual.setImage((Image) fondoEnContext);
        } else {
            imgFondoActual.setImage(
                    new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/DefaultBack3.png"))
            );
        }

        // 3) Cargar el estilo de cartas que guardamos en AppContext
        Object estiloEnContext = AppContext.getInstance().get(AppContext.KEY_ESTILO_CARTAS);
        if (estiloEnContext instanceof String) {
            String ruta = (String) estiloEnContext;
            imgCartasActual.setImage(new Image(getClass().getResourceAsStream(ruta)));
        } else {
            imgCartasActual.setImage(
                    new Image(getClass().getResourceAsStream(AppContext.RUTA_CARTAS_CYBERPUNK))
            );
        }

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

            PauseTransition t3 = new PauseTransition(Duration.seconds(3));
            t3.setOnFinished(e -> {
                AnimationDepartment.slideFromLeft(lblJugadorRegistrado, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPuntajeAcomulado, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblTotalPartidasGanadas, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPartidasPausadas, Duration.ZERO);
                AnimationDepartment.slideFromRight(lblFondoActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(lblCartasActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(lblDificultad, Duration.ZERO);

                AnimationDepartment.glitchTextWithFlicker(lblJugadorRegistrado);
                AnimationDepartment.glitchTextWithFlicker(lblPuntajeAcomulado);
                AnimationDepartment.glitchTextWithFlicker(lblTotalPartidasGanadas);
                AnimationDepartment.glitchTextWithFlicker(lblPartidasPausadas);
                AnimationDepartment.glitchTextWithFlicker(lblFondoActual);
                AnimationDepartment.glitchTextWithFlicker(lblCartasActual);
                AnimationDepartment.glitchTextWithFlicker(lblDificultad);
                AnimationDepartment.glitchTextWithFlicker(lblFaciil);
                AnimationDepartment.glitchTextWithFlicker(lblMedio);
                AnimationDepartment.glitchTextWithFlicker(lblDificl);

            });
            t3.play();

            PauseTransition t4 = new PauseTransition(Duration.seconds(3.5));
            t4.setOnFinished(e -> {
                AnimationDepartment.slideFromLeft(lblJugadorRegistradoDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblPuntajeAcomuladoDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(lblTotalPartidasGanadasDinamico, Duration.ZERO);
                AnimationDepartment.slideFromLeft(tblviewPartidasPausadas, Duration.ZERO);
                AnimationDepartment.slideFromRight(spFondoActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(spCartasActual, Duration.ZERO);
                AnimationDepartment.slideFromRight(hboxDificultades, Duration.ZERO);



            });
            t4.play();


            PauseTransition t5 = new PauseTransition(Duration.seconds(4));
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
                        ((int) (Math.random() * 9000 + 1000)) + "", // Genera un número entero entre 1000 y 10000
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
        lblDificultad.setOpacity(0);
        spMenuLogo.setOpacity(0);
        spFondoActual.setOpacity(0);
        spCartasActual.setOpacity(0);
        hboxDificultades.setOpacity(0);

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
        btnNuevaPartida.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spBackgroundMenu, Duration.seconds(1.1), () -> {
            // 1) Leer la dificultad seleccionada
            String dif;
            if (rbtnFacil.isSelected()) {
                dif = "FACIL";
            } else if (rbtnMedio.isSelected()) {
                dif = "MEDIA";
            } else {
                dif = "DIFICIL";
            }

            // 2) Crear DTO de nueva partida
            PartidaDto partidaDto = new PartidaDto();
            partidaDto.setEstado("EN_JUEGO");
            partidaDto.setFechaInicio(new Date());
            partidaDto.setDificultad(dif);

            JugadorDto jugadorActivo = (JugadorDto) AppContext.getInstance().get("jugadorActivo");
            partidaDto.setJugador(jugadorActivo);

            // 3) Navegar a GameView Y restablecer primerIngreso allí
            FlowController.getInstance().goView("GameView");
            GameController controladorJuego =
                    (GameController) FlowController.getInstance().getController("GameView");
            controladorJuego.primerIngreso = true;  // <— Solo aquí

            // 4) Finalmente, inicializar la vista de juego
            controladorJuego.RunGameView(partidaDto);

            Platform.runLater(() -> btnNuevaPartida.setDisable(false));
        });
    }





    @FXML
    private void onMouseClickedbtnContinuarPartida(MouseEvent event) {
    }

    @FXML
    private void onMouseClickedbtnPersonalizacion(MouseEvent event) {
        btnPersonalizacion.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spBackgroundMenu, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("PersonalizationView");
            PersonalizationController controller = (PersonalizationController) FlowController.getInstance().getController("PersonalizationView");
            controller.RunPersonalizationView();
            Platform.runLater(() -> btnPersonalizacion.setDisable(false));

        });
    }

    @FXML
    private void onMouseClickedbtnVerPuntajes(MouseEvent event) {

        btnVerPuntajes.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spBackgroundMenu, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("PointsView");
            PointsController controller = (PointsController) FlowController.getInstance().getController("PointsView");
            controller.RunPointsView();
            Platform.runLater(() -> btnVerPuntajes.setDisable(false));

        });
    }

    @FXML
    private void onMouseClickedbtnAyuda(MouseEvent event) {
        btnAyuda.setDisable(true);
        AnimationDepartment.stopAllAnimations();

        AnimationDepartment.glitchFadeOut(spBackgroundMenu, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("HelpView");
            HelpController controller = (HelpController) FlowController.getInstance().getController("HelpView");
            controller.RunHelpView();
            Platform.runLater(() -> btnAyuda.setDisable(false));

        });
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
        applyCustomCellStyleMenu(colFecha);

        TableColumn<PartidaMock, String> colPuntaje = new TableColumn<>("Puntaje");
        colPuntaje.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPuntaje()));
        applyCustomCellStyleMenu(colPuntaje);


        TableColumn<PartidaMock, String> colTiempo = new TableColumn<>("Tiempo Jugado");
        colTiempo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTiempo()));
        applyCustomCellStyleMenu(colTiempo);

// Aplica estilos opcionales si tienes CSS
        tblviewPartidasPausadas.getColumns().setAll(colFecha, colPuntaje, colTiempo);
    }

    private void applyCustomCellStyleMenu(TableColumn<PartidaMock, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setStyle("");
                } else {
                    Text text = new Text(item);
                    text.setStyle("-fx-font-size: 16px;");

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.web("#ff65ff"));
                    glow.setRadius(15);
                    glow.setSpread(0.6);
                    glow.setOffsetX(0);
                    glow.setOffsetY(0);
                    text.setEffect(glow);

                    setGraphic(text);

                    TableRow<?> row = getTableRow();
                    if (row != null) {
                        row.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                            if (isSelected) {
                                text.setFill(Color.web("#000000")); // Texto negro cuando la fila está seleccionada
                                setStyle("-fx-background-color: #ffc107; -fx-alignment: CENTER;"); // Fondo dorado
                            } else {
                                text.setFill(Color.web("#ff00ff")); // Texto morado cuando no está seleccionada
                                setStyle("-fx-background-color: #2d0062; -fx-alignment: CENTER;"); // Fondo oscuro
                            }
                        });

                        // Aplicar estilos al cargar la celda
                        if (row.isSelected()) {
                            text.setFill(Color.web("#000000"));
                            setStyle("-fx-background-color: #ffc107; -fx-alignment: CENTER;");
                        } else {
                            text.setFill(Color.web("#ff00ff"));
                            setStyle("-fx-background-color: #2d0062; -fx-alignment: CENTER;");
                        }
                    }
                }
            }
        });
    }



}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.model.JugadorRankingMock;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.SoundDepartment;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class PointsController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<JugadorRankingMock> tblviewRanking;
    @FXML
    private ImageView btnVolver;
    @FXML
    private StackPane spTextFieldContainer;
    @FXML
    private TextField txtfieldFiltro;
    @FXML
    private ImageView btnBuscar;
    @FXML
    private StackPane spBackgroundPoints;
    @FXML
    private ImageView imgBackgroundPoints;
    @FXML
    private ImageView imgArcade;
    @FXML
    private StackPane spArcade;
    @FXML
    private ImageView imgTela1;
    @FXML
    private ImageView imgTela2;
    @FXML
    private ImageView imgArana1;
    @FXML
    private ImageView imgArana2;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @Override
    public void initialize() {
    }

    @FXML
    private void OnMouseClickedBtnBolver(MouseEvent event) {
        SoundDepartment.playClick();
        AnimationDepartment.stopAllAnimations();
        btnVolver.setDisable(true);

        AnimationDepartment.glitchFadeOut(spBackgroundPoints, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnVolver.setDisable(false));

        });
    }

    public void RunPointsView() {
        ResetPointsView();
        populateTableViewRanking();
        System.out.println("Run Login View");

        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundPoints.getChildren().contains(imgBackgroundPoints)) {
            spBackgroundPoints.getChildren().add(0, imgBackgroundPoints);
        } else {
            spBackgroundPoints.getChildren().remove(imgBackgroundPoints);
            spBackgroundPoints.getChildren().add(0, imgBackgroundPoints);
        }

        // 🔁 Re-bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundPoints.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundPoints.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundPoints.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/PointsView.gif")));
        imgBackgroundPoints.setPreserveRatio(false);
        imgBackgroundPoints.setSmooth(true);
        imgBackgroundPoints.setOpacity(0.5);
        imgBackgroundPoints.setVisible(true);

        Platform.runLater(() -> {

            root.requestFocus();
            root.setVisible(true);
            root.setOpacity(1); // 🔓 Forzar visibilidad total

            root.applyCss();
            root.layout(); // ⬅️ Refresca el layout completamente

            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");
            AnimationDepartment.slideFromTop(lblTitulo, Duration.seconds(1));
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.slideFromLeft(txtfieldFiltro, Duration.seconds(1.5));
            AnimationDepartment.slideFromRight(btnBuscar, Duration.seconds(1.5));
            AnimationDepartment.animateNeonGlow(btnBuscar);
            AnimationDepartment.animateNeonBorderWithLED(spTextFieldContainer, txtfieldFiltro, 2.5);
            AnimationDepartment.slideFromRight(btnVolver, Duration.seconds(1.5));
            AnimationDepartment.animateNeonGlow(btnVolver);
            imgArcade.fitHeightProperty().bind(spArcade.heightProperty());
            AnimationDepartment.slideUpWithEpicBounceClean(spArcade, Duration.seconds(2), sceneHeight);
            AnimationDepartment.animateNeonGlow(imgArcade);
            Image telaImage = new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/TelaIcon.png"));
            Image aranaImage = new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/spiderPoints.png"));

            imgTela1.setImage(telaImage);
            imgTela2.setImage(telaImage);
            imgArana1.setImage(aranaImage);
            imgArana2.setImage(aranaImage);

            PauseTransition t1 = new PauseTransition(Duration.seconds(4.5));
            t1.setOnFinished(e -> {
                AnimationDepartment.animateSpiderWithWeb(imgTela1, imgArana1, Duration.seconds(8));
            });
            t1.play();

            PauseTransition t2 = new PauseTransition(Duration.seconds(14));
            t2.setOnFinished(e -> {
                AnimationDepartment.animateSpiderWithWeb(imgTela2, imgArana2, Duration.seconds(8));
            });
            t2.play();


        });

        System.out.println("Run final");


    }

    public void ResetPointsView() {
        // Reset visual
        System.out.println("Reset Login View");
        root.setOpacity(0);

        imgBackgroundPoints.setOpacity(0.5);
        imgBackgroundPoints.setTranslateX(0);
        imgBackgroundPoints.setTranslateY(0);
        imgBackgroundPoints.setEffect(null);
        imgBackgroundPoints.setVisible(true);


        lblTitulo.setOpacity(0);
        lblTitulo.setTranslateX(0);
        lblTitulo.setTranslateY(0);
        lblTitulo.setScaleX(1.0);
        lblTitulo.setScaleY(1.0);

        txtfieldFiltro.setOpacity(0);
        txtfieldFiltro.setTranslateY(0);
        txtfieldFiltro.setText("");

        btnBuscar.setOpacity(0);
        btnBuscar.setTranslateY(0);

//        tblviewRanking.setOpacity(0);
//        tblviewRanking.setTranslateY(0);
//
        btnVolver.setOpacity(0);
        btnVolver.setTranslateY(0);
        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundPoints.setEffect(null);
        spBackgroundPoints.setOpacity(1);
        spBackgroundPoints.setVisible(true);

        imgTela1.setOpacity(0);
        imgTela1.setTranslateX(0);
        imgTela1.setTranslateY(0);
        imgTela1.setEffect(null);
        imgTela1.setVisible(true);

        imgTela2.setOpacity(0);
        imgTela2.setTranslateX(0);
        imgTela2.setTranslateY(0);
        imgTela2.setEffect(null);

        imgArana1.setOpacity(0);
        imgArana1.setTranslateX(0);
        imgArana1.setTranslateY(0);
        imgArana1.setEffect(null);
        imgArana1.setVisible(true);

        imgArana2.setOpacity(0);
        imgArana2.setTranslateX(0);
        imgArana2.setTranslateY(0);
        imgArana2.setEffect(null);
        imgArana2.setVisible(true);

        spArcade.setOpacity(0);
        spArcade.setTranslateX(0);
        spArcade.setTranslateY(0);
        spArcade.setEffect(null);
        spArcade.setVisible(true);
    }

    private void populateTableViewRanking() {
        TableColumn<JugadorRankingMock, String> colNombre = new TableColumn<>("Jugador");
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        applyCustomCellStyle(colNombre);

        TableColumn<JugadorRankingMock, String> colPartidas = new TableColumn<>("Ptds.Ganadas");
        colPartidas.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPartidasGanadas()));
        applyCustomCellStyle(colPartidas);

        TableColumn<JugadorRankingMock, String> colPuntajeTotal = new TableColumn<>("Pts.Totales");
        colPuntajeTotal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPuntajeTotal()));
        colPuntajeTotal.setSortType(TableColumn.SortType.DESCENDING); // 🔥 Orden descendente
        applyCustomCellStyle(colPuntajeTotal);

        tblviewRanking.getColumns().setAll(colNombre, colPartidas, colPuntajeTotal);

        ObservableList<JugadorRankingMock> jugadores = FXCollections.observableArrayList(
                new JugadorRankingMock("Zombi Mariachi", "12", "11000"),
                new JugadorRankingMock("Gordito", "15", "10900"),
                new JugadorRankingMock("Zoncho del amor", "9", "10800"),
                new JugadorRankingMock("Ema excusas", "18", "10700"),
                new JugadorRankingMock("solo gorras marin", "6", "10600"),
                new JugadorRankingMock("Fatima", "14", "8800"),
                new JugadorRankingMock("Gustavo", "7", "5100"),
                new JugadorRankingMock("Helena", "13", "9000"),
                new JugadorRankingMock("Iván", "11", "7900"),
                new JugadorRankingMock("Julia", "8", "6000"),
                new JugadorRankingMock("Kevin", "16", "9700"),
                new JugadorRankingMock("Laura", "10", "7100"),
                new JugadorRankingMock("Manuel", "5", "3500"),
                new JugadorRankingMock("Nadia", "17", "10100"),
                new JugadorRankingMock("Oscar", "4", "2800")
        );

        tblviewRanking.setItems(jugadores);
        tblviewRanking.getSortOrder().add(colPuntajeTotal); // 📌 Aplicar orden automáticamente
    }


    private void applyCustomCellStyle(TableColumn<JugadorRankingMock, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Text text = new Text(item);
                    text.setFill(Color.web("#00ffff"));
                    text.setStyle("-fx-font-size: 12px;");

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.web("#00adad"));
                    glow.setRadius(15);
                    glow.setSpread(0.6);
                    glow.setOffsetX(0);
                    glow.setOffsetY(0);

                    text.setEffect(glow);
                    setGraphic(text);
                    setStyle("-fx-alignment: CENTER; -fx-background-color: #000000;");

                }
            }
        });
    }




    @FXML
    private void onMouseClickedbtnBuscar(MouseEvent event) {
        SoundDepartment.playClick();
    }


}

package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.SoundDepartment;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
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


public class InfoController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane spBackgroundInfo;
    @FXML
    private ImageView imgBackgroundInfo;
    @FXML
    private Label lblTitulo;
    @FXML
    private StackPane spLogo;
    @FXML
    private VBox vbTerminalContainer;
    @FXML
    private Label lblNombreProyecto;
    @FXML
    private Label lblNombreCurso;
    @FXML
    private Label lblProfesor;
    @FXML
    private Label lblEstudiantesAutores;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblSede;
    @FXML
    private Label lblDivision;
    @FXML
    private Label lblTecnologias;
    @FXML
    private Label lblFrase;
    @FXML
    private StackPane spUniversidad;
    @FXML
    private ImageView imgUniversidad;
    @FXML
    private StackPane spIsaac;
    @FXML
    private ImageView imgIsaac;
    @FXML
    private StackPane spMatiw;
    @FXML
    private ImageView imgMatiw;
    @FXML
    private StackPane spEmmanuel;
    @FXML
    private ImageView btnVolver;
    @FXML
    private Label lblAccesoSistema;
    @FXML
    private Label lblHackeando;
    @FXML
    private ImageView imgEmmanuel;
    @FXML
    private ImageView imgHideTerminal;
    @FXML
    private ImageView imgReziseTerminal;
    @FXML
    private ImageView imgCloseTerminal;
    @FXML
    private ImageView imgLogoInfo;
    @FXML
    private VBox vbLablesTitulosTerminal;
    @FXML
    private VBox vbLablesInfoTerminal;
    @FXML
    private VBox vbGlitchEffect;
    @FXML
    private StackPane spGlitchEffect;
    @FXML
    private Canvas canvasTrail;
    @FXML
    private Label lbUniversitario;
    @FXML
    private Label lblDesarrolladores;

    private static Duration computeTypewriterDelay(String text, double speedPerChar, Duration base, double marginSeconds) {
        double writingTime = text.length() * speedPerChar;
        return base.add(Duration.seconds(writingTime + marginSeconds));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {

    }

    public void RunInfoView() {
        ResetInfoView();
        System.out.println("🚀 Run Info View");

        if (!spBackgroundInfo.getChildren().contains(imgBackgroundInfo)) {
            spBackgroundInfo.getChildren().add(0, imgBackgroundInfo);
        } else {
            spBackgroundInfo.getChildren().remove(imgBackgroundInfo);
            spBackgroundInfo.getChildren().add(0, imgBackgroundInfo);
        }

        if (root.getScene() != null) {
            imgBackgroundInfo.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundInfo.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundInfo.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/InfoBackground.gif")));
        imgBackgroundInfo.setPreserveRatio(false);
        imgBackgroundInfo.setSmooth(true);
        imgBackgroundInfo.setOpacity(0.7);

        Platform.runLater(() -> {
            root.setOpacity(1);
            root.setVisible(true);
            root.requestFocus();
            root.applyCss();
            root.layout();

            double sceneHeight = root.getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));

            PauseTransition t1 = new PauseTransition(Duration.seconds(1));
            t1.setOnFinished(e -> {
                AnimationDepartment.slideFromTop(lblTitulo, Duration.ZERO);
                AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            });
            t1.play();

            PauseTransition t2 = new PauseTransition(Duration.seconds(2.5));
            t2.setOnFinished(e -> {
                AnimationDepartment.fadeIn(spLogo, Duration.ZERO);
                AnimationDepartment.pulse(spLogo, 2.5);
            });
            t2.play();

            PauseTransition t3 = new PauseTransition(Duration.seconds(4));
            t3.setOnFinished(e -> {
                AnimationDepartment.fadeIn(lbUniversitario, Duration.ZERO);
                AnimationDepartment.glitchTextWithFlicker(lbUniversitario);
                AnimationDepartment.slideUpWithEpicBounceClean(spUniversidad, Duration.ZERO, sceneHeight);
                AnimationDepartment.pulse(spUniversidad, 4);
            });
            t3.play();

            PauseTransition t4 = new PauseTransition(Duration.seconds(5.5));
            t4.setOnFinished(e -> {
                AnimationDepartment.fadeIn(lblDesarrolladores, Duration.ZERO);
                AnimationDepartment.glitchTextWithFlicker(lblDesarrolladores);
            });
            t4.play();

            PauseTransition isaac = new PauseTransition(Duration.seconds(5));
            isaac.setOnFinished(e -> {
                AnimationDepartment.slideUpWithEpicBounceClean(spIsaac, Duration.ZERO, sceneHeight);
                AnimationDepartment.pulse(spIsaac, 5);
            });
            isaac.play();

            PauseTransition matiw = new PauseTransition(Duration.seconds(5.5));
            matiw.setOnFinished(e -> {
                AnimationDepartment.slideUpWithEpicBounceClean(spMatiw, Duration.ZERO, sceneHeight);
                AnimationDepartment.pulse(spMatiw, 5.5);
            });
            matiw.play();

            PauseTransition emmanuel = new PauseTransition(Duration.seconds(6));
            emmanuel.setOnFinished(e -> {
                AnimationDepartment.slideUpWithEpicBounceClean(spEmmanuel, Duration.ZERO, sceneHeight);
                AnimationDepartment.pulse(spEmmanuel, 6);
            });
            emmanuel.play();

            AnimationDepartment.slideUpWithEpicBounceClean(vbTerminalContainer, Duration.seconds(7.5), sceneHeight);
            AnimationDepartment.applyFullCRTGlitchEffect(spGlitchEffect, Duration.seconds(3));

            Platform.runLater(() -> {
                Duration baseDelay = Duration.seconds(8.5);
                double speed = 0.03;
                double margin = 0.8;

                Duration current = baseDelay;
                AnimationDepartment.typewriterEffect(lblAccesoSistema, "ACCESO AL SISTEMA", current, speed);
                current = computeTypewriterDelay("ACCESO AL SISTEMA", speed, current, margin);

                AnimationDepartment.blinkHackeandoSequence(lblHackeando, Duration.seconds(0.8), Duration.seconds(27), Duration.seconds(4));

                AnimationDepartment.typewriterEffect(lblNombreProyecto, "Nombre del proyecto: Solitario Spider", current, speed);
                current = computeTypewriterDelay("Nombre del proyecto: Solitario Spider", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblNombreCurso, "Nombre del curso: Programación II - I Ciclo 2025", current, speed);
                current = computeTypewriterDelay("Nombre del curso: Programación II - I Ciclo 2025", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblProfesor, "Nombre del profesor: Máster Carlos Carranza Blanco", current, speed);
                current = computeTypewriterDelay("Nombre del profesor: Máster Carlos Carranza Blanco", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblEstudiantesAutores, "Nombre de los estudiantes: Isaac Alvarado Mata, Matiw Rivera Cascante, Emmanuel Gamboa Retana", current, 0.02);
                current = computeTypewriterDelay("Nombre de los estudiantes: Isaac Alvarado Mata, Matiw Rivera Cascante, Emmanuel Gamboa Retana", 0.02, current, margin);

                AnimationDepartment.typewriterEffect(lblFecha, "Fecha de desarrollo: Abril 2025 - Junio 2025", current, speed);
                current = computeTypewriterDelay("Fecha de desarrollo: Abril 2025 - Junio 2025", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblSede, "Sede: Universidad Nacional - Sede Regional Brunca", current, speed);
                current = computeTypewriterDelay("Sede: Universidad Nacional - Sede Regional Brunca", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblDivision, "División: Ciencias Exactas, Naturales y Tecnología", current, speed);
                current = computeTypewriterDelay("División: Ciencias Exactas, Naturales y Tecnología", speed, current, margin);

                AnimationDepartment.typewriterEffect(lblTecnologias, "Tecnologías usadas: JavaFX 23, Maven, Oracle 21c XE, Apache Netbeans / IntelliJ", current, 0.02);
                current = computeTypewriterDelay("Tecnologías usadas: JavaFX 23, Maven, Oracle 21c XE, Apache Netbeans / IntelliJ", 0.02, current, margin);

                AnimationDepartment.typewriterEffect(lblFrase, "\"El código es el arte de darle vida a la lógica.\"", current, 0.05);
            });

            AnimationDepartment.fadeIn(btnVolver, Duration.seconds(31));
            AnimationDepartment.animateNeonGlow(btnVolver);
        });
    }

    public void ResetInfoView() {
        System.out.println("🔁 Reset Info View");

        AnimationDepartment.stopAllAnimations();

        root.setOpacity(0);
        root.setVisible(true);

        imgBackgroundInfo.setOpacity(0.7);
        imgBackgroundInfo.setTranslateX(0);
        imgBackgroundInfo.setTranslateY(0);
        imgBackgroundInfo.setEffect(null);

        lblTitulo.setOpacity(0);
        spLogo.setOpacity(0);
        lbUniversitario.setOpacity(0);
        spUniversidad.setOpacity(0);
        lblDesarrolladores.setOpacity(0);
        spIsaac.setOpacity(0);
        spMatiw.setOpacity(0);
        spEmmanuel.setOpacity(0);
        vbTerminalContainer.setOpacity(0);
        spGlitchEffect.setEffect(null);
        spGlitchEffect.setOpacity(1);

        lblAccesoSistema.setOpacity(0);
        lblAccesoSistema.setText("");
        lblHackeando.setText("HACKEANDO");

        lblNombreProyecto.setOpacity(0);
        lblNombreCurso.setOpacity(0);
        lblProfesor.setOpacity(0);
        lblEstudiantesAutores.setOpacity(0);
        lblFecha.setOpacity(0);
        lblSede.setOpacity(0);
        lblDivision.setOpacity(0);
        lblTecnologias.setOpacity(0);
        lblFrase.setOpacity(0);

        lblNombreProyecto.setText("");
        lblNombreCurso.setText("");
        lblProfesor.setText("");
        lblEstudiantesAutores.setText("");
        lblFecha.setText("");
        lblSede.setText("");
        lblDivision.setText("");
        lblTecnologias.setText("");
        lblFrase.setText("");
        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundInfo.setEffect(null);
        spBackgroundInfo.setOpacity(1);
        spBackgroundInfo.setVisible(true);
    }

    @FXML
    private void onMouseClickedbtnVolver(MouseEvent event) {
        SoundDepartment.playClick();
        AnimationDepartment.stopAllAnimations();
        btnVolver.setDisable(true);

        AnimationDepartment.glitchFadeOut(spBackgroundInfo, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("LoginView");
            LoginController controller = (LoginController) FlowController.getInstance().getController("LoginView");
            controller.RunLoginView();
            Platform.runLater(() -> btnVolver.setDisable(false));

        });

    }
}

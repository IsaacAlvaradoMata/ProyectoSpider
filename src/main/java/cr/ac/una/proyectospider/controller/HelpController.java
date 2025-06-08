package cr.ac.una.proyectospider.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.SoundDepartment;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
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

    private StackPane opcionSeleccionadaActual = null;
    private ImageView imagenSeleccionadaActual = null;
    private Label labelSeleccionadoActual = null;
    
    @FXML
    private ImageView imgInfoGeneral;
    @FXML
    private ImageView imgObjetivo;
    @FXML
    private ImageView imgDificultades;
    @FXML
    private ImageView imgControles;
    @FXML
    private ImageView imgCondiciones;
    @FXML
    private ImageView imgExplicacion;
    @FXML
    private StackPane spBackgroundTabPane;
    @FXML
    private ImageView imgBackgroundTabPane;
    @FXML
    private Label lblInformacionGeneral;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Label lbl3;
    @FXML
    private Label lbl4;
    @FXML
    private Label lbl5;
    @FXML
    private Label lbl6;
    @FXML
    private Label lbl7;
    @FXML
    private Label lbl8;
    @FXML
    private Label lbl9;
    @FXML
    private Label lbl10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {

    }

    public void RunHelpView() {
        ResetHelpView();
        tabpaneCentroDeInfo.getSelectionModel().select(tabGeneral);

        System.out.println("Run Help View");
        if (!spBackgroundHelp.getChildren().contains(imgBackgroundHelp)) {
            spBackgroundHelp.getChildren().add(0, imgBackgroundHelp);
        } else {
            spBackgroundHelp.getChildren().remove(imgBackgroundHelp);
            spBackgroundHelp.getChildren().add(0, imgBackgroundHelp);
        }



        if (root.getScene() != null) {
            imgBackgroundHelp.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundHelp.fitHeightProperty().bind(root.getScene().heightProperty());
            imgBackgroundTabPane.fitWidthProperty().bind(spBackgroundTabPane.widthProperty());
            imgBackgroundTabPane.fitHeightProperty().bind(spBackgroundTabPane.heightProperty());
        }

        imgBackgroundHelp.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/HelpBackground.gif")));
        imgBackgroundHelp.setPreserveRatio(false);
        imgBackgroundHelp.setSmooth(true);
        imgBackgroundHelp.setOpacity(0.5);
        imgBackgroundHelp.setVisible(true);

        imgBackgroundTabPane.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/help1.gif")));
        imgBackgroundTabPane.setPreserveRatio(false);
        imgBackgroundTabPane.setSmooth(true);
        imgBackgroundTabPane.setOpacity(0);
        applyRoundedClip(imgBackgroundTabPane, 20);



        Platform.runLater(() -> {
            root.setOpacity(1);
            root.setVisible(true);
            root.requestFocus();
            root.applyCss();
            root.layout();

            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");

            spObjetivo.setDisable(true);
            spControles.setDisable(true);
            spDificultades.setDisable(true);
            spCondiciones.setDisable(true);
            spExplicacion.setDisable(true);



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

                setupHoverEffect(lblIObjetivo, btnObjetivo, spObjetivo);
                setupHoverEffect(lblControles, btnControles, spControles);
                setupHoverEffect(lblDificultades, btnDificultades, spDificultades);
                setupHoverEffect(lblCondiciones, btnCondiciones, spCondiciones);
                setupHoverEffect(lblExplicacion, btnExplicacion, spExplicacion);

                AnimationDepartment.animateNeonGlow(btnObjetivo);
                AnimationDepartment.animateNeonGlow(btnControles);
                AnimationDepartment.animateNeonGlow(btnDificultades);
                AnimationDepartment.animateNeonGlow(btnCondiciones);
                AnimationDepartment.animateNeonGlow(btnExplicacion);


            });
            t2.play();

            PauseTransition t3 = new PauseTransition(Duration.seconds(2.5));
            t3.setOnFinished(e -> {
                AnimationDepartment.animateNeonGlow(btnVolver);
                AnimationDepartment.slideLoopLeft(imgHelpIcon1, 400, 2);
                AnimationDepartment.slideLoopRight(imgHelpIcon2, 400, 2);
                AnimationDepartment.animateNeonGlow(imgHelpIcon1);
                AnimationDepartment.animateNeonGlow(imgHelpIcon2);
                AnimationDepartment.glitchFadeInBackground(imgBackgroundTabPane,  Duration.ZERO);
                AnimationDepartment.animateNeonGlowStrong(spBackgroundTabPane);
                AnimationDepartment.fadeIn(lblInformacionGeneral, Duration.seconds(1.2));
                AnimationDepartment.fadeIn(imgInfoGeneral, Duration.seconds(1.2));
                AnimationDepartment.animateNeonGlow(imgInfoGeneral);
                AnimationDepartment.animateNeonGlow(imgObjetivo);
                AnimationDepartment.animateNeonGlow(imgDificultades);
                AnimationDepartment.animateNeonGlow(imgControles);
                AnimationDepartment.animateNeonGlow(imgCondiciones);
                AnimationDepartment.animateNeonGlow(imgExplicacion);
                Platform.runLater(() -> {
                    Duration baseDelay = Duration.seconds(1.5);
                    double speed = 0.02;
                    double margin = 0.2;

                    Duration current = baseDelay;
                    AnimationDepartment.typewriterEffect(lbl1, "BIENVENIDO AL MÓDULO DE AYUDA DEL SOLITARIO SPIDER", current, speed);
                    current = computeTypewriterDelay("BIENVENIDO AL MÓDULO DE AYUDA DEL SOLITARIO SPIDER", speed, current, margin);


                    AnimationDepartment.typewriterEffect(lbl2, "ESTA SECCIÓN TE AYUDARÁ A:", current, speed);
                    current = computeTypewriterDelay("ESTA SECCIÓN TE AYUDARÁ A:", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl3, "- COMPRENDER EL OBJETIVO PRINCIPAL DEL JUEGO.", current, speed);
                    current = computeTypewriterDelay("- COMPRENDER EL OBJETIVO PRINCIPAL DEL JUEGO.", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl4, "- ENTENDER LOS NIVELES DE DIFICULTAD Y CÓMO AFECTAN LA JUGABILIDAD.", current, speed);
                    current = computeTypewriterDelay("- ENTENDER LOS NIVELES DE DIFICULTAD Y CÓMO AFECTAN LA JUGABILIDAD.", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl5, "- CONOCER LOS CONTROLES BÁSICOS PARA INTERACTUAR CON LAS CARTAS.", current, 0.02);
                    current = computeTypewriterDelay("- CONOCER LOS CONTROLES BÁSICOS PARA INTERACTUAR CON LAS CARTAS.", 0.02, current, margin);

                    AnimationDepartment.typewriterEffect(lbl6, "- SABER LAS CONDICIONES EN LAS CUALES SE GANA O SE PIERDE UNA PARTIDA.", current, speed);
                    current = computeTypewriterDelay("- SABER LAS CONDICIONES EN LAS CUALES SE GANA O SE PIERDE UNA PARTIDA.", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl7, "- DESCUBRIR CÓMO SE CALCULA TU PUNTAJE CON UNA EXPLICACIÓN CLARA.", current, speed);
                    current = computeTypewriterDelay("- DESCUBRIR CÓMO SE CALCULA TU PUNTAJE CON UNA EXPLICACIÓN CLARA.", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl8, "LAS CATEGORÍAS ESTÁN ORGANIZADAS EN PESTAÑAS:", current, speed);
                    current = computeTypewriterDelay("LAS CATEGORÍAS ESTÁN ORGANIZADAS EN PESTAÑAS:", speed, current, margin);

                    AnimationDepartment.typewriterEffect(lbl9, "SELECCIONA CUALQUIERA DE LAS OPCIONES DEL MENÚ IZQUIERDO PARA EXPLORAR MÁS INFORMACIÓN ESPECÍFICA SOBRE CADA ASPECTO DEL JUEGO.", current, 0.02);
                    current = computeTypewriterDelay("SELECCIONA CUALQUIERA DE LAS OPCIONES DEL MENÚ IZQUIERDO PARA EXPLORAR MÁS INFORMACIÓN ESPECÍFICA SOBRE CADA ASPECTO DEL JUEGO.", 0.02, current, margin);

                    AnimationDepartment.typewriterEffect(lbl10, "¡PREPÁRATE PARA CONVERTIRTE EN UN MAESTRO DEL SOLITARIO SPIDER!", current, 0.05);
                });


            });
            t3.play();

            PauseTransition t4 = new PauseTransition(Duration.seconds(21));
            t4.setOnFinished(e -> {
                spObjetivo.setDisable(false);
                spControles.setDisable(false);
                spDificultades.setDisable(false);
                spCondiciones.setDisable(false);
                spExplicacion.setDisable(false);
            });
            t4.play();

        });


    }


    public void ResetHelpView() {
        System.out.println("🔁 Reset Help View");

        AnimationDepartment.stopAllAnimations();

        root.setOpacity(0);
        root.setVisible(true);

        imgBackgroundHelp.setOpacity(0.7);
        imgBackgroundHelp.setTranslateX(0);
        imgBackgroundHelp.setTranslateY(0);
        imgBackgroundHelp.setEffect(null);

        imgBackgroundTabPane.setOpacity(0);
        imgBackgroundTabPane.setTranslateX(0);
        imgBackgroundTabPane.setTranslateY(0);
        imgBackgroundTabPane.setEffect(null);
        imgBackgroundTabPane.setVisible(true);

        btnObjetivo.setOpacity(0);
        btnDificultades.setOpacity(0);
        btnControles.setOpacity(0);
        btnCondiciones.setOpacity(0);
        btnExplicacion.setOpacity(0);
        btnVolver.setOpacity(0);

        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundHelp.setEffect(null);
        spBackgroundHelp.setOpacity(1);
        spBackgroundHelp.setVisible(true);

        lblIObjetivo.setOpacity(0);
        lblDificultades.setOpacity(0);
        lblControles.setOpacity(0);
        lblCondiciones.setOpacity(0);
        lblExplicacion.setOpacity(0);
        lblAyuda.setOpacity(0);

        imgHelpIcon1.setOpacity(0);
        imgHelpIcon1.setTranslateX(0);
        imgHelpIcon1.setTranslateY(0);
        imgHelpIcon2.setOpacity(0);
        imgHelpIcon2.setTranslateX(0);
        imgHelpIcon2.setTranslateY(0);

        lblInformacionGeneral.setOpacity(0);
        imgInfoGeneral.setOpacity(0);

        lbl1.setOpacity(0);
        lbl2.setOpacity(0);
        lbl3.setOpacity(0);
        lbl4.setOpacity(0);
        lbl5.setOpacity(0);
        lbl6.setOpacity(0);
        lbl7.setOpacity(0);
        lbl8.setOpacity(0);
        lbl9.setOpacity(0);
        lbl10.setOpacity(0);

        lbl1.setText("");
        lbl2.setText("");
        lbl3.setText("");
        lbl4.setText("");
        lbl5.setText("");
        lbl6.setText("");
        lbl7.setText("");
        lbl8.setText("");
        lbl9.setText("");
        lbl10.setText("");

        opcionSeleccionadaActual = null;
        imagenSeleccionadaActual = null;
        labelSeleccionadoActual = null;


    }

    @FXML
    private void OnMouseClickedBtnControles(MouseEvent event) {
        SoundDepartment.playClick();
        tabpaneCentroDeInfo.getSelectionModel().select(tabControles);
        seleccionarOpcionCompleta(spControles, btnControles, lblControles);
    }

    @FXML
    private void OnMouseClickedBtnObjetivo(MouseEvent event) {
        SoundDepartment.playClick();
        tabpaneCentroDeInfo.getSelectionModel().select(tabOjetivoJuego);
        seleccionarOpcionCompleta(spObjetivo, btnObjetivo, lblIObjetivo);
    }

    @FXML
    private void OnMouseClickedBtnDificultades(MouseEvent event) {
        SoundDepartment.playClick();
        tabpaneCentroDeInfo.getSelectionModel().select(tabDificultades);
        seleccionarOpcionCompleta(spDificultades, btnDificultades, lblDificultades);
    }

    @FXML
    private void OnMouseClickedBtnCondiciones(MouseEvent event) {
        SoundDepartment.playClick();
        tabpaneCentroDeInfo.getSelectionModel().select(tabCondiciones);
        seleccionarOpcionCompleta(spCondiciones, btnCondiciones, lblCondiciones);
    }

    @FXML
    private void OnMouseClickedBtnExplicacion(MouseEvent event) {
        SoundDepartment.playClick();
        tabpaneCentroDeInfo.getSelectionModel().select(tabExplicacion);
        seleccionarOpcionCompleta(spExplicacion, btnExplicacion, lblExplicacion);
    }


    @FXML
    private void OnMouseClickedBtnBolver(MouseEvent event) {
        SoundDepartment.playClick();
        AnimationDepartment.stopAllAnimations();
        btnVolver.setDisable(true);

        AnimationDepartment.glitchFadeOut(spBackgroundHelp, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnVolver.setDisable(false));

        });
    }


    private void seleccionarOpcionCompleta(StackPane nuevoSeleccionado, ImageView nuevaImagen, Label nuevoLabel) {
        if (opcionSeleccionadaActual != null && imagenSeleccionadaActual != null && labelSeleccionadoActual != null) {
            DropShadow neonGlow2 = new DropShadow(15, javafx.scene.paint.Color.web("#ff00ff"));
            neonGlow2.setRadius(15);
            neonGlow2.setSpread(0.6);
            imagenSeleccionadaActual.setVisible(false);
            labelSeleccionadoActual.setTextFill(javafx.scene.paint.Color.web("#00ffff"));
            labelSeleccionadoActual.setEffect(null);
            labelSeleccionadoActual.setEffect(neonGlow2);
        }

        nuevaImagen.setVisible(true);
        nuevoLabel.setTextFill(javafx.scene.paint.Color.web("#ffc107"));

        DropShadow glow = new DropShadow(15, javafx.scene.paint.Color.web("#ff8f00"));
        glow.setRadius(15);
        glow.setSpread(0.6);
        nuevoLabel.setEffect(glow);

        opcionSeleccionadaActual = nuevoSeleccionado;
        imagenSeleccionadaActual = nuevaImagen;
        labelSeleccionadoActual = nuevoLabel;
    }


    private void setupHoverEffect(Label label, ImageView imageFrame, StackPane containerStack) {
        imageFrame.setVisible(false);
        imageFrame.setOpacity(1);

        DropShadow neonGlow = new DropShadow();
        neonGlow.setColor(javafx.scene.paint.Color.web("#ff8f00"));
        neonGlow.setRadius(15);
        neonGlow.setSpread(0.6);

        DropShadow neonGlow2 = new DropShadow();
        neonGlow2.setColor(javafx.scene.paint.Color.web("#ff00ff"));
        neonGlow2.setRadius(15);
        neonGlow2.setSpread(0.6);


        containerStack.setOnMouseEntered(e -> {
            imageFrame.setVisible(true);
            label.setTextFill(javafx.scene.paint.Color.web("#ffc107"));
            label.setEffect(neonGlow);
            label.setScaleX(0.95);
            label.setScaleY(0.95);
        });

        containerStack.setOnMouseExited(e -> {
            if (opcionSeleccionadaActual != containerStack) {
                imageFrame.setVisible(false);
                label.setTextFill(javafx.scene.paint.Color.web("#00ffff"));
                label.setEffect(neonGlow2);
                label.setScaleX(1);
                label.setScaleY(1);
            }
        });

        containerStack.setOnMousePressed(e -> {
            label.setScaleX(0.90);
            label.setScaleY(0.90);
        });

        containerStack.setOnMouseReleased(e -> {
            label.setScaleX(1.0);
            label.setScaleY(1.0);
        });
    }

    private static Duration computeTypewriterDelay(String text, double speedPerChar, Duration base, double marginSeconds) {
        double writingTime = text.length() * speedPerChar;
        return base.add(Duration.seconds(writingTime + marginSeconds));
    }

    public static void applyRoundedClip(ImageView imageView, double arcSize) {
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(imageView.fitWidthProperty());
        clip.heightProperty().bind(imageView.fitHeightProperty());
        clip.setArcWidth(arcSize);
        clip.setArcHeight(arcSize);
        imageView.setClip(clip);
    }


}

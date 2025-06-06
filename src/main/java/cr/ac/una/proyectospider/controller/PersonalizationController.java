/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectospider.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.proyectospider.util.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
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
    @FXML
    private ImageView imgFondoPreview;

    private final List<Image> fondosPredeterminados = new ArrayList<>();
    private int currentIndex = 0;
    @FXML
    private VBox vboxleft;
    @FXML
    private VBox vboxRigth;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    public void RunPersonalizationView() {
        ResetPersonalizationView();
        System.out.println("Run Personalization View");

        // 🟡 Reposicionar y asegurar fondo en el índice 0
        if (!spBackgroundPersonalization.getChildren().contains(imgBackgroundPersonalization)) {
            spBackgroundPersonalization.getChildren().add(0, imgBackgroundPersonalization);
        } else {
            spBackgroundPersonalization.getChildren().remove(imgBackgroundPersonalization);
            spBackgroundPersonalization.getChildren().add(0, imgBackgroundPersonalization);
        }

        // 🔁 Re‐bind y recarga de imagen
        if (root.getScene() != null) {
            imgBackgroundPersonalization.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundPersonalization.fitHeightProperty().bind(root.getScene().heightProperty());
        }

        imgBackgroundPersonalization.setImage(
                new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/option6.gif")));
        imgBackgroundPersonalization.setPreserveRatio(false);
        imgBackgroundPersonalization.setSmooth(true);
        imgBackgroundPersonalization.setOpacity(0.3);
        imgBackgroundPersonalization.setVisible(true);

        Platform.runLater(() -> {
            root.requestFocus();
            root.setVisible(true);
            root.setOpacity(1);

            root.applyCss();
            root.layout();

            double sceneHeight = root.getScene().getHeight();
            AnimationDepartment.glitchFadeIn(root, Duration.seconds(0.6));
            System.out.println("se hizo el glitchFadeIn");
            imgPrevistaFondo.setEffect(new ColorAdjust(0, 0, -0.2, 0));

            // --- Inicializar la lista de fondos predeterminados si está vacía ---
            if (fondosPredeterminados.isEmpty()) {
                for (int i = 1; i <= 6; i++) {
                    fondosPredeterminados.add(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/DefaultBack" + i + ".png")));
                }
            }
            mostrarFondoPreview();

            // Animaciones de texto y neón (las tuyas ya estaban aquí)
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.glitchTextWithFlicker(lblJugador);
            AnimationDepartment.glitchTextWithFlicker(lblEstilosCartas);
            AnimationDepartment.glitchTextWithFlicker(lblCyberpunk);
            AnimationDepartment.glitchTextWithFlicker(lblClasicas);
            AnimationDepartment.glitchTextWithFlicker(lblSeleccionFondo);

            AnimationDepartment.animateNeonGlow(imgFondoPreview);
            AnimationDepartment.animateNeonGlow(btnVolver);
            AnimationDepartment.animateNeonGlow(btnFlechaDerecha);
            AnimationDepartment.animateNeonGlow(btnFlechaIzquierda);
            AnimationDepartment.animateNeonGlow(btnAgregarOtro);
            AnimationDepartment.animateNeonGlow(btnGuardarCambios);
            AnimationDepartment.animateNeonGlow(imgCartasCyberpunk);
            AnimationDepartment.animateNeonGlow(imgCartasClasicas);

            AnimationDepartment.slideFromTop(lblTitulo, Duration.seconds(1));
            AnimationDepartment.slideFromLeft(vboxleft, Duration.seconds(1.8));
            AnimationDepartment.slideFromRight(vboxRigth, Duration.seconds(1.8));
            AnimationDepartment.slideUpWithEpicBounceClean(btnVolver, Duration.seconds(2), sceneHeight);

            // ─────────────────────────────────────────────────────────────
            //  Aquí es donde ajustamos QUÉ estilo estaba guardado
            // ─────────────────────────────────────────────────────────────

            // 1) Leemos lo que haya en AppContext.KEY_ESTILO_CARTAS (puede ser null, o bien la ruta que guardamos)
            Object estiloGuardado = AppContext.getInstance().get(AppContext.KEY_ESTILO_CARTAS);

            // 2) Dos posibles valores (constantes):
            final String rutaCyberpunk = AppContext.RUTA_CARTAS_CYBERPUNK;
            final String rutaClasicas   = AppContext.RUTA_CARTAS_CLASICAS;

            // 3) Comprobamos:
            if (estiloGuardado == null) {
                // ─ Si nunca se guardó nada, por defecto “Cyberpunk”
                rbtnCyberpunk.setSelected(true);
                rbtnClasicas.setSelected(false);
                // View previa de Cyberpunk (preview2.png)
                imgCartasPrevista.setImage(new Image(
                        getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview2.png")));
            }
            else {
                String ruta = estiloGuardado.toString();
                if (ruta.equals(rutaCyberpunk)) {
                    // ─ El usuario había seleccionado Cyberpunk previamente
                    rbtnCyberpunk.setSelected(true);
                    rbtnClasicas.setSelected(false);
                    imgCartasPrevista.setImage(new Image(
                            getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview2.png")));
                }
                else if (ruta.equals(rutaClasicas)) {
                    // ─ El usuario había seleccionado Clásicas previamente
                    rbtnCyberpunk.setSelected(false);
                    rbtnClasicas.setSelected(true);
                    imgCartasPrevista.setImage(new Image(
                            getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview1.png")));
                }
                else {
                    // ─ Si el valor guardado no coincide con ninguno (por si se corrompió), usar “Cyberpunk” de fallback
                    rbtnCyberpunk.setSelected(true);
                    rbtnClasicas.setSelected(false);
                    imgCartasPrevista.setImage(new Image(
                            getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview2.png")));
                }
            }

            // 4) Listener para el RadioButton “Cyberpunk”
            rbtnCyberpunk.setOnAction(ev -> {
                if (rbtnCyberpunk.isSelected()) {
                    rbtnClasicas.setSelected(false);
                    imgCartasPrevista.setImage(new Image(
                            getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview2.png")));
                }
            });

            // 5) Listener para el RadioButton “Clásicas”
            rbtnClasicas.setOnAction(ev -> {
                if (rbtnClasicas.isSelected()) {
                    rbtnCyberpunk.setSelected(false);
                    imgCartasPrevista.setImage(new Image(
                            getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/Preview1.png")));
                }
            });
            // ─────────────────────────────────────────────────────────────

        });

        System.out.println("RunPersonalizationView final");
    }


    private void mostrarFondoPreview() {
        Image fondoActual = fondosPredeterminados.get(currentIndex);

        imgFondoPreview.setImage(fondoActual);
        imgPrevistaFondo.setImage(fondoActual);


        FadeTransition fadeFondo = new FadeTransition(Duration.millis(400), imgFondoPreview);
        fadeFondo.setFromValue(0);
        fadeFondo.setToValue(1);
        fadeFondo.play();

        FadeTransition fadePrevista = new FadeTransition(Duration.millis(400), imgPrevistaFondo);
        fadePrevista.setFromValue(0);
        fadePrevista.setToValue(1);
        fadePrevista.play();
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

        lblTitulo.setOpacity(0);
        lblTitulo.setTranslateX(0);
        lblTitulo.setTranslateY(0);
        lblTitulo.setScaleX(1.0);
        lblTitulo.setScaleY(1.0);

        vboxleft.setOpacity(0);
        vboxleft.setTranslateX(0);
        vboxleft.setTranslateY(0);
        vboxleft.setScaleX(1.0);
        vboxleft.setScaleY(1.0);

        vboxRigth.setOpacity(0);
        vboxRigth.setTranslateX(0);
        vboxRigth.setTranslateY(0);
        vboxRigth.setScaleX(1.0);
        vboxRigth.setScaleY(1.0);

        btnVolver.setOpacity(0);
        btnVolver.setTranslateY(0);


        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spBackgroundPersonalization.setEffect(null);
        spBackgroundPersonalization.setOpacity(1);
        spBackgroundPersonalization.setVisible(true);

    }

    @FXML
    private void onMouseClickedbtnGuardarCambios(MouseEvent event) {
        // 1. Guardar el fondo seleccionado
        Image fondoSeleccionado = imgPrevistaFondo.getImage();
        AppContext.getInstance().set(AppContext.KEY_FONDO_SELECCIONADO, fondoSeleccionado);

        // 2. Guardar la RUTA del estilo de cartas, según RadioButton
        String claveEstilo;
        if (rbtnCyberpunk.isSelected()) {
            claveEstilo = AppContext.RUTA_CARTAS_CYBERPUNK;
        } else {
            claveEstilo = AppContext.RUTA_CARTAS_CLASICAS;
        }
        AppContext.getInstance().set(AppContext.KEY_ESTILO_CARTAS, claveEstilo);

        CustomAlert.showInfo(
                spBackgroundPersonalization,                 // El StackPane padre donde “dibuja” la alerta
                "Configuración guardada",                    // Título del diálogo
                "El fondo y el estilo de cartas han sido guardados correctamente.",
                null                                         // Runnable que se ejecuta cuando cierre la alerta (aquí no necesitamos nada más)
        );
        // 3. Feedback al usuario

    }



    @FXML
    private void OnMouseClickedbtnFlechaIzquierda(MouseEvent event) {
        currentIndex = (currentIndex - 1 + fondosPredeterminados.size()) % fondosPredeterminados.size();
        mostrarFondoPreview();
    }

    @FXML
    private void OnMouseClickedbtnFlechaDerecha(MouseEvent event) {
        currentIndex = (currentIndex + 1) % fondosPredeterminados.size();
        mostrarFondoPreview();
    }

    @FXML
    private void onMouseClickedbtnAgregarOtro(MouseEvent event) {
        Window ventanaActual = root.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un fondo de pantalla");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg, *.gif)",
                        "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(ventanaActual);

        if (archivoSeleccionado != null) {
            try {
                String uri = archivoSeleccionado.toURI().toString();
                Image imagenPersonalizada = new Image(uri);

                imgPrevistaFondo.setImage(imagenPersonalizada);


            } catch (Exception ex) {
                CustomAlert.showInfo(
                        spBackgroundPersonalization,                 // El StackPane padre donde “dibuja” la alerta
                        "Error al cargar imagen",                    // Título del diálogo
                        "No se pudo cargar la imagen seleccionada:\n" + ex.getMessage(),
                        null                                         // Runnable que se ejecuta cuando cierre la alerta (aquí no necesitamos nada más)
                );
            }
        }

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

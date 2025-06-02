package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.model.CartasPartidaDto;
import cr.ac.una.proyectospider.model.PartidaDto;
import cr.ac.una.proyectospider.service.PartidaService;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.AppContext;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.MazoGenerator;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

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
    private Label lblTitulo;

    @FXML
    private Label lblTiempo;

    @FXML
    private Label lblMovimientos1;

    @FXML
    private Label lblPuntaje1;

    @FXML
    private Label lblTiempo1;

    @FXML
    private BorderPane root;

    @FXML
    private StackPane spGamebackground;

    @FXML
    private StackPane spTableroBackground;

    @FXML
    private Label lblDificultad;

    @FXML
    private Label lblDificultad1;

    @FXML
    private ImageView imgSpider1;
    @FXML
    private ImageView imgSpider2;

    private List<CartasPartidaDto> cartasEnJuego;
    private List<CartasPartidaDto> cartasSeleccionadas = new ArrayList<>();
    private List<CartasPartidaDto> cartasArrastradas = new ArrayList<>();
    private Map<CartasPartidaDto, ImageView> cartaToImageView = new HashMap<>();
    private int puntaje = 500;
    private int movimientos = 0;
    private Timeline timeline;
    private int segundosTranscurridos = 0;
    private boolean tiempoIniciado = false;
    private boolean usarEstiloClasico = false; // Por defecto, usar el estilo moderno
    private MovimientoSugerido lastHint = null;
    private int lastHintIndex = -1; // Para rotar entre pistas
    private PartidaDto partidaDto;
    public boolean primerIngreso = true;



    private static class MovimientoSugerido {
        List<CartasPartidaDto> grupo;
        CartasPartidaDto destino;
        int columnaOrigen;
        int columnaDestino;
        int longitud;
        int valorSuperior;

        MovimientoSugerido(List<CartasPartidaDto> grupo, CartasPartidaDto destino, int columnaOrigen, int columnaDestino) {
            this.grupo = grupo;
            this.destino = destino;
            this.columnaOrigen = columnaOrigen;
            this.columnaDestino = columnaDestino;
            this.longitud = grupo.size();
            this.valorSuperior = Integer.parseInt(grupo.get(0).getValor());
        }
    }

    /**
     * Calcula el layoutY correcto para una carta en una columna de solitario,
     * usando espaciado vertical dinámico para que todas quepan en el Pane de 600px.
     * Si hay 7 cartas o menos, el espaciado es 22px. Si hay 30 o más, es 8px.
     * Para cantidades intermedias, interpola entre 22 y 8.
     * @param orden El índice de la carta en la columna (0 para la primera carta)
     * @param totalCartas El número total de cartas en la columna
     * @return El valor de layoutY para la carta
     */
    public static double calcularLayoutY(int orden, int totalCartas) {
        final double ALTURA_PANE = 600.0;
        final double SPACING_MAX = 22.0;
        final double SPACING_MIN = 8.0;
        final double ALTURA_CARTA = 100.0;
        double spacing;
        if (totalCartas <= 6) {
            spacing = SPACING_MAX;
        } else if (totalCartas >= 30) {
            spacing = SPACING_MIN;
        } else {
            spacing = SPACING_MAX - ((SPACING_MAX - SPACING_MIN) * (totalCartas - 7) / 23.0);
        }
        double maxSpacing = (ALTURA_PANE - ALTURA_CARTA) / Math.max(1, totalCartas - 1);
        spacing = Math.min(spacing, maxSpacing);
        spacing = Math.max(SPACING_MIN, spacing);
        return orden * spacing;
    }

    @FXML
    void onMouseClickedbtnGuardarySalir(MouseEvent event) {
        btnGuardarySalir.setDisable(true);
        AnimationDepartment.stopAllAnimations();
        detenerTemporizador();
        tiempoIniciado = false;

//        ESTO SIGUE ESTANDO IGUAL, SOLO LO COMENTE

//        List<CartasPartidaDto> cartasActuales = obtenerEstadoDelTablero();

//        // Marcar partida como pausada
//        partidaDto.setEstado("PAUSADA");
//        partidaDto.setFechaFin(new Date());
//
//        boolean exito = new PartidaService().guardarPartidaCompleta(partidaDto, cartasActuales);
//        if (!exito) {
//            System.err.println("❌ No se pudo guardar la partida al salir.");
//        }

        cartasEnJuego = null;
        cartaToImageView.clear();
        movimientos = 0;
        puntaje = 500;

        AnimationDepartment.glitchFadeOut(spGamebackground, Duration.seconds(1.1), () -> {
        FlowController.getInstance().goView("MenuView");
        MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
        controller.RunMenuView();

        Platform.runLater(() -> btnGuardarySalir.setDisable(false));
        });
    }



    @FXML
    void oMouseClickedbtnPista(MouseEvent event) {
        darPista();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void RunGameView(PartidaDto partidaDto) {
        this.partidaDto = partidaDto;
        ResetGameView();

        // — Fondo general “GameBackground.gif” —
        if (!spGamebackground.getChildren().contains(imgBackgroundGame)) {
            spGamebackground.getChildren().add(0, imgBackgroundGame);
        } else {
            spGamebackground.getChildren().remove(imgBackgroundGame);
            spGamebackground.getChildren().add(0, imgBackgroundGame);
        }
        if (root.getScene() != null) {
            imgBackgroundGame.fitWidthProperty().bind(root.getScene().widthProperty());
            imgBackgroundGame.fitHeightProperty().bind(root.getScene().heightProperty());
        }
        imgBackgroundGame.setImage(
                new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/GameBackground.gif")));
        imgBackgroundGame.setPreserveRatio(false);
        imgBackgroundGame.setSmooth(true);
        imgBackgroundGame.setOpacity(0.4);
        imgBackgroundGame.setVisible(true);

        imgBackgroundTablero.setEffect(new ColorAdjust(0, 0, -0.2, 0));
        // — Fondo del tablero (imgBackgroundTablero) con neon glow —
        Object fondoEnContext = AppContext.getInstance().get(AppContext.KEY_FONDO_SELECCIONADO);
        if (fondoEnContext instanceof Image) {
            imgBackgroundTablero.setImage((Image) fondoEnContext);
        } else {
            imgBackgroundTablero.setImage(new Image(
                    getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/DefaultBack3.png")));
        }
        AnimationDepartment.animateNeonGlow2(spTableroBackground);

        // — Seleccionar estilo de cartas (clásico o cyberpunk) —
        Object estiloEnContext = AppContext.getInstance().get(AppContext.KEY_ESTILO_CARTAS);
        if (estiloEnContext instanceof String) {
            String rutaEstilo = (String) estiloEnContext;
            usarEstiloClasico = rutaEstilo.equals(AppContext.RUTA_CARTAS_CLASICAS);
        } else {
            usarEstiloClasico = false;
        }

        // — Dificultad y label —
        String dificultad = partidaDto.getDificultad();
        if (dificultad == null) dificultad = "FACIL";
        lblDificultad.setText(" " + dificultad);

        // — Generar mazo (solo la primera vez) —
        if (cartasEnJuego == null) {
            cartasEnJuego = MazoGenerator.generarMazoPorDificultad(dificultad, usarEstiloClasico);
        }

        // — Reset temporizador si no se ha iniciado —
        if (!tiempoIniciado) {
            segundosTranscurridos = 0;
            actualizarLabelTiempo();
            detenerTemporizador();
        }

        // — Limpiar contenedores antes de pintar —
        hboxTablero.getChildren().clear();
        hboxTableroSuperior.getChildren().clear();
        imgMazo.setImage(null);

        // — Actualizar labels —
        lblPuntaje.setText("" + puntaje);
        lblMovimientos.setText("" + movimientos);
        actualizarLabelTiempo();

        // — PINTAR COLUMNAS + CARTAS: solo este método —
        dibujarColumnasYCargarCartasEnTablero();

        // — PINTAR MAZO + PILAS: solo este método —
        actualizarVistaDelMazoYPilas();

        // — Animaciones de primer ingreso (solo la primera vez) —
        if (primerIngreso) {
            Platform.runLater(this::aplicarAnimacionesDeEntrada);
            primerIngreso = false;
        }
    }


    private void aplicarAnimacionesDeEntrada(){
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
            AnimationDepartment.slideFromTop(lblTitulo, Duration.ZERO);
            AnimationDepartment.glitchTextWithFlicker(lblTitulo);
            AnimationDepartment.fadeIn(imgSpider1, Duration.seconds(1.5));
            AnimationDepartment.fadeIn(imgSpider2, Duration.seconds(1.5));
        });
        t1.play();

        PauseTransition t2 = new PauseTransition(Duration.seconds(2.5));
        t2.setOnFinished(e -> {
            AnimationDepartment.slideFromLeft(lblNombreJugador, Duration.ZERO);
            AnimationDepartment.slideFromRight(lblPuntaje, Duration.ZERO);
            AnimationDepartment.slideFromRight(lblTiempo, Duration.ZERO);
            AnimationDepartment.slideFromRight(lblPuntaje1, Duration.ZERO);
            AnimationDepartment.slideFromRight(lblTiempo1, Duration.ZERO);
            AnimationDepartment.glitchTextWithFlicker(lblNombreJugador);
            AnimationDepartment.glitchTextWithFlicker(lblPuntaje1);
            AnimationDepartment.glitchTextWithFlicker(lblTiempo1);
            AnimationDepartment.slideLoopLeft(imgSpider1, 400, 2);
            AnimationDepartment.slideLoopRight(imgSpider2, 400, 2);
            AnimationDepartment.animateNeonGlow(imgSpider1);
            AnimationDepartment.animateNeonGlow(imgSpider2);

        });
        t2.play();

        PauseTransition t3 = new PauseTransition(Duration.seconds(3));
        t3.setOnFinished(e -> {
            AnimationDepartment.slideUpWithEpicBounceClean(spTableroBackground, Duration.ZERO, sceneHeight);

        });
        t3.play();

        PauseTransition t4 = new PauseTransition(Duration.seconds(3.8));
        t4.setOnFinished(e -> {
            AnimationDepartment.slideFromLeft(lblMovimientos, Duration.ZERO);
            AnimationDepartment.slideFromLeft(lblMovimientos1, Duration.ZERO);
            AnimationDepartment.glitchTextWithFlicker(lblMovimientos1);
            AnimationDepartment.slideFromRight(btnPista, Duration.ZERO);
            AnimationDepartment.animateNeonGlow(btnPista);

        });
        t4.play();

        PauseTransition t5 = new PauseTransition(Duration.seconds(4.1));
        t5.setOnFinished(e -> {
            AnimationDepartment.slideFromLeft(lblDificultad, Duration.ZERO);
            AnimationDepartment.slideFromLeft(lblDificultad1, Duration.ZERO);
            AnimationDepartment.glitchTextWithFlicker(lblDificultad1);
            AnimationDepartment.slideFromRight(btnGuardarySalir, Duration.ZERO);
            AnimationDepartment.animateNeonGlow(btnGuardarySalir);


        });
        t5.play();




    }

    public void ResetGameView() {
        // 1) Hacer fadeOut / reset visual general
        root.setOpacity(0);

        imgBackgroundGame.setOpacity(0.7);
        imgBackgroundGame.setTranslateX(0);
        imgBackgroundGame.setTranslateY(0);
        imgBackgroundGame.setEffect(null);

        lblTitulo.setOpacity(0);
        lblTitulo.setTranslateX(0);
        lblTitulo.setTranslateY(0);
        lblTitulo.setScaleX(1.0);
        lblTitulo.setScaleY(1.0);

        lblNombreJugador.setOpacity(0);
        lblNombreJugador.setTranslateX(0);
        lblNombreJugador.setTranslateY(0);
        lblNombreJugador.setScaleX(1.0);
        lblNombreJugador.setScaleY(1.0);

        lblPuntaje.setOpacity(0);
        lblPuntaje.setTranslateX(0);
        lblPuntaje.setTranslateY(0);
        lblPuntaje.setScaleX(1.0);
        lblPuntaje.setScaleY(1.0);

        lblTiempo.setOpacity(0);
        lblTiempo.setTranslateX(0);
        lblTiempo.setTranslateY(0);
        lblTiempo.setScaleX(1.0);
        lblTiempo.setScaleY(1.0);

        lblMovimientos.setOpacity(0);
        lblMovimientos.setTranslateX(0);
        lblMovimientos.setTranslateY(0);
        lblMovimientos.setScaleX(1.0);
        lblMovimientos.setScaleY(1.0);

        lblDificultad.setOpacity(0);
        lblDificultad.setTranslateX(0);
        lblDificultad.setTranslateY(0);
        lblDificultad.setScaleX(1.0);
        lblDificultad.setScaleY(1.0);

        lblPuntaje1.setOpacity(0);
        lblPuntaje1.setTranslateX(0);
        lblPuntaje1.setTranslateY(0);
        lblPuntaje1.setScaleX(1.0);
        lblPuntaje1.setScaleY(1.0);

        lblTiempo1.setOpacity(0);
        lblTiempo1.setTranslateX(0);
        lblTiempo1.setTranslateY(0);
        lblTiempo1.setScaleX(1.0);
        lblTiempo1.setScaleY(1.0);

        lblMovimientos1.setOpacity(0);
        lblMovimientos1.setTranslateX(0);
        lblMovimientos1.setTranslateY(0);
        lblMovimientos1.setScaleX(1.0);
        lblMovimientos1.setScaleY(1.0);

        lblDificultad1.setOpacity(0);
        lblDificultad1.setTranslateX(0);
        lblDificultad1.setTranslateY(0);
        lblDificultad1.setScaleX(1.0);
        lblDificultad1.setScaleY(1.0);

        spTableroBackground.setOpacity(0);
        spTableroBackground.setTranslateX(0);
        spTableroBackground.setTranslateY(0);
        spTableroBackground.setEffect(null);
        spTableroBackground.setVisible(true);

        btnPista.setOpacity(0);
        btnPista.setTranslateY(0);

        btnGuardarySalir.setOpacity(0);
        btnGuardarySalir.setTranslateY(0);

        imgSpider1.setOpacity(0);
        imgSpider1.setTranslateX(0);
        imgSpider1.setTranslateY(0);
        imgSpider2.setOpacity(0);
        imgSpider2.setTranslateX(0);
        imgSpider2.setTranslateY(0);

        // 2) Reset del root / spGamebackground
        root.setEffect(null);
        root.setOpacity(1);
        root.setVisible(true);
        spGamebackground.setEffect(null);
        spGamebackground.setOpacity(1);
        spGamebackground.setVisible(true);

        // 3) Limpiar contenedores de cartas
        hboxTablero.getChildren().clear();
        hboxPilas.getChildren().clear();

        // 4) Si no hay temporizador iniciado, reiniciar tiempo
        if (!tiempoIniciado) {
            segundosTranscurridos = 0;
            actualizarLabelTiempo();
            detenerTemporizador();
        }
    }

    private void repartirCartasDelMazo() {
        iniciarTemporizadorSiEsNecesario();
        movimientos++;
        puntaje = Math.max(0, puntaje - 1);

        lblMovimientos.setText("" + movimientos);
        lblPuntaje.setText("" + puntaje);

        // Verificar si todas las columnas tienen al menos una carta
        boolean todasColumnasConCartas = true;
        for (int colIndex = 0; colIndex < 10; colIndex++) {
            final int cIndex = colIndex;  // <— cIndex es “efectivamente final”
            boolean columnaConCartas = cartasEnJuego.stream()
                    .anyMatch(c -> c.getColumna() == cIndex);
            if (!columnaConCartas) {
                todasColumnasConCartas = false;
                break;
            }
        }

        if (!todasColumnasConCartas) {
            System.out.println("No se pueden repartir cartas: algunas columnas están vacías");
            return;
        }

        // Obtener cartas que quedan en el mazo
        List<CartasPartidaDto> cartasEnMazo = cartasEnJuego.stream()
                .filter(CartasPartidaDto::getEnMazo)
                .toList();

        if (cartasEnMazo.size() < 10) {
            System.out.println("No hay suficientes cartas en el mazo para repartir");
            return;
        }

        // Repartir una carta a cada columna
        for (int i = 0; i < 10; i++) {
            final int cIndex = i;  // index “efectivamente final” para usar en lambda
            int nuevoOrden = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == cIndex)   // aquí usamos cIndex
                    .mapToInt(CartasPartidaDto::getOrden)
                    .max()
                    .orElse(-1) + 1;

            CartasPartidaDto carta = cartasEnMazo.get(cIndex);
            carta.setEnMazo(false);
            carta.setColumna(cIndex);
            carta.setOrden(nuevoOrden);
            carta.setBocaArriba(true);
        }

        // Verificar si se completa alguna secuencia tras repartir
        verificarSecuenciaCompleta();

        // *** EN LUGAR DE RunGameView(partidaDto) ***
        // simplemente redibujamos columnas y mazo/pilas:
        dibujarColumnasYCargarCartasEnTablero();
        actualizarVistaDelMazoYPilas();
    }

    private double calcularEspaciadoVertical(int cantidadCartas) {
        final double ALTURA_PANE = 600.0;
        final double SPACING_MAX = 22.0;
        final double SPACING_MIN = 8.0;
        final double ALTURA_CARTA = 100.0; // Aproximado, igual que en dragView

        double spacing;
        if (cantidadCartas <= 7) {
            spacing = SPACING_MAX;
        } else if (cantidadCartas >= 30) {
            spacing = SPACING_MIN;
        } else {
            spacing = SPACING_MAX - ((SPACING_MAX - SPACING_MIN) * (cantidadCartas - 7) / 23.0);
        }
        // Ajuste para que todas quepan dentro del Pane
        double maxSpacing = (ALTURA_PANE - ALTURA_CARTA) / Math.max(1, cantidadCartas - 1);
        spacing = Math.min(spacing, maxSpacing);
        spacing = Math.max(SPACING_MIN, spacing);
        return spacing;
    }
    // Save carts on the DB
    private void dibujarColumnasYCargarCartasEnTablero() {
        hboxTablero.getChildren().clear();

        // 1) Crear 10 columnas (Pane) y asignarles OnDragOver / OnDragDropped
        for (int col = 0; col < 10; col++) {
            final int colIndex = col;
            Pane columna = new Pane();
            columna.setPrefWidth(80);
            columna.setPrefHeight(600);

            // Permitir soltar sobre la columna
            columna.setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            // Manejar el drop en esa columna
            columna.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString() && !cartasSeleccionadas.isEmpty()) {
                    // Origen antes de mover
                    CartasPartidaDto cartaOrigen = cartasSeleccionadas.get(0);
                    int colAnterior = cartaOrigen.getColumna();
                    int ordenAnterior = cartaOrigen.getOrden();

                    // Última carta visible en columna destino (colIndex)
                    CartasPartidaDto cartaDestino = obtenerUltimaCartaVisible(colIndex);
                    boolean puedeMover;
                    if (cartaDestino == null) {
                        // Columna vacía: aceptar cualquier grupo válido
                        puedeMover = esGrupoValido(cartasSeleccionadas);
                    } else {
                        if (cartasSeleccionadas.size() == 1) {
                            // Una carta: valorOrigen == valorDestino - 1
                            puedeMover = Integer.parseInt(cartaOrigen.getValor())
                                    == Integer.parseInt(cartaDestino.getValor()) - 1;
                        } else {
                            // Varios: verificar secuencia válida y valor descendente en uno
                            puedeMover = esGrupoValido(cartasSeleccionadas)
                                    && Integer.parseInt(cartaOrigen.getValor())
                                    == Integer.parseInt(cartaDestino.getValor()) - 1;
                        }
                    }

                    if (puedeMover) {
                        // 1) Actualizar modelo
                        moverCartasSeleccionadas(colIndex);
                        // 2) Voltear carta debajo en la columna origen, si existe
                        cartasEnJuego.stream()
                                .filter(c -> c.getColumna() == colAnterior
                                        && c.getOrden() == ordenAnterior - 1)
                                .findFirst()
                                .ifPresent(c -> c.setBocaArriba(true));
                        // 3) Redibujar SOLO las columnas + cartas
                        dibujarColumnasYCargarCartasEnTablero();
                        // 4) Actualizar mazo/pilas en caso de que se haya completado secuencia
                        actualizarVistaDelMazoYPilas();
                        success = true;
                    } else {
                        // Si drop inválido: shake en las cartas seleccionadas
                        for (CartasPartidaDto c : cartasSeleccionadas) {
                            ImageView iv = cartaToImageView.get(c);
                            if (iv != null) shakeNode(iv);
                        }
                    }
                    cartasSeleccionadas.clear();
                }
                event.setDropCompleted(success);
                event.consume();
            });

            hboxTablero.getChildren().add(columna);
        }

        // 2) Agrupar las cartas por columna (modelo)
        Map<Integer, List<CartasPartidaDto>> columnasMap = new HashMap<>();
        for (CartasPartidaDto carta : cartasEnJuego) {
            if (carta.getColumna() != -1) {
                columnasMap
                        .computeIfAbsent(carta.getColumna(), k -> new ArrayList<>())
                        .add(carta);
            }
        }

        // 3) Por cada columna, ordenamos sus cartas y las “pintamos” en pantalla
        for (int col = 0; col < 10; col++) {
            Pane columna = (Pane) hboxTablero.getChildren().get(col);
            columna.getChildren().clear();

            List<CartasPartidaDto> cartasColumna = columnasMap.getOrDefault(col, new ArrayList<>());
            cartasColumna.sort(Comparator.comparingInt(CartasPartidaDto::getOrden));

            double spacing = calcularEspaciadoVertical(cartasColumna.size());

            for (int i = 0; i < cartasColumna.size(); i++) {
                CartasPartidaDto carta = cartasColumna.get(i);
                // Elegir imagen según bocaArriba o estilo clásico
                String imgArchivo = carta.getBocaArriba()
                        ? carta.getImagenNombre()
                        : (usarEstiloClasico ? "rear.png" : "rearS.png");

                ImageView img = new ImageView(new Image(
                        getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/" + imgArchivo)));
                img.setFitWidth(70);
                img.setPreserveRatio(true);
                img.setSmooth(true);
                img.setLayoutY(i * spacing);

                // Guardar referencia para animaciones/shake/drag
                cartaToImageView.put(carta, img);

                if (carta.getBocaArriba()) {
                    // Al hacer clic: autómove
                    img.setOnMouseClicked(e -> {
                        e.consume();
                        cartasSeleccionadas.clear();
                        List<CartasPartidaDto> grupo = obtenerGrupoDesde(carta);
                        cartasSeleccionadas.addAll(grupo);

                        // AUTÓMOVE: buscar destino y mover
                        int destino = buscarMejorDestinoAutoMove(grupo);
                        if (destino != -1) {
                            int colAnterior = carta.getColumna();
                            int ordenAnterior = carta.getOrden();
                            moverCartasSeleccionadas(destino);
                            cartasEnJuego.stream()
                                    .filter(c2 -> c2.getColumna() == colAnterior
                                            && c2.getOrden() == ordenAnterior - 1)
                                    .findFirst()
                                    .ifPresent(c2 -> c2.setBocaArriba(true));
                            cartasSeleccionadas.clear();
                            dibujarColumnasYCargarCartasEnTablero();
                            actualizarVistaDelMazoYPilas();
                        } else {
                            for (CartasPartidaDto c2 : cartaToImageView.keySet()) {
                                // Si no hay destino válido: shake grupo
                            }
                            for (CartasPartidaDto c2 : grupo) {
                                ImageView iv = cartaToImageView.get(c2);
                                if (iv != null) shakeNode(iv);
                            }
                            cartasSeleccionadas.clear();
                        }
                    });

                    // Drag detectado: iniciar arrastre del grupo completo
                    img.setOnDragDetected(e -> {
                        e.consume();
                        cartasSeleccionadas.clear();
                        cartasSeleccionadas.addAll(obtenerGrupoDesde(carta));
                        cartasArrastradas.clear();
                        cartasArrastradas.addAll(cartasSeleccionadas);

                        Dragboard db = img.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(carta.getIdCartaPartida().toString());
                        db.setContent(content);

                        // Ocultar todas las cartas mientras se arrastran
                        for (CartasPartidaDto c2 : cartasSeleccionadas) {
                            ImageView iv = cartaToImageView.get(c2);
                            if (iv != null) iv.setVisible(false);
                        }

                        // Previsualización: si hay varias cartas, apilar snapshot
                        if (cartasSeleccionadas.size() == 1) {
                            ImageView iv = cartaToImageView.get(cartasSeleccionadas.get(0));
                            if (iv != null) db.setDragView(iv.getImage());
                        } else {
                            double width = 70;
                            double height = 22 * (cartasSeleccionadas.size() - 1) + 100;
                            Canvas canvas = new Canvas(width, height);
                            GraphicsContext gc = canvas.getGraphicsContext2D();
                            for (int j = 0; j < cartasSeleccionadas.size(); j++) {
                                ImageView iv = cartaToImageView.get(cartasSeleccionadas.get(j));
                                if (iv != null) {
                                    gc.drawImage(iv.getImage(), 0, j * 22, width, 100);
                                }
                            }
                            SnapshotParameters params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            javafx.scene.image.Image dragImg = canvas.snapshot(params, null);
                            db.setDragView(dragImg);
                        }
                    });

                    // Cuando termina el drag: volver a mostrar las cartas
                    img.setOnDragDone(e -> {
                        e.consume();
                        for (CartasPartidaDto c2 : cartasArrastradas) {
                            ImageView iv = cartaToImageView.get(c2);
                            if (iv != null) iv.setVisible(true);
                        }
                        cartasArrastradas.clear();
                    });
                }

                columna.getChildren().add(img);
            }
        }
    }


    private void actualizarVistaDelMazoYPilas() {
        hboxTableroSuperior.getChildren().clear();

        // 1) Verificar si hay cartas en el mazo:
        boolean hayCartasEnMazo = cartasEnJuego.stream()
                .anyMatch(c -> c.getEnMazo());

        // 2) Si hay cartas en el mazo, creamos el ImageView del mazo con evento click → repartirCartasDelMazo()
        if (hayCartasEnMazo) {
            imgMazo = new ImageView(new Image(getClass().getResourceAsStream(
                    "/cr/ac/una/proyectospider/resources/" +
                            (usarEstiloClasico ? "rear.png" : "rearS.png"))));
            imgMazo.setFitWidth(70);
            imgMazo.setPreserveRatio(true);
            imgMazo.setSmooth(true);
            imgMazo.setOnMouseClicked(e -> repartirCartasDelMazo());
            hboxTableroSuperior.getChildren().add(imgMazo);
        } else {
            // Si no hay cartas en mazo, solo ponemos un espacio vacío de 70px
            Region espacioMazo = new Region();
            espacioMazo.setPrefWidth(70);
            hboxTableroSuperior.getChildren().add(espacioMazo);
        }

        // 3) Añadir un espaciador fijo de 70px al lado derecho del mazo
        Region espacio = new Region();
        espacio.setPrefWidth(70);
        hboxTableroSuperior.getChildren().add(espacio);

        // 4) Contar cuántas secuencias completas (K→A) ya pasaron a pilas
        long secuenciasCompletadas = cartasEnJuego.stream()
                .filter(c -> c.getEnPila())
                .count() / 13; // cada secuencia son 13 cartas

        // 5) Mostrar las 8 pilas (las completadas muestran As; las vacías muestran white.png o whites.png)
        for (int i = 0; i < 8; i++) {
            ImageView pila;
            if (i < secuenciasCompletadas) {
                int pilaActual = i;
                // Tomamos uno de los As de esa secuencia por palo
                String paloSecuencia = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila())
                        .skip(pilaActual * 13)
                        .findFirst()
                        .map(CartasPartidaDto::getPalo)
                        .orElse("C");

                CartasPartidaDto cartaAs = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila()
                                && c.getPalo().equals(paloSecuencia)
                                && c.getValor().equals("1"))
                        .findFirst().orElse(null);

                if (cartaAs != null && cartaAs.getImagenNombre() != null) {
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/" + cartaAs.getImagenNombre())));
                } else {
                    // Si no consigo el As, le pongo un “1C.png” por defecto
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/1C.png")));
                }
            } else {
                // Pila vacía: mostrar “white.png” o “whites.png” según estilo
                String whiteImg = usarEstiloClasico ? "white.png" : "whites.png";
                pila = new ImageView(new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/" + whiteImg)));
            }
            pila.setFitWidth(70);
            pila.setPreserveRatio(true);
            pila.setSmooth(true);
            hboxTableroSuperior.getChildren().add(pila);
        }

        // 6) Verificar si hay victoria (y pop‐up si corresponde):
        verificarVictoria();
    }


    private CartasPartidaDto obtenerUltimaCartaVisible(int columna) {
        return cartasEnJuego.stream()
                .filter(c -> c.getColumna() == columna && c.getBocaArriba() == true)
                .max(Comparator.comparingInt(CartasPartidaDto::getOrden))
                .orElse(null);
    }

    private List<CartasPartidaDto> obtenerGrupoDesde(CartasPartidaDto cartaBase) {
        return cartasEnJuego.stream()
                .filter(c -> Objects.equals(c.getColumna(), cartaBase.getColumna()) &&
                        c.getOrden() >= cartaBase.getOrden())
                .sorted(Comparator.comparingInt(CartasPartidaDto::getOrden))
                .toList();
    }

    private void moverCartasSeleccionadas(int nuevaColumna) {
        iniciarTemporizadorSiEsNecesario();
        movimientos++;
        puntaje = Math.max(0, puntaje - 1);

        int nuevoOrden = cartasEnJuego.stream()
                .filter(c -> c.getColumna() == nuevaColumna)
                .mapToInt(CartasPartidaDto::getOrden)
                .max()
                .orElse(-1) + 1;

        // Evitar mover si ya están en la misma columna y orden
        if (cartasSeleccionadas.size() == 1) {
            CartasPartidaDto carta = cartasSeleccionadas.get(0);
            if (carta.getColumna() == nuevaColumna && carta.getOrden() == nuevoOrden - 1) {
                System.out.println("Movimiento ignorado: carta ya está en la columna y orden destino.");
                return;
            }
        } else if (cartasSeleccionadas.size() > 1) {
            boolean yaEstanEnDestino = true;
            for (int i = 0; i < cartasSeleccionadas.size(); i++) {
                CartasPartidaDto carta = cartasSeleccionadas.get(i);
                if (!(carta.getColumna() == nuevaColumna && carta.getOrden() == nuevoOrden + i)) {
                    yaEstanEnDestino = false;
                    break;
                }
            }
            if (yaEstanEnDestino) {
                System.out.println("Movimiento ignorado: grupo ya está en la columna y orden destino.");
                return;
            }
        }

        System.out.println("Moviendo " + cartasSeleccionadas.size() +
                " cartas a columna " + nuevaColumna + " desde orden " + nuevoOrden);

        for (int i = 0; i < cartasSeleccionadas.size(); i++) {
            CartasPartidaDto carta = cartasSeleccionadas.get(i);

            System.out.printf("%s de %s | Antes: col=%d, orden=%d%n",
                    carta.getValor(), carta.getPalo(), carta.getColumna(), carta.getOrden());

            carta.setColumna(nuevaColumna);
            carta.setOrden(nuevoOrden + i);

            System.out.printf(" Ahora: col=%d, orden=%d%n",
                    carta.getColumna(), carta.getOrden());
        }

        lblMovimientos.setText("" + movimientos);
        lblPuntaje.setText("" + puntaje);

        System.out.println("Movimiento terminado.\n");

        // Verificar si se ha completado una secuencia después del movimiento
        verificarSecuenciaCompleta();
    }

    private boolean esGrupoValido(List<CartasPartidaDto> grupo) {
        if (grupo.isEmpty()) return false;

        String palo = grupo.get(0).getPalo();
        int valor = Integer.parseInt(grupo.get(0).getValor());
        if (grupo.get(0).getBocaArriba() != true) return false;

        for (int i = 1; i < grupo.size(); i++) {
            CartasPartidaDto actual = grupo.get(i);
            int valorActual = Integer.parseInt(actual.getValor());
            // Debe ser descendente y del mismo palo
            if (valorActual != valor - 1 || actual.getBocaArriba() != true || !actual.getPalo().equals(palo)) {
                return false;
            }
            valor = valorActual;
        }
        return true;
    }

    private void verificarSecuenciaCompleta() {
        for (int col = 0; col < 10; col++) {
            final int columna = col;

            List<CartasPartidaDto> cartasColumna = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == columna && c.getBocaArriba())
                    .sorted(Comparator.comparingInt(CartasPartidaDto::getOrden))
                    .toList();

            if (cartasColumna.size() < 13) {
                continue;
            }

            for (int inicio = 0; inicio <= cartasColumna.size() - 13; inicio++) {
                boolean esSecuencia = true;
                String paloSeq = cartasColumna.get(inicio).getPalo();

                for (int i = 0; i < 12; i++) {
                    int valorActual = Integer.parseInt(cartasColumna.get(inicio + i).getValor());
                    int valorSiguiente = Integer.parseInt(cartasColumna.get(inicio + i + 1).getValor());
                    if (!cartasColumna.get(inicio + i + 1).getPalo().equals(paloSeq)
                            || valorSiguiente != valorActual - 1) {
                        esSecuencia = false;
                        break;
                    }
                }

                CartasPartidaDto primera = cartasColumna.get(inicio);
                CartasPartidaDto ultima = cartasColumna.get(inicio + 12);
                if (esSecuencia
                        && Integer.parseInt(primera.getValor()) == 13
                        && Integer.parseInt(ultima.getValor()) == 1) {

                    // Marcar las 13 cartas como “enPila”
                    for (int i = 0; i < 13; i++) {
                        CartasPartidaDto carta = cartasColumna.get(inicio + i);
                        carta.setEnPila(true);
                        carta.setColumna(-1);
                        carta.setOrden(-1);
                    }

                    puntaje += 100;
                    lblPuntaje.setText("" + puntaje);

                    if (inicio > 0) {
                        CartasPartidaDto debajo = cartasColumna.get(inicio - 1);
                        debajo.setBocaArriba(true);
                    } else {
                        // Si no hay cartas visibles restantes, voltear la última carta oculta
                        cartasEnJuego.stream()
                                .filter(c -> c.getColumna() == columna && !c.getBocaArriba())
                                .max(Comparator.comparingInt(CartasPartidaDto::getOrden))
                                .ifPresent(c -> c.setBocaArriba(true));
                    }

                    // *** EN LUGAR DE RunGameView(partidaDto) ***
                    dibujarColumnasYCargarCartasEnTablero();
                    actualizarVistaDelMazoYPilas();
                    return;
                }
            }
        }
    }

    private int buscarMejorDestinoAutoMove(List<CartasPartidaDto> grupo) {
        if (grupo == null || grupo.isEmpty()) return -1;
        CartasPartidaDto cartaOrigen = grupo.get(0);
        int valorOrigen = Integer.parseInt(cartaOrigen.getValor());
        // Buscar en todas las columnas
        for (int col = 0; col < 10; col++) {
            if (col == cartaOrigen.getColumna()) continue;
            CartasPartidaDto cartaDestino = obtenerUltimaCartaVisible(col);
            if (cartaDestino == null) {
                // Columna vacía: aceptar cualquier grupo válido
                if (esGrupoValido(grupo)) return col;
            } else {
                // Permitir mover cualquier grupo válido si el valor es descendente en uno, sin importar el palo
                if (esGrupoValido(grupo)
                        && Integer.parseInt(cartaOrigen.getValor()) == Integer.parseInt(cartaDestino.getValor()) - 1) {
                    return col;
                }
            }
        }
        return -1;
    }

    private void iniciarTemporizadorSiEsNecesario() {
        if (!tiempoIniciado) {
            tiempoIniciado = true;
            segundosTranscurridos = 0;
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                segundosTranscurridos++;
                actualizarLabelTiempo();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    private void detenerTemporizador() {
        if (timeline != null) timeline.stop();
    }

    private void actualizarLabelTiempo() {
        if (lblTiempo != null) {
            int minutos = segundosTranscurridos / 60;
            int segundos = segundosTranscurridos % 60;
            lblTiempo.setText(String.format("%02d:%02d", minutos, segundos));
        }
    }

    private void limpiarPistasVisuales() {
        for (ImageView iv : cartaToImageView.values()) {
            if (iv != null) iv.setStyle("");
        }
        if (imgMazo != null) imgMazo.setStyle("");
    }

    private void animarSugerenciaVisual(List<CartasPartidaDto> grupo, int columnaDestino) {
        if (grupo == null || grupo.isEmpty()) return;
        if (btnPista != null) btnPista.setDisable(true);
        Pane animPane = new Pane();
        animPane.setPickOnBounds(false);
        spGamebackground.getChildren().add(animPane);

        List<ImageView> clones = new ArrayList<>();
        List<Double> origX = new ArrayList<>();
        List<Double> origY = new ArrayList<>();
        List<ImageView> originales = new ArrayList<>();

        for (CartasPartidaDto carta : grupo) {
            ImageView original = cartaToImageView.get(carta);
            if (original == null) continue;
            originales.add(original);
            javafx.geometry.Bounds bounds = original.localToScene(original.getBoundsInLocal());
            javafx.geometry.Bounds parentBounds = spGamebackground.sceneToLocal(bounds);
            ImageView clone = new ImageView(original.getImage());
            clone.setFitWidth(original.getFitWidth());
            clone.setPreserveRatio(true);
            clone.setSmooth(true);
            clone.setLayoutX(parentBounds.getMinX());
            clone.setLayoutY(parentBounds.getMinY());
            clones.add(clone);
            origX.add(parentBounds.getMinX());
            origY.add(parentBounds.getMinY());
            animPane.getChildren().add(clone);
            original.setVisible(false);
        }

        Pane columnaDestinoPane = (Pane) hboxTablero.getChildren().get(columnaDestino);
        double destX = 0, destY = 0;
        javafx.geometry.Bounds destBounds = columnaDestinoPane.localToScene(columnaDestinoPane.getBoundsInLocal());
        javafx.geometry.Bounds destParentBounds = spGamebackground.sceneToLocal(destBounds);
        destX = destParentBounds.getMinX();
        int cartasEnColDest = (int) cartasEnJuego.stream()
                .filter(c -> c.getColumna() == columnaDestino)
                .count();
        double desplazamiento = 22;
        destY = destParentBounds.getMinY() + cartasEnColDest * desplazamiento;

        javafx.animation.ParallelTransition toDest = new javafx.animation.ParallelTransition();
        javafx.animation.ParallelTransition toOrig = new javafx.animation.ParallelTransition();
        for (int i = 0; i < clones.size(); i++) {
            ImageView clone = clones.get(i);
            double targetX = destX;
            double targetY = destY + i * desplazamiento;
            javafx.animation.TranslateTransition go = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(400), clone);
            go.setToX(targetX - origX.get(i));
            go.setToY(targetY - origY.get(i));
            javafx.animation.TranslateTransition back = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(400), clone);
            back.setToX(0);
            back.setToY(0);
            toDest.getChildren().add(go);
            toOrig.getChildren().add(back);
        }

        javafx.animation.SequentialTransition seq = new javafx.animation.SequentialTransition(
                toDest,
                new javafx.animation.PauseTransition(javafx.util.Duration.millis(350)),
                toOrig
        );
        seq.setOnFinished(e -> {
            spGamebackground.getChildren().remove(animPane);
            for (ImageView original : originales) {
                original.setVisible(true);
            }
            if (btnPista != null) btnPista.setDisable(false);
        });
        seq.play();
    }

    public void darPista() {
        limpiarPistasVisuales();
        List<MovimientoSugerido> sugerencias = calcularSugerencias();
        if (!sugerencias.isEmpty()) {
            lastHintIndex = (lastHintIndex + 1) % sugerencias.size();
            MovimientoSugerido mejor = sugerencias.get(lastHintIndex);
            lastHint = mejor;
            for (CartasPartidaDto c : mejor.grupo) {
                ImageView iv = cartaToImageView.get(c);
                if (iv != null)
                    iv.setStyle("-fx-effect: dropshadow(gaussian, #00eaff, 16, 0.7, 0, 0); -fx-border-color: #00eaff; -fx-border-width: 2;");
            }
            if (mejor.destino != null) {
                ImageView iv = cartaToImageView.get(mejor.destino);
                if (iv != null)
                    iv.setStyle("-fx-effect: dropshadow(gaussian, #00eaff, 16, 0.7, 0, 0); -fx-border-color: #00eaff; -fx-border-width: 2;");
            }
            animarSugerenciaVisual(mejor.grupo, mejor.columnaDestino);
        } else {
            lastHintIndex = -1;
            lastHint = null;
            boolean hayCartasEnMazo = cartasEnJuego.stream().anyMatch(c -> c.getEnMazo() == true);
            boolean todasColumnasConCartas = true;
            for (int col = 0; col < 10; col++) {
                final int colFinal2 = col;
                boolean columnaConCartas = cartasEnJuego.stream().anyMatch(c -> c.getColumna() == colFinal2);
                if (!columnaConCartas) {
                    todasColumnasConCartas = false;
                    break;
                }
            }
            if (hayCartasEnMazo && todasColumnasConCartas && imgMazo != null) {
                imgMazo.setStyle("-fx-effect: dropshadow(gaussian, #00eaff, 22, 0.8, 0, 0); -fx-border-color: #00eaff; -fx-border-width: 3;");
            }
        }
    }

    private List<MovimientoSugerido> calcularSugerencias() {
        List<MovimientoSugerido> sugerencias = new ArrayList<>();
        for (int col = 0; col < 10; col++) {
            final int colFinal = col;
            List<CartasPartidaDto> visibles = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == colFinal && c.getBocaArriba() == true)
                    .sorted(Comparator.comparingInt(CartasPartidaDto::getOrden))
                    .toList();
            for (int i = 0; i < visibles.size(); i++) {
                List<CartasPartidaDto> grupo = visibles.subList(i, visibles.size());
                if (!esGrupoValido(grupo)) continue;
                CartasPartidaDto cartaSuperior = grupo.get(0);
                int valorSuperior = Integer.parseInt(cartaSuperior.getValor());
                for (int colDest = 0; colDest < 10; colDest++) {
                    if (colDest == colFinal) continue;
                    CartasPartidaDto cartaDestino = obtenerUltimaCartaVisible(colDest);
                    if (cartaDestino == null) {
                        sugerencias.add(new MovimientoSugerido(grupo, null, colFinal, colDest));
                    } else {
                        int valorDestino = Integer.parseInt(cartaDestino.getValor());
                        if (valorSuperior == valorDestino - 1) {
                            sugerencias.add(new MovimientoSugerido(grupo, cartaDestino, colFinal, colDest));
                        }
                    }
                }
            }
        }
        sugerencias.sort(Comparator.comparingInt((MovimientoSugerido m) -> -m.longitud)
                .thenComparingInt(m -> -m.valorSuperior));
        return sugerencias;
    }

    public void autoClick() {
        MovimientoSugerido sugerencia = lastHint;
        if (sugerencia == null) {
            List<MovimientoSugerido> sugerencias = calcularSugerencias();
            if (!sugerencias.isEmpty()) {
                sugerencia = sugerencias.get(0);
            }
        }
        if (sugerencia != null) {
            cartasSeleccionadas.clear();
            cartasSeleccionadas.addAll(sugerencia.grupo);
            moverCartasSeleccionadas(sugerencia.columnaDestino);
            cartasSeleccionadas.clear();
            RunGameView(partidaDto);
            lastHint = null;
        } else {
            System.out.println("No hay movimientos automáticos disponibles.");
        }
    }

    public boolean verificarVictoria() {
        long cartasEnPila = cartasEnJuego.stream()
                .filter(CartasPartidaDto::getEnPila)
                .count();

        if (cartasEnPila == 104) {
            Platform.runLater(() -> {
                detenerTemporizador();

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("¡Victoria!");
                alert.setHeaderText(null);
                alert.setContentText("¡Felicidades! Has ganado el Solitario Spider.");
                alert.showAndWait();

                primerIngreso = true;
                cartasEnJuego = null;
                cartaToImageView.clear();
                movimientos = 0;
                puntaje = 500;
            });
            return true;
        }
        return false;
    }

    private void shakeNode(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
        tt.setFromX(0);
        tt.setByX(12);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.setOnFinished(e -> node.setTranslateX(0));
        tt.play();
    }

    private List<CartasPartidaDto> obtenerEstadoDelTablero() {
        List<CartasPartidaDto> cartasActuales = new ArrayList<>();

        for (int col = 0; col < 10; col++) {
            Pane columna = (Pane) hboxTablero.getChildren().get(col);
            int orden = 0;

            for (Node node : columna.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView img = (ImageView) node;
                    CartasPartidaDto carta = cartaToImageView.entrySet().stream()
                            .filter(entry -> entry.getValue() == img)
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null);

                    if (carta != null) {
                        carta.setColumna(col);
                        carta.setOrden(orden++);
                        carta.setEnMazo(false);
                        carta.setEnPila(false);
                        cartasActuales.add(carta);
                    }
                }
            }
        }

        cartasEnJuego.stream()
                .filter(c -> c.getEnMazo())
                .forEach(cartasActuales::add);

        cartasEnJuego.stream()
                .filter(c -> c.getEnPila())
                .forEach(cartasActuales::add);

        return cartasActuales;
    }
}


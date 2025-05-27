package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.model.CartasPartidaDto;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.MazoGenerator;
    import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

public class    GameController extends Controller implements Initializable {

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
    private Label lblTitullo;

    @FXML
    private Label lblTiempo;

    @FXML
    private BorderPane root;

    @FXML
    private StackPane spGamebackground;

    @FXML
    private StackPane spTableroBackground;

    private List<CartasPartidaDto> cartasEnJuego;
    private List<CartasPartidaDto> cartasSeleccionadas = new ArrayList<>();
    private List<CartasPartidaDto> cartasArrastradas = new ArrayList<>();
    private boolean esperandoDestino = false;
    private Map<CartasPartidaDto, ImageView> cartaToImageView = new HashMap<>();
    private int puntaje = 500;
    private int movimientos = 0;
    private Timeline timeline;
    private int segundosTranscurridos = 0;
    private boolean tiempoIniciado = false;
    private boolean usarEstiloClasico = false; // Por defecto, usar el estilo moderno

    @FXML
    void oMouseClickedbtnGuardarySalir(MouseEvent event) {

        btnGuardarySalir.setDisable(true);
        AnimationDepartment.stopAllAnimations();
        detenerTemporizador();
        tiempoIniciado = false;
        FlowController.getInstance().goView("MenuView");
        MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
        controller.RunMenuView();
        Platform.runLater(() -> btnGuardarySalir.setDisable(false));

    }

    @FXML
    void oMouseClickedbtnPista(MouseEvent event) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void RunGameView() {
        ResetGameView();
        if (cartasEnJuego == null) {
            cartasEnJuego = MazoGenerator.generarMazoPorDificultad("FACIL", usarEstiloClasico);
        }

        // Detener temporizador y resetear tiempo solo si es un nuevo juego
        if (!tiempoIniciado) {
            segundosTranscurridos = 0;
            actualizarLabelTiempo();
            detenerTemporizador();
        }

        hboxTablero.getChildren().clear();
        hboxTableroSuperior.getChildren().clear();
        imgMazo.setImage(null);

        // Actualizar labels de puntaje y movimientos
        lblPuntaje.setText("Puntaje: " + puntaje);
        lblMovimientos.setText("Movimientos: " + movimientos);
        actualizarLabelTiempo();

        // Creamos las 10 columnas y les añadimos handlers de drag & drop
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

            // Manejar el drop
            columna.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString() && !cartasSeleccionadas.isEmpty()) {
                    // Nos guardamos origen antes de mover
                    CartasPartidaDto cartaOrigen = cartasSeleccionadas.get(0);
                    int colAnterior = cartaOrigen.getColumna();
                    int ordenAnterior = cartaOrigen.getOrden();

                    // Encontramos la carta destino (última visible en la columna destino)
                    CartasPartidaDto cartaDestino = obtenerUltimaCartaVisible(colIndex);
                    boolean puedeMover;
                    if (cartaDestino == null) {
                        // Columna vacía: aceptar cualquier grupo válido
                        puedeMover = esGrupoValido(cartasSeleccionadas);
                    } else {
                        if (cartasSeleccionadas.size() == 1) {
                            // Permitir mover una carta sobre otra solo si el valor de la carta origen es exactamente uno menor que el de la carta destino
                            puedeMover = Integer.parseInt(cartaOrigen.getValor()) == Integer.parseInt(cartaDestino.getValor()) - 1;
                        } else {
                            // Para grupos, exigir secuencia válida (orden descendente y boca arriba, sin importar el palo)
                            puedeMover = esGrupoValido(cartasSeleccionadas)
                                    && Integer.parseInt(cartaOrigen.getValor()) == Integer.parseInt(cartaDestino.getValor()) - 1;
                        }
                    }

                    if (puedeMover) {
                        // 1) Movemos los datos
                        moverCartasSeleccionadas(colIndex);
                        // 2) Volteamos la carta que quedó justo debajo en la columna origen
                        cartasEnJuego.stream()
                                .filter(c -> c.getColumna() == colAnterior
                                        && c.getOrden() == ordenAnterior - 1)
                                .findFirst()
                                .ifPresent(c -> c.setBocaArriba(1));
                        // 3) Refrescamos TODO el tablero
                        RunGameView();
                        success = true;
                    } else {
                        System.out.println("Drop inválido en columna " + colIndex);
                    }
                    cartasSeleccionadas.clear();
                }
                event.setDropCompleted(success);
                event.consume();
            });

            hboxTablero.getChildren().add(columna);
        }

        // Agrupar y ordenar cartas por columna
        Map<Integer, List<CartasPartidaDto>> columnasMap = new HashMap<>();
        for (CartasPartidaDto carta : cartasEnJuego) {
            if (carta.getColumna() != -1) {
                columnasMap
                        .computeIfAbsent(carta.getColumna(), k -> new ArrayList<>())
                        .add(carta);
            }
        }

        double desplazamiento = 22;
        for (int col = 0; col < 10; col++) {
            Pane columna = (Pane) hboxTablero.getChildren().get(col);
            columna.getChildren().clear();

            List<CartasPartidaDto> cartasColumna = columnasMap.getOrDefault(col, new ArrayList<>());
            cartasColumna.sort(Comparator.comparingInt(CartasPartidaDto::getOrden));

            for (CartasPartidaDto carta : cartasColumna) {
                String imgArchivo = carta.getBocaArriba() == 1 ? carta.getImagenNombre() : (usarEstiloClasico ? "rear.png" : "rearS.png");

                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/" + imgArchivo)));
                img.setFitWidth(70);
                img.setPreserveRatio(true);
                img.setSmooth(true);
                img.setLayoutY(carta.getOrden() * desplazamiento);

                cartaToImageView.put(carta, img);

                if (carta.getBocaArriba() == 1) {
                    // Selección por clic (ya existía)
                    img.setOnMouseClicked(e -> {
                        e.consume();
                        if (esperandoDestino && !cartasSeleccionadas.isEmpty()) {
                            CartasPartidaDto cartaDestino = carta;
                            CartasPartidaDto cartaOrigen = cartasSeleccionadas.get(0);
                            boolean puedeMover = esGrupoValido(cartasSeleccionadas)
                                    && Integer.parseInt(cartaOrigen.getValor()) == Integer.parseInt(cartaDestino.getValor()) - 1;
                            if (puedeMover) {
                                int colAnterior = cartaOrigen.getColumna();
                                int ordenAnterior = cartaOrigen.getOrden();
                                int nuevaCol = cartaDestino.getColumna();
                                moverCartasSeleccionadas(nuevaCol);
                                cartasEnJuego.stream()
                                        .filter(c -> c.getColumna() == colAnterior && c.getOrden() == ordenAnterior - 1)
                                        .findFirst()
                                        .ifPresent(c -> c.setBocaArriba(1));
                                esperandoDestino = false;
                                cartasSeleccionadas.clear();
                                RunGameView();
                            } else {
                                System.out.println("Movimiento inválido entre cartas.");
                                esperandoDestino = false;
                                cartasSeleccionadas.clear();
                            }
                        } else {
                            cartasSeleccionadas.clear();
                            List<CartasPartidaDto> grupo = obtenerGrupoDesde(carta);
                            cartasSeleccionadas.addAll(grupo);
                            esperandoDestino = true;
                            // --- AUTO-MOVE: Si solo se hace un clic, buscar destino y mover automáticamente ---
                            int destino = buscarMejorDestinoAutoMove(grupo);
                            if (destino != -1) {
                                int colAnterior = carta.getColumna();
                                int ordenAnterior = carta.getOrden();
                                moverCartasSeleccionadas(destino);
                                cartasEnJuego.stream()
                                        .filter(c2 -> c2.getColumna() == colAnterior && c2.getOrden() == ordenAnterior - 1)
                                        .findFirst()
                                        .ifPresent(c2 -> c2.setBocaArriba(1));
                                esperandoDestino = false;
                                cartasSeleccionadas.clear();
                                RunGameView();
                            } else {
                                // Si no hay destino válido, mantener selección manual
                                System.out.println("No hay destino válido para auto-move");
                            }
                            // --- FIN AUTO-MOVE ---
                        }
                    });

                    // Inicio de drag: cargamos el mismo grupo que con click
                    img.setOnDragDetected(e -> {
                        e.consume();
                        cartasSeleccionadas.clear();
                        cartasSeleccionadas.addAll(obtenerGrupoDesde(carta));
                        // Guarda una copia de las cartas arrastradas
                        cartasArrastradas.clear();
                        cartasArrastradas.addAll(cartasSeleccionadas);
                        System.out.println("DragDetected, seleccionadas: " + cartasSeleccionadas.size());
                        Dragboard db = img.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(carta.getIdCartaPartida().toString());
                        db.setContent(content);
                        // Oculta todas las cartas seleccionadas mientras se arrastran
                        for (CartasPartidaDto c : cartasSeleccionadas) {
                            ImageView iv = cartaToImageView.get(c);
                            if (iv != null) iv.setVisible(false);
                        }
                        // Previsualización de las cartas arrastradas
                        if (cartasSeleccionadas.size() == 1) {
                            ImageView iv = cartaToImageView.get(cartasSeleccionadas.get(0));
                            if (iv != null) db.setDragView(iv.getImage());
                        } else if (cartasSeleccionadas.size() > 1) {
                            // Crear imagen apilada
                            double width = 70;
                            double height = 22 * (cartasSeleccionadas.size() - 1) + 100; // 100 es el alto de la carta
                            Canvas canvas = new Canvas(width, height);
                            GraphicsContext gc = canvas.getGraphicsContext2D();
                            for (int i = 0; i < cartasSeleccionadas.size(); i++) {
                                ImageView iv = cartaToImageView.get(cartasSeleccionadas.get(i));
                                if (iv != null) {
                                    gc.drawImage(iv.getImage(), 0, i * 22, width, 100);
                                }
                            }
                            SnapshotParameters params = new SnapshotParameters();
                            params.setFill(Color.TRANSPARENT);
                            javafx.scene.image.Image dragImg = canvas.snapshot(params, null);
                            db.setDragView(dragImg);
                        }
                    });

                    // Final del drag
                    img.setOnDragDone(e -> {
                        e.consume();
                        // Vuelve a mostrar todas las cartas arrastradas al finalizar el drag
                        for (CartasPartidaDto c : cartasArrastradas) {
                            ImageView iv = cartaToImageView.get(c);
                            if (iv != null) iv.setVisible(true);
                        }
                        cartasArrastradas.clear();
                    });
                }

                columna.getChildren().add(img);
            }
        }

        hboxTableroSuperior.setSpacing(10);

        // Verificar si hay cartas en el mazo
        boolean hayCartasEnMazo = cartasEnJuego.stream()
                .anyMatch(c -> c.getEnMazo() == 1);

        // Solo mostrar el mazo si hay cartas disponibles
        if (hayCartasEnMazo) {
            imgMazo = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/" + (usarEstiloClasico ? "rear.png" : "rearS.png"))));
            imgMazo.setFitWidth(70);
            imgMazo.setPreserveRatio(true);
            imgMazo.setSmooth(true);
            imgMazo.setOnMouseClicked(e -> repartirCartasDelMazo());
            hboxTableroSuperior.getChildren().add(imgMazo);
        } else {
            Region espacioMazo = new Region();
            espacioMazo.setPrefWidth(70);
            hboxTableroSuperior.getChildren().add(espacioMazo);
        }

        Region espacio = new Region();
        espacio.setPrefWidth(70);
        hboxTableroSuperior.getChildren().add(espacio);

        // Contar cuántas secuencias completas se han movido a las pilas
        long secuenciasCompletadas = cartasEnJuego.stream()
                .filter(c -> c.getEnPila() == 1)
                .count() / 13; // Cada secuencia tiene 13 cartas

        System.out.println("Secuencias completadas: " + secuenciasCompletadas);

        // Mostrar las pilas completadas y las vacías
        for (int i = 0; i < 8; i++) {
            ImageView pila;

            if (i < secuenciasCompletadas) {
                int pilaActual = i;
                String paloSecuencia = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila() == 1)
                        .skip(pilaActual * 13)
                        .findFirst()
                        .map(CartasPartidaDto::getPalo)
                        .orElse("C");

                CartasPartidaDto cartaAs = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila() == 1 && c.getPalo().equals(paloSecuencia) && c.getValor().equals("1"))
                        .findFirst()
                        .orElse(null);

                if (cartaAs != null && cartaAs.getImagenNombre() != null) {
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/" + cartaAs.getImagenNombre())));
                } else {
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/1C.png")));
                }
            } else {
                String whiteImg = usarEstiloClasico ? "white.png" : "whites.png";
                pila = new ImageView(new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/" + whiteImg)));
            }

            pila.setFitWidth(70);
            pila.setPreserveRatio(true);
            pila.setSmooth(true);
            hboxTableroSuperior.getChildren().add(pila);
        }
        // Verificar victoria al final de la actualización de la vista
        verificarVictoria();
    }

    public void ResetGameView() {
        hboxTablero.getChildren().clear();
        hboxPilas.getChildren().clear();
        if (!tiempoIniciado) {
            segundosTranscurridos = 0;
            actualizarLabelTiempo();
            detenerTemporizador();
        }
    }

    private CartasPartidaDto obtenerUltimaCartaVisible(int columna) {
        return cartasEnJuego.stream()
                .filter(c -> c.getColumna() == columna && c.getBocaArriba() == 1)
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

        System.out.println("Movimiento terminado.\n");

        // Verificar si se ha completado una secuencia después del movimiento
        verificarSecuenciaCompleta();
    }

    private boolean esGrupoValido(List<CartasPartidaDto> grupo) {
        if (grupo.isEmpty()) return false;

        String palo = grupo.get(0).getPalo();
        int valor = Integer.parseInt(grupo.get(0).getValor());
        if (grupo.get(0).getBocaArriba() != 1) return false;

        for (int i = 1; i < grupo.size(); i++) {
            CartasPartidaDto actual = grupo.get(i);
            int valorActual = Integer.parseInt(actual.getValor());
            // Debe ser descendente y del mismo palo
            if (valorActual != valor - 1 || actual.getBocaArriba() != 1 || !actual.getPalo().equals(palo)) {
                return false;
            }
            valor = valorActual;
        }
        return true;
    }

    /**
     * Reparte una carta del mazo a cada columna del tablero.
     * Siguiendo las reglas del Solitario Spider:
     * - Solo se pueden repartir cartas si todas las columnas tienen al menos una carta
     * - Se reparte una carta a cada columna
     * - Las cartas repartidas se colocan boca arriba
     * - Si no quedan cartas en el mazo, este desaparece
     */
    private void repartirCartasDelMazo() {
        iniciarTemporizadorSiEsNecesario();
        movimientos++;
        puntaje = Math.max(0, puntaje - 1);

        // Verificar si todas las columnas tienen al menos una carta
        boolean todasColumnasConCartas = true;
        for (int colIndex = 0; colIndex < 10; colIndex++) {
            final int col = colIndex; // Crear una variable final para usar en lambda
            boolean columnaConCartas = cartasEnJuego.stream()
                    .anyMatch(c -> c.getColumna() == col);
            if (!columnaConCartas) {
                todasColumnasConCartas = false;
                break;
            }
        }

        if (!todasColumnasConCartas) {
            System.out.println("No se pueden repartir cartas: algunas columnas están vacías");
            return;
        }

        // Obtener cartas del mazo
        List<CartasPartidaDto> cartasEnMazo = cartasEnJuego.stream()
                .filter(c -> c.getEnMazo() == 1)
                .toList();

        if (cartasEnMazo.size() < 10) {
            System.out.println("No hay suficientes cartas en el mazo para repartir");
            return;
        }

        // Repartir una carta a cada columna
        for (int colIndex = 0; colIndex < 10; colIndex++) {
            final int col = colIndex; // Crear una variable final para usar en lambda

            // Encontrar la última carta en la columna actual
            int nuevoOrden = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == col)
                    .mapToInt(CartasPartidaDto::getOrden)
                    .max()
                    .orElse(-1) + 1;

            // Tomar una carta del mazo
            CartasPartidaDto carta = cartasEnMazo.get(colIndex);

            // Actualizar la carta
            carta.setEnMazo(0);
            carta.setColumna(col);
            carta.setOrden(nuevoOrden);
            carta.setBocaArriba(1); // Las cartas repartidas del mazo siempre están boca arriba
        }

        // Verificar si se ha completado una secuencia después de repartir
        verificarSecuenciaCompleta();

        // Actualizar la vista (RunGameView ya verifica si hay cartas en el mazo)
        RunGameView();
    }

    /**
     * Verifica si hay secuencias completas de A a K del mismo palo en alguna columna
     * y las mueve a las pilas superiores.
     * Una secuencia completa consiste en 13 cartas del mismo palo ordenadas de K a A.
     */
    private void verificarSecuenciaCompleta() {
        for (int col = 0; col < 10; col++) {
            final int columna = col;

            List<CartasPartidaDto> cartasColumna = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == columna && c.getBocaArriba() == 1)
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

                    for (int i = 0; i < 13; i++) {
                        CartasPartidaDto carta = cartasColumna.get(inicio + i);
                        carta.setEnPila(1);
                        carta.setColumna(-1);
                        carta.setOrden(-1);
                    }

                    puntaje += 100;

                    if (inicio > 0) {
                        CartasPartidaDto debajo = cartasColumna.get(inicio - 1);
                        debajo.setBocaArriba(1);
                    } else {
                        // Si no hay cartas visibles restantes, voltear la última carta oculta
                        cartasEnJuego.stream()
                                .filter(c -> c.getColumna() == columna && c.getBocaArriba() == 0)
                                .max(Comparator.comparingInt(CartasPartidaDto::getOrden))
                                .ifPresent(c -> c.setBocaArriba(1));
                    }

                    RunGameView();
                    return;
                }
            }
        }
    }

    /**
     * Busca el mejor destino visible para mover una carta o grupo válido,
     * siguiendo las reglas de Spider (descendente, sin importar el palo para movimientos parciales).
     * Devuelve el índice de columna destino, o -1 si no hay destino válido.
     */
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
            lblTiempo.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
        }
    }

    /**
     * Verifica si el jugador ha ganado la partida.
     * Retorna true si hay exactamente 104 cartas con enPila = 1.
     * Si el jugador ha ganado, muestra un mensaje de victoria usando Platform.runLater().
     */
    public boolean verificarVictoria() {
        long cartasEnPila = cartasEnJuego.stream()
                .filter(c -> c.getEnPila() == 1)
                .count();
        if (cartasEnPila == 104) {
            Platform.runLater(() -> {
                detenerTemporizador();
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("¡Victoria!");
                alert.setHeaderText(null);
                alert.setContentText("¡Felicidades! Has ganado el Solitario Spider.");
                alert.showAndWait();
            });
            return true;
        }
        return false;
    }
}

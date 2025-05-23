package cr.ac.una.proyectospider.controller;

import cr.ac.una.proyectospider.model.CartasPartidaDto;
import cr.ac.una.proyectospider.util.AnimationDepartment;
import cr.ac.una.proyectospider.util.FlowController;
import cr.ac.una.proyectospider.util.MazoGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

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
    private Label lblTiempo;

    @FXML
    private Label lblTitullo;

    @FXML
    private BorderPane root;

    @FXML
    private StackPane spGamebackground;

    @FXML
    private StackPane spTableroBackground;

    private List<CartasPartidaDto> cartasEnJuego;
    private List<CartasPartidaDto> cartasSeleccionadas = new ArrayList<>();
   private boolean esperandoDestino = false;



    @FXML
    void oMouseClickedbtnGuardarySalir(MouseEvent event) {

        btnGuardarySalir.setDisable(true);
        AnimationDepartment.stopAllAnimations();

//        AnimationDepartment.glitchFadeOut(spGamebackground, Duration.seconds(1.1), () -> {
            FlowController.getInstance().goView("MenuView");
            MenuController controller = (MenuController) FlowController.getInstance().getController("MenuView");
            controller.RunMenuView();
            Platform.runLater(() -> btnGuardarySalir.setDisable(false));

//        });

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
            cartasEnJuego = MazoGenerator.generarMazoPorDificultad("FACIL");
        }

        hboxTablero.getChildren().clear();
        hboxTableroSuperior.getChildren().clear();
        imgMazo.setImage(null);

        for (int col = 0; col < 10; col++) {
            Pane columna = new Pane();
            columna.setPrefWidth(80);
            columna.setPrefHeight(600);
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
                String imgArchivo = carta.getBocaArriba() == 1 ? carta.getImagenNombre() : "rear.png";

                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/" + imgArchivo)));
                img.setFitWidth(70);
                img.setPreserveRatio(true);
                img.setSmooth(true);
                img.setLayoutY(carta.getOrden() * desplazamiento);

                if (carta.getBocaArriba() == 1) {
                    img.setOnMouseClicked(e -> {
                        e.consume();

                        if (esperandoDestino && !cartasSeleccionadas.isEmpty()) {
                            CartasPartidaDto cartaDestino = carta;
                            CartasPartidaDto cartaOrigen = cartasSeleccionadas.get(0);

                            boolean puedeMover = esGrupoValido(cartasSeleccionadas) &&
                                    cartaDestino.getPalo().equals(cartaOrigen.getPalo()) &&
                                    Integer.parseInt(cartaDestino.getValor()) == Integer.parseInt(cartaOrigen.getValor()) + 1;

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
                            cartasSeleccionadas.addAll(obtenerGrupoDesde(carta));
                            esperandoDestino = true;
                            System.out.println("Seleccionadas: " + cartasSeleccionadas.size());
                        }
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
            imgMazo = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/rear.png")));
            imgMazo.setFitWidth(70);
            imgMazo.setPreserveRatio(true);
            imgMazo.setSmooth(true);
            imgMazo.setOnMouseClicked(e -> repartirCartasDelMazo());
            hboxTableroSuperior.getChildren().add(imgMazo);
        } else {
            // Añadir un espacio vacío donde estaría el mazo
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
                // Mostrar una carta A (as) para representar una secuencia completa
                // Obtenemos el palo de la primera secuencia completada para mostrar su as
                int pilaActual = i;
                String paloSecuencia = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila() == 1)
                        .skip(pilaActual * 13) // Saltamos a la secuencia correspondiente
                        .findFirst()
                        .map(CartasPartidaDto::getPalo)
                        .orElse("C"); // Por defecto corazones si no se encuentra

                // Buscamos la carta A (as) de esa secuencia
                CartasPartidaDto cartaAs = cartasEnJuego.stream()
                        .filter(c -> c.getEnPila() == 1 && c.getPalo().equals(paloSecuencia) && c.getValor().equals("1"))
                        .findFirst()
                        .orElse(null);

                if (cartaAs != null && cartaAs.getImagenNombre() != null) {
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/" + cartaAs.getImagenNombre())));
                } else {
                    // Si no encontramos la carta A, mostramos una carta genérica
                    pila = new ImageView(new Image(getClass().getResourceAsStream(
                            "/cr/ac/una/proyectospider/resources/1C.png")));
                }
            } else {
                // Mostrar un espacio vacío para las pilas no completadas
                pila = new ImageView(new Image(getClass().getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/white.png")));
            }

            pila.setFitWidth(70);
            pila.setPreserveRatio(true);
            pila.setSmooth(true);
            hboxTableroSuperior.getChildren().add(pila);
        }
    }



    public void ResetGameView() {
        hboxTablero.getChildren().clear();
        hboxPilas.getChildren().clear();
//        imgMazo.setImage(null);
//        cartasEnJuego = null;
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
        int nuevoOrden = cartasEnJuego.stream()
                .filter(c -> c.getColumna() == nuevaColumna)
                .mapToInt(CartasPartidaDto::getOrden)
                .max()
                .orElse(-1) + 1;

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

        for (int i = 1; i < grupo.size(); i++) {
            CartasPartidaDto actual = grupo.get(i);
            int valorActual = Integer.parseInt(actual.getValor());

            if (!actual.getPalo().equals(palo) || valorActual != valor - 1) {
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
            // No es necesario ocultar el mazo aquí, ya que RunGameView() lo manejará
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
        // Recorrer cada columna
        for (int columna = 0; columna < 10; columna++) {
            final int col = columna;

            // Obtener todas las cartas de la columna ordenadas por orden (de menor a mayor)
            List<CartasPartidaDto> cartasColumna = cartasEnJuego.stream()
                    .filter(c -> c.getColumna() == col && c.getBocaArriba() == 1)
                    .sorted(Comparator.comparingInt(CartasPartidaDto::getOrden))
                    .toList();

            if (cartasColumna.size() < 13) {
                continue; // No hay suficientes cartas para formar una secuencia completa
            }

            // Buscar secuencias completas empezando desde cada posición posible
            for (int inicio = 0; inicio <= cartasColumna.size() - 13; inicio++) {
                boolean esSecuenciaCompleta = true;
                String paloSecuencia = cartasColumna.get(inicio).getPalo();

                // Verificar si hay 13 cartas consecutivas del mismo palo y en orden descendente (K a A)
                for (int i = 0; i < 12; i++) {
                    CartasPartidaDto cartaActual = cartasColumna.get(inicio + i);
                    CartasPartidaDto cartaSiguiente = cartasColumna.get(inicio + i + 1);

                    int valorActual = Integer.parseInt(cartaActual.getValor());
                    int valorSiguiente = Integer.parseInt(cartaSiguiente.getValor());

                    // Verificar que sean del mismo palo y que el valor disminuya en 1
                    if (!cartaSiguiente.getPalo().equals(paloSecuencia) || valorSiguiente != valorActual - 1) {
                        esSecuenciaCompleta = false;
                        break;
                    }
                }

                // Si encontramos una secuencia completa, moverla a una pila superior
                if (esSecuenciaCompleta) {
                    // Verificar que la secuencia comience con K (13) y termine con A (1)
                    CartasPartidaDto primeraCartaSecuencia = cartasColumna.get(inicio);
                    CartasPartidaDto ultimaCartaSecuencia = cartasColumna.get(inicio + 12);

                    if (Integer.parseInt(primeraCartaSecuencia.getValor()) == 13 && 
                        Integer.parseInt(ultimaCartaSecuencia.getValor()) == 1) {

                        System.out.println("¡Secuencia completa encontrada en columna " + col + "!");

                        // Mover las 13 cartas a una pila superior
                        for (int i = 0; i < 13; i++) {
                            CartasPartidaDto carta = cartasColumna.get(inicio + i);

                            // Marcar la carta como en pila y no en columna
                            carta.setEnPila(1);
                            carta.setColumna(-1);
                            carta.setOrden(-1);

                            System.out.println("Moviendo " + carta.getValor() + " de " + carta.getPalo() + " a pila superior");
                        }

                        // Si hay una carta antes de la secuencia en la columna, voltearla
                        if (inicio > 0) {
                            CartasPartidaDto cartaAnterior = cartasColumna.get(inicio - 1);
                            cartaAnterior.setBocaArriba(1);
                        }

                        // Actualizar la vista después de mover la secuencia
                        Platform.runLater(this::RunGameView);

                        // Continuar verificando el resto de la columna
                        verificarSecuenciaCompleta();
                        return;
                    }
                }
            }
        }
    }

}

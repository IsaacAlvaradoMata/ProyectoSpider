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
            cartasEnJuego = MazoGenerator.generarMazoPorDificultad("MEDIA");
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
        imgMazo = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectospider/resources/rear.png")));
        imgMazo.setFitWidth(70);
        imgMazo.setPreserveRatio(true);
        imgMazo.setSmooth(true);
        hboxTableroSuperior.getChildren().add(imgMazo);

        Region espacio = new Region();
        espacio.setPrefWidth(70);
        hboxTableroSuperior.getChildren().add(espacio);

        for (int i = 0; i < 8; i++) {
            ImageView pila = new ImageView(new Image(getClass().getResourceAsStream(
                    "/cr/ac/una/proyectospider/resources/white.png")));
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

}

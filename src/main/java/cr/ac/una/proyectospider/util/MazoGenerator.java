package cr.ac.una.proyectospider.util;

import cr.ac.una.proyectospider.model.CartasPartidaDto;

import java.util.*;

public class MazoGenerator {

    private static final int TOTAL_BARAJAS = 4;
    private static final int CARTAS_POR_BARAJA = 13;
    private static final int COLUMNAS = 10;

    public static List<CartasPartidaDto> generarMazoPorDificultad(String dificultad) {
        List<CartasPartidaDto> mazo = new ArrayList<>();
        String[] palos;

        switch (dificultad.toUpperCase()) {
            case "FACIL" -> palos = new String[]{"ESPADAS"};
            case "MEDIA" -> palos = new String[]{"ESPADAS", "CORAZONES"};
            case "DIFICIL" -> palos = new String[]{"ESPADAS", "CORAZONES", "DIAMANTES", "TREBOLES"};
            default -> palos = new String[]{"ESPADAS"};
        }

        int repeticiones = 104 / (palos.length * 13);

        for (int i = 0; i < palos.length; i++) {
            String palo = palos[i];
            int numeroPalo = switch (palo) {
                case "ESPADAS" -> 1;
                case "CORAZONES" -> 2;
                case "TREBOLES" -> 3;
                case "DIAMANTES" -> 4;
                default -> throw new IllegalArgumentException("Palo inválido");
            };

            for (int valor = 1; valor <= 13; valor++) {
                for (int j = 0; j < repeticiones; j++) {
                    CartasPartidaDto carta = new CartasPartidaDto();
                    carta.setPalo(palo);
                    carta.setValor(String.valueOf(valor));
                    carta.setColumna(-1);
                    carta.setOrden(-1);
                    carta.setBocaArriba(0);
                    carta.setEnMazo(0);
                    carta.setEnPila(0);
                    carta.setRetirada(0);
                    carta.setImagenNombre(numeroPalo + "-" + valor + ".png"); // ✅ Ej: 1-5.png
                    mazo.add(carta);
                }
            }
        }

        Collections.shuffle(mazo, new Random());
        System.out.println("Cartas generadas: " + mazo.size());

        int[] cartasPorColumna = {6, 6, 6, 6, 5, 5, 5, 5, 5, 5};
        int cartaIndex = 0;

        for (int col = 0; col < 10; col++) {
            for (int j = 0; j < cartasPorColumna[col]; j++) {
                CartasPartidaDto carta = mazo.get(cartaIndex++);
                carta.setColumna(col);
                carta.setOrden(j);
                carta.setEnMazo(0);
                carta.setBocaArriba(0);
            }
        }

        for (int col = 0; col < 10; col++) {
            CartasPartidaDto ultima = null;
            for (CartasPartidaDto carta : mazo.subList(0, cartaIndex)) {
                if (carta.getColumna() == col && (ultima == null || carta.getOrden() > ultima.getOrden())) {
                    ultima = carta;
                }
            }
            if (ultima != null) ultima.setBocaArriba(1);
        }

        for (int i = cartaIndex; i < mazo.size(); i++) {
            CartasPartidaDto carta = mazo.get(i);
            carta.setEnMazo(1);
            carta.setColumna(-1);
            carta.setOrden(-1);
        }

        return mazo;
    }

}

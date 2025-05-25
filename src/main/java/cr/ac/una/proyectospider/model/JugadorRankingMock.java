package cr.ac.una.proyectospider.model;

import javafx.beans.property.SimpleStringProperty;

public class JugadorRankingMock {
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty partidasGanadas;
    private final SimpleStringProperty puntajeTotal;

    public JugadorRankingMock(String nombre, String partidasGanadas, String puntajeTotal) {
        this.nombre = new SimpleStringProperty(nombre);
        this.partidasGanadas = new SimpleStringProperty(partidasGanadas);
        this.puntajeTotal = new SimpleStringProperty(puntajeTotal);
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getPartidasGanadas() {
        return partidasGanadas.get();
    }

    public String getPuntajeTotal() {
        return puntajeTotal.get();
    }
}

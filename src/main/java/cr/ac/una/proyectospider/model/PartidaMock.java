package cr.ac.una.proyectospider.model;

import javafx.beans.property.SimpleStringProperty;

public class PartidaMock {
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty puntaje;
    private final SimpleStringProperty tiempo;

    public PartidaMock(String fecha, String puntaje, String tiempo) {
        this.fecha = new SimpleStringProperty(fecha);
        this.puntaje = new SimpleStringProperty(puntaje);
        this.tiempo = new SimpleStringProperty(tiempo);
    }

    public String getFecha() { return fecha.get(); }
    public String getPuntaje() { return puntaje.get(); }
    public String getTiempo() { return tiempo.get(); }
}


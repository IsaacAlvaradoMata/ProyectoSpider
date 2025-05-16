package cr.ac.una.proyectospider.model;

import cr.ac.una.proyectospider.model.Jugador;
import javafx.beans.property.*;

public class JugadorDto {

    private final ObjectProperty<Long> idJugador;
    private final StringProperty nombreUsuario;
    private final IntegerProperty partidasJugadas;
    private final IntegerProperty partidasGanadas;
    private final IntegerProperty puntosAcumulados;
    private final IntegerProperty estiloCartas;
    private final ObjectProperty<byte[]> imagenFondo;
    private Long version;

    public JugadorDto() {
        this.idJugador = new SimpleObjectProperty<>(null);
        this.nombreUsuario = new SimpleStringProperty();
        this.partidasJugadas = new SimpleIntegerProperty(0);
        this.partidasGanadas = new SimpleIntegerProperty(0);
        this.puntosAcumulados = new SimpleIntegerProperty(0);
        this.estiloCartas = new SimpleIntegerProperty(1);
        this.imagenFondo = new SimpleObjectProperty<>(null);
    }

    public JugadorDto(Jugador jugador) {
        this();
        if (jugador.getIdJugador() != null)
            this.idJugador.set(jugador.getIdJugador().longValue());

        this.nombreUsuario.set(jugador.getNombreUsuario());
        this.partidasJugadas.set(jugador.getPartidasJugadas() != null ? jugador.getPartidasJugadas() : 0);
        this.partidasGanadas.set(jugador.getPartidasGanadas() != null ? jugador.getPartidasGanadas() : 0);
        this.puntosAcumulados.set(jugador.getPuntosAcumulados() != null ? jugador.getPuntosAcumulados() : 0);
        this.estiloCartas.set(jugador.getEstiloCartas() != null ? jugador.getEstiloCartas() : 1);
        this.imagenFondo.set((byte[]) jugador.getImagenFondo());
        this.version = jugador.getVersion();
    }

    public Jugador toEntity() {
        Jugador j = new Jugador();
        if (idJugador.get() != null)
            j.setIdJugador(java.lang.Long.valueOf(idJugador.get()));
        j.setNombreUsuario(nombreUsuario.get());
        j.setPartidasJugadas(partidasJugadas.get());
        j.setPartidasGanadas(partidasGanadas.get());
        j.setPuntosAcumulados(puntosAcumulados.get());
        j.setEstiloCartas(estiloCartas.get());
        j.setImagenFondo(imagenFondo.get());
        j.setVersion(this.version);
        return j;
    }

    // Getters JavaFX para la vista
    public ObjectProperty<Long> idJugadorProperty() { return idJugador; }
    public StringProperty nombreUsuarioProperty() { return nombreUsuario; }
    public IntegerProperty partidasJugadasProperty() { return partidasJugadas; }
    public IntegerProperty partidasGanadasProperty() { return partidasGanadas; }
    public IntegerProperty puntosAcumuladosProperty() { return puntosAcumulados; }
    public IntegerProperty estiloCartasProperty() { return estiloCartas; }
    public ObjectProperty<byte[]> imagenFondoProperty() { return imagenFondo; }
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugadorDto that = (JugadorDto) o;
        return idJugador.get().equals(that.idJugador.get());
    }

    @Override
    public int hashCode() {
        return idJugador.get() != null ? idJugador.get().hashCode() : 0;
    }
    @Override
    public String toString() {
        return "JugadorDto{" +
                "idJugador=" + idJugador.get() +
                ", nombreUsuario=" + nombreUsuario.get() +
                ", partidasJugadas=" + partidasJugadas.get() +
                ", partidasGanadas=" + partidasGanadas.get() +
                ", puntosAcumulados=" + puntosAcumulados.get() +
                ", estiloCartas=" + estiloCartas.get() +
                ", version=" + version +
                '}';
    }



}

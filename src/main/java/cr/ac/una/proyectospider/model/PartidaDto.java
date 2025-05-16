package cr.ac.una.proyectospider.model;

import javafx.beans.property.*;
import java.util.Date;

public class PartidaDto {

    private final ObjectProperty<Long> idPartida;
    private final ObjectProperty<Date> fechaInicio;
    private final ObjectProperty<Date> fechaFin;
    private final IntegerProperty puntos;
    private final IntegerProperty tiempoJugado;
    private final StringProperty estado;
    private final StringProperty dificultad;
    private final ObjectProperty<JugadorDto> jugador;
    private Long version;

    public PartidaDto() {
        this.idPartida = new SimpleObjectProperty<>(null);
        this.fechaInicio = new SimpleObjectProperty<>();
        this.fechaFin = new SimpleObjectProperty<>();
        this.puntos = new SimpleIntegerProperty(0);
        this.tiempoJugado = new SimpleIntegerProperty(0);
        this.estado = new SimpleStringProperty("");
        this.dificultad = new SimpleStringProperty("MEDIA");
        this.jugador = new SimpleObjectProperty<>();
    }

    public PartidaDto(Partida partida) {
        this();
        this.idPartida.set(partida.getIdPartida());
        this.fechaInicio.set(partida.getFechaInicio());
        this.fechaFin.set(partida.getFechaFin());
        this.puntos.set(partida.getPuntos() != null ? partida.getPuntos() : 0);
        this.tiempoJugado.set(partida.getTiempoJugado() != null ? partida.getTiempoJugado() : 0);
        this.estado.set(partida.getEstado());
        this.dificultad.set(partida.getDificultad());
        if (partida.getJugador() != null) {
            this.jugador.set(new JugadorDto(partida.getJugador()));
        }
        this.version = partida.getVersion();
    }

    public Partida toEntity() {
        Partida p = new Partida();
        p.setIdPartida(idPartida.get());
        p.setFechaInicio(fechaInicio.get());
        p.setFechaFin(fechaFin.get());
        p.setPuntos(puntos.get());
        p.setTiempoJugado(tiempoJugado.get());
        p.setEstado(estado.get());
        p.setDificultad(dificultad.get());
        if (jugador.get() != null) {
            p.setJugador(jugador.get().toEntity());
        }
        p.setVersion(this.version);
        return p;
    }

    // Properties
    public ObjectProperty<Long> idPartidaProperty() { return idPartida; }
    public ObjectProperty<Date> fechaInicioProperty() { return fechaInicio; }
    public ObjectProperty<Date> fechaFinProperty() { return fechaFin; }
    public IntegerProperty puntosProperty() { return puntos; }
    public IntegerProperty tiempoJugadoProperty() { return tiempoJugado; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty dificultadProperty() { return dificultad; }
    public ObjectProperty<JugadorDto> jugadorProperty() { return jugador; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartidaDto)) return false;
        PartidaDto that = (PartidaDto) o;
        return idPartida.get() != null && idPartida.get().equals(that.idPartida.get());
    }

    @Override
    public int hashCode() {
        return idPartida.get() != null ? idPartida.get().hashCode() : 0;
    }
}

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
        Partida partida = new Partida();
        partida.setIdPartida(this.getIdPartida());
        partida.setFechaInicio(this.getFechaInicio());
        partida.setFechaFin(this.getFechaFin());
        partida.setPuntos(this.getPuntos());
        partida.setTiempoJugado(this.getTiempoJugado());
        partida.setEstado(this.getEstado());
        partida.setDificultad(this.getDificultad());
        partida.setVersion(this.version);
        return partida;
    }

    // Nuevo método alineado con el constructor Partida(PartidaDto dto)
    public Partida toEntitySinJugador() {
        return new Partida(this);
    }

    // Properties JavaFX
    public ObjectProperty<Long> idPartidaProperty() { return idPartida; }
    public ObjectProperty<Date> fechaInicioProperty() { return fechaInicio; }
    public ObjectProperty<Date> fechaFinProperty() { return fechaFin; }
    public IntegerProperty puntosProperty() { return puntos; }
    public IntegerProperty tiempoJugadoProperty() { return tiempoJugado; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty dificultadProperty() { return dificultad; }
    public ObjectProperty<JugadorDto> jugadorProperty() { return jugador; }

    // Getters normales
    public Long getIdPartida() { return idPartida.get(); }
    public Date getFechaInicio() { return fechaInicio.get(); }
    public Date getFechaFin() { return fechaFin.get(); }
    public Integer getPuntos() { return puntos.get(); }
    public Integer getTiempoJugado() { return tiempoJugado.get(); }
    public String getEstado() { return estado.get(); }
    public String getDificultad() { return dificultad.get(); }
    public JugadorDto getJugador() { return jugador.get(); }
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

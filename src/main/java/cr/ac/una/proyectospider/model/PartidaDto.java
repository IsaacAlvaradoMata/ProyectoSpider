package cr.ac.una.proyectospider.model;

import javafx.beans.property.*;

import java.util.Date;

public class PartidaDto {

    private final ObjectProperty<Long> idPartida;
    private final ObjectProperty<Date> fechaInicio;
    private final ObjectProperty<Date> fechaFin;
    private final IntegerProperty puntos;
    private final IntegerProperty tiempoJugado;
    private final IntegerProperty movimientos;
    private final StringProperty estado;
    private final StringProperty dificultad;
    private final ObjectProperty<JugadorDto> jugador;
    private final StringProperty reversoSeleccionado = new SimpleStringProperty("");
    private Long version;
    private byte[] fondoSeleccionado;

    public PartidaDto() {
        this.idPartida = new SimpleObjectProperty<>(null);
        this.fechaInicio = new SimpleObjectProperty<>();
        this.fechaFin = new SimpleObjectProperty<>();
        this.puntos = new SimpleIntegerProperty(0);
        this.tiempoJugado = new SimpleIntegerProperty(0);
        this.movimientos = new SimpleIntegerProperty(0);
        this.estado = new SimpleStringProperty("");
        this.dificultad = new SimpleStringProperty("MEDIA");
        this.jugador = new SimpleObjectProperty<>();
        this.fondoSeleccionado = null;
    }

    public PartidaDto(Partida partida) {
        this();
        this.idPartida.set(partida.getIdPartida());
        this.fechaInicio.set(partida.getFechaInicio());
        this.fechaFin.set(partida.getFechaFin());
        this.puntos.set(partida.getPuntos() != null ? partida.getPuntos() : 0);
        this.tiempoJugado.set(partida.getTiempoJugado() != null ? partida.getTiempoJugado() : 0);
        this.movimientos.set(partida.getMovimientos() != null ? partida.getMovimientos() : 0);
        this.estado.set(partida.getEstado());
        this.dificultad.set(partida.getDificultad());
        if (partida.getJugador() != null) {
            this.jugador.set(new JugadorDto(partida.getJugador()));
        }
        this.version = partida.getVersion();
        this.fondoSeleccionado = partida.getFondoSeleccionado();
        this.reversoSeleccionado.set(partida.getReversoSeleccionado());
    }

    public Partida toEntitySinJugador() {
        Partida partida = new Partida();
        partida.setIdPartida(this.getIdPartida());
        partida.setFechaInicio(this.getFechaInicio());
        partida.setFechaFin(this.getFechaFin());
        partida.setPuntos(this.getPuntos());
        partida.setTiempoJugado(this.getTiempoJugado());
        partida.setMovimientos(this.getMovimientos());
        partida.setEstado(this.getEstado());
        partida.setDificultad(this.getDificultad());
        partida.setVersion(this.version);
        partida.setFondoSeleccionado(this.getFondoSeleccionado());
        partida.setReversoSeleccionado(this.getReversoSeleccionado());
        return partida;
    }

    public Partida toEntityConJugador(Jugador jugadorEntity) {
        Partida partida = toEntitySinJugador();
        partida.setJugador(jugadorEntity);
        return partida;
    }


    public ObjectProperty<Long> idPartidaProperty() {
        return idPartida;
    }

    public ObjectProperty<Date> fechaInicioProperty() {
        return fechaInicio;
    }

    public ObjectProperty<Date> fechaFinProperty() {
        return fechaFin;
    }

    public IntegerProperty puntosProperty() {
        return puntos;
    }

    public IntegerProperty movimientosProperty() {
        return movimientos;
    }

    public IntegerProperty tiempoJugadoProperty() {
        return tiempoJugado;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public StringProperty dificultadProperty() {
        return dificultad;
    }

    public ObjectProperty<JugadorDto> jugadorProperty() {
        return jugador;
    }

    public StringProperty reversoSeleccionadoProperty() {
        return reversoSeleccionado;
    }

    public String getReversoSeleccionado() {
        return reversoSeleccionado.get();
    }

    public void setReversoSeleccionado(String reverso) {
        this.reversoSeleccionado.set(reverso);
    }

    public Long getIdPartida() {
        return idPartida.get();
    }

    public void setIdPartida(Long id) {
        this.idPartida.set(id);
    }

    public Date getFechaInicio() {
        return fechaInicio.get();
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio.set(fechaInicio);
    }

    public Date getFechaFin() {
        return fechaFin.get();
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin.set(fechaFin);
    }

    public Integer getPuntos() {
        return puntos.get();
    }

    public void setPuntos(Integer puntos) {
        this.puntos.set(puntos);
    }

    public Integer getMovimientos() {
        return movimientos.get();
    }

    public void setMovimientos(Integer m) {
        this.movimientos.set(m);
    }

    public Integer getTiempoJugado() {
        return tiempoJugado.get();
    }

    public void setTiempoJugado(Integer tiempoJugado) {
        this.tiempoJugado.set(tiempoJugado);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getDificultad() {
        return dificultad.get();
    }

    public void setDificultad(String dificultad) {
        this.dificultad.set(dificultad);
    }

    public JugadorDto getJugador() {
        return jugador.get();
    }

    public void setJugador(JugadorDto jugador) {
        this.jugador.set(jugador);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public byte[] getFondoSeleccionado() {
        return fondoSeleccionado;
    }

    public void setFondoSeleccionado(byte[] fondo) {
        this.fondoSeleccionado = fondo;
    }

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

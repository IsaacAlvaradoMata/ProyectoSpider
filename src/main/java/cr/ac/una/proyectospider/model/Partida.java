package cr.ac.una.proyectospider.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PARTIDA", schema = "SPIDER")
@NamedQueries({
        @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
        @NamedQuery(name = "Partida.findByIdPartida", query = "SELECT p FROM Partida p WHERE p.idPartida = :idPartida"),
        @NamedQuery(name = "Partida.findByFechaInicio", query = "SELECT p FROM Partida p WHERE p.fechaInicio = :fechaInicio"),
        @NamedQuery(name = "Partida.findByFechaFin", query = "SELECT p FROM Partida p WHERE p.fechaFin = :fechaFin"),
        @NamedQuery(name = "Partida.findByPuntos", query = "SELECT p FROM Partida p WHERE p.puntos = :puntos"),
        @NamedQuery(name = "Partida.findByTiempoJugado", query = "SELECT p FROM Partida p WHERE p.tiempoJugado = :tiempoJugado"),
        @NamedQuery(name = "Partida.findByEstado", query = "SELECT p FROM Partida p WHERE p.estado = :estado"),
        @NamedQuery(name = "Partida.findByDificultad", query = "SELECT p FROM Partida p WHERE p.dificultad = :dificultad")
})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIDA_SEQ")
    @SequenceGenerator(name = "PARTIDA_SEQ", sequenceName = "SPIDER.SEQ_PARTIDA", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_PARTIDA")
    private Long idPartida;

    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "PUNTOS")
    private Integer puntos;

    @Column(name = "TIEMPO_JUGADO")
    private Integer tiempoJugado;

    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;

    @Basic(optional = false)
    @Column(name = "DIFICULTAD")
    private String dificultad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_JUGADOR", referencedColumnName = "ID_JUGADOR")
    private Jugador jugador;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partida", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CartasPartida> cartasPartidaList;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public Partida() {}

    public Partida(Long idPartida) {
        this.idPartida = idPartida;
    }

    public Partida(PartidaDto dto) {
        this.idPartida = dto.getIdPartida();
        this.fechaInicio = dto.getFechaInicio();
        this.fechaFin = dto.getFechaFin();
        this.puntos = dto.getPuntos();
        this.tiempoJugado = dto.getTiempoJugado();
        this.estado = dto.getEstado();
        this.dificultad = dto.getDificultad();
        this.version = dto.getVersion();
        // jugador y cartas se asignan externamente
    }

    public void actualizar(PartidaDto dto) {
        this.fechaInicio = dto.getFechaInicio();
        this.fechaFin = dto.getFechaFin();
        this.puntos = dto.getPuntos();
        this.tiempoJugado = dto.getTiempoJugado();
        this.estado = dto.getEstado();
        this.dificultad = dto.getDificultad();
        this.version = dto.getVersion();
    }

    // Getters & Setters
    public Long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Long idPartida) {
        this.idPartida = idPartida;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getTiempoJugado() {
        return tiempoJugado;
    }

    public void setTiempoJugado(Integer tiempoJugado) {
        this.tiempoJugado = tiempoJugado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<CartasPartida> getCartasPartidaList() {
        return cartasPartidaList;
    }

    public void setCartasPartidaList(List<CartasPartida> cartasPartidaList) {
        this.cartasPartidaList = cartasPartidaList;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return idPartida != null ? idPartida.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Partida)) return false;
        Partida other = (Partida) obj;
        return this.idPartida != null && this.idPartida.equals(other.idPartida);
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectospider.model.Partida[ idPartida=" + idPartida + " ]";
    }
}
package cr.ac.una.proyectospider.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JUGADOR", schema = "SPIDER")
@NamedQueries({
        @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
        @NamedQuery(name = "Jugador.findByIdJugador", query = "SELECT j FROM Jugador j WHERE j.idJugador = :idJugador"),
        @NamedQuery(name = "Jugador.findByNombreUsuario", query = "SELECT j FROM Jugador j WHERE j.nombreUsuario = :nombreUsuario"),
        @NamedQuery(name = "Jugador.findByPartidasGanadas", query = "SELECT j FROM Jugador j WHERE j.partidasGanadas = :partidasGanadas"),
        @NamedQuery(name = "Jugador.findByPuntosAcumulados", query = "SELECT j FROM Jugador j WHERE j.puntosAcumulados = :puntosAcumulados"),
        @NamedQuery(name = "Jugador.findByEstiloCartas", query = "SELECT j FROM Jugador j WHERE j.estiloCartas = :estiloCartas")
})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JUGADOR_SEQ")
    @SequenceGenerator(name = "JUGADOR_SEQ", sequenceName = "SPIDER.SEQ_JUGADOR", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_JUGADOR")
    private Long idJugador;

    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "PARTIDAS_GANADAS")
    private Integer partidasGanadas;

    @Column(name = "PUNTOS_ACUMULADOS")
    private Integer puntosAcumulados;

    @Column(name = "ESTILO_CARTAS")
    private Integer estiloCartas;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IMAGEN_FONDO")
    private byte[] imagenFondo;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jugador", fetch = FetchType.LAZY)
    private List<Partida> partidaList;

    public Jugador() {
    }

    public Jugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public Jugador(JugadorDto dto) {
        this.idJugador = dto.idJugadorProperty().get();
        actualizar(dto);
    }

    public void actualizar(JugadorDto dto) {
        this.nombreUsuario = dto.nombreUsuarioProperty().get();
        this.partidasGanadas = dto.partidasGanadasProperty().get();
        this.puntosAcumulados = dto.puntosAcumuladosProperty().get();
        this.estiloCartas = dto.estiloCartasProperty().get();
        this.imagenFondo = dto.imagenFondoProperty().get();
    }

    public Long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public Integer getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(Integer puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Integer getEstiloCartas() {
        return estiloCartas;
    }

    public void setEstiloCartas(Integer estiloCartas) {
        this.estiloCartas = estiloCartas;
    }

    public byte[] getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(byte[] imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Partida> getPartidaList() {
        return partidaList;
    }

    public void setPartidaList(List<Partida> partidaList) {
        this.partidaList = partidaList;
    }

    @Override
    public int hashCode() {
        return idJugador != null ? idJugador.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Jugador)) return false;
        Jugador other = (Jugador) object;
        return this.idJugador != null && this.idJugador.equals(other.idJugador);
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectospider.model.Jugador[ idJugador=" + idJugador + " ]";
    }
}

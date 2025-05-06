/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectospider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gambo
 */
@Entity
@Table(name = "PARTIDA", catalog = "", schema = "SPIDER")
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByIdPartida", query = "SELECT p FROM Partida p WHERE p.idPartida = :idPartida"),
    @NamedQuery(name = "Partida.findByFechaInicio", query = "SELECT p FROM Partida p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Partida.findByFechaFin", query = "SELECT p FROM Partida p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "Partida.findByPuntos", query = "SELECT p FROM Partida p WHERE p.puntos = :puntos"),
    @NamedQuery(name = "Partida.findByTiempoJugado", query = "SELECT p FROM Partida p WHERE p.tiempoJugado = :tiempoJugado"),
    @NamedQuery(name = "Partida.findByEstado", query = "SELECT p FROM Partida p WHERE p.estado = :estado"),
    @NamedQuery(name = "Partida.findByDificultad", query = "SELECT p FROM Partida p WHERE p.dificultad = :dificultad")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARTIDA")
    private BigDecimal idPartida;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "PUNTOS")
    private BigInteger puntos;
    @Column(name = "TIEMPO_JUGADO")
    private BigInteger tiempoJugado;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "DIFICULTAD")
    private String dificultad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPartida", fetch = FetchType.LAZY)
    private List<Cartaspartida> cartaspartidaList;
    @JoinColumn(name = "ID_JUGADOR", referencedColumnName = "ID_JUGADOR")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Jugador idJugador;

    public Partida() {
    }

    public Partida(BigDecimal idPartida) {
        this.idPartida = idPartida;
    }

    public Partida(BigDecimal idPartida, String estado, String dificultad) {
        this.idPartida = idPartida;
        this.estado = estado;
        this.dificultad = dificultad;
    }

    public BigDecimal getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(BigDecimal idPartida) {
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

    public BigInteger getPuntos() {
        return puntos;
    }

    public void setPuntos(BigInteger puntos) {
        this.puntos = puntos;
    }

    public BigInteger getTiempoJugado() {
        return tiempoJugado;
    }

    public void setTiempoJugado(BigInteger tiempoJugado) {
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

    public List<Cartaspartida> getCartaspartidaList() {
        return cartaspartidaList;
    }

    public void setCartaspartidaList(List<Cartaspartida> cartaspartidaList) {
        this.cartaspartidaList = cartaspartidaList;
    }

    public Jugador getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Jugador idJugador) {
        this.idJugador = idJugador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPartida != null ? idPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.idPartida == null && other.idPartida != null) || (this.idPartida != null && !this.idPartida.equals(other.idPartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectospider.model.Partida[ idPartida=" + idPartida + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectospider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author gambo
 */
@Entity
@Table(name = "JUGADOR", catalog = "", schema = "SPIDER")
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findByIdJugador", query = "SELECT j FROM Jugador j WHERE j.idJugador = :idJugador"),
    @NamedQuery(name = "Jugador.findByNombreUsuario", query = "SELECT j FROM Jugador j WHERE j.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Jugador.findByPartidasJugadas", query = "SELECT j FROM Jugador j WHERE j.partidasJugadas = :partidasJugadas"),
    @NamedQuery(name = "Jugador.findByPartidasGanadas", query = "SELECT j FROM Jugador j WHERE j.partidasGanadas = :partidasGanadas"),
    @NamedQuery(name = "Jugador.findByPuntosAcumulados", query = "SELECT j FROM Jugador j WHERE j.puntosAcumulados = :puntosAcumulados"),
    @NamedQuery(name = "Jugador.findByEstiloCartas", query = "SELECT j FROM Jugador j WHERE j.estiloCartas = :estiloCartas")})
public class Jugador implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idJugador", fetch = FetchType.LAZY)
    private List<Partida> partidaList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_JUGADOR")
    private BigDecimal idJugador;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "PARTIDAS_JUGADAS")
    private Integer partidasJugadas;
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


    public Jugador() {
    }

    public Jugador(BigDecimal idJugador) {
        this.idJugador = idJugador;
    }

    public Jugador(BigDecimal idJugador, String nombreUsuario) {
        this.idJugador = idJugador;
        this.nombreUsuario = nombreUsuario;
    }

    public BigDecimal getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(BigDecimal idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(Integer partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJugador != null ? idJugador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.idJugador == null && other.idJugador != null) || (this.idJugador != null && !this.idJugador.equals(other.idJugador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectospider.model.Jugador[ idJugador=" + idJugador + " ]";
    }

    public List<Partida> getPartidaList() {
        return partidaList;
    }

    public void setPartidaList(List<Partida> partidaList) {
        this.partidaList = partidaList;
    }
    
}

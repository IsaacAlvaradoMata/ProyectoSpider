/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectospider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gambo
 */
@Entity
@Table(name = "CARTASPARTIDA", catalog = "", schema = "SPIDER")
@NamedQueries({
    @NamedQuery(name = "Cartaspartida.findAll", query = "SELECT c FROM Cartaspartida c"),
    @NamedQuery(name = "Cartaspartida.findByIdCartaPartida", query = "SELECT c FROM Cartaspartida c WHERE c.idCartaPartida = :idCartaPartida"),
    @NamedQuery(name = "Cartaspartida.findByPalo", query = "SELECT c FROM Cartaspartida c WHERE c.palo = :palo"),
    @NamedQuery(name = "Cartaspartida.findByValor", query = "SELECT c FROM Cartaspartida c WHERE c.valor = :valor"),
    @NamedQuery(name = "Cartaspartida.findByColumna", query = "SELECT c FROM Cartaspartida c WHERE c.columna = :columna"),
    @NamedQuery(name = "Cartaspartida.findByOrden", query = "SELECT c FROM Cartaspartida c WHERE c.orden = :orden"),
    @NamedQuery(name = "Cartaspartida.findByBocaArriba", query = "SELECT c FROM Cartaspartida c WHERE c.bocaArriba = :bocaArriba"),
    @NamedQuery(name = "Cartaspartida.findByEnMazo", query = "SELECT c FROM Cartaspartida c WHERE c.enMazo = :enMazo"),
    @NamedQuery(name = "Cartaspartida.findByEnPila", query = "SELECT c FROM Cartaspartida c WHERE c.enPila = :enPila"),
    @NamedQuery(name = "Cartaspartida.findByRetirada", query = "SELECT c FROM Cartaspartida c WHERE c.retirada = :retirada")})
public class Cartaspartida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CARTA_PARTIDA")
    private BigDecimal idCartaPartida;
    @Basic(optional = false)
    @Column(name = "PALO")
    private String palo;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;
    @Basic(optional = false)
    @Column(name = "COLUMNA")
    private BigInteger columna;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Basic(optional = false)
    @Column(name = "BOCA_ARRIBA")
    private BigInteger bocaArriba;
    @Basic(optional = false)
    @Column(name = "EN_MAZO")
    private BigInteger enMazo;
    @Basic(optional = false)
    @Column(name = "EN_PILA")
    private BigInteger enPila;
    @Basic(optional = false)
    @Column(name = "RETIRADA")
    private BigInteger retirada;
    @JoinColumn(name = "ID_PARTIDA", referencedColumnName = "ID_PARTIDA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Partida idPartida;

    public Cartaspartida() {
    }

    public Cartaspartida(BigDecimal idCartaPartida) {
        this.idCartaPartida = idCartaPartida;
    }

    public Cartaspartida(BigDecimal idCartaPartida, String palo, String valor, BigInteger columna, BigInteger orden, BigInteger bocaArriba, BigInteger enMazo, BigInteger enPila, BigInteger retirada) {
        this.idCartaPartida = idCartaPartida;
        this.palo = palo;
        this.valor = valor;
        this.columna = columna;
        this.orden = orden;
        this.bocaArriba = bocaArriba;
        this.enMazo = enMazo;
        this.enPila = enPila;
        this.retirada = retirada;
    }

    public BigDecimal getIdCartaPartida() {
        return idCartaPartida;
    }

    public void setIdCartaPartida(BigDecimal idCartaPartida) {
        this.idCartaPartida = idCartaPartida;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public BigInteger getColumna() {
        return columna;
    }

    public void setColumna(BigInteger columna) {
        this.columna = columna;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public BigInteger getBocaArriba() {
        return bocaArriba;
    }

    public void setBocaArriba(BigInteger bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    public BigInteger getEnMazo() {
        return enMazo;
    }

    public void setEnMazo(BigInteger enMazo) {
        this.enMazo = enMazo;
    }

    public BigInteger getEnPila() {
        return enPila;
    }

    public void setEnPila(BigInteger enPila) {
        this.enPila = enPila;
    }

    public BigInteger getRetirada() {
        return retirada;
    }

    public void setRetirada(BigInteger retirada) {
        this.retirada = retirada;
    }

    public Partida getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Partida idPartida) {
        this.idPartida = idPartida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCartaPartida != null ? idCartaPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartaspartida)) {
            return false;
        }
        Cartaspartida other = (Cartaspartida) object;
        if ((this.idCartaPartida == null && other.idCartaPartida != null) || (this.idCartaPartida != null && !this.idCartaPartida.equals(other.idCartaPartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectospider.model.Cartaspartida[ idCartaPartida=" + idCartaPartida + " ]";
    }
    
}

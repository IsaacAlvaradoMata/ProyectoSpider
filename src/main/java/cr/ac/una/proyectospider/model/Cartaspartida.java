package cr.ac.una.proyectospider.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CARTASPARTIDA", schema = "SPIDER")
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
        @NamedQuery(name = "Cartaspartida.findByRetirada", query = "SELECT c FROM Cartaspartida c WHERE c.retirada = :retirada")
})
public class Cartaspartida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTASPARTIDA_SEQ")
    @SequenceGenerator(name = "CARTASPARTIDA_SEQ", sequenceName = "SPIDER.SEQ_CARTASPARTIDA", allocationSize = 1)
    @Column(name = "ID_CARTA_PARTIDA")
    private Long idCartaPartida;

    @Column(name = "PALO", nullable = false)
    private String palo;

    @Column(name = "VALOR", nullable = false)
    private String valor;

    @Column(name = "COLUMNA", nullable = false)
    private Integer columna;

    @Column(name = "ORDEN", nullable = false)
    private Integer orden;

    @Column(name = "BOCA_ARRIBA", nullable = false)
    private Integer bocaArriba;

    @Column(name = "EN_MAZO", nullable = false)
    private Integer enMazo;

    @Column(name = "EN_PILA", nullable = false)
    private Integer enPila;

    @Column(name = "RETIRADA", nullable = false)
    private Integer retirada;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARTIDA", referencedColumnName = "ID_PARTIDA", nullable = false)
    private Partida partida;

    // Getters and Setters
    public Long getIdCartaPartida() {
        return idCartaPartida;
    }

    public void setIdCartaPartida(Long idCartaPartida) {
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

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getBocaArriba() {
        return bocaArriba;
    }

    public void setBocaArriba(Integer bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    public Integer getEnMazo() {
        return enMazo;
    }

    public void setEnMazo(Integer enMazo) {
        this.enMazo = enMazo;
    }

    public Integer getEnPila() {
        return enPila;
    }

    public void setEnPila(Integer enPila) {
        this.enPila = enPila;
    }

    public Integer getRetirada() {
        return retirada;
    }

    public void setRetirada(Integer retirada) {
        this.retirada = retirada;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cartaspartida)) return false;
        Cartaspartida that = (Cartaspartida) o;
        return idCartaPartida != null && idCartaPartida.equals(that.idCartaPartida);
    }

    @Override
    public int hashCode() {
        return idCartaPartida != null ? idCartaPartida.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cartaspartida[ idCartaPartida=" + idCartaPartida + " ]";
    }
}

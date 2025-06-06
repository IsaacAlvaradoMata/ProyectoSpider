package cr.ac.una.proyectospider.model;

import cr.ac.una.proyectospider.util.BooleanToIntegerConverter;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CARTASPARTIDA", schema = "SPIDER")
@NamedQueries({
        @NamedQuery(name = "CartasPartida.findAll", query = "SELECT c FROM CartasPartida c"),
        @NamedQuery(name = "CartasPartida.findByIdCartaPartida", query = "SELECT c FROM CartasPartida c WHERE c.idCartaPartida = :idCartaPartida"),
        @NamedQuery(name = "CartasPartida.findByPalo", query = "SELECT c FROM CartasPartida c WHERE c.palo = :palo"),
        @NamedQuery(name = "CartasPartida.findByValor", query = "SELECT c FROM CartasPartida c WHERE c.valor = :valor"),
        @NamedQuery(name = "CartasPartida.findByColumna", query = "SELECT c FROM CartasPartida c WHERE c.columna = :columna"),
        @NamedQuery(name = "CartasPartida.findByOrden", query = "SELECT c FROM CartasPartida c WHERE c.orden = :orden"),
        @NamedQuery(name = "CartasPartida.findByBocaArriba", query = "SELECT c FROM CartasPartida c WHERE c.bocaArriba = :bocaArriba"),
        @NamedQuery(name = "CartasPartida.findByEnMazo", query = "SELECT c FROM CartasPartida c WHERE c.enMazo = :enMazo"),
        @NamedQuery(name = "CartasPartida.findByEnPila", query = "SELECT c FROM CartasPartida c WHERE c.enPila = :enPila"),
        @NamedQuery(name = "CartasPartida.findByRetirada", query = "SELECT c FROM CartasPartida c WHERE c.retirada = :retirada"),
        @NamedQuery(name = "CartasPartida.findByNombreCarta", query = "SELECT c FROM CartasPartida c WHERE c.nombreCarta = :nombreCarta")
})
public class CartasPartida implements Serializable {

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

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "BOCA_ARRIBA")
    private Boolean bocaArriba;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "EN_MAZO", nullable = false)
    private Boolean enMazo;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "EN_PILA", nullable = false)
    private Boolean enPila;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "RETIRADA", nullable = false)
    private Boolean retirada;

    @Column(name = "NOMBRE_CARTA")
    private String nombreCarta;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARTIDA", referencedColumnName = "ID_PARTIDA", nullable = false)
    private Partida partida;

    // --- Constructors ---
    public CartasPartida() {}

    public CartasPartida(CartasPartidaDto dto) {
        this.idCartaPartida = dto.getIdCartaPartida();
        actualizar(dto);
    }

    public void actualizar(CartasPartidaDto dto) {
        this.palo = dto.getPalo();
        this.valor = dto.getValor();
        this.columna = dto.getColumna();
        this.orden = dto.getOrden();
        this.bocaArriba = dto.getBocaArriba();
        this.enMazo = dto.getEnMazo();
        this.enPila = dto.getEnPila();
        this.retirada = dto.getRetirada();
        this.nombreCarta = dto.getImagenNombre(); // <--- ahora sí se asigna correctamente
        this.version = dto.getVersion();
    }

    // --- Getters y Setters ---
    public Long getIdCartaPartida() { return idCartaPartida; }
    public void setIdCartaPartida(Long idCartaPartida) { this.idCartaPartida = idCartaPartida; }

    public String getPalo() { return palo; }
    public void setPalo(String palo) { this.palo = palo; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public Integer getColumna() { return columna; }
    public void setColumna(Integer columna) { this.columna = columna; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Boolean getBocaArriba() { return bocaArriba; }
    public void setBocaArriba(Boolean bocaArriba) { this.bocaArriba = bocaArriba; }

    public Boolean getEnMazo() { return enMazo; }
    public void setEnMazo(Boolean enMazo) { this.enMazo = enMazo; }

    public Boolean getEnPila() { return enPila; }
    public void setEnPila(Boolean enPila) { this.enPila = enPila; }

    public Boolean getRetirada() { return retirada; }
    public void setRetirada(Boolean retirada) { this.retirada = retirada; }

    public String getNombreCarta() { return nombreCarta; }
    public void setNombreCarta(String nombreCarta) { this.nombreCarta = nombreCarta; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }

    // --- Equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartasPartida)) return false;
        CartasPartida that = (CartasPartida) o;
        return idCartaPartida != null && idCartaPartida.equals(that.idCartaPartida);
    }

    @Override
    public int hashCode() {
        return idCartaPartida != null ? idCartaPartida.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CartasPartida[ idCartaPartida=" + idCartaPartida + " ]";
    }
}

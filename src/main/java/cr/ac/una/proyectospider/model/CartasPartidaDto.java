package cr.ac.una.proyectospider.model;

import javafx.beans.property.*;

public class CartasPartidaDto {

    private final LongProperty idCartaPartida;
    private final StringProperty palo;
    private final StringProperty valor;
    private final IntegerProperty columna;
    private final IntegerProperty orden;
    private final IntegerProperty bocaArriba;
    private final IntegerProperty enMazo;
    private final IntegerProperty enPila;
    private final IntegerProperty retirada;
    private final ObjectProperty<PartidaDto> partida;
    private Long version;

    public CartasPartidaDto() {
        this.idCartaPartida = new SimpleLongProperty();
        this.palo = new SimpleStringProperty();
        this.valor = new SimpleStringProperty();
        this.columna = new SimpleIntegerProperty();
        this.orden = new SimpleIntegerProperty();
        this.bocaArriba = new SimpleIntegerProperty();
        this.enMazo = new SimpleIntegerProperty();
        this.enPila = new SimpleIntegerProperty();
        this.retirada = new SimpleIntegerProperty();
        this.partida = new SimpleObjectProperty<>();
    }

    public CartasPartidaDto(Cartaspartida entity) {
        this();
        this.idCartaPartida.set(entity.getIdCartaPartida());
        this.palo.set(entity.getPalo());
        this.valor.set(entity.getValor());
        this.columna.set(entity.getColumna());
        this.orden.set(entity.getOrden());
        this.bocaArriba.set(entity.getBocaArriba());
        this.enMazo.set(entity.getEnMazo());
        this.enPila.set(entity.getEnPila());
        this.retirada.set(entity.getRetirada());
        this.version = entity.getVersion();

        if (entity.getPartida() != null) {
            this.partida.set(new PartidaDto(entity.getPartida()));
        }
    }

    public Cartaspartida toEntity() {
        Cartaspartida entity = new Cartaspartida();
        entity.setIdCartaPartida(this.idCartaPartida.get());
        entity.setPalo(this.palo.get());
        entity.setValor(this.valor.get());
        entity.setColumna(this.columna.get());
        entity.setOrden(this.orden.get());
        entity.setBocaArriba(this.bocaArriba.get());
        entity.setEnMazo(this.enMazo.get());
        entity.setEnPila(this.enPila.get());
        entity.setRetirada(this.retirada.get());
        entity.setVersion(this.version);

        if (this.partida.get() != null) {
            entity.setPartida(this.partida.get().toEntity());
        }

        return entity;
    }

    // Properties
    public LongProperty idCartaPartidaProperty() { return idCartaPartida; }
    public StringProperty paloProperty() { return palo; }
    public StringProperty valorProperty() { return valor; }
    public IntegerProperty columnaProperty() { return columna; }
    public IntegerProperty ordenProperty() { return orden; }
    public IntegerProperty bocaArribaProperty() { return bocaArriba; }
    public IntegerProperty enMazoProperty() { return enMazo; }
    public IntegerProperty enPilaProperty() { return enPila; }
    public IntegerProperty retiradaProperty() { return retirada; }
    public ObjectProperty<PartidaDto> partidaProperty() { return partida; }

    // Manual getters and setters
    public Long getIdCartaPartida() { return idCartaPartida.get(); }
    public void setIdCartaPartida(Long id) { this.idCartaPartida.set(id); }

    public String getPalo() { return palo.get(); }
    public void setPalo(String p) { this.palo.set(p); }

    public String getValor() { return valor.get(); }
    public void setValor(String v) { this.valor.set(v); }

    public Integer getColumna() { return columna.get(); }
    public void setColumna(Integer c) { this.columna.set(c); }

    public Integer getOrden() { return orden.get(); }
    public void setOrden(Integer o) { this.orden.set(o); }

    public Integer getBocaArriba() { return bocaArriba.get(); }
    public void setBocaArriba(Integer b) { this.bocaArriba.set(b); }

    public Integer getEnMazo() { return enMazo.get(); }
    public void setEnMazo(Integer m) { this.enMazo.set(m); }

    public Integer getEnPila() { return enPila.get(); }
    public void setEnPila(Integer p) { this.enPila.set(p); }

    public Integer getRetirada() { return retirada.get(); }
    public void setRetirada(Integer r) { this.retirada.set(r); }

    public PartidaDto getPartida() { return partida.get(); }
    public void setPartida(PartidaDto partida) { this.partida.set(partida); }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    @Override
    public String toString() {
        return "CartasPartidaDto{" +
                "id=" + idCartaPartida.get() +
                ", palo='" + palo.get() + '\'' +
                ", valor='" + valor.get() + '\'' +
                '}';
    }
}

package cr.ac.una.proyectospider.model;

import javafx.beans.property.*;

public class CartasPartidaDto {

    private final LongProperty idCartaPartida;
    private final StringProperty palo;
    private final StringProperty valor;
    private final IntegerProperty columna;
    private final IntegerProperty orden;
    private final BooleanProperty bocaArriba;
    private final BooleanProperty enMazo;
    private final BooleanProperty enPila;
    private final BooleanProperty retirada;
    private final ObjectProperty<PartidaDto> partida;
    private String imagenNombre;

    public CartasPartidaDto() {
        this.idCartaPartida = new SimpleLongProperty();
        this.palo = new SimpleStringProperty();
        this.valor = new SimpleStringProperty();
        this.columna = new SimpleIntegerProperty();
        this.orden = new SimpleIntegerProperty();
        this.bocaArriba = new SimpleBooleanProperty();
        this.enMazo = new SimpleBooleanProperty();
        this.enPila = new SimpleBooleanProperty();
        this.retirada = new SimpleBooleanProperty();
        this.partida = new SimpleObjectProperty<>();
    }

    public CartasPartidaDto(CartasPartida entity) {
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
        this.imagenNombre = entity.getNombreCarta();
        if (entity.getPartida() != null) {
            this.partida.set(new PartidaDto(entity.getPartida()));
        }
    }

    public CartasPartida toEntity() {
        throw new IllegalStateException("⚠️ No usar toEntity() sin referencia de Partida gestionada. Usa toEntity(Partida partidaRef).");
    }

    public CartasPartida toEntity(Partida partidaRef) {
        CartasPartida entity = new CartasPartida();
        entity.setIdCartaPartida(this.idCartaPartida.get());
        entity.setPalo(this.palo.get());
        entity.setValor(this.valor.get());
        entity.setColumna(this.columna.get());
        entity.setOrden(this.orden.get());
        entity.setBocaArriba(this.bocaArriba.get());
        entity.setEnMazo(this.enMazo.get());
        entity.setEnPila(this.enPila.get());
        entity.setRetirada(this.retirada.get());
        entity.setPartida(partidaRef);
        entity.setNombreCarta(this.imagenNombre);
        return entity;
    }

    public LongProperty idCartaPartidaProperty() { return idCartaPartida; }
    public StringProperty paloProperty() { return palo; }
    public StringProperty valorProperty() { return valor; }
    public IntegerProperty columnaProperty() { return columna; }
    public IntegerProperty ordenProperty() { return orden; }
    public BooleanProperty bocaArribaProperty() { return bocaArriba; }
    public BooleanProperty enMazoProperty() { return enMazo; }
    public BooleanProperty enPilaProperty() { return enPila; }
    public BooleanProperty retiradaProperty() { return retirada; }
    public ObjectProperty<PartidaDto> partidaProperty() { return partida; }

    // Getters y setters
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

    public Boolean getBocaArriba() { return bocaArriba.get(); }
    public void setBocaArriba(Boolean b) { this.bocaArriba.set(b); }

    public Boolean getEnMazo() { return enMazo.get(); }
    public void setEnMazo(Boolean m) { this.enMazo.set(m); }

    public Boolean getEnPila() { return enPila.get(); }
    public void setEnPila(Boolean p) { this.enPila.set(p); }

    public Boolean getRetirada() { return retirada.get(); }
    public void setRetirada(Boolean r) { this.retirada.set(r); }

    public PartidaDto getPartida() { return partida.get(); }
    public void setPartida(PartidaDto partida) { this.partida.set(partida); }
    public String getImagenNombre() { return imagenNombre; }
    public void setImagenNombre(String imagenNombre) { this.imagenNombre = imagenNombre; }

    @Override
    public String toString() {
        return "CartasPartidaDto{" +
                "id=" + idCartaPartida.get() +
                ", palo='" + palo.get() + '\'' +
                ", valor='" + valor.get() + '\'' +
                '}';
    }
}

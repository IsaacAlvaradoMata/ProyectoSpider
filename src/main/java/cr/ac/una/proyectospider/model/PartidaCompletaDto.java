package cr.ac.una.proyectospider.model;

import java.util.List;

public class PartidaCompletaDto {
    private PartidaDto partida;
    private List<CartasPartidaDto> cartas;

    public PartidaCompletaDto(PartidaDto partida, List<CartasPartidaDto> cartas) {
        this.partida = partida;
        this.cartas = cartas;
    }

    public PartidaDto getPartida() {
        return partida;
    }

    public List<CartasPartidaDto> getCartas() {
        return cartas;
    }
}
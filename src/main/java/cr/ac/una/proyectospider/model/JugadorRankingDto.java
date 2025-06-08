package cr.ac.una.proyectospider.model;

public class JugadorRankingDto {
    private final String nombreUsuario;
    private final Integer partidasGanadas;
    private final Integer puntosAcumulados;

    public JugadorRankingDto(String nombreUsuario,
                             Integer partidasGanadas,
                             Integer puntosAcumulados) {
        this.nombreUsuario    = nombreUsuario;
        this.partidasGanadas  = partidasGanadas;
        this.puntosAcumulados = puntosAcumulados;
    }
    public String  getNombreUsuario()   { return nombreUsuario; }
    public Integer getPartidasGanadas() { return partidasGanadas; }
    public Integer getPuntosAcumulados(){ return puntosAcumulados; }
}

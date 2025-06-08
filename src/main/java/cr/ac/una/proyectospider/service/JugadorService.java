package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.Jugador;
import cr.ac.una.proyectospider.model.JugadorDto;
import cr.ac.una.proyectospider.model.PartidaDto;
import cr.ac.una.proyectospider.model.JugadorRankingDto;
import jakarta.persistence.*;

import java.util.List;

public class JugadorService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cr.ac.una_ProyectoSpider_jar_1.0-SNAPSHOTPU");

    public JugadorDto registrarJugador(String nombreUsuario) {
        EntityManager em = emf.createEntityManager();

        try {
            Jugador existente = em.createNamedQuery("Jugador.findByNombreUsuario", Jugador.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existente != null) {
                return null;
            }

            Jugador nuevo = new Jugador();
            nuevo.setNombreUsuario(nombreUsuario);
            nuevo.setPartidasGanadas(0);
            nuevo.setPuntosAcumulados(0);
            nuevo.setEstiloCartas(1);
            nuevo.setImagenFondo(null);

            em.getTransaction().begin();
            em.persist(nuevo);
            em.getTransaction().commit();

            return new JugadorDto(nuevo);
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al registrar jugador: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public JugadorDto buscarJugadorPorNombre(String nombreUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            Jugador jugador = em.createNamedQuery("Jugador.findByNombreUsuario", Jugador.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            return jugador != null ? new JugadorDto(jugador) : null;
        } finally {
            em.close();
        }
    }

    public void actualizarPersonalizacion(Long idJugador, byte[] imagenFondo, byte[] imagenReverso, byte[] imagenFrente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Jugador jugador = em.find(Jugador.class, idJugador);
            if (jugador != null) {
                jugador.setImagenFondo(imagenFondo);
                em.merge(jugador);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al actualizar personalización: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizarEstadisticas(Long idJugador) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Jugador jugador = em.find(Jugador.class, idJugador);
            if (jugador != null) {
                // Obtener partidas terminadas usando PartidaService
                PartidaService partidaService = new PartidaService();
                java.util.List<PartidaDto> partidasTerminadas = partidaService.listarTerminadasPorJugador(idJugador);
                int partidasGanadas = partidasTerminadas.size();
                int puntosAcumulados = partidasTerminadas.stream()
                    .mapToInt(p -> p.getPuntos() != null ? p.getPuntos() : 0)
                    .sum();
                jugador.setPartidasGanadas(partidasGanadas);
                jugador.setPuntosAcumulados(puntosAcumulados);
                em.merge(jugador);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("❌ Error al actualizar estadísticas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<JugadorRankingDto> getRankingPorPuntaje() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT new cr.ac.una.proyectospider.model.JugadorRankingDto(" +
                            "  j.nombreUsuario, j.partidasGanadas, j.puntosAcumulados) " +
                            "FROM Jugador j " +
                            "ORDER BY j.puntosAcumulados DESC",
                    JugadorRankingDto.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public List<JugadorRankingDto> getRankingPorFiltro(String filtro) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT new cr.ac.una.proyectospider.model.JugadorRankingDto(" +
                                    "  j.nombreUsuario, j.partidasGanadas, j.puntosAcumulados) " +
                                    "FROM Jugador j " +
                                    "WHERE LOWER(j.nombreUsuario) LIKE :filtro " +
                                    "ORDER BY j.puntosAcumulados DESC",
                            JugadorRankingDto.class
                    )
                    .setParameter("filtro", "%" + filtro.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
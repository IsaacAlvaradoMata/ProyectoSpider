package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.CartasPartida;
import cr.ac.una.proyectospider.model.CartasPartidaDto;
import cr.ac.una.proyectospider.model.Partida;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio dedicado exclusivamente a operaciones CRUD sobre CartasPartida.
 * Usado por PartidaService (delegación).
 */
public class CartasPartidaService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cr.ac.una_ProyectoSpider_jar_1.0-SNAPSHOTPU");

    public boolean guardarCarta(CartasPartidaDto cartaDto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Long idPartida = cartaDto.getPartida().getIdPartida();
            Partida partidaRef = em.getReference(Partida.class, idPartida);

            CartasPartida carta = cartaDto.toEntity(partidaRef);

            if (carta.getIdCartaPartida() == null) {
                em.persist(carta);
            } else {
                em.merge(carta);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al guardar carta: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean guardarCartasPorPartida(Long idPartida, List<CartasPartidaDto> cartas) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM CartasPartida c WHERE c.partida.idPartida = :idPartida")
                    .setParameter("idPartida", idPartida)
                    .executeUpdate();

            Partida partidaRef = em.getReference(Partida.class, idPartida);
            for (CartasPartidaDto dto : cartas) {
                CartasPartida carta = dto.toEntity(partidaRef);
                em.persist(carta);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al guardar cartas por partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminarCartasPorPartida(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM CartasPartida c WHERE c.partida.idPartida = :idPartida");
            query.setParameter("idPartida", idPartida);
            query.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al eliminar cartas de partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public List<CartasPartidaDto> listarPorPartida(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CartasPartida> query = em.createQuery(
                    "SELECT c FROM CartasPartida c WHERE c.partida.idPartida = :idPartida ORDER BY c.orden",
                    CartasPartida.class
            );
            query.setParameter("idPartida", idPartida);
            return query.getResultList().stream().map(CartasPartidaDto::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}

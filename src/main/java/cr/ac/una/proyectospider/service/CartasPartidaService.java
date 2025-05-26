package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.CartasPartida;
import cr.ac.una.proyectospider.model.CartasPartidaDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class CartasPartidaService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cr.ac.una_ProyectoSpider_jar_1.0-SNAPSHOTPU");

    public boolean guardarCarta(CartasPartidaDto cartaDto) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            CartasPartida carta = cartaDto.toEntity();
            carta.setPartida(em.getReference(carta.getPartida().getClass(), carta.getPartida().getIdPartida()));

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

            return query.getResultList()
                    .stream()
                    .map(CartasPartidaDto::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}

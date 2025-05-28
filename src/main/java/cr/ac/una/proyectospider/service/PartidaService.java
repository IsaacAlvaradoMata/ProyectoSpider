package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class PartidaService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cr.ac.una_ProyectoSpider_jar_1.0-SNAPSHOTPU");

    public PartidaDto crearPartida(PartidaDto partidaDto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Partida partida = partidaDto.toEntitySinJugador();
            Jugador jugadorRef = em.getReference(Jugador.class, partidaDto.jugadorProperty().get().idJugadorProperty().get());
            partida.setJugador(jugadorRef);

            em.persist(partida);
            em.getTransaction().commit();

            return new PartidaDto(partida);
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al crear partida: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public PartidaDto buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Partida partida = em.find(Partida.class, id);
            return partida != null ? new PartidaDto(partida) : null;
        } finally {
            em.close();
        }
    }

    public List<PartidaDto> listarPausadasPorJugador(Long idJugador) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partida> query = em.createQuery(
                    "SELECT p FROM Partida p WHERE p.jugador.idJugador = :idJugador AND p.estado = 'PAUSADA' ORDER BY p.fechaInicio DESC",
                    Partida.class
            );
            query.setParameter("idJugador", idJugador);
            return query.getResultList().stream().map(PartidaDto::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    public PartidaDto cargarPartidaCompleta(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        try {
            Partida partida = em.createQuery(
                    "SELECT p FROM Partida p LEFT JOIN FETCH p.cartasPartidaList WHERE p.idPartida = :id",
                    Partida.class
            ).setParameter("id", idPartida).getSingleResult();

            return new PartidaDto(partida);
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean guardarPartidaCompleta(PartidaDto partidaDto, List<CartasPartidaDto> cartas) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Partida partida = partidaDto.toEntitySinJugador();
            partida.setJugador(em.getReference(Jugador.class, partidaDto.getJugador().idJugadorProperty().get()));

            // Guardar la partida
            Partida managed = em.merge(partida);

            // Eliminar cartas previas (limpieza segura)
            em.createQuery("DELETE FROM CartasPartida c WHERE c.partida.idPartida = :id")
                    .setParameter("id", managed.getIdPartida())
                    .executeUpdate();

            // Agregar nuevas cartas
            for (CartasPartidaDto cartaDto : cartas) {
                CartasPartida carta = cartaDto.toEntity();
                carta.setPartida(managed);
                em.persist(carta);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al guardar partida completa: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public List<PartidaDto> listarPorJugador(Long idJugador) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Partida> query = em.createQuery(
                    "SELECT p FROM Partida p WHERE p.jugador.idJugador = :idJugador ORDER BY p.fechaInicio DESC",
                    Partida.class
            );
            query.setParameter("idJugador", idJugador);
            return query.getResultList().stream().map(PartidaDto::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    public boolean actualizarPartida(PartidaDto partidaDto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Partida partida = em.find(Partida.class, partidaDto.getIdPartida());
            if (partida == null) return false;

            partida.setFechaFin(partidaDto.getFechaFin());
            partida.setEstado(partidaDto.getEstado());
            partida.setPuntos(partidaDto.getPuntos());
            partida.setTiempoJugado(partidaDto.getTiempoJugado());
            partida.setDificultad(partidaDto.getDificultad());
            partida.setVersion(partidaDto.getVersion());

            em.merge(partida);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al actualizar partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminarPartida(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Partida partida = em.find(Partida.class, idPartida);
            if (partida != null) {
                em.remove(partida);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error al eliminar partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}

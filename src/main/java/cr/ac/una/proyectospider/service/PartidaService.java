package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class PartidaService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cr.ac.una_ProyectoSpider_jar_1.0-SNAPSHOTPU");

    public PartidaDto crearPartida(PartidaDto partidaDto) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Partida partida = partidaDto.toEntitySinJugador();

            // 🛡️ Validar jugador no nulo
            if (partidaDto.getJugador() == null || partidaDto.getJugador().getIdJugador() == null) {
                throw new IllegalStateException("Jugador no asignado a la partida.");
            }

            Jugador jugadorRef = em.getReference(Jugador.class, partidaDto.getJugador().getIdJugador());
            partida.setJugador(jugadorRef);

            em.persist(partida);
            tx.commit();

            return new PartidaDto(partida);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("❌ Error al crear partida: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public boolean guardarPartidaCompleta(PartidaDto partidaDto, List<CartasPartidaDto> cartas) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 🛡️ Validar jugador
            if (partidaDto.getJugador() == null || partidaDto.getJugador().getIdJugador() == null) {
                throw new IllegalStateException("Jugador no asignado a la partida.");
            }

            Partida partida = partidaDto.toEntitySinJugador();
            Jugador jugadorRef = em.getReference(Jugador.class, partidaDto.getJugador().getIdJugador());
            partida.setJugador(jugadorRef);

            // Guardar partida primero
            em.persist(partida);
            em.flush(); // Asegura que tenga ID antes de insertar cartas

            // Guardar cartas asociadas
            for (CartasPartidaDto cartaDto : cartas) {
                CartasPartida carta = cartaDto.toEntity();
                carta.setPartida(partida);
                em.persist(carta);
            }

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("❌ Error al guardar partida completa: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public PartidaCompletaDto cargarPartidaCompletaDto(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        try {
            Partida partida = em.createQuery(
                    "SELECT p FROM Partida p LEFT JOIN FETCH p.cartasPartidaList WHERE p.idPartida = :id",
                    Partida.class
            ).setParameter("id", idPartida).getSingleResult();

            PartidaDto partidaDto = new PartidaDto(partida);
            List<CartasPartidaDto> cartas = partida.getCartasPartidaList().stream()
                    .map(CartasPartidaDto::new)
                    .collect(Collectors.toList());

            return new PartidaCompletaDto(partidaDto, cartas);
        } catch (NoResultException e) {
            System.err.println("❌ No se encontró la partida con ID: " + idPartida);
            return null;
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

    public boolean actualizarPartida(PartidaDto partidaDto) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Partida partida = em.find(Partida.class, partidaDto.getIdPartida());
            if (partida == null) return false;

            partida.setFechaFin(partidaDto.getFechaFin());
            partida.setEstado(partidaDto.getEstado());
            partida.setPuntos(partidaDto.getPuntos());
            partida.setTiempoJugado(partidaDto.getTiempoJugado());
            partida.setDificultad(partidaDto.getDificultad());
            partida.setVersion(partidaDto.getVersion());

            em.merge(partida);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("❌ Error al actualizar partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminarPartida(Long idPartida) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Partida partida = em.find(Partida.class, idPartida);
            if (partida != null) {
                em.remove(partida);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("❌ Error al eliminar partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}

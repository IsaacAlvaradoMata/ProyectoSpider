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
            Jugador jugadorRef = em.getReference(Jugador.class, partidaDto.getJugador().getIdJugador());
            partida.setJugador(jugadorRef);

            em.persist(partida);
            em.flush(); // 🔐 asegura ID_PARTIDA generado
            em.getTransaction().commit();

            return new PartidaDto(partida);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("❌ Error al crear partida: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public boolean guardarPartidaCompleta(PartidaDto partidaDto, List<CartasPartidaDto> cartas) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("🔍 [DEBUG] Iniciando guardado completo de partida...");
            em.getTransaction().begin();

            Partida partida = partidaDto.toEntitySinJugador();
            Jugador jugadorRef = em.getReference(Jugador.class, partidaDto.getJugador().getIdJugador());
            partida.setJugador(jugadorRef);

            // ⏱️ Guardar movimientos
            partida.setMovimientos(partidaDto.getMovimientos());

            Partida managed;

            if (partidaDto.getIdPartida() == null) {
                em.persist(partida);
                em.flush(); // 🔥 sincroniza con DB y obtiene el ID real
                partidaDto.setIdPartida(partida.getIdPartida());
                System.out.println("🎯 [DEBUG] Partida NUEVA persistida con ID real: " + partidaDto.getIdPartida());
                managed = partida;
            } else {
                managed = em.merge(partida);
                System.out.println("♻️ [DEBUG] Partida EXISTENTE mergeada con ID: " + managed.getIdPartida());
            }

            System.out.println("🧹 [DEBUG] Eliminando cartas antiguas para ID_PARTIDA: " + managed.getIdPartida());
            em.createQuery("DELETE FROM CartasPartida c WHERE c.partida.idPartida = :idPartida")
                    .setParameter("idPartida", managed.getIdPartida())
                    .executeUpdate();

            for (CartasPartidaDto dto : cartas) {
                System.out.println("🃏 [DEBUG] Insertando carta con orden: " + dto.getOrden() + " y valor: " + dto.getValor());
                CartasPartida carta = dto.toEntity(managed); // usar partida gestionada
                em.persist(carta);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("❌ [ERROR] Error al guardar partida completa: " + e);
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
        try {
            em.getTransaction().begin();
            Partida partida = em.find(Partida.class, partidaDto.getIdPartida());
            if (partida == null) return false;

            partida.setFechaFin(partidaDto.getFechaFin());
            partida.setEstado(partidaDto.getEstado());
            partida.setPuntos(partidaDto.getPuntos());
            partida.setTiempoJugado(partidaDto.getTiempoJugado());
            partida.setMovimientos(partidaDto.getMovimientos()); // 🟢 Actualizamos movimientos también
            partida.setDificultad(partidaDto.getDificultad());
            partida.setVersion(partidaDto.getVersion());

            em.merge(partida);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
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
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("❌ Error al eliminar partida: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}
package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.Jugador;
import cr.ac.una.proyectospider.model.JugadorDto;
import jakarta.persistence.*;

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
}
package cr.ac.una.proyectospider.service;

import cr.ac.una.proyectospider.model.Jugador;
import cr.ac.una.proyectospider.model.JugadorDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
                return null;  // 🔁 cambio clave: si ya existe, retorna null
            }

            Jugador nuevo = new Jugador();
            nuevo.setNombreUsuario(nombreUsuario);
            nuevo.setPartidasGanadas(0);
            nuevo.setPartidasJugadas(0);
            nuevo.setPuntosAcumulados(0);
            nuevo.setEstiloCartas(1);
            nuevo.setImagenFondo(null);

            em.getTransaction().begin();
            em.persist(nuevo);
            em.getTransaction().commit();

            return new JugadorDto(nuevo);  // sólo si fue insertado
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
}

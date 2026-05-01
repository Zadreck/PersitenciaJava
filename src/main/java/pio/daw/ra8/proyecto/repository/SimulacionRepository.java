package pio.daw.ra8.proyecto.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import pio.daw.ra8.proyecto.model.Individuo;
import pio.daw.ra8.proyecto.model.Intercambio;
import pio.daw.ra8.proyecto.model.Simulacion;
import pio.daw.ra8.util.JPAUtil;

// Aquí están las preguntas que hacemos a la BD
public class SimulacionRepository {
    
    private final EntityManager em;
    private static final String DB_FILE = "target/simulacion.odb";
    
    public SimulacionRepository() {
        this.em = JPAUtil.crearEMF(DB_FILE).createEntityManager();
    }
    
    // Buscar una simulación por ID
    public Simulacion findSimulacionById(Long id) {
        return em.find(Simulacion.class, id);
    }
    
    // Listar a todos ordenados de más rico a más pobre
    public List<Individuo> listarIndividuosPorSaldo(Simulacion simulacion) {
        TypedQuery<Individuo> query = em.createQuery(
                "SELECT i FROM Individuo i WHERE i.simulacion = :sim " +
                "ORDER BY i.saldoActual DESC",
                Individuo.class);
        query.setParameter("sim", simulacion);
        return query.getResultList();
    }
    
    // El que más plata tiene
    public Individuo obtenerIndividuoMasRico(Simulacion simulacion) {
        TypedQuery<Individuo> query = em.createQuery(
                "SELECT i FROM Individuo i WHERE i.simulacion = :sim " +
                "ORDER BY i.saldoActual DESC",
                Individuo.class);
        query.setParameter("sim", simulacion);
        query.setMaxResults(1);
        List<Individuo> resultado = query.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
    
    // El que menos plata tiene
    public Individuo obtenerIndividuoMasPane(Simulacion simulacion) {
        TypedQuery<Individuo> query = em.createQuery(
                "SELECT i FROM Individuo i WHERE i.simulacion = :sim " +
                "ORDER BY i.saldoActual ASC",
                Individuo.class);
        query.setParameter("sim", simulacion);
        query.setMaxResults(1);
        List<Individuo> resultado = query.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
    
    // Promedio de plata
    public Double calcularSaldoMedio(Simulacion simulacion) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(i.saldoActual) FROM Individuo i WHERE i.simulacion = :sim",
                Double.class);
        query.setParameter("sim", simulacion);
        return query.getSingleResult();
    }
    
    // El máximo de plata
    public Double calcularSaldoMaximo(Simulacion simulacion) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT MAX(i.saldoActual) FROM Individuo i WHERE i.simulacion = :sim",
                Double.class);
        query.setParameter("sim", simulacion);
        return query.getSingleResult();
    }
    
    // El mínimo de plata
    public Double calcularSaldoMinimo(Simulacion simulacion) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT MIN(i.saldoActual) FROM Individuo i WHERE i.simulacion = :sim",
                Double.class);
        query.setParameter("sim", simulacion);
        return query.getSingleResult();
    }
    
    // Cuántos tienen más de la mitad de la plata total
    public Long contarIndividuosRicos(Simulacion simulacion) {
        double totalSaldoInicial = simulacion.getNumIndividuos() * simulacion.getSaldoInicial();
        double umbral = totalSaldoInicial * 0.5;
        
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(i) FROM Individuo i WHERE i.simulacion = :sim " +
                "AND i.saldoActual > :umbral",
                Long.class);
        query.setParameter("sim", simulacion);
        query.setParameter("umbral", umbral);
        return query.getSingleResult();
    }
    
    // Los 10 mayores intercambios de plata
    public List<Intercambio> listar10IntercambiosMayores(Simulacion simulacion) {
        TypedQuery<Intercambio> query = em.createQuery(
                "SELECT ic FROM Intercambio ic WHERE ic.simulacion = :sim " +
                "ORDER BY ic.importe DESC",
                Intercambio.class);
        query.setParameter("sim", simulacion);
        query.setMaxResults(10);
        return query.getResultList();
    }
    
    // Todas las simulaciones guardadas
    public List<Simulacion> obtenerTodasSimulaciones() {
        TypedQuery<Simulacion> query = em.createQuery(
                "SELECT s FROM Simulacion s ORDER BY s.id DESC",
                Simulacion.class);
        return query.getResultList();
    }
    
    // Cerrar la conexión
    public void cerrar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

package pio.daw.ra8.proyecto.service;

import java.util.Random;

import jakarta.persistence.EntityManager;
import pio.daw.ra8.proyecto.model.Individuo;
import pio.daw.ra8.proyecto.model.Intercambio;
import pio.daw.ra8.proyecto.model.Simulacion;
import pio.daw.ra8.util.JPAUtil;

// Aquí está toda la lógica del mercado: crear gente, hacer intercambios, guardar datos
public class SimulacionService {
    
    private final EntityManager em;
    private final Random random;
    private static final String DB_FILE = "target/simulacion.odb";
    
    public SimulacionService() {
        this.em = JPAUtil.crearEMF(DB_FILE).createEntityManager();
        this.random = new Random();
    }
    
    // Ejecuta toda la simulación
    public Simulacion ejecutarSimulacion(String nombre, int numIndividuos, double saldoInicial, int numRondas) {
        System.out.println("\n=== Iniciando Simulación ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Individuos: " + numIndividuos);
        System.out.println("Saldo inicial: " + saldoInicial);
        System.out.println("Rondas: " + numRondas);
        System.out.println("========================\n");
        
        // Crear la simulación
        Simulacion simulacion = new Simulacion(nombre, numRondas, numIndividuos, saldoInicial);
        
        // Crear los individuos
        System.out.println("Creando población inicial...");
        for (int i = 1; i <= numIndividuos; i++) {
            Individuo individuo = new Individuo("Individuo_" + i, saldoInicial, simulacion);
            simulacion.getIndividuos().add(individuo);
        }
        
        // Guardar todo en la BD
        em.getTransaction().begin();
        em.persist(simulacion);
        em.getTransaction().commit();
        System.out.println("Población creada y persistida.\n");
        
        // Hacer los intercambios
        System.out.println("Ejecutando rondas...");
        long tiempoInicio = System.currentTimeMillis();
        
        em.getTransaction().begin();
        for (int ronda = 0; ronda < numRondas; ronda++) {
            // Elegir 2 personas diferentes al azar
            int idx1 = random.nextInt(numIndividuos);
            int idx2 = random.nextInt(numIndividuos);
            
            while (idx2 == idx1) {
                idx2 = random.nextInt(numIndividuos);
            }
            
            Individuo individuo1 = simulacion.getIndividuos().get(idx1);
            Individuo individuo2 = simulacion.getIndividuos().get(idx2);
            
            // Decidir quién le pasa plata a quién
            Individuo emisor, receptor;
            if (random.nextBoolean()) {
                emisor = individuo1;
                receptor = individuo2;
            } else {
                emisor = individuo2;
                receptor = individuo1;
            }
            
            // Cuánta plata se intercambia
            double saldoMinimo = Math.min(emisor.getSaldoActual(), receptor.getSaldoActual());
            if (saldoMinimo < 1) {
                saldoMinimo = 1;
            }
            double importe = 1.0 + random.nextDouble() * (saldoMinimo - 1.0);
            
            // Que no quede con deuda
            if (importe > emisor.getSaldoActual()) {
                importe = emisor.getSaldoActual();
            }
            
            // Transferir la plata
            emisor.setSaldoActual(emisor.getSaldoActual() - importe);
            receptor.setSaldoActual(receptor.getSaldoActual() + importe);
            
            // Guardar el intercambio
            Intercambio intercambio = new Intercambio(ronda, importe, emisor, receptor, simulacion);
            em.persist(intercambio);
            simulacion.getIntercambios().add(intercambio);
            
            // Guardar cada 100 rondas para no llenar la memoria
            if ((ronda + 1) % 100 == 0) {
                em.getTransaction().commit();
                em.clear(); // Limpiar la memoria
                em.getTransaction().begin();
                System.out.println("  Ronda " + (ronda + 1) + "/" + numRondas + " completada. Commit realizado.");
            }
        }
        
        // Guardar lo que queda
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        
        long tiempoFin = System.currentTimeMillis();
        System.out.println("Simulación completada en " + (tiempoFin - tiempoInicio) + " ms\n");
        
        return simulacion;
    }
    
    // Cerrar la conexión
    public void cerrar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

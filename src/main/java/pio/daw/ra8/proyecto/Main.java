package pio.daw.ra8.proyecto;

import java.util.List;

import pio.daw.ra8.proyecto.model.Individuo;
import pio.daw.ra8.proyecto.model.Intercambio;
import pio.daw.ra8.proyecto.model.Simulacion;
import pio.daw.ra8.proyecto.repository.SimulacionRepository;
import pio.daw.ra8.proyecto.service.SimulacionService;
import pio.daw.ra8.proyecto.ui.GraficaDistribucion;

// Punto de entrada: ejecuta todo
public class Main {
    
    public static void main(String[] args) {
        System.out.println("  SIMULACIÓN DE MERCADO LIBRE CON JPA Y OBJECTDB ");
        
        SimulacionService servicio = null;
        SimulacionRepository repositorio = null;
        
        try {
            // Ejecutar la simulación
            servicio = new SimulacionService();
            
            // Parámetros
            String nombreSimulacion = "Mercado Libre - Experimento 1";
            int numIndividuos = 100;
            double saldoInicial = 100.0;
            int numRondas = 10000;
            
            // Correr todo
            Simulacion simulacion = servicio.ejecutarSimulacion(
                    nombreSimulacion,
                    numIndividuos,
                    saldoInicial,
                    numRondas
            );
            
            servicio.cerrar();
            
            // Hacer preguntas a la BD
            repositorio = new SimulacionRepository();
            
            System.out.println("  TAREA 3: CONSULTAS JPQL Y ANÁLISIS DE DATOS   ");
            
            // Recargar desde la BD
            simulacion = repositorio.findSimulacionById(simulacion.getId());
            
            // Los 10 más ricos
            System.out.println("--- Consulta 1: Top 10 Individuos más Ricos ---");
            List<Individuo> individuosPorSaldo = repositorio.listarIndividuosPorSaldo(simulacion);
            for (int i = 0; i < Math.min(10, individuosPorSaldo.size()); i++) {
                Individuo ind = individuosPorSaldo.get(i);
                System.out.printf("%2d. %-20s Saldo: %10.2f (Cambio: %+8.2f)%n",
                        i + 1,
                        ind.getNombre(),
                        ind.getSaldoActual(),
                        ind.getDiferenciaRiqueza());
            }
            System.out.println();
            
            // El más rico y el más pobre
            System.out.println("--- Consulta 2: Extremos de Riqueza ---");
            Individuo masRico = repositorio.obtenerIndividuoMasRico(simulacion);
            Individuo maspobre = repositorio.obtenerIndividuoMasPane(simulacion);
            
            System.out.println("Más rico:  " + masRico.getNombre() + " -> " + 
                    String.format("%.2f", masRico.getSaldoActual()) + " u.m.");
            System.out.println("Más pobre: " + maspobre.getNombre() + " -> " + 
                    String.format("%.2f", maspobre.getSaldoActual()) + " u.m.");
            System.out.println();
            
            // Estadísticas de saldo
            System.out.println("--- Consulta 3: Estadísticas de Saldo (Agregaciones) ---");
            Double saldoMedio = repositorio.calcularSaldoMedio(simulacion);
            Double saldoMax = repositorio.calcularSaldoMaximo(simulacion);
            Double saldoMin = repositorio.calcularSaldoMinimo(simulacion);
            
            System.out.printf("Saldo Medio: %.2f u.m.%n", saldoMedio);
            System.out.printf("Saldo Máximo: %.2f u.m.%n", saldoMax);
            System.out.printf("Saldo Mínimo: %.2f u.m.%n", saldoMin);
            System.out.println();
            
            // Cuántos tienen más de la mitad
            System.out.println("--- Consulta 4: Individuos con >50% Riqueza Total ---");
            Long countRicos = repositorio.contarIndividuosRicos(simulacion);
            double porcentajeRicos = (countRicos * 100.0) / simulacion.getNumIndividuos();
            System.out.printf("Individuos con >50%% del total: %d (%.2f%%)%n", countRicos, porcentajeRicos);
            System.out.println();
            
            // Consulta 5 (Opcional): Top 10 intercambios de mayor importe
            System.out.println("--- Consulta 5 (Opcional): Top 10 Intercambios Mayores ---");
            List<Intercambio> top10 = repositorio.listar10IntercambiosMayores(simulacion);
            for (int i = 0; i < top10.size(); i++) {
                Intercambio ic = top10.get(i);
                System.out.printf("%2d. Ronda %5d: %s → %s | Importe: %.2f%n",
                        i + 1,
                        ic.getNumRonda(),
                        ic.getEmisor().getNombre(),
                        ic.getReceptor().getNombre(),
                        ic.getImporte());
            }
            System.out.println();
            
            // ===== TAREA 4: VISUALIZACIÓN GRÁFICA =====
            System.out.println("  TAREA 4: VISUALIZACIÓN GRÁFICA                 ");
            
            // Mostrar histograma en consola
            GraficaDistribucion.mostrarHistogramaConsola(simulacion);
            
            // Mostrar gráfica visual
            System.out.println("\nGenerando gráfica visual...");
            GraficaDistribucion.mostrarGrafica(simulacion);
            
            System.out.println("\n✓ Simulación completada exitosamente!");
            System.out.println("✓ Total de intercambios registrados: " + simulacion.getIntercambios().size());
            
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
            System.err.println("Stack trace:");
            for (StackTraceElement element : e.getStackTrace()) {
                System.err.println("  at " + element);
            }
        } finally {
            if (repositorio != null) {
                repositorio.cerrar();
            }
            if (servicio != null) {
                servicio.cerrar();
            }
        }
    }
}

package pio.daw.ra8.proyecto.ui;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pio.daw.ra8.proyecto.model.Individuo;
import pio.daw.ra8.proyecto.model.Simulacion;

// Aquí dibujamos gráficas para ver cómo está distribuida la plata
public class GraficaDistribucion {
    
    // Mostrar gráfica visual
    public static void mostrarGrafica(Simulacion simulacion) {
        List<Individuo> individuos = simulacion.getIndividuos();
        
        if (individuos.isEmpty()) {
            System.out.println("No hay individuos para mostrar en la gráfica.");
            return;
        }
        
        // Encontrar saldo mínimo y máximo
        double saldoMax = individuos.stream()
                .mapToDouble(Individuo::getSaldoActual)
                .max()
                .orElse(0);
        
        double saldoMin = individuos.stream()
                .mapToDouble(Individuo::getSaldoActual)
                .min()
                .orElse(0);
        
        // Dividir en 10 rangos
        int numRangos = 10;
        double[] rangos = new double[numRangos + 1];
        double rangoTamanio = (saldoMax - saldoMin) / numRangos;
        
        for (int i = 0; i <= numRangos; i++) {
            rangos[i] = saldoMin + (i * rangoTamanio);
        }
        
        // Contar individuos en cada rango
        int[] conteoRangos = new int[numRangos];
        for (Individuo ind : individuos) {
            double saldo = ind.getSaldoActual();
            for (int i = 0; i < numRangos; i++) {
                if (saldo >= rangos[i] && saldo < rangos[i + 1]) {
                    conteoRangos[i]++;
                    break;
                } else if (i == numRangos - 1 && saldo == rangos[i + 1]) {
                    // Incluir el valor máximo en el último rango
                    conteoRangos[i]++;
                    break;
                }
            }
        }
        
        // Preparar los datos
        XYSeries serie = new XYSeries("Distribución de Riqueza");
        for (int i = 0; i < numRangos; i++) {
            double puntoMedio = (rangos[i] + rangos[i + 1]) / 2;
            serie.add(puntoMedio, conteoRangos[i]);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection(serie);
        
        // Hacer la gráfica
        JFreeChart grafica = ChartFactory.createXYLineChart(
                "Distribución de Riqueza - " + simulacion.getNombre(),  // Título
                "Rango de Saldo",                                        // Eje X
                "Número de Individuos",                                  // Eje Y
                dataset
        );
        
        // Mostrarla
        ChartFrame frame = new ChartFrame("Gráfica de Distribución", grafica);
        frame.setSize(800, 600);
        frame.setVisible(true);
        
        System.out.println("\nGráfica mostrada: Distribución de Riqueza");
        System.out.println("Rango de saldo: [" + String.format("%.2f", saldoMin) + ", " 
                + String.format("%.2f", saldoMax) + "]");
    }
    
    // Mostrar histograma en texto
    public static void mostrarHistogramaConsola(Simulacion simulacion) {
        List<Individuo> individuos = simulacion.getIndividuos();
        
        if (individuos.isEmpty()) {
            System.out.println("No hay individuos para mostrar.");
            return;
        }
        
        // Encontrar saldo mínimo y máximo
        double saldoMax = individuos.stream()
                .mapToDouble(Individuo::getSaldoActual)
                .max()
                .orElse(0);
        
        double saldoMin = individuos.stream()
                .mapToDouble(Individuo::getSaldoActual)
                .min()
                .orElse(0);
        
        // Crear 10 rangos
        int numRangos = 10;
        double[] rangos = new double[numRangos + 1];
        double rangoTamanio = (saldoMax - saldoMin) / numRangos;
        
        for (int i = 0; i <= numRangos; i++) {
            rangos[i] = saldoMin + (i * rangoTamanio);
        }
        
        // Contar individuos en cada rango
        int[] conteoRangos = new int[numRangos];
        for (Individuo ind : individuos) {
            double saldo = ind.getSaldoActual();
            for (int i = 0; i < numRangos; i++) {
                if (saldo >= rangos[i] && saldo < rangos[i + 1]) {
                    conteoRangos[i]++;
                    break;
                } else if (i == numRangos - 1 && saldo == rangos[i + 1]) {
                    conteoRangos[i]++;
                    break;
                }
            }
        }
        
        // Buscar el máximo para escala
        int maxConteo = 0;
        for (int conteo : conteoRangos) {
            if (conteo > maxConteo) {
                maxConteo = conteo;
            }
        }
        
        // Imprimir el histograma
        System.out.println("\n=== HISTOGRAMA DE DISTRIBUCIÓN DE RIQUEZA ===");
        System.out.println("Simulación: " + simulacion.getNombre());
        System.out.println("Total de individuos: " + individuos.size());
        System.out.println("");
        
        for (int i = 0; i < numRangos; i++) {
            System.out.printf("[%8.2f - %8.2f): ", rangos[i], rangos[i + 1]);
            
            // Dibujar barras
            int barras = (int) ((conteoRangos[i] * 40) / maxConteo);
            for (int j = 0; j < barras; j++) {
                System.out.print("█");
            }
            
            System.out.println(" " + conteoRangos[i]);
        }
        
        System.out.println("");
        System.out.println("Total: " + individuos.size());
    }
}

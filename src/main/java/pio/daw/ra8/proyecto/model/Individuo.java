package pio.daw.ra8.proyecto.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Un individuo: tiene dinero y puede intercambiar
@Entity
@Table(name = "individuos")
public class Individuo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private double saldoActual;
    
    @Column(nullable = false)
    private double saldoInicial;
    
    // A qué simulación pertenece
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "simulacion_id", nullable = false)
    private Simulacion simulacion;
    
    // Constructores
    public Individuo() {}
    
    public Individuo(String nombre, double saldoInicial, Simulacion simulacion) {
        this.nombre = nombre;
        this.saldoInicial = saldoInicial;
        this.saldoActual = saldoInicial;
        this.simulacion = simulacion;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }
    
    public double getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    
    public Simulacion getSimulacion() {
        return simulacion;
    }
    
    public void setSimulacion(Simulacion simulacion) {
        this.simulacion = simulacion;
    }
    
    // Qué ganó o perdió
    public double getDiferenciaRiqueza() {
        return saldoActual - saldoInicial;
    }
    
    // Porcentaje de lo que tenía al principio
    public double getPorcentajeRiqueza() {
        return (saldoActual / saldoInicial) * 100.0;
    }
    
    @Override
    public String toString() {
        return "Individuo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", saldoActual=" + String.format("%.2f", saldoActual) +
                ", saldoInicial=" + String.format("%.2f", saldoInicial) +
                '}';
    }
}

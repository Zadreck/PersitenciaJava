package pio.daw.ra8.proyecto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// La simulación: guarda todo lo que pasa en el mercado
@Entity
@Table(name = "simulaciones")
public class Simulacion implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private int numRondas;
    
    @Column(nullable = false)
    private int numIndividuos;
    
    @Column(nullable = false)
    private double saldoInicial;
    
    // Lista de individuos en la simulación
    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Individuo> individuos = new ArrayList<>();
    
    // Lista de todos los intercambios que pasaron
    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Intercambio> intercambios = new ArrayList<>();
    
    // Constructor vacío
    public Simulacion() {}
    
    public Simulacion(String nombre, int numRondas, int numIndividuos, double saldoInicial) {
        this.nombre = nombre;
        this.numRondas = numRondas;
        this.numIndividuos = numIndividuos;
        this.saldoInicial = saldoInicial;
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
    
    public int getNumRondas() {
        return numRondas;
    }
    
    public void setNumRondas(int numRondas) {
        this.numRondas = numRondas;
    }
    
    public int getNumIndividuos() {
        return numIndividuos;
    }
    
    public void setNumIndividuos(int numIndividuos) {
        this.numIndividuos = numIndividuos;
    }
    
    public double getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    
    public List<Individuo> getIndividuos() {
        return individuos;
    }
    
    public void setIndividuos(List<Individuo> individuos) {
        this.individuos = individuos;
    }
    
    public List<Intercambio> getIntercambios() {
        return intercambios;
    }
    
    public void setIntercambios(List<Intercambio> intercambios) {
        this.intercambios = intercambios;
    }
    
    @Override
    public String toString() {
        return "Simulacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numRondas=" + numRondas +
                ", numIndividuos=" + numIndividuos +
                ", saldoInicial=" + saldoInicial +
                ", totalIntercambios=" + intercambios.size() +
                '}';
    }
}

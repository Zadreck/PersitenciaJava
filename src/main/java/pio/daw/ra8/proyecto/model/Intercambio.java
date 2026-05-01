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

// Un intercambio: X le pasó dinero a Y
@Entity
@Table(name = "intercambios")
public class Intercambio implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int numRonda;
    
    @Column(nullable = false)
    private double importe;
    
    // Quién le pasó el dinero
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emisor_id", nullable = false)
    private Individuo emisor;
    
    // Quién recibió el dinero
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receptor_id", nullable = false)
    private Individuo receptor;
    
    // En cuál simulación pasó esto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "simulacion_id", nullable = false)
    private Simulacion simulacion;
    
    // Constructores
    public Intercambio() {}
    
    public Intercambio(int numRonda, double importe, Individuo emisor, Individuo receptor, Simulacion simulacion) {
        this.numRonda = numRonda;
        this.importe = importe;
        this.emisor = emisor;
        this.receptor = receptor;
        this.simulacion = simulacion;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getNumRonda() {
        return numRonda;
    }
    
    public void setNumRonda(int numRonda) {
        this.numRonda = numRonda;
    }
    
    public double getImporte() {
        return importe;
    }
    
    public void setImporte(double importe) {
        this.importe = importe;
    }
    
    public Individuo getEmisor() {
        return emisor;
    }
    
    public void setEmisor(Individuo emisor) {
        this.emisor = emisor;
    }
    
    public Individuo getReceptor() {
        return receptor;
    }
    
    public void setReceptor(Individuo receptor) {
        this.receptor = receptor;
    }
    
    public Simulacion getSimulacion() {
        return simulacion;
    }
    
    public void setSimulacion(Simulacion simulacion) {
        this.simulacion = simulacion;
    }
    
    @Override
    public String toString() {
        return "Intercambio{" +
                "id=" + id +
                ", numRonda=" + numRonda +
                ", importe=" + String.format("%.2f", importe) +
                ", emisor=" + (emisor != null ? emisor.getNombre() : "null") +
                ", receptor=" + (receptor != null ? receptor.getNombre() : "null") +
                '}';
    }
}

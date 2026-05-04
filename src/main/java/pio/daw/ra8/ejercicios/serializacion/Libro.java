package pio.daw.ra8.ejercicios.serializacion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;


public class Libro implements Serializable {

    public static final Path RUTA_DEFAULT = Path.of("target/libro.dat");

    private String titulo;
    private String autor;
    private Double precio;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Libro(String titulo, String autor, Double precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Libro [titulo=" + titulo + ", autor=" + autor + ", precio=" + precio + "]";
    }
    public void guardar(Path ruta){
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ruta.toFile()))) {
            oos.writeObject(this);
            System.out.println("Guardado en: " + ruta);
        } catch (IOException e) {
            System.err.println("Error al serializar: " + e.getMessage());
            return;
        }
    }

    public static Libro cargar(Path ruta){
        Libro recuperado = null;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ruta.toFile()))) {

            recuperado = (Libro) ois.readObject();
            System.out.println("Recuperado:   " + recuperado);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar: " + e.getMessage());
        }
        return recuperado;
    }

    public static void main(String[] args) {
        Libro libroAntes = new Libro("100 años de soledad", "Gabriel Garcia Marquez", 20.0);

        System.out.println(libroAntes);

        libroAntes.guardar(RUTA_DEFAULT);

        System.out.println("Libro guardado");

        Libro libroDespues = Libro.cargar(RUTA_DEFAULT);

        System.out.println(libroDespues);

        
        
    }
    

    
    
    
}

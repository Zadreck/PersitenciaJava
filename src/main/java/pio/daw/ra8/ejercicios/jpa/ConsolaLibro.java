package pio.daw.ra8.ejercicios.jpa;

import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import pio.daw.ra8.util.JPAUtil;

public class ConsolaLibro {

    private EntityManager em;

    public ConsolaLibro(){
        EntityManagerFactory emf = JPAUtil.crearEMFWithoutDelete("target/ConsolaLibro.odb");
        this.em = emf.createEntityManager();
    }

    public void printMenu(){
        StringBuilder sb = new StringBuilder();
        sb.append("1 - Añade un libro\n");
        sb.append("2 - Lista los libros\n");
        sb.append("3 - Actualiza un libro\n");
        sb.append("4 - Borra un libro\n");
        sb.append("5 - Salir\n");
        System.out.println(sb.toString());
    }

    private String askForString(Scanner sc, String name){
        System.out.print(name + ": ");
        String nombre = sc.nextLine();
        return nombre;
    }

    private double askForDouble(Scanner sc, String name){
        System.out.print(name + ": ");
        double nombre = sc.nextDouble();
        sc.nextLine();
        return nombre;
    }

    public void añadirLibro(){
        Scanner sc = new Scanner(System.in);
        String nombre = askForString(sc, "Nombre");
        String autor = askForString(sc, "Autor");
        String ISBN = askForString(sc, "ISBN");
        double precio = askForDouble(sc, "Nombre");

        this.em.getTransaction().begin();
        this.em.persist(new Libro(ISBN, nombre, autor, precio));
        this.em.getTransaction().commit();

        System.out.println("Añadido a la BBDD: " + this.em.find(Libro.class, ISBN));

    }

    public void listarLibros(){
        this.em.createQuery("null");
    }


    
}

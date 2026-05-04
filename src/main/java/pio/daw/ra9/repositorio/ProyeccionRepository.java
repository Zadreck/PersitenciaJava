package pio.daw.ra9.repositorio;

import pio.daw.ra9.modelo.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {

    List<Proyeccion> findByFecha(LocalDate fecha);
    List<Proyeccion> findBySalaId(Long salaId);
    List<Proyeccion> findByPeliculaId(Long peliculaId);
    List<Proyeccion> findBySalaIdAndFecha(Long salaId, LocalDate fecha);
    List<Proyeccion> findByAsientosDisponiblesGreaterThan(Integer minAsientos);
}

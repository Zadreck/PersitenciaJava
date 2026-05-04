package pio.daw.ra9.repositorio;

import pio.daw.ra9.modelo.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    Optional<Sala> findByNombreIgnoreCase(String nombre);
    java.util.List<Sala> findByAforoGreaterThanEqual(Integer aforo);
}

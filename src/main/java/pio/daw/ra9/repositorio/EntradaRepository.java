package pio.daw.ra9.repositorio;

import pio.daw.ra9.modelo.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    Optional<Entrada> findByLocalizador(String localizador);
    List<Entrada> findByProyeccionId(Long proyeccionId);
}

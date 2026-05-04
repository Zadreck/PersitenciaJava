package pio.daw.ra9.excepcion;

public class SinAsientosException extends RuntimeException {

    public SinAsientosException(Long proyeccionId) {
        super("No quedan asientos disponibles en la proyección " + proyeccionId);
    }
}

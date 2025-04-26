package src.airportmanager.exceptions;

/**
 * Excepción para cuando no hay hangares disponibles.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NoHangaresDisponiblesException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public NoHangaresDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
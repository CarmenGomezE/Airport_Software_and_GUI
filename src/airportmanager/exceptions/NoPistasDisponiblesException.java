package src.airportmanager.exceptions;

/**
 * Excepción para cuando no hay pistas disponibles.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NoPistasDisponiblesException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public NoPistasDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
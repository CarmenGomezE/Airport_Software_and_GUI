package src.airportmanager.exceptions;

/**
 * Excepción para cuando no hay puertas de embarque disponibles.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NoModosEmbarqueDisponiblesException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public NoModosEmbarqueDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
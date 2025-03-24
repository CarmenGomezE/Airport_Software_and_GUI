package src.airportmanager.exceptions;

/**
 * Excepción para cuando no hay modos o puertas de embarque disponibles.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NoModosPuertasEmbarqueDisponiblesException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public NoModosPuertasEmbarqueDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
package src.airportmanager.exceptions;

/**
 * Excepción para cuando un avion tiene vuelo en menos de una hora.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class VueloEnMenosDe1HoraException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public VueloEnMenosDe1HoraException(String mensaje) {
        super(mensaje);
    }
}
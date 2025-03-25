package src.airportmanager.exceptions;

/**
 * Excepción para cuando la carga es equivocada.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class CargaAvionEquivocadaException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public CargaAvionEquivocadaException(String mensaje) {
        super(mensaje);
    }
}
package src.airportmanager.exceptions;

/**
 * Excepción para cuando un usuario no puede cambiar ese estado.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadoNoDelUsuarioException extends Exception {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
     * 
     * @param mensaje Mensaje de la excepcion
     */
    public EstadoNoDelUsuarioException(String mensaje) {
        super(mensaje);
    }
}
package aeropuerto.src.maria;

/**
 * Excepción para cuando se cambia a un estado que no está permitido.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadoEquivocadoException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public EstadoEquivocadoException(String mensaje) {
        super(mensaje);
    }
}
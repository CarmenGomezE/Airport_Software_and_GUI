package aeropuerto.src.carmen;

/**
 * Excepción para cuando un avión no es válido.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AvionNoValidoException extends Exception {
	/** Número de versión de la clase*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public AvionNoValidoException(String mensaje) {
        super(mensaje);
    }
}

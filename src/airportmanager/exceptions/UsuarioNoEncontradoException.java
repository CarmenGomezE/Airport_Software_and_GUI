package src.airportmanager.exceptions;

/**
 * Excepción para cuando el usuario no se encuentra.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class UsuarioNoEncontradoException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public UsuarioNoEncontradoException(String mensaje) {
		super(mensaje);
	}
}
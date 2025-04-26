package src.airportmanager.exceptions;

/**
 * Excepción para cuando otro usuario ya tiene ese dni.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class UsuarioYaExisteException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public UsuarioYaExisteException(String mensaje) {
		super(mensaje);
	}
}

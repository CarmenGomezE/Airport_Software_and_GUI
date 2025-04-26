package src.airportmanager.exceptions;

/**
 * Excepción para cuando otra terminal ya tiene ese código.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class TerminalYaExisteException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public TerminalYaExisteException(String mensaje) {
		super(mensaje);
	}
}

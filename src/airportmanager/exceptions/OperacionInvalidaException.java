package src.airportmanager.exceptions;

/**
 * Excepción para cuando una solicitud de vuelo compartido no es válida.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class OperacionInvalidaException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public OperacionInvalidaException(String mensaje) {
		super(mensaje);
	}
}
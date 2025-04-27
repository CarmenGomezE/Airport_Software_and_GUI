package src.airportmanager.exceptions;

/**
 * Excepción para cuando el formato del dni es incorrecto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class FormatoDniIncorrectoException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public FormatoDniIncorrectoException(String mensaje) {
		super(mensaje);
	}
}

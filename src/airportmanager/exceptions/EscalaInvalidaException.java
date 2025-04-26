package src.airportmanager.exceptions;

/**
 * Excepción para cuando una escala es inválida.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EscalaInvalidaException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public EscalaInvalidaException(String mensaje) {
		super(mensaje);
	}
}

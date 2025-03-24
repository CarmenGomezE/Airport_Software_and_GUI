package src.airportmanager.exceptions;

/**
 * Excepción para cuando la antelación no es válida.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NumeroMaxDeTemporadasException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public NumeroMaxDeTemporadasException(String mensaje) {
		super(mensaje);
	}
}

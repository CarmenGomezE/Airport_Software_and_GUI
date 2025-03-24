package src.airportmanager.exceptions;

/**
 * Excepción para cuando un horario no es válido.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class HorarioInvalidoException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public HorarioInvalidoException(String mensaje) {
		super(mensaje);
	}
}

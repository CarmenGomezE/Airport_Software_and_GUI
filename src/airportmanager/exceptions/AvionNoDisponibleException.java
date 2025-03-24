package src.airportmanager.exceptions;

/**
 * Excepción para cuando un avión no está disponible.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AvionNoDisponibleException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public AvionNoDisponibleException(String mensaje) {
		super(mensaje);
	}
}

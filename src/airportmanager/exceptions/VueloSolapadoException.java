package src.airportmanager.exceptions;

/**
 * Excepción para cuando dos vuelos se solapan.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class VueloSolapadoException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public VueloSolapadoException(String mensaje) {
		super(mensaje);
	}
}
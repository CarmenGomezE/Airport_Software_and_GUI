package src.airportmanager.exceptions;

/**
 * Excepción para cuando otro aeropuerto de destino ya tiene ese código.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class AeropuertoYaExisteException extends Exception {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion
	 * 
	 * @param mensaje Mensaje de la excepcion
	 */
	public AeropuertoYaExisteException(String mensaje) {
		super(mensaje);
	}
}

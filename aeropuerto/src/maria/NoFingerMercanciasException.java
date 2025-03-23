package aeropuerto.src.maria;

/**
 * Excepción para cuando un finger se asigna a un avión de mercancías.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NoFingerMercanciasException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public NoFingerMercanciasException(String mensaje) {
        super(mensaje);
    }
}
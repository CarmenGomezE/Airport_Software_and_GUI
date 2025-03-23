package aeropuerto.src.maria;

/**
 * Excepción para cuando una puerta de embaque supera el máximo.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class MaxPuertaEmbarqueException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public MaxPuertaEmbarqueException(String mensaje) {
        super(mensaje);
    }
}
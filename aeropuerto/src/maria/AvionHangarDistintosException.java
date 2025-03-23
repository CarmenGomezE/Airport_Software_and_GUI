package aeropuerto.src.maria;

/**
 * Excepción para cuando el avión y el hangar no son compatibles.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AvionHangarDistintosException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public AvionHangarDistintosException(String mensaje) {
        super(mensaje);
    }
}
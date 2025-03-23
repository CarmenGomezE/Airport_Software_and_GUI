package aeropuerto.src.maria;

/**
 * Excepción para cuando un vuelo no es de ese usuario.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class VueloNoDelUsuarioException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public VueloNoDelUsuarioException(String mensaje) {
        super(mensaje);
    }
}
package aeropuerto.src.maria;

/**
 * Excepción para cuando un avion no tiene vuelo en menos de una hora.
 * 
 * @author  María Pozo, Carmen Gómez, Claudia Municio.
 */
public class NoVueloEnMenosDe1HoraException extends Exception {
	/** Número de versión de la clase*/
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion
	 * 
	 * @param mensaje	Mensaje de la excepcion
	 * */
	public NoVueloEnMenosDe1HoraException(String mensaje) {
        super(mensaje);
    }
}
package aeropuerto.src.carmen;

public class EscalaInvalidaException extends Exception {
	private static final long serialVersionUID = 1L;

	public EscalaInvalidaException(String mensaje) {
        super(mensaje);
    }
}

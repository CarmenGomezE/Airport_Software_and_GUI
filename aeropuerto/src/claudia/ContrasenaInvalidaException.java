package aeropuerto.src.claudia;

public class ContrasenaInvalidaException extends Exception {
	private static final long serialVersionUID = 1L;

	public ContrasenaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
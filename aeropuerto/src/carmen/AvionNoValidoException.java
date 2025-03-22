package aeropuerto.src.carmen;

public class AvionNoValidoException extends Exception {
	private static final long serialVersionUID = 1L;

	public AvionNoValidoException(String mensaje) {
        super(mensaje);
    }
}

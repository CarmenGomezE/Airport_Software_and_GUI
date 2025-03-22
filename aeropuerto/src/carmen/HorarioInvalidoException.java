package aeropuerto.src.carmen;

public class HorarioInvalidoException extends Exception {
	private static final long serialVersionUID = 1L;

	public HorarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}

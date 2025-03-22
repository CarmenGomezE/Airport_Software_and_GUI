package aeropuerto.src.carmen;

public class AvionNoDisponibleException extends Exception {
	private static final long serialVersionUID = 1L;

	public AvionNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

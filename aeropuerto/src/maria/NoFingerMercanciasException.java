package aeropuerto.src.maria;

public class NoFingerMercanciasException extends Exception {
    private static final long serialVersionUID = 1L;

	public NoFingerMercanciasException(String mensaje) {
        super(mensaje);
    }
}
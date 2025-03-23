package aeropuerto.src.maria;

public class NoHangaresDisponiblesException extends Exception {
    private static final long serialVersionUID = 1L;

	public NoHangaresDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
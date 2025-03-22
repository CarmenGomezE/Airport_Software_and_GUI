package aeropuerto.src.maria;

public class NoPistasDisponiblesException extends Exception {
    private static final long serialVersionUID = 1L;

	public NoPistasDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
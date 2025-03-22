package aeropuerto.src.maria;

public class NoModosEmbarqueDisponiblesException extends Exception {
    private static final long serialVersionUID = 1L;

	public NoModosEmbarqueDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
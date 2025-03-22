package aeropuerto.src.maria;

public class EstadoEquivocadoException extends Exception {
    private static final long serialVersionUID = 1L;

	public EstadoEquivocadoException(String mensaje) {
        super(mensaje);
    }
}
package aeropuerto.src.maria;

public class EstadoNoDelUsuarioException extends Exception {
    private static final long serialVersionUID = 1L;

	public EstadoNoDelUsuarioException(String mensaje) {
        super(mensaje);
    }
}
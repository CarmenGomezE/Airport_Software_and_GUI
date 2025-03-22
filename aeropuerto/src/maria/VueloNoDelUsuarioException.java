package aeropuerto.src.maria;

public class VueloNoDelUsuarioException extends Exception {
    private static final long serialVersionUID = 1L;

	public VueloNoDelUsuarioException(String mensaje) {
        super(mensaje);
    }
}
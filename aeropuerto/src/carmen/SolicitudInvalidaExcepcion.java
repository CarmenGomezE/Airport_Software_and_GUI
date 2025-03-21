package aeropuerto.src.carmen;

public class SolicitudInvalidaExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public SolicitudInvalidaExcepcion(String mensaje) {
        super(mensaje);
    }
}

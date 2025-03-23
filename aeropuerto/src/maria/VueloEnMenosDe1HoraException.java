package aeropuerto.src.maria;

public class VueloEnMenosDe1HoraException extends Exception {
    private static final long serialVersionUID = 1L;

	public VueloEnMenosDe1HoraException(String mensaje) {
        super(mensaje);
    }
}
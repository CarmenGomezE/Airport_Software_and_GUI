package src.aeropuerto.maria;
/**
 * La clase ControladorAereo representa al usuario controlador de la aplicación
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class ControladorAereo extends UsuarioConDni {
    /**
     * Constructor de la clase ControladorAereo.
     * 
     * @param nombre                Nombre del controlador aéreo.
     * @param dni                   Dni del controlador aéreo.
     * @param contraseña            Contraseña del controlador aéreo.
     */
    public ControladorAereo(String nombre, String dni, String Contraseña) {
        super(nombre, dni, contraseña);
    }
} 
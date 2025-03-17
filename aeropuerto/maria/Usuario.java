package aeropuerto;
/**
 * La clase Usuario representa a los usuarios de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Usuario {
    /** Nombre del usuario*/
    private String name;
    /** Contraseña del usuario*/
    private String contraseña;

    /**
     * Constructor sin contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor con contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     * @param contraseña            Contraseña del usuario.
     */
    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

}
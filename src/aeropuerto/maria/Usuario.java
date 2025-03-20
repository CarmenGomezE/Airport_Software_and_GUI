package src.aeropuerto.maria;
/**
 * La clase Usuario representa a los usuarios de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Usuario {
    /** Nombre del usuario*/
    private String name;
    /** Contraseña del usuario*/
    private String contrasena;
    /** Criterio de búsqueda del usuario*/
    private CriterioBusqeda criterioBusqueda; /** <-----------------mirar esto que no tiene buena pinta------------------*/
    /** Notificaciones del usuario*/
    private List<Notificacion> notificaciones;

    /**
     * Constructor sin contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.notificaciones = new ArrayList<>();
    }

    /**
     * Constructor con contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     * @param contraseña            Contraseña del usuario.
     */
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.notificaciones = new ArrayList<>();
    }

    /**
     * Método para obtener el nombre de un usuario.
     * 
     * @return nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Método para obtener la contraseña de un usuario.
     * 
     * @return contrasena del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }
}
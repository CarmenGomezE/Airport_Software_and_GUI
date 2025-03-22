package aeropuerto.src.maria;
/**
 * La clase UsuarioConDni representa a los usuarios con Dni de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class UsuarioConDni extends Usuario {
    /** Dni del usuario con dni*/
    private String dni;

    /**
     * Constructor sin contraseña de la clase UsuarioConDni.
     * 
     * @param nombre             Nombre del usuario con dni.
     * @param dni                Dni del usuario con dni.
     */
    public UsuarioConDni(String nombre, String dni) {
        super(nombre);
        this.dni = dni;
    }

    /**
     * Constructor con contraseña de la clase UsuarioConDni.
     * 
     * @param nombre             Nombre del usuario con dni.
     * @param contraseña         Contraseña del usuario con dni.
     * @param dni                Dni del usuario con dni.
     */
    public UsuarioConDni(String nombre, String contraseña, String dni) {
        super(nombre, contraseña);
        this.dni = dni;
    }

    /**
     * Método para obtener el dni de un usuario.
     * 
     * @return el dni del usuario.
     */
    public String getDni() {
        return dni;
    }
}
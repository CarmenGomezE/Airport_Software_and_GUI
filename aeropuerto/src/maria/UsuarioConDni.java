package aeropuerto.src.maria;

import aeropuerto.src.claudia.*;

/**
 * La clase UsuarioConDni representa a los usuarios con Dni de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class UsuarioConDni extends Usuario {
    /** Dni del usuario con dni*/
    private String dni;
    /** Getor del usuario con dni*/
    private Gestor gestor;

    /**
     * Constructor sin contraseña de la clase UsuarioConDni.
     * 
     * @param nombre             Nombre del usuario con dni.
     * @param dni                Dni del usuario con dni.
     * @param aeropuerto         Aeropuerto en el que trabaja el usuario con dni.
     */
    public UsuarioConDni(String nombre, String dni, AeropuertoPropio aeropuerto) {
        super(nombre, aeropuerto);
        this.dni = dni;
    }

    /**
     * Constructor con contraseña de la clase UsuarioConDni.
     * 
     * @param nombre             Nombre del usuario con dni.
     * @param contraseña         Contraseña del usuario con dni.
     * @param dni                Dni del usuario con dni.
     * @param aeropuerto         Aeropuerto en el que trabaja el usuario con dni.
     */
    public UsuarioConDni(String nombre, String contraseña, String dni, AeropuertoPropio aeropuerto) {
        super(nombre, contraseña, aeropuerto);
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

    /**
     * Método para obtener el gestor de un usuario con dni.
     * 
     * @return El gestor del usuario con dni.
     */
    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     * @param estado    Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException 
     * @throws EstadoNoDelUsuarioException 
     */
    public abstract void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException;

    /**
     * Método para recibir una notificación de cambio de estado de un vuelo.
     * 
     * @param vuelo     El vuelo del que trata la notificación.
     */
    public void recibirNotificacionCambioEstado(Vuelo vuelo) {
        String mensaje = "El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".";
        Notificacion notificacion = new Notificacion(mensaje, TipoNotificacion.CAMBIO_ESTADO_VUELO);

        recibirNotificacion(notificacion);
    }

}

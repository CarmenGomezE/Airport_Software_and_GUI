package src.airportmanager.usuario;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.EstadoEquivocadoException;
import src.airportmanager.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.exceptions.NoFingerMercanciasException;
import src.airportmanager.exceptions.NoHangaresDisponiblesException;
import src.airportmanager.exceptions.NoModosEmbarqueDisponiblesException;
import src.airportmanager.exceptions.NoModosPuertasEmbarqueDisponiblesException;
import src.airportmanager.exceptions.NoPistasDisponiblesException;
import src.airportmanager.exceptions.NoVueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.exceptions.VueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.VueloNoDelUsuarioException;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Vuelo;

/**
 * La clase UsuarioConDni representa a los usuarios con Dni de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class UsuarioConDni extends Usuario {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Dni del usuario con dni */
    private String dni;
    /** Gestor del usuario con dni */
    protected Gestor gestor;

    /**
     * Constructor sin contraseña de la clase UsuarioConDni.
     * 
     * @param nombre     Nombre del usuario con dni.
     * @param dni        Dni del usuario con dni.
     * @param aeropuerto Aeropuerto en el que trabaja el usuario con dni.
     * @param gestor     Gestor del usuario con dni.
     * @param sistema    Sistema.
     */
    public UsuarioConDni(String nombre, String dni, AeropuertoPropio aeropuerto, Gestor gestor, AirportManager sistema) {
        super(nombre, aeropuerto, sistema);
        this.dni = dni;
        this.gestor = gestor;
    }

    /**
     * Constructor con contraseña de la clase UsuarioConDni.
     * 
     * @param nombre     Nombre del usuario con dni.
     * @param contrasena Contraseña del usuario con dni.
     * @param dni        Dni del usuario con dni.
     * @param aeropuerto Aeropuerto en el que trabaja el usuario con dni.
     *  @param gestor     Gestor del usuario con dni.
     * @param sistema    Sistema.
     */
    public UsuarioConDni(String nombre, String contrasena, String dni, AeropuertoPropio aeropuerto, Gestor gestor, AirportManager sistema) {
        super(nombre, contrasena, aeropuerto, sistema);
        this.dni = dni;
        this.gestor = gestor;
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
     * @param vuelo  Vuelo al que se le cambia el estado.
     * @param estado Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException vuelo no del usuario.
     * @throws EstadoNoDelUsuarioException estado no del usuario.
     * @throws EstadoEquivocadoException estado equivocado.
     * @throws NoModosEmbarqueDisponiblesException no modos de embarque disponibles.
     * @throws NoPistasDisponiblesException no pistas disponibles.
     * @throws NoHangaresDisponiblesException no hangares disponibles.
     * @throws NoModosPuertasEmbarqueDisponiblesException no puertas con modos de embarque disponibles.
     * @throws NoFingerMercanciasException no finger a los de mercancias.
     * @throws VueloEnMenosDe1HoraException vuelo en menos de 1 hora.
     * @throws NoVueloEnMenosDe1HoraException no vuelo en menos de 1 hora.
     * @throws IllegalArgumentException argumento no válido.
     * @throws CargaAvionEquivocadaException carga no válida.
     */
    public abstract void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado)
            throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException, EstadoEquivocadoException,
            NoModosEmbarqueDisponiblesException, NoPistasDisponiblesException, IllegalArgumentException,
            NoVueloEnMenosDe1HoraException, VueloEnMenosDe1HoraException, NoFingerMercanciasException,
            NoModosPuertasEmbarqueDisponiblesException, NoHangaresDisponiblesException, CargaAvionEquivocadaException;

    /**
     * Método para recibir una notificación de cambio de estado de un vuelo.
     * 
     * @param vuelo El vuelo del que trata la notificación.
     */
    public void recibirNotificacionCambioEstado(Vuelo vuelo) {
        String mensaje = "El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".";
        Notificacion notificacion = new Notificacion(mensaje, TipoNotificacion.CAMBIO_ESTADO_VUELO);

        recibirNotificacion(notificacion);
    }

    /**
     * Método para iniciar sesión.
     * 
     * @param dni         Dni del usuario con dni.
     * @param contrasena  Contraseña del usuario con dni.
     * @throws ContrasenaInvalidaException  Si el usuario existe, pero la contraseña
     *                                      es incorrecta.
     * @throws UsuarioNoEncontradoException Si el usuario no existe.
     */
    public void iniciarSesion(String dni, String contrasena) throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        sistema.validarUsuario(dni, contrasena);
        System.out.printf("El usuario con dni %s ha iniciado sesión.%n", dni);
    }
    
    /**
     * Método para iniciar sesión.
     * 
     * @throws ContrasenaInvalidaException  Si el usuario existe, pero la contraseña
     *                                      es incorrecta.
     * @throws UsuarioNoEncontradoException Si el usuario no existe.
     */
    public void heOlvidadoContrasena() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        gestor.modificarUsuario(this.getNombre(), this);
    }
}

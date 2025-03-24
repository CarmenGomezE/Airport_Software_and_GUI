package src.airportmanager.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.vuelo.*;

/**
 * La clase Usuario representa a los usuarios de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Usuario implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Nombre del usuario */
    private String nombre;
    /** Contraseña del usuario */
    protected String contrasena;
    /** Notificaciones del usuario */
    protected List<Notificacion> notificaciones;
    /** Vuelos del usuario */
    protected List<Vuelo> vuelosQueControla;
    /** Aeropuerto en el que trabaja el usuario */
    protected AeropuertoPropio aeropuerto;
    /** Sistema */
    protected AirportManager sistema;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor sin contraseña de la clase Usuario.
     * 
     * @param nombre     Nombre del usuario.
     * @param aeropuerto Aeropuerto en el que trabaja el usuario.
     * @param sistema    Sistema.
     */
    public Usuario(String nombre, AeropuertoPropio aeropuerto, AirportManager sistema) {
        this.nombre = nombre;
        this.notificaciones = new ArrayList<>();
        this.vuelosQueControla = new ArrayList<>();
        this.aeropuerto = aeropuerto;
        this.sistema = sistema;
    }

    /**
     * Constructor con contraseña de la clase Usuario.
     * 
     * @param nombre     Nombre del usuario.
     * @param contraseña Contraseña del usuario.
     * @param aeropuerto Aeropuerto en el que trabaja el usuario.
     * @param sistema    Sistema.
     */
    public Usuario(String nombre, String contrasena, AeropuertoPropio aeropuerto, AirportManager sistema) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.notificaciones = new ArrayList<>();
        this.vuelosQueControla = new ArrayList<>();
        this.aeropuerto = aeropuerto;
        this.sistema = sistema;
    }

    /**
     * Método para obtener el aeropuerto propio.
     * 
     * @return El aeropuerto propio.
     */
    public AeropuertoPropio getAeropuerto() {
        return this.aeropuerto;
    }

    /**
     * Método para obtener el sistema.
     * 
     * @return El sistema.
     */
    public AirportManager getSistema() {
        return sistema;
    }

    /**
     * Método para obtener el nombre de un usuario.
     * 
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para obtener la contraseña de un usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Método para obtener las notificaciones de un usuario.
     * 
     * @return Las notificaciones del usuario.
     */
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Método para obtener los vuelos de un usuario.
     * 
     * @return Los vuelos del usuario.
     */
    public List<Vuelo> getVuelos() {
        return vuelosQueControla;
    }

    /**
     * Método para cambiar el nombre de un usuario.
     * 
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para cambiar la contraseña de un usuario.
     * 
     * @param contrasena La nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Método para imprimir la lista de vuelos del usuario con sus estados.
     */
    public void verListaVuelos() {
    	LocalDate fecha = LocalDate.now();
    	System.out.printf("LISTA DE VUELOS DEL DÍA %d%n", fecha.format(formatoFecha));
        System.out.println("+-----------------+----------------------+");
        System.out.println("| Código de Vuelo | Estado del Vuelo     |");
        System.out.println("+-----------------+----------------------+");

        for (Vuelo v : getAeropuerto().getVuelosAceptados()) {
        	if (v.getFecha() == fecha) {
                System.out.printf("| %-15s | %-20s |\n", v.getCodigo(), v.getEstado());
        	}
        }

        System.out.println("+-----------------+----------------------+");
    }
    
    /**
     * Método para recargar la lista de vuelos del usuario con sus estados.
     */
    public void recargarListaVuelos() {
    	System.out.println("Refrescando lista de vuelos...");
    	verListaVuelos();
    }

    /**
     * Método para buscar un vuelo según un criterio.
     * 
     * @param criterio Criterio de búsqueda.
     * @param valor    Valor del criterio.
     */
    public void buscarVuelo(CriterioBusqueda criterio, String valor) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        switch (criterio) {
            case CODIGO:
                vuelosEncontrados = buscarVueloPorCodigo(valor);
                break;
            case ORIGEN:
                vuelosEncontrados = buscarVueloPorOrigen(valor);
                break;
            case DESTINO:
                vuelosEncontrados = buscarVueloPorDestino(valor);
                break;
            case FECHA:
                vuelosEncontrados = buscarVueloPorFecha(valor);
                break;
            case HORA_SALIDA:
                vuelosEncontrados = buscarVueloPorHoraSalida(valor);
                break;
            case HORA_LLEGADA:
                vuelosEncontrados = buscarVueloPorHoraLlegada(valor);
                break;
            case TERMINAL:
                vuelosEncontrados = buscarVueloPorTerminal(valor);
                break;
            default:
                throw new IllegalArgumentException("El criterio de búsqueda debe ser válido.");
        }

        System.out.printf("BÚSQUEDA DE VUELOS SEGÚN EL CRITERIO %s\n", criterio);

        if (vuelosEncontrados.isEmpty()) {
            System.out.println("No se encontraron vuelos con el criterio de búsqueda especificado.");
            return;
        }

        System.out.println("+-----------------+----------------------+");
        System.out.println("| Código de Vuelo | Estado del Vuelo     |");
        System.out.println("+-----------------+----------------------+");

        for (Vuelo v : vuelosEncontrados) {
            System.out.printf("| %-15s | %-20s |\n", v.getCodigo(), v.getEstado());
        }

        System.out.println("+-----------------+----------------------+");
    }

    private List<Vuelo> buscarVueloPorCodigo(String codigo) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        for (Vuelo v : vuelosQueControla) {
            if (v.getCodigo().equals(codigo)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorOrigen(String origen) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        for (Vuelo v : vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getAeropuertoDestino().getPais().equals(origen)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorDestino(String destino) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        for (Vuelo v : vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getAeropuertoDestino().getPais().equals(destino)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorFecha(String fecha) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        LocalDate fechaVuelo = LocalDate.parse(fecha, formatoFecha);

        for (Vuelo v : vuelosQueControla) {
            if (v.getFecha().equals(fechaVuelo)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorHoraSalida(String horaSalida) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        LocalTime horaVuelo = LocalTime.parse(horaSalida, formatoHora);

        for (Vuelo v : vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getHora().equals(horaVuelo)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorHoraLlegada(String horaLlegada) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        LocalTime horaVuelo = LocalTime.parse(horaLlegada, formatoHora);

        for (Vuelo v : vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getHora().equals(horaVuelo)) {
                vuelosEncontrados.add(v);
            }
        }

        return vuelosEncontrados;
    }

    private List<Vuelo> buscarVueloPorTerminal(String codigoTerminal) {
        List<Vuelo> vuelosTerminal = new ArrayList<>();

        for (Vuelo v : vuelosQueControla) {
            if (v.getControlador().getTerminal().getCodigo().equals(codigoTerminal)) {
                vuelosTerminal.add(v);
            }
        }

        return vuelosTerminal;
    }

    /**
     * Método para recibir una notificación.
     * 
     * @param notificacion La notificación que se recibe.
     */
    public void recibirNotificacion(Notificacion notificacion) {
        if (notificacion == null) {
            throw new IllegalArgumentException("La notificación reicbida no puede ser null.");
        }

        notificaciones.add(notificacion);
    }

    /**
     * Método para añadir un vuelo.
     * 
     * @param vuelo El vuelo que se añade.
     */
    public void addVuelo(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null.");
        }

        vuelosQueControla.add(vuelo);
    }

    /**
     * Método para imprimir las notificaciones del usuario.
     */
    public void verNotificaciones() {
        System.out.println("NOTIFICACIONES");

        for (Notificacion n : notificaciones) {
            System.out.println(n);
        }
    }
    
    /**
     * Método para cerrar sesión.
     */
    public void cerrarSesion() {
    	System.out.println("El usuario ha cerrado sesión.");
    }
}

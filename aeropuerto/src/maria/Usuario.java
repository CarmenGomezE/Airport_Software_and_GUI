package aeropuerto.src.maria;

import aeropuerto.src.claudia.*;
import aeropuerto.src.carmen.vuelo.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * La clase Usuario representa a los usuarios de la aplicación.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Usuario {
    /** Nombre del usuario*/
    private String nombre;
    /** Contraseña del usuario*/
    private String contrasena;
    /** Notificaciones del usuario*/
    protected List<Notificacion> notificaciones;
    /** Vuelos del usuario*/
    protected List<Vuelo> vuelosQueControla;
    /** Aeropuerto en el que trabaja el usuario*/
    private AeropuertoPropio aeropuerto;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor sin contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     * @param aeropuerto            Aeropuerto en el que trabaja el usuario.
     */
    public Usuario(String nombre, AeropuertoPropio aeropuerto) {
        this.nombre = nombre;
        this.notificaciones = new ArrayList<>();
        this.vuelosQueControla = new ArrayList<>();
        this.aeropuerto = aeropuerto;
    }

    /**
     * Constructor con contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     * @param contraseña            Contraseña del usuario.
     * @param aeropuerto            Aeropuerto en el que trabaja el usuario.
     */
    public Usuario(String nombre, String contrasena, AeropuertoPropio aeropuerto) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.notificaciones = new ArrayList<>();
        this.vuelosQueControla = new ArrayList<>();
        this.aeropuerto = aeropuerto;
    }
    
    public AeropuertoPropio getAeropuerto() {
    	return this.aeropuerto;
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
        System.out.println("+-----------------+----------------------+");
        System.out.println("| Código de Vuelo | Estado del Vuelo     |");
        System.out.println("+-----------------+----------------------+");

        for (Vuelo v: getAeropuerto().getVuelosAceptados()) {
            System.out.printf("| %-15s | %-20s |\n", v.getCodigo(), v.getEstado());
        }

        System.out.println("+-----------------+----------------------+");
    }

    /**
     * Método para buscar un vuelo según un criterio.
     * 
     * @param criterio      Criterio de búsqueda.
     * @param valor         Valor del criterio.
     */
    public void buscarVuelo(CriterioBusqueda criterio, String valor) {
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        switch (criterio) {
            case CODIGO:
                vuelosEncontrados = buscarVueloPorCodigo(valor);
            case ORIGEN:
                vuelosEncontrados = buscarVueloPorOrigen(valor);
            case DESTINO:
                vuelosEncontrados = buscarVueloPorDestino(valor);
            case FECHA: 
                vuelosEncontrados = buscarVueloPorFecha(valor);
            case HORA_SALIDA: 
                vuelosEncontrados = buscarVueloPorHoraSalida(valor);
            case HORA_LLEGADA: 
                vuelosEncontrados = buscarVueloPorHoraLlegada(valor);
            case TERMINAL:
                vuelosEncontrados = buscarVueloPorTerminal(valor);
            default:
                throw new IllegalArgumentException("El criterio de búsqueda debe ser válido.");
        }

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

    private Map<String, EstadoVuelo> buscarVueloPorCodigo(String codigo) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelosQueControla) {
            if (v.getCodigo().equals(codigo)) {
                mapaVuelos.put(codigo, v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorOrigen(String origen) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getAeropuertoDestino().getPais().equals(origen)) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorDestino(String destino) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getAeropuertoDestino().getPais().equals(destino)) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorFecha(String fecha) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalDate fechaVuelo = LocalDate.parse(fecha, formatoFecha);
        
        for (Vuelo v: vuelosQueControla) {
            if (v.getFecha() == fechaVuelo) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }
        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraSalida(String horaSalida) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalTime horaVuelo = LocalTime.parse(horaSalida, formatoHora);

        for (Vuelo v: vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getHora().equals(horaVuelo)) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraLlegada(String horaLlegada) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalTime horaVuelo = LocalTime.parse(horaLlegada, formatoHora);

        for (Vuelo v: vuelosQueControla) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getHora().equals(horaVuelo)) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorTerminal(String codigoTerminal) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelosQueControla) {
            if (v.getControlador().getTerminal().getCodigo().equals(codigoTerminal)) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    /**
     * Método para recibir una notificación.
     * 
     * @param notificacion  La notificación que se recibe.
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
     * @param vuelo  El vuelo que se añade.
     */
    public void addVuelo(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null.");
        }

        vuelosQueControla.add(vuelo);
    }



}

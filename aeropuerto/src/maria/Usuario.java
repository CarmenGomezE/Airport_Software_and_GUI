package aeropuerto.src.maria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private CriterioBusqueda criterioBusqueda; /** <-----------------mirar esto que no tiene buena pinta------------------*/
    /** Notificaciones del usuario*/
    private List<Notificacion> notificaciones;
    /** Vuelos del usuario*/
    private List<Vuelo> vuelos;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor sin contraseña de la clase Usuario.
     * 
     * @param nombre                Nombre del usuario.
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.notificaciones = new ArrayList<>();
        this.vuelos = new ArrayList<>();
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
        this.vuelos = new ArrayList<>();
    }

    /**
     * Método para obtener el nombre de un usuario.
     * 
     * @return El nombre del usuario.
     */
    public String getName() {
        return name;
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
        return vuelos;
    }

    /** 
     * Método para obtener la lista de vuelos del usuario con sus estados.
     * 
     * @return Un mapa con el código del vuelo y su estado.
     */
    public Map<String, EstadoVuelo> verListaVuelos() {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();
        for (Vuelo v : vuelos) {
            mapaVuelos.put(v.getCodigo(), v.getEstado());
        }
        return mapaVuelos;
    }

    /**
     * Método para buscar un vuelo según un criterio.
     * 
     * @param criterio      Criterio de búsqueda.
     * @param valor         Valor del criterio.
     * 
     * @return El mapa con los vuelos encontrados.
     */
    public Map<String, EstadoVuelo> buscarVuelo(CriterioBusqueda criterio, String valor) {
        switch (criterio) {
            case CODIGO:
                return buscarVueloPorCodigo(valor);
            case ORIGEN:
                return buscarVueloPorOrigen(valor);
            case DESTINO:
                return buscarVueloPorDestino(valor);
            case FECHA: 
                return buscarVueloPorFecha(valor);
            case HORA_SALIDA: 
                return buscarVueloPorHoraSalida(valor);
            case HORA_LLEGADA: 
                return buscarVueloPorHoraLlegada(valor);
            case TERMINAL:
                return buscarVueloPorTerminal(valor);
            default:
                return new HashMap<>();
        }
    }

    private Map<String, EstadoVuelo> buscarVueloPorCodigo(String codigo) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelos) {
            if (v.getCodigo() == codigo) {
                mapaVuelos.put(codigo, v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorOrigen(String origen) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelos) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getAeropuertoDestino().getPais() == origen) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorDestino(String destino) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelos) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getAeropuertoDestino().getPais() == destino) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorFecha(String fecha) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalDate fechaVuelo = LocalDate.parse(fecha, formatoFecha);
        
        for (Vuelo v: vuelos) {
            if (v.getFecha() == fechaVuelo) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }
        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraSalida(String horaSalida) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalTime horaVuelo = LocalTime.parse(horaSalida, formatoHora);

        for (Vuelo v: vuelos) {
            if (v.getTipoVuelo() == TipoVuelo.SALIDA && v.getHora() == horaVuelo) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraLlegada(String horaLlegada) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        LocalTime horaVuelo = LocalTime.parse(horaLlegada, formatoHora);

        for (Vuelo v: vuelos) {
            if (v.getTipoVuelo() == TipoVuelo.LLEGADA && v.getHora() == horaVuelo) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }

    private Map<String, EstadoVuelo> buscarVueloPorTerminal(String codigoTerminal) {
        Map<String, EstadoVuelo> mapaVuelos = new HashMap<>();

        for (Vuelo v: vuelos) {
            if (v.getControlador().getTerminal().getCodigo() == codigoTerminal) {
                mapaVuelos.put(v.getCodigo(), v.getEstado());
            }
        }

        return mapaVuelos;
    }



}
package aeropuerto.src.maria;
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
            case TERMINAL:
                return buscarVueloPorTerminal(valor);
            default:
                return new HashMap<>();
        }
    }

    private Map<String, EstadoVuelo> buscarVueloPorCodigo(String codigo) {
        return buscarVueloPorAtributo(v -> v.getCodigo().equalsIgnoreCase(codigo));
    }

    private Map<String, EstadoVuelo> buscarVueloPorOrigen(String origen) {
        return buscarVueloPorAtributo(v -> v.getOrigen().equalsIgnoreCase(origen));
    }

    private Map<String, EstadoVuelo> buscarVueloPorDestino(String destino) {
        return buscarVueloPorAtributo(v -> v.getDestino().equalsIgnoreCase(destino));
    }

    private Map<String, EstadoVuelo> buscarVueloPorFecha(Date fecha) {
        return buscarVueloPorAtributo(v -> v.getFecha().equals(fecha));
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraSalida(LocalTime horaSalida) {
        return buscarVueloPorAtributo(v -> v.getHoraSalida().equals(horaSalida));
    }

    private Map<String, EstadoVuelo> buscarVueloPorHoraLlegada(LocalTime horaLlegada) {
        return buscarVueloPorAtributo(v -> v.getHoraLlegada().equals(horaLlegada));
    }

    private Map<String, EstadoVuelo> buscarVueloPorTerminal(String codigoTerminal) {
        return buscarVueloPorAtributo(v -> v.getCodigoTerminal().equalsIgnoreCase(codigoTerminal));
    }



}
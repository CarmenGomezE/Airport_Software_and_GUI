package aeropuerto.src.carmen;

/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el
 * encargado de cambiar los estados de vuelo de embarque y desembarque, así como
 * el de en preparación para despegar. Además podrá realizar otras acciones como
 * dar de alta aviones, programar y compartir vuelos, recibir notificaciones o
 * pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudVueloCompartido {
    /** Porcentaje de vuelo que se solicita compartir */
    private int porcentaje;

    /** Estado de la solicitud */
    private EstadoSolicitud estadoSolicitud;
    /** Aerolinea que solicita compartir vuelo */
    private Aerolinea aerolineaQueSolicita;
    /** Aerolinea original que compartirá el vuleo */
    private Aerolinea aerolineaOriginal;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param porcentaje           Porcentaje de vuelo solicitado.
     * @param estadoSolicitud      Estado de la solicitud.
     * @param aerolineaQueSolicita Aerolinea que ha realizado la solicitud.
     * @param aerolineaOriginal    Aerolinea que ha recibido la solicitud.
     */
    public SolicitudVueloCompartido(int porcentaje, EstadoSolicitud estadoSolicitud, Aerolinea aerolineaQueSolicita,
            Aerolinea aerolineaOriginal) {
        this.porcentaje = porcentaje;
        this.estadoSolicitud = estadoSolicitud;
        this.aerolineaQueSolicita = aerolineaQueSolicita;
        this.aerolineaOriginal = aerolineaOriginal;
    }

    /**
     * Método que devuelve el porcentaje que se solicita compartir.
     * 
     * @return Porcentaje.
     */
    public int getPorcentaje() {
        return this.porcentaje;
    }

    /**
     * Método que devuelve el estado de la solicitud.
     * 
     * @return Estado de la solicitud.
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    /**
     * Método que devuelve la aerolinea que ha hecho la solicitud.
     * 
     * @return Aerolinea que ha solicitado compartir el vuelo.
     */
    public Aerolinea getAerolineaQueSolicita() {
        return this.aerolineaQueSolicita;
    }

    /**
     * Método que devuelve la aerolinea que ha recibido la solicitd.
     * 
     * @return Aerolinea que ha recibido la solicitud.
     */
    public Aerolinea getAerolineaOriginal() {
        return this.aerolineaOriginal;
    }

    public void aceptar() {

    }

    public void rechazar() {

    }
}
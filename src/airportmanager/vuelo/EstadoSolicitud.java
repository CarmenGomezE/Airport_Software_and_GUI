package src.airportmanager.vuelo;

/**
 * La enumeración EstadoSolicitud define si una solicitud de vuelo
 * esta pendiente, aceptada o ha sido rechazada.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum EstadoSolicitud {
    /** La solicitud de vuelo compartido esta pendiente de respuesta */
    PENDIENTE,
    /** La solicitud de vuelo compartido ha sido aceptada */
    ACEPTADA,
    /** La solicitud de vuelo compartido ha sido rechazada */
    RECHAZADA;
}

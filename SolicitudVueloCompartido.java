/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el encargado
 * de cambiar los estados de vuelo de embarque y desembarque, así como el de en preparación
 * para despegar. Además podrá realizar otras acciones como dar de alta aviones, programar 
 * y compartir vuelos, recibir notificaciones o pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudVueloCompartido{
    /** Porcentaje de vuelo que se solicita compartir */
    private int porcentaje;

    /** Estado de la solicitud */
    private EstadoSolicitud estadoSolicitud;
    /** Aerolinea que solicita compartir vuelo */
    private Aerolinea aerolineaQueSolicita;
    /** Aerolinea original que compartirá el vuleo */
    private Aerolinea aerolineaOriginal;
}
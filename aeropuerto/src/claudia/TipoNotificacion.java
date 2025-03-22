package aeropuerto.src.claudia;

/**
 * La enumeraciónTipoNotificacion representa el tipo de notificación.
 * @author Claudia Municio, Carmen Gómez, María Pozo
 */
public enum TipoNotificacion{
    /**La notificación es acerca de un cambio en el estado de vuelo */
    CAMBIO_ESTADO_VUELO,
    /**La notificación es acerca de un olvido de contraseña */
    OLVIDO_CONTRASENA,
    /**La notificación es acerca de una solicitud de compartición de vuelo */
    SOLICITUD_COMPARTIR_VUELO,
    /**La notificación es acerca de una factura emitida  */
    FACTURA_EMITIDA,
    /**La notificación es acerca de opciones de reprogramar el vuelo */
    OPCIONES_REPROGRAMAR_VUELO, 
    SOLICITUD_VUELO_PROGRAMADO;
}
package aeropuerto.src.claudia;


import java.time.*;
/**
 * La clase Notificacion representa las notificaciones que reciben los distintos usuarios.
 * @author Claudia Municio, Carmen Gómez, María Pozo
 */
public class Notificacion {

    /**Mensaje de la notificación */
    private String mensaje;
    /**Fecha de la notificación */
    private LocalDateTime fecha;
    /**Tipo de la notificación */
    private TipoNotificacion tipoNotificacion;

    /**
     * Constructor de la clase Notificacion.
     * @param mensaje               Mensaje de la notificación.
     * @param fecha                 Fecha de la notificación.
     * @param tipoNotificacion      Tipo de la notificación.
     */ 
    public Notificacion(String mensaje, TipoNotificacion tipoNotificacion) {
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
        this.tipoNotificacion = tipoNotificacion;
    }

    /**
     * Método que devuelve el mensaje de la notificación.
     * @return Mensaje de la notificación.
     */
    public String getMensaje() {
        return this.mensaje;
    }

    /**
     * Método que devuelve la fecha de la notificación.
     * @return Fecha de la notificación.
     */
    public LocalDateTime getFecha()  {
        return this.fecha;
    }

    /**
     * Método que devuelve el tipo de la notificación.
     * @return Tipo de notificación.
     */
    public TipoNotificacion getTipoNotificacion() {
        return this.tipoNotificacion;
    }
}
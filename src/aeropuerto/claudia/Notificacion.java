package src.aeropuerto.claudia;
import java.util.*;
import java.time.*;
/**
 * La clase Notificacion representa las notificaciones que reciben los distintos usuarios.
 * @author Claudia Municio, Carmen Gómez, María Pozo
 */
public class Notificacion {

    /**Mensaje de la notificación */
    String mensaje;
    /**Fecha de la notificación */
    Date fecha;
    /**Tipo de la notificación */
    TipoNotificacion tipoNotificacion;

    /**
     * Constructor de la clase Notificacion.
     * @param mensaje               Mensaje de la notificación.
     * @param fecha                 Fecha de la notificación.
     * @param tipoNotificacion      Tipo de la notificación.
     */ 
    public Notificacion(String mensaje, Date fecha, TipoNotificacion tipoNotificacion) {
        this.mensaje = mensaje;
        this.fecha = fecha;
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
    public Date getFecha()  {
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
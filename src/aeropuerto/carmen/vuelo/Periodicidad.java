package src.aeropuerto.carmen.vuelo;
import java.util.*;
/**
 * La enumeración Periodicidad define si un vuelo es de puntual, 
 * recurrente algunos días de la semana o diario.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum Periodicidad{
    /** El vuelo es puntutal */
    PUNTUAL(null, null),
    /** El vuelo es diario */
    DIARIO(null, null),
    /** El vuelo es recurrente */
    RECURRENTE(null, null);
    
    /** Cuando se programa por primera vez el vuelo */
    private Date fechaInicio;
    /** Cuando se programa por última vez el vuelo */
    private Date fechaFinal;

    /**
     * Constructor de la clase VueloProgramado.
     * 
     * @param fechaInicio            Código del vuelo.
     * @param fechaFinal             Fecha del vuelo.
     */
    private Periodicidad(Date fechaInicio, Date fechaFinal) {
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
    }

    /**
     * Método que devuelve la fecha de inicio de una Periodicidad
     * 
     * @return Fecha de inicio.
     */
    public Date getFechaInicio(){
        return this.fechaInicio;
    }

    /**
     * Método que devuelve la fecha de fin de una Periodicidad
     * 
     * @return Fecha de fin.
     */
    public Date getFechaFinal(){
        return this.fechaFinal;
    }
}

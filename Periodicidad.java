/**
 * La enumeración Periodicidad define si un vuelo es de puntual, 
 * recurrente algunos días de la semana o diario.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum Periodicidad{
    /** El vuelo es puntutal */
    PUNTUAL,
    /** El vuelo es diario */
    DIARIO,
    /** El vuelo es recurrente */
    RECURRENTE;
    
    /** Cuando se programa por primera vez el vuelo */
    private Date fechaInicio;
    /** Cuando se programa por última vez el vuelo */
    private Date fechaFinal;
}

package aeropuerto.src.maria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * La clase Temporada representa las distintas temporadas que puede tener el aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Temporada {
    /** Fecha de inicio de la temporada*/
    private LocalDate fechaInicio;
    /** Horario de apertura de la temporada*/
    private LocalTime horarioApertura;
    /** Horario de cierre de la temporada*/
    private LocalTime horarioCierre;
    /** Fecha de fin de la temporada*/
    private LocalDate fechaFin;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Contructor de la clase Temporada.
     * 
     * @param fechaInicio           Fecha de inicio de la temporada.
     * @param horarioApertura       Horario de apertura de la temporada.
     * @param horarioCierre         Horario de cierre de la temporada.
     * @param fechaFin              Fecha de fin de la temporada
     */
    public Temporada(String fechaInicio, String horarioApertura, String horarioCierre, String fechaFin) {
        this.fechaInicio = LocalDate.parse(fechaInicio, formatoFecha).withYear(LocalDate.now().getYear());
        this.fechaFin = LocalDate.parse(fechaFin, formatoFecha).withYear(LocalDate.now().getYear());
        this.horarioCierre = LocalTime.parse(horarioCierre, formatoHora);
        this.fechaFin = LocalDate.parse(fechaFin);
    }

    /**
     * Método para obtener la fecha de inicio de una temporada.
     * 
     * @return la fecha de inicio de la temporada.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método para obtener la fecha de fin de una temporada.
     * 
     * @return la fecha de fin de la temporada.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Método para obtener el horario de apertura de una temporada.
     * 
     * @return el horario de apertura de la temporada.
     */
    public LocalTime getHorarioApertura() {
        return horarioApertura;
    }

    /**
     * Método para obtener el horario de cierre de una temporada.
     * 
     * @return el horario de cierre de la temporada.
     */
    public LocalTime getHorarioCierre() {
        return horarioCierre;
    }

    /**
     * Método para comprobar que una nueva temporada no se solaparía con esta temporada.
     * 
     * @param nuevaTemporada La nueva temporada que se comprueba.
     * 
     * @return true si se solapan, false si no
     */
    public boolean comprobarTemporada(Temporada nuevaTemporada) {
        if (nuevaTemporada.getFechaInicio.isAfter(getFechaFinal) || nuevaTemporada.getFechaFinal.isBefore(getFechaInicio)) {
            return false;
        } 

        return true;
    }

    public boolean estaEnTemporada(LocalDate fecha) {
        if ((fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio)) && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin)) == true) {
            return true;
        }
        
        return false;
    }
}
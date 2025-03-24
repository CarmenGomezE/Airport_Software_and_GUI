package src.airportmanager.aeropuerto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * La clase Temporada representa las distintas temporadas que puede tener el
 * aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Temporada implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Fecha de inicio de la temporada */
    private LocalDate fechaInicio;
    /** Horario de apertura de la temporada */
    private LocalTime horarioApertura;
    /** Horario de cierre de la temporada */
    private LocalTime horarioCierre;
    /** Fecha de fin de la temporada */
    private LocalDate fechaFin;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Contructor de la clase Temporada.
     * 
     * @param fechaInicio     Fecha de inicio de la temporada.
     * @param horarioApertura Horario de apertura de la temporada.
     * @param horarioCierre   Horario de cierre de la temporada.
     * @param fechaFin        Fecha de fin de la temporada
     */
    public Temporada(String fechaInicio, String horarioApertura, String horarioCierre, String fechaFin) {
    	int thisYear = LocalDate.now().getYear(); 
        this.fechaInicio = MonthDay.parse(fechaInicio, formatoFecha).atYear(thisYear);
        this.fechaFin = MonthDay.parse(fechaFin, formatoFecha).atYear(thisYear);
        
        this.horarioApertura = LocalTime.parse(horarioApertura, formatoHora);
        this.horarioCierre = LocalTime.parse(horarioCierre, formatoHora);
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
     * Método para comprobar que una nueva temporada no se solaparía con esta
     * temporada.
     * 
     * @param nuevaTemporada La nueva temporada que se comprueba.
     * 
     * @return true si se solapan, false si no
     */
    public boolean comprobarTemporada(Temporada nuevaTemporada) {
        if (nuevaTemporada.getFechaInicio().isAfter(getFechaFin())
                || nuevaTemporada.getFechaFin().isBefore(getFechaInicio())) {
            return false;
        }

        return true;
    }

    /**
     * Método para comprobar que una fecha esta en esta temporada.
     * 
     * @param fecha La fecha que se comprueba.
     * 
     * @return true si está, false si no
     */
    public boolean estaEnTemporada(LocalDate fecha) {
        if ((fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio))
                && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin)) == true) {
            return true;
        }

        return false;
    }
}
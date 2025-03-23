package aeropuerto.src.carmen.vuelo;

import java.util.*;
import aeropuerto.src.maria.*;
import java.time.*;

import aeropuerto.src.carmen.avion.*;

/**
 * La clase VueloProgramado representa los vuelos que se pretenden añadir a los
 * vuelos del aeropuerto. Este tendrá un tipo de vuelo, de modo de embarque y de
 * carga, y una periodicidad.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class VueloProgramado {

    /** Codigo del vuelo */
    protected String codigo;
    /** Fecha del vuelo */
    protected LocalDate fecha;
    /** Fecha de incio de vuelos que se repiten */
    protected LocalDate fechaInicio;
    /** Fecha de fin de vuelos que se repiten */
    protected LocalDate fechaFinal;
    /** Hora del vuelo */
    protected LocalTime hora;
    /** Dias en los que se repite el vuelo */
    protected List<DayOfWeek> diasRecurrente;
    /** Tipo de carga que transporta el vuelo */
    protected TipoCarga tipoCarga;
    /** Tipo de embarque del vuelo */
    protected TipoModoEmbarque tipoModoEmbarque;
    /** Tipo de periodicidad del vuelo */
    protected Periodicidad periodicidad;
    /** Tipo de vuelo */
    protected TipoVuelo tipoVuelo;
    /** Aeropuerto de destino */
    protected Aeropuerto aeropuertoDestino;
    /** Aeropuerto de destino */
    protected Aeropuerto aeropuertoSalida;
    /** Escala del vuelo */
    protected Escala escala;
    /** Avión que realiza el vuelo */
    protected Avion avion;

    /**
     * Constructor de la clase VueloProgramado.
     * 
     * @param codigo				Código del vueloProgramado.
     * @param fecha         		Fecha del vueloProgramado.
     * @param fechaInicio      		Fecha incio del vueloProgramado.
     * @param fechaFinal       	 	Fecha final del vueloProgramado.
     * @param hora	            	Hora del vueloProgramado.
     * @param diasRecurrente		Días recurrente del vueloProgramado.
     * @param tipoCarga         	Tipo de carga del vueloProgramado.
     * @param tipoModoEmbarque  	Tipo de modo de embarque del vueloProgramado.
     * @param tipoVuelo	            tipo de vuelo del vueloProgramado.
     * @param avion					Avión del vueloProgramado.
     * @param aeropuertoDestino     Aeropuerto de destino del vueloProgramado.
     * @param aeropuertoSalida    	Aeropuerto de salida del vueloProgramado.
     * @param escala		        Escala del vueloProgramado.
     */
    public VueloProgramado(String codigo, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFinal, 
            LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, TipoModoEmbarque tipoModoEmbarque, 
            Periodicidad periodicidad, TipoVuelo tipoVuelo, Avion avion, Aeropuerto aeropuertoDestino, 
            Aeropuerto aeropuertoSalida, Escala escala) {
    	
		this.codigo = codigo;
		this.hora = hora;
		this.tipoCarga = tipoCarga;
		this.tipoModoEmbarque = tipoModoEmbarque;
		this.tipoVuelo = tipoVuelo;
		this.avion = avion;
		this.aeropuertoDestino = aeropuertoDestino;
		this.aeropuertoSalida = aeropuertoSalida;
		this.escala = escala;
		
		if (periodicidad == Periodicidad.PUNTUAL) {
			this.fecha = fecha;
			this.fechaInicio = null;
			this.fechaFinal = null;
			this.diasRecurrente = null;
		} else if (periodicidad == Periodicidad.DIARIO) {
			this.fechaInicio = fechaInicio;
			this.fechaFinal = fechaFinal;
			this.diasRecurrente = null; 
		} else if (periodicidad == Periodicidad.RECURRENTE) {
			this.fechaInicio = fechaInicio;
			this.fechaFinal = fechaFinal;
			this.diasRecurrente = diasRecurrente;
		} else {
			throw new IllegalArgumentException("Periodicidad no válida");
		}
		this.periodicidad = periodicidad;
	}

    /**
     * Método que devuelve el aeropuerto de destino.
     * 
     * @return Aeropuerto de destino.
     */
    public Aeropuerto getAeropuertoDestino() {
    	return this.aeropuertoDestino;
    }
    
    /**
     * Método que devuelve el aeropuerto de destino.
     * 
     * @return Aeropuerto de destino.
     */
    public Aeropuerto getAeropuertoSalida() {
    	return this.aeropuertoSalida;
    }
    
    /**
     * Método que devuelve la fecha de inicio de una Periodicidad del VueloProgramado.
     * 
     * @return Fecha de inicio.
     */
    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    /**
     * Método que devuelve la fecha de fin de una Periodicidad del VueloProgramado.
     * 
     * @return Fecha de fin.
     */
    public LocalDate getFechaFinal() {
        return this.fechaFinal;
    }
    
    /**
     * Método que devuelve el código del VueloProgramado.
     * 
     * @return Código del vuelo.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Método que devuelve la fecha del VueloProgramado.
     * 
     * @return Fecha del vuelo.
     */
    public LocalDate getFecha() {
        return this.fecha;
    }

    /**
     * Método que devuelve la hora del VueloProgramado.
     * 
     * @return Hora del vuelo.
     */
    public LocalTime getHora() {
        return this.hora;
    }

    /**
     * Método que devuelve los dias de la semana en los que se repite el
     * VueloProgramado.
     * 
     * @return Días de la semana en los que se repite el vuelo.
     */
    public List<DayOfWeek> getDiasRecurrente() {
        return this.diasRecurrente;
    }

    /**
     * Método que devuelve el tipo de carga del VueloProgramado.
     * 
     * @return El tipo de carga.
     */
    public TipoCarga getTipoCarga() {
        return this.tipoCarga;
    }

    /**
     * Método que devuelve el modo de embarque del VueloProgramado.
     * 
     * @return El tipo de modo de embarque.
     */
    public TipoModoEmbarque getTipoModoEmbarque() {
        return this.tipoModoEmbarque;
    }

    /**
     * Método que devuelve la periodicidad del VueloProgramado.
     * 
     * @return La periodicidad.
     */
    public Periodicidad getPeriodicidad() {
        return this.periodicidad;
    }

    /**
     * Método que devuelve el tipo de VueloProgramado.
     * 
     * @return Tipo de vuelo.
     */
    public TipoVuelo getTipoVuelo() {
        return this.tipoVuelo;
    }

    /**
     * Método que devuelve el avión del VueloProgramado.
     * 
     * @return Avión.
     */
    public Avion getAvion() {
        return this.avion;
    }

    /**
     * Método que establece una nueva hora para el VueloProgramado.
     * 
     * @param nuevaHora		Nueva hora del vuelo.
     */
	public void setHora(LocalTime nuevaHora) {
		this.hora=nuevaHora;
		
	}
	
	public void setAvion(Avion avion) {
		this.avion=avion;
	}

	/**
     * Método que establece una nueva fecha para el VueloProgramado.
     * 
     * @param nuevaFecha	Nueva fecha del vuelo.
     */
	public void setFecha(LocalDate nuevaFecha) {
		this.fecha=nuevaFecha;
		
	}

	public Escala getEscala() {
		return this.escala;
	}

}
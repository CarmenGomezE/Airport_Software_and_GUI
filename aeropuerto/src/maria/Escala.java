package aeropuerto.src.maria;
import java.time.LocalTime;

/**
 * La clase Escala representa las escalas de un vuelo.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Escala {
    /** Origen de la escala*/
    private Aeropuerto aeropuertoOrigen;
    /** Destino de la escala*/
    private Aeropuerto aeropuertoDestino;
    /** Hora de salida de la escala*/
    private LocalTime horaSalida;
    /** Hora de llegada de la escala*/
    private LocalTime horaLlegada;

    /** 
     * Constructor de la clase Escala.
     * 
     * @param origen        Origen de la escala.
     * @param destino       Destino de la escala.
     * @param horaSalida    Hora de salida de la escala.
     * @param horaLlegada   Hora de llegada de la escala.
     */
    public Escala(Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, LocalTime horaLlegada, LocalTime horaSalida) {
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
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
     * Método que devuelve el aeropuerto de origen.
     * 
     * @return Aeropuerto de origen.
     */
    public Aeropuerto getAeropuertoOrigen() {
    	return this.aeropuertoOrigen;
    }
    
    /**
     * Método que devuelve el la hora de llegada.
     * 
     * @return Hora de llegada.
     */
    public LocalTime getHoraLlegada() {
    	return this.horaSalida;
    }
    
    /**
     * Método que devuelve el la hora de salida.
     * 
     * @return Hora de salida.
     */
    public LocalTime getHoraSalida() {
    	return this.horaLlegada;
    }
}


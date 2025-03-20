package src.aeropuerto.maria;
import java.time.LocalTime;

/**
 * La clase Escala representa las escalas de un vuelo.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Escala {
    /** Origen de la escala*/
    private String origen;
    /** Destino de la escala*/
    private String destino;
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
    public Escala(String origen, String destino, LocalTime horaLlegada, LocalTime horaSalida) {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }
}


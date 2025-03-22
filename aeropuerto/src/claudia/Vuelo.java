package aeropuerto.src.claudia;
import aeropuerto.src.carmen.*;
import aeropuerto.src.carmen.vuelo.*;
import java.time.*;
import java.util.*;

/**
 * La clase Vuelo representa los vuelos 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Vuelo extends VueloProgramado{
    /**El estado del vuelo*/
    private EstadoVuelo estado;
    private double tiempoRetraso;
    private ModoEmbarque modoEmbarque;
    private PuertaEmbarque puertaEmbarque;


    /**
     * Constructor de la clase Vuelo .
     * 
     * @param codigo                Código del vuelo.
     * @param fecha                 Fecha del vuelo.
     * @param hora                  Hora del vuelo.
     * @param diasRecurrente        Días en los que es recurrente el vuelo.
     * @param tipoCarga             Tipo de carga del vuelo.
     * @param tipoModoEmbarque      Modo de embarque del vuelo.
     * @param periodicidad          Periodicidad del vuelo.
     * @param tipoVuelo             Tipo de vuelo.
     * @param avion                 Avion del vuelo.
     * @param estado                Estado del vuelo.
     * @param tiempoRetraso         El tiempo de retraso del vuelo.
     */
    public Vuelo(String codigo, Date fecha, LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, 
                            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo,
                            Avion avion, EstadoVuelo estado, double tiempoRetraso) {
        super(codigo,fecha,hora,diasRecurrente,tipoCarga,tipoModoEmbarque,periodicidad,tipoVuelo,avion);
        this.estado=estado;
        this.tiempoRetraso = tiempoRetraso;
    }


    /**
     * Método que cambia el estado de un vuelo.
     * @param estado El nuevo estado del vuelo
     */
    public void cambiarEstado(EstadoVuelo estado){
        this.estado=estado;
    }

    /**
     * Método que devuelve el estado del vuelo.
     * @return Estado del vuelo.
     */

    public EstadoVuelo getEstado(){
        return this.estado;
    }

    /**
     * Método que devuelve el tiempo de retraso del vuelo.
     * @return Tiempo de retraso del vuelo.
     */
    public double getRetraso() {
        return this.tiempoRetraso;
    }

    public ModoEmbarque getModoEmbarque() {
        return this.modoEmbarque;
    }

    public void setModoEmbarque(ModoEmbarque modo) {
        this.modoEmbarque = modo;
    }

    public PuertaEmbarque getPuertaEmbarque() {
        return this.puertaEmbarque;
    }

    public void setPuertaEmbarque(PuertaEmbarque puerta) {
        this.puertaEmbarque = puerta;
    }

}
package src.aeropuerto.claudia;
import java.time.*;
import java.util.*;

import src.aeropuerto.carmen.*;
import src.aeropuerto.carmen.vuelo.Periodicidad;

/**
 * La clase Vuelo representa los vuelos 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Vuelo extends VueloProgramado{
    /**El estado del vuelo*/
    EstadoVuelo estado;

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
     */
    public Vuelo(String codigo, Date fecha, LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, 
                            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo,
                            Avion avion, EstadoVuelo estado) {
        super(codigo,fecha,hora,diasRecurrente,tipoCarga,tipoModoEmbarque,periodicidad,tipoVuelo,avion);
        this.estado=estado;
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
}
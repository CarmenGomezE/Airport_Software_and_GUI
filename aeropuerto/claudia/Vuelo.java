package aeropuerto.claudia;
import aeropuerto.carmen.*;
import java.time.*;
import java.util.*;

public class Vuelo extends VueloProgramado{
    EstadoVuelo estado;

    public Vuelo(String codigo, Date fecha, LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, 
                            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo,
                            Avion avion, EstadoVuelo estado) {
        super(codigo,fecha,hora,diasRecurrente,tipoCarga,tipoModoEmbarque,periodicidad,tipoVuelo,avion);
        this.estado=estado;
    }

    public void cambiarEstado(EstadoVuelo estado){
        this.estado=estado;
    }

    public EstadoVuelo getEstado(){
        return this.estado;
    }
}
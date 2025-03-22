package aeropuerto.src.claudia;

import aeropuerto.src.maria.*;
import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.avion.*;

import java.time.*;
import java.util.*;


/**
 * La clase Vuelo representa los vuelos 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Vuelo extends VueloProgramado{
    /**El estado del vuelo*/
    private EstadoVuelo estado;
    private HistorialEstadoVuelo historialEstadoVuelo;
    private int tiempoRetraso;
    private ModoEmbarque modoEmbarque;
    private PuertaEmbarque puertaEmbarque;
    private Terminal terminal;
    private ControladorAereo controladorAereo;
    private Pista pista;


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
    public Vuelo(String codigo, LocalDate fecha,LocalDate fechaInicio, LocalDate fechaFinal, LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, 
                            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo,Avion avion,Aeropuerto aeropuertoDestino, 
                            Aeropuerto aeropuertoSalida, Escala escala) {
        super(codigo,fecha,fechaInicio, fechaFinal,hora, diasRecurrente,tipoCarga,tipoModoEmbarque,periodicidad,tipoVuelo,avion,aeropuertoDestino, aeropuertoSalida,escala);
        this.estado=EstadoVuelo.ACEPTADO;
        this.historialEstadoVuelo=new HistorialEstadoVuelo();
        this.tiempoRetraso = 0;
        this.puertaEmbarque=null;
        this.terminal=null;
        this.controladorAereo=null;
        this.pista=null;
        this.modoEmbarque = null;
    }


    /**
     * Método que cambia el estado de un vuelo.
     * @param estado El nuevo estado del vuelo
     */
    public void cambiarEstado(EstadoVuelo nuevoEstado) {
        this.estado = nuevoEstado;
        this.historialEstadoVuelo.agregarCambioEstado(nuevoEstado, LocalTime.now());
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
    public int getRetraso() {
        return this.tiempoRetraso;
    }
    
    /**
     * Método que devuelve el tiempo de retraso del vuelo.
     * @return Tiempo de retraso del vuelo.
     */
    public void addRetraso(int retraso) {
        this.tiempoRetraso+=retraso;
    }
    
    /**
     * Método que devuelve el historial de cambios de estado.
     * 
     * @return Historial de cambios de estado.
     */
    public HistorialEstadoVuelo getHistorialEstadoVuelo() {
        return this.historialEstadoVuelo;
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
    
	public void setTerminal(Terminal terminal) {
		this.terminal=terminal;
	}

	public void setControlador(ControladorAereo controladorAereo) {
		this.controladorAereo=controladorAereo;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public ControladorAereo getControlador() {
		return this.controladorAereo;
	}

    public Pista getPista() {
        return this.pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }
}

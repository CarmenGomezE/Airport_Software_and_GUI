package src.airportmanager.vuelo;

import java.time.*;
import java.util.*;

import src.airportmanager.aeropuerto.Aeropuerto;
import src.airportmanager.avion.*;
import src.airportmanager.pista.Pista;
import src.airportmanager.terminal.ModoEmbarque;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.Operador;

/**
 * La clase Vuelo representa los vuelos
 * 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Vuelo extends VueloProgramado {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** El historial de estados del vuelo */
    private HistorialEstadoVuelo historialEstadoVuelo;
    /** El tiempo de retraso del vuelo */
    private double tiempoRetraso;
    /** El modo de embarque del vuelo */
    private ModoEmbarque modoEmbarque;
    /** La puerta de embarque del vuelo */
    private PuertaEmbarque puertaEmbarque;
    /** La terminal del vuelo */
    private Terminal terminal;
    /** El controlador del vuelo */
    private ControladorAereo controladorAereo;
    /** El operador principal del vuelo */
    private Operador operadorPrincipal;
    /** La pista del vuelo */
    private Pista pista;
    /** Los operadores secundarios del vuelo si los hay y su porcentaje */
    private Map<Operador, Double> cargaPorOperador;

    /**
     * Constructor de la clase Vuelo .
     * 
     * @param codigo           Código del vuelo.
     * @param fecha            Fecha del vuelo.
     * @param fechaInicio      Fecha inicio del vuelo.
     * @param fechaFinal       Fecha final del vuelo.
     * @param hora             Hora del vuelo.
     * @param diasRecurrente   Días en los que es recurrente el vuelo.
     * @param tipoCarga        Tipo de carga del vuelo.
     * @param tipoModoEmbarque Modo de embarque del vuelo.
     * @param periodicidad     Periodicidad del vuelo.
     * @param tipoVuelo        Tipo de vuelo.
     * @param avion            Avion del vuelo.
     * @param aeropuertoDestino        Aeropuerto de destino.
     * @param aeropuertoSalida 			Aeropuerto de origen.
     * @param escala     				Escala en el aeropuerto.
     * @param operadorPrincipal        Operador principal.
     */
    public Vuelo(String codigo, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFinal, LocalTime hora,
            List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga,
            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo, Avion avion,
            Aeropuerto aeropuertoDestino,
            Aeropuerto aeropuertoSalida, Escala escala, Operador operadorPrincipal) {
        super(codigo, fecha, fechaInicio, fechaFinal, hora, diasRecurrente, tipoCarga, tipoModoEmbarque, periodicidad,
                tipoVuelo, avion, aeropuertoDestino, aeropuertoSalida, escala);
        this.historialEstadoVuelo = new HistorialEstadoVuelo();
        this.tiempoRetraso = -1;
        this.puertaEmbarque = null;
        this.terminal = null;
        this.controladorAereo = null;
        this.pista = null;
        this.modoEmbarque = null;
        this.cargaPorOperador = new HashMap<>();
        this.cargaPorOperador.put(operadorPrincipal, 100.0);
        this.operadorPrincipal = operadorPrincipal;
    }

    /**
     * Método que cambia el estado de un vuelo.
     * 
     * @param nuevoEstado El nuevo estado del vuelo
     */
    public void cambiarEstado(EstadoVuelo nuevoEstado) {
        this.historialEstadoVuelo.agregarCambioEstado(nuevoEstado, LocalTime.now());
    }

    /**
     * Método que devuelve el estado del vuelo.
     * 
     * @return Estado del vuelo.
     */

    public EstadoVuelo getEstado() {
        return this.historialEstadoVuelo.getUltimoEstado();
    }

    /**
     * Método que devuelve el tiempo de retraso del vuelo.
     * 
     * @return Tiempo de retraso del vuelo.
     */
    public double getRetraso() {
        return this.tiempoRetraso;
    }

    /**
     * Método que devuelve el tiempo de retraso del vuelo.
     * 
     * @param retraso	 Tiempo de retraso del vuelo.
     */
    public void setRetraso(double retraso) {
        this.tiempoRetraso = retraso;
    }

    /**
     * Método que devuelve el historial de cambios de estado.
     * 
     * @return Historial de cambios de estado.
     */
    public HistorialEstadoVuelo getHistorialEstadoVuelo() {
        return this.historialEstadoVuelo;
    }

    /**
     * Método que devuelve el modo de embarque del vuelo.
     * 
     * @return Modo de embarque.
     */
    public ModoEmbarque getModoEmbarque() {
        return this.modoEmbarque;
    }

    /**
     * Método que asigna un modo de embarque al vuelo.
     * 
     * @param modo Modo de embarque.
     */
    public void setModoEmbarque(ModoEmbarque modo) {
        this.modoEmbarque = modo;
    }

    /**
     * Método que devuelve la puerta de embarque de un vuelo.
     * 
     * @return La puerta de embarque de un vuelo.
     */
    public PuertaEmbarque getPuertaEmbarque() {
        return this.puertaEmbarque;
    }

    /**
     * Método que asigna una puerta de embarque a un vuelo.
     * 
     * @param puerta Puerta de embarque.
     */
    public void setPuertaEmbarque(PuertaEmbarque puerta) {
        this.puertaEmbarque = puerta;
    }

    /**
     * Método que asigna una terminal a un vuelo.
     * 
     * @param terminal Terminal que se asigna a un vuelo.
     */
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Método que asigna un controlador a un vuelo.
     * 
     * @param controladorAereo Controlador.
     */
    public void setControlador(ControladorAereo controladorAereo) {
        this.controladorAereo = controladorAereo;
    }

    /**
     * Método que devuelve la terminal asignada del vuelo.
     * 
     * @return La terminal del vuelo.
     */
    public Terminal getTerminal() {
        return this.terminal;
    }

    /**
     * Método que devuelve el controlador del vuelo.
     * 
     * @return Controlador del vuelo.
     */
    public ControladorAereo getControlador() {
        return this.controladorAereo;
    }

    /**
     * Método que devuelve la pista del vuelo.
     * 
     * @return La pista asignada.
     */
    public Pista getPista() {
        return this.pista;
    }

    /**
     * Método que asigna una pista al vuelo.
     * 
     * @param pista Pista.
     */
    public void setPista(Pista pista) {
        this.pista = pista;
    }

    /**
     * Método que añade un operador y su carga al vuelo.
     * 
     * @param operador        Operador que va a añadirse.
     * @param porcentajeCarga Porcentaje asume,
     */
    public void agregarOperadorSecundario(Operador operador, double porcentajeCarga) {
        if (this.cargaPorOperador.containsKey(operador)) {
            throw new IllegalArgumentException("El operador " + operador.getNombre() + " ya está asignado al vuelo.");
        }
        double cargaPrincipal = this.cargaPorOperador.get(operadorPrincipal);

        this.cargaPorOperador.put(operadorPrincipal, cargaPrincipal - porcentajeCarga);
        this.cargaPorOperador.put(operador, porcentajeCarga);
    }

    /**
     * Método que devuelve la carga de un operador dado.
     * 
     * @param operador Operador del que se devuelve la carga.
     * @return La carga asignada a un operador.
     */
    public double getCargaAsignada(Operador operador) {
        return this.cargaPorOperador.getOrDefault(operador, 0.0);
    }

    /**
     * Método que devuelve la carga total del vuelo.
     * 
     * @return Carga total del vuelo.
     */
    public double getCargaTotalAsignada() {
        return cargaPorOperador.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Método que devuelve el operador principal del vuelo.
     * 
     * @return Operador principal del vuelo.
     */
    public Operador getOperadorPrincipal() {
        return this.operadorPrincipal;
    }

    /**
     * Método que el mapa de operadores y sus respectivas cargas.
     * 
     * @return Mapa de operadores y carga.
     */
    public Map<Operador, Double> getOperadoresAsignados() {
        return this.cargaPorOperador;
    }

    /**
     * Método que devuelve todos los operadores del vuelo.
     * 
     * @return Operadores del vuelo.
     */
    public List<Operador> getOperadores() {
        List<Operador> operadores = new ArrayList<>();
        if (this.operadorPrincipal != null &&!this.cargaPorOperador.containsKey(this.operadorPrincipal)) {
            operadores.add(this.operadorPrincipal);
        }
        if (this.cargaPorOperador != null) {
            operadores.addAll(this.cargaPorOperador.keySet());
        }
        return operadores;
    }

    /**
     * Método que devuelve el tiempo de espera del vuelo.
     * 
     * @return Tiempo de espera de despegue.
     */
    public int getTiempoEspera() {
        LocalTime horaCambio;

        
        if (this.getEstado() != EstadoVuelo.ESPERANDO_DESPEGUE) {
            System.out.println("Estado no es ESPERANDO_DESPEGUE, estado actual: " + this.getEstado());
            return 0;
        }

        
        horaCambio = this.historialEstadoVuelo.getHoraCambio(EstadoVuelo.ESPERANDO_DESPEGUE);

        
        if (horaCambio == null) {
            System.out.println("No se encontró hora de cambio para ESPERANDO_DESPEGUE.");
            return 0;
        }

        
        System.out.println("Hora de cambio: " + horaCambio);

        
        long minutos = Duration.between(horaCambio, LocalTime.now()).toMinutes();

        
        if (minutos < 0) {
            System.out.println("La duración calculada es negativa, horaCambio: " + horaCambio + ", ahora: " + LocalTime.now());
            return 0;
        }

        
        return (int) minutos;
    }


}

package src.airportmanager.vuelo;

import java.io.Serializable;

import src.airportmanager.exceptions.SolicitudInvalidaExcepcion;
import src.airportmanager.usuario.Operador;

/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el
 * encargado de cambiar los estados de vuelo de embarque y desembarque, así como
 * el de en preparación para despegar. Además podrá realizar otras acciones como
 * dar de alta aviones, programar y compartir vuelos, recibir notificaciones o
 * pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudVueloCompartido implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Porcentaje de vuelo que se solicita compartir */
    private Vuelo vueloOriginal;
    /** Operador que recibe la solicitud */
    private Operador operadorReceptor;
    /** Porcentaje de carga a compartir de la solicitud */
    private double porcentajeCarga;
    /** Estado de la solicitud */
    private EstadoSolicitud estadoSolicitud;
    /** Aerolinea que solicita compartir vuelo */
    private Operador operadorSolicita;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param vuelo               Vuelo que se solicita compartir.
     * @param operadorSolicitante Estado de la solicitud.
     * @param operadorReceptor    Aerolinea que ha realizado la solicitud.
     * @param porcentaje          Aerolinea que ha recibido la solicitud.
     */
    public SolicitudVueloCompartido(Vuelo vuelo, Operador operadorSolicitante, Operador operadorReceptor,
            double porcentaje) {
        this.vueloOriginal = vuelo;
        this.operadorSolicita = operadorSolicitante;
        this.operadorReceptor = operadorReceptor;
        this.porcentajeCarga = porcentaje;
        this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }

    /**
     * Método que devuelve el estado de la solicitud.
     * 
     * @return Estado de la solicitud.
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    /**
     * Método que devuelve el porcentaje de la solicitud.
     * 
     * @return Porcentaje de carga a compartir de la solicitud.
     */
    public double getPorcentaje() {
        return this.porcentajeCarga;
    }

    /**
     * Método que devuelve el operador que hace la solicitud.
     * 
     * @return Operador solicitante.
     */
    public Operador getOperadorSolicitante() {
        return this.operadorSolicita;
    }

    /**
     * Método que devuelve el operador que recibe la solicitud.
     * 
     * @return Operador receptor.
     */
    public Operador getOperadorReceptor() {
        return this.operadorReceptor;
    }

    /**
     * Método que acepta una solicitud.
     * @throws SolicitudInvalidaExcepcion solicitud inválida.
     */
    public void aceptar() throws SolicitudInvalidaExcepcion {
    	 if (!esSolicitudValida()) {
    		 	throw new SolicitudInvalidaExcepcion("La solicitud es inválida. Ha sido rechazada.");
    	    }
    	    
    	    vueloOriginal.agregarOperadorSecundario(operadorSolicita, porcentajeCarga);
    	    this.estadoSolicitud = EstadoSolicitud.ACEPTADA;
    	    System.out.println("Solicitud aceptada: " + operadorSolicita.getNombre() + " ahora comparte el vuelo "
    	            + vueloOriginal.getCodigo());
    	}

    /**
     * Método que rechaza la solicitud.
     */
    public void rechazar() {
        this.estadoSolicitud = EstadoSolicitud.RECHAZADA;
        System.out.println("Solicitud rechazada: " + operadorSolicita.getNombre() + " no compartirá el vuelo "
                + vueloOriginal.getCodigo());
    }

    /**
     * Método que verifica si la solicitud cumple las condiciones para ser aceptada.
     * 
     * @return true si la solicitud es válida.
     * @throws SolicitudInvalidaExcepcion si la solicitud no cumple los requisitos.
     */
    public boolean esSolicitudValida() throws SolicitudInvalidaExcepcion {
        double cargaActual = vueloOriginal.getCargaTotalAsignada();
        double cargaRestanteOperadorPrincipal = vueloOriginal.getCargaAsignada(vueloOriginal.getOperadorPrincipal()) - porcentajeCarga;
        double nuevaCargaTotal = cargaActual - porcentajeCarga + porcentajeCarga;

        if (nuevaCargaTotal > 100.0) {
        	rechazar();
            throw new SolicitudInvalidaExcepcion("El porcentaje total de carga no puede superar el 100%.");
        }

        if (cargaRestanteOperadorPrincipal < 50.0) {
        	rechazar();
            throw new SolicitudInvalidaExcepcion("El operador principal debe conservar al menos el 50% de la carga.");
        }

		return true;
    }

    /**
     * Método que devuelve el vuelo original de la solicitud.
     * 
     * @return Vuelo original.
     */
    public Vuelo getVueloOriginal() {
        return this.vueloOriginal;
    }
}

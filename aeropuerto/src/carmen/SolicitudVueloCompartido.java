package aeropuerto.src.carmen;

import aeropuerto.src.claudia.*;

/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el
 * encargado de cambiar los estados de vuelo de embarque y desembarque, así como
 * el de en preparación para despegar. Además podrá realizar otras acciones como
 * dar de alta aviones, programar y compartir vuelos, recibir notificaciones o
 * pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudVueloCompartido {
    /** Porcentaje de vuelo que se solicita compartir */
    private Vuelo vueloOriginal;  
    private Operador operadorReceptor; 
    private double porcentajeCarga;  
    /** Estado de la solicitud */
    private EstadoSolicitud estadoSolicitud;
    /** Aerolinea que solicita compartir vuelo */
    private Operador operadorSolicita;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param porcentaje           Porcentaje de vuelo solicitado.
     * @param estadoSolicitud      Estado de la solicitud.
     * @param aerolineaQueSolicita Aerolinea que ha realizado la solicitud.
     * @param aerolineaOriginal    Aerolinea que ha recibido la solicitud.
     */
    public SolicitudVueloCompartido(Vuelo vuelo, Operador operadorSolicitante, Operador operadorReceptor, double porcentaje) {
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
    
    public double getPorcentaje() {
    	return this.porcentajeCarga;
    }

    public Operador getOperadorSolicitante() {
        return this.operadorSolicita;
    }

    public Operador getOperadorReceptor() {
        return this.operadorReceptor;
    }
    public void aceptar() {
        try {
            if (esSolicitudValida()) {
                vueloOriginal.agregarOperadorSecundario(operadorSolicita, porcentajeCarga);
                this.estadoSolicitud = EstadoSolicitud.ACEPTADA;
                System.out.println("Solicitud aceptada: " + operadorSolicita.getNombre() + " ahora comparte el vuelo " + vueloOriginal.getCodigo());
            }
        } catch (SolicitudInvalidaExcepcion e) {
            this.estadoSolicitud = EstadoSolicitud.RECHAZADA;
            System.out.println("Error al aceptar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Rechaza la solicitud.
     */
    public void rechazar() {
        this.estadoSolicitud = EstadoSolicitud.RECHAZADA;
        System.out.println("Solicitud rechazada: " + operadorSolicita.getNombre() + " no compartirá el vuelo " + vueloOriginal.getCodigo());
    }
    
    /**
     * Verifica si la solicitud cumple las condiciones para ser aceptada.
     * @return true si la solicitud es válida.
     * @throws SolicitudInvalidaExcepcion si la solicitud no cumple los requisitos.
     */
    boolean esSolicitudValida() throws SolicitudInvalidaExcepcion {
        double cargaActual = vueloOriginal.getCargaTotalAsignada();
        double cargaPrincipal = vueloOriginal.getCargaAsignada(vueloOriginal.getOperadorPrincipal());

        if (cargaActual + porcentajeCarga > 100.0) {
            throw new SolicitudInvalidaExcepcion("El porcentaje total de carga no puede superar el 100%.");
        }

        if (cargaPrincipal - porcentajeCarga < 50.0) {
            throw new SolicitudInvalidaExcepcion("El operador principal debe conservar al menos el 50% de la carga.");
        }

        return true;
    }

    /**
     * Obtiene el vuelo original de la solicitud.
     * @return Vuelo original.
     */
    public Vuelo getVueloOriginal() {
        return this.vueloOriginal;
    }
}

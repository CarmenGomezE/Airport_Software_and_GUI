package aeropuerto.src.carmen;

import aeropuerto.src.claudia.*;
import aeropuerto.src.carmen.vuelo.*;
import java.util.*;

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
    private VueloProgramado vueloOriginal;  
    private List<Operador> operadoresCompartidos; 
    private List<Double> porcentajesCarga;  
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
    public SolicitudVueloCompartido(Vuelo vueloOriginal, Operador operadorSolicitante) {
        this.vueloOriginal = vueloOriginal;
        this.operadorSolicita = operadorSolicitante;
        this.operadoresCompartidos = new ArrayList<>();
        this.porcentajesCarga = new ArrayList<>();
        this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }
    }

    /**
     * Método que devuelve el porcentaje que se solicita compartir.
     * 
     * @return Porcentaje.
     */
    public double getPorcentaje() {
        return this.porcentaje;
    }

    /**
     * Método que devuelve el estado de la solicitud.
     * 
     * @return Estado de la solicitud.
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    public void agregarOperadorCompartido(Operador operador, double porcentajeCarga) {
        this.operadoresCompartidos.add(operador);
        this.porcentajesCarga.add(porcentajeCarga);
    }

    public void aceptar() {
        if (esSolicitudValida()) {
            this.estado = EstadoSolicitud.ACEPTADA;
            this.aceptadoPorAerolíneaOriginal = true;
        } else {
            this.estado = EstadoSolicitud.RECHAZADA;
        }
    }

    public void rechazar() {
        this.estado = EstadoSolicitud.RECHAZADA;
    }
}
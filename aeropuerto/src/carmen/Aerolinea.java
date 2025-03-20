package aeropuerto.src.carmen;

import java.util.*;
import java.time.*;
import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.factura.*;

/**
 * La clase Aerolinea representa una aerolinea que opera en el aeropuerto.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Aerolinea {
    /** Nombre de la aerolínea */
    private String nombre;
    /** Código de la aerolínea */
    private String codigo;
    /** Operador de la aerolínea */
    private Operador operador;
    /** Lista de solicitudes recibidas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesRecibidas;
    /** Lista de solicitudes enviadas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesEnviadas;
    /** Facturas de la aerolínea */
    private List<Factura> facturas;

    private List<VueloProgramado> vuelos;

    public Aerolinea(String nombre, String codigo, Operador operador) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.operador = operador;
    }

    /* Añadir cosas de sus listas */
    public String getNombre() {
        return this.nombre;
    }

    public Operador getOperador() {
        return this.operador;
    }

    public List<VueloProgramado> vuelosMensuales(LocalDate fecha) {
        List<VueloProgramado> vuelosMensuales = new ArrayList<>();
        for (VueloProgramado vuelo : this.vuelos) {
            if ((vuelo.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getMonth() == fecha
                    .getMonth()) {
                vuelosMensuales.add(vuelo);
            }
        }
        return vuelosMensuales;
    }

}
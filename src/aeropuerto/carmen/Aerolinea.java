package src.aeropuerto.carmen;
import java.util.*;
import java.time.*;

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
    /**Lista de solicitudes recibidas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesRecibidas;
    /**Lista de solicitudes enviadas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesEnviadas;
    /** Facturas de la aerolínea */
    private List<Factura> facturas;

    private List<VueloProgramado> vuelos;

    public Aerolinea(String nombre, String codigo, Operador operador, List<SolicitudVueloCompartido> solicitudesRecibidas,List<SolicitudVueloCompartido> solicitudesEnviada, List<Factura> facturas, List<VueloProgramado> vuelos){
        this.nombre=nombre;
        this.codigo=codigo;
        this.operador=operador;
        this.solicitudesRecibidas= solicitudesRecibidas;
        this.solicitudesEnviadas= solicitudesEnviadas;
        this.facturas=facturas;
        this.vuelos=vuelos;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Operador getOperador(){
        return this.operador;
    }

    public List<VueloProgramado> vuelosMensuales(Month mes, int anyo){
        List<VueloProgramado> vuelosMensuales= new ArrayList<>();
        for(VueloProgramado vuelo: this.vuelos){
            if((vuelo.getFecha().getYear()==ano) && (vuelo.getFecha().getMonth()==mes)){
                vuelosMensuales.add(vuelo);
            }
        }
        return vuelosMensuales;
    }




}
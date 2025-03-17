package aeropuerto.carmen;
import java.util.*;
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



}
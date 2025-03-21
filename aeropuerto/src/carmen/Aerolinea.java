package aeropuerto.src.carmen;

import java.util.*;
import java.time.*;
import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.factura.*;
import aeropuerto.src.claudia.*;

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
    /** Facturas de la aerolínea */
    private List<Factura> facturas;

    private List<VueloProgramado> vuelosProgramados;
    private List<Vuelo> vuelos;

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

    public List<Vuelo> vuelosMensuales(Month mes) {
        List<Vuelo> vuelosMensuales = new ArrayList<>();
        for (Vuelo vuelo : this.vuelos) {
            if (vuelo.getFecha().getMonth() == mes) {
                vuelosMensuales.add(vuelo);
            }
        }
        return vuelosMensuales;
    }

	public String getCodigo() {
		return this.codigo;
	}
	
	public void addFactura(Factura factura) {
		this.facturas.add(factura);
	}
	
	public List<Vuelo> getVuelos(){
		return this.vuelos;
	}


}
package aeropuerto.src.carmen;

import java.util.*;
import java.time.*;
import aeropuerto.src.carmen.avion.*;
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
    /** Aviones de la aerolínea */
    private List<Avion> aviones;

    /**
     * Constructor de la clase Aerolinea.
     * 
     * @param nombre 		Nombre de la aerolinea.
     * @param codigo        Código de la aerolinea.
     * @param operador      Operador de la aerolinea.
     */
    public Aerolinea(String nombre, String codigo, Operador operador) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.operador = operador;
        this.facturas= new ArrayList<>();
        this.aviones= new ArrayList<>();
    }

    /**
     * Método que devuelve el nombre de la aerolinea.
     * 
     * @return Nombre de la aerolinea.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que devuelve el operador de la aerolinea.
     * 
     * @return Operador de la aerolinea.
     */
    public Operador getOperador() {
        return this.operador;
    }

    /**
     * Método que devuelve la lista de vuelos de una aerolinea en un mes.
     * 
     * @param mes 		Mes de los que se quieren los vuelos
     * @return Lista de vuelos mensuales de la aerolinea.
     */
    public List<Vuelo> vuelosMensuales(Month mes) {
        List<Vuelo> vuelosMensuales = new ArrayList<>();
        for (Vuelo vuelo : this.getVuelos()) {
            if (vuelo.getFecha().getMonth() == mes) {
                vuelosMensuales.add(vuelo);
            }
        }
        return vuelosMensuales;
    }

    /**
     * Método que devuelve el código de la aerolinea.
     * 
     * @return Código de la aerolinea.
     */
	public String getCodigo() {
		return this.codigo;
	}
	
	/**
     * Método que añade una factura a las de la aerolinea.
     * 
     * @param factura	Factura que se quiere añadir.
     */
	public void addFactura(Factura factura) {
		this.facturas.add(factura);
	}
	
	/**
     * Método que devuelve la lista de vuelos de la aerolinea.
     * 
     * @return Vuelos de la aerolinea.
     */
	public List<Vuelo> getVuelos(){
		return this.operador.getVuelos();
	}

	/**
     * Método que devuelve la lista de facturas de la aerolinea.
     * 
     * @return Facturas de la aerolinea.
     */
	public List<Factura>getFacturas() {
		return this.facturas;
	}
	
	/**
     * Método que devuelve la lista de aivones de la aerolinea.
     * 
     * @return Aviones de la aerolinea.
     */
	public List<Avion> getAviones(){
		return this.aviones;
	}
	
	/**
     * Método que devuelve el nombre de la aerolinea.
     * 
     * @return Nombre de la aerolinea.
     */
	public void agregarAvion(Avion avion) {
		avion.setAerolinea(this);
		this.aviones.add(avion);
	}

	/**
     * Método que establece un nuevo operador de la aerolinea.
     * 
     * @param operador		Nuevo operador.
     */
	public void setOperador(Operador operador) {
		this.operador=operador;
		
	}



}
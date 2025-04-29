package src.airportmanager.avion;

import java.util.*;


import src.airportmanager.factura.*;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.Vuelo;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * La clase Aerolinea representa una aerolinea que opera en el aeropuerto.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Aerolinea implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Nombre de la aerolínea */
    private String nombre;
    /** Código de la aerolínea */
    private String codigo;
    /** Operador de la aerolínea */
    private Operador operador;
    /** Facturas de la aerolínea */
    private Set<Factura> facturas;
    /** Aviones de la aerolínea */
    private List<Avion> aviones;

    /**
     * Constructor de la clase Aerolinea.
     * 
     * @param nombre   Nombre de la aerolinea.
     * @param codigo   Código de la aerolinea.
     */
    public Aerolinea(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.operador = null;
        this.facturas = new HashSet<>();
        this.aviones = new ArrayList<>();
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
     * @param mes Mes de los que se quieren los vuelos
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
     * @param factura Factura que se quiere añadir.
     */
    public void addFactura(Factura factura) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNueva = LocalDate.parse(factura.getInvoiceDate(), formatter);
        YearMonth mesNueva = YearMonth.from(fechaNueva);

        boolean yaExiste = false;
        for (Factura f : facturas) {
            LocalDate fechaExistente = LocalDate.parse(f.getInvoiceDate(), formatter);
            YearMonth mesExistente = YearMonth.from(fechaExistente);
            if (mesExistente.equals(mesNueva)) {
                yaExiste = true;
                break;
            }
        }

        if (yaExiste) {
            System.out.println("Ya existe una factura para el mes: " + mesNueva);
            return;
        }

        this.facturas.add(factura);
    }

    /**
     * Método que devuelve la lista de vuelos de la aerolinea.
     * 
     * @return Vuelos de la aerolinea.
     */
    public List<Vuelo> getVuelos() {
    	List<Vuelo> vuelos= new ArrayList<>();
    	for(Vuelo vuelo: getOperador().getAeropuerto().getVuelosAceptados()) {
    		for(Avion avion: getAviones()) {
    			if(vuelo.getAvion().equals(avion)) {
    				vuelos.add(vuelo);
    			}
    		}
    	}  
    	return vuelos;
    }

    /**
     * Método que devuelve la lista de facturas de la aerolinea.
     * 
     * @return Facturas de la aerolinea.
     */
    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    /**
     * Método que devuelve la lista de aivones de la aerolinea.
     * 
     * @return Aviones de la aerolinea.
     */
    public List<Avion> getAviones() {
        return this.aviones;
    }

    /**
     * Método que devuelve el nombre de la aerolinea.
     * 
     * @param avion 	Avion
     */
    public void agregarAvion(Avion avion) {
        avion.setAerolinea(this);
        this.aviones.add(avion);
    }

    /**
     * Método que establece un nuevo operador de la aerolinea.
     * 
     * @param operador Nuevo operador.
     */
    public void setOperador(Operador operador) {
        this.operador = operador;

    }

    /**
     * Método devuelve una factura dado su código.
     * 
     * @param codigo Código de la factura.
     * @return La fatura con ese código.
     */
	public Factura buscarFacturaPorCodigo(String codigo) {
		for (Factura f : getFacturas()) {
            if (f.getInvoiceIdentifier().equalsIgnoreCase(codigo)) {
                return f;
            }
        }
        return null;
	}

}
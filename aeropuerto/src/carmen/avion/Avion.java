package aeropuerto.src.carmen.avion;

import aeropuerto.src.claudia.*;
import aeropuerto.src.carmen.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * La clase Avión representa un avión de una aerolinea que realizará una
 * serie de vuelos diarios.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Avion{
    /** Marca del avión */
    private String marca;
    /** Modelo del avión */
    private String modelo;
    /** Dimensiones de alto del avión */
    private double dimAlto;
    /** Dimensiones de ancho del avión */
    private double dimAncho;
    /** Dimensiones de largo del avión */
    private double dimLargo;
    /** Autonomía del avión */
    private double autonomia;
    /** Año de compra del avión */
    private int anoCompra;
    /** Fecha de la última revisión del avión */
    private Date fechaUltimaRevision;
    /** Matrícula del avión */
    private String matricula;
    /** Aerolinea del avión */
    private Aerolinea aerolinea;
    /** Vuelos del avión */
    private List<Vuelo> vuelos;
    
    /**
     * Constructor de la clase Avion.
     * 
     * @param marca               Marca del avión.
     * @param modelo              Modelo del avión.
     * @param dimAlto             Dimensión de alto del avión.
     * @param dimAncho            Dimensión de ancho del avión.
     * @param dimLargo            Dimensión de largo del avión.
     * @param autonomia           Autonomía del avión.
     * @param anoCompra           Año de compra del avión.
     * @param fechaUltimaRevision Fecha de la última revisión del avión.
     * @param matricula           Matrícula del avión.
     */
    public Avion(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula) {
        this.marca = marca;
        this.modelo = modelo;
        this.dimAlto = dimAlto;
        this.dimAncho = dimAncho;
        this.dimLargo = dimLargo;
        this.autonomia = autonomia;
        this.anoCompra = anoCompra;
        this.fechaUltimaRevision = fechaUltimaRevision;
        this.matricula = matricula;
        this.vuelos=new ArrayList<Vuelo>();
        this.aerolinea=null;
    }

    /**
     * Método que devuelve la marca de un Avion.
     * 
     * @return Marca.
     */
    public String getMarca() {
        return this.marca;
    }

    /**
     * Método que devuelve el modelo de un Avion.
     * 
     * @return Modelo.
     */
    public String getModelo() {
        return this.modelo;
    }

    /**
     * Método que devuelve la dimensión de altura de un Avion.
     * 
     * @return La dimensión de altura.
     */
    public double getDimAlto() {
        return this.dimAlto;
    }

    /**
     * Método que devuelve la dimensión de ancho de un Avion.
     * 
     * @return La dimensión de ancho.
     */
    public double getDimAncho() {
        return this.dimAncho;
    }

    /**
     * Método que devuelve la dimensión de largo de un Avion.
     * 
     * @return La dimensión de largo.
     */
    public double getDimLargo() {
        return this.dimLargo;
    }

    /**
     * Método que devuelve la autonomia de un Avion.
     * 
     * @return Autonomía.
     */
    public double getAutonomia() {
        return this.autonomia;
    }

    /**
     * Método que devuelve año de compra de un Avion.
     * 
     * @return Año de compra.
     */
    public int getAnoCompra() {
        return this.anoCompra;
    }

    /**
     * Método que devuelve la fecha de la última revisión de un Avion.
     * 
     * @return Fecha de última revisión.
     */
    public Date getFechaUltimaRevision() {
        return this.fechaUltimaRevision;
    }

    /**
     * Método que devuelve la matrícula de un Avion.
     * 
     * @return Matrícula.
     */
    public String getMatricula() {
        return this.matricula;
    }
    
    /**
     * Método que establece la Aerolinea de un Avion.
     * 
     * @param aerolinea 	Aerolinea a la que pertenece.
     */
    public void setAerolinea(Aerolinea aerolinea) {
    	this.aerolinea=aerolinea;
    }
    
    /**
     * Método que devuelve la aerolinea de un Avion.
     * 
     * @return Aerolinea.
     */
    public Aerolinea getAerolinea() {
    	return this.aerolinea;
    }
    
    /**
     * Método que añade un vuelo al Avion.
     * 
     * @param vuelo		 Nuevo vuelo.
     */
    public void agregarVuelo(Vuelo vuelo) {
    	this.vuelos.add(vuelo);
    }

    /**
     * Método que devuelve si un avión está disponible a una hora y fecha determinada.
     * 
     * @param fecha		Día que se quiere verificar.
     * @param hora		Hora que se quiere verificar.
     * 
     * @return True si está disponible, false en caso contrario.
     */
	public boolean estaDisponible(LocalDate fecha, LocalTime hora) {
		for(Vuelo vuelo: this.vuelos) {
			if(!vuelo.getHora().equals(hora) &&!vuelo.getFecha().equals(fecha)) {
				return true;
			}
		}
		return false;
	}
}

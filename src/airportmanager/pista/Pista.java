package src.airportmanager.pista;

import java.util.*;


import src.airportmanager.vuelo.Vuelo;

/**
 * La clase Pista representa una pista de aeropuerto que puede ser de aterrizaje
 * o despegue.
 * 
 * @author Claudia Municio, María Pozo, Carmen Gómez.
 */
public class Pista {
    /** Código de la pista */
    private String codigo;
    /** Longitud de la pista */
    private double longitud;
    /** Tipo de la pista */
    private TipoPista tipoPista;
    /** Vuelos si la pista está ocupada */
    private List<Vuelo> vuelos;

    /**
     * Constructor de la clase Pista.
     * 
     * @param codigo    Código de la pista
     * @param longitud  Longitud de la pista
     * @param tipoPista Tipo de pista (aterrizaje o despegue)
     */
    public Pista(String codigo, double longitud, TipoPista tipoPista) {
        this.codigo = codigo;
        this.longitud = longitud;
        this.tipoPista = tipoPista;
        this.vuelos = new ArrayList<>();
    }

    /**
     * Método que devuelve el código de la pista.
     * 
     * @return Código de la pista.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Método que devuelve la longitud de la pista.
     * 
     * @return Longitud de la pista.
     */
    public double getLongitud() {
        return this.longitud;
    }

    /**
     * Método que devuelve el tipo de la pista.
     * 
     * @return Tipo de la pista.
     */
    public TipoPista getTipoPista() {
        return this.tipoPista;
    }

    /**
     * Método que devuelve los vuelos que ocupa la pista.
     * 
     * @return Vuelos de la pista.
     */
    public List<Vuelo> getVuelos() {
        return this.vuelos;
    }

    /**
     * Método que añade un vuelo a la pista.
     * 
     * @param vuelo Vuelo de la pista.
     */
    public void addVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    /**
     * Método que quita un vuelo de la pista.
     * 
     * @param vuelo Vuelo de la pista.
     */
    public void removeVuelo(Vuelo vuelo) {
        this.vuelos.remove(vuelo);

    }

}

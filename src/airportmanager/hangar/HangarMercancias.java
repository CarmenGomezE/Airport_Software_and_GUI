package src.airportmanager.hangar;

import java.util.*;

import src.airportmanager.avion.*;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.vuelo.Vuelo;

/**
 * La clase HangarMercancias representa los hangares de mercancias del
 * aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class HangarMercancias extends Hangar {

	/** Número de versión de la clase */
    private static final long serialVersionUID = 1L;
	/** Permiso para mercancías peligrosas */
    private boolean mercaPeligrosa;
    /** Aviones de mercancías en el hangar */
    private List<AvionMercancias> aviones;

    /**
     * Constructor de la clase HangarMercancias.
     * 
     * @param numeroMaxAviones Número máximo de aviones del hangar.
     * @param dimPlazaAncho    Ancho de cada plaza del hangar.
     * @param dimPlazaAlto     Alto de cada plaza del hangar.
     * @param dimPlazaLargo    Largo de cada plaza del hangar.
     * @param costePorHora     Coste por hora del hangar.
     * @param mercaPeligrosa   Permiso para mercancías peligrosas.
     */
    public HangarMercancias(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo,
            double costePorHora, boolean mercaPeligrosa) {
        super(numeroMaxAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
        this.mercaPeligrosa = mercaPeligrosa;
        this.aviones = new ArrayList<>();
    }

    /**
     * Método para obtener los aviones de mercancías en un hangar de mercancías.
     * 
     * @return Lista de aviones de mercancías en un hangar de mercancías.
     */
    public List<AvionMercancias> getAviones() {
        return aviones;
    }

    /**
     * Método para ver si hay espacio en el hangar.
     * 
     * @return true si hay espacio, false si no.
     */
    @Override
    public boolean hayEspacioEnHangar() {
        if (aviones.size() < numeroMaxAviones) {
            return true;
        }

        return false;
    }

    /**
     * Método para comprobar si un avión con mercancía peligrosa puede entrar en el
     * hangar.
     * 
     * @param vuelo Vuelo que tiene el avión que entra a hangar.
     * @return True si es, false si no.
     */
    public boolean comprobarMercaPeligrosa(Vuelo vuelo) {
        if (vuelo.getAvion() instanceof AvionMercancias) {
            AvionMercancias avionMercancias = (AvionMercancias) vuelo.getAvion();
            if (avionMercancias.getMercaPeligrosa() && !mercaPeligrosa) {
                return false;
            }
        }

        return true;
    }

    /**
     * Método para añadir un avion al hangar.
     * 
     * @param avion Avion que se añade al hangar.
     * @throws CargaAvionEquivocadaException Tipo de carga equivocado.
     */
    @Override
	public void addAvion(Avion avion) throws CargaAvionEquivocadaException {
        if (avion instanceof AvionMercancias == false) {
    		throw new CargaAvionEquivocadaException("El avión debe ser de mercancías para añadirlo.");
    	}
        
        if (hayEspacioEnHangar()) {
            this.aviones.add((AvionMercancias) avion);
        }
    }
}

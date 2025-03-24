package src.airportmanager.hangar;

import java.util.*;

import src.airportmanager.avion.*;
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
     * Método para obtener el permiso para mercancías peligrosas.
     * 
     * @return true si el hangar de mercancías tiene permiso para mercancías
     *         peligrosas o false si no.
     */
    public boolean getMercaPeligrosa() {
        return mercaPeligrosa;
    }

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     */
    @Override
    public void sacarAvion(Avion avion) {
        aviones.remove((AvionMercancias)avion);
    }

    /**
     * Método para comprobar si un avión está en el hangar.
     * 
     * @param avion Avión a comprobar.
     * 
     * @return true si está, false si no.
     */
    @Override
    public boolean comprobarAvionEnHangar(Avion avion) {
        return aviones.contains((AvionMercancias)avion);
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
     */
    @Override
	public void addAvion(Avion avion) {
        this.aviones.add((AvionMercancias) avion);

    }

}

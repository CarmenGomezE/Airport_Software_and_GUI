package src.airportmanager.hangar;

import java.util.*;

import src.airportmanager.avion.*;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;

/**
 * La clase HangarPasajeros representa los hangares de pasajeros del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class HangarPasajeros extends Hangar {
	/**Número de versión de clase*/
    private static final long serialVersionUID = 1L;
	/** Aviones de pasajeros en el hanagar */
    private List<AvionPasajeros> aviones;

    /**
     * Constructor de la clase HangarPasajeros.
     * 
     * @param numeroMaxAviones Número máximo de aviones del hangar.
     * @param dimPlazaAncho    Ancho de cada plaza del hangar.
     * @param dimPlazaAlto     Alto de cada plaza del hangar.
     * @param dimPlazaLargo    Largo de cada plaza del hangar.
     * @param costePorHora     Coste por hora del hangar.
     */
    public HangarPasajeros(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo,
            double costePorHora) {
        super(numeroMaxAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
        this.aviones = new ArrayList<>();
    }

    /**
     * Método para obtener los aviones de pasajeros que hay en un hangar de
     * pasajeros.
     * 
     * @return Lista de aviones de pasajeros que hay en un hangar de pasajeros.
     */
    public List<AvionPasajeros> getAviones() {
        return aviones;
    }

    /**
     * Método para añadir un avión al hangar.
     * 
     * @param avion Avión que entra al hangar.
     */
    @Override
    public void addAvion(Avion avion) throws CargaAvionEquivocadaException {
    	if (avion instanceof AvionPasajeros == false) {
    		throw new CargaAvionEquivocadaException("El avión debe ser de pasajeros para añadirlo.");
    	}
        aviones.add((AvionPasajeros) avion);
    }

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     */
    @Override
    public void sacarAvion(Avion avion) throws CargaAvionEquivocadaException {
    	if (avion instanceof AvionPasajeros == false) {
    		throw new CargaAvionEquivocadaException("El avión debe ser de pasajeros para sacarlo.");
    	}
        aviones.remove(avion);
    }

    /**
     * Método para comprobar si un avión está en el hangar.
     * 
     * @param avion Avión a comprobar.
     * 
     * @return true si está, false si no.
     * @throws CargaAvionEquivocadaException carga no válida.
     */
    @Override
    public boolean comprobarAvionEnHangar(Avion avion) throws CargaAvionEquivocadaException {
    	if (avion == null) {
    		throw new IllegalArgumentException("El avión no puede ser nulo.");
    	}
    	if (avion instanceof AvionPasajeros == false) {
    		throw new CargaAvionEquivocadaException("El avión debe ser de pasajeros para comprobar si está en el hangar.");
    	}
        return aviones.contains((AvionPasajeros)avion);
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

}

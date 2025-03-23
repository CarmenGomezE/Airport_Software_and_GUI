package aeropuerto.src.maria;

import aeropuerto.src.carmen.avion.*;
import java.util.*;
/**
 * La clase HangarMercancias representa los hangares de mercancias del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class HangarMercancias extends Hangar {
    /** Permiso para mercancías peligrosas*/
    private boolean mercaPeligrosa;
    /** Aviones de mercancías en el hangar*/
    private List<AvionMercancias> aviones;

    /** 
     * Constructor de la clase HangarMercancias.
     * @param numeroMaxAviones  Número máximo de aviones del hangar.
     * @param dimPlazaAncho     Ancho de cada plaza del hangar.
     * @param dimPlazaAlto      Alto de cada plaza del hangar.
     * @param dimPlazaLargo     Largo de cada plaza del hangar.
     * @param costePorHora      Coste por hora del hangar.
     * @param mercaPeligrosa    Permiso para mercancías peligrosas.
     */
    public HangarMercancias(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo, double costePorHora, boolean mercaPeligrosa) {
        super(numeroMaxAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
        this.mercaPeligrosa = mercaPeligrosa;
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
     * @return true si el hangar de mercancías tiene permiso para mercancías peligrosas o false si no.
     */
    public boolean getMercaPeligrosa() {
        return mercaPeligrosa;
    }

    /**
     * Método para añadir un avión al hangar.
     * 
     * @param avion Avión que entra al hangar.
     */
    public void addAvion(AvionMercancias avion) {
        aviones.add(avion);
    }

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     */
    public void sacarAvion(AvionMercancias avion) {
        for (AvionMercancias a: this.aviones) {
            if (a == avion) {
                aviones.remove(avion);
            }
        }
    }

    /**
     * Método para comprobar si un avión está en el hangar.
     * 
     * @param avion Avión a comprobar.
     * 
     * @return true si está, false si no.
     */
    public boolean comprobarAvionEnHangar(AvionMercancias avion) {
        return aviones.contains(avion);
    }

    /**
     * Método para ver si hay espacio en el hangar.
     * 
     * @return true si hay espacio, false si no.
     */
    public boolean hayEspacioEnHangar() {
        if (aviones.size() < numeroMaxAviones) {
            return true;
        }

        return false;
    }
}

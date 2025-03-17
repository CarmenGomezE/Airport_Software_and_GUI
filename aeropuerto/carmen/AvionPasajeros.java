package aeropuerto.carmen;
import java.util.*;
/**
 * La clase AvionPasajeros representa un avión de pasajeros que realizará una 
 * serie de vuelos diarios. 
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AvionPasajeros extends Avion {
    /** Capacidad máxima de pasajeros del avión de mercancías */
    private int capacidadMaxPasajeros;

    /**
     * Constructor de la clase AvionPasajeros.
     * 
     * @param marca                     Marca del avión.
     * @param modelo                    Modelo del avión.
     * @param dimAlto                   Dimensión de alto del avión.
     * @param dimAncho                  Dimensión de ancho del avión.
     * @param dimLargo                  Dimensión de largo del avión.
     * @param autonomia                 Autonomía del avión.
     * @param anoCompra                 Año de compra del avión.
     * @param fechaUltimaRevision       Fecha de la última revisión del avión.
     * @param matricula                 Matrícula del avión.
     * @param capacidadMaxPasajeros     Capacidad máxima del avión.
     */
    public AvionPasajeros(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
                         double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
                         int capacidadMaxPasajeros) {
        super(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, fechaUltimaRevision, matricula);
        this.capacidadMaxPasajeros=capacidadMaxPasajeros;
    }

    /**
     * Método que devuelve la capacidad máxima de pasajeros de un AvionPasajeros.
     * 
     * @return Capacidad máxima de pasajeros.
     */
    public int getCapacidadMaxPasajeros(){
        return this.capacidadMaxPasajeros;
    }

}

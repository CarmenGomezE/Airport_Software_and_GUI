package src.airportmanager.hangar;

import java.io.Serializable;

import src.airportmanager.avion.Avion;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.vuelo.Vuelo;

/**
 * La clase Hangar representa los hangares del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Hangar implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Contador para el identificador del hangar */
    private static int contadorId = 1;

    /** Código único del hangar */
    private int id;
    /** Número máximo de aviones del hangar */
    protected int numeroMaxAviones;
    /** Ancho de cada plaza del hangar */
    private double dimPlazaAncho;
    /** Alto de cada plaza del hangar */
    private double dimPlazaAlto;
    /** Largo de cada plaza del hangar */
    private double dimPlazaLargo;
    /** Coste por hora del hangar */
    private double costePorHora;
    /** Número de horas de uso del hangar */
    private double horasUso;

    /**
     * Constructor de la clase Hangar.
     * 
     * @param numeroMaxAviones Número máximo de aviones del hangar.
     * @param dimPlazaAncho    Ancho de cada plaza del hangar.
     * @param dimPlazaAlto     Alto de cada plaza del hangar.
     * @param dimPlazaLargo    Largo de cada plaza del hangar.
     * @param costePorHora     Coste por hora del hangar.
     */
    public Hangar(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo,
            double costePorHora) {
        this.id = contadorId++;
        this.numeroMaxAviones = numeroMaxAviones;
        this.dimPlazaAncho = dimPlazaAncho;
        this.dimPlazaAlto = dimPlazaAlto;
        this.dimPlazaLargo = dimPlazaLargo;
        this.costePorHora = costePorHora;
        this.horasUso = 0;
    }

    /**
     * Método para obtener el código único del hangar.
     * 
     * @return Código único del hangar.
     */
    public int getId() {
        return id;
    }

    /**
     * Método para obtener el número máximo de aviones del hangar.
     * 
     * @return Número máximo de aviones del hangar.
     */
    public int getNumeroMaxAviones() {
        return numeroMaxAviones;
    }

    /**
     * Método para obtener el ancho de cada plaza del hangar.
     * 
     * @return Ancho de cada plaza del hangar.
     */
    public double getDimPlazaAncho() {
        return dimPlazaAncho;
    }

    /**
     * Método para obtener el alto de cada plaza del hangar.
     * 
     * @return Alto de cada plaza del hangar.
     */
    public double getDimPlazaAlto() {
        return dimPlazaAlto;
    }

    /**
     * Método para obtener el largo de cada plaza del hangar.
     * 
     * @return Largo de cada plaza del hangar.
     */
    public double getDimPlazaLargo() {
        return dimPlazaLargo;
    }

    /**
     * Método para obtener el coste por hora del hangar.
     * 
     * @return Coste por hora del hangar.
     */
    public double getCostePorHora() {
        return costePorHora;
    }

    /**
     * Método para obtener el número de horas de uso del hangar.
     * 
     * @return Número de horas de uso del hangar.
     */
    public double getHorasUso() {
        return horasUso;
    }

    /**
     * Método para añadir horas de uso al hangar.
     * 
     * @param horas Horas que se suman.
     */
    public void addHorasUso(double horas) {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas deben ser un número positivo");
        }

        horasUso += horas;
    }

    /**
     * Método para reiniciar a 0 las horas de uso del hangar.
     */
    public void resetHorasUso() {
        horasUso = 0;
    }

    /**
     * Método para comprobar si un avion entra en las plazas del hangar.
     * 
     * @param vuelo El vuelo que tiene el avion que va a entrar en hangar.
     * 
     * @return true si entra, false si no.
     */
    public boolean comprobarTamano(Vuelo vuelo) {
        if (vuelo.getAvion().getDimAlto() <= dimPlazaAlto && vuelo.getAvion().getDimAncho() <= dimPlazaAncho
                && vuelo.getAvion().getDimLargo() <= dimPlazaLargo) { 
            return true;
        }

        return false;
    }

    /**
     * Método que comprueba si hay espacio en el hangar.
     * 
     * @return True si hay, false si no.
     */
    public abstract boolean hayEspacioEnHangar();

    /**
     * Método para añadir un avion al hangar.
     * 
     * @param avion Avion que se añade al hangar.
     * @throws CargaAvionEquivocadaException carga no válida. 
     */
    public abstract void addAvion(Avion avion) throws CargaAvionEquivocadaException;

    /**
     * Método para comprobar si un avión está en el hangar.
     * 
     * @param avion Avión a comprobar.
     * 
     * @return true si está, false si no.
     * @throws CargaAvionEquivocadaException carga no válida. 
     */
    public abstract boolean comprobarAvionEnHangar(Avion avion) throws CargaAvionEquivocadaException;

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     * @throws CargaAvionEquivocadaException  carga no válida. 
     */
    public abstract void sacarAvion(Avion avion) throws CargaAvionEquivocadaException;

}
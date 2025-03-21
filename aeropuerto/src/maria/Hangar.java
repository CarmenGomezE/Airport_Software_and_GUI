package aeropuerto;
/**
 * La clase Hangar representa los hangares del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Hangar {
    private static int contadorId = 1; 

    /** Código único del hangar*/
    private int id;
    /** Número máximo de aviones del hangar*/
    private int numeroMaxAviones;
    /** Ancho de cada plaza del hangar*/
    private double dimPlazaAncho;
    /** Alto de cada plaza del hangar*/
    private double dimPlazaAlto;
    /** Largo de cada plaza del hangar*/
    private double dimPlazaLargo;
    /** Coste por hora del hangar*/
    private double costePorHora;
    /** Horas de uso del hangar*/
    private double horasUso;

    /**
     * Constructor de la clase Hangar.
     * 
     * @param numeroMaxAviones  Número máximo de aviones del hangar.
     * @param dimPlazaAncho     Ancho de cada plaza del hangar.
     * @param dimPlazaAlto      Alto de cada plaza del hangar.
     * @param dimPlazaLargo     Largo de cada plaza del hangar.
     * @param costePorHora      Coste por hora del hangar.
     */
    public Hangar(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo, double costePorHora) {
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
            return;
        }

        horasUso += horas;
    }

    /**
     * Método para reiniciar a 0 las horas de uso del hangar.
     */
    public void resetHorasUso() {
        horasUso = 0;
    }
}
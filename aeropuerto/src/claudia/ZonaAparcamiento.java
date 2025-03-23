package aeropuerto.src.claudia;
/**
 * La clase ZonaAparcamiento representa una zona de aparcamiento en un aeropuerto.
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class ZonaAparcamiento extends ModoEmbarque {
    /**La dimensión de ancho de la plaza de aparcamiento */
    private double dimPlazaAncho;
     /**La dimensión de largo de la plaza de aparcamiento */
    private double dimPlazaLargo;

    private double horasUso;
    private int id;
    private static int contador = 1;

    /**
     * Constructor de la clase ZonaAparcamiento
     * @param codigo                    Código de la zona de aparcamiento.
     * @param costePorHora              Coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho             Dimensión de ancho de la zona de aparcamiento.
     * @param dimPlazaLargo             dimensión de largo de la zona de aparcamiento.
     */
    public ZonaAparcamiento(String codigo, double costePorHora, double dimPlazaAncho, double dimPlazaLargo) {
        super(codigo, costePorHora);
        this.dimPlazaAncho = dimPlazaAncho;
        this.dimPlazaLargo = dimPlazaLargo;
        this.horasUso = 0;
        this.id = contador ++;
    }

    /**
     * Método que devuelve la dimensión de ancho de la plaza de aparcamiento.
     * @return Dimensión de ancho de la plaza.
     */
    public double getDimPlazaAncho() {
        return this.dimPlazaAncho;
    }

     /**
     * Método que devuelve la dimensión de largo de la plaza de aparcamiento.
     * @return Dimensión de largo de la plaza.
     */
    public double getDimPlazaLargo() {
        return this.dimPlazaLargo;
    }

    public double getHorasUso() {
        return this.horasUso;
    }

    public void addHorasUso(double horas) {
        this.horasUso += horas;
    }

    public void horasUsoReset() {
        this.horasUso = 0;
    }

    public int getId(){
        return this.id;
    }

    /**
     * Método para comprobar si un avion entra en la zona de aparcamiento.
     * 
     * @param vuelo   El vuelo que tiene el avion que va a entrar en la zona de aparcamiento.
     * 
     * @return true si entra, false si no.
     */
    public boolean comprobarTamano(Vuelo vuelo) {
        if (vuelo.getAvion().getDimAncho() <= dimPlazaAncho && vuelo.getAvion().getDimLargo() <= dimPlazaLargo) {
            return true;
        }

        return false;
    }
}
package aeropuerto.claudia;
/**
 * La clase ZonaAparcamiento representa una zona de aparcamiento en un aeropuerto.
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class ZonaAparcamiento extends ModoEmbarque {
    /**La dimensión de ancho de la plaza de aparcamiento */
    private double dimPlazaAncho;
     /**La dimensión de largo de la plaza de aparcamiento */
    private double dimPlazaLargo;

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


}
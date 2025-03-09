/**
 * Representa una zona de aparcamiento en un aeropuerto.
 */
public class ZonaAparcamiento extends ModoEmbarque {
    private double dimPlazaAncho;
    private double dimPlazaLargo;

    /**
     * Crea una zona de aparcamiento.
     * @param codigo el código de la zona de aparcamiento.
     * @param costePorHora el coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho la dimensión de ancho de la zona de aparcamiento.
     * @param dimPlazaLargo la dimensión de largo de la zona de aparcamiento.
     */
    public ZonaAparcamiento(String codigo, double costePorHora, double dimPlazaAncho, double dimPlazaLargo) {
        super(codigo, costePorHora);
        this.dimPlazaAncho = dimPlazaAncho;
        this.dimPlazaLargo = dimPlazaLargo;

    }
}
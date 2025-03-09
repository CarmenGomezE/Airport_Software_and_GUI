package aeropuerto;
/**
 * La clase HangarMercancias representa los hangares de mercancias del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class HangarMercancias extends Hangar {
    /** Permiso para mercancías peligrosas*/
    private boolean mercaPeligrosa;
    /** Aviones de mercancías en el hangar*/
    private List<AvionMercancias> = aviones;

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
     * Método para añadir un avión al hangar.
     * 
     * @param avion Avión que entra al hangar.
     */
    public void añadirAvion(AvionMercancias avion) {
        aviones.add(avion);
    }

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     */
    public void sacarAvion(AvionMercancias avion) {
        for (AvionMercancias a: this.aviones) {
            if (a.equals(avion)) {
                this.aviones.remove(avion);
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
        return this.aviones.contains(avion);
    }
}
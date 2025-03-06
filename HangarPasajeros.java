/**
 * La clase HangarPasajeros representa los hangares de pasajeros del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class HangarPasajeros extends Hangar {
    /** Aviones de pasajeros en el hanagar*/
    private List<AvionPasajeros> = aviones;

    /** 
     * Constructor de la clase HangarPasajeros.
     * @param numeroMaxAviones  Número máximo de aviones del hangar.
     * @param dimPlazaAncho     Ancho de cada plaza del hangar.
     * @param dimPlazaAlto      Alto de cada plaza del hangar.
     * @param dimPlazaLargo     Largo de cada plaza del hangar.
     * @param costePorHora      Coste por hora del hangar.
     */
    public HangarPasajeros(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo, double costePorHora) {
        super(numeroMaxAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
    }
    
    /**
     * Método para añadir un avión al hangar.
     * 
     * @param avion Avión que entra al hangar.
     */
    public void añadirAvion(AvionPasajeros avion) {
        aviones.add(avion);
    }

    /**
     * Método para sacar un avión del hangar.
     * 
     * @param avion Avión que sale del hangar.
     */
    public void sacarAvion(AvionPasajeros avion) {
        for (AvionPasajeros a: this.aviones) {
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
    public boolean comprobarAvionEnHangar(AvionPasajeros avion) {
        return this.aviones.contains(avion);
    }
}
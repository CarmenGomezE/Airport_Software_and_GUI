/**
 * La clase AvionMercancias representa un avión de mercancias que realizará una 
 * serie de vuelos diarios. 
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AvionMercancias extends Avion {
    /** Bandera que indica si un avion de mercancias transporta mercancias peligrosas */
    private boolean mercaPeligrosa;
    /** Capacidad máxima de la carga del avión de mercancías */
    private double capacidadMaxCarga;
    /** Bandera que indica si un avion de mercancias requiere control de temperatura */
    private boolean controlTemperatura;

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
     * @param capacidadMaxCarga         Carga máxima del avión.
     * @param mercaPeligrosa            Bandera que indica si transporta mercancías peligrosas.
     * @param controlTemperatura        Bandera que indica si necesita control de temperatura.
     */
    public AvionMercancias(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo, 
                            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula, 
                            double capacidadMaxCarga, boolean mercaPeligrosa, boolean controlTemperatura) {

        super(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, fechaUltimaRevision, matricula);
        this.capacidadMaxCarga=capacidadMaxCarga;
        this.mercaPeligrosa=mercaPeligrosa;
        this.controlTemperatura= controlTemperatura;
    }

    /**
     * Método que devuelve la capacidad máxima de carga de un AvionMercancias.
     * 
     * @return Capacidad máxima de carga.
     */
    public double getCapacidadMaxCarga(){
        return this.capacidadMaxCarga;
    }

    /**
     * Método que devuelve si un AvionMercancias necesita control de temperatura.
     * 
     * @return True en caso de que necesite control de temperatura.
     */
    public boolean getControlTemperatura(){
        return this.controlTemperatura;
    }

    /**
     * Método que devuelve si un AvionMercancias transporta mercancias peligrosas.
     * 
     * @return True en caso de que transporte mercancías peligrosas
     */
    public boolean getMercaPeligrosa(){
        return this.mercaPeligrosa;
    }

}
package aeropuerto;
/**
 * Representa un finger de un aeropuerto.
 */
public class Finger extends ModoEmbarque {

    private double dimAlto;

    /**
    * Crea un finger de un aeropuerto
    * @param codigo el código del finger
    * @param costePorHora el coste por hora del finger
    * @param dimAlto la dimensión de alto del finger
    */
    public Finger(String codigo, double costePorHora, double dimAlto) {
        super(codigo, costePorHora);
        this.dimAlto = dimAlto;
        /*Falta poner puerta de embarque*/ 
    }


}
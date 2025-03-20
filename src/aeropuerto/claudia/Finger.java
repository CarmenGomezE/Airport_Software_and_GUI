package src.aeropuerto.claudia;
import java.util.*;
/**
 * La clase Finger representa un finger de un aeropuerto.
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Finger extends ModoEmbarque {

    /**La dimensión de alto del finger. */
    private double dimAlto;

    /**
    * Constructor de la clase Finger
    * @param codigo                 Código del finger.
    * @param costePorHora           Coste por hora del finger.
    * @param dimAlto                Dimensión de alto del finger.
    */
    public Finger(String codigo, double costePorHora, double dimAlto) {
        super(codigo, costePorHora);
        this.dimAlto = dimAlto;
        /*Falta poner puerta de embarque*/ 
    }

    /**
     * Método que devuelve el código del finger.
     * @return Código del finger.
     */
    public String getCodigo() {
        return this.codigo;
    }

     /**
     * Método que devuelve el coste por hora del finger.
     * @return Coste por hora del finger.
     */
    public double getCostePorHora() {
        return this.costePorHora;
    }
    
     /**
     * Método que devuelve la dimensión de alto del finger.
     * @return Dimensión de alto del finger.
     */
    public double getDimAlto() {
        return this.dimAlto;
    }



}
package src.aeropuerto.maria;
/**
 * La clase Aeropuerto representa los distintos aeropuertos que interactuan con el aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Aeropuerto {
    /** Nombre del aeropuerto*/
    private String nombre;
    /** Código del aeropuerto*/
    private String codigo;
    /** Ciudad más cercana al aeropuerto*/
    private String ciudadMasCercana;
    /** País del aeropuerto*/
    private String pais;
    /** Distancia hasta la ciudad más cercana al aeropuerto*/
    private double distanciaCiudad;
    /** Dirección relativa a la ciudad más cercana al aeropuerto*/
    private DireccionRelativaCiudad direccionCiudad;
    /** Diferencia horaria UTC*/
    private int diferenciaHoraria; /** <-------------mirar como se pone esto no se que de UTC-------------*/
    /** Horario de verano del aeropuerto*/
    private String horarioVerano; /** <-------------mirar esto me parece que habia un ejemplo en moodle-------------*/
    /** Horario de invierno del aeropuerto*/
    private Stirng horarioInvierno; /** <-------------mirar esto me parece que habia un ejemplo en moodle-------------*/

    /**
     * Constructor de la clase Aeropuerto.
     * 
     * @param nombre                Nombre del aeropuerto.
     * @param codigo                Código del aeropuerto.
     * @param ciudad                Ciudad más cercana al aeropuerto.
     * @param pais                  País del aeropuerto.
     * @param distanciaCiudad       Distancia hasta la ciudad más cercana al aeropuerto.
     * @param diferenciaHoraria     Diferencia horaria UTC.
     * @param horarioVerano         Horario de verano del aeropuerto.
     * @param horarioInvierno       Horario de invierno del aeropuerto.
     * @param direccionCiudad
     */
    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distanciaCiudad, int diferenciaHoraria, String horarioVerano, String horarioInvierno, DireccionRelativaCiudad direccionCiudad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.pais = pais;
        this.distanciaCiudad = distanciaCiudad;
        this.diferenciaHoraria = diferenciaHoraria;
        this.horarioVerano = horarioVerano;
        this.horarioInvierno = horarioInvierno;
        this.direccionCiudad = direccionCiudad;
    }
}
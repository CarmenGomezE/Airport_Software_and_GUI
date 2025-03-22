package aeropuerto.src.maria;
import java.time.ZoneOffset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    private ZoneOffset diferenciaHoraria; 
    /** Número de temporadas del aeropuerto*/
    private int numTemporadas;
    /** Temporadas del aeropuerto*/
    private Temporada[] temporadas;

    /**
     * Constructor de la clase Aeropuerto.
     * 
     * @param nombre                Nombre del aeropuerto.
     * @param codigo                Código del aeropuerto.
     * @param ciudad                Ciudad más cercana al aeropuerto.
     * @param pais                  País del aeropuerto.
     * @param distanciaCiudad       Distancia hasta la ciudad más cercana al aeropuerto.
     * @param diferenciaHoraria     Diferencia horaria UTC del aeropuerto.
     * @param numTemporadas         Número de temporadas del aeropuerto.
     * @param direccionCiudad       Dirección relativa a la ciudad más cercana del aeropuerto.
     */
    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distanciaCiudad, ZoneOffset diferenciaHoraria, int numTemporadas, DireccionRelativaCiudad direccionCiudad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.pais = pais;
        this.distanciaCiudad = distanciaCiudad;
        this.diferenciaHoraria = diferenciaHoraria;
        this.numTemporadas = numTemporadas;
        this.temporadas = new Temporada[numTemporadas];
        this.direccionCiudad = direccionCiudad;
    }

    /**
     * Método para obtener el nombre de un aeropuerto.
     * 
     * @return El nombre del aeropuerto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para obtener el código de un aeropuerto.
     * 
     * @return El código del aeropuerto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para obtener la ciudad de un aeropuerto.
     * 
     * @return La ciudad del aeropuerto.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Método para obtener el país de un aeropuerto.
     * 
     * @return El país del aeropuerto.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Método para obtener la distancia a la ciudad más cercana de un aeropuerto.
     * 
     * @return La distancia a la ciudad más cercana del aeropuerto.
     */
    public double getDistanciaCiudad() {
        return distanciaCiudad;
    }

    /**
     * Método para obtener la diferencia del horario de un aeropuerto.
     * 
     * @return La diferencia horaria del aeropuerto.
     */
    public ZoneOffset getDiferenciaHoraria() {
        return diferenciaHoraria;
    }

    /**
     * Método para obtener el número de temporadas de un aeropuerto.
     * 
     * @return El número de temporadas del aeropuerto.
     */
    public int getNumTemporadas() {
        return numTemporadas;
    }

    /**
     * Método para obtener la dirección relativa a la ciudad más cercana de un aeropuerto.
     * 
     * @return La dirección relativa a la ciudad más cercana del aeropuerto.
     */
    public DireccionRelativaCiudad getDireccionCiudad() {
        return direccionCiudad;
    }

    /**
     * Método para obtener las temporadas de un aeropuerto.
     * 
     * @return Las temporadas del aeropuerto.
     */
    public Temporada[] getTemporadas() {
        return temporadas;
    }

    /**
     * Método para añadir una temporada al aeropuerto.
     * 
     * @param nuevaTemporada    Nueva temporada del aeropuerto.
     */
    public void agregarTemporada(Temporada nuevaTemporada) {
        ArrayList<Temporada> listaTemporadas = new ArrayList<>(Array.asList(temporadas));

        for (Temporada t: listaTemporadas) {
            if (t != null && t.comprobarTemporada(nuevaTemporada)) {
                return;
            }
        }

        listaTemporadas.add(nuevaTemporada);
        listaTemporadas.sort(Comparator.comparing(Temporada::getFechaInicio));

        temporadas = listaTemporada.toArray(new Temporada[0]);
        numTemporadas++;
    }

}
package src.airportmanager.aeropuerto;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.util.*;

import src.airportmanager.exceptions.TemporadasSolapadasException;

/**
 * La clase Aeropuerto representa los distintos aeropuertos que interactuan con
 * el aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Aeropuerto implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Nombre del aeropuerto */
    private String nombre;
    /** Código del aeropuerto */
    private String codigo;
    /** Ciudad más cercana al aeropuerto */
    private String ciudadMasCercana;
    /** País del aeropuerto */
    private String pais;
    /** Distancia hasta la ciudad más cercana al aeropuerto */
    private double distanciaCiudad;
    /** Dirección relativa a la ciudad más cercana al aeropuerto */
    private DireccionRelativaCiudad direccionCiudad;
    /** Diferencia horaria UTC */
    private ZoneOffset diferenciaHoraria;
    /** Número de temporadas del aeropuerto */
    private int numTemporadas;
    /** Temporadas del aeropuerto */
    protected List<Temporada> temporadas;

    /**
     * Constructor de la clase Aeropuerto.
     * 
     * @param nombre            Nombre del aeropuerto.
     * @param codigo            Código del aeropuerto.
     * @param ciudad            Ciudad más cercana al aeropuerto.
     * @param pais              País del aeropuerto.
     * @param distanciaCiudad   Distancia hasta la ciudad más cercana al aeropuerto.
     * @param diferenciaHoraria Diferencia horaria UTC del aeropuerto.
     * @param numTemporadas     Número de temporadas del aeropuerto.
     * @param direccionCiudad   Dirección relativa a la ciudad más cercana del
     *                          aeropuerto.
     */
    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distanciaCiudad,
            ZoneOffset diferenciaHoraria, int numTemporadas, DireccionRelativaCiudad direccionCiudad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudadMasCercana = ciudad;
        this.pais = pais;
        this.distanciaCiudad = distanciaCiudad;
        this.diferenciaHoraria = diferenciaHoraria;
        this.numTemporadas = numTemporadas;
        this.temporadas = new ArrayList<>();
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
     * Método para modificar el nombre de un aeropuerto.
     * 
     * @param nombre El nombre del aeropuerto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * Método para modificar el código de un aeropuerto.
     * 
     * @param codigo El código del aeropuerto.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método para obtener la ciudad de un aeropuerto.
     * 
     * @return La ciudad del aeropuerto.
     */
    public String getCiudad() {
        return ciudadMasCercana;
    }

    /**
     * Método para modificar la ciudad de un aeropuerto.
     * 
     * @param ciudad La ciudad del aeropuerto.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
     * Método para modificar el país de un aeropuerto.
     * 
     * @param pais El país del aeropuerto.
     */
    public void setPais(String pais) {
        this.pais = pais;
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
     * Método para modificar la distancia a la ciudad más cercana de un aeropuerto.
     * 
     * @param distancia La distancia a la ciudad más cercana del aeropuerto.
     */
    public void setDistanciaCiudad(double distanciaCiudad) {
        this.distanciaCiudad = distanciaCiudad;
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
     * Método para modificar la diferencia horaria del país de un aeropuerto.
     * 
     * @param diferenciaHoraria La diferencia horaria del país del aeropuerto.
     */
    public void setDiferenciaHoraria(ZonaOffset diferenciaHoraria) {
        this.diferenciaHoraria = diferenciaHoraria;
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
     * Método para modificar el número de temporadas de un aeropuerto.
     * 
     * @param numTemporadas El número de temporadas del aeropuerto.
     */
    public void setNumTemporadas(int numTemporadas) {
        this.numTemporadas = numTemporadas;
    }

    /**
     * Método para obtener la dirección relativa a la ciudad más cercana de un
     * aeropuerto.
     * 
     * @return La dirección relativa a la ciudad más cercana del aeropuerto.
     */
    public DireccionRelativaCiudad getDireccionCiudad() {
        return direccionCiudad;
    }

    /**
     * Método para modificar la dirección relativa a la ciudad más cercana de un aeropuerto.
     * 
     * @param pais La dirección relativa a la ciudad más cercana del aeropuerto.
     */
    public void setDireccionCiudad(DireccionRelativaCiudad direccionCiudad) {
        this.direccionCiudad = direccionCiudad;
    }

    /**
     * Método para obtener las temporadas de un aeropuerto.
     * 
     * @return Las temporadas del aeropuerto.
     */
    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    /**
     * Método para modificar las temporadas de un aeropuerto.
     * 
     * @param temporadas Las temporadas del aeropuerto.
     */
    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    /**
     * Método para añadir una temporada al aeropuerto.
     * 
     * @param nuevaTemporada Nueva temporada del aeropuerto.
     * @throws TemporadasSolapadasException
     */
    public void agregarTemporada(Temporada nuevaTemporada) throws TemporadasSolapadasException {
    	if (nuevaTemporada == null) {
    	    throw new IllegalArgumentException("La nueva temporada no puede ser nula");
    	}

    	for (Temporada t : getTemporadas()) {
    	    if (t.comprobarTemporada(nuevaTemporada)) {
    	        throw new TemporadasSolapadasException("La nueva temporada se solapa con otra existente");
    	    }
    	}

    	temporadas.add(nuevaTemporada);

    	temporadas.sort(Comparator.comparing(
    	    Temporada::getFechaInicio));

    	numTemporadas=temporadas.size();
    }

}
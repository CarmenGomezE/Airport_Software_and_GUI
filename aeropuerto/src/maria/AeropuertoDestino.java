package aeropuerto.src.maria;
/**
 * La clase AeropuertoDestino representa los aeropuertos de destino que interactuan con el aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class AeropuertoDestino extends Aeropuerto{
    /**
     * Constructor de la clase AeropuertoDestino.
     * 
     * @param nombre                Nombre del aeropuerto destino.
     * @param codigo                Código del aeropuerto destino.
     * @param ciudad                Ciudad más cercana al aeropuerto destino.
     * @param pais                  País del aeropuerto destino.
     * @param distanciaCiudad       Distancia hasta la ciudad más cercana al aeropuerto destino.
     * @param diferenciaHoraria     Diferencia horaria UTC del aeropuerto destino.
     * @param numTemporadas         Número de temporadas del aeropuerto destino.
     * @param direccionCiudad       Dirección relativa a la ciudad más cercana del aeropuerto destino.
     */
    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distanciaCiudad, ZoneOffset diferenciaHoraria, int numTemporadas, DireccionRelativaCiudad direccionCiudad) {
        super(nombre, codigo, ciudad, pais, distanciaCiudad, diferenciaHoraria, numTemporadas, direccionCiudad);
    }
}
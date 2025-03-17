package aeropuerto;
import java.util.ArrayList;
import java.util.List;
/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el encargado
 * de cambiar los estados de vuelo de embarque y desembarque, así como el de en preparación
 * para despegar. Además podrá realizar otras acciones como dar de alta aviones, programar 
 * y compartir vuelos, recibir notificaciones o pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Operador extends UsuarioConDni {
    /** Lista de los vuelos que tiene programados */
    private List<VueloProgramado> vuelosPorgramados;
    /** Aerolinea para la que opera */
    private Aerolinea aerolinea;
    /** Estadísticas del operador sobre vuelos de su aerolinea */
    private EstadisticasOperador estadisticas;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param vuelosPorgramados     Vuelos programados asignados al operador.
     * @param aerolinea             Aerolinea a la que pertenece.
     * @param estadisticas          Estadísticas sobre vuelos.
     */
    public Operador( List<VueloProgramado> vuelosPorgramados, Aerolinea aerolinea, EstadisticasOperador estadisticas){
        this.vuelosPorgramados=new ArrayList<>();
        if(vuelosPorgramados!=null){
            for(VueloProgramado v: vuelosPorgramados){
                this.vuelosPorgramados.add(v);
            }
        }
        this.aerolinea=aerolinea;
        this.estadisticas=estadisticas;
    }

    /**
     * Método que devuelve los vuelos programados del Operador.
     * 
     * @return Vuelos programados del Operador.
     */
    public List<VueloProgramado> getVuelosProgramados(){
        return this.vuelosPorgramados;
    }

     /**
     * Método que añade vuelos programados a los del Operador.
     * 
     * @param vueloProgramado       Vuelo a añadir.
     */
    public void addVueloProgramado(VueloProgramado vueloPorgramado){
        this.vuelosPorgramados.add(vueloPorgramado);
    }


    /**
     * Método que devuelve la Aerolinea del Operador.
     * 
     * @return Aerolinea del Operador.
     */
    public Aerolinea getAerolinea(){
        return this.aerolinea;
    }

    /**
     * Método que devuelve las estadísticas del Operador.
     * 
     * @return Estadísticas el Operador.
     */
    public EstadisticasOperador getEstadisticasOperador(){
        return this.estadisticas;
    }

}
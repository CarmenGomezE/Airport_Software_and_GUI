package aeropuerto.carmen;
import aeropuerto.claudia.*;
import aeropuerto.maria.*;
import java.util.*;
import java.time.*;

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

    public void cambiarEstadoEnPreparacionDespegar(Vuelo vuelo){
        if(vuelo.getEstado()==EstadoVuelo.OPERATIVO){
            vuelo.cambiarEstado(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
        }
    }

    public void cambiarEstadoInicioEmbarque(Vuelo vuelo){
        LocalTime horaActual= LocalTime.now();
        long minutosDeDiferencia= Duration.between(horaActual, vuelo.getHora()).toMinutes();

        if( vuelo.getAvion() instanceof AvionMercancias){
            AvionMercancias avion = (AvionMercancias)vuelo.getAvion();
            double capacidadCarga= avion.getCapacidadMaxCarga();
            if(minutosDeDiferencia<=30 && (capacidadCarga<1000) || (minutosDeDiferencia<=45 && capacidadCarga>1000)){
                if(vuelo.getEstado()==EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR){
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE); 
                }
            }
        }
        else if( vuelo.getAvion() instanceof AvionPasajeros){
            AvionPasajeros avion = (AvionPasajeros)vuelo.getAvion();
            int numeroPasajeros= avion.getCapacidadMaxPasajeros();
            if((minutosDeDiferencia<=30 && numeroPasajeros<100) || (minutosDeDiferencia<=45 && numeroPasajeros>100)){
                if(vuelo.getEstado()==EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR){
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE); 
                }
            }
        }
    }

    public void cambiarEstadoFinEmbarque(Vuelo vuelo){
        
        
    }

    public void cambiarEstadoInicioDesembarque(Vuelo vuelo){
        
    }

    public void cambiarEstadoFinDesembarque(Vuelo vuelo){
        
    }

}
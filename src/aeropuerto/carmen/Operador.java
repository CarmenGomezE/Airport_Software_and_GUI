package src.aeropuerto.carmen;
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
    public Operador(List<Notificacion> notificaciones, List<VueloProgramado> vuelosPorgramados, Aerolinea aerolinea, EstadisticasOperador estadisticas){
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
        if(vuelo.getEstado()==EstadoVuelo.INICIO_DEL_EMBARQUE){
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_EMBARQUE);
        }
    }

    public void cambiarEstadoInicioDesembarque(Vuelo vuelo){
        if(vuelo.getEstado()==EstadoVuelo.ATERRIZAJE){
            vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_DESEMBARQUE);
        }
    }

    public void cambiarEstadoFinDesembarque(Vuelo vuelo){
        if(vuelo.getEstado()==EstadoVuelo.INICIO_DEL_DESEMBARQUE){
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_DESEMBARQUE);
        }
    }

    public void darDeAltaAvionPasajeros(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
                                        double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
                                        int capacidadMaxPasajeros){
        AvionPasajeros avion= new AvionPasajeros(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, 
                                                 fechaUltimaRevision, matricula, capacidadMaxPasajeros);
    }

    public void darDeAltaAvionMercancías(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo, 
                                        double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula, 
                                        double capacidadMaxCarga, boolean mercaPeligrosa, boolean controlTemperatura){
        AvionMercancias avion= new AvionMercancias(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, fechaUltimaRevision, 
                                                   matricula, capacidadMaxCarga, mercaPeligrosa, controlTemperatura);
    }

    public String verEstadisticas(){

    }

    public void recibirNotificacionOpcionesReprogramacion(){

    }

    public void recibirNotificacionFacturaEmitida(Factura factura){
        System.out.println("Operador notificado: Nueva factura generada para " + factura.getAerolinea().getNombre() + " por un total de " + factura.getPrecioTotal() + "€.");
    }
    }

    public void pagarFactura(Factura factura, String numeroDeTarjeta){
        try {
            TeleChargeAndPaySystem.isValidCardNumber(numeroDeTarjeta);
            TeleChargeAndPaySystem.charge(numeroDeTarjeta,factura.getInvoiceIdentifier(), factura.getPrice());
            factura.marcarComoPagada();

        } catch (InvalidCardNumberException e) {
            System.err.println("Error: Número de tarjeta inválido.");
        } catch (FailedInternetConnectionException e) {
            System.err.println("Error: Conexión a Internet fallida.");
        } catch (OrderRejectedException e) {
            System.err.println("Error: El pago fue rechazado.");
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
        }
    }

    public void solicitarCompartirVuelo(){

    }

    public void aceptarSolicitud(){

    }

    public void rechazarSolicitud(){

    }
    
    public void verSolicitudes(){

    }

}
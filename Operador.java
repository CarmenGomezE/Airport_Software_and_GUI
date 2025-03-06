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

}
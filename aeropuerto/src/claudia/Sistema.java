package aeropuerto;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Sistema representa el sistema del aeropuerto
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class Sistema {
    private List<String> dnis;
    private List<String> contrasena;
    private List<Usuario> usuarios; 

    public Sistema() {
        this.dni = new ArrayList<>();
        this.dni = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    private void validarUsuario(String dni, String contraseña) {

    }

    private boolean validarDni(String dni) {

        
       

    }

    private String obtenerContrasena(String dni) {

    }

    private String validarContrasena(String contrasena) {

    }

    private boolean esOperador(String dni) {

    }

    private boolean esControlador(String dni) {

    }

    private boolean tieneContrasenaEstablecida (String dni) {

    }

    private void establecerContrasena(String dni) {

    }

    private boolean validarTerminalVuelo(Terminal terminal) {

    }

    private boolean validarCondicionesVuelo(Avion avion) {

    }

    private void asignarContrasena(String dni, String contrasena) {

    }

    private boolean verificarVueloMenosHora(Avion avion) {

    }

    private boolean validarModoEmbarque(TipoElemento modo) {

    }

    private boolean validarPuertaEmbarque(PuertaEmbarque puerta) {

    }

    public void actualizarEstadoVueloAutomatico (Vuelo v) {

    }

    private boolean validarCondicionesEmbarque(Vuelo v) {

    }

    private List<Vuelo> ordenarColaVuelos() {

    }

    private boolean validarPagoFactura(Factura f) {

    }

    private boolean validarMercanciasSinFinger(VueloProgramado v, TipoModoEmbarque modo)  {

    }

    private boolean validarSolicitud (Vuelo v, Aerolinea aerolinea, double porcentaje) {

    }

    



    

}
package aeropuerto.src.claudia;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Sistema representa el sistema del aeropuerto
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class Sistema {
   
    private List<Usuario> usuarios; 
    private List<Vuelo> vuelos;
    private Map<String, String> credenciales;

    public Sistema() {
        this.usuarios = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.credenciales = new HashMap<>();
    }

    private Usuario buscarUsuarioPorDni (String dni) {

         for (Usuario usuario: usuarios) {
            if (usuario instanceof UsuarioConDni && ((UsuarioConDni)usuario).getDni().equals(dni)) {
                return usuario;
            } 
        }

        throw new UsuarioNoEncontradoException("El usuario con DNI " + dni + "no existe");
    }

    private String obtenerContrasena(String dni) {

        Usuario usuario;

        usuario = buscarUsuarioPorDni(dni);
        return usuario.getContrasena();
        
    }

    private boolean esOperador(String dni) {

        Usuario usuario;

        usuario = buscarUsuarioPorDni(dni);
        if (usuario instanceof Operador && ((UsuarioConDni)usuario).getDni().equals(dni)) {
            return true;
        }
        
        return false;
    }

    private boolean esControlador(String dni) {

        Usuario usuario;

        usuario = buscarUsuarioPorDni(dni);
        if (usuario instanceof ControladorAereo && ((UsuarioConDni)usuario).getDni().equals(dni)) {
            return true;
        }
        
        return false;
    }

    private boolean tieneContrasenaEstablecida (String dni) {

        String contrasena;
        Usuario usuario;

        usuario = buscarUsuarioPorDni(dni);
        contrasena = usuario.getContrasena();
        if (contrasena == null || contrasena.isEmpty()) {
            return true;
        }
        
        return false; 
    }

    /**No entiendo cual es la diferencia con asignar contraseña*/
    private void establecerContrasena(String dni, String contrasena) {


    }

    private void asignarContrasena(String dni, String contrasena) {

        Usuario usuario;
        
        usuario = buscarUsuarioPorDni(dni);
        
        if (contrasena == null || contrasena.isEmpty()) {
            throw new ContrasenaInvalidaException("La contraseña no es válida.");
        }

        ((UsuarioConDni)usuario).setContrasena(contrasena);
        System.out.println("Contraseña asignada correctamente");

    }

    public void actualizarEstadoVueloAutomatico (Vuelo v) {

        LocalDateTime ahora = LocalDateTime.now()

        if (v.getTipoVuelo() == TipoVuelo.LLEGADA) {
            if (ahora.isBefore(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.A_TIEMPO);
            }

            else if (v.getEstado() != EstadoVuelo.ATERRIZAJE && ahora.isAfter(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.RETRASADO);

            }

        }

        else if (v.getTipoVuelo() == TipoVuelo.SALIDA) {
            long minutosRestantes = Duration.between(ahora, v.getHora()).toMinutes;

            if (ahora.isAfter(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.VUELO);
            }

            else if ((v.getEstado() == EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE || v.getEstado() == EstadoVuelo.ESPERANDO_DESPEGUE) && minutosRestantes > 45) {
                v.cambiarEstado(EstadoVuelo.RETRASADO);
            }
        }

        System.out.println("Estado de vuelo actualizado: " + v.getEstado());
        
    }


    private void validarUsuario(String dni, String contraseña) {

        if (!credenciales.containsKey(dni)) {
            throw new UsuarioNoEncontradoException("El usuario con DNI" + dni + "no existe");
        }

        if (!credenciales.get(dni).equals(contrasena)) {
            throw new ContrasenaInvalidaException("Contraseña incorrecta");
        }

        System.out.println("Usuario validado correctamente");
    }

    private boolean validarDni(String dni) {

        return credenciales.containsKey(dni);
    
    }

    private boolean validarContrasena(String dni, String contrasena) {

        if (!credenciales.containsKey(dni)) {
            throw new UsuarioNoEncontradoException("El usuario con DNI" + dni + "no existe");
        }

        return credenciales.get(dni).equals(contrasena);
    }

    /**Devuelve un numero positivo si el segundo vuelo tiene mas prioridad para luego cambiarle la posicion */
    private int compararVuelos(Vuelo v1, Vuelo v2) {

        Boolean esPasajero1, esPasajero2;
        int pasajeros1 = 0, pasajeros2 = 0;

        if (v1.getRetraso() != v2.getRetraso()) {
            return v2.getRetraso() - v1.getRetraso();
        }

        if (v1.getTiempoEspera() != v2.getTiempoEspera()) {
            return v2.getTiempoEspera() - v1.getTiempoEspera();
        }

        esPasajero1 = v1.getAvion() instanceof AvionPasajeros;
        esPasajero2 = v2.getAvion() instanceof AvionPasajeros;

        if (esPasajero1 != esPasajero2) {
            if (esPasajero1 == true) {
                return -1;
            }

            else {
                return 1;
            } 
        }

        if (esPasajero1 == true) {
            pasajeros1 = ((AvionPasajeros)v1.getAvion()).getCapacidadMaxPasajeros();
        }

        if (esPasajero2 == true) {
            pasajeros2 = ((AvionPasajeros)v2.getAvion()).getCapacidadMaxPasajeros();
        }

        return pasajeros2 - pasajeros1;
    }

    private List<Vuelo> ordenarColaVuelos() {

        List<Vuelo> vuelosEnEspera = new ArrayList<>();
        int n, i, j;
        Vuelo v1, v2;

        for (Vuelo vuelo: vuelos) {
            if (vuelo.getEstado() == EstadoVuelo.ESPERANDO_DESPEGUE) {
                vuelosEnEspera.add(vuelo);
            }
        }

        n = vuelosEnEspera.size();
        for (i=0; i<n; i++) {
            for (j=0; j<n-i-1; j++) {
                v1 = vuelosEnEspera.get(j);
                v2 = vuelosEnEspera.get(j+1);

                if (compararVuelos(v1, v2) > 0) {
                    vuelosEnEspera.set(j, v2);
                    vuelosEnEspera.set(j+1, v1);
                }
            }
        }

        return vuelosEnEspera;

    }

}
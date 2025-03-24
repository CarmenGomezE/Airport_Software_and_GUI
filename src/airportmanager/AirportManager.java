package src.airportmanager;

import java.io.*;
import java.util.*;

import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.AvionPasajeros;

import java.security.*;
import java.time.*;

/**
 * La clase Sistema representa el sistema del aeropuerto
 * 
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class AirportManager {

    private List<Usuario> usuarios;
    private Gestor gestor;
    private Map<String, String> credenciales;
    private AeropuertoPropio aeropuerto;
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/";
    private static SecureRandom random = new SecureRandom();
    private static final String ARCHIVO_ESTADO = "estado_airport_manager.dat";

    public AirportManager() {
        this.usuarios = new ArrayList<>();
        this.aeropuerto = null;
        this.gestor=null;
        this.credenciales = new HashMap<>();
        this.credenciales.put("Gestor", gestor.getContrasena());
    }
    
    public void iniciarAplicacion() {
    	if (aeropuerto == null) {
            System.out.println("Configurando nuevo sistema.");

            try {
                aeropuerto = new AeropuertoPropio("Aeropuerto Adolfo Suarez Madrid-Barajas", "MAD", "Madrid", "España", 10.5, ZoneOffset.ofHours(2), 2, DireccionRelativaCiudad.ESTE);
                aeropuerto.agregarTemporada(new Temporada("2025-06-01", "10:00", "20:00", "2025-09-30"));
                aeropuerto.agregarTemporada(new Temporada("2025-10-01", "08:00", "24:00", "2025-05-30"));
            } catch (TemporadasSolapadasException e) {
                System.out.println("Error al configurar el aeropuerto: " + e.getMessage());
            }

            try {
                gestor = new Gestor("Pepito Pérez", this.generarContrasena(), aeropuerto, this);
            } catch (Exception e) {
                System.out.println("Error al crear el gestor: " + e.getMessage());
            }
            if (gestor != null) {
                this.usuarios.add(gestor);
                aeropuerto.addUsuario(gestor);
                credenciales.put("Gestor", gestor.getContrasena());
            }
        }
    }

    /** Esto es para guardar y cargar de momento ni caso */
    public void guardarEstado(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
            System.out.println("Estado del sistema guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el estado: " + e.getMessage());
        }
    }

    public static AirportManager cargarEstado(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (AirportManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el estado: " + e.getMessage());
            return null;
        }
    }

    public String generarContrasena( ) {
        StringBuilder contrasena = new StringBuilder(12);
        
        for (int i = 0; i < 12; i++) {
            int indice = random.nextInt(CARACTERES.length());
            contrasena.append(CARACTERES.charAt(indice));
        }
        
        return contrasena.toString();
    }
    
    public void actualizarEstadosVuelosAutomaticamente() {
    	for(Vuelo vuelo: aeropuerto.getVuelosAceptados()) {
    		actualizarEstadoVueloAutomatico(vuelo);
    	}
    }
    
    /** Llama sobre los vuelos del aeropuerto propio */
    public void actualizarEstadoVueloAutomatico(Vuelo v) {
        LocalDateTime ahora = LocalDateTime.now();

        if (v.getTipoVuelo() == TipoVuelo.LLEGADA) {
            if (ahora.toLocalTime().isBefore(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.A_TIEMPO);
            } else if (v.getEstado() != EstadoVuelo.ATERRIZAJE && ahora.toLocalTime().isAfter(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.RETRASADO);
            }
        } else if (v.getTipoVuelo() == TipoVuelo.SALIDA) {
            long minutosRestantes = Duration.between(ahora, v.getHora()).toMinutes();

            if (ahora.toLocalTime().isAfter(v.getHora())) {
                v.cambiarEstado(EstadoVuelo.VUELO);
            } else if ((v.getEstado() == EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE
                    || v.getEstado() == EstadoVuelo.ESPERANDO_DESPEGUE)
                    && minutosRestantes < 45) {
                v.cambiarEstado(EstadoVuelo.RETRASADO);
            }
        }

        System.out.println("Estado de vuelo actualizado: " + v.getEstado());
    }

    private Usuario buscarUsuarioPorDni(String dni) throws UsuarioNoEncontradoException {

        for (Usuario usuario : usuarios) {
            if (usuario instanceof UsuarioConDni && ((UsuarioConDni) usuario).getDni().equals(dni)) {
                return usuario;
            }
        }

        throw new UsuarioNoEncontradoException("El usuario con DNI " + dni + "no existe");
    }

    public void asignarContrasena(String dni, String contrasena)
            throws UsuarioNoEncontradoException, ContrasenaInvalidaException {

        Usuario usuario;

        usuario = buscarUsuarioPorDni(dni);

        if (contrasena == null || contrasena.isEmpty()) {
            throw new ContrasenaInvalidaException("La contraseña no es válida.");
        }

        ((UsuarioConDni) usuario).setContrasena(contrasena);
        System.out.println("Contraseña asignada correctamente");

    }

    private boolean validarDni(String dni) {

        return credenciales.containsKey(dni);

    }

    private boolean validarContrasena(String dni, String contrasena) throws UsuarioNoEncontradoException {
        return credenciales.get(dni).equals(contrasena);
    }


    /**
     * Método auxiliar para validar un usuario con dni.
     * 
     * @param dni        Dni del usuario con dni.
     * @param contrasena Contraseña del usuario con dni.
     * @throws UsuarioNoEncontradoException Si el usuario no existe.
     * @throws ContrasenaInvalidaException  Si el usuario existe pero la contraseña
     *                                      es incorrecta.
     */
    public void validarUsuario(String dni, String contrasena)
            throws UsuarioNoEncontradoException, ContrasenaInvalidaException {

        if (!validarDni(dni)) {
            throw new UsuarioNoEncontradoException("El usuario con DNI" + dni + "no existe");
        }

        if (!validarContrasena(dni, contrasena)) {
            throw new ContrasenaInvalidaException("Contraseña incorrecta");
        }
        System.out.println("Usuario validado correctamente");
    }

    /**
     * Método para añadir un credencial.
     * 
     * @param dni        Dni que se añade.
     * @param contrasena Contraseña que se añade.
     */
    public void addCredencial(String dni, String contrasena) {
        if (dni == null || contrasena == null) {
            throw new IllegalArgumentException("El dni y la contraseña no pueden ser null.");
        }

        credenciales.put(dni, contrasena);
    }
    

    private List<Vuelo> ordenarColaVuelos() {

        List<Vuelo> vuelosEnEspera = new ArrayList<>();
        int n, i, j;
        Vuelo v1, v2;

        for (Vuelo vuelo : vuelos) {
            if (vuelo.getEstado() == EstadoVuelo.ESPERANDO_DESPEGUE) {
                vuelosEnEspera.add(vuelo);
            }
        }

        n = vuelosEnEspera.size();
        for (i = 0; i < n; i++) {
            for (j = 0; j < n - i - 1; j++) {
                v1 = vuelosEnEspera.get(j);
                v2 = vuelosEnEspera.get(j + 1);

                if (compararVuelos(v1, v2) > 0) {
                    vuelosEnEspera.set(j, v2);
                    vuelosEnEspera.set(j + 1, v1);
                }
            }
        }

        return vuelosEnEspera;

    }
    
    /**
     * Devuelve un numero positivo si el segundo vuelo tiene mas prioridad para
     * luego cambiarle la posicion
     */
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
            pasajeros1 = ((AvionPasajeros) v1.getAvion()).getCapacidadMaxPasajeros();
        }

        if (esPasajero2 == true) {
            pasajeros2 = ((AvionPasajeros) v2.getAvion()).getCapacidadMaxPasajeros();
        }

        return pasajeros2 - pasajeros1;
    }



}
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
public class AirportManager implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
	private List<Usuario> usuarios;
    private Gestor gestor;
    private Map<String, String> credenciales;
    private AeropuertoPropio aeropuerto;
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/";
    private static SecureRandom random = new SecureRandom();

    public AirportManager() {
    	this.usuarios = new ArrayList<>();
        this.aeropuerto = null;
        this.gestor=null;
        this.credenciales = new HashMap<>();
    }
    
    public void iniciarAplicacion() {
    	if (aeropuerto == null) {
            System.out.println("Configurando nuevo sistema.");

            try {
                aeropuerto = new AeropuertoPropio("Aeropuerto Adolfo Suarez Madrid-Barajas", "MAD", "Madrid", "España", 10.5, ZoneOffset.ofHours(0), 2, DireccionRelativaCiudad.ESTE);
                aeropuerto.agregarTemporada(new Temporada("01/06", "10:00", "20:00", "30/09"));
                aeropuerto.agregarTemporada(new Temporada("01/10", "08:00", "24:00", "30/05"));
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
                
                if (gestor.getAeropuerto() == aeropuerto) {
                    System.out.println("El gestor está correctamente asignado al aeropuerto.");
                } else {
                    System.out.println("Error: El gestor no está correctamente asignado al aeropuerto.");
                }
            }
        }
    }
    
    public void guardarEstado(String archivo) {
        try {
            File file = new File(archivo);
            System.out.println("\nIntentando guardar en: "+file.getAbsolutePath());

            file.getParentFile().mkdirs();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(this);
                System.out.println("Estado del sistema guardado correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el estado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static AirportManager cargarEstado(String archivo) {
    	if (archivo == null || !new File(archivo).exists()) {
            System.out.println("El archivo no existe o es null. Creando un nuevo archivo...");
            AirportManager nuevoEstado = new AirportManager(); 
            nuevoEstado.guardarEstado(archivo); 
            return nuevoEstado;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (AirportManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el estado. Creando un nuevo archivo...");
            AirportManager nuevoEstado = new AirportManager(); 
            nuevoEstado.guardarEstado(archivo); 
            return nuevoEstado;
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
    
  /** Llama sobre los vuelos del aeropuerto propio */
    public void actualizarEstadosVuelosAutomaticamente() {
        List<Vuelo> vuelosOrdenados = ordenarColaVuelos(); 
    
        LocalTime horaSalidaBase = LocalTime.now(); 
        for (int i = 0; i < vuelosOrdenados.size(); i++) {
            Vuelo vuelo = vuelosOrdenados.get(i);
            
            LocalTime nuevaHoraSalida = horaSalidaBase.plusMinutes(i * 5);
            vuelo.setHora(nuevaHoraSalida);

            actualizarEstadoVueloAutomatico(vuelo);
        }
    }    

 /**
     * Método que actualiza los estados automáticos de los vuelos (A_TIEMPO, RETRASADO, VUELO) teniendo en cuenta la cola de los vuelos en esperando despegue.
     * @param v                             Vuelo al que actualizar sus estados.
     */
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


     /**
     * Método que asigna una contraseña a un usuario a partir de su dni.
     * @param dni                           Dni del usuario.
     * @param contrasena                    Contraseña del usuario.
     * 
     */
    public void asignarContrasena(String dni, String contrasena)
            throws UsuarioNoEncontradoException, ContrasenaInvalidaException {

        credenciales.put(dni, contrasena);
        System.out.println("Contraseña asignada correctamente.");
    }

    /**
     * Método que valida el dni de un usuario.
     * @param dni                           Dni del usuario.
     * @return true si el dni está registrado en el sistema o false si no.
     */
    private boolean validarDni(String dni) {

        return credenciales.containsKey(dni);

    }

    /**
     * Método que valida la contraseña de un usuario.
     * @param dni                           Dni del usuario.
     * @param contraseña                    Contraseña del usuario.
     * @return true si la contraseña está registrada en el sistema o false si no.
     */
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
            throw new UsuarioNoEncontradoException("El usuario con DNI" + dni + " no existe");
        }

        if (!validarContrasena(dni, contrasena)) {
            throw new ContrasenaInvalidaException("Contraseña incorrecta.");
        }
        System.out.println("Usuario validado correctamente.");
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
    
     /**
     * Método que ordena la cola de vuelos que están esperando despegue.
     * @return la cola de vuelos ordenada.
     */
    private List<Vuelo> ordenarColaVuelos() {

        List<Vuelo> vuelosEnEspera = new ArrayList<>();
        int n, i, j;
        Vuelo v1, v2;

        for (Vuelo vuelo : aeropuerto.getVuelosAceptados()) {
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
     * Método que compara dos vuelos según su prioridad.
     * @param v1                            Primer vuelo
     * @param v2                            Segundo vuelo
     * @return un número positivo si el segundo vuelo tiene más prioridad o un número negativo si el primer vuelo tiene más prioridad.
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

	public AeropuertoPropio getAeropuerto() {
		return this.aeropuerto;
	}
	
	public Gestor getGestor() {
		return this.gestor;
	}



}

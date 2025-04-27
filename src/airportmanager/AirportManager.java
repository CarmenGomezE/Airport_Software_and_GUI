package src.airportmanager;

import java.io.*;

import java.util.*;

import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
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
	/** Lista de usuarios */
	private List<Usuario> usuarios;
	/** Gestor */
    private Gestor gestor;
    /** Credenciales de los usuarios*/
    private Map<String, String> credenciales;
    /** Aeropuerto*/
    private AeropuertoPropio aeropuerto;
    /** Caracteres para generar contraseñas */
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/";
    /** Para generar contraseñas */
    private static SecureRandom random = new SecureRandom();
    private transient Map<Vuelo, Timer> temporizadores = new HashMap<>();

    /**
     * Constructor de AirportManager.
     */
    public AirportManager() {
    	this.usuarios = new ArrayList<>();
        this.aeropuerto = null;
        this.gestor=null;
        this.credenciales = new HashMap<>();
    }
    
    /**
     * Método que inicia la aplicación.
     */
    public void iniciarAplicacion() {
    	if (aeropuerto == null) {
            System.out.println("Configurando nuevo sistema.");

            try {
                aeropuerto = new AeropuertoPropio("Aeropuerto Adolfo Suarez Madrid-Barajas", "MAD", "Madrid", "España", 10.5, ZoneOffset.ofHours(0), 2, DireccionRelativaCiudad.ESTE);
                aeropuerto.agregarTemporada(new Temporada("01/07", "10:00", "20:00", "30/09"));
                aeropuerto.agregarTemporada(new Temporada("01/10", "08:00", "24:00", "31/12"));
            } catch (TemporadasSolapadasException e) {
                System.out.println("Error al configurar el aeropuerto: " + e.getMessage());
            }

            try {
                gestor = new Gestor("Pepito Pérez", "SoyGestor", aeropuerto, this);
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
    
    /**
     * Método que guarda el estado al archivo
     * 
     * @param archivo	Archivo.
     */
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

    /**
     * Método que carga el estado al archivo
     * 
     * @param archivo Archivo.
     * @return la aplicación.
     */
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

    /**
     * Método que genera contraseñas.
     * 
     * @return contraseña nueva.
     */
    public String generarContrasena( ) {
        StringBuilder contrasena = new StringBuilder(12);
        
        for (int i = 0; i < 12; i++) {
            int indice = random.nextInt(CARACTERES.length());
            contrasena.append(CARACTERES.charAt(indice));
        }
        
        return contrasena.toString();
    }

    /**
     * Método que actualiza los estados automáticos de los vuelos (A_TIEMPO, RETRASADO, VUELO) teniendo en cuenta la cola de los vuelos en esperando despegue.
     * @param v                             Vuelo al que actualizar sus estados.
     */
    public void actualizarEstadoVueloAutomatico(Vuelo v) {
        LocalDateTime ahora = LocalDateTime.now();  
        LocalDateTime horaVuelo = LocalDateTime.of(LocalDate.now(), v.getHora()); 

        if (v.getTipoVuelo() == TipoVuelo.LLEGADA) {
            if (ahora.isBefore(horaVuelo)) {
                v.cambiarEstado(EstadoVuelo.A_TIEMPO);
            } else if (v.getEstado() != EstadoVuelo.ATERRIZAJE || v.getEstado() != EstadoVuelo.OPERATIVO || 
                       v.getEstado() != EstadoVuelo.AVION_A_HANGAR || v.getEstado() != EstadoVuelo.RETRASADO || 
                       v.getEstado() == EstadoVuelo.A_TIEMPO) {
                double minutosRetraso = (double) Duration.between(horaVuelo, ahora).toMinutes();
                v.setRetraso(minutosRetraso);
                v.cambiarEstado(EstadoVuelo.RETRASADO);

                for (ControladorAereo controlador: v.getTerminal().getControladores()) {
                    controlador.recibirNotificacionCambioEstado(v);
                } 
                for (Operador o : v.getOperadores()) {
                    o.recibirNotificacionCambioEstado(v);
                }
                gestor.recibirNotificacion(new Notificacion("El vuelo " + v.getCodigo() + " ha cambiado su estado a " + v.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
            }
        } else if (v.getTipoVuelo() == TipoVuelo.SALIDA) {
            long minutosRestantes = Duration.between(ahora, horaVuelo).toMinutes(); 
            if ((v.getEstado() == EstadoVuelo.OPERATIVO || v.getEstado() == EstadoVuelo.ATERRIZAJE || 
                 v.getEstado() == EstadoVuelo.ACEPTADO || v.getEstado() == EstadoVuelo.A_TIEMPO || 
                 v.getEstado() == EstadoVuelo.RETRASADO) && minutosRestantes > 45) {
                v.setRetraso(0);
                v.cambiarEstado(EstadoVuelo.A_TIEMPO);
            } else {
                v.cambiarEstado(EstadoVuelo.RETRASADO);
                
                for (ControladorAereo controlador: v.getTerminal().getControladores()) {
                    controlador.recibirNotificacionCambioEstado(v);
                } 
                for (Operador o : v.getOperadores()) {
                    o.recibirNotificacionCambioEstado(v);
                }
                gestor.recibirNotificacion(new Notificacion("El vuelo " + v.getCodigo() + " ha cambiado su estado a " + v.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
            }
        }

        if (ordenarColaVuelos() != null) {
            Vuelo vueloVuela = ordenarColaVuelos();
            vueloVuela.cambiarEstado(EstadoVuelo.VUELO);
            vueloVuela.getControlador().liberarPista(vueloVuela);
            
            for (ControladorAereo controlador: vueloVuela.getTerminal().getControladores()) {
                controlador.recibirNotificacionCambioEstado(vueloVuela);
            }
            for (Operador o : vueloVuela.getOperadores()) {
                o.recibirNotificacionCambioEstado(vueloVuela);
            }
            gestor.recibirNotificacion(new Notificacion("El vuelo " + vueloVuela.getCodigo() + " ha cambiado su estado a " + vueloVuela.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
        }
    }
	
	/**
     * Método que actualiza los estados automáticos cada 5 minutos para un vuelo específico.
     * @param v Vuelo al que actualizar sus estados.
     */
    public void iniciarActualizacionCada5Min(Vuelo v) {
        Timer timer = new Timer();
        temporizadores.put(v, timer);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime ahora = LocalDateTime.now();
                LocalDateTime vueloFechaHora = LocalDateTime.of(v.getFecha(), v.getHora());

                boolean vueloYaSalio = ahora.isAfter(vueloFechaHora);
                boolean estadoFinal = v.getEstado() == EstadoVuelo.VUELO || 
                                       v.getEstado() == EstadoVuelo.ATERRIZAJE || 
                                       v.getEstado() == EstadoVuelo.AVION_A_HANGAR;

                actualizarEstadoVueloAutomatico(v);

                if (vueloYaSalio && estadoFinal) {
                    timer.cancel();
                    temporizadores.remove(v);
                    System.out.println("Se cancela el timer del vuelo " + v.getCodigo());
                }
            }
        };
        timer.scheduleAtFixedRate(tarea, 0, 5 * 60 * 1000);
        System.out.println("Temporizador iniciado para el vuelo " + v.getCodigo());
    }
    
    public void activarTimersParaVuelos(List<Vuelo> vuelos) {
        for (Vuelo v : vuelos) {
            if (debeTenerTimer(v)) {
                iniciarActualizacionCada5Min(v);
                System.out.println("Timer activado para vuelo " + v.getCodigo());
            } else {
                System.out.println("No se activa timer para vuelo " + v.getCodigo());
            }
        }
    }

    private boolean debeTenerTimer(Vuelo v) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaHoraVuelo = LocalDateTime.of(v.getFecha(), v.getHora());

        if (v.getTipoVuelo() == TipoVuelo.SALIDA) {
            long minutosRestantes = Duration.between(ahora, fechaHoraVuelo).toMinutes();
            boolean cercaDeSalir = minutosRestantes <= 45 && minutosRestantes >= 0;
            boolean noHaVolado = v.getEstado() != EstadoVuelo.VUELO;

            return cercaDeSalir && noHaVolado;
        } else if (v.getTipoVuelo() == TipoVuelo.LLEGADA) {
            boolean esHoraLlegada = !ahora.isBefore(fechaHoraVuelo);
            boolean noHaAterrizado = v.getEstado() != EstadoVuelo.ATERRIZAJE && v.getEstado() != EstadoVuelo.AVION_A_HANGAR;

            return esHoraLlegada && noHaAterrizado;
        }
        return false;
    }

     /**
     * Método que asigna una contraseña a un usuario a partir de su dni.
     * @param dni                           Dni del usuario.
     * @param contrasena                    Contraseña del usuario.
     * @throws UsuarioNoEncontradoException no existe usuario.
     * @throws ContrasenaInvalidaException contraseña inválida
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
    public boolean validarDni(String dni) {
        return credenciales.containsKey(dni);
    }

    /**
     * Método que valida la contraseña de un usuario.
     * @param dni                           Dni del usuario.
     * @param contrasena                    Contraseña del usuario.
     * @return true si la contraseña está registrada en el sistema o false si no.
     * @throws UsuarioNoEncontradoException no existe usuario.
     */
    public boolean validarContrasena(String dni, String contrasena) throws UsuarioNoEncontradoException {
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
     * @return el vuelo que sale de la cola.
     */
    public Vuelo ordenarColaVuelos() {

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
        if(!vuelosEnEspera.isEmpty()) {
        	Vuelo vueloReturn= vuelosEnEspera.get(0);
        	vuelosEnEspera.remove(0);
        	return vueloReturn;
        } 
        return null;
    }
    
    /**
     * Método que compara dos vuelos según su prioridad.
     * @param v1     Primer vuelo
     * @param v2     Segundo vuelo
     * @return un número positivo si el segundo vuelo tiene más prioridad o un número negativo si el primer vuelo tiene más prioridad.
     */
    public double compararVuelos(Vuelo v1, Vuelo v2) {

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
            if (esPasajero1) {
                return -1;
            } else {
                return 1; 
            }
        }

        if (esPasajero1) {
            pasajeros1 = ((AvionPasajeros) v1.getAvion()).getCapacidadMaxPasajeros();
        }

        if (esPasajero2) { 
            pasajeros2 = ((AvionPasajeros) v2.getAvion()).getCapacidadMaxPasajeros();
        }

        return pasajeros2 - pasajeros1;
    }

    /**
    * Método devuelve el aeropuerto.
    * @return Aeropuerto.
    */
	public AeropuertoPropio getAeropuerto() {
		return this.aeropuerto;
	}
	
	/**
    * Método devuelve el gestor.
    * @return Getsor.
    */
	public Gestor getGestor() {
		return this.gestor;
	}
	/**
    * Método devuelve los usuarios.
    * @return Lista usuarios.
    */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	/**
    * Método devuelve los credenciales.
    * @return Mapa credenciales.
    */
	public Map<String, String> getCredenciales() {
		return credenciales;
	}

	/**
    * Método devuelve un vuleo dado su código.
    * 
    * @param codigo Código del vuelo.
    * @return Vuelo al que pertenece el código.
    */
	public Vuelo buscarVueloPorCodigo(String codigo) {
		Vuelo vueloBuscado = null;
		for(Vuelo v:getAeropuerto().getVuelosAceptados()) {
			if(v.getCodigo().equals(codigo)) {
				vueloBuscado=v;
				break;
			}
		}
		return vueloBuscado;
	}

}

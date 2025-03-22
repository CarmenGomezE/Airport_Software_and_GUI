package aeropuerto.src.maria;
import aeropuerto.src.claudia.*;
import java.time.*;
import java.util.*;

/**
 * La clase AeropuertoPropio representa el aeropuerto que se gestiona.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class AeropuertoPropio extends Aeropuerto {
    /** Fingers del aeropuerto propio*/
    private List<Finger> fingers;
    /** Hangares del aeropuerto propio*/
    private List<Hangar> hangares;
    /** Zonas de aparcamiento del aeropuerto propio*/
    private List<ZonaAparcamiento> zonasAparcamiento;
    /** Terminales del aeropuerto propio*/
    private List<Terminal> terminales;
    /** Usuarios que trabajan en el aeropuerto propio*/
    private List<Usuario> usuarios;
    /** Pistas del aeropuerto propio*/
    private List<Pista> pistas;
    /** Aerolineas del aeropuerto propio*/
    private List<Aerolinea> aerolineas;
    private List<Vuelo> vuelosAceptados;
    private List<Aeropuerto> aeropuertosDestino;

    /**
     * Constructor de la clase AeropuertoPropio.
     * 
     * @param nombre                Nombre del aeropuerto propio.
     * @param codigo                Código del aeropuerto propio.
     * @param ciudad                Ciudad más cercana al aeropuerto propio.
     * @param pais                  País del aeropuerto propio.
     * @param distanciaCiudad       Distancia hasta la ciudad más cercana al aeropuerto propio.
     * @param diferenciaHoraria     Diferencia horaria UTC del aeropuerto propio.
     * @param numTemporadas         Número de temporadas del aeropuerto propio.
     * @param direccionCiudad       Dirección relativa a la ciudad más cercana del aeropuerto propio.
     */
    public AeropuertoPropio(String nombre, String codigo, String ciudad, String pais, double distanciaCiudad, ZoneOffset diferenciaHoraria, int numTemporadas, DireccionRelativaCiudad direccionCiudad) {
        super(nombre, codigo, ciudad, pais, distanciaCiudad, diferenciaHoraria, numTemporadas, direccionCiudad);
        this.vuelosAceptados=new ArrayList<>();
        this.fingers=new ArrayList<>();
        this.hangares=new ArrayList<>();
        this.zonasAparcamiento=new ArrayList<>();
        this.terminales=new ArrayList<>();
	this.usuarios= new ArrayList<>();
        this.pistas=new ArrayList<>();
	this.aerolineas= new ArrayList<>();
	this.vuelosAceptados= new ArrayList<>();
        this.aeropuertosDestino= new ArrayList<>();
    }

    /**
     * Método para obtener los fingers del aeropuerto propio.
     * 
     * @return Fingers del aeropuerto propio.
     */
    public List<Finger> getFingers() {
        return fingers;
    }

    /**
     * Método para obtener los hangares del aeropuerto propio.
     * 
     * @return Hangares del aeropuerto propio.
     */
    public List<Hangar> getHangares() {
        return hangares;
    }

    /**
     * Método para obtener las zonas de aparcamiento del aeropuerto propio.
     * 
     * @return Zonas de aparcamiento del aeropuerto propio.
     */
    public List<ZonaAparcamiento> getZonasAparcamiento() {
        return zonasAparcamiento;
    }

    /**
     * Método para obtener las terminales del aeropuerto propio.
     * 
     * @return Temrinales del aeropuerto propio.
     */
    public List<Terminal> getTerminales() {
        return terminales;
    }

    /**
     * Método para obtener los usuarios que trabajan en el aeropuerto propio.
     * 
     * @return Los usuarios que trabajan en el aeropuerto propio.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    /**
     * Método para obtener las pistas del aeropuerto propio.
     * 
     * @return Temrinales del aeropuerto propio.
     */
    public List<Pista> getPistas() {
        return pistas;
    }
    
    public List<Aeropuerto> getAeropuertosDestino() {
        return aeropuertosDestino;
    }

    /**
     * Método para obtener las puertas de embarque del aeropuerto propio.
     * 
     * @return Puertas de embarque del aeropuerto propio.
     */
    public List<PuertaEmbarque> getPuertasEmbarque() {
        List<PuertaEmbarque> puertasEmbarque = new ArrayList<>();

        for (Terminal t: terminales) {
            puertasEmbarque.addAll(t.getPuertasEmbarque());
        }

        return puertasEmbarque;
    }

    /**
     * Método para añadir una terminal al aeropuerto propio.
     * 
     * @param terminal La terminal que se añade.
     */
    public void addTerminal(Terminal terminal) {
        if (terminal == null) {
            throw new IllegalArgumentException("La terminal no puede ser null");
        } 

        terminales.add(terminal);
    }

    /**
     * Método para añadir un finger al aeropuerto propio.
     * 
     * @param finger El finger que se añade.
     */
    public void addFinger(Finger finger) {
        if (finger == null) {
            throw new IllegalArgumentException("El finger no puede ser null");
        } 

        fingers.add(finger);
    }

    /**
     * Método para añadir una zona de aparcamiento al aeropuerto propio.
     * 
     * @param zonaAparcamiento La zona de aparcamiento que se añade.
     */
    public void addZonaAparcamiento(ZonaAparcamiento zonaAparcamiento) {
        if (zonaAparcamiento == null) {
            throw new IllegalArgumentException("La zona de aparcamiento no puede ser null");
        } 

        zonasAparcamiento.add(zonaAparcamiento);
    }

    /**
     * Método para añadir un hangar al aeropuerto propio.
     * 
     * @param hangar El hangar que se añade.
     */
    public void addHangar(Hangar hangar) {
        if (hangar == null) {
            throw new IllegalArgumentException("El hangar no puede ser null");
        } 

        hangares.add(hangar);
    }

    /**
     * Método para añadir un usuario al aeropuerto propio.
     * 
     * @param usuario El usuario que se añade.
     */
    public void addUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        } 

        usuarios.add(usuario);
    }
    
    public void addPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no puede ser null");
        } 

        pistas.add(pista);
    }

    /**
     * Método para obtener el número de horas de operación del aeropuerto propio.
     * 
     * @return Número de horas de operación del aeropuerto propio.
     */
    public double getHorasOperacion() {
        LocalDate hoy = LocalDate.now();

        for (Temporada t: temporadas) {
            if (t.estaEnTemporada(hoy)) {
                LocalTime apertura = t.getHorarioApertura();
                LocalTime cierre = t.getHorarioCierre();

                return calcularHorasEntre(apertura, cierre);
            }
        }

        return 0;
    }

    /** 
     * Método para calcular la diferencia entre dos horas.
     * 
     * @param hora1     Hora que va antes.
     * @param hora2     Hora que va después.
     * 
     * @return Diferencia entre la hora1 y la hora2
    */
    public double calcularHorasEntre(LocalTime hora1, LocalTime hora2) {
        return (double) (hora2.toSecondOfDay() - hora1.toSecondOfDay()) / 3600;
    }

	public List<Vuelo> getVuelosAceptados() {
		return this.vuelosAceptados;
	}
	
	public void agregarVueloAceptado(Vuelo vuelo) {
		if (vuelo == null) {
            throw new IllegalArgumentException("La pista no puede ser null");
        } 
		this.vuelosAceptados.add(vuelo);
	}

	public void agregarAeropuertoDestino(AeropuertoDestino aeropuertoDestino) {
		this.aeropuertosDestino.add(aeropuertoDestino);
		
	}


}

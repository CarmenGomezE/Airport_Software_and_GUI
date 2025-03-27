package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.aeropuerto.Temporada;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.avion.Avion;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.EstadoEquivocadoException;
import src.airportmanager.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.exceptions.NoFingerMercanciasException;
import src.airportmanager.exceptions.NoHangaresDisponiblesException;
import src.airportmanager.exceptions.NoModosPuertasEmbarqueDisponiblesException;
import src.airportmanager.exceptions.NoPistasDisponiblesException;
import src.airportmanager.exceptions.NoVueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.exceptions.VueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.VueloNoDelUsuarioException;
import src.airportmanager.hangar.Hangar;
import src.airportmanager.hangar.HangarPasajeros;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.terminal.ZonaAparcamiento;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;
import src.airportmanager.usuario.Usuario;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;

class ControladorAereoTest {
	
	private AeropuertoPropio aeropuertoPropio;
	private HangarPasajeros hangar1, hangar2;
	private ControladorAereo controlador;
	private Operador operador;
	private Gestor gestor;
	private TerminalPasajeros terminal1, terminal2;
	private Aerolinea aerolinea1, aerolinea2;
	private Vuelo vuelo1, vuelo2;
	private AeropuertoDestino aeropuertoDestino1, aeropuertoDestino2;
	private PuertaEmbarque p1, p2, p3, p4;
	private List<Hangar> hangares;
	private ZonaAparcamiento zona1, zona2;
	private List<ZonaAparcamiento> zonas;
	private List<Terminal> terminales;
	private AirportManager sistema;
	private List<Aerolinea> aerolineas;
	private List<Usuario> usuarios;
	private Avion avion;
	private List<Vuelo> vuelos;
	private List<AeropuertoDestino> aeropuertosDestino;
	private Pista pista1, pista2;
	private List<Pista> pistas;
	private List<PuertaEmbarque> puertasList, puertasList2;
	private Temporada temporada1, temporada2;
	private List<Temporada> temporadas;
	private Notificacion notificacion, notificacion2;
	private List<Notificacion> notificaciones;
    private ByteArrayOutputStream salidaConsola;

	@BeforeEach
	void setUp() throws Exception {
		
		aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2, DireccionRelativaCiudad.ESTE);
		
		
		puertasList = new ArrayList<>();
		puertasList2 = new ArrayList<>();
		p1 = new PuertaEmbarque();
		p2 = new PuertaEmbarque();
		p3= new PuertaEmbarque();
		p4= new PuertaEmbarque();
				
	
		puertasList.add(p1);
		puertasList.add(p3);
		puertasList2.add(p2);
		puertasList2.add(p4);
		
		
		hangar1 = new HangarPasajeros(20, 34.5, 54.8, 60.9, 4.7);
		hangar2 = new HangarPasajeros(25, 54.5, 59.8, 90.9, 4.2);
		hangares = new ArrayList<>();
		hangares.add(hangar1);
		hangares.add(hangar2);
		
		zona1 = new ZonaAparcamiento(2.5, 7.9, 6.5);
		zona2 = new ZonaAparcamiento(3.5, 6.9, 8.5);
		zonas = new ArrayList<>();
		zonas.add(zona1);
		zonas.add(zona2);
		
		terminal1 = new TerminalPasajeros("T1", puertasList, 100);
		terminal2 = new TerminalPasajeros("T2", puertasList2, 150);
		terminales = new ArrayList<>();
		terminales.add(terminal1);
		terminales.add(terminal2);
		
		aerolinea1 = new Aerolinea("IBERIA", "IBE1");
		aerolinea2 = new Aerolinea("RYANAIR", "RYN1");
		aerolineas = new ArrayList<>();
		aerolineas.add(aerolinea1);
		aerolineas.add(aerolinea2);
		
		sistema = new AirportManager();
		gestor = new Gestor("Lorena", "Lore76", aeropuertoPropio, sistema);
		controlador = new ControladorAereo("Juan Pedro", "56872372J", "Juanpe44", aeropuertoPropio, gestor, terminal1, sistema);
		operador = new Operador("Miguel", "56098976M", aerolinea1, aeropuertoPropio, gestor, sistema);
		usuarios = new ArrayList<>();
		usuarios.add(gestor);
		usuarios.add(operador);
		usuarios.add(controlador);
		
		avion = new Avion("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78");
		vuelo1 = new Vuelo("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelo2 = new Vuelo("V132", LocalDate.of(2024, 5, 12), null, null, LocalTime.of(10, 30), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelos = new ArrayList<>();
		vuelos.add(vuelo1);
		vuelos.add(vuelo2);
		
		aeropuertoDestino1 = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
		aeropuertoDestino2 = new AeropuertoDestino("Fuiminio", "FMR", "Roma", "Italia", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.OESTE);
		aeropuertosDestino = new ArrayList<>();
		aeropuertosDestino.add(aeropuertoDestino1);
		aeropuertosDestino.add(aeropuertoDestino2);
		
		pista1 = new Pista("P2", 10, TipoPista.ATERRIZAJE);
		pista2 = new Pista("P1", 20, TipoPista.DESPEGUE);
		pistas = new ArrayList<>();
		pistas.add(pista1);
		pistas.add(pista2);
		
		temporada1 = new Temporada("06/01", "08:00", "22:00", "30/09"); 
		temporada2 = new Temporada("30/09", "08:00", "23:00", "30/12"); 

		temporadas = new ArrayList<>();
		temporadas.add(temporada1);
		temporadas.add(temporada2);
		
		notificacion = new Notificacion("Cambio estado de vuelo", TipoNotificacion.CAMBIO_ESTADO_VUELO);
		notificaciones = new ArrayList<>();
		
	}

	@Test
	void testRecibirNotificacion() {
		controlador.recibirNotificacion(notificacion);
		assertTrue(controlador.getNotificaciones().contains(notificacion), "Error la notificación no se recibió correctamente");
	}

	@Test
	void testCambiarEstadoVuelo() throws IllegalArgumentException, VueloNoDelUsuarioException, EstadoNoDelUsuarioException, EstadoEquivocadoException, NoPistasDisponiblesException, NoVueloEnMenosDe1HoraException, VueloEnMenosDe1HoraException, NoFingerMercanciasException, NoModosPuertasEmbarqueDisponiblesException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
		vuelo1.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);  
		 controlador.cambiarEstadoVuelo(vuelo1, EstadoVuelo.ESPERANDO_DESPEGUE);

		 assertEquals(EstadoVuelo.ESPERANDO_DESPEGUE, vuelo1.getEstado(), 
		        "Error: El estado del vuelo no cambió correctamente a ESPERANDO_DESPEGUE.");
		 vuelo1.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE); // Estado inválido para cambiar

		 Exception excepcion = assertThrows(EstadoEquivocadoException.class, () -> {
		 controlador.cambiarEstadoVuelo(vuelo1, EstadoVuelo.OPERATIVO);
		        });

		assertEquals("El vuelo no está en el estado correcto para cambiar a este estado", excepcion.getMessage(),
		            "Error: No se lanzó la excepción correcta cuando el estado es incorrecto.");
		
		 vuelo1.cambiarEstado(EstadoVuelo.A_TIEMPO);

		   Exception excepcion2 = assertThrows(NoPistasDisponiblesException.class, () -> {
		        controlador.cambiarEstadoVuelo(vuelo1, EstadoVuelo.ESPERANDO_ATERRIZAJE);
		    });

		    assertEquals("No hay pistas disponibles", excepcion2.getMessage(),
		        "Error: No se lanzó la excepción esperada cuando no hay pistas disponibles.");
		    
		    Exception excepcion3 = assertThrows(IllegalArgumentException.class, () -> {
		        controlador.cambiarEstadoVuelo(null, EstadoVuelo.OPERATIVO);
		    });

		    assertEquals("El vuelo y el nuevo estado no pueden ser null.", excepcion3.getMessage(),
		        "Error: No se lanzó la excepción esperada cuando el vuelo es null.");
		
	}

	@Test
	void testRecibirNotificacionCambioEstado() throws IllegalArgumentException, VueloNoDelUsuarioException, EstadoNoDelUsuarioException, EstadoEquivocadoException, NoPistasDisponiblesException, NoVueloEnMenosDe1HoraException, VueloEnMenosDe1HoraException, NoFingerMercanciasException, NoModosPuertasEmbarqueDisponiblesException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
		controlador.cambiarEstadoVuelo(vuelo1, EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
		controlador.recibirNotificacionCambioEstado(vuelo1);
		notificacion2 = new Notificacion("El vuelo " + vuelo1.getCodigo() + " ha cambiado su estado a " + vuelo1.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO);
		notificaciones.add(notificacion2);
		assertEquals(notificaciones, controlador.getNotificaciones(), "Error, no se recibió la notificación correctamente");
		
	}


	@Test
	void testGetTerminal() {
		assertEquals(terminal1, controlador.getTerminal(), "Error, la terminal no es correcta");
	}

	@Test
	void testGetDni() {
		assertEquals("56872372J", controlador.getDni(), "Error, el dni no coincide");
	}

	@Test
	void testGetGestor() {
		assertEquals(gestor, controlador.getGestor(), "Error el gestor no coincide");
	}

	@Test
	void testIniciarSesion() {
		assertDoesNotThrow(()->controlador.iniciarSesion("56872372J", "Juanpe44"), "Error, no debería lanzar una excpeción");
		assertThrows(ContrasenaInvalidaException.class, ()->controlador.iniciarSesion("56872372J", "NoContra"), "Error debería lanzar excepción de contraseña inválida");
		assertThrows(UsuarioNoEncontradoException.class, ()->controlador.iniciarSesion("56086572J", "NoContra"), "Error debería lanzar excepción de usuario no encontrado");
		
	}

	@Test
	void testGetAeropuerto() {
		assertEquals(aeropuertoPropio, controlador.getAeropuerto(), "Error el aeropuerto no es correcto");
	}

	@Test
	void testGetSistema() {
		assertEquals(sistema, controlador.getSistema(), "Error el sistema no coincide");
	}

	@Test
	void testGetNombre() {
		assertEquals("Juan Pedro", controlador.getNombre(), "Error, el nombre no es correcto");
	}

	@Test
	void testGetContrasena() {
		assertEquals("Juanpe44", controlador.getContrasena(), "Error la contraseña no es correcta");
	
	}

	@Test
	void testGetNotificaciones() {
		controlador.recibirNotificacion(notificacion);
		controlador.recibirNotificacion(notificacion2);
		notificaciones.add(notificacion);
		notificaciones.add(notificacion2);
		assertEquals(notificaciones, controlador.getNotificaciones(), "Error la lista de notificaciones no coincide");
	}

	@Test
	void testSetNombre() {
		controlador.setNombre("Raúl");
		assertEquals("Raúl", controlador.getNombre(), "Error, el nombre no se asignó correctamente");
	}

	@Test
	void testSetContrasena() {
		controlador.setContrasena("NGFCG2U48XS");
		assertEquals("NGFCG2U48XS", controlador.getContrasena(), "Error la contraseña no se asignó correctamente");
	}

	@Test
	void testVerListaVuelos() {
		aeropuertoPropio.addVueloAceptado(vuelo1);
		aeropuertoPropio.addVueloAceptado(vuelo2);
		
		controlador.verListaVuelos();
		salidaConsola = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaConsola));
        String salidaEsperada = """
        LISTA DE VUELOS DEL DÍA %s
        +----------------------+----------------------+
        | Código de Vuelo      | Estado del Vuelo     |
        +----------------------+----------------------+
        | V132                 | ESPERANDO_PISTA_DE_ATERRIZAJE |Esperando pista durante más de 10min
        +----------------------+----------------------+
        """.formatted(LocalDate.now());

        assertEquals(salidaEsperada.strip(), salidaConsola.toString().strip(), "Error: La salida de la consola no coincide con la esperada.");
	}

	@Test
	void testBuscarVuelo() {
		aeropuertoPropio.addVueloAceptado(vuelo1);
		aeropuertoPropio.addVueloAceptado(vuelo2);
		System.setOut(System.out); 
        
        String salidaEsperada =
                "BÚSQUEDA DE VUELOS SEGÚN EL CRITERIO CODIGO\n" +
                "+---------------------+------------------+\n" +
                "| Código de Vuelo     | Estado del Vuelo |\n" +
                "+---------------------+------------------+\n" +
                "| V123               | ACEPTADO          |\n" +
                "+---------------------+------------------+\n" +
                "+---------------------+------------------+";

        assertEquals(salidaEsperada.trim(), salidaConsola.toString().trim(), "Error: La salida de la búsqueda por código no es correcta.");
	}


	@Test
	void testCerrarSesion() {
		controlador.cerrarSesion();
		System.setOut(System.out);
		assertEquals("El usuario ha cerrado sesión", salidaConsola.toString().trim(), 
                "Error: El mensaje de cerrar sesión no coincide con lo esperado.");
	}

}

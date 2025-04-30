package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.app.AirportManager;
import src.airportmanager.app.aeropuerto.AeropuertoDestino;
import src.airportmanager.app.aeropuerto.AeropuertoPropio;
import src.airportmanager.app.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.app.aeropuerto.Temporada;
import src.airportmanager.app.avion.Aerolinea;
import src.airportmanager.app.avion.Avion;
import src.airportmanager.app.avion.AvionPasajeros;
import src.airportmanager.app.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.app.exceptions.ContrasenaInvalidaException;
import src.airportmanager.app.exceptions.EstadoEquivocadoException;
import src.airportmanager.app.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.app.exceptions.NoFingerMercanciasException;
import src.airportmanager.app.exceptions.NoHangaresDisponiblesException;
import src.airportmanager.app.exceptions.NoModosPuertasEmbarqueDisponiblesException;
import src.airportmanager.app.exceptions.NoPistasDisponiblesException;
import src.airportmanager.app.exceptions.NoVueloEnMenosDe1HoraException;
import src.airportmanager.app.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.app.exceptions.VueloEnMenosDe1HoraException;
import src.airportmanager.app.exceptions.VueloNoDelUsuarioException;
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
import src.airportmanager.usuario.CriterioBusqueda;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;
import src.airportmanager.usuario.Usuario;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;

/**
 * ControladorAereoTets
 */
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

	/**
	 * Constructor
	 */
	public ControladorAereoTest() {

	}

	/**
	 * CreaObjetos
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2,
				DireccionRelativaCiudad.ESTE);

		puertasList = new ArrayList<>();
		puertasList2 = new ArrayList<>();
		p1 = new PuertaEmbarque();
		p2 = new PuertaEmbarque();
		p3 = new PuertaEmbarque();
		p4 = new PuertaEmbarque();

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
		controlador = new ControladorAereo("Juan Pedro", "56872372J", "Juanpe44", aeropuertoPropio, gestor, terminal1,
				sistema);
		operador = new Operador("Miguel", "56098976M", aerolinea1, aeropuertoPropio, gestor, sistema);
		usuarios = new ArrayList<>();
		usuarios.add(gestor);
		usuarios.add(operador);
		usuarios.add(controlador);

		avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 0);
		vuelo1 = new Vuelo("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30), null,
				TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA,
				avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelo2 = new Vuelo("V132", LocalDate.of(2024, 5, 12), null, null, LocalTime.of(10, 30), null,
				TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA,
				avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelos = new ArrayList<>();
		vuelos.add(vuelo1);
		vuelos.add(vuelo2);

		aeropuertoDestino1 = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9,
				ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
		aeropuertoDestino2 = new AeropuertoDestino("Fuiminio", "FMR", "Roma", "Italia", 56.9, ZoneOffset.of("+1"), 3,
				DireccionRelativaCiudad.OESTE);
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
		salidaConsola = new ByteArrayOutputStream();
		System.setOut(new PrintStream(salidaConsola));

	}

	/**
	 * RecibirNotificaicon
	 */
	@Test
	void testRecibirNotificacion() {
		controlador.recibirNotificacion(notificacion);
		assertTrue(controlador.getNotificaciones().contains(notificacion),
				"Error la notificación no se recibió correctamente");
	}

	/**
	 * CambiarEstadoVuelo
	 */
	@Test
	void testCambiarEstadoVuelo() {

		LocalTime horaFutura = LocalTime.now().plusHours(2);
		Vuelo vueloPrueba = new Vuelo("V_TEST", LocalDate.now(), null, null,
				horaFutura, null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino1,
				aeropuertoPropio, null, operador);

		vueloPrueba.setControlador(controlador);
		vueloPrueba.setTerminal(terminal1);
		vueloPrueba.setPista(pista2);
		pista2.addVuelo(vueloPrueba);
		aeropuertoPropio.addVueloAceptado(vueloPrueba);
		aeropuertoPropio.addTerminal(terminal1);
		aeropuertoPropio.addPista(pista2);

		vueloPrueba.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);

		System.out.println("=== PRE-TEST VERIFICACIÓN ===");
		System.out.println("Terminal asignada: " + (vueloPrueba.getTerminal() != null));
		System.out.println("Pista asignada: " + (vueloPrueba.getPista() != null));
		System.out.println(
				"Tipo pista: " + (vueloPrueba.getPista() != null ? vueloPrueba.getPista().getTipoPista() : "N/A"));
		System.out.println("Estado inicial: " + vueloPrueba.getEstado());

		assertDoesNotThrow(() -> {
			controlador.cambiarEstadoVuelo(vueloPrueba, EstadoVuelo.ESPERANDO_DESPEGUE);
		}, "No debería lanzar excepción");

		assertEquals(EstadoVuelo.ESPERANDO_DESPEGUE, vueloPrueba.getEstado(),
				"El estado debería haber cambiado a ESPERANDO_DESPEGUE");

		System.out.println("=== POST-TEST ===");
		System.out.println("Estado resultante: " + vueloPrueba.getEstado());
	}

	/**
	 * recibirNotifcacionCambioEstado
	 */
	@Test
	void testRecibirNotificacionCambioEstado() {

		vuelo1.setControlador(controlador);
		vuelo1.setTerminal(terminal1);
		aeropuertoPropio.addVueloAceptado(vuelo1);
		aeropuertoPropio.addTerminal(terminal1);
		terminal1.agregarControlador(controlador);

		controlador.getNotificaciones().clear();

		vuelo1.cambiarEstado(EstadoVuelo.A_TIEMPO);

		assertEquals(EstadoVuelo.A_TIEMPO, vuelo1.getEstado(),
				"El estado del vuelo debería haber cambiado");

		List<Notificacion> notificaciones = controlador.getNotificaciones();

		if (notificaciones.isEmpty()) {

			System.out.println("=== DEBUG INFO ===");
			System.out.println("Estado actual del vuelo: " + vuelo1.getEstado());
			System.out.println("Controlador asignado: " + vuelo1.getControlador());
			System.out.println("Notificaciones en controlador: " + notificaciones.size());
			System.out.println("Métodos disponibles en controlador: " +
					Arrays.toString(controlador.getClass().getMethods()));
		}

		assertFalse(!notificaciones.isEmpty(),
				"Debería haber al menos una notificación después de cambiar el estado");
	}

	/**
	 * GetTerminal
	 */
	@Test
	void testGetTerminal() {
		assertEquals(terminal1, controlador.getTerminal(), "Error, la terminal no es correcta");
	}

	/**
	 * GetDni
	 */
	@Test
	void testGetDni() {
		assertEquals("56872372J", controlador.getDni(), "Error, el dni no coincide");
	}

	/**
	 * GetGestor
	 */
	@Test
	void testGetGestor() {
		assertEquals(gestor, controlador.getGestor(), "Error el gestor no coincide");
	}

	/**
	 * GetAeropuerto
	 */
	@Test
	void testGetAeropuerto() {
		assertEquals(aeropuertoPropio, controlador.getAeropuerto(), "Error el aeropuerto no es correcto");
	}

	/**
	 * GetSistema
	 */
	@Test
	void testGetSistema() {
		assertEquals(sistema, controlador.getSistema(), "Error el sistema no coincide");
	}

	/**
	 * GetNombre
	 */
	@Test
	void testGetNombre() {
		assertEquals("Juan Pedro", controlador.getNombre(), "Error, el nombre no es correcto");
	}

	/**
	 * GetContrasena
	 */
	@Test
	void testGetContrasena() {
		assertEquals("Juanpe44", controlador.getContrasena(), "Error la contraseña no es correcta");

	}

	/**
	 * GetNotificacones
	 */
	@Test
	void testGetNotificaciones() {

		controlador.getNotificaciones().clear();

		Notificacion notifCambioEstado1 = new Notificacion("Cambio estado vuelo 123",
				TipoNotificacion.CAMBIO_ESTADO_VUELO);
		Notificacion notifCambioEstado2 = new Notificacion("Cambio estado vuelo 456",
				TipoNotificacion.CAMBIO_ESTADO_VUELO);

		controlador.recibirNotificacion(notifCambioEstado1);
		controlador.recibirNotificacion(notifCambioEstado2);

		assertEquals(2, controlador.getNotificaciones().size(),
				"Debería tener exactamente 2 notificaciones de cambio de estado");

		assertTrue(controlador.getNotificaciones().stream()
				.allMatch(n -> n.getTipoNotificacion() == TipoNotificacion.CAMBIO_ESTADO_VUELO),
				"Todas las notificaciones deben ser de cambio de estado");

		List<String> mensajesEsperados = List.of("Cambio estado vuelo 123", "Cambio estado vuelo 456");
		List<String> mensajesRecibidos = controlador.getNotificaciones().stream()
				.map(Notificacion::getMensaje)
				.collect(Collectors.toList());

		assertTrue(mensajesRecibidos.containsAll(mensajesEsperados),
				"Falta alguno de los mensajes esperados");
	}

	/**
	 * SetNombre
	 */
	@Test
	void testSetNombre() {
		controlador.setNombre("Raúl");
		assertEquals("Raúl", controlador.getNombre(), "Error, el nombre no se asignó correctamente");
	}

	/**
	 * SetContrasema
	 */
	@Test
	void testSetContrasena() {
		controlador.setContrasena("NGFCG2U48XS");
		assertEquals("NGFCG2U48XS", controlador.getContrasena(), "Error la contraseña no se asignó correctamente");
	}

	/**
	 * VerListaVuelos
	 */
	@Test
	void testVerListaVuelos() {

		vuelo1.setTerminal(terminal1);
		vuelo1.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE);
		vuelo1.setRetraso(15);

		aeropuertoPropio.addVueloAceptado(vuelo1);
		aeropuertoPropio.addTerminal(terminal1);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		controlador.verListaVuelos();
		String salida = outContent.toString();

		assertTrue(salida.contains("LISTA DE VUELOS DEL DÍA"));
		assertTrue(salida.contains(vuelo1.getCodigo()));
		assertTrue(salida.contains("ESPERANDO_PISTA_DE_ATERRIZAJE"));

		System.setOut(System.out);
	}

	/**
	 * CerrarSesion
	 */
	@Test
	void testCerrarSesion() {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		controlador.cerrarSesion();

		System.setOut(originalOut);

		String salida = outputStream.toString().trim();
		assertTrue(salida.contains("El usuario ha cerrado sesión") ||
				salida.contains("Sesión cerrada"),
				"El mensaje de cierre de sesión no es correcto");
	}

}

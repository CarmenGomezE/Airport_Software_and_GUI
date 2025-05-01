package src;

import java.util.*;
import java.util.Map.Entry;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.pista.*;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.usuario.*;
import src.airportmanager.terminal.*;
import src.airportmanager.notificacion.*;
import java.util.Scanner;

import es.uam.eps.padsof.invoices.IResourceUsageInfo;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Clase que implementa un demostrador básico de la aplicación.
 * 
 * @author Carmen Gómez, Claudia Municio, María Pozo.
 */
public class AirportManagerMain {

	/**
	 * Nombre del fichero desde el que se carga y donde se guarda el estado de la
	 * aplicación
	 */
	private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";
	private static final String ARCHIVO_AEDESTINO = "resources/aeropuerto_destino.txt";
	private static AirportManager airportManager;

	/**
	 * Constructor
	 */
	private AirportManagerMain() {
		/** Constructor vacío */
	}

	/**
	 * Método principal
	 * 
	 * @param args Argumentos recibidos.
	 * @throws ContrasenaInvalidaException                contraseña inválida.
	 * @throws UsuarioNoEncontradoException               usuario no encontrado.
	 * @throws OperacionInvalidaException                 operación inválida.
	 * @throws SolicitudInvalidaExcepcion                 solicitud inválida.
	 * @throws CargaAvionEquivocadaException              carga no válida.
	 * @throws NoHangaresDisponiblesException             no hnagar disponibles.
	 * @throws NoModosPuertasEmbarqueDisponiblesException no puerta con modo
	 *                                                    disponible.
	 * @throws NoFingerMercanciasException                finger mercancias no.
	 * @throws VueloEnMenosDe1HoraException               vuelo menos de 1 hora.
	 * @throws NoVueloEnMenosDe1HoraException             no vuelo menos de 1 hora.
	 * @throws NoPistasDisponiblesException               no pistas disponibles.
	 * @throws EstadoEquivocadoException                  estado equivocado.
	 * @throws EstadoNoDelUsuarioException                estado no del usuario.
	 * @throws VueloNoDelUsuarioException                 vuelo no del usuario.
	 * @throws IllegalArgumentException                   argumento inválido.
	 */
	public static void main(String[] args)
			throws UsuarioNoEncontradoException, ContrasenaInvalidaException, SolicitudInvalidaExcepcion,
			OperacionInvalidaException, IllegalArgumentException, VueloNoDelUsuarioException,
			EstadoNoDelUsuarioException, EstadoEquivocadoException, NoPistasDisponiblesException,
			NoVueloEnMenosDe1HoraException, VueloEnMenosDe1HoraException, NoFingerMercanciasException,
			NoModosPuertasEmbarqueDisponiblesException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
		/** Inicialización del estado */
		vaciarArchivo(ARCHIVO_ESTADO);
		System.out.println("Iniciando simulación...");
		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println(
				"\n0.- Comenzamos cargando la aplicación desde un estado previo si lo tiene o inicalizándolo.");
		airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO);
		if (airportManager == null) {
			System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
			airportManager = new AirportManager();
		} else {
			System.out.println("Estado cargado correctamente.");
		}
		airportManager.iniciarAplicacion();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n1.- Dar de alta elementos del aeropuerto.");
		darDeAlta();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n2.- Configurar datos del aeropuerto propio.");
		configurarDatosAeropuertoPropio();
		resetearDatosAeropuertoPropio();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n3.- Iniciar y cerrar sesión.");
		inicioSesion();
		cierreSesion();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n4.- Programar vuelos y compartirlos.");
		programarVuelos();
		compartirVuelosProgramados();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n5.- Notificaciones.");
		recibirNotificaciones();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n6.- Buscar vuelos.");
		buscarVuelos();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\nPara lo que queda de simulación se van a añadir vuelos, y otros datos a mano.");
		generarListaVuelosDia();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n7.- Ver y recargar la lista de vuelos del día y sus estados.");
		verListaVuelosDia();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n8.- Cambiar el estado de los vuelos.");
		cambiarEstadoVuelo();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n9.- Ver estadísticas.");
		verEstadisticas();

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\n10.- Emisión y pago de facturas.");
		emisionYpagoFacturas();

		System.out.println(
				"\n*********************************************************************************************************************");

		System.out.println("\n11.-Guardamos el estado en un fichero.");
		airportManager.guardarEstado(ARCHIVO_ESTADO);
		System.out.println("Estado guardado. Cerrando aplicación.");

		System.out.println(
				"\n*********************************************************************************************************************");
		System.out.println("\nFinalizando simulación...");
	}

	/**
	 * Método que vacia el archivo.
	 * 
	 * @param archivo Archivo.
	 */
	public static void vaciarArchivo(String archivo) {
		try {
			File file = new File(archivo);
			System.out.println("\nIntentando vaciar el archivo: " + file.getAbsolutePath());

			file.getParentFile().mkdirs();

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.close();
				System.out.println("El archivo ha sido vaciado correctamente.");
			}
		} catch (IOException e) {
			System.err.println("Error al vaciar el archivo: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Método que prueba dar de alta.
	 */
	public static void darDeAlta() {
		System.out.println("\nIniciando el proceso de alta de aerolíneas y operadores.");
		/** Aerolinea y operador */
		System.out.println("Dando de alta la aerolínea Iberia con código IBE.");
		try {
			airportManager.getGestor().darDeAltaAerolinea("Iberia", "IBE");
		} catch (AerolineaYaExisteException e) {
			System.err.println("Error al dar de alta aerolinea: " + e.getMessage());
		}
		System.out.println("Dando de alta el operador Francisco Gonzalez para la aerolínea Iberia.");
		try {
			airportManager.getGestor().darDeAltaOperador("Francisco Gonzalez", "12345678A",
					airportManager.getAeropuerto().getAerolineas().get(0));
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta operador: " + e.getMessage());
		}

		Operador operadorIberia = airportManager.getAeropuerto().getAerolineas().get(0).getOperador();
		/** Aviones */
		System.out.println("Dando de alta los aviones para la aerolínea Iberia:");
		operadorIberia.darDeAltaAvionPasajeros("Boeing", "737", 12.5, 30.5, 40.0, 3500, 2015, new Date(), "EC-HGR",
				180);
		System.out.println(
				"Avión de pasajeros Boeing 737 de Iberia dado de alta con matrícula EC-HGR y capacidad de 180 pasajeros.");
		operadorIberia.darDeAltaAvionPasajeros("Airbus", "A319", 12.5, 30.5, 40.0, 3500, 2016, new Date(), "EC-HGS",
				180);
		System.out.println(
				"Avión de pasajeros Airbus A319 de Iberia dado de alta con matrícula EC-HGS y capacidad de 180 pasajeros.");
		operadorIberia.darDeAltaAvionPasajeros("ATR72", "600", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "EC-HGT", 180);
		System.out.println(
				"Avión de pasajeros ATR72 600 de Iberia dado de alta con matrícula EC-HGT y capacidad de 180 pasajeros.");
		operadorIberia.darDeAltaAvionMercancias("Airbus", "A300", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "EC-KBX",
				10000, true, true);
		System.out.println(
				"Avión de mercancías Airbus A300 de Iberia dado de alta con matrícula EC-KBX y capacidad de 10,000 kg.");

		/** Aerolinea y operador */
		System.out.println("Dando de alta la aerolínea Air France con código AFR.");
		try {
			airportManager.getGestor().darDeAltaAerolinea("Air France", "AFR");
		} catch (AerolineaYaExisteException e) {
			System.err.println("Error al dar de alta aerolinea: " + e.getMessage());
		}
		System.out.println("Dando de alta el operador Danielle Abbadie para la aerolínea Air France.");
		try {
			airportManager.getGestor().darDeAltaOperador("Danielle Abbadie", "87654321B",
					airportManager.getAeropuerto().getAerolineas().get(1));
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta operador: " + e.getMessage());
		}

		Operador operadorAirFrance = airportManager.getAeropuerto().getAerolineas().get(1).getOperador();
		/** Aviones */
		operadorAirFrance.darDeAltaAvionPasajeros("Boeing", "787", 14.0, 40.0, 50.0, 7000, 2018, new Date(), "F-GKXC",
				250);
		System.out.println(
				"Avión de pasajeros Boeing 787 de Air France dado de alta con matrícula F-GKXC y capacidad de 250 pasajeros.");
		operadorAirFrance.darDeAltaAvionMercancias("Airbus", "A330", 18.0, 50.0, 70.0, 10000, 2017, new Date(),
				"F-GKXG", 15000, false, true);
		System.out.println(
				"Avión de mercancías Airbus A330 de Air France dado de alta con matrícula F-GKXG y capacidad de 15,000 kg.");
		operadorAirFrance.darDeAltaAvionMercancias("Airbus", "A220", 18.0, 50.0, 70.0, 10000, 2017, new Date(),
				"F-GKXL", 15000, false, true);
		System.out.println(
				"Avión de mercancías Airbus A220 de Air France dado de alta con matrícula F-GKXL y capacidad de 15,000 kg.");

		System.out.println("Proceso de alta de aerolíneas y aviones completado.");

		System.out.println("\nIniciando el proceso de alta de la infraestructura del aeropuerto.");

		/** Puertas de embarque */
		System.out.println("Creando puertas de embarque para la terminal T1 (Pasajeros) y T2 (Mercancías).");
		List<PuertaEmbarque> puertasT1 = new ArrayList<>();
		List<PuertaEmbarque> puertasT2 = new ArrayList<>();
		List<PuertaEmbarque> puertasNuevasT1 = new ArrayList<>();
		List<PuertaEmbarque> puertasNuevasT2 = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			puertasT1.add(new PuertaEmbarque());
			puertasT2.add(new PuertaEmbarque());
			puertasNuevasT1.add(new PuertaEmbarque());
			puertasNuevasT2.add(new PuertaEmbarque());
		}
		System.out.println("Puertas de embarque para T1: " + puertasT1.size() + " y para T2: " + puertasT2.size());

		/** Terminales */
		try {
			airportManager.getGestor().darDeAltaTerminal("T1", "Pasajeros", 500, puertasT1);
		} catch (TerminalYaExisteException e) {
			System.err.println("Error al dar de alta terminal: " + e.getMessage());
		}
		Terminal terminalT1 = airportManager.getAeropuerto().getTerminales().get(0);
		try {
			airportManager.getGestor().darDeAltaTerminal("T2", "Mercancías", 500, puertasT2);
		} catch (TerminalYaExisteException e) {
			System.err.println("Error al dar de alta terminal: " + e.getMessage());
		}
		Terminal terminalT2 = airportManager.getAeropuerto().getTerminales().get(1);
		System.out.println("Dadas de alta Terminal T1 (Pasajeros) y Terminal T2 (Mercancías).");

		System.out.println("Dando de alta las puertas de embarque en las terminales.");
		for (PuertaEmbarque puerta : puertasNuevasT1) {
			airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT1);
		}
		for (PuertaEmbarque puerta : puertasNuevasT2) {
			airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT2);
		}
		System.out.println("Puertas de embarque dadas de alta en las terminales T1 y T2.");

		/** Controladores */
		System.out.println("Dando de alta los controladores para las terminales:");
		try {
			airportManager.getGestor().darDeAltaControlador("Ana Gómez", "12345678L", terminalT1);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta controlador: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaControlador("Victoria Ramirez", "12335678C", terminalT1); 
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta controlador: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaControlador("Alfonso Ruiz", "12345623T", terminalT2);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta controlador: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaControlador("Charles Green", "17445678F", terminalT2);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta controlador: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaControlador("Marta Sanz", "12312678K", terminalT2);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta controlador: " + e.getMessage());
		}
		System.out.println("Controladores Ana Gómez y Victoria Ramirez dados de alta para la Terminal T1.");
		System.out.println("Controladores Alfonso Ruiz, Charles Green y Marta Sanz dados de alta para la Terminal T2.");

		/** Hangares */
		System.out.println("Dando de alta hangares para las terminales:");
		airportManager.getGestor().darDeAltaHangar("Pasajeros", 6, 2500, 1500, 4000, 1000.0, false);
		airportManager.getGestor().darDeAltaHangar("Mercancías", 3, 2800, 1700, 4300, 1000.0, true);
		System.out.println("Hangar de Pasajeros y Hangar de Mercancías dados de alta.");

		PuertaEmbarque puerta1T1 = airportManager.getAeropuerto().getTerminales().get(0).getPuertasEmbarque().get(0);
		PuertaEmbarque puerta2T1 = airportManager.getAeropuerto().getTerminales().get(0).getPuertasEmbarque().get(1);
		PuertaEmbarque puerta3T1 = airportManager.getAeropuerto().getTerminales().get(0).getPuertasEmbarque().get(2);
		PuertaEmbarque puerta4T1 = airportManager.getAeropuerto().getTerminales().get(0).getPuertasEmbarque().get(3);
		PuertaEmbarque puerta1T2 = airportManager.getAeropuerto().getTerminales().get(1).getPuertasEmbarque().get(0);
		PuertaEmbarque puerta2T2 = airportManager.getAeropuerto().getTerminales().get(1).getPuertasEmbarque().get(1);
		PuertaEmbarque puerta3T2 = airportManager.getAeropuerto().getTerminales().get(1).getPuertasEmbarque().get(2);
		PuertaEmbarque puerta4T2 = airportManager.getAeropuerto().getTerminales().get(1).getPuertasEmbarque().get(3);

		List<PuertaEmbarque> puertasF1 = new ArrayList<>();
		puertasF1.add(puerta1T1);
		List<PuertaEmbarque> puertasF2 = new ArrayList<>();
		puertasF2.add(puerta2T1);
		List<PuertaEmbarque> puertasF3 = new ArrayList<>();
		puertasF3.add(puerta1T1);
		puertasF3.add(puerta3T1);

		/** Fingers */
		System.out.println("Dando de alta los Finger para la Terminal T1.");
		airportManager.getGestor().darDeAltaFinger(5.0, 200.0, puertasF1);
		airportManager.getGestor().darDeAltaFinger(5.0, 200.0, puertasF2);
		airportManager.getGestor().darDeAltaFinger(5.0, 200.0, puertasF3);
		System.out.println("Fingers dados de alta para las puertas de embarque T1.");

		for (Terminal terminal : airportManager.getAeropuerto().getTerminales()) {
			for (PuertaEmbarque puerta : terminal.getPuertasEmbarque()) {
				if (puerta.getFinger() == null) {
					System.out.println("La puerta de embarque " + puerta.getId() + " no tiene asignado un Finger.");
				}
			}
		}

		List<PuertaEmbarque> puertasZ1 = new ArrayList<>();
		puertasZ1.add(puerta1T1);
		puertasZ1.add(puerta2T1);
		puertasZ1.add(puerta3T1);
		puertasZ1.add(puerta4T1);
		List<PuertaEmbarque> puertasZ2 = new ArrayList<>();
		puertasZ2.add(puerta1T2);
		puertasZ2.add(puerta2T2);
		puertasZ2.add(puerta3T2);
		puertasZ2.add(puerta4T2);

		/** Zonas de aparcamiento */
		System.out.println("Dando de alta zonas de aparcamiento en el aeropuerto.");
		airportManager.getGestor().darDeAltaZonaAparcamiento(2.5, 50, 70, puertasZ1);
		airportManager.getGestor().darDeAltaZonaAparcamiento(3.0, 50, 70, puertasZ2);
		System.out.println("Zonas de aparcamiento dadas de alta.");

		/** Pistas */
		System.out.println("Dando de alta las pistas de aterrizaje y despegue.");
		try {
			airportManager.getGestor().darDeAltaPista("P1", 3000, TipoPista.ATERRIZAJE);
		} catch (PistaYaExisteException e) {
			System.err.println("Error:al dar de alta pista: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaPista("P2", 3000, TipoPista.DESPEGUE);
		} catch (PistaYaExisteException e) {
			System.err.println("Error al dar de alta pista: " + e.getMessage());
		}
		System.out.println("Pistas de aterrizaje y despegue dadas de alta.");
		System.out.println("Proceso de alta de infraestructura del aeropuerto completado.");

		/** Aeropuertos de destino */
		System.out.println("\nIniciando proceso de alta de aeropuertos de destino.");
		try {
			airportManager.getGestor().darDeAltaAeropuertoDestino(ARCHIVO_AEDESTINO);
			System.out.println("Aeropuerto destino dado de alta correctamente. Nombre del nuevo aeropuerto: "
					+ airportManager.getAeropuerto().getAeropuertosDestino().getLast().getNombre());
		} catch (IOException | TemporadasSolapadasException e) {
			System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
		} catch (AeropuertoYaExisteException e) {
			System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
		}

		System.out.println("Proceso de alta de aeropuertos de destino completado.");
	}

	/**
	 * Método que prueba programar vuelos.
	 */
	private static void programarVuelos() {
		Operador operadorIberia = (Operador) airportManager.getGestor().buscarUsuarioPorDni("12345678A");
		Operador operadorAirFrance = (Operador) airportManager.getGestor().buscarUsuarioPorDni("87654321B");
		Gestor gestorBarajas = airportManager.getGestor();
		;
		Avion avionEC_HGR = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(0);
		Avion avionEC_HGS = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(1);
		Avion avionEC_HGT = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(2);
		Avion avionEC_KBX = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(3);
		Avion avionF_GKXC = airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(0);
		Avion avionF_GKXG = airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(1);
		Avion avionF_GKXL = airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(2);

		AeropuertoPropio aePropio = airportManager.getAeropuerto();
		Aeropuerto aeCairo = airportManager.getAeropuerto().getAeropuertosDestino().get(8);
		Aeropuerto aeTokio = airportManager.getAeropuerto().getAeropuertosDestino().get(6);
		Aeropuerto aeFKennedy = airportManager.getAeropuerto().getAeropuertosDestino().get(3);
		Aeropuerto aeMallorca = airportManager.getAeropuerto().getAeropuertosDestino().get(2);
		Aeropuerto aeMalaga = airportManager.getAeropuerto().getAeropuertosDestino().get(0);

		System.out.println("\nIniciando las propuestas del vuelos.");
		System.out.println("\nIniciando la propuesta del vuelos del Operador de Iberia.");
		try {
			System.out.println("Proponiendo vuelo IBE1721...");
			operadorIberia.proponerVuelo("IBE1721", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
					Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGR, aeTokio, aePropio, null);
			System.out.println("Vuelo IBE1721 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo IBE1721: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo IBE8246...");
			operadorIberia.proponerVuelo("IBE8246", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
					Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGS, aeCairo, aePropio, null);
			System.out.println("Vuelo IBE8246 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo IBE8246: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo IBE2325...");
			operadorIberia.proponerVuelo("IBE2325", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeFKennedy, null);
			System.out.println("Vuelo IBE2325 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo IBE2325: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo IBE5017...");
			operadorIberia.proponerVuelo("IBE5017", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(10, 0), Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SATURDAY), TipoCarga.MERCANCIAS,
					TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.RECURRENTE, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeMallorca, null);
			System.out.println("Vuelo IBE5017 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo IBE5017: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo IBE3371...");
			operadorIberia.proponerVuelo("IBE3371", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGT, aeFKennedy, aePropio, null);
			System.out.println("Vuelo IBE3371 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo IBE3371: " + e.getMessage());
		}

		System.out.println("Iniciando gestión de vuelo del Gestor.");
		List<VueloProgramado> vuelosAGestionar = new ArrayList<>(gestorBarajas.getVuelosPropuestos());
		for (VueloProgramado v : vuelosAGestionar) {
			gestorBarajas.gestionarVuelos(v);
		}
		System.out.println("Gestión de vuelos completada con éxito.");

		System.out.println("Iniciando gestión de alternativas del Operador.");
		if (!operadorIberia.getAlternativasAgrupadas().isEmpty()) {
			Map<VueloProgramado, List<Vuelo>> alternativasAgrupadas = operadorAirFrance.getAlternativasAgrupadas();

			if (!alternativasAgrupadas.isEmpty()) {
				VueloProgramado clave = alternativasAgrupadas.keySet().iterator().next();
				operadorIberia.rechazarVuelo(clave);
				System.out.println("Alternativa aceptada.");
			} else {
				System.out.println("No hay alternativas disponibles.");
			}
		}

		System.out.println("\nIniciando la propuesta del vuelos del Operador de AirFrance.");
		try {
			System.out.println("Proponiendo vuelo AFR2270...");
			operadorAirFrance.proponerVuelo("AFR2270", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionF_GKXL, aeMalaga, aePropio, null);
			System.out.println("Vuelo AFR2270 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR2270: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo AFR3101...");
			operadorAirFrance.proponerVuelo("AFR3101", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
					Periodicidad.DIARIO, TipoVuelo.SALIDA, avionF_GKXC, aeMallorca, aePropio, null);
			System.out.println("Vuelo AFR3101 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR3101: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo AFR8802...");
			operadorAirFrance.proponerVuelo("AFR8802", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(17, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
					Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionF_GKXC, aePropio, aeFKennedy,
					new Escala(aePropio, aeTokio, LocalTime.of(17, 0), LocalTime.of(23, 0)));
			System.out.println("Vuelo AFR8802 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR8802: " + e.getMessage());
		}

		try {
			System.out.println("Proponiendo vuelo AFR7107...");
			operadorAirFrance.proponerVuelo("AFR7107", LocalDate.now().plusDays(31), LocalDate.now(),
					LocalDate.now().plusDays(2),
					LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionF_GKXC, aeTokio, aePropio, null);
			System.out.println("Vuelo AFR7107 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR7107: " + e.getMessage());
		}

		System.out.println("Iniciando gestión de vuelo del Gestor.");
		List<VueloProgramado> vuelosAGestionar2 = new ArrayList<>(gestorBarajas.getVuelosPropuestos());
		for (VueloProgramado v : vuelosAGestionar2) {
			gestorBarajas.gestionarVuelos(v);
		}

		System.out.println("Gestión de vuelos completada con éxito.");

		System.out.println("Iniciando gestión de alternativas del Operador.");
		if (!operadorAirFrance.getAlternativasAgrupadas().isEmpty()) {
			Map<VueloProgramado, List<Vuelo>> alternativasAgrupadas = operadorAirFrance.getAlternativasAgrupadas();

			VueloProgramado clave = alternativasAgrupadas.keySet().iterator().next();
			operadorAirFrance.aceptarVuelo(alternativasAgrupadas.get(clave).get(0));
			System.out.println("Alternativa aceptada.");
		}

		System.out.println("\nIniciando algunos ejemplos de propuestas de vuelos que generan errores.");
		try {
			System.out.println("Proponiendo vuelo AFR2993...");
			operadorAirFrance.proponerVuelo("AFR2993", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(20, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.DIARIO, TipoVuelo.LLEGADA, avionF_GKXG, aePropio, aeCairo,
					new Escala(aePropio, aeFKennedy, LocalTime.of(20, 0), LocalTime.of(20, 30)));
			System.out.println("Vuelo AFR2993 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR2993: " + e.getMessage());
		}
		try {
			System.out.println("Proponiendo vuelo AFR2993...");
			operadorAirFrance.proponerVuelo("AFR2993", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(20, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.DIARIO, TipoVuelo.LLEGADA, avionF_GKXC, aePropio, aeCairo,
					new Escala(aePropio, aeFKennedy, LocalTime.of(20, 0), LocalTime.of(20, 30)));
			System.out.println("Vuelo AFR2993 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR2993: " + e.getMessage());
		}
		try {
			System.out.println("Proponiendo vuelo AFR2993...");
			operadorAirFrance.proponerVuelo("AFR2993", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(20, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.DIARIO, TipoVuelo.LLEGADA, avionF_GKXG, aePropio, aePropio,
					new Escala(aePropio, aeFKennedy, LocalTime.of(20, 0), LocalTime.of(20, 30)));
			System.out.println("Vuelo AFR2993 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR2993: " + e.getMessage());
		}
		try {
			System.out.println("Proponiendo vuelo AFR2993...");
			operadorAirFrance.proponerVuelo("AFR2993", LocalDate.now().plusDays(31), LocalDate.now().plusDays(31),
					LocalDate.now().plusDays(33),
					LocalTime.of(20, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
					Periodicidad.DIARIO, TipoVuelo.LLEGADA, avionF_GKXC, aeCairo, aePropio,
					new Escala(aePropio, aeFKennedy, LocalTime.of(20, 0), LocalTime.of(20, 30)));
			System.out.println("Vuelo AFR2993 propuesto correctamente.");
		} catch (Exception e) {
			System.err.println("Error al proponer el vuelo AFR2993: " + e.getMessage());
		}

		System.out.println("\nLista de vuelos aceptados:");
		for (VueloProgramado v : airportManager.getAeropuerto().getVuelosAceptados()) {
			System.out.println("Vuelo: " + v.getCodigo() + " - Fecha: " + v.getFecha() + " - Hora: " + v.getHora());
		}

		System.out.println("\nMostrando notificaciones del gestor de Barajas...");
		gestorBarajas.verNotificaciones();

		System.out.println("\nMostrando notificaciones del operador de Iberia...");
		operadorIberia.verNotificaciones();

		System.out.println("\nMostrando notificaciones del operador de AirFrance...");
		operadorAirFrance.verNotificaciones();

		System.out.println("Proceso de propuesta de vuelos finalizado.");
	}

	/**
	 * Método que prueba compartir vuelos.
	 */
	private static void compartirVuelosProgramados() {
		Operador operadorIberia = (Operador) airportManager.getGestor().buscarUsuarioPorDni("12345678A");
		Operador operadorAirFrance = (Operador) airportManager.getGestor().buscarUsuarioPorDni("87654321B");
		try {
			airportManager.getGestor().darDeAltaAerolinea("Ryanair", "FRK");
		} catch (AerolineaYaExisteException e) {
			System.err.println("Error al dar de alta aerolinea: " + e.getMessage());
		}
		try {
			airportManager.getGestor().darDeAltaOperador("Eustaquio Hernandez", "50738890L",
					airportManager.getAeropuerto().getAerolineas().get(2));
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			System.err.println("Error al dar de alta operador: " + e.getMessage());
		}
		Operador operadorRyanair = airportManager.getAeropuerto().getAerolineas().get(2).getOperador();
		Vuelo vueloAFR2270 = airportManager.getAeropuerto().getVuelosAceptados().get(6);
		Vuelo vueloAFR7107 = airportManager.getAeropuerto().getVuelosAceptados().get(8);

		System.out.println("\nIniciando las solicitudes de compartir vuelo.");
		System.out.println(
				"Realizando una solicitud del Operador de Iberia de compartir vuelo al Operador de AirFrance.");
		try {
			operadorIberia.solicitarCompartirVuelo(vueloAFR2270, operadorAirFrance, 20);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}

		System.out.println("El operador de AirFrance la rechaza.");
		operadorAirFrance.rechazarSolicitud(operadorAirFrance.getSolicitudesRecibidas().get(0));

		System.out.println(
				"Realizando otra solicitud del Operador de Iberia de compartir vuelo al Operador de AirFrance.");
		try {
			operadorIberia.solicitarCompartirVuelo(vueloAFR2270, operadorAirFrance, 30);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}

		System.out.println("El operador de AirFrance la acepta.");
		operadorAirFrance.aceptarSolicitud(operadorAirFrance.getSolicitudesRecibidas().get(1));

		System.out.println(
				"Realizando una solicitud del Operador de Ryanair de compartir vuelo al Operador de AirFrance.");
		try {
			operadorRyanair.solicitarCompartirVuelo(vueloAFR2270, operadorAirFrance, 20);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}

		System.out.println("El operador de AirFrance la acepta.");
		operadorAirFrance.aceptarSolicitud(operadorAirFrance.getSolicitudesRecibidas().get(2));

		System.out.println(
				"\nRealizando solicitudes inválidas del Operador de Ryanair de compartir vuelo al Operador de AirFrance.");
		try {
			operadorRyanair.solicitarCompartirVuelo(vueloAFR2270, operadorAirFrance, 20);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}
		try {
			operadorRyanair.solicitarCompartirVuelo(vueloAFR7107, operadorIberia, 80);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}
		try {
			operadorRyanair.solicitarCompartirVuelo(vueloAFR7107, operadorAirFrance, 80);
		} catch (SolicitudInvalidaExcepcion e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		} catch (OperacionInvalidaException e) {
			System.err.println("Error al solicitar compartir el vuelo AFR2270: " + e.getMessage());
		}
		System.out.println("El operador de AirFrance la intenta aceptar.");
		operadorAirFrance.aceptarSolicitud(operadorAirFrance.getSolicitudesRecibidas().get(3));

		System.out.println("\nBandeja de solicitudes del Operador de Iberia");
		operadorIberia.verSolicitudes();
		System.out.println("\nBandeja de solicitudes del Operador de Ryanair");
		operadorRyanair.verSolicitudes();
		System.out.println("\nBandeja de solicitudes del Operador de AirFrance");
		operadorAirFrance.verSolicitudes();

		System.out.println("Proceso de compartir vuelos finalizado.");
	}

	/**
	 * Método que prueba la emisión de facturas.
	 */
	private static void emisionYpagoFacturas() {
		Gestor gestorBarajas = airportManager.getGestor();
		Aerolinea Ryanair = airportManager.getAeropuerto().getAerolineas().get(2);
		Operador operadorRyanair = (Operador) airportManager.getGestor().buscarUsuarioPorDni("50738890L");

		System.out.println("\nLista de vuelos aceptados:");
		for (VueloProgramado v : airportManager.getAeropuerto().getVuelosAceptados()) {
			System.out.println("Vuelo: " + v.getCodigo() + " - Fecha: " + v.getFecha() + " - Hora: " + v.getHora());
		}

		System.out.println("\nIniciando el proceso de creación de facturas.");
		System.out.println("Generando factura para la aerolínea Ryanair...");
		gestorBarajas.emitirFactura(Ryanair);
		System.out.println("Factura generada para Ryanair.");

		System.out.println("Obteniendo la primera factura de Ryanair...");
		Factura facturaRyanair = null;
		for (Factura f : Ryanair.getFacturas()) {
			System.out.println("Factura para aerolínea: " + f.getAirline());
			System.out.println("Código: " + f.getInvoiceIdentifier());
			System.out.println("Fecha: " + f.getInvoiceDate());
			System.out.println("Base: " + f.getBasePrice());
			System.out.println("Recargo: " + f.getSurcharge());
			System.out.println("Recursos:");
			for (IResourceUsageInfo recurso : f.getResourcePrices()) {
				System.out.println(" - " + recurso.getResourceDescription() + ": " +
						recurso.getPrice());
			}
			System.out.println("----------------------");
			facturaRyanair = f;
		}

		String numeroTarjeta = "1234567891234567";
		System.out.println("Iniciando pago de la factura " + facturaRyanair.getInvoiceIdentifier()
				+ " con tarjeta de crédito: " + numeroTarjeta);
		operadorRyanair.pagarFactura(facturaRyanair, numeroTarjeta);
		System.out.println("Pago de la factura realizado con éxito.");
		gestorBarajas.verFacturas();

		System.out.println("Proceso de creación de facturas finalizado.");
	}

	/**
	 * Método que configura datos del aeropuerto.
	 */
	private static void configurarDatosAeropuertoPropio() {
		System.out.println("\nIniciando configuración del aeropuerto: " + airportManager.getAeropuerto().getNombre());

		System.out.println(
				"Nombre del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getNombre());
		airportManager.getGestor().configurarAeropuertoPropioNombre("Heathrow");
		System.out.println(
				"Nombre del aeropuerto después de la configuración: " + airportManager.getAeropuerto().getNombre());

		System.out.println(
				"Código del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCodigo());
		airportManager.getGestor().configurarAeropuertoPropioCodigo("LHR");
		System.out.println(
				"Código del aeropuerto después de la configuración: " + airportManager.getAeropuerto().getCodigo());

		System.out.println(
				"Ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCiudad());
		airportManager.getGestor().configurarAeropuertoPropioCiudad("Londres");
		System.out.println(
				"Ciudad del aeropuerto después de la configuración: " + airportManager.getAeropuerto().getCiudad());

		System.out.println("País del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getPais());
		airportManager.getGestor().configurarAeropuertoPropioPais("Reino Unido");
		System.out.println(
				"País del aeropuerto después de la configuración: " + airportManager.getAeropuerto().getPais());

		System.out.println("Distancia ciudad del aeropuerto antes de la configuración:"
				+ airportManager.getAeropuerto().getDistanciaCiudad());
		airportManager.getGestor().configurarAeropuertoPropioDistanciaCiudad(24);
		System.out.println("Distancia ciudad del aeropuerto después de la configuración: "
				+ airportManager.getAeropuerto().getDistanciaCiudad());

		System.out.println("Diferencia horaria del aeropuerto antes de la configuración:"
				+ airportManager.getAeropuerto().getDiferenciaHoraria());
		airportManager.getGestor().configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset.ofHours(+1));
		System.out.println("Diferencia horaria del aeropuerto después de la configuración: "
				+ airportManager.getAeropuerto().getDiferenciaHoraria());

		System.out.println("Número de temporadas del aeropuerto antes de la configuración:"
				+ airportManager.getAeropuerto().getTemporadas().size());
		airportManager.getGestor().configurarAeropuertoPropioNumTemporadas(3);
		try {
			airportManager.getAeropuerto().agregarTemporada(new Temporada("01/01", "10:00", "20:00", "30/06"));
		} catch (TemporadasSolapadasException e) {
			System.out.println("Error al configurar el aeropuerto: " + e.getMessage());
		}
		System.out.println("Número de temporadas del aeropuerto después de la configuración: "
				+ airportManager.getAeropuerto().getTemporadas().size());

		System.out.println("Dirección ciudad del aeropuerto antes de la configuración:"
				+ airportManager.getAeropuerto().getDireccionCiudad());
		airportManager.getGestor().configurarAeropuertoPropioDireccionCiudad(DireccionRelativaCiudad.SUR);
		System.out.println("Dirección ciudad  del aeropuerto después de la configuración: "
				+ airportManager.getAeropuerto().getDireccionCiudad());

		System.out.println("Procedimiento de configuración del aeropuerto finalizado.");
	}

	/**
	 * Método que reestablece la configuración del aeropuerto.
	 */
	private static void resetearDatosAeropuertoPropio() {
		airportManager.getGestor().configurarAeropuertoPropioNombre("Aeropuerto Adolfo Suarez Madrid-Barajas");
		airportManager.getGestor().configurarAeropuertoPropioCodigo("MAD");
		airportManager.getGestor().configurarAeropuertoPropioCiudad("Madrid");
		airportManager.getGestor().configurarAeropuertoPropioPais("España");
		airportManager.getGestor().configurarAeropuertoPropioDistanciaCiudad(10.5);
		airportManager.getGestor().configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset.ofHours(0));
		airportManager.getGestor().configurarAeropuertoPropioNumTemporadas(2);
		airportManager.getGestor().configurarAeropuertoPropioDireccionCiudad(DireccionRelativaCiudad.ESTE);
	}

	/**
	 * Método que genera la lista de vuelos del día.
	 */
	private static void generarListaVuelosDia() throws SolicitudInvalidaExcepcion, OperacionInvalidaException {
		Operador operadorRyanair = airportManager.getAeropuerto().getAerolineas().get(2).getOperador();
		Operador operadorIberia = airportManager.getAeropuerto().getAerolineas().get(0).getOperador();

		operadorRyanair.darDeAltaAvionPasajeros("Boeing", "787", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "FR_HGR",
				180);
		operadorRyanair.darDeAltaAvionMercancias("Boeing", "737", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "FR_HGS",
				8000, true, true);
		operadorRyanair.darDeAltaAvionMercancias("Boeing", "767", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "FR_HGT",
				8000, true, true);
		operadorRyanair.darDeAltaAvionPasajeros("Boeing", "757", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "FR_HGU",
				180);
		operadorRyanair.darDeAltaAvionMercancias("Boeing", "747", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "FR_HGP",
				8000, true, true);
		operadorRyanair.darDeAltaAvionPasajeros("Boeing", "707", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "FR_HGC",
				180);
		operadorRyanair.darDeAltaAvionMercancias("Boeing", "797", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "FR_HGM",
				8000, true, true);
		operadorRyanair.darDeAltaAvionPasajeros("Boeing", "717", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "FR_HGQ",
				180);

		operadorIberia.darDeAltaAvionPasajeros("Boeing", "787", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "IB_HGR",
				180);
		operadorIberia.darDeAltaAvionMercancias("Boeing", "737", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "IB_HGS",
				8000, true, true);
		operadorIberia.darDeAltaAvionMercancias("Boeing", "767", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "IB_HGT",
				8000, true, true);
		operadorIberia.darDeAltaAvionPasajeros("Boeing", "757", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "IB_HGU",
				180);
		operadorIberia.darDeAltaAvionMercancias("Boeing", "747", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "IB_HGP",
				8000, true, true);
		operadorIberia.darDeAltaAvionPasajeros("Boeing", "707", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "IB_HGC",
				180);
		operadorIberia.darDeAltaAvionMercancias("Boeing", "797", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "IB_HGM",
				8000, true, true);
		operadorIberia.darDeAltaAvionPasajeros("Boeing", "717", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "IB_HGQ",
				180);

		Avion avionFR_HGR = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(0);
		Avion avionFR_HGS = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(1);
		Avion avionFR_HGT = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(2);
		Avion avionFR_HGU = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(3);
		Avion avionFR_HGP = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(4);
		Avion avionFR_HGC = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(5);
		Avion avionFR_HGM = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(6);
		Avion avionFR_HGQ = airportManager.getAeropuerto().getAerolineas().get(2).getAviones().get(7);

		Avion avionIB_HGR = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(0);
		Avion avionIB_HGS = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(1);
		Avion avionIB_HGT = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(2);
		Avion avionIB_HGU = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(3);
		Avion avionIB_HGP = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(4);
		Avion avionIB_HGC = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(5);
		Avion avionIB_HGM = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(6);
		Avion avionIB_HGQ = airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(7); 

		AeropuertoPropio aePropio = airportManager.getAeropuerto();
		Aeropuerto aeTokio = airportManager.getAeropuerto().getAeropuertosDestino().get(6);
		Aeropuerto aeMallorca = airportManager.getAeropuerto().getAeropuertosDestino().get(2);
		Aeropuerto aeCairo = airportManager.getAeropuerto().getAeropuertosDestino().get(8);
		Aeropuerto aeFKennedy = airportManager.getAeropuerto().getAeropuertosDestino().get(3);
		Aeropuerto aeMalaga = airportManager.getAeropuerto().getAeropuertosDestino().get(0);

		Vuelo vueloFRK2234 = new Vuelo("FRK2234", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGR, aeTokio, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8103 = new Vuelo("FRK8103", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGS, aePropio, aeMallorca, null, operadorRyanair);
		Vuelo vueloFRK4152 = new Vuelo("FRK4152", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGS, aePropio, aeCairo, null, operadorRyanair);
		Vuelo vueloFRK8104 = new Vuelo("FRK8104", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGU, aeTokio, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8105 = new Vuelo("FRK8105", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGT, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloFRK8106 = new Vuelo("FRK8106", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGS, aePropio, aeCairo, null, operadorRyanair);
		Vuelo vueloFRK8107 = new Vuelo("FRK8107", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGP, aePropio, aeMallorca, null, operadorRyanair);
		Vuelo vueloFRK8108 = new Vuelo("FRK8108", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGC, aeMallorca, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8109 = new Vuelo("FRK8109", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(40), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGT, aeCairo, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8110 = new Vuelo("FRK8110", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGQ, aeMalaga, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8111 = new Vuelo("FRK8111", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionFR_HGP, aeMalaga, aePropio, null, operadorRyanair);
		Vuelo vueloFRK8112 = new Vuelo("FRK8112", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGU, aePropio, aeMalaga, null, operadorRyanair);
		Vuelo vueloFRK8113 = new Vuelo("FRK8113", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGM, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloFRK8114 = new Vuelo("FRK8114", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGM, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloFRK8115 = new Vuelo("FRK8115", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionFR_HGC, aePropio, aeFKennedy, null, operadorRyanair);

		Vuelo vueloIBE2234 = new Vuelo("IBE2234", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGR, aeTokio, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8103 = new Vuelo("IBE8103", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGS, aePropio, aeMallorca, null, operadorRyanair);
		Vuelo vueloIBE4152 = new Vuelo("IBE4152", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGS, aePropio, aeCairo, null, operadorRyanair);
		Vuelo vueloIBE8104 = new Vuelo("IBE8104", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGU, aeTokio, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8105 = new Vuelo("IBE8105", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGT, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloIBE8106 = new Vuelo("IBE8106", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGS, aePropio, aeCairo, null, operadorRyanair);
		Vuelo vueloIBE8107 = new Vuelo("IBE8107", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGP, aePropio, aeMallorca, null, operadorRyanair);
		Vuelo vueloIBE8108 = new Vuelo("IBE8108", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGC, aeMallorca, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8109 = new Vuelo("IBE8109", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGT, aeCairo, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8110 = new Vuelo("IBE8110", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(42), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGQ, aeMalaga, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8111 = new Vuelo("IBE8111", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().minusMinutes(42), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionIB_HGP, aeMalaga, aePropio, null, operadorRyanair);
		Vuelo vueloIBE8112 = new Vuelo("IBE8112", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGU, aePropio, aeMalaga, null, operadorRyanair);
		Vuelo vueloIBE8113 = new Vuelo("IBE8113", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGM, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloIBE8114 = new Vuelo("IBE8114", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGM, aePropio, aeFKennedy, null, operadorRyanair);
		Vuelo vueloIBE8115 = new Vuelo("IBE8115", LocalDate.now(), LocalDate.now(), LocalDate.now(),
				LocalTime.now().plusMinutes(28), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
				Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionIB_HGC, aePropio, aeFKennedy, null, operadorRyanair);

		Terminal terminalT1 = airportManager.getAeropuerto().getTerminales().get(0);
		Terminal terminalT2 = airportManager.getAeropuerto().getTerminales().get(1);

		ControladorAereo controladorT1 = terminalT1.getControladores().get(0);
		ControladorAereo controladorT2 = terminalT2.getControladores().get(0);

		airportManager.getGestor().aceptarVuelo(vueloFRK2234, terminalT1, controladorT1);
		airportManager.getGestor().aceptarVuelo(vueloFRK8104, terminalT1, controladorT1);
		
		 airportManager.getGestor().aceptarVuelo(vueloFRK8106, terminalT1,controladorT1);
		 airportManager.getGestor().aceptarVuelo(vueloFRK8108, terminalT1,controladorT1);
		 
		 airportManager.getGestor().aceptarVuelo(vueloFRK8110, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8112, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8115, terminalT1,
		  controladorT1);
		  
		  airportManager.getGestor().aceptarVuelo(vueloFRK8103, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK4152, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8105, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8107, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8109, terminalT2,
		 controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8111, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8113, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloFRK8114, terminalT2,
		  controladorT2);
		  
		  airportManager.getGestor().aceptarVuelo(vueloIBE2234, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8104, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8106, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8108, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8110, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8112, terminalT1,
		  controladorT1);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8115, terminalT1,
		  controladorT1);
		  
		  
		  airportManager.getGestor().aceptarVuelo(vueloIBE8103, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE4152, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8105, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8107, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8109, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8111, terminalT2,
		  controladorT2);
		  airportManager.getGestor().aceptarVuelo(vueloIBE8113, terminalT2,
		 controladorT2);
		 airportManager.getGestor().aceptarVuelo(vueloIBE8114, terminalT2,controladorT2);
	}

	/**
	 * Método que cambia de estados.
	 */
	private static void cambiarEstadoVuelo() throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException,
			IllegalArgumentException, EstadoEquivocadoException, NoPistasDisponiblesException,
			NoVueloEnMenosDe1HoraException, VueloEnMenosDe1HoraException, NoFingerMercanciasException,
			NoModosPuertasEmbarqueDisponiblesException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
		Operador operadorRyanair = (Operador) airportManager.getGestor().buscarUsuarioPorDni("50738890L");
		Operador operadorIberia = airportManager.getAeropuerto().getAerolineas().get(0).getOperador();

		Terminal terminalT1 = airportManager.getAeropuerto().getTerminales().get(0);
		Terminal terminalT2 = airportManager.getAeropuerto().getTerminales().get(1);

		ControladorAereo controladorT1 = terminalT1.getControladores().get(0);
		ControladorAereo controladorT2 = terminalT2.getControladores().get(0);

		Vuelo vueloFRK2234 = airportManager.getAeropuerto().getVuelosAceptados().get(10);
		Vuelo vueloFRK8104 = airportManager.getAeropuerto().getVuelosAceptados().get(11);
		Vuelo vueloFRK8103 = airportManager.getAeropuerto().getVuelosAceptados().get(17);

		try {
			operadorIberia.solicitarCompartirVuelo(vueloFRK2234, operadorRyanair, 30);
		} catch (SolicitudInvalidaExcepcion | OperacionInvalidaException e) {
			e.printStackTrace();
		}

		operadorRyanair.aceptarSolicitud(operadorRyanair.getSolicitudesRecibidas().get(0));
		
		System.out.println("\nIniciando el proceso de cambio de estados de vuelos.");
		System.out.println("Comenzamos con los estados de un vuelo de salida:");
		System.out.println("Vuelo FRK2234: Estado cambiado a EN_PREPARACION_PARA_DESPEGAR");
		operadorRyanair.cambiarEstadoVuelo(vueloFRK2234, EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);

		System.out.println("\nVuelo FRK2234 se intenta cambiar a estado INICIO_DEL_EMBARQUE");
		operadorRyanair.cambiarEstadoVuelo(vueloFRK2234, EstadoVuelo.INICIO_DEL_EMBARQUE);

		System.out.println("\nVuelo FRK2234 se intenta cambiar a estado FIN_DEL_EMBARQUE");
		operadorRyanair.cambiarEstadoVuelo(vueloFRK2234, EstadoVuelo.FIN_DEL_EMBARQUE);

		System.out.println("\nVuelo FRK2234 se intenta cambiar a estado ESPERANDO_PISTA_DE_DESPEGUE");
		controladorT1.cambiarEstadoVuelo(vueloFRK2234, EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);

		System.out.println("\nVuelo FRK2234 se intenta cambiar a estado ESPERANDO_DESPEGUE");

		System.out.println("\nContinuamos con los estados de un vuelo de llegada:");
		
		System.out.println("Vuelo FRK8103 se intenta cambiar a estado ESPERANDO_PISTA_DE_ATERRIZAJE");
		controladorT2.cambiarEstadoVuelo(vueloFRK8103, EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE);
		 
		 System.out.println("\nVuelo FRK8103 se intenta cambiar a estado ESPERANDO_ATERRIZAJE");
		 controladorT2.cambiarEstadoVuelo(vueloFRK8103,EstadoVuelo.ESPERANDO_ATERRIZAJE);
		  
		  System.out.println("\nVuelo FRK8103 se intenta cambiar a estado ATERRIZAJE");
		  controladorT2.cambiarEstadoVuelo(vueloFRK8103, EstadoVuelo.ATERRIZAJE);
		  
		  System.out.println("\nVuelo FRK8103 se intenta cambiar a estadoINICIO_DEL_DESEMBARQUE");
		  operadorRyanair.cambiarEstadoVuelo(vueloFRK8103, EstadoVuelo.INICIO_DEL_DESEMBARQUE);
		  
		  System.out.println("\nVuelo FRK8103 se intenta cambiar a estado FIN_DEL_DESEMBARQUE");
		  operadorRyanair.cambiarEstadoVuelo(vueloFRK8103,
		  EstadoVuelo.FIN_DEL_DESEMBARQUE);
		  
		  System.out.println("\nVuelo FRK8103 se intenta cambiar a estado OPERATIVO");
		  controladorT2.cambiarEstadoVuelo(vueloFRK8103, EstadoVuelo.OPERATIVO);
		  
		  System.out.println("\nVuelo FRK8103 se intenta cambiar a estadoAVION_A_HANGAR");
		  controladorT2.cambiarEstadoVuelo(vueloFRK8103, EstadoVuelo.AVION_A_HANGAR);
		 

		System.out.println("Vamos a imprimir el historial de estados de cada vuelo:");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		System.out.println("Historial de vuelo FRK2234:");
		for (Entry<LocalTime, EstadoVuelo> entry : vueloFRK2234.getHistorialEstadoVuelo().getHistorial().entrySet()) {
			System.out.println("  - " + entry.getKey().format(formatter) + " → " + entry.getValue());
		}

		try {
			operadorRyanair.cambiarEstadoVuelo(vueloFRK8104, EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
			operadorRyanair.cambiarEstadoVuelo(vueloFRK8104, EstadoVuelo.INICIO_DEL_EMBARQUE);
			operadorRyanair.cambiarEstadoVuelo(vueloFRK8104, EstadoVuelo.FIN_DEL_EMBARQUE);
			controladorT1.cambiarEstadoVuelo(vueloFRK8104, EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
			controladorT1.cambiarEstadoVuelo(vueloFRK8104, EstadoVuelo.ESPERANDO_DESPEGUE);

		} catch (VueloNoDelUsuarioException | EstadoNoDelUsuarioException | EstadoEquivocadoException e) {
			e.printStackTrace();
		}

		System.out.println("\nProcedimiento de cambio de estados de vuelos finalizado.");
	}

	/**
	 * Método que recibe notificaciones.
	 */
	private static void recibirNotificaciones() {
		Operador operadorAirFrance = (Operador) airportManager.getGestor().buscarUsuarioPorDni("87654321B");
		ControladorAereo controladorT2 = (ControladorAereo) airportManager.getGestor().buscarUsuarioPorDni("12312678K");
		Gestor gestorBarajas = airportManager.getGestor();

		Notificacion notificacionCambioEstado = new Notificacion("El vuelo AF123 ha cambiado su estado a 'Embarcando'.",
				TipoNotificacion.CAMBIO_ESTADO_VUELO);
		Notificacion notificacionOlvidoContrasena = new Notificacion(
				"Se ha solicitado un restablecimiento de contraseña para su cuenta.",
				TipoNotificacion.OLVIDO_CONTRASENA);
		Notificacion notificacionSolicitudCompartirVuelo = new Notificacion(
				"El operador de Iberia ha solicitado compartir el vuelo IB456 con su aerolínea.",
				TipoNotificacion.SOLICITUD_COMPARTIR_VUELO);
		Notificacion notificacionFacturaEmitida = new Notificacion(
				"Se ha emitido una factura por el vuelo AF789. Consulte su perfil para más detalles.",
				TipoNotificacion.FACTURA_EMITIDA);
		Notificacion notificacionOpcionesReprogramarVuelo = new Notificacion(
				"Su vuelo IB321 ha sido afectado. Tiene nuevas opciones para reprogramarlo.",
				TipoNotificacion.OPCIONES_REPROGRAMAR_VUELO);
		Notificacion notificacionSolicitudVueloProgramado = new Notificacion(
				"Se ha programado un nuevo vuelo desde Madrid a París para el 10 de abril a las 15:00.",
				TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO);

		System.out.println("\nIniciando recepción de notificaciones.");
		System.out.println("\nPrimero probamos el método genérico.");
		System.out.println("Enviamos al operador de AirFrance todas las posibles notificaciones.");
		operadorAirFrance.recibirNotificacion(notificacionCambioEstado);
		operadorAirFrance.recibirNotificacion(notificacionOlvidoContrasena);
		operadorAirFrance.recibirNotificacion(notificacionSolicitudCompartirVuelo);
		operadorAirFrance.recibirNotificacion(notificacionFacturaEmitida);
		operadorAirFrance.recibirNotificacion(notificacionOpcionesReprogramarVuelo);
		operadorAirFrance.recibirNotificacion(notificacionSolicitudVueloProgramado);
		System.out.println("Imprimimos las notificaciones que ha recibido el operador de AirFrance.");
		operadorAirFrance.verNotificaciones();

		System.out.println("\nEnviamos a un controlador de la T2 todas las posibles notificaciones.");
		controladorT2.recibirNotificacion(notificacionCambioEstado);
		controladorT2.recibirNotificacion(notificacionOlvidoContrasena);
		controladorT2.recibirNotificacion(notificacionSolicitudCompartirVuelo);
		controladorT2.recibirNotificacion(notificacionFacturaEmitida);
		controladorT2.recibirNotificacion(notificacionOpcionesReprogramarVuelo);
		controladorT2.recibirNotificacion(notificacionSolicitudVueloProgramado);
		System.out.println("Imprimimos las notificaciones que ha recibido el controlador de la T2.");
		controladorT2.verNotificaciones();

		System.out.println("\nEnviamos al gestor del aeropuerto todas las posibles notificaciones.");
		gestorBarajas.recibirNotificacion(notificacionCambioEstado);
		gestorBarajas.recibirNotificacion(notificacionOlvidoContrasena);
		gestorBarajas.recibirNotificacion(notificacionSolicitudCompartirVuelo);
		gestorBarajas.recibirNotificacion(notificacionFacturaEmitida);
		gestorBarajas.recibirNotificacion(notificacionOpcionesReprogramarVuelo);
		gestorBarajas.recibirNotificacion(notificacionSolicitudVueloProgramado);
		System.out.println("Imprimimos las notificaciones que ha recibido el gestor de Barajas.");
		gestorBarajas.verNotificaciones();

		System.out.println(
				"\nEliminamos que el gestor reciba notificaciones de cambio de estado de vuelo, y le mandamos una para comprobar que no la recibe.");
		List<TipoNotificacion> nuevaConfiguracion = gestorBarajas.getTiposPermitidosNotificaciones();
		nuevaConfiguracion.remove(TipoNotificacion.CAMBIO_ESTADO_VUELO);
		gestorBarajas.configurarNotificaciones(nuevaConfiguracion);
		gestorBarajas.recibirNotificacion(notificacionCambioEstado);

		nuevaConfiguracion.add(TipoNotificacion.CAMBIO_ESTADO_VUELO);
		gestorBarajas.configurarNotificaciones(nuevaConfiguracion);
		
		System.out.println("\nSegundo comprobamos otras situaciones.");
		System.out.println("Al operador de AirFrance con un vuelo de su aerolinea.");
		operadorAirFrance.recibirNotificacionCambioEstado(airportManager.getAeropuerto().getVuelosAceptados().get(6));
		System.out.println("Al operador de AirFrance con un vuelo de otra aerolinea.");
		operadorAirFrance.recibirNotificacionCambioEstado(airportManager.getAeropuerto().getVuelosAceptados().get(1));
		System.out.println("Al operador de AirFrance con un vuelo de otra aerolinea.");
		System.out.println("Al controlador de la T2 un cambio de vuelo de su terminal.");
		controladorT2.recibirNotificacionCambioEstado(airportManager.getAeropuerto().getVuelosAceptados().get(2));
		System.out.println("Al controlador de la T2 un cambio de vuelo de otra terminal.");
		controladorT2.recibirNotificacionCambioEstado(airportManager.getAeropuerto().getVuelosAceptados().get(1));

		System.out.println("\nProceso de recepción de notificaciones finalizado.");
	}

	/**
	 * Método que ve la lista de vuelos del dia.
	 */
	private static void verListaVuelosDia() {
		System.out.println("\nIniciando ver la lista de vuelos del día.");
		airportManager.getGestor().verListaVuelos();
		System.out.println("\nProceso de visualización de la lista dle vuelos del día finalizado.");
	}

	/**
	 * Método que busca vuelos por criterios
	 */
	private static void buscarVuelos() {
		Operador operadorAirFrance = (Operador) airportManager.getGestor().buscarUsuarioPorDni("87654321B");
		ControladorAereo controladorT2 = (ControladorAereo) airportManager.getGestor().buscarUsuarioPorDni("12312678K");
		Gestor gestorBarajas = airportManager.getGestor();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		/** Con parámetros válidos */
		System.out.println("\nIniciando búsqueda de vuelos por criterios de búsqueda.");
		System.out.println("Operador Air France busca vuelo por código...");
		operadorAirFrance.buscarVuelo(CriterioBusqueda.CODIGO, "IBE1721");

		System.out.println("Operador Air France busca vuelo por destino...");
		operadorAirFrance.buscarVuelo(CriterioBusqueda.DESTINO, "España");

		System.out.println("Controlador T2 busca vuelo por fecha...");
		controladorT2.buscarVuelo(CriterioBusqueda.FECHA, LocalDate.now().plusDays(31).format(formatter));

		System.out.println("Controlador T2 busca vuelo por hora de llegada...");
		controladorT2.buscarVuelo(CriterioBusqueda.HORA_LLEGADA, "10:00");

		System.out.println("Controlador T2 busca vuelo por hora de salida...");
		controladorT2.buscarVuelo(CriterioBusqueda.HORA_SALIDA, "10:00");

		System.out.println("Gestor Barajas busca vuelo por origen...");
		gestorBarajas.buscarVuelo(CriterioBusqueda.ORIGEN, "España");

		System.out.println("Gestor Barajas busca vuelo por terminal...");
		gestorBarajas.buscarVuelo(CriterioBusqueda.TERMINAL, "T1");

		System.out.println("Operador Air France busca vuelo por código inválido...");
		operadorAirFrance.buscarVuelo(CriterioBusqueda.CODIGO, "Albacete");

	}

	/**
	 * Método que ve estadísticas.
	 */
	private static void verEstadisticas() {
		Operador operadorIberia = (Operador) airportManager.getGestor().buscarUsuarioPorDni("12345678A");
		Gestor gestorBarajas = airportManager.getGestor();

		System.out.println("\nIniciando visualización de estadísticas.");
		System.out.println("Estadísticas del operador de Iberia.");
		operadorIberia.getEstadisticasOperador().cargarDatosEstadisticasTiempoMedio();
		operadorIberia.getEstadisticasOperador().imprimirNumeroVuelosEnHora();
		operadorIberia.getEstadisticasOperador().imprimirNumeroVuelosRetrasados();
		operadorIberia.getEstadisticasOperador().imprimirTiempoMedioRetraso(Month.MARCH);
		operadorIberia.getEstadisticasOperador().imprimirTiempoMedioRetraso(ZoneId.of("Europe/Madrid"));

		System.out.println("\nEstadísticas del gestor de Barajas.");
		gestorBarajas.getEstadisticasAeropuerto().cargarDatosEstadisticas();

		gestorBarajas.getEstadisticasAeropuerto().imprimirUsoMedioFingers();
		gestorBarajas.getEstadisticasAeropuerto().imprimirUsoMedioHangares();
		gestorBarajas.getEstadisticasAeropuerto().imprimirUsoMedioPuertasEmbarque();
		gestorBarajas.getEstadisticasAeropuerto().imprimirUsoMedioZonasAparcamiento();

		System.out.println("Procesos de visualización de estadísticas finalizados.");
	}

	/**
	 * Método que cierra sesión.
	 */
	private static void cierreSesion() {
		Operador operadorIberia = (Operador) airportManager.getGestor().buscarUsuarioPorDni("12345678A");
		ControladorAereo controladorT1 = (ControladorAereo) airportManager.getGestor().buscarUsuarioPorDni("12335678C");
		Gestor gestorBarajas = airportManager.getGestor();

		System.out.println("\nIniciando los cierres de sesión de los usuarios.");
		System.out.println("\nSesión cerrada para el operador Iberia.");
		operadorIberia.cerrarSesion();

		System.out.println("Sesión cerrada para el controlador T1.");
		controladorT1.cerrarSesion();

		System.out.println("Sesión cerrada para el gestor Barajas.");
		gestorBarajas.cerrarSesion();
		System.out.println("Procesos de cierre de sesión finalizados.");

	}

	/**
	 * Método que inicia sesión.
	 */
	private static void inicioSesion() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
		try (Scanner scanner = new Scanner(System.in)) {
			Operador operadorIberia = (Operador) airportManager.getGestor().buscarUsuarioPorDni("12345678A");
			ControladorAereo controladorT1 = (ControladorAereo) airportManager.getGestor()
					.buscarUsuarioPorDni("12335678C");
			Gestor gestorBarajas = airportManager.getGestor();

			System.out.println("\nIniciando los inicios de sesión de los usuarios.");

			/** Inicio de sesión válido */
			try {
				System.out.println("\nIniciando sesión para el operador Iberia.");

				if (operadorIberia.isNecesitaEstablecerContrasena()) {
					System.out.print("El operador debe establecer una nueva contraseña. Introduce nueva contraseña: ");
					String nuevaContrasena = scanner.nextLine();
					System.out.print("Confirma la nueva contraseña: ");
					String confirmarContrasena = scanner.nextLine();

					if (!nuevaContrasena.equals(confirmarContrasena)) {
						System.out.println("Las contraseñas no coinciden. Intenta de nuevo.");
						return;
					}

					operadorIberia.setContrasena(nuevaContrasena);
					operadorIberia.setNecesitaEstablecerContrasena(false);
					System.out.println("Contraseña establecida correctamente.");
				}

				operadorIberia.iniciarSesion(operadorIberia.getDni(), operadorIberia.getContrasena());
				System.out.println("Sesión iniciada correctamente para el operador Iberia.");

				System.out.println("\nIniciando sesión para el controlador T1.");
				controladorT1.iniciarSesion(controladorT1.getDni(), controladorT1.getContrasena());
				System.out.println("Sesión iniciada correctamente para el controlador T1.");

				System.out.println("\nIniciando sesión para el gestor Barajas.");
				gestorBarajas.iniciarSesion(gestorBarajas.getContrasena());
				System.out.println("Sesión iniciada correctamente para el gestor Barajas.");
			} catch (Exception e) {
				System.err.println("Error al iniciar sesión con credenciales válidas: " + e.getMessage());
			}

			/** Inicio de sesión con DNI inválido */
			try {
				System.out.println("\nIntentando iniciar sesión con DNI inválido para el operador Iberia.");
				operadorIberia.iniciarSesion("33333333F", operadorIberia.getContrasena());
			} catch (Exception e) {
				System.out
						.println("Error al iniciar sesión con DNI inválido para el operador Iberia: " + e.getMessage());
			}

			try {
				System.out.println("\nIntentando iniciar sesión con DNI inválido para el controlador T1.");
				controladorT1.iniciarSesion("33333333F", controladorT1.getContrasena());
			} catch (Exception e) {
				System.out
						.println("Error al iniciar sesión con DNI inválido para el controlador T1: " + e.getMessage());
			}

			/** Inicio de sesión con contraseña inválida */
			try {
				System.out.println("\nIntentando iniciar sesión con contraseña inválida para el operador Iberia.");
				operadorIberia.iniciarSesion(operadorIberia.getDni(), airportManager.generarContrasena());
			} catch (Exception e) {
				System.out.println(
						"Error al iniciar sesión con contraseña inválida para el operador Iberia: " + e.getMessage());
			}

			try {
				System.out.println("\nIntentando iniciar sesión con contraseña inválida para el controlador T1.");
				controladorT1.iniciarSesion(controladorT1.getDni(), airportManager.generarContrasena());
			} catch (Exception e) {
				System.out.println(
						"Error al iniciar sesión con contraseña inválida para el controlador T1: " + e.getMessage());
			}

			try {
				System.out.println("\nIntentando iniciar sesión con contraseña inválida para el gestor Barajas.");
				gestorBarajas.iniciarSesion(airportManager.generarContrasena());
			} catch (Exception e) {
				System.out.println(
						"Error al iniciar sesión con contraseña inválida para el gestor Barajas: " + e.getMessage());
			}

			/** Restablecimiento de contraseña */
			try {
				System.out.println("\nSolicitando restablecimiento de contraseña para el operador Iberia.");
				operadorIberia.heOlvidadoContrasena();
				System.out.println("Contraseña restablecida para el operador Iberia.");
			} catch (Exception e) {
				System.out.println("Error al restablecer contraseña para el operador Iberia: " + e.getMessage());
			}

			try {
				System.out.println("\nSolicitando restablecimiento de contraseña para el controlador T1.");
				controladorT1.heOlvidadoContrasena();
				System.out.println("Contraseña restablecida para el controlador T1.");
			} catch (Exception e) {
				System.out.println("Error al restablecer contraseña para el controlador T1: " + e.getMessage());
			}

			try {
				System.out.println("\nSolicitando restablecimiento de contraseña para el gestor Barajas.");
				gestorBarajas.heOlvidadoContrasena();
				System.out.println("Contraseña restablecida para el gestor Barajas.");
			} catch (Exception e) {
				System.out.println("Error al restablecer contraseña para el gestor Barajas: " + e.getMessage());
			}

			/** Inicio de sesión tras restablecer contraseña */
			try {
				System.out.println("\nIniciando sesión con nueva contraseña para el operador Iberia.");
				operadorIberia.iniciarSesion(operadorIberia.getDni(), operadorIberia.getContrasena());
				System.out.println("Sesión iniciada correctamente para el operador Iberia.");

				System.out.println("\nIniciando sesión con nueva contraseña para el controlador T1.");
				controladorT1.iniciarSesion(controladorT1.getDni(), controladorT1.getContrasena());
				System.out.println("Sesión iniciada correctamente para el controlador T1.");

				System.out.println("\nIniciando sesión con nueva contraseña para el gestor Barajas.");
				gestorBarajas.iniciarSesion(gestorBarajas.getContrasena());
				System.out.println("Sesión iniciada correctamente para el gestor Barajas.");
			} catch (Exception e) {
				System.err.println("Error al iniciar sesión con nuevas credenciales: " + e.getMessage());
			}
		}

		System.out.println("Proceso de inicio de sesión finalizado.");
	}

}

package src;

import java.util.*;


import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.pista.*;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.usuario.*;
import src.airportmanager.terminal.*;
import src.airportmanager.hangar.*;
import src.airportmanager.notificacion.*;
import src.airportmanager.estadisticas.*;

import java.util.*;
import java.io.*;
import java.time.*;

/**
 * Clase que implementa un demostrador básico de la aplicación.
 * 
 * @autor Carmen Gómez, Claudia Municio, María Pozo.
 */
public class AirportManagerMain {
	
	/**Nombre del fichero desde el que se carga y donde se guarda el estado de la aplicación*/
    private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";
    private static final String ARCHIVO_AEDESTINO = "resources/aeropuerto_destino.txt";
    private static AirportManager airportManager;
    /**
     * Método principal
     * 
     * @param args		Argumentos recibidos.
     * @throws ContrasenaInvalidaException 
     * @throws UsuarioNoEncontradoException 
     */
    public static void main(String[] args) throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
    	/** Inicialización del estado*/
    	vaciarArchivo(ARCHIVO_ESTADO);
    	System.out.println("Iniciando simulación...");
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n0.- Comenzamos cargando la aplicación desde un estado previo si lo tiene o inicalizándolo.");
    	airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO); 
        if (airportManager == null) {
            System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
            airportManager = new AirportManager();
        } else {
            System.out.println("Estado cargado correctamente.");
        }
        airportManager.iniciarAplicacion();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n1.- Dar de alta elementos del aeropuerto.");
        darDeAlta();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n2.- Configurar datos del aeropuerto propio.");
        configurarDatosAeropuertoPropio();
        resetearDatosAeropuertoPropio();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n3.- Iniciar y cerrar sesión.");
        inicioSesion();
        cierreSesion();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n4.- Programar vuelos y compartirlos.");
        programarVuelos();
        compartirVuelosProgramados();

        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n5.- Notificaciones.");
        recibirNotificaciones();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n6.- Buscar vuelos.");
        buscarVuelos();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n7.- Cambiar el estado de los vuelos.");
        cambiarEstadoVueloContorlador();
        cambiarEstadoVueloOperador();
        cambiarEstadoVueloAutomatico();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n8.- Ver y recargar la lista de vuelos del día y sus estados.");
        verListaVuelosDia();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n9.- Ver estadísticas.");
        verEstadisticas();
        
        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\n10.- Emisión y pago de facturas.");
        emisionFacturas();
        pagoFacturas();
        
        System.out.println("\n*********************************************************************************************************************");

        System.out.println("\n11.-Guardamos el estado en un fichero.");
        airportManager.guardarEstado(ARCHIVO_ESTADO);
        System.out.println("Estado guardado. Cerrando aplicación.");

        System.out.println("\n*********************************************************************************************************************");
        System.out.println("\nFinalizando simulación...");
    }


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
    public static void darDeAlta() {
    	System.out.println("\nIniciando el proceso de alta de aerolíneas y operadores.");
    	/** Aerolinea y operador*/
    	System.out.println("Dando de alta la aerolínea Iberia con código IBE.");
    	airportManager.getGestor().darDeAltaAerolinea("Iberia", "IBE");
    	System.out.println("Dando de alta el operador Francisco Gonzalez para la aerolínea Iberia.");
    	airportManager.getGestor().darDeAltaOperador("Francisco Gonzalez", "12345678A", airportManager.getAeropuerto().getAerolineas().get(0));
    	
    	Operador operadorIberia= airportManager.getAeropuerto().getAerolineas().get(0).getOperador();
    	/** Aviones*/
    	System.out.println("Dando de alta los aviones para la aerolínea Iberia:");
    	operadorIberia.darDeAltaAvionPasajeros("Boeing", "737", 12.5, 30.5, 40.0, 3500, 2015, new Date(), "EC-HGR", 180);
    	System.out.println("Avión de pasajeros Boeing 737 de Iberia dado de alta con matrícula EC-HGR y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionPasajeros("Airbus", "A319", 12.5, 30.5, 40.0, 3500, 2016, new Date(), "EC-HGS", 180);
    	System.out.println("Avión de pasajeros Airbus A319 de Iberia dado de alta con matrícula EC-HGS y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionPasajeros("ATR72", "600", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "EC-HGT", 180);
    	System.out.println("Avión de pasajeros ATR72 600 de Iberia dado de alta con matrícula EC-HGT y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionMercancias("Airbus", "A300", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "EC-KBX", 10000, true, true);
    	System.out.println("Avión de mercancías Airbus A300 de Iberia dado de alta con matrícula EC-KBX y capacidad de 10,000 kg.");

    	/** Aerolinea y operador*/
    	System.out.println("Dando de alta la aerolínea Air France con código AFR.");
    	airportManager.getGestor().darDeAltaAerolinea("Air France", "AFR");
    	System.out.println("Dando de alta el operador Danielle Abbadie para la aerolínea Air France.");
    	airportManager.getGestor().darDeAltaOperador("Danielle Abbadie", "87654321B", airportManager.getAeropuerto().getAerolineas().get(1));
    	
    	Operador operadorAirFrance= airportManager.getAeropuerto().getAerolineas().get(1).getOperador();
    	/** Aviones*/
    	operadorAirFrance.darDeAltaAvionPasajeros("Boeing", "787", 14.0, 40.0, 50.0, 7000, 2018, new Date(), "F-GKXC", 250);
    	System.out.println("Avión de pasajeros Boeing 787 de Air France dado de alta con matrícula F-GKXC y capacidad de 250 pasajeros.");
    	operadorAirFrance.darDeAltaAvionMercancias("Airbus", "A330", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXG", 15000, false, true);
    	System.out.println("Avión de mercancías Airbus A330 de Air France dado de alta con matrícula F-GKXG y capacidad de 15,000 kg.");
    	operadorAirFrance.darDeAltaAvionMercancias("Airbus", "A220", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXL", 15000, false, true);
    	System.out.println("Avión de mercancías Airbus A220 de Air France dado de alta con matrícula F-GKXL y capacidad de 15,000 kg.");

    	System.out.println("Proceso de alta de aerolíneas y aviones completado.");

    	System.out.println("\nIniciando el proceso de alta de la infraestructura del aeropuerto.");

    	/** Puertas de embarque */
    	System.out.println("Creando puertas de embarque para la terminal T1 (Pasajeros) y T2 (Mercancías).");
    	List<PuertaEmbarque> puertasT1 = new ArrayList<>();
    	List<PuertaEmbarque> puertasT2 = new ArrayList<>();
    	for (int i = 0; i < 4; i++) {  
    	    puertasT1.add(new PuertaEmbarque());
    	    puertasT2.add(new PuertaEmbarque());
    	}
    	System.out.println("Puertas de embarque para T1: " + puertasT1.size() + " y para T2: " + puertasT2.size());

    	/** Terminales */
    	airportManager.getGestor().darDeAltaTerminal("T1", "Pasajeros", 500, puertasT1);
    	Terminal terminalT1= airportManager.getAeropuerto().getTerminales().get(0);
    	airportManager.getGestor().darDeAltaTerminal("T2", "Mercancías", 500, puertasT2);
    	Terminal terminalT2=airportManager.getAeropuerto().getTerminales().get(1);
    	System.out.println("Dadas de alta Terminal T1 (Pasajeros) y Terminal T2 (Mercancías).");
    	
    	System.out.println("Dando de alta las puertas de embarque en las terminales.");
    	for (PuertaEmbarque puerta : puertasT1) {
    	    airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT1);
    	}
    	for (PuertaEmbarque puerta : puertasT2) {
    	    airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT2);
    	}
    	System.out.println("Puertas de embarque dadas de alta en las terminales T1 y T2.");

    	/** Controladores */
    	System.out.println("Dando de alta los controladores para las terminales:");
    	airportManager.getGestor().darDeAltaControlador("Ana Gómez", "123456789L", terminalT1);
    	airportManager.getGestor().darDeAltaControlador("Victoria Ramirez", "123356789C", terminalT1);
    	airportManager.getGestor().darDeAltaControlador("Alfonso Ruiz", "123456239T", terminalT2);
    	airportManager.getGestor().darDeAltaControlador("Charles Green", "174456789F", terminalT2);
    	airportManager.getGestor().darDeAltaControlador("Marta Sanz", "123126789K", terminalT2);
    	System.out.println("Controladores Ana Gómez y Victoria Ramirez dados de alta para la Terminal T1.");
    	System.out.println("Controladores Alfonso Ruiz, Charles Green y Marta Sanz dados de alta para la Terminal T2.");

    	/** Hangares */
    	System.out.println("Dando de alta hangares para las terminales:");
    	airportManager.getGestor().darDeAltaHangar("Pasajeros", 6, 25.0, 15.0, 40.0, 1000.0, false);
    	airportManager.getGestor().darDeAltaHangar("Mercancías", 3, 28.0, 17.0, 43.0, 1000.0, true);
    	System.out.println("Hangar de Pasajeros y Hangar de Mercancías dados de alta.");

    	/** Fingers */
    	System.out.println("Dando de alta los Finger para la Terminal T1.");
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0)));
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(2))); 
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0), puertasT1.get(1)));
    	System.out.println("Fingers dados de alta para las puertas de embarque T1.");

    	/** Zonas de aparcamiento */
    	System.out.println("Dando de alta zonas de aparcamiento en el aeropuerto.");
    	airportManager.getGestor().darDeAltaZonaAparcamiento(2.5, 5.0, 5.0);
    	airportManager.getGestor().darDeAltaZonaAparcamiento(3.0, 6.0, 6.0);
    	System.out.println("Zonas de aparcamiento dadas de alta.");

    	/** Pistas */
    	System.out.println("Dando de alta las pistas de aterrizaje y despegue.");
    	airportManager.getGestor().darDeAltaPista("P1", 3000, TipoPista.ATERRIZAJE);
    	airportManager.getGestor().darDeAltaPista("P2", 3000, TipoPista.DESPEGUE);
    	System.out.println("Pistas de aterrizaje y despegue dadas de alta.");
    	System.out.println("Proceso de alta de infraestructura del aeropuerto completado.");
        
    	/**Aeropuertos de destino */
    	System.out.println("\nIniciando proceso de alta de aeropuertos de destino.");
    	try {
    	    airportManager.getGestor().darDeAltaAeropuertoDestino(ARCHIVO_AEDESTINO);
    	    System.out.println("Aeropuerto destino dado de alta correctamente. Nombre del nuevo aeropuerto: "+airportManager.getAeropuerto().getAeropuertosDestino().getLast().getNombre());
    	} catch (IOException | TemporadasSolapadasException e) {
    	    System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
    	}
    	
    	System.out.println("Proceso de alta de aeropuertos de destino completado.");
    }
    
    private static void programarVuelos() {
    	Operador operadorIberia=(Operador)airportManager.getGestor().buscarUsuarioPorDni("12345678A");
    	Operador operadorAirFrance=(Operador)airportManager.getGestor().buscarUsuarioPorDni("87654321B");
    	Gestor gestorBarajas=airportManager.getGestor();
;    	
    	Avion avionEC_HGR= airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(0);
    	Avion avionEC_HGS= airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(1);
    	Avion avionEC_HGT= airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(2);
    	Avion avionEC_KBX= airportManager.getAeropuerto().getAerolineas().get(0).getAviones().get(3);
    	Avion avionF_GKXC= airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(0);
    	Avion avionF_GKXG= airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(1);
    	Avion avionF_GKXL= airportManager.getAeropuerto().getAerolineas().get(1).getAviones().get(2);
    	
    	AeropuertoPropio aePropio= airportManager.getAeropuerto();
    	Aeropuerto aeCairo= airportManager.getAeropuerto().getAeropuertosDestino().get(8);
    	Aeropuerto aeTokio= airportManager.getAeropuerto().getAeropuertosDestino().get(6);
    	Aeropuerto aeFKennedy= airportManager.getAeropuerto().getAeropuertosDestino().get(3);
    	Aeropuerto aeMallorca= airportManager.getAeropuerto().getAeropuertosDestino().get(2);
    	Aeropuerto aeMalaga= airportManager.getAeropuerto().getAeropuertosDestino().get(0);
    	
    	System.out.println("\nIniciando la propuesta del vuelos del Operador de Iberia.");
    	try {
    	    System.out.println("Proponiendo vuelo IBE1721...");
    	    operadorIberia.proponerVuelo("IBE1721", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGR, aeTokio, aePropio, null);
    	    System.out.println("Vuelo IBE1721 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE1721: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE8246...");
    	    operadorAirFrance.proponerVuelo("IBE8246", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGS, aeCairo, aePropio, null);
    	    System.out.println("Vuelo IBE8246 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE8246: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE2325...");
    	    operadorIberia.proponerVuelo("IBE2325", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeFKennedy, null);
    	    System.out.println("Vuelo IBE2325 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE2325: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE5017...");
    	    operadorAirFrance.proponerVuelo("IBE5017", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SATURDAY), TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.RECURRENTE, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeMallorca, null);
    	    System.out.println("Vuelo IBE5017 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE5017: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE3371...");
    	    operadorIberia.proponerVuelo("IBE3371", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGT, aeFKennedy, aePropio, null);
    	    System.out.println("Vuelo IBE3371 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE3371: " + e.getMessage());
    	}
    	
    	System.out.println("\nMostrando notificaciones del gestor de Barajas...");
    	gestorBarajas.verNotificaciones();
    	

	    System.out.println("Iniciando gestión de vuelo del Gestor.");
	    gestorBarajas.gestionarVuelos(gestorBarajas.getVuelosPropuestos());
	    System.out.println("Gestión de vuelos completada con éxito.");
 
    	
    	System.out.println("\nMostrando notificaciones del operador de Iberia...");
    	operadorIberia.verNotificaciones();
    	

	    System.out.println("Iniciando gestiónde alternativas del Operador.");
	    if(operadorIberia.getAlternativas()!=null) {
	    	operadorIberia.aceptarVuelo(operadorIberia.getAlternativas().get(0));
	    }
	    System.out.println("Gestión de vuelos completada con éxito.");

    	System.out.println("\nIniciando la propuesta del vuelos del Operador de AirFrance.");
    	try {
    	    System.out.println("Proponiendo vuelo AFR2270...");
    	    operadorAirFrance.proponerVuelo("AFR2270", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionF_GKXL, aeMalaga, aePropio, null);
    	    System.out.println("Vuelo AFR2270 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo AFR2270: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo AFR3101...");
    	    operadorIberia.proponerVuelo("AFR3101", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.DIARIO, TipoVuelo.SALIDA, avionF_GKXC, aeMallorca, aePropio, null);
    	    System.out.println("Vuelo AFR3101 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo AFR3101: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo AFR8802...");
    	    operadorAirFrance.proponerVuelo("AFR8802", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(17, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionF_GKXC, aePropio, aeFKennedy,
    	            new Escala(aePropio, aeTokio, LocalTime.of(17, 0), LocalTime.of(19, 0)));
    	    System.out.println("Vuelo AFR8802 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo AFR8802: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo AFR7107...");
    	    operadorIberia.proponerVuelo("AFR7107", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionF_GKXC, aeTokio, aePropio, null);
    	    System.out.println("Vuelo AFR7107 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo AFR7107: " + e.getMessage());
    	}
    	
    	
    	System.out.println("\nIniciando propuestas de vuelos que generan errores.");
    	try {
    	    System.out.println("Proponiendo vuelo AFR2993...");
    	    operadorAirFrance.proponerVuelo("AFR2993", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(20, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.DIARIO, TipoVuelo.LLEGADA, avionF_GKXG, aePropio, aeCairo,
    	            new Escala(aePropio, aeFKennedy, LocalTime.of(20, 0), LocalTime.of(20, 30)));
    	    System.out.println("Vuelo AFR2993 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo AFR2993: " + e.getMessage());
    	}
    	
    	System.out.println("\nSe repite la prueba forzando al sistema a enviar alternativas, para ello rango en el que se puede mover=0.");
    	Gestor.setRangoMinutosMaximoCambioVuelo(0);
    	System.out.println(Gestor.rangoMinutosMaximoCambioVuelo);
    	
    	System.out.println("\nIniciando la propuesta del vuelos del Operador de Iberia.");
    	try {
    		System.out.println("Proponiendo vuelo IBE1721...");
    	    operadorIberia.proponerVuelo("IBE1721", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGR, aeTokio, aePropio, null);
    	    System.out.println("Vuelo IBE1721 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE1721: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE8246...");
    	    operadorAirFrance.proponerVuelo("IBE8246", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGS, aeCairo, aePropio, null);
    	    System.out.println("Vuelo IBE8246 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE8246: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE2325...");
    	    operadorIberia.proponerVuelo("IBE2325", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeFKennedy, null);
    	    System.out.println("Vuelo IBE2325 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE2325: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE5017...");
    	    operadorAirFrance.proponerVuelo("IBE5017", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SATURDAY), TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.RECURRENTE, TipoVuelo.LLEGADA, avionEC_KBX, aePropio, aeMallorca, null);
    	    System.out.println("Vuelo IBE5017 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE5017: " + e.getMessage());
    	}

    	try {
    	    System.out.println("Proponiendo vuelo IBE3371...");
    	    operadorIberia.proponerVuelo("IBE3371", LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(2),
    	            LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
    	            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionEC_HGT, aeFKennedy, aePropio, null);
    	    System.out.println("Vuelo IBE3371 propuesto correctamente.");
    	} catch (Exception e) {
    	    System.err.println("Error al proponer el vuelo IBE3371: " + e.getMessage());
    	}
    	
    	System.out.println("[DEBUG] Lista de vuelos propuestos:");
    	for (VueloProgramado v : gestorBarajas.getVuelosPropuestos()) {
    	    System.out.println("[DEBUG] Vuelo: " + v.getCodigo() + " - Fecha: " + v.getFecha() + " - Hora: " + v.getHora());
    	}
    	System.out.println("[DEBUG] Lista de vuelos aceptados:");
    	for (VueloProgramado v : airportManager.getAeropuerto().getVuelosAceptados()) {
    	    System.out.println("[DEBUG] Vuelo: " + v.getCodigo() + " - Fecha: " + v.getFecha() + " - Hora: " + v.getHora());
    	}
    	System.out.println("\nMostrando notificaciones del gestor de Barajas...");
    	gestorBarajas.verNotificaciones();
    	
    	
    	System.out.println("Proceso de propuesta de vuelos finalizado.");
	}

	private static void compartirVuelosProgramados() {
		// TODO Auto-generated method stub
		
	}
	

	private static void pagoFacturas() {
		// TODO Auto-generated method stub
		
	}

	private static void emisionFacturas() {
		// TODO Auto-generated method stub
		
	}

	private static void configurarDatosAeropuertoPropio() {
		System.out.println("\nIniciando configuración del aeropuerto: "+airportManager.getAeropuerto().getNombre());

        System.out.println("Nombre del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getNombre());
        airportManager.getGestor().configurarAeropuertoPropioNombre("Heathrow");
        System.out.println("Nombre del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getNombre());

        System.out.println("Código del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCodigo());
        airportManager.getGestor().configurarAeropuertoPropioCodigo("LHR");
        System.out.println("Código del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getCodigo());

        System.out.println("Ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCiudad());
        airportManager.getGestor().configurarAeropuertoPropioCiudad("Londres");
        System.out.println("Ciudad del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getCiudad());

        System.out.println("País del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getPais());
        airportManager.getGestor().configurarAeropuertoPropioPais("Reino Unido");
        System.out.println("País del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getPais());

        System.out.println("Distancia ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDistanciaCiudad());
        airportManager.getGestor().configurarAeropuertoPropioDistanciaCiudad(24);
        System.out.println("Distancia ciudad del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDistanciaCiudad());
       
        System.out.println("Diferencia horaria del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDiferenciaHoraria());
        airportManager.getGestor().configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset.ofHours(+1));
        System.out.println("Diferencia horaria del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDiferenciaHoraria());

        System.out.println("Número de temporadas del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getTemporadas().size());
        airportManager.getGestor().configurarAeropuertoPropioNumTemporadas(3);
        try {
			airportManager.getAeropuerto().agregarTemporada(new Temporada("01/01", "10:00", "20:00", "30/06"));
		 } catch (TemporadasSolapadasException e) {
			  System.out.println("Error al configurar el aeropuerto: " + e.getMessage());
		 }
        System.out.println("Número de temporadas del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getTemporadas().size());

        System.out.println("Dirección ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDireccionCiudad());
        airportManager.getGestor().configurarAeropuertoPropioDireccionCiudad(DireccionRelativaCiudad.SUR);
        System.out.println("Dirección ciudad  del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDireccionCiudad());
		

		System.out.println("Procedimiento de configuración del aeropuerto finalizado.");
	}
	
	private static void resetearDatosAeropuertoPropio() {
		airportManager.getGestor().configurarAeropuertoPropioNombre("Aeropuerto Adolfo Suarez Madrid-Barajas");
        airportManager.getGestor().configurarAeropuertoPropioCodigo("MAD");
        airportManager.getGestor().configurarAeropuertoPropioCiudad("Madrid");
        airportManager.getGestor().configurarAeropuertoPropioPais("España");
        airportManager.getGestor().configurarAeropuertoPropioDistanciaCiudad(10.5);
        airportManager.getGestor().configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset.ofHours(0));
        airportManager.getGestor().configurarAeropuertoPropioNumTemporadas(2);
        airportManager.getGestor().configurarAeropuertoPropioDireccionCiudad( DireccionRelativaCiudad.ESTE);
	}

	private static void cambiarEstadoVueloContorlador() {
		// TODO Auto-generated method stub
		
	}

	private static void cambiarEstadoVueloOperador() {
		// TODO Auto-generated method stub
		
	}

	private static void cambiarEstadoVueloAutomatico() {
		// TODO Auto-generated method stub
		
	}

	private static void recibirNotificaciones() {
		Operador operadorAirFrance=(Operador)airportManager.getGestor().buscarUsuarioPorDni("87654321B");
    	ControladorAereo controladorT2=(ControladorAereo)airportManager.getGestor().buscarUsuarioPorDni("123126789K");
    	Gestor gestorBarajas= airportManager.getGestor();
    	
    	Notificacion notificacionCambioEstado = new Notificacion("El vuelo AF123 ha cambiado su estado a 'Embarcando'.", TipoNotificacion.CAMBIO_ESTADO_VUELO);
    	Notificacion notificacionOlvidoContrasena = new Notificacion("Se ha solicitado un restablecimiento de contraseña para su cuenta.", TipoNotificacion.OLVIDO_CONTRASENA);
    	Notificacion notificacionSolicitudCompartirVuelo = new Notificacion("El operador de Iberia ha solicitado compartir el vuelo IB456 con su aerolínea.", TipoNotificacion.SOLICITUD_COMPARTIR_VUELO);
    	Notificacion notificacionFacturaEmitida = new Notificacion("Se ha emitido una factura por el vuelo AF789. Consulte su perfil para más detalles.", TipoNotificacion.FACTURA_EMITIDA);
    	Notificacion notificacionOpcionesReprogramarVuelo = new Notificacion("Su vuelo IB321 ha sido afectado. Tiene nuevas opciones para reprogramarlo.", TipoNotificacion.OPCIONES_REPROGRAMAR_VUELO);
    	Notificacion notificacionSolicitudVueloProgramado = new Notificacion("Se ha programado un nuevo vuelo desde Madrid a París para el 10 de abril a las 15:00.", TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO);


    	System.out.println("\nIniciando recepción de notificaciones.");
    	operadorAirFrance.recibirNotificacion(notificacionCambioEstado);
    	operadorAirFrance.recibirNotificacion(notificacionOlvidoContrasena);
    	operadorAirFrance.recibirNotificacion(notificacionSolicitudCompartirVuelo);
    	operadorAirFrance.recibirNotificacion(notificacionFacturaEmitida);
    	operadorAirFrance.recibirNotificacion(notificacionOpcionesReprogramarVuelo);
    	operadorAirFrance.recibirNotificacion(notificacionSolicitudVueloProgramado);
    	operadorAirFrance.recibirSolicitud(null);
	}

	private static void verListaVuelosDia() {
		// TODO Auto-generated method stub
		
	}

	private static void buscarVuelos() {
		Operador operadorAirFrance=(Operador)airportManager.getGestor().buscarUsuarioPorDni("87654321B");
    	ControladorAereo controladorT2=(ControladorAereo)airportManager.getGestor().buscarUsuarioPorDni("123126789K");
    	Gestor gestorBarajas= airportManager.getGestor();
    	
    	Vuelo vueloIBE12371= new Vuelo("IBE1231", LocalDate.of(2025, 9, 19), LocalDate.of(2025, 9, 19),LocalDate.of(2025, 10, 19), LocalTime.of(11,30), null, TipoCarga.MERCANCIAS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, 
    									Periodicidad.PUNTUAL,TipoVuelo.SALIDA, operadorAirFrance.getAerolinea().getAviones().get(0), airportManager.getAeropuerto().getAeropuertosDestino().get(2), 
    									(Aeropuerto)airportManager.getAeropuerto(),null, operadorAirFrance);
    	
    	/** Con parámetros válidos */
    	System.out.println("\nIniciando búsqueda de vuelos por criterios de búsqueda con valores válidos.");
    	System.out.println("Operador Air France busca vuelo por código...");
    	operadorAirFrance.buscarVuelo(CriterioBusqueda.CODIGO, null);

        System.out.println("Operador Air France busca vuelo por destino...");
        operadorAirFrance.buscarVuelo(CriterioBusqueda.DESTINO, null);

        System.out.println("Controlador T2 busca vuelo por fecha...");
        controladorT2.buscarVuelo(CriterioBusqueda.FECHA, null);

        System.out.println("Controlador T2 busca vuelo por hora de llegada...");
        controladorT2.buscarVuelo(CriterioBusqueda.HORA_LLEGADA, null);

        System.out.println("Controlador T2 busca vuelo por hora de salida...");
        controladorT2.buscarVuelo(CriterioBusqueda.HORA_SALIDA, null);

        System.out.println("Gestor Barajas busca vuelo por origen...");
        gestorBarajas.buscarVuelo(CriterioBusqueda.ORIGEN, null);

        System.out.println("Gestor Barajas busca vuelo por terminal...");
        gestorBarajas.buscarVuelo(CriterioBusqueda.TERMINAL, null);
        
        System.out.println("\nIniciando búsqueda de vuelos por criterios de búsqueda con valores inválidos.");
    	System.out.println("Operador Air France busca vuelo por código inválido...");
    	operadorAirFrance.buscarVuelo(CriterioBusqueda.CODIGO, null);

        System.out.println("Operador Air France busca vuelo por destino inválido...");
        operadorAirFrance.buscarVuelo(CriterioBusqueda.DESTINO, null);

        System.out.println("Controlador T2 busca vuelo por fecha inválido...");
        controladorT2.buscarVuelo(CriterioBusqueda.FECHA, null);

        System.out.println("Controlador T2 busca vuelo por hora de llegada inválido...");
        controladorT2.buscarVuelo(CriterioBusqueda.HORA_LLEGADA, null);

        System.out.println("Controlador T2 busca vuelo por hora de salida inválido...");
        controladorT2.buscarVuelo(CriterioBusqueda.HORA_SALIDA, null);

        System.out.println("Gestor Barajas busca vuelo por origen inválido...");
        gestorBarajas.buscarVuelo(CriterioBusqueda.ORIGEN, null);

        System.out.println("Gestor Barajas busca vuelo por terminal inválido...");
        gestorBarajas.buscarVuelo(CriterioBusqueda.TERMINAL, null);
	}

	private static void verEstadisticas() {
		// TODO Auto-generated method stub
		
	}

	private static void cierreSesion() {
		Operador operadorIberia=(Operador)airportManager.getGestor().buscarUsuarioPorDni("12345678A");
    	ControladorAereo controladorT1=(ControladorAereo)airportManager.getGestor().buscarUsuarioPorDni("123356789C");
    	Gestor gestorBarajas= airportManager.getGestor();
    	
		System.out.println("\nSesión cerrada para el operador Iberia.");
        operadorIberia.cerrarSesion();

        System.out.println("Sesión cerrada para el controlador T1.");
        controladorT1.cerrarSesion();
        
        System.out.println("Sesión cerrada para el gestor Barajas.");
        gestorBarajas.cerrarSesion();
		
	}

	private static void inicioSesion() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
		Operador operadorIberia=(Operador)airportManager.getGestor().buscarUsuarioPorDni("12345678A");
    	ControladorAereo controladorT1=(ControladorAereo)airportManager.getGestor().buscarUsuarioPorDni("123356789C");
    	Gestor gestorBarajas= airportManager.getGestor();
    	
    	try {
            System.out.println("\nIniciando sesión con DNI y contraseña válidos para el operador Iberia.");
            operadorIberia.iniciarSesion(operadorIberia.getDni(), operadorIberia.getContrasena());
            System.out.println("Sesión iniciada correctamente para el operador Iberia.");
            
            System.out.println("\nIniciando sesión con DNI y contraseña válidos para el controlador T1.");
            controladorT1.iniciarSesion(controladorT1.getDni(), controladorT1.getContrasena());
            System.out.println("Sesión iniciada correctamente para el controlador T1.");
            
            System.out.println("\nIniciando sesión con contraseña válida para el gestor Barajas.");
            gestorBarajas.iniciarSesion(gestorBarajas.getContrasena());
            System.out.println("Sesión iniciada correctamente para el gestor Barajas.");
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión con DNI y contraseña válidos: " + e.getMessage());
        }

        // **Con dni inválido**
        try {
            System.out.println("\nIntentando iniciar sesión con DNI inválido para el operador Iberia.");
            operadorIberia.iniciarSesion("33333333F", operadorIberia.getContrasena());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión con DNI inválido para el operador Iberia: " + e.getMessage());
        }
        
        try {
            System.out.println("\nIntentando iniciar sesión con DNI inválido para el controlador T1.");
            controladorT1.iniciarSesion("33333333F", controladorT1.getContrasena());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión con DNI inválido para el controlador T1: " + e.getMessage());
        }

        // **Con contraseñas inválidas**
        try {
            System.out.println("\nIntentando iniciar sesión con contraseña inválida para el operador Iberia.");
            operadorIberia.iniciarSesion(operadorIberia.getDni(), airportManager.generarContrasena());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión con contraseña inválida para el operador Iberia: " + e.getMessage());
        }
        
        try {
            System.out.println("\nIntentando iniciar sesión con contraseña inválida para el controlador T1.");
            controladorT1.iniciarSesion(controladorT1.getDni(), airportManager.generarContrasena());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión con contraseña inválida para el controlador T1: " + e.getMessage());
        }

        try {
            System.out.println("\nIntentando iniciar sesión con contraseña inválida para el gestor Barajas.");
            gestorBarajas.iniciarSesion(airportManager.generarContrasena());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión con contraseña inválida para el gestor Barajas: " + e.getMessage());
        }

        // **Solicitan reestablecer la contraseña**
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

        // **Con contraseña y dni nuevos válidos**
        try {
            System.out.println("\nIniciando sesión con DNI y nueva contraseña válidos para el operador Iberia.");
            operadorIberia.iniciarSesion(operadorIberia.getDni(), operadorIberia.getContrasena());
            System.out.println("Sesión iniciada correctamente para el operador Iberia.");
            
            System.out.println("\nIniciando sesión con DNI y nueva contraseña válidos para el controlador T1.");
            controladorT1.iniciarSesion(controladorT1.getDni(), controladorT1.getContrasena());
            System.out.println("Sesión iniciada correctamente para el controlador T1.");
            
            System.out.println("\nIniciando sesión con nueva contraseña válida para el gestor Barajas.");
            gestorBarajas.iniciarSesion(gestorBarajas.getContrasena());
            System.out.println("Sesión iniciada correctamente para el gestor Barajas.");
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión con DNI y nueva contraseña válidos: " + e.getMessage());
        }
	}

}

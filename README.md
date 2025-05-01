# ✈️ ENTREGA 3.2 - INTERFAZ GRÁFICA

Proyecto que implementa la interfaz gráfica para un software de gestión de un aeropuerto.
---
## Estructura del proyecto

/src 
	/airportmanager
		/aeropuerto
		/avion
		/estadisticas
		/excepciones
		/factura
		/hangar
		/notificacion
		/pista
		/terminal
		/usuario
		/vuelo
	/gui
		/controlador
		/operador
		/gestor
	/test
/resources
/doc
/facturas

---

## Ejecución del programa

1. Descargar el proyecto
2. Ejecutar la interfaz. La interfaz se ejecuta desde el archivo Main.java, que carga los datos de prueba_interfaz.dat. Si se desea cargar otro fichero, incluirlo en /resources y modificar la variable ARCHIVO_ESTADO de Main.java.
NOTA: hay otro .dat, estado_airport_manager.dat donde se carga AiportManagerMain.java que es un main que se ejecuta unicamente en la terminal, se puede cargar ese estado también.
3. Probar la interfaz. A continuación se describen posibles acciones para probar la interfaz.	
	
## 3.1. VENTANA PRINCIPAL:
		- INICIO DE SESIÓN:
			usuario: 12345678A		tipo: Operador (Iberia)		 contraseña: UsuarioNuevo
			usuario: 87654321B		tipo: Operador (AirFrance)		 contraseña: (Establecer la primera vez que se acceda)
			usuario: 50738890L		tipo: Operador (Ryanair)		 contraseña: SoyOperador
			usuario: 12345678L		tipo: Controlador (T1)		 	 contraseña: SoyControlador
			usuario: 12335678C		tipo: Controlador (T1)		 	 contraseña: UsuarioNuevo
			usuario: 12345623T		tipo: Controlador (T2)			 contraseña: SoyControlador
			usuario: 17445678F		tipo: Controlador (T2)			 contraseña: SoyControlador
			usuario: 12312678K		tipo: Controlador (T2)			 contraseña: SoyControlador
			
			En general, al crear los usuarios se estableceran las contraseñas "SoyControlador" y "SoyGestor", y al 
			generar una nueva contraseña "SoyNuevoGestor" o "UsuarioNuevo".
			
		- RESTABLECER CONTRASEÑA:
			Pulsar el botón y obtener nueva contraseña.
		- CERRAR EL PROGRAMA: cerrando la ventana. 
		
## 3.2 VENTANA DEL CONTROLADOR:
		- NOTIFICACIONES:
			Ver notificaciones.
		- CAMBIO DE ESTADO:
			Primero se deben programar los vuelos.
			(Controlador con dni=12345678L)(Si al crearlos no se le han asignado a este, al que se le hayan asignado).
			- código= "IBE1235",
			- estado= ESPERANDO_PISTA_DE_DESPEGUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1235",
			- estado= ESPERANDO_DESPEGUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos 2 veces.
			
			- código= "IBE1234",
			- estado= ESPERANDO_PISTA_DE_ATERRIZAJE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1234",
			- estado= ESPERANDO_ATERRIZAJE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1234",
			- estado= ATERRIZAJE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			
			(Primero hay que cambiar el desembarque desde el operador)
			- código= "IBE1234",
			- estado= OPERATIVO.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1234",
			- estado= AVION_A_HANGAR.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			
## 3.3 VENTANA DEL OPERADOR:
		- NOTIFICACIONES:
			Ver notificaciones.
		- CAMBIO DE ESTADO:
			Primero se deben programar los vuelos.
			(Operador con dni=50738890L)
			- código= "IBE1235",
			- estado= EN_PREPARACION_PARA_DESPEGAR.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1235",
			- estado= INICIO_DEL_EMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1235",
			- estado= FIN_DEL_EMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			
			(Controlador con dni=12345678A)
			- código= "IBE1234",
			- estado= INICIO_DESEMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "IBE1234",
			- estado= FIN_DESEMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			
		- DAR DE ALTA AVIÓN:
			- pasajeros: 
				- marca= "Boeing",
				- modelo= "737",
				- alto= "15",
				- ancho= "30.5",
				- largo= "60",
				- autonomía= "5000",
				- año de compra= "2024",
				- fecha de última revisión= "01/01/2025"
				- matrícula= "EC-HJK",
				- tipo de avión= "Pasajeros"
				- capacidad pasajeros= "60".
			- mercancías:
			 	- marca= "Airbus",
				- modelo= "300",
				- alto= "18",
				- ancho= "35",
				- largo= "80.5",
				- autonomía= "10000",
				- año de compra= "2025",
				- fecha de última revisión= "01/03/2025"
				- matrícula= "EC-HLP",
				- tipo de avión= "Mercancías"
				- capacidad carga= "20000".
				- mercancías peligrosas: (Seleccionar),
				- control de temperatura: (Seleccionar).
				
		- PROGRAMAR UN VUELO:
		Se recomienda programar 6 vuelos a la misma hora para ver como se gestionan.
		NOTA: La antelación mínima para programar vuelos está puesta a 0 días para poder programar vuelos y probar el cambio de estado de estos.
		(Controlador con dni=12345678A)
			Vuelo1:
			- código= "IBE1234",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio= "(día de hoy yyyy-mm-dd)",
			- fecha fin= "(día de hoy yyyy-mm-dd)",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "pasajeros",
			- modo embarque= "finger",
			- periodidicad= "puntual",
			- tipo de vuelo= "llegada",
			- avión=  "EC-HGR",
			- aeropuerto de salida/llegada= "Kennedy",
			- escala= "El Cairo", "(Dentro de 20 minutos HH:mm)", "(Dentro de 2 horas y 20 minutos HH:mm)".
			
			Vuelo2:
			- código= "IBE1235",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio= "(día de hoy yyyy-mm-dd)",
			- fecha fin= "(día de hoy yyyy-mm-dd)",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "pasajeros",
			- modo embarque= "zona de aparcamiento",
			- periodidicad= "puntual",
			- tipo de vuelo= "salida",
			- avión= "IB_HGC",
			- aeropuerto de salida/llegada= "Málaga".
			
			Vuelo3:
			- código= "IBE1236",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio= "2025-05-04",
			- fecha fin= "2025-05-06",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "mercancias",
			- modo embarque= "zona de aparcamiento",
			- periodidicad= "recurrente",
			- tipo de vuelo= "salida",
			- avión=  "IB_HGS",
			- aeropuerto de salida/llegada= "Málaga",
			- días recurrente= (Seleccionar martes y jueves).

    		Vuelo4:
			- código= "IBE1237",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio="(día de hoy yyyy-mm-dd)",
			- fecha fin="(día de hoy yyyy-mm-dd)",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "mercancias",
			- modo embarque= "zona de aparcamiento",
			- periodidicad= "puntual",
			- tipo de vuelo= "salida",
			- avión=  "EC-KBX",
			- aeropuerto de salida/llegada= "El Cairo".
			
			Vuelo5:
			- código= "IBE1238",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio= "(día de hoy yyyy-mm-dd)",
			- fecha fin= "(día de hoy yyyy-mm-dd)",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "pasajeros",
			- modo embarque= "zona de aparcamiento",
			- periodidicad= "puntual",
			- tipo de vuelo= "salida",
			- avión= "IB_HGC",
			- aeropuerto de salida/llegada= "El part".
			
			Vuelo6:
			- código= "IBE1231",
			- fecha= "(día de hoy yyyy-mm-dd)",
			- fecha inicio= "(día de hoy yyyy-mm-dd)",
			- fecha fin= "(día de hoy yyyy-mm-dd)",
			- hora= "(Dentro de 20 minutos HH:mm)",
			- tipo carga= "pasajeros",
			- modo embarque= "zona de aparcamiento",
			- periodidicad= "puntual",
			- tipo de vuelo= "salida",
			- avión= "IB_HGC",
			- aeropuerto de salida/llegada= "Mallorca".
			
		- COMPARTIR UN VUELO:
			Antes haber solicitado compartir el vuelo.
			(Controlador con dni=12345678A)
			- código= "IBE1238", ACEPTAR.
			
		- SOLICITAR COMPARTIR VUELO:
			(Operador con dni=50738890L)
			- código= "IBE1234",
			- porcentaje= "20".
			
		- ESTADÍSTICAS DE LOS VUELOS:
			Ver estadísticas y seleccionar, mes, franja o vuelos.
			
		- ALTERNATIVAS A LOS VUELOS PROPUESTOS:
			Para aceptar se debe introducir el código de la aternativa, sin embarqo, para rechazar se debe introducir el código del vuelo al que se le han generado 				alternativas. Entonces se podría hacer:
			- código= "(código alternativa)", ACEPTAR.
			- código= "(código vuelo)", RECHAZAR.
			
		- PAGAR FACTURAS AEROLÍNEA:
			(Generar primero la factura desde el gestor)
			(Operador con dni=12345678A)
			- código= "IBE000001",
			- número de tarjeta= (Número de 16 dígitos).
			Refrescar para verificar que se ha pagado.
	
## 3.4 VENTANA DEL GESTOR:
		- NOTIFICACIONES: 
			Seleccionar los tipos que se quieren recibir.
			
		- DAR DE ALTA HANGAR:
			- mercancías:
				- tipo= mercancias,
				- numero aviones= "6",
				- dimAlto= "1500",
				- dimLargo= "4500",
				- dimAncho="2500",
				- coste="1000".
			- pasajeros:
				- tipo= pasajeros,
				- numero aviones= "6",
				- dimAlto= "1500",
				- dimLargo= "4500",
				- dimAncho="2500",
				- coste="1000".
				
		- DAR DE ALTA TERMINAL:
			- mercancías:
				- código= "T3",
				- puertas de embarque= (Pulsar 3 veces el botón de puertas de embarque) "P9,P10,P11".
			- pasajeros:
				- código= "T4",
				- puertas de embarque= (Pulsar 2 veces el botón de puertas de embarque) "P12,P13".
				
		- DAR DE ALTA FINGER:
			- altura= "5",
			- coste= "200",
			- puertas embarque= "P3".
			
		- DAR DE ALTA ZONA DE APARCAMIENTO:
			- coste= "100",
			- dimAlto= "1500",
			- dimLargo= "4500",
			- puertas embarque= "P1,P2,P5".
			
		- DAR DE ALTA PISTA DESPEGUE:
			- código= "P3",
			- longitud= "3000".
			
		- DAR DE ALTA PISTA ATERRIZAJE:
			- código= "P4",
			- longitud= "2000".
			
		- DAR DE ALTA AEROLÍNEA:
			- nombre: "Vueling",
			- código= "VUE".
			
		- DAR DE ALTA AEROPUERTO DE DESTINO:
			- nombre fichero= "resources/otro_aeropuerto_destino.txt".
			
		- DAR DE ALTA CONTROLADOR:
			- nombre: "Juan Suárez",
			- dni: "57392734K",
			- contraseña: "SoyControlador",
			- terminal: "T3".
			
		- DAR DE ALTA OPERADOR:
			- nombre: "Luis López",
			- dni: "27839181J",
			- aerolínea: "Vueling".
			
		- CONFIGURACIÓN DEL AEROPUERTO PROPIO:
			- nombre= "Heatrhtow",
			- codigo= "LHR",
			- país= "Reino Unido",
			- ciudad= "Londres",
			- distancia ciudad= "24",
			- direccion ciudad= "Sur",
			- diferencia horaria= "+1",
			- Agregar temporada (si lo marcas:)
				- Fecha de inicio: "01/06"
				- Fecha de fin: "02/11"
				- Horario de apertura: "09:30"
				- Horario de cierre: "23:45"
			Reestablecer los datos iniciales:
			- Nombre: "Aeropuerto Adolfo Suárez Madrid Barajas"
			- Código: "MAD"
			- Ciudad más cercana: "Madrid"
			- País: "España"
			- Distancia a la ciudad más cercana: "10.5"
			- Dirección relativa a la ciudad: "Este"
			- Diferencia horaria: "0"
			- Agregar temporada (si lo marcas:)
				- Fecha de inicio: "01/06"
				- Fecha de fin: "02/11"
				- Horario de apertura: "09:30"
				- Horario de cierre: "23:45"
				
		- ESTADÍSTICAS DE LA INFRAESTRUCTURA:
			Ver estadísticas.
		
		- GESTIÓN DE VUELOS:
			Primero se deben programar los vuelos desde algún operador.
			- código= "IBE1234",GESTIONAR.
			- código= "IBE1235",GESTIONAR.
			- código= "IBE1236-2025-05-01", GESTIONAR.
			(Los vuelos recurrentes tienen el mismo dódigo acompañado de la fecha para que sea más sencillo identificarlos).
			- código= "IBE1237", GESTIONAR.
		
		- GESTIÓN DE FACTURAS:
			Selecciona Iberia y emite la factura.
			
		- VER INFORMACIÓN DEL AEROPUERTO PROPIO:
			Consultar vuelos, infraestructura y usuarios del aeropuerto.
	
## 3.5 COMÚN TODOS LOS USUARIOS:
		- BÚSQUEDA DE VUELOS POR CRITERIO:
			código: "IBE1721", 
			origen= "España", 
			destino= "España", 
			fecha= "(fecha de hoy formato dd/mm/yyyy)", 
			hora de llegada= "10:00", 
			hora de salida= "10:00", 
			terminal= "T1".
		- REFRESCAR LISTA VUELOS: botón refrescar.
		- CIERRE DE SESIÓN: botón cerrar sesión.
	

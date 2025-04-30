# ✈️ ENTREGA 3.2 - INTERFAZ GRÁFICA

Proyecto que implementa la interfaz gráfica para un software de gestión de un aeropuerto.
---

## Contenido del proyecto

- Autenticación de usuarios por rol.
- Interfaz gráfica para cada tipo de usuario.

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

## Requisitos del sistema

- Java 8 o superior
- IDE recomendado: Eclipse
- Sistema operativo: Windows / Linux / macOS

---

## Ejecución del programa

1. Descargar el proyecto
2. Ejecutar el archivo Main.java para que arranque la interfaz.
(Se incluye un archivo prueba_interfaz.dat con datos cargados para poder probar el funcionamiento).
3. Probar la interfaz. A continuación se describen posibles acciones para probar la interfaz.
	
	3.1. VENTANA PRINCIPAL:
		- INICIO DE SESIÓN:
			usuario: tipo: contraseña:
		- RESTABLECER CONTRASEÑA:
		- CERRAR EL PROGRAMA: cerrando la ventana. 
		
	3.2 VENTANA DEL CONTROLADOR:
		- NOTIFICACIONES:
			Ver notificaciones.
		- CAMBIO DE ESTADO:
			(Controlador con dni=12345678L)
			- código= "FRK2234",
			- estado= ESPERANDO_DESPEGUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos 2 veces.
			
	3.3 VENTANA DEL OPERADOR:
		- NOTIFICACIONES:
			Ver notificaciones.
		- CAMBIO DE ESTADO:
			- código= "FRK8106",
			- estado= EN_PREPARACION_PARA_DESPEGAR.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "FRK8106",
			- estado= INICIO_DEL_EMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			- código= "FRK8106",
			- estado= FIN_DEL_EMBARQUE.
			Después, volver a la pantalla principal y actualizar la lista de vuelos.
			
		- DAR DE ALTA AVIÓN:
			- mercancías:
			- pasajeros:
		- PROGRAMAR UN VUELO:
		- COMPARTIR UN VUELO:
		- SOLICITAR COMPARTIR VUELO:
		- ESTADÍSTICAS DE LOS VUELOS:
		- ALTERNATIVAS A LOS VUELOS PROPUESTOS:
		- PAGAR FACTURAS AEROLÍNEA:
	
	3.4 VENTANA DEL GESTOR:
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
			- nombre= " ",
			- codigo= " ",
			- país= " ",
			- ciudad= " ",
			- distancia ciudad= " ",
			- direccion ciudad= " ",
			- diferencia horaria= " ",
			- TEMPORADA: (" ", " ", " ", " ").
		- ESTADÍSTICAS DE LA INFRAESTRUCTURA:
			Ver estadísticas
		- GESTIÓN DE VUELOS:
			Primero se deben programar los vuelos desde algún operador.
			********FALTA********
		- GESTIÓN DE FACTURAS:
			Selecciona Iberia y emite la contraseña.
		- VER INFORMACIÓN DEL AEROPUERTO PROPIO:
			Consultar vuelos, infraestructura y usuarios del aeropuerto.
	
	3.5 COMÚN TODOS LOS USUARIOS:
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
	
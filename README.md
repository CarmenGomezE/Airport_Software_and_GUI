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
		- CAMBIO DE ESTADO:
	
	3.3 VENTANA DEL OPERADOR:
		- NOTIFICACIONES:
		- CAMBIO DE ESTADO:
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
		- DAR DE ALTA HANGAR:
			- mercancías:
				tipo= mercancias,
				n
			- pasajeros:
		- DAR DE ALTA TERMINAL:
			- mercancías:
			- pasajeros:
		- DAR DE ALTA FINGER:
			
		- DAR DE ALTA ZONA DE APARCAMIENTO:
		- DAR DE ALTA PISTA DESPEGUE:
		- DAR DE ALTA PISTA ATERRIZAJE:
		- DAR DE ALTA AEROLÍNEA:
		- DAR DE ALTA AEROPUERTO DE DESTINO:
			fichero:
		- DAR DE ALTA CONTROLADOR:
		- DAR DE ALTA OPERADOR:
		- CONFIGURACIÓN DEL AEROPUERTO PROPIO:
   			- Nombre: Aeropuerto Adolfo Suárez Madrid Barajas
   			- Código: MAD
   			- Ciudad más cercana: Madrid
   			- País: España
   			- Distancia a la ciudad más cercana: 10.5
   			- Dirección relativa a la ciudad: Este
   			- Diferencia horaria: 0
   			- Agregar temporada (si lo marcas:)
   				- Fecha de inicio: 01/06
   				- Fecha de fin: 02/11
   				- Horario de apertura: 09:30
   				- Horario de cierre: 23:45
   
   
		- ESTADÍSTICAS DE LA INFRAESTRUCTURA:
		- GESTIÓN DE VUELOS:
		- GESTIÓN DE FACTURAS:
		- VER INFORMACIÓN DEL AEROPUERTO PROPIO:
	
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
	

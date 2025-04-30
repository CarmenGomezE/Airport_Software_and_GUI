package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import src.airportmanager.notificacion.*;
import java.time.*;

/**
 * NotificacionTest
 */
class NotificacionTest{

    Notificacion noti1, noti2;
    Exception excepcionVacio, excepcionNull;
    LocalDateTime ahora;

    /**
	 * Constructor
	 */
	public NotificacionTest() {
		
	}
	
    /**
     * CreaObjetos
     */
    @BeforeEach
    void setUp() {
        noti1 = new Notificacion("Contraseña incorrecta", TipoNotificacion.OLVIDO_CONTRASENA);
        noti2 = new Notificacion("El vuelo se ha cambiado de estado", TipoNotificacion.CAMBIO_ESTADO_VUELO);

    }

    /**
     * GetMensaje
     */
    @Test
    void testGetMensaje() {
        assertEquals("Contraseña incorrecta", noti1.getMensaje(), "Error, el mensaje que se muestra no es el esperado.");
        assertEquals("El vuelo se ha cambiado de estado", noti2.getMensaje(), "Error, el mensaje que se muestra no es el esperado.");
    }

 
   /**
    * GetTipoNotificacion
    */
    @Test 
    void testGetTipoNotificacion() {
        assertEquals(TipoNotificacion.OLVIDO_CONTRASENA, noti1.getTipoNotificacion(), "Error el tipo de notificación no es correcto");
        assertEquals(TipoNotificacion.CAMBIO_ESTADO_VUELO, noti2.getTipoNotificacion(), "Error el tipo de notificación no es correcto");
    }
}
package aeropuerto.src.maria;

import aeropuerto.src.claudia.*;
/**
 * La clase ControladorAereo representa al usuario controlador de la aplicación
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class ControladorAereo extends UsuarioConDni {
	private Terminal terminal;
    /**
     * Constructor de la clase ControladorAereo.
     * 
     * @param nombre                Nombre del controlador aéreo.
     * @param dni                   Dni del controlador aéreo.
     * @param contraseña            Contraseña del controlador aéreo.
     */
    public ControladorAereo(String nombre, String dni, String contrasena) {
        super(nombre, dni, contrasena);
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     * @param estado    Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException 
     * @throws EstadoNoDelUsuarioException 
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (vuelos.contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario");
        }

        switch (estado) {
            case ESPERANDO_DESPEGUE:
            	cambiarEstadoEsperandoDespegue(vuelo);
            case ESPERANDO_PISTA_DE_DESPEGUE:
                cambiarEstadoEsperandoPistaDespegue(vuelo);
            case ESPERANDO_PISTA_DE_ATERRIZAJE:
                cambiarEstadoEsperandoPistaAterrizaje(vuelo);
            case ESPERANDO_ATERRIZAJE:
                cambiarEstadoEsperandoAterrizaje(vuelo);
            case ATERRIZAJE:
                cambiarEstadoAterrizaje(vuelo);
            case AVION_A_HANGAR:
                cambiarEstadoAHangar(vuelo);
            case OPERATIVO:
                cambiarEstadoOperativo(vuelo);
            default:
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado");
        }
    }

	private void cambiarEstadoOperativo(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoAHangar(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoAterrizaje(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoEsperandoAterrizaje(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoEsperandoPistaAterrizaje(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoEsperandoPistaDespegue(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	private void cambiarEstadoEsperandoDespegue(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
	}

	public Terminal getTerminal() {
		return this.terminal;
	}
} 
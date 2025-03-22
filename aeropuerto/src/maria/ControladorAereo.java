package aeropuerto.src.maria;
/**
 * La clase ControladorAereo representa al usuario controlador de la aplicación
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class ControladorAereo extends UsuarioConDni {
    /**
     * Constructor de la clase ControladorAereo.
     * 
     * @param nombre                Nombre del controlador aéreo.
     * @param dni                   Dni del controlador aéreo.
     * @param contraseña            Contraseña del controlador aéreo.
     */
    public ControladorAereo(String nombre, String dni, String Contraseña) {
        super(nombre, dni, contraseña);
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     * @param estado    Nuevo estado del vuelo.
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (vuelos.contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario");
        }

        switch (estado) {
            case ESPERANDO_DESPEGUE:
                return cambiarEstadoEsperandoDespegue(vuelo);
            case ESPERANDO_PISTA_DE_DESPEGUE:
                return cambiarEstadoEsperandoPistaDespegue(vuelo);
            case ESPERANDO_PISTA_DE_ATERRIZAJE:
                return cambiarEstadoEsperandoPistaAterrizaje(vuelo);
            case ESPERANDO_ATERRIZAJE:
                return cambiarEstadoEsperandoAterrizaje(vuelo);
            case ATERRIZAJE:
                return cambiarEstadoAterrizaje(vuelo);
            case AVION_A_HANGAR:
                return cambiarEstadoAHangar(vuelo);
            case OPERATIVO:
                return cambiarEstadoOperativo(vuelo);
            default:
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado");
        }
    }
} 
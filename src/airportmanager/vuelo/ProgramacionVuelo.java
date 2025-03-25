package src.airportmanager.vuelo;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramacionVuelo {
    private List<VueloProgramado> vuelosPropuestos = new ArrayList<>();
    private AeropuertoPropio aeropuerto;
    /** Dias de anticipación mínima para una propuesta de Vuelo. */
    private static int diasAnticipacionMinima = 30;
    /** Rango de minutos que como máximo se puede modificar una propuesta de Vuelo.*/
    public static int rangoMinutosMaximoCambioVuelo = 30;
    /** Horas máximas que se puede mover la reprogramación de una propuesta de Vuelo.*/
    private static int horasMaximasReprogramacion = 48;

    public ProgramacionVuelo(AeropuertoPropio aeropuerto, int diasAnticipacionMinima, int horasMaximasReprogramacion, int rangoMinutosMaximoCambioVuelo) {
        this.aeropuerto = aeropuerto;
        this.diasAnticipacionMinima = diasAnticipacionMinima;
        this.horasMaximasReprogramacion = horasMaximasReprogramacion;
        this.rangoMinutosMaximoCambioVuelo = rangoMinutosMaximoCambioVuelo;
    }

    public void recibirNotificacionProposicionVuelo(List<VueloProgramado> vuelosPropuestos) throws AntelacionInvalidaException {
        System.out.println("[INFO] Recibiendo notificación de vuelos propuestos...");
        for (VueloProgramado vuelo : vuelosPropuestos) {
            if (vuelo != null) {
                System.out.println("[INFO] Procesando vuelo: " + vuelo.getCodigo() + " con fecha " + vuelo.getFecha());
                if (!vuelo.getFecha().isBefore(LocalDate.now().plusDays(diasAnticipacionMinima))) {
                    throw new AntelacionInvalidaException("[ERROR] El vuelo " + vuelo.getCodigo() + " no cumple con la antelación mínima de días.");
                } else {
                    System.out.println("[INFO] El vuelo " + vuelo.getCodigo() + " cumple con la antelación mínima.");
                    this.vuelosPropuestos.add(vuelo);
                }
            }
        }
    }

    public void gestionarVuelos() throws VueloSolapadoException {
        System.out.println("[INFO] Gestionando vuelos propuestos...");
        for (VueloProgramado vuelo : vuelosPropuestos) {
            System.out.println("[INFO] Intentando programar vuelo: " + vuelo.getCodigo());
            try {
                if (puedeProgramarse(vuelo)) {
                    System.out.println("[INFO] Vuelo " + vuelo.getCodigo() + " programado con éxito.");
                    Vuelo nuevoVuelo = new Vuelo(vuelo.getCodigo(), vuelo.getFecha(), vuelo.getFechaInicio(), vuelo.getFechaFinal(),
                            vuelo.getHora(), vuelo.getDiasRecurrente(), vuelo.getTipoCarga(), vuelo.getTipoModoEmbarque(),
                            vuelo.getPeriodicidad(), vuelo.getTipoVuelo(), vuelo.getAvion(), vuelo.getAeropuertoDestino(),
                            vuelo.getAeropuertoSalida(), vuelo.getEscala(), vuelo.getAvion().getAerolinea().getOperador());

                    aeropuerto.addVueloAceptado(nuevoVuelo);
                } else {
                    System.out.println("[WARNING] No se pudo programar el vuelo " + vuelo.getCodigo());
                }
            } catch (VueloSolapadoException e) {
                System.out.println("[ERROR] Error al intentar programar el vuelo " + vuelo.getCodigo() + ": " + e.getMessage());
            }
        }
    }

    public boolean puedeProgramarse(VueloProgramado vuelo) throws VueloSolapadoException {
        System.out.println("[INFO] Comprobando si el vuelo " + vuelo.getCodigo() + " puede programarse...");
        boolean puedeProgramarse = true;
        for (Vuelo vueloExistente : aeropuerto.getVuelosAceptados()) {
            if (vuelosSeSolapan(vuelo, vueloExistente)) {
                System.out.println("[WARNING] El vuelo " + vuelo.getCodigo() + " se solapa con otro vuelo.");
                puedeProgramarse = false;
            }
        }
        return puedeProgramarse;
    }

    private boolean vuelosSeSolapan(VueloProgramado vuelo, Vuelo vueloExistente) {
        return vuelo.getFecha().equals(vueloExistente.getFecha()) && vuelo.getHora().equals(vueloExistente.getHora());
    }

	/**
	 * Método que establece el número mínimo de días de anticipación para las
	 * reservas.
	 * 
	 * @param nuevosDiasAnticipacionMinima El nuevo número de días de anticipación
	 *                                     mínima que se va a configurar.
	 */
	public static void setDiasAnticipacionMinima(int nuevosDiasAnticipacionMinima) {
	    diasAnticipacionMinima = nuevosDiasAnticipacionMinima;
	}
	/**
	 * Método que devuelve el tiempo que tiene el Operador para aceptar una
	 * propuesta de Vuelo.
	 * 
	 * @return Tiempo que tiene el Operador para aceptar una propuesta de Vuelo.
	 * 
	 */
	public static int getRangoMinutosMaximoCambioVuelo() {
	    return rangoMinutosMaximoCambioVuelo;
	}
	
	/**
	 * Método para establecer el tiempo que tiene el Operador para aceptar una
	 * propuesta de Vuelo.
	 * 
	 * @param nuevoTiempo Tiempo.
	 * 
	 */
	public static void setRangoMinutosMaximoCambioVuelo(int nuevoTiempo) {
		rangoMinutosMaximoCambioVuelo = nuevoTiempo;
	}
}

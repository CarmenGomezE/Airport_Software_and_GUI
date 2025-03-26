package src.airportmanager.usuario;

import java.util.*;



import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.exceptions.EstadoEquivocadoException;
import src.airportmanager.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.exceptions.NoFingerMercanciasException;
import src.airportmanager.exceptions.NoHangaresDisponiblesException;
import src.airportmanager.exceptions.NoModosPuertasEmbarqueDisponiblesException;
import src.airportmanager.exceptions.NoPistasDisponiblesException;
import src.airportmanager.exceptions.NoVueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.VueloEnMenosDe1HoraException;
import src.airportmanager.exceptions.VueloNoDelUsuarioException;
import src.airportmanager.hangar.Hangar;
import src.airportmanager.hangar.HangarMercancias;
import src.airportmanager.hangar.HangarPasajeros;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.terminal.Finger;
import src.airportmanager.terminal.ModoEmbarque;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.terminal.ZonaAparcamiento;
import src.airportmanager.vuelo.*;
import src.airportmanager.*;
import java.time.*;

/**
 * La clase ControladorAereo representa al usuario controlador de la aplicación
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class ControladorAereo extends UsuarioConDni {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Temrinal del controlador aéreo */
    private Terminal terminal;

    /**
     * Constructor de la clase ControladorAereo.
     * 
     * @param nombre     Nombre del controlador aéreo.
     * @param dni        Dni del controlador aéreo.
     * @param contraseña Contraseña del controlador aéreo.
     * @param gestor     Gestor del controlador aéreo.
     * @param terminal   Terminal del controlador aéreo.
     */
    public ControladorAereo(String nombre, String dni, String contrasena, AeropuertoPropio aeropuerto, Gestor gestor,
            Terminal terminal, AirportManager sistema) {
        super(nombre, contrasena, dni, aeropuerto, gestor, sistema);
        this.terminal = terminal;
    }

    /**
     * Método para obtener la terminal de un controlador aéreo.
     * 
     * @return La terminal del controlador aéreo.
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo  Vuelo al que se le cambia el estado.
     * @param estado Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException
     * @throws EstadoNoDelUsuarioException
     * @throws EstadoEquivocadoException
     * @throws NoPistasDisponiblesException
     * @throws IllegalArgumentException
     * @throws NoVueloEnMenosDe1HoraException
     * @throws VueloEnMenosDe1HoraException
     * @throws NoFingerMercanciasException
     * @throws NoModosPuertasEmbarqueDisponiblesException
     * @throws NoHangaresDisponiblesException
     * @throws CargaAvionEquivocadaException 
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado)
            throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException,
            EstadoEquivocadoException, NoPistasDisponiblesException, IllegalArgumentException,
            NoVueloEnMenosDe1HoraException,
            VueloEnMenosDe1HoraException, NoFingerMercanciasException, NoModosPuertasEmbarqueDisponiblesException,
            NoHangaresDisponiblesException, CargaAvionEquivocadaException {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null.");
        }

        if (vuelosQueControla.contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario.");
        }

        switch (estado) {
            case ESPERANDO_DESPEGUE:
                cambiarEstadoEsperandoDespegue(vuelo);
                break;
            case ESPERANDO_PISTA_DE_DESPEGUE:
                cambiarEstadoEsperandoPistaDespegue(vuelo);
                break;
            case ESPERANDO_PISTA_DE_ATERRIZAJE:
                cambiarEstadoEsperandoPistaAterrizaje(vuelo);
                break;
            case ESPERANDO_ATERRIZAJE:
                cambiarEstadoEsperandoAterrizaje(vuelo);
                break;
            case ATERRIZAJE:
                cambiarEstadoAterrizaje(vuelo);
                break;
            case AVION_A_HANGAR:
                cambiarEstadoAHangar(vuelo);
                break;
            case OPERATIVO:
                cambiarEstadoOperativo(vuelo);
                break;
            default:
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado.");
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a OPERATIVO.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     * @throws NoVueloEnMenosDe1HoraException
     * @throws NoModosPuertasEmbarqueDisponiblesException
     * @throws NoFingerMercanciasException
     */
    private void cambiarEstadoOperativo(Vuelo vuelo) throws EstadoEquivocadoException, NoVueloEnMenosDe1HoraException,
            NoModosPuertasEmbarqueDisponiblesException, NoFingerMercanciasException {
        if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado.");
        }

        Vuelo vueloNuevo = null;
        ModoEmbarque modoActual = vuelo.getModoEmbarque();

        for (Vuelo v : aeropuerto.getVuelosAceptados()) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo() == TipoVuelo.SALIDA) {
                if (verificarVueloMenosHora(vuelo.getAvion()) == true) {
                    vueloNuevo = v;
                    break;
                }
            }
        }

        if (vueloNuevo == null) {
            throw new NoVueloEnMenosDe1HoraException(
                    "El avión del vuelo no tiene programado un vuelo en menos de 1 hora.");
        }

        PuertaEmbarque puerta = vuelo.getPuertaEmbarque();

        if (vueloNuevo.getTipoModoEmbarque() == vuelo.getTipoModoEmbarque()) {
            liberarPuertaEmbarque(vuelo);
            asignarPuertaEmbarque(puerta, vueloNuevo);
            liberarModoEmbarque(vuelo);
            asignarModoEmbarque(modoActual, vueloNuevo);
        } else {
            // Intentamos asignarle modo de embarque al vuelo nuevo sin cambiarlo de puerta
            // de embarque.
            if (vueloNuevo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
        
        		if (puerta.getFinger().comprobarTamano(vueloNuevo) == true && puerta.getFinger().getVuelo() == null) {
                    liberarPuertaEmbarque(vuelo);
                    asignarPuertaEmbarque(puerta, vueloNuevo);
                    liberarModoEmbarque(vuelo);
                    asignarModoEmbarque(puerta.getFinger(), vueloNuevo);
        		}

                
            } else {
                for (ZonaAparcamiento z : puerta.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vueloNuevo) == true && z.getVuelo() == null) {
                        liberarPuertaEmbarque(vuelo);
                        asignarPuertaEmbarque(puerta, vueloNuevo);
                        liberarModoEmbarque(vuelo);
                        asignarModoEmbarque(z, vueloNuevo);
                        break;
                    }
                }
            }

            // Si no se ha podido asignarle modo de embarque al vuelo nuevo sin cambiar de
            // puerta de embarque, se cambia de puerta de embarque.
            if (vueloNuevo.getModoEmbarque() == null) {
                liberarPuertaEmbarque(vuelo);
                if (vueloNuevo.getTipoCarga() == TipoCarga.MERCANCIAS) {
                    if (vueloNuevo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
                        throw new NoFingerMercanciasException("Un vuelo de mercancías no puede tener finger.");
                    } else {
                        for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                            if (z.comprobarTamano(vueloNuevo) == true && z.getVuelo() == null
                                    && vueloNuevo.getModoEmbarque() == null) {
                                for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                                    if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                        asignarModoEmbarque(z, vueloNuevo);
                                        asignarPuertaEmbarque(p, vueloNuevo);
                                        break;
                                    }
                                }
                            }
                        }
                        if (vueloNuevo.getModoEmbarque() == null || vueloNuevo.getPuertaEmbarque() == null) {
                            throw new NoModosPuertasEmbarqueDisponiblesException(
                                    "No hay modos o puertas de embarque disponibles.");
                        }
                    }
                } else {
                    if (vueloNuevo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
                        for (Finger f : aeropuerto.getFingers()) {
                            if (f.comprobarTamano(vueloNuevo) == true && f.getVuelo() == null
                                    && vueloNuevo.getModoEmbarque() == null) {
                                for (PuertaEmbarque p : f.getPuertasEmbarque()) {
                                    if (p.getVuelo() == null) {
                                        asignarModoEmbarque(f, vueloNuevo);
                                        asignarPuertaEmbarque(p, vueloNuevo);
                                        break;
                                    }
                                }
                            }
                        }
                        if (vueloNuevo.getModoEmbarque() == null || vueloNuevo.getPuertaEmbarque() == null) {
                            throw new NoModosPuertasEmbarqueDisponiblesException(
                                    "No hay modos o puertas de embarque disponibles");
                        }
                    } else {
                        for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                            if (z.comprobarTamano(vueloNuevo) == true && z.getVuelo() == null
                                    && vueloNuevo.getModoEmbarque() == null) {
                                for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                                    if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                        asignarModoEmbarque(z, vueloNuevo);
                                        asignarPuertaEmbarque(p, vueloNuevo);
                                        break;
                                    }
                                }
                            }
                        }
                        if (vueloNuevo.getModoEmbarque() == null || vueloNuevo.getPuertaEmbarque() == null) {
                            throw new NoModosPuertasEmbarqueDisponiblesException(
                                    "No hay modos o puertas de embarque disponibles");
                        }
                    }
                }
            }
        }

        vueloNuevo.cambiarEstado(EstadoVuelo.OPERATIVO);
        recibirNotificacionCambioEstado(vueloNuevo);
        for (Operador o : vueloNuevo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vueloNuevo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a A_HANGAR.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     * @throws VueloEnMenosDe1HoraException
     * @throws NoHangaresDisponiblesException
     * @throws CargaAvionEquivocadaException 
     */
    private void cambiarEstadoAHangar(Vuelo vuelo)
            throws EstadoEquivocadoException, VueloEnMenosDe1HoraException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
        if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        for (Vuelo v : aeropuerto.getVuelosAceptados()) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo() == TipoVuelo.SALIDA) {
                if (verificarVueloMenosHora(vuelo.getAvion()) == true) {
                    throw new VueloEnMenosDe1HoraException(
                            "El avión del vuelo tiene programado un vuelo en menos de 1 hora");
                }
            }
        }

        Hangar hangar = null;
        for (Hangar h : aeropuerto.getHangares()) {
            if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
                if ((h instanceof HangarMercancias == true) && (h.hayEspacioEnHangar() == true)
                        && (h.comprobarTamano(vuelo) == true)
                        && ((HangarMercancias) h).comprobarMercaPeligrosa(vuelo) == true) {
                    hangar = h;
                    liberarAvion(vuelo);
                    break;
                }
            } else {
                if (h instanceof HangarPasajeros == true && h.hayEspacioEnHangar() == true
                        && h.comprobarTamano(vuelo) == true) {
                    hangar = h;
                    liberarAvion(vuelo);
                    break;
                }
            }

        }
        if (vuelo.getAvion() != null) {
            throw new NoHangaresDisponiblesException("No hay hangares disponibles");
        }

        liberarModoEmbarque(vuelo);
        hangar.addAvion(vuelo.getAvion());
        liberarAvion(vuelo);
        vuelo.cambiarEstado(EstadoVuelo.AVION_A_HANGAR);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     */
    private void cambiarEstadoAterrizaje(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ATERRIZAJE);
        liberarPista(vuelo);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ESPERANDO_ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     * @throws NoPistasDisponiblesException
     */
    private void cambiarEstadoEsperandoAterrizaje(Vuelo vuelo)
            throws EstadoEquivocadoException, NoPistasDisponiblesException {
        if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        int ocupada = 0;

        for (Vuelo v : vuelo.getPista().getVuelos()) {
            if (v.getEstado() == EstadoVuelo.ESPERANDO_ATERRIZAJE) {
                ocupada = 1;
            }
        }

        if (ocupada == 0) {
            vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_ATERRIZAJE);
        } else {
            throw new NoPistasDisponiblesException("No hay pistas disponibles");
        }

        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a
     * ESPERANDO_PISTA_DE_ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     * @throws NoPistasDisponiblesException
     * @throws NoFingerMercanciasException
     * @throws NoModosPuertasEmbarqueDisponiblesException
     */
    private void cambiarEstadoEsperandoPistaAterrizaje(Vuelo vuelo)
            throws EstadoEquivocadoException, NoPistasDisponiblesException,
            NoFingerMercanciasException, NoModosPuertasEmbarqueDisponiblesException {
        if (vuelo.getEstado() != EstadoVuelo.VUELO) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        List<Pista> pistaDespegue = new ArrayList<>();
        for (Pista p : aeropuerto.getPistas()) {
            if (p.getTipoPista() == TipoPista.ATERRIZAJE) {
                pistaDespegue.add(p);
            }
        }
        Random rand = new Random();
        Pista pista = pistaDespegue.get(rand.nextInt(pistaDespegue.size()));
        asignarPista(pista, vuelo);
        if (vuelo.getPista() == null) {
            throw new NoPistasDisponiblesException("No hay pistas disponibles");
        }

        if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
            if (vuelo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
                liberarPista(vuelo);
                throw new NoFingerMercanciasException("Un vuelo de mercancías no puede tener finger");
            } else {
                for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                            if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
                                break;
                            }
                        }
                    }
                }
                if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
                    liberarPista(vuelo);
                    throw new NoModosPuertasEmbarqueDisponiblesException(
                            "No hay modos o puertas de embarque disponibles");
                }
            }
        } else {
            if (vuelo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
                for (Finger f : aeropuerto.getFingers()) {
                    if (f.comprobarTamano(vuelo) == true && f.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p : f.getPuertasEmbarque()) {
                            if (p.getVuelo() == null) {
                                asignarModoEmbarque(f, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
                                break;
                            }
                        }
                    }
                }
                if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
                    liberarPista(vuelo);
                    throw new NoModosPuertasEmbarqueDisponiblesException(
                            "No hay modos o puertas de embarque disponibles");
                }
            } else {
                for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                            if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
                                break;
                            }
                        }
                    }
                }
                if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
                    liberarPista(vuelo);
                    throw new NoModosPuertasEmbarqueDisponiblesException(
                            "No hay modos o puertas de embarque disponibles");
                }
            }
        }

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a
     * ESPERANDO_PISTA_DE_DESPEGUE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     */
    private void cambiarEstadoEsperandoPistaDespegue(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_EMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ESPERANDO_DESPEGUE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException
     * @throws NoPistasDisponiblesException
     */
    private void cambiarEstadoEsperandoDespegue(Vuelo vuelo)
            throws EstadoEquivocadoException, NoPistasDisponiblesException {
        if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        List<Pista> pistas = new ArrayList<>();
        for (Pista p : aeropuerto.getPistas()) {
            if (p.getTipoPista() == TipoPista.DESPEGUE) {
                pistas.add(p);
            }
        }
        Random rand = new Random();
        Pista pista = pistas.get(rand.nextInt(pistas.size()));
        asignarPista(pista, vuelo);
        if (vuelo.getPista() == null) {
            throw new NoPistasDisponiblesException("No hay pistas disponibles");
        }

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

    /**
     * Método auxiliar para asignar modo de embarque a un vuelo.
     * 
     * @param modo  El modo de embarque que se asigna.
     * @param vuelo El vuelo al que se le asigna.
     * 
     * @throws IllegalArgumentException Si los argumentos son nulos.
     */
    private void asignarModoEmbarque(ModoEmbarque modo, Vuelo vuelo) throws IllegalArgumentException {
        if (modo == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y el modo de embarque no pueden ser null");
        }

        modo.setVuelo(vuelo);
        vuelo.setModoEmbarque(modo);
    }

    /**
     * Método auxiliar para liberar el modo de embarque de un vuelo.
     * 
     * @param vuelo El vuelo del que se libera.
     * 
     * @throws IllegalArgumentException Si el argumentos es nulo.
     */
    private void liberarModoEmbarque(Vuelo vuelo) throws IllegalArgumentException {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        ModoEmbarque modo = vuelo.getModoEmbarque();
        vuelo.setModoEmbarque(null);
        modo.setVuelo(null);
    }

    /**
     * Método auxiliar para asignar pista a un vuelo.
     * 
     * @param pista La pista que se asigna.
     * @param vuelo El vuelo al que se le asigna.
     * 
     * @throws IllegalArgumentException Si los argumentos son nulos.
     */
    private void asignarPista(Pista pista, Vuelo vuelo) throws IllegalArgumentException {
        if (pista == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y la pista no pueden ser null");
        }

        pista.addVuelo(vuelo);
        vuelo.setPista(pista);
    }

    /**
     * Método auxiliar para liberar la pista de un vuelo.
     * 
     * @param vuelo El vuelo del que se libera.
     * 
     * @throws IllegalArgumentException Si el argumentos es nulo.
     */
    private void liberarPista(Vuelo vuelo) throws IllegalArgumentException {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        Pista pista = vuelo.getPista();
        vuelo.setPista(null);
        pista.removeVuelo(vuelo);
    }

    /**
     * Método auxiliar para liberar el avion de un vuelo.
     * 
     * @param vuelo El vuelo del que se libera.
     * 
     * @throws IllegalArgumentException Si el argumentos es nulo.
     */
    private void liberarAvion(Vuelo vuelo) throws IllegalArgumentException {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        Avion avion = vuelo.getAvion();
        vuelo.setAvion(null);
        avion.setAerolinea(null);
    }

    /**
     * Método auxiliar para asignar puerta de embarque a un vuelo.
     * 
     * @param puerta La puerta de embarque que se asigna.
     * @param vuelo  El vuelo al que se le asigna.
     * 
     * @throws IllegalArgumentException Si los argumentos son nulos.
     */
    private void asignarPuertaEmbarque(PuertaEmbarque puerta, Vuelo vuelo) throws IllegalArgumentException {
        if (puerta == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y la puerta de embarque no pueden ser null");
        }

        puerta.setVuelo(vuelo);
        vuelo.setPuertaEmbarque(puerta);
    }

    /**
     * Método auxiliar para liberar la puerta de embarque de un vuelo.
     * 
     * @param vuelo El vuelo del que se libera.
     * 
     * @throws IllegalArgumentException Si el argumentos es nulo.
     */
    private void liberarPuertaEmbarque(Vuelo vuelo) throws IllegalArgumentException {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        PuertaEmbarque puerta = vuelo.getPuertaEmbarque();
        vuelo.setPuertaEmbarque(null);
        puerta.setVuelo(null);
    }

    /**
     * Método auxiliar para verificar si un avión tiene un vuelo en menos de una
     * hora.
     * 
     * @param avion El avion que se verifica.
     * 
     * @return true si tiene un vuelo en menos de una hora, false si no.
     */
    private boolean verificarVueloMenosHora(Avion avion) {
        LocalDateTime ahora = LocalDateTime.now();

        for (Vuelo vuelo : vuelosQueControla) {
            if (vuelo.getAvion().equals(avion) && vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                LocalTime salida = vuelo.getHora();

                if (Duration.between(ahora, salida).toMinutes() <= 60) {
                    return true;
                }
            }
        }

        return false;
    }
    
    @Override
    public void recibirNotificacion(Notificacion notificacion) {
    	
		if(notificacion.getTipoNotificacion()==TipoNotificacion.CAMBIO_ESTADO_VUELO) {
			 notificaciones.add(notificacion);
			 System.out.println("Nueva notificación: " + notificacion.getMensaje());
		}
    }
    
    /**
     * Método para recibir una notificación de cambio de estado de un vuelo.
     * 
     * @param vuelo El vuelo del que trata la notificación.
     */
	@Override
    public void recibirNotificacionCambioEstado(Vuelo vuelo) {
        String mensaje = "El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".";
        Notificacion notificacion = new Notificacion(mensaje, TipoNotificacion.CAMBIO_ESTADO_VUELO);
	    if(vuelo.getTerminal().equals(this.terminal)) {
	        recibirNotificacion(notificacion);
		}
    }
}

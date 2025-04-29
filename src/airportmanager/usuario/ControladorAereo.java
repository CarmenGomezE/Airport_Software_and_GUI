package src.airportmanager.usuario;

import java.util.*;



import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.exceptions.EstadoEquivocadoException;
import src.airportmanager.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
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
    /** Mensaje de la interfaz. */
    private String mensajeGUI;

    /**
     * Constructor de la clase ControladorAereo.
     * 
     * @param nombre     Nombre del controlador aéreo.
     * @param dni        Dni del controlador aéreo.
     * @param contrasena Contraseña del controlador aéreo.
     * @param gestor     Gestor del controlador aéreo.
     * @param terminal   Terminal del controlador aéreo.
     * @param aeropuerto Aeropuerto.
     * @param sistema    Sistema.
     * @throws FormatoDniIncorrectoException Formato del dni incorrecto.
     */
    public ControladorAereo(String nombre, String dni, String contrasena, AeropuertoPropio aeropuerto, Gestor gestor,
            Terminal terminal, AirportManager sistema) throws FormatoDniIncorrectoException {
        super(nombre, contrasena, dni, aeropuerto, gestor, sistema);
        this.terminal = terminal;
        this.mensajeGUI = ""; 
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
     * Método para obtener el mensaje de la interfaz.
     * 
     * @return El mensaje de la interfaz.
     */
    public String getMensajeGUI() {
    	return mensajeGUI;
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo  Vuelo al que se le cambia el estado. 
     * @param estado Nuevo estado del vuelo.
     * @throws IllegalArgumentException argumento no válido.
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) {
        if (vuelo == null || estado == null) {
            System.out.println("Error: El vuelo y el nuevo estado no pueden ser null.");
            return;
        }

        try {
            if (vuelo.getControlador() != this) {
                throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario.");
            }
            if(vuelo.getPista()!=null ){
            	System.out.println("ANTES Código: "+vuelo.getCodigo()+" Pista: "+vuelo.getPista().getCodigo());
            } if(vuelo.getPuertaEmbarque()!=null ){
            	System.out.println("ANTES Código: "+vuelo.getCodigo()+" Puerta: "+vuelo.getPuertaEmbarque().getId());
            } if( vuelo.getModoEmbarque()!=null){
            	System.out.println("ANTES Código: "+vuelo.getCodigo()+" Modo embarque: "+vuelo.getModoEmbarque().getId());
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
        } catch (VueloNoDelUsuarioException e) {
            System.out.println("Error: " + e.getMessage()); 
        } catch (EstadoNoDelUsuarioException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EstadoEquivocadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoPistasDisponiblesException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoVueloEnMenosDe1HoraException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (VueloEnMenosDe1HoraException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoFingerMercanciasException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoModosPuertasEmbarqueDisponiblesException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoHangaresDisponiblesException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (CargaAvionEquivocadaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) { 
            System.out.println("Error inesperado: " + e.getMessage());  
        }
        
        if(vuelo.getPista()!=null ){
        	System.out.println("DESPUES Código: "+vuelo.getCodigo()+" Pista: "+vuelo.getPista().getCodigo());
        } if(vuelo.getPuertaEmbarque()!=null ){
        	System.out.println("DESPUES Código: "+vuelo.getCodigo()+" Puerta: "+vuelo.getPuertaEmbarque().getId());
        } if( vuelo.getModoEmbarque()!=null){
        	System.out.println("DESPUES Código: "+vuelo.getCodigo()+" Modo embarque: "+vuelo.getModoEmbarque().getId());
        }
        System.out.println("\n");
    }
    
    /**
     * Método auxiliar para cambiar el estado de un vuelo a OPERATIVO.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     * @throws NoVueloEnMenosDe1HoraException no vuelo menos 1 hora.
     * @throws NoModosPuertasEmbarqueDisponiblesException no modos en puerta.
     * @throws NoFingerMercanciasException no finger para mercancías.
     * @throws CargaAvionEquivocadaException carga no válida.
     * @throws NoHangaresDisponiblesException hangares no disponibles.
     * @throws VueloEnMenosDe1HoraException vuelo en menos de 1 hora.
     */
    private void cambiarEstadoOperativo(Vuelo vuelo) throws EstadoEquivocadoException, NoVueloEnMenosDe1HoraException,
            NoModosPuertasEmbarqueDisponiblesException, NoFingerMercanciasException, VueloEnMenosDe1HoraException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
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

        try {
            if (vueloNuevo == null) {
                throw new NoVueloEnMenosDe1HoraException("El avión del vuelo no tiene programado un vuelo en menos de 1 hora. Debería asignar hangar.");
            }
        } catch (NoVueloEnMenosDe1HoraException e) {
            System.out.println("Advertencia: " + e.getMessage());
            return;
        }
       
        PuertaEmbarque puerta = vuelo.getPuertaEmbarque();

        if (vueloNuevo.getTipoModoEmbarque() == vuelo.getTipoModoEmbarque()) {
            liberarPuertaEmbarque(vuelo);
            asignarPuertaEmbarque(puerta, vueloNuevo);
            liberarModoEmbarque(vuelo);
            asignarModoEmbarque(modoActual, vueloNuevo);
            mensajeGUI = "Se le han asignado la puerta de embarque " + puerta.getId() + " y el modo de embarque " + modoActual.getId() + " al vuelo " + vueloNuevo.getCodigo() + ".";
        } else {
            if (vueloNuevo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
        
        		if (puerta.getFinger().comprobarTamano(vueloNuevo) == true && puerta.getFinger().getVuelo() == null) {
                    liberarPuertaEmbarque(vuelo);
                    asignarPuertaEmbarque(puerta, vueloNuevo);
                    liberarModoEmbarque(vuelo);
                    asignarModoEmbarque(puerta.getFinger(), vueloNuevo);
                    mensajeGUI = "Se le han asignado la puerta de embarque " + puerta.getId() + " y el modo de embarque " + puerta.getFinger().getId() + " al vuelo " + vueloNuevo.getCodigo() + ".";
        		}

                
            } else {
                for (ZonaAparcamiento z : puerta.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vueloNuevo) == true && z.getVuelo() == null) {
                        liberarPuertaEmbarque(vuelo);
                        asignarPuertaEmbarque(puerta, vueloNuevo);
                        liberarModoEmbarque(vuelo);
                        asignarModoEmbarque(z, vueloNuevo);
                        mensajeGUI = "Se le han asignado la puerta de embarque " + puerta.getId() + " y el modo de embarque " + z.getId() + " al vuelo " + vueloNuevo.getCodigo() + ".";
                        break;
                    }
                }
            }

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
                                        mensajeGUI = "Se le han asignado la puerta de embarque " + p.getId() + " y el modo de embarque " + z.getId() + " al vuelo " + vueloNuevo.getCodigo() + ".";
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
                    	for (Terminal t : aeropuerto.getTerminales()) {
                    	    for (PuertaEmbarque p : t.getPuertasEmbarque()) {
                    	        Finger f = p.getFinger();
                    	        if (f != null && f.comprobarTamano(vueloNuevo) && f.getVuelo() == null && vueloNuevo.getModoEmbarque() == null) {
                    	            if (p.getVuelo() == null) {
                    	                asignarModoEmbarque(f, vueloNuevo);
                    	                asignarPuertaEmbarque(p, vueloNuevo);
                    	                break; 
                    	            }
                    	        }
                    	    }
                        }
                        if (vueloNuevo.getModoEmbarque() == null || vueloNuevo.getPuertaEmbarque() == null) {
                            throw new NoModosPuertasEmbarqueDisponiblesException( "No hay modos o puertas de embarque disponibles");
                        }
                    } else {
                        for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                            if (z.comprobarTamano(vueloNuevo) == true && z.getVuelo() == null
                                    && vueloNuevo.getModoEmbarque() == null) {
                                for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                                    if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                        asignarModoEmbarque(z, vueloNuevo);
                                        asignarPuertaEmbarque(p, vueloNuevo);
                                        mensajeGUI = "Se le han asignado la puerta de embarque " + p.getId() + " y el modo de embarque " + z.getId() + " al vuelo " + vueloNuevo.getCodigo() + ".";
                                        break;
                                    }
                                }
                            }
                        }
                        if (vueloNuevo.getModoEmbarque() == null || vueloNuevo.getPuertaEmbarque() == null) {
                            throw new NoModosPuertasEmbarqueDisponiblesException( "No hay modos o puertas de embarque disponibles");
                        }
                    }
                }
            }
        }

        vueloNuevo.cambiarEstado(EstadoVuelo.OPERATIVO);
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a A_HANGAR.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     * @throws VueloEnMenosDe1HoraException vuelo en menos de 1 hora.
     * @throws NoHangaresDisponiblesException no hnagares disponibles.
     * @throws CargaAvionEquivocadaException carga no válida.
     */
    private void cambiarEstadoAHangar(Vuelo vuelo)
            throws EstadoEquivocadoException, VueloEnMenosDe1HoraException, NoHangaresDisponiblesException, CargaAvionEquivocadaException {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.FIN_DEL_DESEMBARQUE ) {
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
                if ((h instanceof HangarMercancias == true) && (h.hayEspacioEnHangar() == true) && (h.comprobarTamano(vuelo) == true) && ((HangarMercancias) h).comprobarMercaPeligrosa(vuelo) == true) {
                	hangar=((HangarMercancias) h);
                    hangar.addAvion(vuelo.getAvion());
                    break;
                }
            } else if  (vuelo.getTipoCarga() == TipoCarga.PASAJEROS){  
                if (h instanceof HangarPasajeros == true && h.hayEspacioEnHangar() == true && h.comprobarTamano(vuelo) == true) {   
                	hangar= ((HangarPasajeros) h);
                	((HangarPasajeros) h).addAvion(vuelo.getAvion());
                    break;
                }
            }
        }
        mensajeGUI = "Se le han asignado el hangar " + hangar.getId() +" al vuelo " + vuelo.getCodigo() + ".";
        
        if (hangar instanceof HangarMercancias == true) {
        	if (((HangarMercancias) hangar).getAviones().contains(vuelo.getAvion())==false) {
                throw new NoHangaresDisponiblesException("No hay hangares disponibles");
            }
        } else if  (hangar instanceof HangarPasajeros == true ){  
        	if (((HangarPasajeros) hangar).getAviones().contains(vuelo.getAvion())==false) {
                throw new NoHangaresDisponiblesException("No hay hangares disponibles");
            }
        }

        liberarModoEmbarque(vuelo);
        liberarPuertaEmbarque(vuelo);
        vuelo.cambiarEstado(EstadoVuelo.AVION_A_HANGAR);
        
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     */
    private void cambiarEstadoAterrizaje(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.ESPERANDO_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ATERRIZAJE);
        liberarPista(vuelo);
        
        mensajeGUI = "";
        
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ESPERANDO_ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     * @throws NoPistasDisponiblesException no pista disponible.
     */
    private void cambiarEstadoEsperandoAterrizaje(Vuelo vuelo)
            throws EstadoEquivocadoException, NoPistasDisponiblesException {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        int ocupada = 0;

        for (Vuelo v : vuelo.getPista().getVuelos()) {
            if (v.getEstado() == EstadoVuelo.ESPERANDO_ATERRIZAJE) {
                ocupada = 1;
            }
        }
        
        mensajeGUI = "";

        if (ocupada == 0) {
            vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_ATERRIZAJE);
        } else {
            throw new NoPistasDisponiblesException("No hay pistas disponibles");
        }

        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a ESPERANDO_PISTA_DE_ATERRIZAJE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws NoPistasDisponiblesException no pista disponible.
     * @throws NoFingerMercanciasException no finger para mercancias.
     * @throws NoModosPuertasEmbarqueDisponiblesException no modos en puerta.
     */
    private void cambiarEstadoEsperandoPistaAterrizaje(Vuelo vuelo) throws NoPistasDisponiblesException, NoFingerMercanciasException, NoModosPuertasEmbarqueDisponiblesException {
        List<Pista> pistaAterrizaje = new ArrayList<>();
        for (Pista p : aeropuerto.getPistas()) {
            if (p.getTipoPista() == TipoPista.ATERRIZAJE) {
            	pistaAterrizaje.add(p);
            }
        }
        Random rand = new Random();
        Pista pista = pistaAterrizaje.get(rand.nextInt(pistaAterrizaje.size()));
        asignarPista(pista, vuelo); 
        if (vuelo.getPista() == null) {
            throw new NoPistasDisponiblesException("No hay pistas disponibles");
        }

        if (vuelo.getTipoCarga()==TipoCarga.MERCANCIAS) {
            if (vuelo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
                liberarPista(vuelo);
                throw new NoFingerMercanciasException("Un vuelo de mercancías no puede tener finger");
            } 
            else {
                for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque()==null) {
                        for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                            if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
                                mensajeGUI = "Se le han asignado la pista " + pista.getCodigo() + ", la puerta de embarque " + p.getId() + " y el modo de embarque " + z.getId() + " al vuelo " + vuelo.getCodigo() + ".";
                                break;
                            }
		                }
		            }
		        }
		        if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
		        	liberarPista(vuelo);
		            throw new NoModosPuertasEmbarqueDisponiblesException("No hay modos o puertas de embarque disponibles");
		        }
            }
        } else {
            if (vuelo.getTipoModoEmbarque() == TipoModoEmbarque.FINGER) {
        	    for (PuertaEmbarque p : terminal.getPuertasEmbarque()) {
        	        Finger f = p.getFinger();
        	        if (f != null && f.comprobarTamano(vuelo) && f.getVuelo() == null && vuelo.getModoEmbarque() == null) {
        	            if (p.getVuelo() == null) {
        	                asignarModoEmbarque(f, vuelo);
        	                asignarPuertaEmbarque(p, vuelo);
                            mensajeGUI = "Se le han asignado la pista " + pista.getCodigo() + ", la puerta de embarque " + p.getId() + " y el modo de embarque " + f.getId() + " al vuelo " + vuelo.getCodigo() + ".";
        	                break;  
        	            }
        	        }
            	}
                if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
                    liberarPista(vuelo);
                    throw new NoModosPuertasEmbarqueDisponiblesException("No hay modos o puertas de embarque disponibles");
                }
            } else {
                for (ZonaAparcamiento z : aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p : getTerminal().getPuertasEmbarque()) {
                            if (p.contieneZonaAparcamiento(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
                                mensajeGUI = "Se le han asignado la pista " + pista.getCodigo() + ", la puerta de embarque " + p.getId() + " y el modo de embarque " + z.getId() + " al vuelo " + vuelo.getCodigo() + ".";
                                break;
                            }
                        }
                    }
                }
                if (vuelo.getModoEmbarque() == null || vuelo.getPuertaEmbarque() == null) {
                    liberarPista(vuelo);
                    throw new NoModosPuertasEmbarqueDisponiblesException("No hay modos o puertas de embarque disponibles");
                }
            }
        }

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE);
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método auxiliar para cambiar el estado de un vuelo a
     * ESPERANDO_PISTA_DE_DESPEGUE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     * @throws NoPistasDisponiblesException no pista.
     */
    private void cambiarEstadoEsperandoPistaDespegue(Vuelo vuelo) throws EstadoEquivocadoException, NoPistasDisponiblesException {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.FIN_DEL_EMBARQUE ) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
        if(vuelo.getModoEmbarque()!=null) {
        	liberarModoEmbarque(vuelo);
        }
        if(vuelo.getPuertaEmbarque()!=null) {
        	liberarPuertaEmbarque(vuelo);
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
        mensajeGUI = "Se le ha asignado la pista " + pista.getCodigo() + " al vuelo " + vuelo.getCodigo() + ".";
        

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));
    }

   
    /**
     * Método auxiliar para cambiar el estado de un vuelo a ESPERANDO_DESPEGUE.
     * 
     * @param vuelo El vuelo al que se le intenta cambiar el estado.
     * 
     * @throws EstadoEquivocadoException estado equivocado.
     */
    private void cambiarEstadoEsperandoDespegue(Vuelo vuelo) throws EstadoEquivocadoException  {
        if (vuelo.getHistorialEstadoVuelo().getUltimoEstadoManual() != EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        mensajeGUI = "";
        if(vuelo.getPista()!=null) {
        	vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        }
        
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

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
    public void liberarPista(Vuelo vuelo) throws IllegalArgumentException {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        Pista pista = vuelo.getPista();
        vuelo.setPista(null);
        if(pista!=null) {
        	pista.removeVuelo(vuelo);
        }
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

        for (Vuelo vuelo : aeropuerto.getVuelosAceptados()) {
            if (vuelo.getAvion().equals(avion) && vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                LocalTime salida = vuelo.getHora();

                if (Duration.between(ahora, salida).toMinutes() <= 60) {
                    return true;
                }
            }
        }

        return false;
    }
    
    /**  
     * Método to string.
     * 
     * @return cadena que respresenta al controlador.
     */
    @Override
    public String toString() {
    	return "Controlador: "+super.nombre;
    }
    
    /**
     * Método para recibir una notificación de su terminal.
     * 
     * @param notificacion Notificación que se recibe.
     */
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

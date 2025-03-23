package aeropuerto.src.maria;

import aeropuerto.src.carmen.*;
import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.avion.*;
import aeropuerto.src.claudia.*;
import java.util.*;
import java.time.*;

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
     * @param gestor                Gestor del controlador aéreo.
     */
    public ControladorAereo(String nombre, String dni, String contrasena, AeropuertoPropio aeropuerto, Gestor gestor, Terminal terminal) {
        super(nombre, contrasena, dni, aeropuerto, gestor);
        this.terminal=terminal;
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
     * @param vuelo     Vuelo al que se le cambia el estado.
     * @param estado    Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException 
     * @throws EstadoNoDelUsuarioException 
     * @throws EstadoEquivocadoException 
     * @throws NoModosEmbarqueDisponiblesException 
     * @throws NoPistasDisponiblesException 
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException, EstadoEquivocadoException, NoModosEmbarqueDisponiblesException, NoPistasDisponiblesException {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (vuelosQueControla.contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario");
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
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado");
        }
    }

	private void cambiarEstadoOperativo(Vuelo vuelo) throws EstadoEquivocadoException, NoModosEmbarqueDisponiblesException {
		if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        Vuelo vueloNuevo = null;
        ModoEmbarque modoActual = vuelo.getModoEmbarque(); 
        ModoEmbarque modoNuevo = null;

        for (Vuelo v: aeropuerto.getVuelosAceptados()) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo() == TipoVuelo.SALIDA) {
                if (verificarVueloMenosHora(vuelo.getAvion()) == true) {
                    modoNuevo = v.getModoEmbarque();
                    break;
                }
            }
        }

        if (modoNuevo != null && !modoNuevo.equals(modoActual)) {
            liberarModoEmbarque(vuelo);
        }

        if (modoNuevo instanceof Finger) {
            asignarModoEmbarque((Finger) modoNuevo, vueloNuevo);
        } else if (modoNuevo instanceof ZonaAparcamiento) {
            asignarModoEmbarque((ZonaAparcamiento) modoNuevo, vueloNuevo);
        } else {
            throw new NoModosEmbarqueDisponiblesException("No hay modos de embarque disponibles.");
        }

        vuelo.cambiarEstado(EstadoVuelo.OPERATIVO);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void cambiarEstadoAHangar(Vuelo vuelo) {
		if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        for (Vuelo v: aeropuerto.getVuelosAceptados()) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo() == TipoVuelo.SALIDA) {
                if (verificarVueloMenosHora(vuelo.getAvion()) == true) {
                    throw new VueloEnMenosDe1HoraException("El avión del vuelo tiene programado un vuelo en menos de 1 hora");
                }
            }
        }

        for (Hangar h: aeropuerto.getHangares()) {
            if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
                if ((h instanceof HangarMercancias == true) && (h.hayEspacioEnHangar() == true) && (h.comprobarTamano(vuelo) == true) && ((HangarMercancias) h).comprobarMercaPeligrosa(vuelo) == true) {
                    liberarAvion(vuelo);
                    break;
                }
            } else {
                if (h instanceof HangarPasajeros == true && h.hayEspacioEnHangar() == true && h.comprobarTamano(vuelo) == true) {
                    liberarAvion(vuelo);
                    break;
                }
            }
            
        }
        if (vuelo.getAvion() != null) {
            throw new NoHangaresDisponiblesException("No hay hangares disponibles");
        } 

        liberarModoEmbarque(vuelo);
        h.addAvion(vuelo.getAvion());
        liberarAvion(vuelo);
        vuelo.cambiarEstado(EstadoVuelo.AVION_A_HANGAR);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void cambiarEstadoAterrizaje(Vuelo vuelo) throws EstadoEquivocadoException {
		if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ATERRIZAJE);
        liberarPista(vuelo);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void cambiarEstadoEsperandoAterrizaje(Vuelo vuelo) {
		if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
		
        int ocupada = 0;

        for (Vuelo v: vuelo.getPista().getVuelos()) {
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
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void cambiarEstadoEsperandoPistaAterrizaje(Vuelo vuelo) {
        if (vuelo.getEstado() != EstadoVuelo.VUELO) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        List<Pista> pistaDespegue = new ArrayList<>();
        for (Pista p: aeropuerto.getPistas()) {
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
                for(ZonaAparcamiento z: aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p: getTerminal().getPuertasEmbarque()) {
                            if (p.contains(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
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
                for(Finger f: aeropuerto.getFingers()) {
                    if (f.comprobarTamano(vuelo) == true && f.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p: f.getPuertasEmbarque()) {
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
                    throw new NoModosEmbarqueDisponiblesException("No hay modos o puertas de embarque disponibles");
                }
            } else {
                for(ZonaAparcamiento z: aeropuerto.getZonasAparcamiento()) {
                    if (z.comprobarTamano(vuelo) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
                        for (PuertaEmbarque p: getTerminal().getPuertasEmbarque()) {
                            if (p.contains(z) && p.getVuelo() == null) {
                                asignarModoEmbarque(z, vuelo);
                                asignarPuertaEmbarque(p, vuelo);
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
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
    }

	private void cambiarEstadoEsperandoPistaDespegue(Vuelo vuelo) throws EstadoEquivocadoException {
		if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_EMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
        recibirNotificacionCambioEstado(vuelo);
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void cambiarEstadoEsperandoDespegue(Vuelo vuelo) throws EstadoEquivocadoException, NoPistasDisponiblesException {
		if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        List<Pista> pistas = new ArrayList<>();
        for (Pista p: aeropuerto.getPistas()) {
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
        for (Operador o: vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
	}

	private void asignarModoEmbarque(ModoEmbarque modo, Vuelo vuelo) {
        if (modo == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y el modo de embarque no pueden ser null");
        }
     
        modo.setVuelo(vuelo);
        vuelo.setModoEmbarque(modo);
    }

    private void liberarModoEmbarque(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        ModoEmbarque modo = vuelo.getModoEmbarque();
        vuelo.setModoEmbarque(null);
        modo.setVuelo(null);
    }

    private void asignarPista(Pista pista, Vuelo vuelo) {
        if (pista == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y la pista no pueden ser null");
        }
     
        pista.addVuelo(vuelo);
        vuelo.setPista(pista);
    }

    private void liberarPista(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        Pista pista = vuelo.getPista();
        vuelo.setPista(null);
        pista.setVuelo(null);
    }

    private void liberarAvion(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        Avion avion = vuelo.getAvion();
        vuelo.setAvion(null);
        avion.setAerolinea(null);
    }

    private void asignarPuertaEmbarque(PuertaEmbarque puerta, Vuelo vuelo) {
        if (puerta == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y la puerta de embarque no pueden ser null");
        }
     
        puerta.setVuelo(vuelo);
        vuelo.setPuertaEmbarque(puerta);
    }

    private void liberarPuertaEmbarque(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        PuertaEmbarque puerta = vuelo.getPuertaEmbarque();
        vuelo.setPuertaEmbarque(null);
        puerta.setVuelo(null);
    }

    private void asignarHangar(Hangar hangar, Vuelo vuelo) throws AvionHangarDistintosException {
        if (hangar == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y el hangar no pueden ser null");
        }

        if (vuelo.getTipoCarga() == TipoCarga.PASAJEROS && hangar instanceof HangarPasajeros == true) {
            ((HangarPasajeros) hangar).addAvion((AvionPasajeros) vuelo.getAvion());
        } else if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS && hangar instanceof HangarMercancias == true) {
            ((HangarMercancias) hangar).addAvion((AvionMercancias) vuelo.getAvion());
        } else {
            throw new AvionHangarDistintosException("El vuelo y el hangar tienen que tener el mismo tipo de carga");
        }
    }

    private boolean verificarVueloMenosHora(Avion avion) {

        LocalDateTime ahora = LocalDateTime.now();

        for (Vuelo vuelo: vuelosQueControla) {
            if (vuelo.getAvion().equals(avion) && vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                LocalTime salida = vuelo.getHora();

                if (Duration.between(ahora, salida).toMinutes() <= 60) {
                    return true;
                }
            }
        }

        return false;
    }
} 

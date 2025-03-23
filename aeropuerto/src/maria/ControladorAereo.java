package aeropuerto.src.maria;

import aeropuerto.src.claudia.*;
import java.util.Random;

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
    public ControladorAereo(String nombre, String dni, String contrasena, AeropuertoPropio aeropuerto, Gestor gestor) {
        super(nombre, dni, contrasena, aeropuerto, gestor);
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
     * Método para cambiar la terminal de un controlador aéreo.
     * 
     * @param Terminal Nueva terminal del controlador aéreo.
     */
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
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

	private void cambiarEstadoOperativo(Vuelo vuelo) {
		if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        for (Vuelo v: aeropuerto.getVuelosAceptados) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo == TipoVuelo.SALIDA) {
                if (v.getHoraSalida().isAfter(LocalDateTime.now()) && v.getHoraSalida().isBefore(LocalDateTime.now().plusHours(1))) {
                    throw new VueloEnMenosDe1HoraException("El avión del vuelo tiene programado un vuelo en menos de 1 hora");
                }
            }
        }
	}

	private void cambiarEstadoAHangar(Vuelo vuelo) {
		if (vuelo.getEstado() != EstadoVuelo.FIN_DEL_DESEMBARQUE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        for (Vuelo v: aeropuerto.getVuelosAceptados) {
            if (v.getAvion().equals(vuelo.getAvion()) && v.getTipoVuelo == TipoVuelo.SALIDA) {
                if (v.getHoraSalida().isAfter(LocalDateTime.now()) && v.getHoraSalida().isBefore(LocalDateTime.now().plusHours(1))) {
                    throw new VueloEnMenosDe1HoraException("El avión del vuelo tiene programado un vuelo en menos de 1 hora");
                }
            }
        }

        for (Hangar h: aeropuerto.getHangares()) {

        }
	}

	private void cambiarEstadoAterrizaje(Vuelo vuelo) {
		if (vuelo.getEstado() != EstadoVuelo.ESPERANDO_ATERRIZAJE) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        vuelo.cambiarEstado(EstadoVuelo.ATERRIZAJE);

        liberarPista(vuelo);

        recibirNotificacionCambioEstado(vuelo);
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
	}

	private void cambiarEstadoEsperandoPistaAterrizaje(Vuelo vuelo) {
        if (vuelo.getEstado() != EstadoVuelo.VUELO) {
            throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }

        List<Pista> pistas =  getAeropuertoPropio().getPistas();
        Random rand = new Random();
        Pista pista = pistas.get(rand.nextInt(pistas.size()));
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
                    if (comprobarTamano(vuelo, z) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
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
                    if (comprobarTamano(vuelo, f) == true && f.getVuelo() == null && vuelo.getModoEmbarque() == null) {
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
                    if (comprobarTamano(vuelo, z) == true && z.getVuelo() == null && vuelo.getModoEmbarque() == null) {
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
    }

	private void cambiarEstadoEsperandoPistaDespegue(Vuelo vuelo) {
		
		
	}

	private void cambiarEstadoEsperandoDespegue(Vuelo vuelo) {
		// TODO Auto-generated method stub
		
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

    private void asignarPuertaEmbarque(PuertaEmbarque puerta, Vuelo vuelo) {
        if (puerta == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y la puerta de embarque no pueden ser null");
        }
     
        puerta.setVuelo(vuelo);
        vuelo.setPuerta(puerta);
    }

    private void liberarPuertaEmbarque(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser null");
        }

        PuertaEmbarque puerta = vuelo.getPuertaEmbarque();
        vuelo.setPuerta(null);
        puerta.setVuelo(null);
    }

    private void asignarHangar(Hangar hangar, Vuelo vuelo) {
        if (hangar == null || vuelo == null) {
            throw new IllegalArgumentException("El vuelo y el hangar no pueden ser null");
        }

        if (vuelo.getTipoCarga() == TipoCarga.PASAJEROS && hangar instanceof HangarPasajeros == true) {
            hangar.addAvion(vuelo.getAvion());
        } else if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS && hangar instanceof HangarMercancias == true) {
            hangar.addAvion(vuelo.getAvion());
        } else {
            throw new AvionHangarDistintosException("El vuelo y el hangar tienen que tener el mismo tipo de carga");
        }
    }
} 

package src.airportmanager.factura;

import java.util.*;
import java.io.Serializable;
import java.time.*;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;

/**
 * Clase que implementa el sistema de facturación.
 * 
 * @author Cramen Gómez, María Pozo, Claudia Municio
 */
public class SistemaFacturacion implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Coste base de los vuelos de salida */
    private double costeBaseVueloSalida;
    /** Coste base de los vuelos de llegada */
    private double costeBaseVueloLlegada;
    /** Recargo a los aviones de Mercancias */
    private double recargoAvionMercancias;
    /** Contador para generar los códigos de las facturas */
    private static int contadorFactura = 1;

    /**
     * Constructor de la clase Factura.
     * 
     * @param recargoAvionMercancias Recargo a los aviones de Mercancias.
     * @param costeBaseVueloSalida   Coste base de los vuelos de salida.
     * @param costeBaseVueloLlegada  Coste base de los vuelos de llegada.
     */
    public SistemaFacturacion(double recargoAvionMercancias, double costeBaseVueloSalida,
            double costeBaseVueloLlegada) {
        this.recargoAvionMercancias = recargoAvionMercancias;
        this.costeBaseVueloLlegada = costeBaseVueloLlegada;
        this.costeBaseVueloSalida = costeBaseVueloSalida;
    }

    /**
     * Método que crea Facturas.
     * 
     * @param aerolinea Aerolinea de la que es la factura.
     * @return Factura mensual de la aerolinea.
     * 
     */public List<Factura> generarFactura(Aerolinea aerolinea) {
    	    List<Vuelo> vuelosMensuales = aerolinea.vuelosMensuales(LocalDate.now().getMonth());
    	    List<Factura> facturas = new ArrayList<>();
    	    if (vuelosMensuales.isEmpty()) {
    	        return null;
    	    }

    	    List<IResourceUsageInfo> recursos = new ArrayList<>();
    	    for (Vuelo vuelo : vuelosMensuales) {
    	        int precioBaseVuelo = 0;
    	        int precioRecargoVuelo = 0;

    	        // Procesar recargos y costes base según el tipo de vuelo y carga
    	        if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
    	            recursos.add(new UsoRecursos("Recargo de mercancías", this.recargoAvionMercancias, 1));
    	            precioRecargoVuelo += this.recargoAvionMercancias;
    	        }
    	        if (vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
    	            recursos.add(new UsoRecursos("Vuelo de salida", this.costeBaseVueloSalida, 1));
    	            precioBaseVuelo += this.costeBaseVueloSalida;
    	        } else if (vuelo.getTipoVuelo() == TipoVuelo.LLEGADA) {
    	            recursos.add(new UsoRecursos("Vuelo de llegada", this.costeBaseVueloLlegada, 1));
    	            precioBaseVuelo += this.costeBaseVueloLlegada;
    	        }

    	        if (vuelo.getOperadoresAsignados() != null && !vuelo.getOperadoresAsignados().isEmpty()) {
    	            double porcentajeTotal = 0.0;

    	            for (Map.Entry<Operador, Double> entry : vuelo.getOperadoresAsignados().entrySet()) {
    	                Operador operador = entry.getKey();
    	                double porcentajeCarga = entry.getValue();
    	                porcentajeTotal += porcentajeCarga;

    	                if (!operador.getAerolinea().equals(aerolinea)) {
    	                    int precioProporcionalBase = (int) (precioBaseVuelo * porcentajeCarga / 100);
    	                    int precioProporcionalRecargo = (int) (precioRecargoVuelo * porcentajeCarga / 100);

    	                    facturas.add(new Factura(operador.getAerolinea(), LocalDate.now(), precioProporcionalBase,
    	                            precioProporcionalRecargo, generarCodigoFactura(operador.getAerolinea()), recursos,
    	                            EstadoFactura.PENDIENTE));
    	                }
    	            }

    	            if (vuelo.getOperadorPrincipal().getAerolinea().equals(aerolinea)) {
    	                int precioProporcionalBase = (int) (precioBaseVuelo * (100 - porcentajeTotal) / 100);
    	                int precioProporcionalRecargo = (int) (precioRecargoVuelo * (100 - porcentajeTotal) / 100);
    	                facturas.add(new Factura(aerolinea, LocalDate.now(), precioProporcionalBase, precioProporcionalRecargo,
    	                        generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE));
    	            }
    	        } else {
    	            if (vuelo.getOperadorPrincipal().getAerolinea().equals(aerolinea)) {
    	                facturas.add(new Factura(aerolinea, LocalDate.now(), precioBaseVuelo, precioRecargoVuelo,
    	                        generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE));
    	            }
    	        }
    	    }

    	    return facturas;
    	}
    

    /**
     * Método que crea códigos de Facturas.
     * 
     * @param aerolinea Aerolinea de la que es la factura.
     * @return Código de la factura.
     */
    public String generarCodigoFactura(Aerolinea aerolinea) {
        return aerolinea.getCodigo() + String.format("%06d", contadorFactura++);
    }
    
    /** 
     * Método para obtener el coste base de un vuelo de salida.
     * 
     * @return El coste base de un vuelo de salida.
     */
    public double getCosteBaseVueloSalida() {
    	return costeBaseVueloSalida;
    }
    
    /**
     * Método para obtener el coste base de un vuelo de llegada.
     * 
     * @return El coste base de un vuelo de llegada.
     */
    public double getCosteBaseVueloLlegada() {
    	return costeBaseVueloLlegada;
    }
    
    /**
     * Método para obtener el recargo de un avión de mercancías.
     * 
     * @return El recargo de un avión de mercancías.
     */
    public double getRecargoAvionMercancias() {
    	return recargoAvionMercancias;
    }
    
    /**
     * Método para modificar el coste base de un vuelo de salida..
     * 
     * @param costeBaseVueloSalida El coste base de un vuelo de salida.
     */
    public void setCosteBaseVueloSalida(double costeBaseVueloSalida) {
        this.costeBaseVueloSalida = costeBaseVueloSalida;
    }

    /**
     * Método para modificar el coste base de un vuelo de llegada..
     * 
     * @param costeBaseVueloLlegada El coste base de un vuelo de llegada.
     */
    public void setCosteBaseVueloLlegada(double costeBaseVueloLlegada) {
        this.costeBaseVueloLlegada = costeBaseVueloLlegada;
    }

    /**
     * Método para modificar el recargo de un avión de mercancías.
     * 
     * @param recargoAvionMercancias El recargo de un avión de mercancías.
     */
    public void setRecargoAvionMercancias(double recargoAvionMercancias) {
        this.recargoAvionMercancias = recargoAvionMercancias;
    }
}
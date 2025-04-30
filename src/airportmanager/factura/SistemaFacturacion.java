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
     */
    public List<Factura> generarFactura(Aerolinea aerolinea) {
		List<Vuelo> vuelosMensuales = aerolinea.vuelosMensuales(LocalDate.now().getMonth());
		if (vuelosMensuales.isEmpty()) return null;
		
		Map<Aerolinea, Integer> totalBasePorAerolinea = new HashMap<>();
		Map<Aerolinea, Integer> totalRecargoPorAerolinea = new HashMap<>();
		Map<Aerolinea, List<IResourceUsageInfo>> recursosPorAerolinea = new HashMap<>();
		
		for (Vuelo vuelo : vuelosMensuales) {
		    int precioBase = 0;
		    int precioRecargo = 0;
		    List<IResourceUsageInfo> recursosVuelo = new ArrayList<>();
		
		    if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
		        recursosVuelo.add(new UsoRecursos("Recargo de mercancías", recargoAvionMercancias, 1));
		        precioRecargo += recargoAvionMercancias;
		    }
		    if (vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
		        recursosVuelo.add(new UsoRecursos("Vuelo de salida", costeBaseVueloSalida, 1));
		        precioBase += costeBaseVueloSalida;
		    } else if (vuelo.getTipoVuelo() == TipoVuelo.LLEGADA) {
		        recursosVuelo.add(new UsoRecursos("Vuelo de llegada", costeBaseVueloLlegada, 1));
		        precioBase += costeBaseVueloLlegada;
		    }
		
		    Map<Aerolinea, Double> porcentajePorAerolinea = new HashMap<>();
		
		    if (vuelo.getOperadoresAsignados() != null) {
		        for (Map.Entry<Operador, Double> entry : vuelo.getOperadoresAsignados().entrySet()) {
		            Aerolinea aero = entry.getKey().getAerolinea();
		            porcentajePorAerolinea.put(aero,
		                porcentajePorAerolinea.getOrDefault(aero, 0.0) + entry.getValue());
		        }
		    }
		
		    Aerolinea principal = vuelo.getOperadorPrincipal().getAerolinea();
		    double restante = 100.0 - porcentajePorAerolinea.values().stream().mapToDouble(Double::doubleValue).sum();
		    porcentajePorAerolinea.put(principal,
		        porcentajePorAerolinea.getOrDefault(principal, 0.0) + restante);
		
		    for (Map.Entry<Aerolinea, Double> entry : porcentajePorAerolinea.entrySet()) {
		        Aerolinea aero = entry.getKey();
		        double porcentaje = entry.getValue();
		
		        int base = (int) Math.round(precioBase * porcentaje / 100);
		        int recargo = (int) (precioRecargo * porcentaje / 100);
		
		        totalBasePorAerolinea.put(aero,
		            totalBasePorAerolinea.getOrDefault(aero, 0) + base);
		        totalRecargoPorAerolinea.put(aero,
		            totalRecargoPorAerolinea.getOrDefault(aero, 0) + recargo);
		
		        recursosPorAerolinea
		            .computeIfAbsent(aero, k -> new ArrayList<>())
		            .addAll(recursosVuelo);
		    }
		}
		
		List<Factura> facturas = new ArrayList<>();
		for (Aerolinea aero : totalBasePorAerolinea.keySet()) {
		    int base = totalBasePorAerolinea.get(aero);
		    int recargo = totalRecargoPorAerolinea.getOrDefault(aero, 0);
		    List<IResourceUsageInfo> recursos = recursosPorAerolinea.getOrDefault(aero, new ArrayList<>());
		
		    facturas.add(new Factura(aero,LocalDate.now(),base,recargo,generarCodigoFactura(aero),new ArrayList<>(recursos),EstadoFactura.PENDIENTE));
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
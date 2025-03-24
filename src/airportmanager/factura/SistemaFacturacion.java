package src.airportmanager.factura;

import java.util.*;
import java.io.Serializable;
import java.time.*;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;

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
    public Factura generarFactura(Aerolinea aerolinea) {
        List<Vuelo> vuelosMensuales = aerolinea.vuelosMensuales(LocalDate.now().getMonth());
        if (vuelosMensuales.isEmpty()) {
            return null;
        }

        int precioBase = 0;
        int precioRecargo = 0;
        List<IResourceUsageInfo> recursos = new ArrayList<>();
        for (Vuelo vuelo : vuelosMensuales) {
            if (vuelo.getTipoCarga() == TipoCarga.MERCANCIAS) {
                recursos.add(new UsoRecursos("Recargo de mercancías", this.recargoAvionMercancias, 1));
                precioRecargo += this.recargoAvionMercancias;
            }
            if (vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                recursos.add(new UsoRecursos("Vuelo de salida", this.costeBaseVueloSalida, 1));
                precioBase += this.costeBaseVueloSalida;
            } else if (vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                recursos.add(new UsoRecursos("Vuelo de llegada", this.costeBaseVueloLlegada, 1));
                precioBase += this.costeBaseVueloLlegada;
            }

            if (vuelo.getOperadoresAsignados() != null && !vuelo.getOperadoresAsignados().isEmpty()) {
                double porcentajeTotal = 0.0;

                for (Map.Entry<Operador, Double> entry : vuelo.getOperadoresAsignados().entrySet()) {
                    Operador operador = entry.getKey();
                    double porcentajeCarga = entry.getValue();
                    porcentajeTotal += porcentajeCarga;

                    if (!operador.getAerolinea().equals(aerolinea)) {
                        int precioProporcionalBase = (int) (precioBase * porcentajeCarga / 100);
                        int precioProporcionalRecargo = (int) (precioRecargo * porcentajeCarga / 100);

                        return new Factura(aerolinea, LocalDate.now(), precioProporcionalBase,
                                precioProporcionalRecargo,
                                generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE);
                    }
                }

                if (vuelo.getOperadorPrincipal().getAerolinea().equals(aerolinea)) {
                    int precioProporcionalBase = (int) (precioBase * (100 - porcentajeTotal) / 100);
                    int precioProporcionalRecargo = (int) (precioRecargo * (100 - porcentajeTotal) / 100);

                    return new Factura(aerolinea, LocalDate.now(), precioProporcionalBase, precioProporcionalRecargo,
                            generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE);
                }
            }

            if (vuelo.getOperadorPrincipal().getAerolinea().equals(aerolinea)) {
                precioBase += precioBase;
                precioRecargo += precioRecargo;
            }
        }

        return new Factura(aerolinea, LocalDate.now(), precioBase, precioRecargo, generarCodigoFactura(aerolinea),
                recursos, EstadoFactura.PENDIENTE);
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
}
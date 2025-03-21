package aeropuerto.src.carmen.factura;

import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.*;
import java.util.*;
import java.time.*;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;

public class SistemaFacturacion {
    private double costeBaseVueloSalida;
    private double costeBaseVueloLlegada;
    private double recargoAvionMercancias;
    private static int contadorFactura = 1;

    public SistemaFacturacion(double recargoAvionMercancias, double costeBaseVueloSalida,
            double costeBaseVueloLlegada) {
        this.recargoAvionMercancias = recargoAvionMercancias;
        this.costeBaseVueloLlegada = costeBaseVueloLlegada;
        this.costeBaseVueloSalida = costeBaseVueloSalida;
    }

    public Factura generarFactura(Aerolinea aerolinea) {
        List<VueloProgramado> vuelosMensuales= aerolinea.vuelosMensuales(LocalDate.now());
        if(vuelosMensuales.isEmpty()){
            return null;
        }

        int precioBase=0;
        int precioRecargo=0;
        List<IResourceUsageInfo> recursos = new ArrayList<>();
        for (VueloProgramado vuelo: vuelosMensuales) {
            if (vuelo.getTipoCarga()==TipoCarga.MERCANCIAS) {
                recursos.add(new UsoRecursos("Recargo de mercancías",this.recargoAvionMercancias, 1));
                precioRecargo += this.recargoAvionMercancias;
            }
            if(vuelo.getTipoVuelo()==TipoVuelo.SALIDA){
            	recursos.add(new UsoRecursos("Vuelo de salida", this.costeBaseVueloSalida, 1));
                precioBase+=this.costeBaseVueloSalida;
            }
            else if(vuelo.getTipoVuelo()==TipoVuelo.SALIDA){
            	recursos.add(new UsoRecursos("Vuelo de llegada", this.costeBaseVueloLlegada, 1));
                precioBase+=this.costeBaseVueloLlegada;
            }
        }

        return new Factura(aerolinea, LocalDate.now(), precioBase, precioRecargo, generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE);
    }

    public String generarCodigoFactura(Aerolinea aerolinea) {
        return aerolinea.getCodigo() + String.format("%06d", contadorFactura++);
    }
}
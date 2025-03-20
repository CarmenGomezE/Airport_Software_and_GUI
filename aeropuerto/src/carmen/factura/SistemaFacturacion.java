package aeropuerto.src.carmen.factura;

import aeropuerto.src.carmen.avion.*;
import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.*;
import java.util.*;
import java.time.*;

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

        int precioBase;
        int precioRecargo;
        List<IResourceUsageInfo> recursos = new ArrayList<>();
        for (VueloProgramado vuelo: vuelosMensuales) {
            if (vuelo.getTipoCarga()==TipoCarga.MERCANCIAS) {
                recursosUtilizados.add(new UsoRecursos("Recargo de mercancías",this.recargoMercancias, NO ENTIENDO));
                precioRecargo += this.recargoMercancias;
            }
            if(vuelo.getTipoVuelo()==TipoVuelo.SALIDA){
                recursosUtilizados.add(new UsoRecursos("Vuelo de salida", this.costeBaseVueloSalida, NO ENTIENDO));
                precioBase+=this.costeBaseVueloSalida;
            }
            else if(vuelo.getTipoVuelo()==TipoVuelo.SALIDA){
                recursosUtilizados.add(new UsoRecursos("Vuelo de llegada", this.costeBaseVueloLlegada, NO ENTIENDO));
                precioBase+=this.costeBaseVueloLlegada;
            }
        }

        return new Factura(aerolinea, LocalDate.now(), precioBase, precioRecargo, generarCodigoFactura(aerolinea), recursos, EstadoFactura.PENDIENTE);
    }

    public String generarCodigoFactura(Aerolinea aerolinea) {
        return aerolinea.getCodigo() + String.format("%06d", contadorFactura++);
    }
}
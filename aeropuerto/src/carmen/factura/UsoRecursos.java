package aeropuerto.src.carmen.factura;

import es.uam.eps.padsof.invoices.IResourceUsageInfo;

class UsoRecursos implements IResourceUsageInfo {
    private String descripcion;
    private double precioPorHora;
    private double tiempoDeUso;

    public UsoRecursos(String descripcion, double precioPorHora, String tiempoDeUso) {
        this.descripcion = descripcion;
        this.precioPorHora = precioPorHora;
        this.tiempoDeUso = tiempoDeUso;
    }

    @Override
    public String getResourceDescription() {
        return descripcion;
    }

    @Override
    public double getHourlyPrice() {
        return precioPorHora;
    }

    @Override
    public String getUsageTime() {
        return tiempoDeUso;
    }

    @Override
    public double getPrice() {
        return (this.tiempoDeUso * this.precioPorHora);
    }
}
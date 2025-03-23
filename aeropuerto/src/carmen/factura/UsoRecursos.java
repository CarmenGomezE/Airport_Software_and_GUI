package aeropuerto.src.carmen.factura;

import es.uam.eps.padsof.invoices.IResourceUsageInfo;

/**
 * La clase UsoRecurso implementa la interfaz de los recursos y representa un recurso utilizado.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
class UsoRecursos implements IResourceUsageInfo {
    private String descripcion;
    private double precioPorHora;
    private int tiempoDeUso;

    /**
     * Constructor de la clase UsoRecurso.
     * 
     * @param descripcion          	Descripción del resurso.
     * @param precioPorHora      	Precio por hora.
     * @param tiempoDeUso 	 		Tiempo de uso.
     */
    public UsoRecursos(String descripcion, double precioPorHora, int tiempoDeUso) {
        this.descripcion = descripcion;
        this.precioPorHora = precioPorHora;
        this.tiempoDeUso = tiempoDeUso;
    }

    
    /**
     * Método de la interfaz.
     * 
     * @return Descripcion del recurso.
     */
    public String getResourceDescription() {
        return this.descripcion;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Precio por hora del resurso.
     */
    public double getHourlyPrice() {
        return this.precioPorHora;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Tiempo de uso del recurso.
     */
    public String getUsageTime() {
        return String.valueOf(this.tiempoDeUso);
    }

    /**
     * Método de la interfaz.
     * 
     * @return Precio total del recurso.
     */
    public double getPrice() {
        return (this.tiempoDeUso * this.precioPorHora);
    }
}
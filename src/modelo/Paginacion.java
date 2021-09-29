package modelo;

public class Paginacion {
    
    private int cantPaginas;
    private int cantLineas;
    private double cantTotalMemoria;

    public Paginacion(int cantPaginas, int cantLineas, double cantTotalMemoria) {
        this.cantPaginas = cantPaginas;
        this.cantLineas = cantLineas;
        this.cantTotalMemoria = cantTotalMemoria;
    }

    public Paginacion() {
    }       

    public int getCantPaginas() {
        return cantPaginas;
    }

    public void setCantPaginas(int cantPaginas) {
        this.cantPaginas = cantPaginas;
    }

    public int getCantLineas() {
        return cantLineas;
    }

    public void setCantLineas(int cantLineas) {
        this.cantLineas = cantLineas;
    }

    public double getCantTotalMemoria() {
        return cantTotalMemoria;
    }

    public void setCantTotalMemoria(double cantTotalMemoria) {
        this.cantTotalMemoria = cantTotalMemoria;
    }           
}
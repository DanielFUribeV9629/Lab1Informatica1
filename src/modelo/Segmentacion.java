
package modelo;

public class Segmentacion {
    
    private double tamanoCodigo;
    private double baseCodigo;
    private double limiteCodigo;
    private double tamanoDatos;
    private double baseDatos;
    private double limiteDatos;
    private double tamanoPila;
    private double basePila;
    private double limitePila;
    private double cantTotalMemoria;

    public Segmentacion(double tamanoCodigo, double baseCodigo, double limiteCodigo, double tamanoDatos, double baseDatos, double limiteDatos, double tamanoPila, double basePila, double limitePila, double cantTotalMemoria) {
        this.tamanoCodigo = tamanoCodigo;
        this.baseCodigo = baseCodigo;
        this.limiteCodigo = limiteCodigo;
        this.tamanoDatos = tamanoDatos;
        this.baseDatos = baseDatos;
        this.limiteDatos = limiteDatos;
        this.tamanoPila = tamanoPila;
        this.basePila = basePila;
        this.limitePila = limitePila;
        this.cantTotalMemoria = cantTotalMemoria;
    }

    public Segmentacion() {
    }

    public double getTamanoCodigo() {
        return tamanoCodigo;
    }

    public void setTamanoCodigo(double tamanoCodigo) {
        this.tamanoCodigo = tamanoCodigo;
    }

    public double getBaseCodigo() {
        return baseCodigo;
    }

    public void setBaseCodigo(double baseCodigo) {
        this.baseCodigo = baseCodigo;
    }

    public double getLimiteCodigo() {
        return limiteCodigo;
    }

    public void setLimiteCodigo(double limiteCodigo) {
        this.limiteCodigo = limiteCodigo;
    }

    public double getTamanoDatos() {
        return tamanoDatos;
    }

    public void setTamanoDatos(double tamanoDatos) {
        this.tamanoDatos = tamanoDatos;
    }

    public double getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(double baseDatos) {
        this.baseDatos = baseDatos;
    }

    public double getLimiteDatos() {
        return limiteDatos;
    }

    public void setLimiteDatos(double limiteDatos) {
        this.limiteDatos = limiteDatos;
    }

    public double getTamanoPila() {
        return tamanoPila;
    }

    public void setTamanoPila(double tamanoPila) {
        this.tamanoPila = tamanoPila;
    }

    public double getBasePila() {
        return basePila;
    }

    public void setBasePila(double basePila) {
        this.basePila = basePila;
    }

    public double getLimitePila() {
        return limitePila;
    }

    public void setLimitePila(double limitePila) {
        this.limitePila = limitePila;
    }

    public double getCantTotalMemoria() {
        return cantTotalMemoria;
    }

    public void setCantTotalMemoria(double cantTotalMemoria) {
        this.cantTotalMemoria = cantTotalMemoria;
    }
    
    
    
    
}

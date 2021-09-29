package modelo;

public class Particion {
    
    private int multiplicadorParticion;
    private double tamanoMinimo;

    public Particion(int multiplicadorParticion, double tamanoMinimo) {
        this.multiplicadorParticion = multiplicadorParticion;
        this.tamanoMinimo = tamanoMinimo;
    }        

    public Particion() {
    }        

    public int getMultiplicadorParticion() {
        return multiplicadorParticion;
    }

    public void setMultiplicadorParticion(int multiplicadorParticion) {
        this.multiplicadorParticion = multiplicadorParticion;
    }

    public double getTamanoMinimo() {
        return tamanoMinimo;
    }

    public void setTamanoMinimo(double tamanoMinimo) {
        this.tamanoMinimo = tamanoMinimo;
    }        
    
}

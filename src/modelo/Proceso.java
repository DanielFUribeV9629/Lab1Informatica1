package modelo;

public class Proceso {
    private String nombre;
    private int tamData, tamCode, tamPila , idProc;

    public int getIdProc() {
        return idProc;
    }

    public void setIdProc(int idProc) {
        this.idProc = idProc;
    }

    public Proceso(String nombre, int tamData, int tamCode, int tamPila, int idProc) {
        this.nombre = nombre;
        this.tamData = tamData;
        this.tamCode = tamCode;
        this.tamPila = tamPila;
        this.idProc = idProc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTamData(int tamData) {
        this.tamData = tamData;
    }

    public void setTamCode(int tamCode) {
        this.tamCode = tamCode;
    }

    public void setTamPila(int tamPila) {
        this.tamPila = tamPila;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTamData() {
        return tamData;
    }

    public int getTamCode() {
        return tamCode;
    }

    public int getTamPila() {
        return tamPila;
    }

    @Override
    public String toString() {
        return nombre + "(" +String.valueOf(tamData+tamCode+tamPila) + ")" ; //To change body of generated methods, choose Tools | Templates.
    }
    
}

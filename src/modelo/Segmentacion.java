
package modelo;

import java.util.ArrayList;
import java.util.List;

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
    private List<Proceso> procesos;
    private List<Integer[]> segmentos;
    

    public Segmentacion(double tamanoCodigo, double baseCodigo, double limiteCodigo, double tamanoDatos, double baseDatos, double limiteDatos, double tamanoPila, double basePila, double limitePila, double cantTotalMemoria,List<Proceso> procesos, List<Integer[]> segmentos) {
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
        this.procesos = procesos;
        this.segmentos = segmentos;
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
    
    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public List<Proceso> getProcesos() {
        if(procesos == null){ procesos = new ArrayList<>();}
        return procesos;
    }

    public List<Integer[]> getSegmentos() {
        if(segmentos == null){ segmentos = new ArrayList<>();}
        return segmentos;
    }

    public void setSegmentos(List<Integer[]> segmentos) {
        this.segmentos = segmentos;
    }
    
    public Proceso agregarProceso(Proceso proceso){
        if(procesos == null){ procesos = new ArrayList<>();}
        if(segmentos == null){ segmentos = new ArrayList<>();}
        int key = procesos.size() + 1;
        
        int baseCodigoProceso = (int)baseCodigo;
        int limiteCodigoProceso = (int)baseCodigo + proceso.getTamCode();
        int baseDataProceso = (int)baseDatos;
        int limiteDataProceso = (int)baseDatos + proceso.getTamData();
        int basePilaProceso = (int)limitePila - proceso.getTamPila();
        int limitePilaProceso = (int)limitePila;
        
        Proceso procTran = new Proceso(proceso.getNombre(),proceso.getTamData(),proceso.getTamCode(),proceso.getTamPila(),key);
        if(limiteCodigoProceso > limiteCodigo) {procTran.setIdProc(0); return procTran;}
        if(limiteDataProceso > limiteDatos) {procTran.setIdProc(0); return procTran;}
        if(basePilaProceso < basePila) {procTran.setIdProc(0); return procTran;}
        
        procesos.add(procTran);
        segmentos.add( new Integer[]{1,key,baseCodigoProceso,limiteCodigoProceso} );
        segmentos.add( new Integer[]{2,key,baseDataProceso,limiteDataProceso} );
        segmentos.add( new Integer[]{3,key,basePilaProceso,limitePilaProceso} );
        this.baseCodigo = limiteCodigoProceso;
        this.baseDatos = limiteDataProceso;
        this.limitePila = basePilaProceso;
        return procTran;
    }
    
    public void limpiarProcesos(){
        procesos.clear();
        segmentos.clear();
        iniciar();
    }
    
    public void iniciar(){
        
        this.cantTotalMemoria = 16777216;
        this.baseCodigo = 2097153;
        this.limiteCodigo = 6291456;//4
        this.baseDatos = 6291457;
        this.limiteDatos = 10485760;//4
        this.basePila = 10485761;
        this.limitePila = 16777216;//6
    }
}

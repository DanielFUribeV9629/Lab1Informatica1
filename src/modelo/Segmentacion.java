
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
    private List<Integer[]> vacios;
    private String ajuste;

    public Segmentacion(double tamanoCodigo, double baseCodigo, double limiteCodigo, double tamanoDatos, double baseDatos, double limiteDatos, double tamanoPila, double basePila, double limitePila, double cantTotalMemoria,List<Proceso> procesos, List<Integer[]> segmentos,List<Integer[]> vacios, String ajuste) {
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
        this.vacios = vacios;
        this.ajuste = ajuste;
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

    public List<Integer[]> getVacios() {
        if(vacios == null){ vacios = new ArrayList<>();}
        return vacios;
    }

    public void setVacios(List<Integer[]> vacios) {
        this.vacios = vacios;
    }

    public String getAjuste() {
        return ajuste;
    }

    public void setAjuste(String ajuste) {
        this.ajuste = ajuste;
    }
    
    public Proceso agregarProceso(Proceso proceso){
        if(procesos == null){ procesos = new ArrayList<>();}
        if(segmentos == null){ segmentos = new ArrayList<>();}
        int key = procesos.size() + 1;
        int baseCodigoProceso = 0;
        int limiteCodigoProceso = 0;
        int baseDataProceso = 0;
        int limiteDataProceso = 0;
        int basePilaProceso = 0;
        int limitePilaProceso = 0;
        int[] secciones = {1,2,3};
        
        Proceso procTran = new Proceso(proceso.getNombre(),proceso.getTamData(),proceso.getTamCode(),proceso.getTamPila(),key);
        if (ajuste == "MEJOR_AJUSTE") {
            
            for(int item : secciones)
            {
                int ind1 = -1;
                for (int i = 0; i < vacios.size(); i++) {
                    int dif = 0 ;
                    int dif2 = 0;
                    switch (item) {
                        case 1:
                            dif=(vacios.get(i))[3] - procTran.getTamCode();
                            if(ind1>=0){
                            dif2=(vacios.get(ind1))[3] - procTran.getTamCode();}
                            else{dif2=procTran.getTamCode();}
                            break;
                        case 2:
                            dif=(vacios.get(i))[3] - procTran.getTamData();
                            if(ind1>=0){
                            dif2=(vacios.get(ind1))[3] - procTran.getTamCode();}
                            else{dif2=procTran.getTamCode();}
                            break;
                        case 3:
                            dif=(vacios.get(i))[3] - procTran.getTamPila();
                            if(ind1>=0){
                            dif2=(vacios.get(ind1))[3] - procTran.getTamCode();}
                            else{dif2=procTran.getTamCode();}
                            break;
                        default:
                            break;
                    }
                    
                    if ((vacios.get(i))[0] == item) {
                        if (ind1 == -1 && dif >= 0) {
                            ind1 = i;
                        } else if (dif >= 0 && dif < dif2) {
                            ind1 = i;
                        }
                    }
                }
                if(ind1==-1){procTran.setIdProc(0); return procTran;}
                switch(item){
                    case 1:
                        baseCodigoProceso = (vacios.get(ind1))[1];
                        limiteCodigoProceso = (vacios.get(ind1))[1] + proceso.getTamCode();
                        if(limiteCodigoProceso > limiteCodigo) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamCode()>0)
                        {
                            vacios.add( new Integer[]{1,(limiteCodigoProceso+1),(vacios.get(ind1))[2], ((vacios.get(ind1))[2]- (limiteCodigoProceso+1) )} );
                        }
                        vacios.remove(ind1);
                        break;
                    case 2:
                        baseDataProceso = (vacios.get(ind1))[1];
                        limiteDataProceso = (vacios.get(ind1))[1] + proceso.getTamData();
                        if(limiteDataProceso > limiteDatos) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamData()>0)
                        {
                            vacios.add( new Integer[]{2,(limiteDataProceso+1),(vacios.get(ind1))[2], ((vacios.get(ind1))[2]- (limiteDataProceso+1) )} );
                        }
                        vacios.remove(ind1);
                        break;
                    case 3:
                        basePilaProceso = (vacios.get(ind1))[2] - proceso.getTamPila();
                        limitePilaProceso = (vacios.get(ind1))[2];
                        if(basePilaProceso < basePila) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamCode()>0)
                        {
                            vacios.add( new Integer[]{3,(vacios.get(ind1))[1] ,(basePilaProceso-1), ((basePilaProceso-1) - (vacios.get(ind1))[1] )} );
                        }
                        vacios.remove(ind1);
                        break;
                }
                
            }
            
            procesos.add(procTran);
            segmentos.add( new Integer[]{1,key,baseCodigoProceso,limiteCodigoProceso} );
            segmentos.add( new Integer[]{2,key,baseDataProceso,limiteDataProceso} );
            segmentos.add( new Integer[]{3,key,basePilaProceso,limitePilaProceso} );
            
        }
        else if (ajuste == "PRIMER_AJUSTE") {
            
            for(int item : secciones)
            {
                int ind1 = -1;
                for (int i = 0; i < vacios.size(); i++) {
                    int dif = 0 ;
                    switch (item) {
                        case 1:
                            dif=(vacios.get(i))[3] - procTran.getTamCode();
                            break;
                        case 2:
                            dif=(vacios.get(i))[3] - procTran.getTamData();
                            break;
                        case 3:
                            dif=(vacios.get(i))[3] - procTran.getTamPila();
                            break;
                        default:
                            break;
                    }
                    
                    if ((vacios.get(i))[0] == item) {
                        if (ind1 == -1 && dif >= 0) {
                            ind1 = i;
                        } 
                    }
                }
                if(ind1==-1){procTran.setIdProc(0); return procTran;}
                switch(item){
                    case 1:
                        baseCodigoProceso = (vacios.get(ind1))[1];
                        limiteCodigoProceso = (vacios.get(ind1))[1] + proceso.getTamCode();
                        if(limiteCodigoProceso > limiteCodigo) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamCode()>0)
                        {
                            vacios.add( new Integer[]{1,(limiteCodigoProceso+1),(vacios.get(ind1))[2], ((vacios.get(ind1))[2]- (limiteCodigoProceso+1) )} );
                        }
                        vacios.remove(ind1);
                        break;
                    case 2:
                        baseDataProceso = (vacios.get(ind1))[1];
                        limiteDataProceso = (vacios.get(ind1))[1] + proceso.getTamData();
                        if(limiteDataProceso > limiteDatos) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamData()>0)
                        {
                            vacios.add( new Integer[]{2,(limiteDataProceso+1),(vacios.get(ind1))[2], ((vacios.get(ind1))[2]- (limiteDataProceso+1) )} );
                        }
                        vacios.remove(ind1);
                        break;
                    case 3:
                        basePilaProceso = (vacios.get(ind1))[2] - proceso.getTamPila();
                        limitePilaProceso = (vacios.get(ind1))[2];
                        if(basePilaProceso < basePila) {procTran.setIdProc(0); return procTran;}
                        if((vacios.get(ind1))[3] - procTran.getTamCode()>0)
                        {
                            vacios.add( new Integer[]{3,(vacios.get(ind1))[1] ,(basePilaProceso-1), ((basePilaProceso-1) - (vacios.get(ind1))[1] )} );
                        }
                        vacios.remove(ind1);
                        break;
                }
                
            }
            
            procesos.add(procTran);
            segmentos.add( new Integer[]{1,key,baseCodigoProceso,limiteCodigoProceso} );
            segmentos.add( new Integer[]{2,key,baseDataProceso,limiteDataProceso} );
            segmentos.add( new Integer[]{3,key,basePilaProceso,limitePilaProceso} );
        }
        else if (ajuste == "PEOR_AJUSTE") {
            baseCodigoProceso = (int)baseCodigo;
            limiteCodigoProceso = (int)baseCodigo + proceso.getTamCode();
            baseDataProceso = (int)baseDatos;
            limiteDataProceso = (int)baseDatos + proceso.getTamData();
            basePilaProceso = (int)limitePila - proceso.getTamPila();
            limitePilaProceso = (int)limitePila;
        
            
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
        }
        
        Hilos hilo = new Hilos(procTran.getNombre());
        hilo.run();
        return procTran;
    }
    
    public void CerrarProceso(Proceso proceso){
        int[] secciones = {1,2,3};
//        procesos.remove(proceso);
        for (int item : secciones) {
            for (int i = 0; i < segmentos.size(); i++) {
                if (proceso.getIdProc() == (segmentos.get(i))[1]) {
                    vacios.add(new Integer[]{(segmentos.get(i))[0], (segmentos.get(i))[2], (segmentos.get(i))[3], ((segmentos.get(i))[3] - (segmentos.get(i))[2])});
                    segmentos.remove(i);
                }
            }
        }
    }
    
    public void limpiarProcesos(){
        procesos.clear();
        segmentos.clear();
        vacios.clear();
        iniciar();
    }
    
    public void iniciar(){
        if(vacios == null){ vacios = new ArrayList<>();}
        this.cantTotalMemoria = 16777216;
        this.baseCodigo = 2097153;
        this.limiteCodigo = 6291456;//4
        this.baseDatos = 6291457;
        this.limiteDatos = 10485760;//4
        this.basePila = 10485761;
        this.limitePila = 16777216;//6
        vacios.add( new Integer[]{1,(int)this.baseCodigo,(int)this.limiteCodigo, (int)(this.limiteCodigo-this.baseCodigo)} );
        vacios.add( new Integer[]{2,(int)this.baseDatos,(int)this.limiteDatos, (int)(this.limiteDatos-this.baseDatos)} );
        vacios.add( new Integer[]{3,(int)this.basePila,(int)this.limitePila, (int)(this.limitePila-this.basePila)} );
    }
}

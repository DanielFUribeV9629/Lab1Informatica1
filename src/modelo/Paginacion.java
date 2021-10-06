package modelo;

import java.util.ArrayList;


public class Paginacion {
 
    private ArrayList<ProcesoPaginacion> procesos = new ArrayList<ProcesoPaginacion>();
    private int [][] particionesPEV = {
                                       {2097153, 6291457, 4194304,0}, 
                                       {6291458,9437186,3145728,0}, 
                                       {9437187,12582915,3145728,0},
                                       {12582916,14680068,2097152,0},
                                       {14680069,15728645,1048576,0},
                                       {15728646,16777222,1048576,0}};
    
    private int totalMemory=1048576*14;
    private int memorySO=1048576*2;

    public boolean Agregar_PEF (String nameProcess, int sizeProcess, int cantidad){   
        int tamParticion=totalMemory/cantidad;
        ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess,sizeProcess);
        
        System.out.println("Tamaño Particiones: "+String.valueOf(tamParticion));
        if(sizeProcess>(tamParticion)){
            return false;
        }else{
            int totalProcesos=procesos.size();
            int bytesIni=tamParticion*totalProcesos+memorySO+1;
            int bytesFin=bytesIni+sizeProcess;
            procesoTemp.setByteAddress1(bytesIni);
            procesoTemp.setByteAddress2(bytesFin);
            if (bytesFin>totalMemory+memorySO){
                return false;
            }else{
                procesos.add(procesoTemp);
                return true;
            }       
        }
        
        
    }
    
    
    public boolean Agregar_PEV (String nameProcess, int sizeProcess, String ajuste){
        int i=0;
        int flag=0;
        int indiceMax=0, indiceMin=0, tamMax=0, tamMin=999999999;
        
        ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess,sizeProcess);
        for (i=0; i < 6; i++){
           //System.out.println("Particion: "+i+" Estado: "+particionesPEV[i][3]+" Tamaño Proceso: "+sizeProcess+" Tamaño Particion: "+particionesPEV[i][2]);
           if(ajuste=="PRIMER_AJUSTE"){
               if(particionesPEV[i][3]==0 && particionesPEV[i][2]>=sizeProcess){
                   System.out.println("Se asigna en la Particion: "+i);
                   int bytesIni=particionesPEV[i][0];
                   int bytesFin=bytesIni+sizeProcess;
                   procesoTemp.setByteAddress1(bytesIni);
                   procesoTemp.setByteAddress2(bytesFin);
                   particionesPEV[i][3]=1;
                   procesos.add(procesoTemp);
                   flag=1;
                   break;
               }
           }else{
                //OTROS AJUSTES
                if(particionesPEV[i][3]==0 && particionesPEV[i][2]>=sizeProcess){
                    flag=1;
                    if(particionesPEV[i][2]<=tamMin){
                        tamMin=particionesPEV[i][2];
                        indiceMin=i;
                    }
                    if(particionesPEV[i][2]>=tamMax){
                        tamMax=particionesPEV[i][2];
                        indiceMax=i;
                    }
                }
           } 
           
        }
        if(flag==1){
            if(ajuste!="PRIMER_AJUSTE"){
                if(ajuste=="PEOR_AJUSTE"){
                    System.out.println("Se asigna en la Particion: "+indiceMax);
                    int bytesIni=particionesPEV[indiceMax][0];
                    int bytesFin=bytesIni+sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    particionesPEV[indiceMax][3]=1;
                    procesos.add(procesoTemp);
                }
                if(ajuste=="MEJOR_AJUSTE"){
                    System.out.println("Se asigna en la Particion: "+indiceMin);
                    int bytesIni=particionesPEV[indiceMin][0];
                    int bytesFin=bytesIni+sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    particionesPEV[indiceMin][3]=1;
                    procesos.add(procesoTemp);
                }
            }
            return true;
        }else{
            return false;
        }
            
        
        
    }
    
    public ArrayList<ProcesoPaginacion> getArray (){
        return procesos;
    }
    
    public void limpiar (){
        procesos.clear();
        particionesPEV[0][3]=0;
        particionesPEV[1][3]=0;
        particionesPEV[2][3]=0;
        particionesPEV[3][3]=0;
        particionesPEV[4][3]=0;
        particionesPEV[5][3]=0;
    }
    
}

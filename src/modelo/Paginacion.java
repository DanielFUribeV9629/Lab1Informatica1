package modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class Paginacion {

    private ArrayList<ProcesoPaginacion> procesos = new ArrayList<ProcesoPaginacion>();
    private int pages_taken = 0;
    private static int memPages, free_pages, process_pages, totalProcesos;
    private static String name_process;
    private static String page_frames[][];
    private int[][] particionesPEV = {
        {2097153, 6291457, 4194304, 0},
        {6291458, 9437186, 3145728, 0},
        {9437187, 12582915, 3145728, 0},
        {12582916, 14680068, 2097152, 0},
        {14680069, 15728645, 1048576, 0},
        {15728646, 16777222, 1048576, 0}};

    private int[][] particionesPEF;

    private int totalMemory = 1048576 * 14;
    private int memorySO = 1048576 * 2;

    public void Init_PEF(int cantidad) {

        int tamParticion = totalMemory / cantidad;
        particionesPEF = new int[cantidad][4];
        for (int i = 0; i < cantidad; i++) {
            particionesPEF[i][0] = tamParticion * i + memorySO + 1;
            particionesPEF[i][1] = 0;
            particionesPEF[i][2] = 0;
            particionesPEF[i][3] = 0;

        }
    }

    public boolean Agregar_PEF(String nameProcess, int sizeProcess, int cantidad) {
        int tamParticion = totalMemory / cantidad;
        ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess, sizeProcess);
        //System.out.println("Tamaño Particiones: " + String.valueOf(tamParticion));
        if (sizeProcess > (tamParticion)) {
            return false;
        } else {
            /*int totalProcesos = procesos.size();
            int bytesIni = tamParticion * totalProcesos + memorySO + 1;
            int bytesFin = bytesIni + sizeProcess;
            procesoTemp.setByteAddress1(bytesIni);
            procesoTemp.setByteAddress2(bytesFin);*/
            int flag = 0;
            for (int i = 0; i < cantidad; i++) {
                if (particionesPEF[i][3] == 0) {
                    int bytesIni = particionesPEF[i][0];
                    int bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    procesoTemp.setIndice(i);
                    procesos.add(procesoTemp);
                    flag = 1;
                    particionesPEF[i][3] = 1;
                    break;
                }
            }
            if (flag == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean Agregar_PEV(String nameProcess, int sizeProcess, String ajuste) {
        int i = 0;
        int flag = 0;
        int indiceMax = 0, indiceMin = 0, tamMax = 0, tamMin = 999999999;

        ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess, sizeProcess);
        for (i = 0; i < 6; i++) {
            //System.out.println("Particion: "+i+" Estado: "+particionesPEV[i][3]+" Tamaño Proceso: "+sizeProcess+" Tamaño Particion: "+particionesPEV[i][2]);
            if (ajuste == "PRIMER_AJUSTE") {
                if (particionesPEV[i][3] == 0 && particionesPEV[i][2] >= sizeProcess) {
                    System.out.println("Se asigna en la Particion: " + i);
                    int bytesIni = particionesPEV[i][0];
                    int bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    procesoTemp.setIndice(i);
                    particionesPEV[i][3] = 1;
                    procesos.add(procesoTemp);
                    flag = 1;
                    break;
                }
            } else {
                //OTROS AJUSTES
                if (particionesPEV[i][3] == 0 && particionesPEV[i][2] >= sizeProcess) {
                    flag = 1;
                    if (particionesPEV[i][2] <= tamMin) {
                        tamMin = particionesPEV[i][2];
                        indiceMin = i;
                    }
                    if (particionesPEV[i][2] >= tamMax) {
                        tamMax = particionesPEV[i][2];
                        indiceMax = i;
                    }
                }
            }

        }
        if (flag == 1) {
            if (ajuste != "PRIMER_AJUSTE") {
                if (ajuste == "PEOR_AJUSTE") {
                    System.out.println("Se asigna en la Particion: " + indiceMax);
                    int bytesIni = particionesPEV[indiceMax][0];
                    int bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    procesoTemp.setIndice(indiceMax);
                    particionesPEV[indiceMax][3] = 1;
                    procesos.add(procesoTemp);
                }
                if (ajuste == "MEJOR_AJUSTE") {
                    System.out.println("Se asigna en la Particion: " + indiceMin);
                    int bytesIni = particionesPEV[indiceMin][0];
                    int bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                    procesoTemp.setIndice(indiceMin);
                    particionesPEV[indiceMin][3] = 1;
                    procesos.add(procesoTemp);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean Agregar_PD(String nameProcess, int sizeProcess, String ajuste) {
        ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess, sizeProcess);
        int totalProcesos = 0;
        int bytesIni, bytesFin = 0;

        if (ajuste == "PRIMER_AJUSTE") {
            for (int i = 0; i < procesos.size(); i++) {
                totalProcesos = totalProcesos + procesos.get(i).getSize();
            }
            int availableMemory = totalMemory - totalProcesos;

            if (availableMemory < sizeProcess) {
                return false;
            } else {
                if (procesos.size() == 0) {
                    primerElemento(procesoTemp, sizeProcess);
                } else {
                    bytesIni = memorySO + totalProcesos;
                    bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                }
                if (bytesFin > totalMemory + memorySO) {
                    return false;
                } else {
                    System.out.println("Agregue proceso");
                    procesos.add(procesoTemp);
                    return true;
                }
            }
        } else if (ajuste == "PEOR_AJUSTE") {
            for (int i = 0; i < procesos.size(); i++) {
                totalProcesos = totalProcesos + procesos.get(i).getSize();
            }
            int availableMemory = totalMemory - totalProcesos;

            if (availableMemory < sizeProcess) {
                return false;
            } else {
                if (procesos.size() == 0) {
                    primerElemento(procesoTemp, sizeProcess);
                } else {
                    bytesIni = memorySO + totalProcesos;
                    bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                }
                if (bytesFin > totalMemory + memorySO) {
                    return false;
                } else {
                    System.out.println("Agregue proceso");
                    procesos.add(procesoTemp);
                    return true;
                }
            }
        } else if (ajuste == "MEJOR_AJUSTE") {
            for (int i = 0; i < procesos.size(); i++) {
                totalProcesos = totalProcesos + procesos.get(i).getSize();
            }
            int availableMemory = totalMemory - totalProcesos;

            if (availableMemory < sizeProcess) {
                return false;
            } else {
                if (procesos.size() == 0) {
                    primerElemento(procesoTemp, sizeProcess);
                } else {
                    bytesIni = memorySO + totalProcesos;
                    bytesFin = bytesIni + sizeProcess;
                    procesoTemp.setByteAddress1(bytesIni);
                    procesoTemp.setByteAddress2(bytesFin);
                }
                if (bytesFin > totalMemory + memorySO) {
                    return false;
                } else {
                    System.out.println("Agregue proceso");
                    procesos.add(procesoTemp);
                    return true;
                }
            }
        }
        return false;
    }

    public void primerElemento(ProcesoPaginacion procesoTemp, int sizeProcess) {
        int bytesIni = memorySO + 1;
        int bytesFin = bytesIni + sizeProcess;
        procesoTemp.setByteAddress1(bytesIni);
        procesoTemp.setByteAddress2(bytesFin);
    }

    public ArrayList<ProcesoPaginacion> getArray() {
        return procesos;
    }

    public void limpiar() {
        procesos.clear();
        particionesPEV[0][3] = 0;
        particionesPEV[1][3] = 0;
        particionesPEV[2][3] = 0;
        particionesPEV[3][3] = 0;
        particionesPEV[4][3] = 0;
        particionesPEV[5][3] = 0;
    }

    public void quitPEV(int indice) {
        //System.out.println("Cerrando el Proceso: "+indice);
        int indiceMatriz = procesos.get(indice).getIndice();
        procesos.remove(indice);
        particionesPEV[indiceMatriz][3] = 0;
    }

    public void quitPEF(int indice) {
        //System.out.println("Cerrando el Proceso: "+indice);
        int indiceMatriz = procesos.get(indice).getIndice();
        procesos.remove(indice);
        particionesPEF[indiceMatriz][3] = 0;
    }

    public void quitPD(int indice) {
        int byteIni, byteIniAnt, byteFin, cuenta = 0;
        for (int i = indice; i < procesos.size() - 1; i++) {

            if (cuenta == 0) {
                byteIniAnt = procesos.get(i - 1).getByteAddress2();
            } else {
                byteIniAnt = procesos.get(i).getByteAddress2();
            }

            byteIni = byteIniAnt;
            byteFin = byteIni + procesos.get(i + 1).getSize();
            procesos.get(i + 1).setByteAddress1(byteIni);
            procesos.get(i + 1).setByteAddress2(byteFin);
            cuenta++;
        }
        procesos.remove(indice);
    }

    public String[][] startMap(int mempages) {
        page_frames = new String[mempages][2];
        for (String[] row : page_frames) {
            Arrays.fill(row, "0");
        }
        return page_frames;
    }

    public void removeProcessPag(int indice) {
        ProcesoPaginacion selected_process=procesos.get(indice);
        String process_name = selected_process.getName();
        System.out.println("Cierra paginas: " + process_name);
        ArrayList<ProcesoPaginacion> process_pages = new ArrayList<ProcesoPaginacion>();
        // remove all pages from list
        for(ProcesoPaginacion page : procesos ){
            if(page.getName() == process_name){
                process_pages.add(page);
            }
        }
        procesos.removeAll(process_pages);
        pages_taken -= process_pages.size();
        // remove all pages from stack
        for(int x=0; x < page_frames.length; x++) {
            if (page_frames[x][1] == process_name) {
                page_frames[x][1] = "0";
                page_frames[x][0] = "0";
            }
        }
        //show_stats();
    }

    public boolean Agregar_PPAG(String nameProcess, int sizeProcess, int size_page) {
        int page_size = size_page * 1024;
        int frame_number = 0;
        name_process = nameProcess;
        memPages = totalMemory / page_size;
        // set memory frames
        int totalProcesos = procesos.size();
        if (totalProcesos == 0) {
            startMap(memPages);
        }
        // set pages by process
        if (sizeProcess % page_size > 0) {
            process_pages = sizeProcess / page_size + 1;
        } else {
            process_pages = sizeProcess / page_size;
        }

        if (process_pages <= memPages - pages_taken) {
            pages_taken += process_pages;
            free_pages = memPages - pages_taken;
            // locate each process pages
            for (int i = 0; i < process_pages; i++) {
                for (int x = 0; x < page_frames.length; x++) {
                    if (page_frames[x][0] == "0") {
                        page_frames[x][1] = name_process;
                        page_frames[x][0] = "1";
                        frame_number = x;
                        break;
                    }
                }
                int bytesIni = page_size * frame_number + memorySO + 1;
                int bytesFin = bytesIni + page_size;
                ProcesoPaginacion procesoTemp = new ProcesoPaginacion(nameProcess, sizeProcess);
                procesoTemp.setByteAddress1(bytesIni);
                procesoTemp.setByteAddress2(bytesFin);
                procesos.add(procesoTemp);
                show_stats();
            }
            return true;
        } else {
            show_stats();
            return false;
        }
    }

    private void show_stats() {
        System.out.println("===============================");
        totalProcesos = procesos.size();
        System.out.println("Total Procesos: " + String.valueOf(totalProcesos));
        System.out.println("Total Marcos de memoria: " + String.valueOf(memPages));
        System.out.println("Paginas proceso [" + name_process + "]:" + String.valueOf(process_pages));
        System.out.println("Marcos libres:" + String.valueOf(free_pages));
        System.out.println(Arrays.deepToString(page_frames));
    }

    public ProcesoPaginacion agregarProceso(ProcesoPaginacion current_process) {
        return current_process;
    }

}

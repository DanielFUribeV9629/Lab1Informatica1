package controlador;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelo.Paginacion;
import modelo.Proceso;
import modelo.ProcesoPaginacion;
import modelo.Segmentacion;
import vista.Vista;

public class ControladorParticion implements ActionListener {

    private Vista view;
    private Paginacion modelPaginacion;
    private Segmentacion segmentacion;

    DefaultListModel ListaProcesos = new DefaultListModel();
    DefaultListModel ListaProcesosAct = new DefaultListModel();

    public ControladorParticion(Vista view, Paginacion modelPaginacion) {
        this.view = view;
        this.modelPaginacion = modelPaginacion;
        this.segmentacion = new Segmentacion();
        this.view.IniciarProceso.addActionListener(this);
        this.view.CancelarProceso.addActionListener(this);
        this.view.IniciarModelo.addActionListener(this);
        this.view.DetenerModelo.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Asignación de Memoria");
        view.setLocationRelativeTo(null);
        view.DetenerModelo.setEnabled(false);
        view.IniciarProceso.setEnabled(false);
        view.CancelarProceso.setEnabled(false);
        segmentacion.iniciar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        int tab = view.jTabbedPane2.getSelectedIndex();
        //Rutina que Realiza el Precargue de Procesos
        if (source.getText() == "INICIAR") {
            if (view.PEF_npar.getText().isEmpty() && tab == 0) {
                System.out.println("entreeeeeeee");
                JOptionPane.showMessageDialog(null, "Debe ingresar la Cantidad de Particiones.");
            }else{
                if(tab==1 && !view.MejorAjuste.isSelected() && !view.PrimerAjuste.isSelected() 
                        && !view.PeorAjuste.isSelected()){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar alguno de los ajustes.");
                }else if (tab==2 && !view.MejorAjuste.isSelected() && !view.PrimerAjuste.isSelected() 
                        && !view.PeorAjuste.isSelected()){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar alguno de los ajustes.");
                } else {
                    view.jTabbedPane2.setEnabled(false);
                    view.IniciarModelo.setEnabled(false);
                    view.DetenerModelo.setEnabled(true);
                    view.IniciarProceso.setEnabled(true);
                    view.CancelarProceso.setEnabled(true);
                    view.MejorAjuste.setEnabled(false);
                    view.PeorAjuste.setEnabled(false);
                    view.PrimerAjuste.setEnabled(false);
                    cargarProcesos();
                    if (tab == 0) {
                        int cantidad = Integer.parseInt(view.PEF_npar.getText());
                        modelPaginacion.Init_PEF(cantidad);
                    }
                }
            }
        }
        if (source.getText() == "DETENER") {
            System.out.println("Entra en Detener");
            if (view.jTabbedPane2.getSelectedIndex() == 0 || view.jTabbedPane2.getSelectedIndex() == 1){
                limpiaMemoria();
            }
            view.jTabbedPane2.setEnabled(true);
            view.IniciarModelo.setEnabled(true);
            view.DetenerModelo.setEnabled(false);
            view.IniciarProceso.setEnabled(false);
            view.CancelarProceso.setEnabled(false);
            view.MejorAjuste.setEnabled(true);
            view.PeorAjuste.setEnabled(true);
            view.PrimerAjuste.setEnabled(true);
            ListaProcesos.clear();
            ListaProcesosAct.clear();
            modelPaginacion.limpiar();
            if (view.jTabbedPane2.getSelectedIndex() != 3 || view.jTabbedPane2.getSelectedIndex() != 4) {
               // modelPaginacion.limpiar();
            } 
            if (view.jTabbedPane2.getSelectedIndex() == 3) {
                segmentacion.limpiarProcesos();
            }
            
        }
        if (source.getText() == "Cancelar Proceso") {
            System.out.println("Entra en Cancelar Proceso");
            int index2 = view.jList2.getSelectedIndex();
            switch (tab) {
                case 0:
                    modelPaginacion.quitPEF(index2);
                    cargarActivos();                    
                    break;
                case 1:
                    modelPaginacion.quitPEV(index2);
                    cargarActivos();
                    break;
                case 2:
                    modelPaginacion.quitPD_compac(index2);
                    cargarActivos();
                    break;
                case 3:
                    Proceso procesoSeg = (Proceso) ListaProcesosAct.getElementAt(index2);
                    segmentacion.CerrarProceso(procesoSeg);
                    ListaProcesosAct.remove(index2);
                    dibujarMemoria();
                    break;
                case 4:
                    modelPaginacion.removeProcessPag(index2);
                    cargarActivosPag();
                    break;
            }
        }
        if (source.getText() == "Elegir Proceso") {
            //SE AGREGA EL PROCESO SELECCIONADO
            int index = view.jList1.getSelectedIndex();
            String ajuste = "";
            boolean flag;

            switch (tab) {
                case 0:
                    System.out.println("Proceso de PEF");
                    ProcesoPaginacion proceso = (ProcesoPaginacion) ListaProcesos.getElementAt(index);
                    int cantidad = Integer.parseInt(view.PEF_npar.getText());
                    flag = modelPaginacion.Agregar_PEF(proceso.getName(), proceso.getSize(), cantidad);
                    if (flag == false) {
                        JOptionPane.showMessageDialog(null, "Memoria Insuficiente");
                    } else {
                        cargarActivos();
                    }
                    break;
                case 1:
                    System.out.println("Proceso de PEV");
                    ProcesoPaginacion proceso1 = (ProcesoPaginacion) ListaProcesos.getElementAt(index);
                    if (view.MejorAjuste.isSelected()) {
                        ajuste = "MEJOR_AJUSTE";
                    } else if (view.PrimerAjuste.isSelected()) {
                        ajuste = "PRIMER_AJUSTE";
                    } else if (view.PeorAjuste.isSelected()) {
                        ajuste = "PEOR_AJUSTE";
                    }
                    flag = modelPaginacion.Agregar_PEV(proceso1.getName(), proceso1.getSize(), ajuste);
                    if (flag == false) {
                        JOptionPane.showMessageDialog(null, "Memoria Insuficiente");
                    } else {
                        cargarActivos();
                    }
                    break;
                case 2:
                    System.out.println("Proceso de PD");
                    boolean compactacion = true;
                    ProcesoPaginacion proceso2 = (ProcesoPaginacion) ListaProcesos.getElementAt(index);
                    
                    if (view.CompactacionSi.isSelected()){
                        compactacion =true;
                    }else if (view.CompactacionNo.isSelected()){
                        compactacion = false;
                    }
                    ///---------------
                    if (view.MejorAjuste.isSelected()) {
                        ajuste = "MEJOR_AJUSTE";
                    } else if (view.PrimerAjuste.isSelected()) {
                        ajuste = "PRIMER_AJUSTE";
                    } else if (view.PeorAjuste.isSelected()) {
                        ajuste = "PEOR_AJUSTE";
                    }
                    if (compactacion){
                        flag = modelPaginacion.Agregar_PD_compacSi(proceso2.getName(), proceso2.getSize(), ajuste);
                    }else{
                        flag = modelPaginacion.Agregar_PD_compacNo(proceso2.getName(), proceso2.getSize(), ajuste);
                    }                                             
                    if (flag == false) {
                        JOptionPane.showMessageDialog(null, "Memoria Insuficiente");
                    } else {
                        cargarActivos();
                    }
                    break;
                case 3:
                    System.out.println("Proceso de Segmentación");
                    Proceso procesoSeg = (Proceso) ListaProcesos.getElementAt(index);
                    if (view.MejorAjuste.isSelected()) {
                        ajuste = "MEJOR_AJUSTE";
                    } else if (view.PrimerAjuste.isSelected()) {
                        ajuste = "PRIMER_AJUSTE";
                    } else if (view.PeorAjuste.isSelected()) {
                        ajuste = "PEOR_AJUSTE";
                    }
                    segmentacion.setAjuste(ajuste);
                    Proceso procesoEnMemoria = segmentacion.agregarProceso(procesoSeg);
                    if (procesoEnMemoria.getIdProc() > 0) {
                        cargarActivosSeg();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error de segmentación de memoria");
                    }
                    break;
                case 4:
                    System.out.println("Proceso de Paginación");
                    ProcesoPaginacion procesoPaginacion = (ProcesoPaginacion) ListaProcesos.getElementAt(index);
                    ProcesoPaginacion process_loaded = modelPaginacion.agregarProceso(procesoPaginacion);
                    int size_pag = Integer.parseInt(view.page_size.getText());
                    flag = modelPaginacion.Agregar_PPAG(process_loaded.getName(), process_loaded.getSize(), size_pag);
                    if (flag == false) {
                        JOptionPane.showMessageDialog(null, "Error de paginacion - Memoria Insuficiente");
                    } else {
                        cargarActivosPag();
                    }
                    break;
            }
        }
    }
    
    

public void cargarProcesos() {
        view.jList1.setModel(ListaProcesos);
        ListaProcesos.clear();
        if (view.jTabbedPane2.getSelectedIndex() == 3) {
            ListaProcesos.addElement(new Proceso("Proceso_1", 1048576, 1048576, 1048576, 0));
            ListaProcesos.addElement(new Proceso("Proceso_2", 1048576/2, 1048576/2, 1048576/2, 0));
            ListaProcesos.addElement(new Proceso("Proceso_3", 1048576/3, 1048576/3, 1048576/3, 0));
            ListaProcesos.addElement(new Proceso("Proceso_4", 1048576/4, 1048576/4, 1048576/4, 0));
            ListaProcesos.addElement(new Proceso("Proceso_5", 1048576/4, 1048576/3, 1048576/5, 0));
            ListaProcesos.addElement(new Proceso("Proceso_6", 1048576/2, 1048576/4, 1048576/3, 0));
        } else {
            ListaProcesos.addElement(new ProcesoPaginacion("Word", 1048576));
            ListaProcesos.addElement(new ProcesoPaginacion("Excel", 1048576 * 2));
            ListaProcesos.addElement(new ProcesoPaginacion("Adobe Reader", 1048576));
            ListaProcesos.addElement(new ProcesoPaginacion("NetBeans", 1048576 * 3));
            ListaProcesos.addElement(new ProcesoPaginacion("IntelliJ", 1048576 * 3));
            ListaProcesos.addElement(new ProcesoPaginacion("uTorrent", 1048576 * 2));
            ListaProcesos.addElement(new ProcesoPaginacion("Sublime Text", 524288));
            ListaProcesos.addElement(new ProcesoPaginacion("Android Studio", 6291456));
        }
    }

    public void cargarActivos() {
        view.jList2.setModel(ListaProcesosAct);
        ListaProcesosAct.clear();
        for (int i = 0; i < modelPaginacion.getArray().size(); i++) {
            ListaProcesosAct.addElement(modelPaginacion.getArray().get(i));
        }
        dibujarProcesos();
    }

    public void cargarActivosSeg() {
        view.jList2.setModel(ListaProcesosAct);
        ListaProcesosAct.clear();
        segmentacion.getProcesos().forEach(item -> {
            ListaProcesosAct.addElement(item);
        });
        dibujarMemoria();
    }

    public void cargarActivosPag() {
        view.jList2.setModel(ListaProcesosAct);
        ListaProcesosAct.clear();
        for (int i = 0; i < modelPaginacion.getArray().size(); i++) {
            ListaProcesosAct.addElement(modelPaginacion.getArray().get(i));
        }
        dibujarProcesos();
    }

    public void dibujarProcesos() {
        double pos1 = 0;
        double pos2 = 0;
        int totalMemory = 1048576 * 14;
        int memorySO = 1048576 * 2;
        Canvas papel = view.canvas1;
        Graphics lienzo = papel.getGraphics();
        BufferedImage dobleBuffer = new BufferedImage(papel.getWidth(), papel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics lapiz = dobleBuffer.getGraphics();
        lapiz.clearRect(0, 0, papel.getWidth(), papel.getHeight());
        lienzo.drawImage(dobleBuffer, 0, 0, papel);
        int canvaW = papel.getWidth();
        int canvaH = papel.getHeight();
        //Pinta Memoria
        lapiz.setColor(Color.GREEN);
        lapiz.fillRect(0, 0, canvaW, canvaH);
        lapiz.drawRect(0, 0, canvaW, canvaH);

        //Pinta SO
        lapiz.setColor(Color.BLUE);
        pos1 = 0;
        pos2 = (2097152 * canvaH) / (1048576 * 16);
        lapiz.fillRect(0, (int) pos1, canvaW, (int) pos2);
        lapiz.drawRect(0, (int) pos1, canvaW, (int) pos2);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("SO", canvaW / 2, (int) (pos2 / 2));

        //Pinta Procesos
        for (int i = 0; i < modelPaginacion.getArray().size(); i++) {
            String name = modelPaginacion.getArray().get(i).getName();
            long p1 = modelPaginacion.getArray().get(i).getByteAddress1();
            long p2 = modelPaginacion.getArray().get(i).getByteAddress2();
            pos1 = (p1 * canvaH) / (1048576 * 16);
            pos2 = ((p2 * canvaH) / (1048576 * 16) - pos1);
            System.out.println("Dibuja 1: " + pos1 + " Dibuja 2: " + pos2);
            lapiz.setColor(Color.WHITE);
            lapiz.fillRect(0, (int) pos1, canvaW, (int) pos2);
            lapiz.drawRect(0, (int) pos1, canvaW, (int) pos2);
            lapiz.setColor(Color.BLACK);
            lapiz.drawString(String.valueOf(i + 1) + " " + name, canvaW / 2, (int) pos1 + (int) (pos2 / 2));
        }

        //Pinta Separaciones
        lapiz.setColor(Color.RED);
        if (view.jTabbedPane2.getSelectedIndex() == 0) {

            int cantidad = Integer.parseInt(view.PEF_npar.getText());
            double tamParticion = totalMemory / cantidad;
            for (int i = 0; i <= cantidad + 1; i++) {
                long bitsTemp = (int) (tamParticion * i) + memorySO;
                lapiz.fillRect(0, (int) bitsTopixel(bitsTemp, canvaH), canvaW, 2);
                System.out.println("Pinta Lineas Proceso de PEF: " + bitsTemp + " ");
            }
        } else if (view.jTabbedPane2.getSelectedIndex() == 1) {
            lapiz.fillRect(0, (int) bitsTopixel(2097153, canvaH), canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(6291458, canvaH), canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(9437187, canvaH), canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(12582916, canvaH), canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(14680069, canvaH), canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(15728646, canvaH), canvaW, 2);
        } else if (view.jTabbedPane2.getSelectedIndex() == 2) {
            lapiz.fillRect(0, (int) bitsTopixel(2097153, canvaH), canvaW, 2);            
        }
        lienzo.drawImage(dobleBuffer, 0, 0, papel);
    }

    public long bitsTopixel(long bits, int canvaH) {
        return (long) (bits * canvaH) / (1048576 * 16);
    }

    public void dibujarMemoria() //para segmentacion
    {
        Canvas papel = view.canvas1;
        Graphics lienzo = papel.getGraphics();
        BufferedImage dobleBuffer = new BufferedImage(papel.getWidth(), papel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics lapiz = dobleBuffer.getGraphics();
        lapiz.clearRect(0, 0, papel.getWidth(), papel.getHeight());
        lapiz.setColor(Color.white);
        int canvaW = papel.getWidth();
        int canvaH = papel.getHeight();

        double totalMem = segmentacion.getCantTotalMemoria();
        List<Integer[]> segmentos = segmentacion.getSegmentos();

        segmentos.forEach(item -> {
            double procY1 = (((double) item[2] * (double) canvaH) / totalMem);
            double procY2 = (((double) item[3] * (double) canvaH) / totalMem);
            if (item[0] == 1) {
                lapiz.setColor(Color.CYAN);
            }
            if (item[0] == 2) {
                lapiz.setColor(Color.RED);
            }
            if (item[0] == 3) {
                lapiz.setColor(Color.GREEN);
            }

            lapiz.fillRect(0, (int) procY1, canvaW, (int) procY2 - (int) procY1);
            lapiz.setColor(Color.WHITE);
            lapiz.drawRect(0, (int) procY1, canvaW, (int) procY2 - (int) procY1);
            lapiz.setColor(Color.BLACK);
            lapiz.drawString(String.valueOf(item[1]), canvaW / 2, (int) procY2);
            //lapiz.drawLine(0, (int)procY1, canvaW, (int)procY1);
            //lapiz.drawLine(0, (int)procY2, canvaW, (int)procY2);
        });

        lienzo.drawImage(dobleBuffer, 0, 0, papel);
    }
    
    public void limpiaMemoria() {
        System.out.println("---Limpia la memoria----");
        Canvas papel = view.canvas1;
        Graphics lienzo = papel.getGraphics();
        BufferedImage dobleBuffer = new BufferedImage(papel.getWidth(), papel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics lapiz = dobleBuffer.getGraphics();
        lapiz.clearRect(0, 0, papel.getWidth(), papel.getHeight());
        lienzo.drawImage(dobleBuffer, 0, 0, papel);
        int canvaW = papel.getWidth();
        int canvaH = papel.getHeight();
        //Pinta Memoria
        lapiz.setColor(Color.GREEN);
        lapiz.fillRect(0, 0, canvaW, canvaH);
        lapiz.drawRect(0, 0, canvaW, canvaH);

        lienzo.drawImage(dobleBuffer, 0, 0, papel);
        
        
    }
    
    

}

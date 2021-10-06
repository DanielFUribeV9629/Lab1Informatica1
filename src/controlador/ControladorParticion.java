
package controlador;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelo.Paginacion;
import modelo.ProcesoPaginacion;
import vista.Vista;

public class ControladorParticion implements ActionListener {
    private Vista view;
    private Paginacion modelPaginacion;
    DefaultListModel ListaProcesos = new DefaultListModel();
    DefaultListModel ListaProcesosAct = new DefaultListModel();
    
    public ControladorParticion(Vista view, Paginacion modelPaginacion){
        this.view=view;
        this.modelPaginacion=modelPaginacion;
        this.view.IniciarProceso.addActionListener(this);
        this.view.IniciarModelo.addActionListener(this);
        this.view.DetenerModelo.addActionListener(this);
    }
    
    public void iniciar(){
        view.setTitle("Asignación de Memoria");
        view.setLocationRelativeTo(null); 
        view.DetenerModelo.setEnabled(false);
        view.CancelarProceso.setEnabled(false);
        view.IniciarProceso.setEnabled(false);
        view.CancelarProceso.setEnabled(false);   
    }
    
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        //Rutina que Realiza el Precargue de Procesos
        if(source.getText()=="INICIAR"){
            view.jTabbedPane2.setEnabled(false);
            view.IniciarModelo.setEnabled(false);
            view.DetenerModelo.setEnabled(true);
            view.IniciarProceso.setEnabled(true);
            view.MejorAjuste.setEnabled(false);
            view.PeorAjuste.setEnabled(false);
            view.PrimerAjuste.setEnabled(false);
            cargarProcesos();   
        }
        if(source.getText()=="DETENER"){
            view.jTabbedPane2.setEnabled(true);
            view.IniciarModelo.setEnabled(true);
            view.DetenerModelo.setEnabled(false);
            view.IniciarProceso.setEnabled(false);
            view.MejorAjuste.setEnabled(true);
            view.PeorAjuste.setEnabled(true);
            view.PrimerAjuste.setEnabled(true);
            ListaProcesos.clear();
            ListaProcesosAct.clear();  
            modelPaginacion.limpiar();
        } 
        if(source.getText()=="Elegir Proceso"){
            //SE AGREGA EL PROCESO SELECCIONADO
            int index= view.jList1.getSelectedIndex();
            ProcesoPaginacion proceso=(ProcesoPaginacion)ListaProcesos.getElementAt(index);
            
            if (view.PEF_npar.getText().length()>0){
                System.out.println("Proceso de PEF");
                
                int cantidad=Integer.parseInt(view.PEF_npar.getText());
                boolean flag=modelPaginacion.Agregar_PEF(proceso.getName(),proceso.getSize(),cantidad);
                if(flag==false){
                    JOptionPane.showMessageDialog(null, "Memoria Insuficiente");
                }else{
                    cargarActivos();
                }
            }else {
                System.out.println("Proceso de PEV");
                String ajuste="";  
                if(view.MejorAjuste.isSelected()){
                    ajuste="MEJOR_AJUSTE";
                }else if (view.PrimerAjuste.isSelected()){
                    ajuste="PRIMER_AJUSTE";
                }else if (view.PeorAjuste.isSelected()){
                    ajuste="PEOR_AJUSTE";
                }
                boolean flag=modelPaginacion.Agregar_PEV(proceso.getName(),proceso.getSize(), ajuste);
                if(flag==false){
                    JOptionPane.showMessageDialog(null, "Memoria Insuficiente");
                }else{
                    cargarActivos();
                } 
            }  
        } 
    }
    
    public void cargarProcesos(){
        view.jList1.setModel(ListaProcesos);
        ListaProcesos.clear();
        ListaProcesos.addElement(new ProcesoPaginacion("Word", 1048576));
        ListaProcesos.addElement(new ProcesoPaginacion("Excel", 1048576*2));
        ListaProcesos.addElement(new ProcesoPaginacion("Adobe Reader", 1048576));
        ListaProcesos.addElement(new ProcesoPaginacion("NetBeans", 1048576*3));  
    }
    
    public void cargarActivos(){
        view.jList2.setModel(ListaProcesosAct);
        ListaProcesosAct.clear();
        for (int i=0; i < modelPaginacion.getArray().size(); i++){
            ListaProcesosAct.addElement(modelPaginacion.getArray().get(i));    
        }  
        dibujarProcesos();
    }
    
    public void dibujarProcesos(){
        double pos1=0;
        double pos2=0;
        int totalMemory=1048576*14;
        int memorySO=1048576*2;
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
        pos1=0;
        pos2=(2097152*canvaH)/(1048576*16);
        lapiz.fillRect(0, (int) pos1, canvaW, (int) pos2);
        lapiz.drawRect(0, (int) pos1, canvaW, (int) pos2);
        lapiz.setColor(Color.WHITE);
        lapiz.drawString("SO", canvaW/2 , (int) (pos2/2));
        
        //Pinta Procesos
        for (int i=0; i < modelPaginacion.getArray().size(); i++){
            String name = modelPaginacion.getArray().get(i).getName();
            long p1=modelPaginacion.getArray().get(i).getByteAddress1();
            long p2=modelPaginacion.getArray().get(i).getByteAddress2();
            pos1=(p1*canvaH)/(1048576*16);
            pos2=((p2*canvaH)/(1048576*16)-pos1);
            System.out.println("Dibuja 1: "+pos1+" Dibuja 2: "+pos2);
            lapiz.setColor(Color.WHITE);
            lapiz.fillRect(0, (int) pos1, canvaW, (int) pos2);
            lapiz.drawRect(0, (int) pos1, canvaW, (int) pos2);
            lapiz.setColor(Color.BLACK);
            lapiz.drawString(String.valueOf(i+1)+" "+name, canvaW/2 , (int) pos1+(int) (pos2/2));
        } 
        
        //Pinta Separaciones
        if (view.PEF_npar.getText().length()>0){
            
            int cantidad=Integer.parseInt(view.PEF_npar.getText());
            double tamParticion=totalMemory/cantidad;
            for(int i=0; i<=cantidad+1; i++){
                lapiz.setColor(Color.RED);
                long bitsTemp=(int)(tamParticion*i)+memorySO;
                lapiz.fillRect(0, (int) bitsTopixel(bitsTemp,canvaH),canvaW, 2);  
                System.out.println("Pinta Lineas Proceso de PEF: "+bitsTemp+" ");
            }
        }else{
            lapiz.setColor(Color.RED);
            lapiz.fillRect(0, (int) bitsTopixel(2097153,canvaH),canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(6291458,canvaH),canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(9437187,canvaH),canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(12582916,canvaH),canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(14680069,canvaH),canvaW, 2);
            lapiz.fillRect(0, (int) bitsTopixel(15728646,canvaH),canvaW, 2);
        }
        lienzo.drawImage(dobleBuffer, 0, 0, papel);
    }
    
    public long bitsTopixel(long bits, int canvaH){
        return (long) (bits*canvaH)/(1048576*16);
    }
    
}

package lab1informa1;


import controlador.ControladorParticion;
import modelo.Paginacion;
import vista.Vista;

public class Lab1Informa1 {
    public static void main(String[] args) {
        Paginacion mod= new Paginacion();
        Vista view = new Vista();
        ControladorParticion control= new ControladorParticion(view, mod);
        control.iniciar();
        view.setVisible(true);   
    }
}

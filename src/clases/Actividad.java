/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Miguel
 */
public class Actividad {
    private int numeroDeActividad;
    private String nombreDeTarea;
    private double duracion;
    private List<Actividad> predecesoras;    
    private ComboBox comboAgregar;
    private Button botonEliminar;

    private static int indice = -1;
    public Actividad() {
        indice++;
        numeroDeActividad = indice;
        predecesoras = new LinkedList<>();
    }
      public Actividad(String nombreDeTarea, double duracion) {
        indice++;
        this.numeroDeActividad = indice;
        this.nombreDeTarea = nombreDeTarea;
        this.duracion = duracion;
        predecesoras = new LinkedList<>();
    }

    public Actividad(int numeroDeActividad, String nombreDeTarea, double duracion) {
        indice++;
        this.numeroDeActividad = numeroDeActividad;
        this.nombreDeTarea = nombreDeTarea;
        this.duracion = duracion;
        predecesoras = new LinkedList<>();
    }
    
    
    
    public int getNumeroDeActividad() {
        return numeroDeActividad;
    }

    public void setNumeroDeActividad(int numeroDeActividad) {
        this.numeroDeActividad = numeroDeActividad;
    }

    public String getNombreDeTarea() {
        return nombreDeTarea;
    }

    public void setNombreDeTarea(String nombreDeTarea) {
        this.nombreDeTarea = nombreDeTarea;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }
    
    public void agregarPredecesora(Actividad predecesor){
        this.predecesoras.add(predecesor);
    }
    
    public void eliminarPredecesora(Actividad predecesor){
        this.predecesoras.remove(predecesor);
    }
    
    public ComboBox getCombo() {
        return comboAgregar;
    }

    public void setCombo(ComboBox combo) {
        this.comboAgregar = combo;
    }

    public Button getBotonEliminar() {
        return botonEliminar;
    }

    public void setBotonEliminar(Button botonEliminar) {
        this.botonEliminar = botonEliminar;
    }

    public List<Actividad> getPredecesoras() {
        return predecesoras;
    }

    public static void reducirIndice(){
        indice--;
        System.out.println("NUEVO INDICE: "+indice);
    }
    
    @Override
    public String toString() {
        return "Actividad{" + "numeroDeActividad=" + numeroDeActividad + 
                ", nombreDeTarea=" + nombreDeTarea + ", duracion=" + duracion 
                + ", predecesoras=" + predecesoras  + '}';
    }
    
   public static void resetearInidice(){
       indice = -1;
   }
    
}

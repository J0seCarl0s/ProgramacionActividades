/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel
 */
public class CMP {

    public static void main(String[] args) {
        /*List<Actividad> actividades = new LinkedList<>();
        Actividad act0 = new Actividad(0, "A", 5.0);
        Actividad act1 = new Actividad(1, "B", 6.0);
        Actividad act2 = new Actividad(2, "C", 4.0);
        Actividad act3 = new Actividad(3, "D", 3.0);
        Actividad act4 = new Actividad(4, "E", 1.0);
        Actividad act5 = new Actividad(5, "F", 4.0);
        Actividad act6 = new Actividad(6, "G", 14.0);
        Actividad act7 = new Actividad(7, "H", 12.0);
        Actividad act8 = new Actividad(8, "I", 2.0);

        act0.getPredecesoras().add(act2);
        act0.getPredecesoras().add(act3);
        act0.getPredecesoras().add(act4);
        act1.getPredecesoras().add(act7);
        act2.getPredecesoras().add(act7);
        act3.getPredecesoras().add(act6);
        act4.getPredecesoras().add(act5);
        act5.getPredecesoras().add(act6);
        act6.getPredecesoras().add(act8);
        act7.getPredecesoras().add(act8);
        actividades.add(act0);
        actividades.add(act1);
        actividades.add(act2);
        actividades.add(act3);
        actividades.add(act4);
        actividades.add(act5);
        actividades.add(act6);
        actividades.add(act7);
        actividades.add(act8);
        programarProyecto(actividades);*/
    }

    public static List<ActividadProgramada> programarProyecto(List<Actividad> actividades) {
        System.out.println("INICIANDO FUNCION");
        List<ActividadProgramada> lista = new LinkedList<>();
        //NUMERO DE ACTIVIDADES
        int N = actividades.size();
        // CALCULANDO NODO INICIAL Y NODO FINAL
        int inicio = 2 * N;
        int fin = 2 * N + 1;

        // ARMANDO EL GRAFO
        DigrafoAristaPonderada G = new DigrafoAristaPonderada(2 * N + 2);
        for (int i = 0; i < N; i++) {
            double duracion = actividades.get(i).getDuracion();
            G.agregarArista(new AristaDirigida(inicio, i, 0.0));
            G.agregarArista(new AristaDirigida(i + N, fin, 0.0));
            G.agregarArista(new AristaDirigida(i, i + N, duracion));

            // AGREGANDO PREDECESORAS
            List<Actividad> listaPredecesoras = actividades.get(i).getPredecesoras();
            for (int j = 0; j < actividades.get(i).getPredecesoras().size(); j++) {
                G.agregarArista(new AristaDirigida(N + i, listaPredecesoras.get(j).getNumeroDeActividad(), 0.0));
            }

        }

        // compute longest path
        LPAciclico cml;
        try {
            cml = new LPAciclico(G, inicio);
        } catch (java.lang.IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "ERROR!! \n El digrafo es ciclico", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        double total = cml.distanciaHacia(fin);

        for (int i = 0; i < N; i++) {
            ActividadProgramada act = new ActividadProgramada();

            double aux = new LPAciclico(G, i + N).distanciaHacia(fin);

            act.setInicioMasTemprano(cml.distanciaHacia(i));
            act.setNombreActividad(actividades.get(i).getNombreDeTarea());
            act.setTerminacionMasTemprana(cml.distanciaHacia(i + N));
            act.setHolgura((total - cml.distanciaHacia(i + N) - aux));
            act.setInicioMasTardio(act.getInicioMasTemprano() + act.getHolgura());
            act.setTerminacionMasTardia(act.getTerminacionMasTemprana() + act.getHolgura());
            lista.add(act);
        }
        // agregando  los que son ruta critica
        for (AristaDirigida x : cml.caminoHacia(fin)) {
            int v = 0;
            if ((v = x.desde()) < N) {
                lista.get(v).setRutaCritica(true);
            }
        }

        lista.forEach(System.out::println);
        return lista;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Josecarlos
 */
public class Painter {
    private final AnchorPane panelDibujo;

    public Painter(AnchorPane panelDibujo) {
        this.panelDibujo = panelDibujo;
    }
    
    public void aniadirLabel(String texto, double x, double y){
        Label label = new Label(texto);
        label.setVisible(true);
        label.setLayoutX(x);
        label.setLayoutY(y);
        panelDibujo.getChildren().add(label);
    }
    
    public void dibujarRectangulo(double x, double y, double width
            , double height, Color fillColor, String estilo){
        
        Rectangle rectangulo = new Rectangle(x,y,width,height);
        //Seteo el color del rectangulo
        rectangulo.setFill(fillColor);
        //Bordes negros
        rectangulo.setStroke(Color.BLACK);
        rectangulo.setStyle(estilo);
        
        panelDibujo.getChildren().add(rectangulo);
    }
    
    public void dibujarRectangulo(double x, double y, double width
            , double height, Color fillColor, String estilo, String textoToolTip){
        
        Rectangle rectangulo = new Rectangle(x,y,width,height);
        //Seteo el color del rectangulo
        rectangulo.setFill(fillColor);
        //Bordes negros
        rectangulo.setStroke(Color.BLACK);
        rectangulo.setStyle(estilo);
        
        Tooltip tooltip = new Tooltip(textoToolTip);
        Tooltip.install(rectangulo, tooltip);
        
        panelDibujo.getChildren().add(rectangulo);
    }
    
    public void dibujarLinea(double xFrom, double yFrom
            , double xTo, double yTo, boolean esEntrecortada){
        
        Line entreCortada = new Line(xFrom, yFrom, xTo, yTo);
        entreCortada.setStroke(Color.BLACK);
        if(esEntrecortada) entreCortada.getStrokeDashArray().addAll(20d,10d);
        panelDibujo.getChildren().add(entreCortada);
    }
}

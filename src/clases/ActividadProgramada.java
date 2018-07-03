/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Miguel
 */
public class ActividadProgramada {

    String nombreActividad;
    double inicioMasTemprano;
    double inicioMasTardio;
    double terminacionMasTemprana;
    double terminacionMasTardia;
    double holgura;
    boolean rutaCritica;

    public ActividadProgramada() {
    }

    
    public ActividadProgramada(String actividad, double inicioMasTemprano, double inicioMasTardio, double terminacionMasTemprana, double terminacionMasTardia, double holgura, boolean rutaCritica) {
        this.nombreActividad = actividad;
        this.inicioMasTemprano = inicioMasTemprano;
        this.inicioMasTardio = inicioMasTardio;
        this.terminacionMasTemprana = terminacionMasTemprana;
        this.terminacionMasTardia = terminacionMasTardia;
        this.holgura = holgura;
        this.rutaCritica = rutaCritica;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public double getInicioMasTemprano() {
        return inicioMasTemprano;
    }

    public void setInicioMasTemprano(double inicioMasTemprano) {
        this.inicioMasTemprano = inicioMasTemprano;
    }

    public double getInicioMasTardio() {
        return inicioMasTardio;
    }

    public void setInicioMasTardio(double inicioMasTardio) {
        this.inicioMasTardio = inicioMasTardio;
    }

    public double getTerminacionMasTemprana() {
        return terminacionMasTemprana;
    }

    public void setTerminacionMasTemprana(double terminacionMasTemprana) {
        this.terminacionMasTemprana = terminacionMasTemprana;
    }

    public double getTerminacionMasTardia() {
        return terminacionMasTardia;
    }

    public void setTerminacionMasTardia(double terminacionMasTardia) {
        this.terminacionMasTardia = terminacionMasTardia;
    }

    public double getHolgura() {
        return holgura;
    }

    public void setHolgura(double holgura) {
        this.holgura = holgura;
    }

    public boolean isRutaCritica() {
        return rutaCritica;
    }

    public void setRutaCritica(boolean rutaCritica) {
        this.rutaCritica = rutaCritica;
    }

    @Override
    public String toString() {
        return "Resultado{" + "actividad=" + nombreActividad + ", inicioMasTemprano=" + inicioMasTemprano + ", inicioMasTardio=" + inicioMasTardio + ", terminacionMasTemprana=" + terminacionMasTemprana + ", terminacionMasTardia=" + terminacionMasTardia + ", holgura=" + holgura + ", rutaCritica=" + rutaCritica + '}';
    }
    
}

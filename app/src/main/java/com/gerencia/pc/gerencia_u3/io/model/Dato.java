package com.gerencia.pc.gerencia_u3.io.model;

/**
 * Created by pc on 01/12/2016.
 */

public class Dato {
    private String categoria;
    private String reproducibilidad;
    private String severidad;
    private String prioridad;
    private String estado;
    private String resumen;
    private String p_reproducior;
    private String i_adicional;
    private String fecha_actu;
    private String descripcion;
    private String color;
    private String solucion;
    private String asignar;

    public String getUsuar() {
        return usuar;
    }

    private String usuar;
    private boolean error;


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getReproducibilidad() {
        return reproducibilidad;
    }

    public void setReproducibilidad(String reproducibilidad) {
        this.reproducibilidad = reproducibilidad;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getP_reproducior() {
        return p_reproducior;
    }

    public void setP_reproducior(String p_reproducior) {
        this.p_reproducior = p_reproducior;
    }

    public String getI_adicional() {
        return i_adicional;
    }

    public void setI_adicional(String i_adicional) {
        this.i_adicional = i_adicional;
    }

    public String getFecha_actu() {
        return fecha_actu;
    }

    public void setFecha_actu(String fecha_actu) {
        this.fecha_actu = fecha_actu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getAsignar() {
        return asignar;
    }

    public void setAsignar(String asignar) {
        this.asignar = asignar;
    }
}

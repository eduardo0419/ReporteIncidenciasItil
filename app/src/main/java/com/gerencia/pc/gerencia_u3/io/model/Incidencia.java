package com.gerencia.pc.gerencia_u3.io.model;

/**
 * Created by pc on 30/11/2016.
 */

public class Incidencia {
    private int id;
    private String titulo;
    private String color;
    private boolean error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

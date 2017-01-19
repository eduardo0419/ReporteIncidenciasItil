package com.gerencia.pc.gerencia_u3.io.model;

/**
 * Created by pc on 01/12/2016.
 */

public class Proyecto {
    private int id;
    private String descripcion;
    private boolean error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

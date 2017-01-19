package com.gerencia.pc.gerencia_u3.io.model;

/**
 * Created by pc on 02/12/2016.
 */

public class Personal {
    private int id;
    private String nombre;
    private boolean error;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isError() {
        return error;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

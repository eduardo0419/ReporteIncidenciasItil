package com.gerencia.pc.gerencia_u3.io.model;

/**
 * Created by pc on 02/12/2016.
 */

public class Categoria {
    private int id;
    private String categoria;
    private boolean error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

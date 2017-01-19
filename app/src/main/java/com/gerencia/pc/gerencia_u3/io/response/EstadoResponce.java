package com.gerencia.pc.gerencia_u3.io.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 10/01/2017.
 */

public class EstadoResponce {
    @SerializedName("estados")
    private ArrayList<String> arraEstados;
    @SerializedName("cargos")
    private ArrayList<String> arraEmpeadors;


    public ArrayList<String> getArraEstados() {
        return arraEstados;
    }

    public void setArraEstados(ArrayList<String> arraEstados) {
        this.arraEstados = arraEstados;
    }

    public ArrayList<String> getArraEmpeadors() {
        return arraEmpeadors;
    }

    public void setArraEmpeadors(ArrayList<String> arraEmpeadors) {
        this.arraEmpeadors = arraEmpeadors;
    }
}

package com.gerencia.pc.gerencia_u3.io.response;

import com.gerencia.pc.gerencia_u3.io.model.Grafica;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 02/12/2016.
 */

public class GraficaResponce {
    @SerializedName("grafica")
    private ArrayList<Grafica> grafica;

    public ArrayList<Grafica> getGrafica() {
        return grafica;
    }

    public void setGrafica(ArrayList<Grafica> grafica) {
        this.grafica = grafica;
    }
}

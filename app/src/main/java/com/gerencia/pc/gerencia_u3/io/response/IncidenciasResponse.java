package com.gerencia.pc.gerencia_u3.io.response;

import com.gerencia.pc.gerencia_u3.io.model.Incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 30/11/2016.
 */

public class IncidenciasResponse {
    @SerializedName("incidencia")
    private ArrayList<Incidencia> incidencia;

    public ArrayList<Incidencia> getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(ArrayList<Incidencia> incidencia) {
        this.incidencia = incidencia;
    }
}

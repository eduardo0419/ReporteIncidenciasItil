package com.gerencia.pc.gerencia_u3.io.response;

import com.gerencia.pc.gerencia_u3.io.model.Reporte;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 02/12/2016.
 */

public class ReporteResponce {
    @SerializedName("reporte")
    private ArrayList<Reporte> reporte;

    public ArrayList<Reporte> getReporte() {
        return reporte;
    }

    public void setReporte(ArrayList<Reporte> reporte) {
        this.reporte = reporte;
    }
}

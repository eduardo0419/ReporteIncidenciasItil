package com.gerencia.pc.gerencia_u3.io.response;

import com.gerencia.pc.gerencia_u3.io.model.Personal;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pc on 02/12/2016.
 */

public class PersonalResponce {
    @SerializedName("personal")
    private ArrayList<Personal> personal;

    public ArrayList<Personal> getPersonal() {
        return personal;
    }

    public void setPersonal(ArrayList<Personal> personal) {
        this.personal = personal;
    }
}

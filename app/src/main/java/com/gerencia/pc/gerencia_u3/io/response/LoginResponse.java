package com.gerencia.pc.gerencia_u3.io.response;

import com.gerencia.pc.gerencia_u3.io.model.Usuario;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

    @SerializedName("user")
    private ArrayList<Usuario> user;

    public ArrayList<Usuario> getUser() {
        return user;
    }

    public void setUser(ArrayList<Usuario> user) {
        this.user = user;
    }
}

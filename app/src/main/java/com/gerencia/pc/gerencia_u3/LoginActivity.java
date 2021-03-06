package com.gerencia.pc.gerencia_u3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Usuario;
import com.gerencia.pc.gerencia_u3.io.response.LoginResponse;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<LoginResponse> {


    Button Ingresa;
    EditText txt_usuario, txt_pass;
    ArrayList<Usuario> Resultado;
    AlertDialog alert;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Ingresa=(Button)findViewById(R.id.btn_ingresa);
        Ingresa.setOnClickListener(this);

        txt_usuario= (EditText)findViewById(R.id.txt_usuario);
        txt_pass= (EditText)findViewById(R.id.txt_pass);


        activity=this;
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingrese su servidor por favor")
                .setView(input)
                .setPositiveButton("Listo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Global.setNombreUsuarioShared(activity,"server","http://"+input.getText()+"/");
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ingresa:
                login();
                break;
        }
    }
    public void login(){
        Call<LoginResponse> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(this,"server")).getLogin(txt_usuario.getText().toString(), txt_pass.getText().toString());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful()) {
            LoginResponse loginResponse = response.body();
            Resultado = loginResponse.getUser();
            if (!Resultado.get(0).isError()){
                Intent intent=new Intent(this,MenuActivity.class);
                intent.putExtra("id_usuario",Resultado.get(0).getId());
                startActivity(intent);
            }else{
                Crouton.makeText(this, "Datos ingresados erroneos", Style.ALERT).show();
            }

        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        Crouton.makeText(this, "No hay conexion con el servidor", Style.ALERT).show();
    }
}

package com.gerencia.pc.gerencia_u3.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Dato;
import com.gerencia.pc.gerencia_u3.io.response.DatosResponce;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncidenciaFragment extends Fragment implements Callback<DatosResponce>, View.OnClickListener {

    private int id_incidencia;
    int id_usuario;
    View view;
    ArrayList<Dato> arrayDatos;
    FloatingActionButton button,editar;

    public IncidenciaFragment(int id,int usuario) {
        this.id_incidencia=id;
        this.id_usuario=usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_incidencia, container, false);
        TraeDatos();
        button=(FloatingActionButton) view.findViewById(R.id.btn_nota);
        editar=(FloatingActionButton) view.findViewById(R.id.edita);
        button.setOnClickListener(this);
        editar.setOnClickListener(this);

        Bundle Data = new Bundle();
        Data.putInt("id_incidencia",id_incidencia);
        Intent intent = getActivity().getIntent();
        intent.putExtras(Data);

        return view;
    }



    public void TraeDatos(){
        Call<DatosResponce> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getDatos(id_incidencia);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<DatosResponce> call, Response<DatosResponce> response) {
        if (response.isSuccessful()) {
            DatosResponce datosResponce = response.body();
            arrayDatos = datosResponce.getDatos();
            if (!arrayDatos.get(0).isError()){
                CargaDatos(arrayDatos);
            }else{
                Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
            }

        }
    }

    @Override
    public void onFailure(Call<DatosResponce> call, Throwable t) {
        Crouton.makeText(getActivity(), "Error de conexion a servidor", Style.ALERT).show();
    }

    public void CargaDatos(ArrayList<Dato> array){
        for (Dato a: array) {
            ((TextView)view.findViewById(R.id.txt_proyect)).setText(a.getDescripcion());
            ((TextView)view.findViewById(R.id.txt_categoria)).setText(a.getCategoria());
            ((TextView)view.findViewById(R.id.txt_prioridad)).setText(a.getPrioridad());
            ((TextView)view.findViewById(R.id.txt_reproducibilidad)).setText(a.getReproducibilidad());
            ((TextView)view.findViewById(R.id.txt_severidad)).setText(a.getSeveridad());
            ((TextView)view.findViewById(R.id.txt_estado)).setText(a.getEstado());
            ((LinearLayout) view.findViewById(R.id.cont_estado)).setBackgroundColor(Color.parseColor(a.getColor()));
            ((TextView)view.findViewById(R.id.txt_fecha)).setText(a.getFecha_actu());
            ((TextView)view.findViewById(R.id.txt_pasos)).setText(a.getP_reproducior());
            ((TextView)view.findViewById(R.id.txt_info)).setText(a.getI_adicional());
            if (a.getSolucion()==""){
                ((TextView)view.findViewById(R.id.txt_solu)).setText("(Aun no tiene solucion)");
            }else {
                ((TextView)view.findViewById(R.id.txt_solu)).setText(a.getSolucion());
            }
            if (Integer.parseInt(a.getAsignar())==id_usuario || Integer.parseInt(a.getUsuar())==id_usuario){
                editar.setVisibility(View.VISIBLE);
            }else {
                editar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_nota:{
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmet_layout,new NotaFragment(id_incidencia,id_usuario)).commit();
                break;
            }
            case R.id.edita:{
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmet_layout,new EditaFragment(id_incidencia,id_usuario)).commit();
                break;
            }
        }
    }

    public int getId_incidencia() {
        return id_incidencia;
    }
}

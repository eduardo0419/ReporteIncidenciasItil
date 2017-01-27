package com.gerencia.pc.gerencia_u3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.MenuActivity;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Proyecto;
import com.gerencia.pc.gerencia_u3.io.model.Reporte;
import com.gerencia.pc.gerencia_u3.io.response.DatosResponce;
import com.gerencia.pc.gerencia_u3.io.response.EstadoResponce;
import com.gerencia.pc.gerencia_u3.io.response.ProyectosResponce;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditaFragment extends Fragment implements Callback<EstadoResponce> {

    View view;
    int id_inci;
    int id_usu;

    Spinner cargos;
    Spinner estados;
    Button btn;
    ArrayList<String> arrayCargp;

    EditText resul;
    TextView tit;
    public EditaFragment(int id_incidencia, int id_usuario) {
        this.id_inci=id_incidencia;
        this.id_usu=id_usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_edita, container, false);
        cargos=(Spinner)view.findViewById(R.id.spinner2);
        estados=(Spinner)view.findViewById(R.id.spinner3);

        Call<EstadoResponce> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getEstados(id_inci,id_usu);
        call.enqueue(this);


        tit=(TextView) view.findViewById(R.id.tere);
        resul=(EditText)view.findViewById(R.id.txt_resul);
        cargos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==5) {
                    List<String> l = new ArrayList<String>();
                    l.add("Seleccione....");
                    l.add(arrayCargp.get(0));
                    ArrayAdapter<String> spinerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, l);
                    spinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                    estados.setAdapter(spinerAdapter);
                }else{
                    estados.setAdapter(null);
                }
                if (position==6){
                    tit.setVisibility(View.VISIBLE);
                    resul.setVisibility(View.VISIBLE);
                }else{
                    tit.setVisibility(View.GONE);
                    resul.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn=(Button)view.findViewById(R.id.btn_act);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="";
                if (estados.getSelectedItem()==null){
                    s="";
                }else {
                    s=estados.getSelectedItem().toString();
                }
                Call<Reporte> calls=GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).setIp(id_inci,cargos.getSelectedItemPosition(),resul.getText().toString(),s);
                calls.enqueue(new actuyaluza());
            }
        });
        return view;
    }

    @Override
    public void onResponse(Call<EstadoResponce> call, Response<EstadoResponce> response) {
        if (response.isSuccessful()) {
            EstadoResponce estado = response.body();
            List<String> l =new ArrayList<String>();
            l.add("Seleccione....");
            for(int i=0;i<estado.getArraEstados().size();i++){
                l.add(estado.getArraEstados().get(i));
            }

            ArrayAdapter<String> spinerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,l);
            spinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            cargos.setAdapter(spinerAdapter);

            arrayCargp=estado.getArraEmpeadors();
        }
    }

    @Override
    public void onFailure(Call<EstadoResponce> call, Throwable t) {

    }

    public  class  actuyaluza implements Callback<Reporte> {

        @Override
        public void onResponse(Call<Reporte> call, Response<Reporte> response) {
            Intent intent =new Intent(getContext(), MenuActivity.class);
            intent.putExtra("id_usuario",id_usu);
            startActivity(intent);
        }

        @Override
        public void onFailure(Call<Reporte> call, Throwable t) {

        }
    }
}

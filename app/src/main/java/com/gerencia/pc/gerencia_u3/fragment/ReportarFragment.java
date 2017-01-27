package com.gerencia.pc.gerencia_u3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Proyecto;
import com.gerencia.pc.gerencia_u3.io.response.ProyectosResponce;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportarFragment extends Fragment implements Callback<ProyectosResponce>, AdapterView.OnItemSelectedListener {


    View view;
    int id_usuario;
    Spinner spinner;
    ArrayList<Proyecto> arrayDatos;
    public ReportarFragment(int i) {
        this.id_usuario=i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_reportar, container, false);
        spinner =(Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        traeProyectos();
        return view;
    }

    public void traeProyectos(){
        Call<ProyectosResponce> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getProyectos(id_usuario);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ProyectosResponce> call, Response<ProyectosResponce> response) {
        if (response.isSuccessful()) {
            ProyectosResponce proyectosResponce = response.body();
            arrayDatos = proyectosResponce.getProyectos();
            if (!arrayDatos.get(0).isError()){
                CargaProyectos(arrayDatos);
            }else{
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_formulario,new ErrorFragment()).commit();
            }

        }
    }

    @Override
    public void onFailure(Call<ProyectosResponce> call, Throwable t) {
        Crouton.makeText(getActivity(), "Error de conexion al servidor", Style.ALERT).show();
    }
    public void CargaProyectos(ArrayList<Proyecto> list){
        List<String> l =new ArrayList<String>();
        l.add("Seleccione....");
        for(Proyecto p :list){
            l.add(p.getDescripcion());
        }

        ArrayAdapter<String> spinerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,l);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(spinerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int index=spinner.getSelectedItemPosition()-1;
        if (index!=-1){
            int id_pro=arrayDatos.get(index).getId();
            cargarDatos(id_pro);
        }else {
            FragmentManager fm=getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_formulario,new ErrorFragment()).commit();
            Toast.makeText(getContext(), "Por favor seleccione un proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void cargarDatos(int id){
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_formulario,new FormularioFragment(id,id_usuario)).commit();
    }
}

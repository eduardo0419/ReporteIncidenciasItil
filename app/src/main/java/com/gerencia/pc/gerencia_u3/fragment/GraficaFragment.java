package com.gerencia.pc.gerencia_u3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Grafica;
import com.gerencia.pc.gerencia_u3.io.model.Proyecto;
import com.gerencia.pc.gerencia_u3.io.response.GraficaResponce;
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
public class GraficaFragment extends Fragment implements Callback<ProyectosResponce>, View.OnClickListener {


    View view;
    int id_usuario;
    ArrayList<Proyecto> arrayDatos;
    Spinner spinner;
    Button button;
    public GraficaFragment(int i) {
        this.id_usuario=i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grafica, container, false);
        spinner =(Spinner)view.findViewById(R.id.spinner);
        button =(Button)view.findViewById(R.id.btn_grafica);
        button.setOnClickListener(this);
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
                Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
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
    public void onClick(View view) {

        if (view.getId()==R.id.btn_grafica) {
            int index=spinner.getSelectedItemPosition()-1;
            if (index!=-1){
                int id_pro=arrayDatos.get(index).getId();
                    traeDatosGrafica(id_pro);
            }else {
                Toast.makeText(getContext(), "Por favor seleccione un proyecto", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void traeDatosGrafica(int id_pro){
        Call<GraficaResponce> call= GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getGrafica(id_pro);
        call.enqueue(new Graficas());
    }
    public class Graficas implements Callback<GraficaResponce> {
        ArrayList<Grafica> array;
        Fragment fragment;
        @Override
        public void onResponse(Call<GraficaResponce> call, Response<GraficaResponce> response) {
            if (response.isSuccessful()) {
                GraficaResponce graficaResponce = response.body();
                array = graficaResponce.getGrafica();
                if (!array.get(0).isError()){
                    LlamaGrafica(array);
                }else{
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_pie,new ErrorFragment()).commit();
                    Toast.makeText(getContext(),"No hay incidencias registradas para este \n proyecto",Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onFailure(Call<GraficaResponce> call, Throwable t) {
            Crouton.makeText(getActivity(), "Error de conexion al servidor", Style.ALERT).show();
        }
        public void LlamaGrafica(ArrayList<Grafica> arrayGrafica){
            String[] xdatos = new String[arrayGrafica.size()];
            float[] yDatos = new float[arrayGrafica.size()];
            String[] colors=new String[arrayGrafica.size()];
            int total=0;

            int i=0;
            for (Grafica g:arrayGrafica){
                xdatos[i]=g.getEstado();
                yDatos[i]=g.getCantidad();
                colors[i]=g.getColor();
                total=g.getTotal();
                i=i+1;
            }
            FragmentManager fm=getActivity().getSupportFragmentManager();
            fragment=new PieChartFragment(xdatos,yDatos,colors,total);
            fm.beginTransaction().replace(R.id.fragment_pie,fragment).commit();
        }
    }
}

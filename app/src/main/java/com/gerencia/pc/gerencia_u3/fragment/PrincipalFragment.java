package com.gerencia.pc.gerencia_u3.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Incidencia;
import com.gerencia.pc.gerencia_u3.io.response.IncidenciasResponse;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PrincipalFragment extends Fragment implements Callback<IncidenciasResponse>, View.OnClickListener {

    int id_usuario;
    View view;
    TableLayout table;
    ArrayList<Incidencia> incidencias;

    public PrincipalFragment(int id) {
        this.id_usuario=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        TraerIncidencias();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void TraerIncidencias(){
        Call<IncidenciasResponse> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getIncidencias(id_usuario);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<IncidenciasResponse> call, Response<IncidenciasResponse> response) {
        if (response.isSuccessful()) {
            IncidenciasResponse incidenciasResponse = response.body();
            incidencias = incidenciasResponse.getIncidencia();
            if (!incidencias.get(0).isError()){
                CargaIncidencias(incidencias);
            }else{
                Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
            }

        }
    }

    @Override
    public void onFailure(Call<IncidenciasResponse> call, Throwable t) {
        Crouton.makeText(getActivity(), "Error de conexion a servidor", Style.ALERT).show();
    }

    public void CargaIncidencias(ArrayList<Incidencia> incidencia){
        table = (TableLayout) view.findViewById(R.id.tabla_incidencias);
        TableRow row = (TableRow) LayoutInflater.from(getContext()).inflate(R.layout.encabezado_incidencias, null);
        table.addView(row);
        for (Incidencia i: incidencia) {
            TableRow row2 = (TableRow)LayoutInflater.from(getContext()).inflate(R.layout.fila_incidencia, null);

            EditText input_id=(EditText)row2.findViewById(R.id.input_id);
            input_id.setText("ID"+i.getId());
            input_id.setBackgroundColor(Color.parseColor(i.getColor()));

            EditText input_incidencia=(EditText)row2.findViewById(R.id.input_incidencia);
            input_incidencia.setText(i.getTitulo());
            input_incidencia.setBackgroundColor(Color.parseColor(i.getColor()));

            LinearLayout linearLayout=(LinearLayout)row2.findViewById(R.id.fondo_boton);
            linearLayout.setBackgroundColor(Color.parseColor(i.getColor()));

            ImageButton button =(ImageButton) row2.findViewById(R.id.boton_bus);
            button.setId(i.getId());
            button.setOnClickListener(this);

            table.addView(row2);
        }
        table.requestLayout();
    }

    @Override
    public void onClick(View view) {
         int id;
         id=view.getId();
         FragmentManager fm=getActivity().getSupportFragmentManager();
         fm.beginTransaction().replace(R.id.fragmet_layout,new IncidenciaFragment(id,id_usuario)).commit();

    }
}

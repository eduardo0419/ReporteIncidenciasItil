package com.gerencia.pc.gerencia_u3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Nota;
import com.gerencia.pc.gerencia_u3.io.response.NotaResponce;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotaFragment extends Fragment implements View.OnClickListener, Callback<NotaResponce> {

    View view;
    int id_incidencia;
    int id_usuario;
    Button button;
    EditText input;

    public NotaFragment(int id,int usuario) {
        this.id_incidencia=id;
        this.id_usuario=usuario;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nota, container, false);
        button= (Button)view.findViewById(R.id.btn_envia_nota);
        button.setOnClickListener(this);

        input=(EditText) view.findViewById(R.id.input_nota);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_envia_nota){
            Call<NotaResponce> call= GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).setNota(id_usuario,id_incidencia,input.getText().toString());
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<NotaResponce> call, Response<NotaResponce> response) {
        if (response.isSuccessful()) {
            NotaResponce notaResponce = response.body();
            ArrayList<Nota> array=notaResponce.getNota();
            if (!array.get(0).isError()){
                Crouton.makeText(getActivity(), "La nota fue registrada,Correctamente", Style.CONFIRM).show();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmet_layout,new PrincipalFragment(id_usuario)).commit();
            }else{
                Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
            }

        }
    }

    @Override
    public void onFailure(Call<NotaResponce> call, Throwable t) {
        Crouton.makeText(getActivity(), "Error de conexion a servidor", Style.ALERT).show();
    }
}

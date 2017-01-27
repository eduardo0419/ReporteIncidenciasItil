package com.gerencia.pc.gerencia_u3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gerencia.pc.gerencia_u3.Global;
import com.gerencia.pc.gerencia_u3.R;
import com.gerencia.pc.gerencia_u3.io.GerenciaApiAdapter;
import com.gerencia.pc.gerencia_u3.io.model.Categoria;
import com.gerencia.pc.gerencia_u3.io.model.Personal;
import com.gerencia.pc.gerencia_u3.io.model.Reporte;
import com.gerencia.pc.gerencia_u3.io.response.CategoriaResponce;
import com.gerencia.pc.gerencia_u3.io.response.PersonalResponce;
import com.gerencia.pc.gerencia_u3.io.response.ReporteResponce;

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
public class FormularioFragment extends Fragment implements Callback<CategoriaResponce>, View.OnClickListener {


    int id_pro;
    View view;
    int id_usuario;
    Spinner spinner_categoria;
    Spinner spinner_personal;
    Spinner spinner_reproduci;
    Spinner spinner_severidad;
    Spinner spinner_priorida;
    Spinner spinner_asignar;

    EditText input_titulo;
    EditText input_pasos;
    EditText input_infoadi;

    ArrayList<Categoria> arrayCategorias;
    ArrayList<Personal> arrayPersonal;
    Button enviar;
    public FormularioFragment(int i,int j) {
        this.id_pro=i;
        this.id_usuario=j;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_formulario, container, false);

        spinner_categoria =(Spinner)view.findViewById(R.id.spinner_categorias);
        spinner_personal =(Spinner)view.findViewById(R.id.spinner_Asignar);
        spinner_reproduci =(Spinner)view.findViewById(R.id.spinner_reprodu);
        spinner_severidad =(Spinner)view.findViewById(R.id.spinner_severidad);
        spinner_priorida =(Spinner)view.findViewById(R.id.spinner_prioridad);
        spinner_asignar =(Spinner)view.findViewById(R.id.spinner_Asignar);

        input_titulo=(EditText)view.findViewById(R.id.input_titulo);
        input_pasos=(EditText)view.findViewById(R.id.input_pasos_repro);
        input_infoadi=(EditText)view.findViewById(R.id.input_info_adi);

        enviar =(Button)view.findViewById(R.id.btn_reportar_incidencia);
        enviar.setOnClickListener(this);
        traeProyectos();
        traePersonal();
        return view;
    }

    public void traeProyectos(){
        Call<CategoriaResponce> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getCategoria(id_pro);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CategoriaResponce> call, Response<CategoriaResponce> response) {
        if (response.isSuccessful()) {
            CategoriaResponce categoriaRespon = response.body();
            arrayCategorias = categoriaRespon.getCategorias();
            if (!arrayCategorias.get(0).isError()){
                CargaCategorias(arrayCategorias);
            }else{
                Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
            }

        }
    }

    @Override
    public void onFailure(Call<CategoriaResponce> call, Throwable t) {

    }
    public void CargaCategorias(ArrayList<Categoria> list){
        List<String> l =new ArrayList<String>();
        l.add("Seleccione....");
        for(Categoria p :list){
            l.add(p.getCategoria());
        }

        ArrayAdapter<String> spinerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,l);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_categoria.setAdapter(spinerAdapter);
    }

    public void traePersonal(){
        Call<PersonalResponce> call = GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).getPersonal(id_pro,id_usuario);
        call.enqueue(new LlenarSpinnerPersonal());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_reportar_incidencia){
            enviaForm();
        }
    }

    public class LlenarSpinnerPersonal implements Callback<PersonalResponce> {

        @Override
        public void onResponse(Call<PersonalResponce> call, Response<PersonalResponce> response) {
            if (response.isSuccessful()) {
                PersonalResponce personalResponce = response.body();
                arrayPersonal = personalResponce.getPersonal();
                if (!arrayPersonal.get(0).isError()){
                    CargaPersonal(arrayPersonal);
                }else{
                    Crouton.makeText(getActivity(), "Error de conexion en internet", Style.ALERT).show();
                }
            }
        }
        @Override
        public void onFailure(Call<PersonalResponce> call, Throwable t) {

        }
    }

    public void CargaPersonal(ArrayList<Personal> pers){
        List<String> l =new ArrayList<String>();
        l.add("Seleccione....");
        l.add("No asignar....");
        for(Personal p :pers){
            l.add(p.getNombre());
        }

        ArrayAdapter<String> spinerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,l);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner_personal.setAdapter(spinerAdapter);
    }

    public void enviaForm(){
        int indexCatego=spinner_categoria.getSelectedItemPosition()-1;
        int indexAsigna=spinner_asignar.getSelectedItemPosition()-2;

        int id_categoria =arrayCategorias.get(indexCatego).getId();


        String reproducibilidad=spinner_reproduci.getSelectedItem().toString();
        String severidad=spinner_severidad.getSelectedItem().toString();
        String prioridad=spinner_priorida.getSelectedItem().toString();

        int id_asigna=0;
        if (indexAsigna>=0){
           id_asigna=arrayPersonal.get(indexAsigna).getId();
        }
        String titulo =input_titulo.getText().toString();
        String PasosReproducir =input_pasos.getText().toString();
        String InfoAdicional =input_infoadi.getText().toString();

        Call<ReporteResponce> call= GerenciaApiAdapter.getApiService(Global.getNombreUsuarioFromShared(getActivity(),"server")).setReporte(id_categoria,reproducibilidad,severidad,prioridad,id_asigna,titulo,PasosReproducir,InfoAdicional,id_pro,id_usuario);
        call.enqueue(new enviaReporteIncidencia());


    }
    public class enviaReporteIncidencia implements Callback<ReporteResponce> {
        ArrayList<Reporte> arrayRespuesta;
        @Override
        public void onResponse(Call<ReporteResponce> call, Response<ReporteResponce> response) {
            if (response.isSuccessful()) {
                ReporteResponce reporteResponce = response.body();
                arrayRespuesta = reporteResponce.getReporte();
                if (!arrayRespuesta.get(0).isError()){
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragmet_layout,new PrincipalFragment(id_usuario)).commit();
                }else{
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragmet_layout,new ErrorFragment()).commit();
                }

            }
        }

        @Override
        public void onFailure(Call<ReporteResponce> call, Throwable t) {

        }
    }
}

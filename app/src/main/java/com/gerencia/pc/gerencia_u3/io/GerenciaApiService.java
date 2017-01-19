package com.gerencia.pc.gerencia_u3.io;


import com.gerencia.pc.gerencia_u3.io.model.Reporte;
import com.gerencia.pc.gerencia_u3.io.response.CategoriaResponce;
import com.gerencia.pc.gerencia_u3.io.response.DatosResponce;
import com.gerencia.pc.gerencia_u3.io.response.EstadoResponce;
import com.gerencia.pc.gerencia_u3.io.response.GraficaResponce;
import com.gerencia.pc.gerencia_u3.io.response.IncidenciasResponse;
import com.gerencia.pc.gerencia_u3.io.response.LoginResponse;
import com.gerencia.pc.gerencia_u3.io.response.NotaResponce;
import com.gerencia.pc.gerencia_u3.io.response.PersonalResponce;
import com.gerencia.pc.gerencia_u3.io.response.ProyectosResponce;
import com.gerencia.pc.gerencia_u3.io.response.ReporteResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GerenciaApiService {

    @GET("Gerencia_U3/Controladores/login.php")
    Call<LoginResponse> getLogin(@Query("login") String login, @Query("clave") String clave);

    @GET("Gerencia_U3/Controladores/cargaIncidencias.php")
    Call<IncidenciasResponse> getIncidencias(@Query("id") int id);

    @GET("Gerencia_U3/Controladores/carga_incidencia.php")
    Call<DatosResponce> getDatos(@Query("id_inci") int id_inci);

    @GET("Gerencia_U3/Controladores/registra_nota.php")
    Call<NotaResponce> setNota(@Query("id_usu") int id_usu,@Query("id_inci") int id_inci, @Query("nota") String nota);

    @GET("Gerencia_U3/Controladores/carga_proyectos.php")
    Call<ProyectosResponce> getProyectos (@Query("id_usu") int id_usu);

    @GET("Gerencia_U3/Controladores/carga_grafica.php")
    Call<GraficaResponce> getGrafica (@Query("id_pro") int id_pro);

    @GET("Gerencia_U3/Controladores/carga_categorias.php")
    Call<CategoriaResponce> getCategoria (@Query("id_pro") int id_pro);

    @GET("Gerencia_U3/Controladores/carga_personal.php")
    Call<PersonalResponce> getPersonal (@Query("id_pro") int id_pro,@Query("id_usu") int id_usu);

    @GET("Gerencia_U3/Controladores/actu.php")
    Call<EstadoResponce> getEstados (@Query("id_inci") int id_inci, @Query("id_usu") int id_usu);

    @GET("Gerencia_U3/Controladores/update.php")
    Call<Reporte> setIp(@Query("id_inci") int id_inci, @Query("estado") int estado,@Query("resultado") String resultado,@Query("cargo") String car);



    @GET("Gerencia_U3/Controladores/registra_incidencia.php")
    Call<ReporteResponce> setReporte (@Query("listCatego") int listCatego, @Query("listRepro") String listRepro, @Query("listSeve") String listSeve, @Query("listPrio") String listPrio, @Query("listAsignar") int listAsignar,
                                      @Query("listResu") String listResu,@Query("listPasos") String listPasos,@Query("listInfo") String listInfo,@Query("id_pro") int id_pro,@Query("id_usu") int id_usu);
}
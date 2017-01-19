package com.gerencia.pc.gerencia_u3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.gerencia.pc.gerencia_u3.fragment.GraficaFragment;
import com.gerencia.pc.gerencia_u3.fragment.IncidenciaFragment;
import com.gerencia.pc.gerencia_u3.fragment.NotaFragment;
import com.gerencia.pc.gerencia_u3.fragment.PrincipalFragment;
import com.gerencia.pc.gerencia_u3.fragment.ReportarFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int id_usuario;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        id_usuario = getIntent().getIntExtra("id_usuario",0);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmet_layout,new PrincipalFragment(id_usuario)).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_principal) {
            fragment =new PrincipalFragment(id_usuario);
        } else if (id == R.id.nav_reportar) {
            fragment =new ReportarFragment(id_usuario);

        } else if (id == R.id.nav_grafico) {
            fragment =new GraficaFragment(id_usuario);

        } else if (id == R.id.nav_salir) {
            logaut();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmet_layout,fragment)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logaut(){
        Intent intent= new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmet_layout);
            if (currentFragment.getClass().getName()== IncidenciaFragment.class.getName()) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmet_layout, new PrincipalFragment(id_usuario)).commit();
                return false;
            }
            if (currentFragment.getClass().getName()== NotaFragment.class.getName()) {
                Bundle gameData = getIntent().getExtras();
                int id_incidencia = gameData.getInt("id_incidencia");
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmet_layout, new IncidenciaFragment(id_incidencia,id_usuario)).commit();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

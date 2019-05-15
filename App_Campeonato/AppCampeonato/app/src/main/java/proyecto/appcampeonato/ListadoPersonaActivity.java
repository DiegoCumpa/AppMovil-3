package proyecto.appcampeonato;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import proyecto.appcampeonato.adaptador.ListadoPersona;
import proyecto.appcampeonato.beans.EstadoBean;
import proyecto.appcampeonato.beans.PersonaBean;
import proyecto.appcampeonato.beans.SexoBean;
import proyecto.appcampeonato.beans.TipoDocBean;
import proyecto.appcampeonato.conexion.Conexion;
import proyecto.appcampeonato.dao.PersonaDAO;

public class ListadoPersonaActivity extends AppCompatActivity{

    GridView listap;
    ArrayList<PersonaBean> persona;
    ImageButton btnnuevo;
    ImageButton btnregresar;
    Context contexto;


    private void IniciaComponente() {
        btnnuevo = (ImageButton) findViewById(R.id.btnNuevo);
        btnregresar = (ImageButton) findViewById(R.id.btnRegresar);
        listap = (GridView) findViewById(R.id.lstP);
        persona = new ArrayList<>();

    }


    private void CargaPersonas() {
        PersonaDAO dao = new PersonaDAO(this);
        ArrayList<PersonaBean> personas = dao.List();

        ListadoPersona adapter = new ListadoPersona(this, R.layout.persona_items, personas);

        listap.setAdapter(adapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_per_activity);
        IniciaComponente();
        CargaPersonas();

        btnnuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir = new Intent(ListadoPersonaActivity.this, MantPActivity.class);
                startActivity(ir);
            }
        });

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir = new Intent(ListadoPersonaActivity.this, MainActivity.class);
                startActivity(ir);
            }
        });

        listap.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Editar"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListadoPersonaActivity.this);

                dialog.setTitle("Elija una opción");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            PersonaBean obj=(PersonaBean)listap.getItemAtPosition(position);
                            Intent ir = new Intent(ListadoPersonaActivity.this, MantPActivity.class);
                            ir.putExtra("usu",obj);
                            startActivity(ir);
                            CargaPersonas();

                        } else {

                        }
                    }
                });
                dialog.show();
                return true;
            }
        });





    }






}

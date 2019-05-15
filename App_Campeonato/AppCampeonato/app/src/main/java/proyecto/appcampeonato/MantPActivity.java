package proyecto.appcampeonato;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import proyecto.appcampeonato.adaptador.ListadoPersona;
import proyecto.appcampeonato.beans.EstadoBean;
import proyecto.appcampeonato.beans.PersonaBean;
import proyecto.appcampeonato.beans.SexoBean;
import proyecto.appcampeonato.beans.TipoDocBean;
import proyecto.appcampeonato.conexion.Conexion;
import proyecto.appcampeonato.dao.EstadoDAO;
import proyecto.appcampeonato.dao.PersonaDAO;
import proyecto.appcampeonato.dao.SexoDAO;
import proyecto.appcampeonato.dao.TipoDocDAO;

public class MantPActivity extends AppCompatActivity implements View.OnClickListener {


    EditText txtcodigo,txtnombres,txtapaterno,txtamaterno,txtnumdoc,txtnac,txtemail,txttelefono;
    Spinner spsexo,sptipdoc,spestado;
    Button btncarga,btnregistra,btntomar,btneliminar;
    ImageView imgfoto;


    ArrayList<SexoBean> sexo;
    ArrayList<TipoDocBean> tipodoc;
    ArrayList<EstadoBean> estado;

    final int REQUEST_CODE_GALLERY = 999;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void IniciaComponente(){

        txtcodigo = (EditText)findViewById((R.id.txtCodigo));
        txtnombres = (EditText)findViewById((R.id.txtNomP));
        txtapaterno = (EditText)findViewById((R.id.txtAPater));
        txtamaterno = (EditText)findViewById((R.id.txtMaterP));
        txtnumdoc = (EditText)findViewById((R.id.txtNumDoc));
        txtnac = (EditText)findViewById((R.id.txtNacP));
        txtemail = (EditText)findViewById((R.id.txtEmailP));
        txttelefono = (EditText)findViewById((R.id.txtTelefP));
        spsexo=(Spinner) findViewById(R.id.spSexo);
        sptipdoc=(Spinner) findViewById(R.id.spDoc);
        spestado=(Spinner) findViewById(R.id.spEstado);
        btncarga=(Button) findViewById(R.id.btnCargar);
        btnregistra=(Button) findViewById(R.id.btnRegistrar);
        btntomar=(Button) findViewById(R.id.btnTomar);
        btneliminar=(Button) findViewById(R.id.btnEliminar);
        imgfoto = (ImageView) findViewById(R.id.imgFotoP);
        btncarga.setOnClickListener(this);
        btnregistra.setOnClickListener(this);
        btneliminar.setOnClickListener(this);
    }

    private void CargaSexo(){
        SexoDAO dao=new SexoDAO(this);
        sexo=dao.List();
        ArrayAdapter<SexoBean> adapter=
                new ArrayAdapter<SexoBean>(this,
                        android.R.layout.simple_list_item_1,
                        sexo);
        spsexo.setAdapter(adapter);

    }

    private void CargaTipoDoc(){
        TipoDocDAO dao=new TipoDocDAO(this);
        tipodoc=dao.List();
        ArrayAdapter<TipoDocBean> adapter=
                new ArrayAdapter<TipoDocBean>(this,
                        android.R.layout.simple_list_item_1,
                        tipodoc);
        sptipdoc.setAdapter(adapter);

    }

    private void CargaEstado(){
        EstadoDAO dao=new EstadoDAO(this);
        estado=dao.List();
        ArrayAdapter<EstadoBean> adapter=
                new ArrayAdapter<EstadoBean>(this,
                        android.R.layout.simple_list_item_1,
                        estado);
        spestado.setAdapter(adapter);

    }

    public void setPositionSex(int idsexo){
        for(int i=0;i<sexo.size();i++){
            SexoBean bean=sexo.get(i);
            if(bean.getCodigo() == idsexo){
                spsexo.setSelection(i);
                break;
            }
        }
    }

    public void setPositionTip(int iddoc){
        for(int i=0;i<tipodoc.size();i++){
            TipoDocBean bean=tipodoc.get(i);
            if(bean.getCodigo() == iddoc){
                sptipdoc.setSelection(i);
                break;
            }
        }
    }

    public void setPositionEst(int idestado){
        for(int i=0;i<estado.size();i++){
            EstadoBean bean=estado.get(i);
            if(bean.getCodigo() == idestado){
                spestado.setSelection(i);
                break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mant_p);

        IniciaComponente();
        CargaEstado();
        CargaSexo();
        CargaTipoDoc();
        Intent rec=getIntent();

        if(rec.getSerializableExtra("usu")!=null){//Modificar
            txtcodigo.setEnabled(false);
            PersonaBean cod=(PersonaBean)rec.getSerializableExtra("usu");
            txtcodigo.setText(Integer.toString(cod.getCodigo()));
            PersonaDAO dao = new PersonaDAO(this);
            PersonaBean bean=dao.Buscar(txtcodigo.getText().toString());
            txtnombres.setText(bean.getNombres());
            txtapaterno.setText(bean.getApaterno());
            txtamaterno.setText(bean.getAmaterno());
            setPositionSex(bean.getSexo());
            setPositionTip(bean.getTipodoc());
            txtnumdoc.setText(bean.getNumdoc());
            txtnac.setText(bean.getFecnac());
            txtemail.setText(bean.getEmail());
            txttelefono.setText(bean.getTelefono());
            setPositionEst(bean.getEstado());
            byte[] foto = bean.getFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
            imgfoto.setImageBitmap(bitmap);

            btnregistra.setText("EDITAR");
        }else{
            btnregistra.setText("Registrar");
            btneliminar.setEnabled(false);


        }

        btntomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "No tienes permisos", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgfoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgfoto.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onClick(View view) {

        if (view == btncarga) {
            ActivityCompat.requestPermissions(
                    MantPActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
            view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }


        if (view == btnregistra) {


            PersonaBean bean = new PersonaBean();

            bean.setNombres(txtnombres.getText().toString());
            bean.setApaterno(txtapaterno.getText().toString());
            bean.setAmaterno(txtamaterno.getText().toString());
            int idsexo = ((SexoBean) spsexo.getItemAtPosition(spsexo.getSelectedItemPosition())).getCodigo();
            bean.setSexo(idsexo);
            int idtipodoc = ((TipoDocBean) sptipdoc.getItemAtPosition(sptipdoc.getSelectedItemPosition())).getCodigo();
            bean.setTipodoc(idtipodoc);
            bean.setNumdoc(txtnumdoc.getText().toString());
            bean.setFecnac(txtnac.getText().toString());
            bean.setEmail(txtemail.getText().toString());
            bean.setTelefono(txttelefono.getText().toString());
            int idestado = ((EstadoBean) spestado.getItemAtPosition(spestado.getSelectedItemPosition())).getCodigo();
            bean.setEstado(idestado);
            bean.setFoto(imageViewToByte(imgfoto));

            PersonaDAO dao = new PersonaDAO(this);

            if(txtcodigo.isEnabled()){
                dao.Grabar(bean);
            }else{
                dao.Editar(bean);
            }


            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show();
            txtnombres.setText("");
            txtapaterno.setText("");
            txtamaterno.setText("");
            txtnumdoc.setText("");
            txtnac.setText("");
            txtemail.setText("");
            txttelefono.setText("");
            imgfoto.setImageResource(R.mipmap.userfoto);
            txtnombres.requestFocus();


            Intent ir = new Intent(MantPActivity.this, ListadoPersonaActivity.class);
            startActivity(ir);

        }

        if(view == btneliminar){
            Conexion cn=new Conexion(this);
            SQLiteDatabase sql= cn.getWritableDatabase();
            sql.execSQL("delete from persona where idpersona=?",
                    new Object[]{txtcodigo.getText().toString()});
            Intent ir = new Intent(MantPActivity.this, ListadoPersonaActivity.class);
            startActivity(ir);


        }
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    }




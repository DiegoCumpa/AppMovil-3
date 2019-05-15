package proyecto.appcampeonato.dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import proyecto.appcampeonato.beans.PersonaBean;
import proyecto.appcampeonato.conexion.Conexion;

public class PersonaDAO  {

    Context contexto;

    public PersonaDAO(Context ctx) {
        contexto = ctx;
    }


    public int Grabar(PersonaBean bean){
        int resultado=0;
        try{
            Conexion cn = new Conexion(contexto);
            SQLiteDatabase sql = cn.getWritableDatabase();
            sql.execSQL("insert into persona values(null,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{bean.getNombres(),bean.getApaterno(),
                    bean.getAmaterno(),bean.getSexo(),bean.getTipodoc(),bean.getNumdoc(),bean.getFecnac(),
                    bean.getEmail(),bean.getTelefono(),bean.getEstado(),bean.getFoto()});

            sql.close();
            resultado=1;
        }catch (Exception ex){
            resultado=-1;
        }

        return resultado;
    }

    public ArrayList<PersonaBean> List() {
        ArrayList<PersonaBean> persona = new ArrayList<PersonaBean>();
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur = sql.rawQuery("select idpersona,nombres,apaterno,email,foto from persona", null);
        PersonaBean bean;
        while (cur.moveToNext()) {
            bean = new PersonaBean();
            bean.setCodigo(cur.getInt(0));
            bean.setNombres(cur.getString(1));
            bean.setApaterno(cur.getString(2));
            bean.setEmail(cur.getString(3));
            bean.setFoto(cur.getBlob(4));
            persona.add(bean);
        }
        sql.close();
        return persona;
    }

    public int Editar(PersonaBean bean){
        int resultado=0;
        try{
            Conexion cn = new Conexion(contexto);
            SQLiteDatabase sql = cn.getWritableDatabase();
            sql.execSQL("update persona set nombres=?,apaterno=?,amaterno=?,sexo=?,tipdoc=?,numdoc=?,fecnac=?,email=?,telefono=?,estado=?," +
                    "foto=? where idpersona=?",new Object[]{bean.getNombres(),bean.getApaterno(),bean.getAmaterno(),bean.getSexo()
                    ,bean.getTipodoc(),bean.getNumdoc(),bean.getFecnac(),bean.getEmail(),bean.getTelefono(),bean.getEstado(),bean.getFoto(),
                    bean.getCodigo()});

            resultado=1;
            sql.close();
        }catch (Exception ex){
            resultado=-1;
        }
        System.out.println(resultado);
        return resultado;
    }

    public PersonaBean Buscar(String codigo){
        PersonaBean bean=null;
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur=sql.rawQuery("select * from persona where idpersona=?",new String []{codigo});

        if(cur.moveToNext()){
            bean = new PersonaBean();
            bean.setCodigo(cur.getInt(0));
            bean.setNombres(cur.getString(1));
            bean.setApaterno(cur.getString(2));
            bean.setAmaterno(cur.getString(3));
            bean.setSexo(cur.getInt(4));
            bean.setTipodoc(cur.getInt(5));
            bean.setNumdoc(cur.getString(6));
            bean.setFecnac(cur.getString(7));
            bean.setEmail(cur.getString(8));
            bean.setTelefono(cur.getString(9));
            bean.setEstado(cur.getInt(10));
            bean.setFoto(cur.getBlob(11));

        }

        sql.close();
        return  bean;

    }
}

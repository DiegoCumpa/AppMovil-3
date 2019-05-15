package proyecto.appcampeonato.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import proyecto.appcampeonato.beans.SexoBean;
import proyecto.appcampeonato.conexion.Conexion;


public class SexoDAO {

    Context contexto;

    public SexoDAO(Context ctx) {
        contexto = ctx;
    }

    public ArrayList<SexoBean> List() {
        ArrayList<SexoBean> sexo = new ArrayList<SexoBean>();
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur = sql.rawQuery("select * from sexo", null);
        SexoBean bean;
        while (cur.moveToNext()) {
            bean = new SexoBean();
            bean.setCodigo(cur.getInt(0));
            bean.setNombre(cur.getString(1));
            sexo.add(bean);
        }
        cur.close();
        return sexo;
    }


}

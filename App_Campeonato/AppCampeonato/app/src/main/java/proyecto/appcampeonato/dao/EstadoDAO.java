package proyecto.appcampeonato.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import proyecto.appcampeonato.beans.EstadoBean;
import proyecto.appcampeonato.conexion.Conexion;


public class EstadoDAO {

    Context contexto;

    public EstadoDAO(Context ctx) {
        contexto = ctx;
    }

    public ArrayList<EstadoBean> List() {
        ArrayList<EstadoBean> estado = new ArrayList<EstadoBean>();
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur = sql.rawQuery("select * from estado", null);
        EstadoBean bean;
        while (cur.moveToNext()) {
            bean = new EstadoBean();
            bean.setCodigo(cur.getInt(0));
            bean.setNombre(cur.getString(1));
            estado.add(bean);
        }
        cur.close();
        return estado;
    }


}

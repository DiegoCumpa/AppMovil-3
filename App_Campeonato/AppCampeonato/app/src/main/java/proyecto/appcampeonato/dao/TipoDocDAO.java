package proyecto.appcampeonato.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import proyecto.appcampeonato.beans.TipoDocBean;
import proyecto.appcampeonato.conexion.Conexion;


public class TipoDocDAO {

    Context contexto;

    public TipoDocDAO(Context ctx) {
        contexto = ctx;
    }

    public ArrayList<TipoDocBean> List() {
        ArrayList<TipoDocBean> tipoDoc = new ArrayList<TipoDocBean>();
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur = sql.rawQuery("select * from tipodoc", null);
        TipoDocBean bean;
        while (cur.moveToNext()) {
            bean = new TipoDocBean();
            bean.setCodigo(cur.getInt(0));
            bean.setNombre(cur.getString(1));
            tipoDoc.add(bean);
        }
        cur.close();
        return tipoDoc;
    }


}

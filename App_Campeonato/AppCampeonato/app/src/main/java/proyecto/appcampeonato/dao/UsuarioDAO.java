package proyecto.appcampeonato.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import proyecto.appcampeonato.beans.UsuarioBean;
import proyecto.appcampeonato.conexion.Conexion;

public class UsuarioDAO {

    Context contexto;
    public UsuarioDAO(Context ctx){
        contexto = ctx;

    }

    public UsuarioBean Login(String email, String password){
        UsuarioBean bean=null;
        Conexion cn = new Conexion(contexto);
        SQLiteDatabase sql = cn.getReadableDatabase();
        Cursor cur=sql.rawQuery("select * from usuario where email=? and pwd=?",new String []{email,password});

        if(cur.moveToNext()){
            bean = new UsuarioBean();
            bean.setEmail(cur.getString(0));
            bean.setPassword(cur.getString(1));

        }
        sql.close();
        return  bean;

    }
}
